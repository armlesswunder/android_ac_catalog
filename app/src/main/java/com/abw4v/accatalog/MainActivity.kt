package com.abw4v.accatalog

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abw4v.accatalog.MainActivity.Companion.selectedFilter
import java.lang.ref.WeakReference
import java.util.*
import android.content.SharedPreferences
import androidx.cardview.widget.CardView
import com.abw4v.accatalog.MainActivity.Companion.useCritterWarningColors

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var myDataset: MutableList<MutableMap<String, String>>
        lateinit var masterDataset: MutableList<MutableMap<String, String>>
        var name = ""
        var from = ""
        var useCurrentSeason = false
        var useCritterWarningColors = true
        var selectedFilter = ALL_ITEMS
        var qualifier = "acnh_"
        var selectedSeason = ""
        var qualifierIndex = 4
        var tableIndex = 0
        var seasonIndex = 0
        var selectedSeasonIndex = 0
        var thisSeason = 0
        var itemType = "all_furniture"

        lateinit var recyclerView: RecyclerView
        lateinit var viewAdapter: RecyclerView.Adapter<*>
        lateinit var viewManager: RecyclerView.LayoutManager

        val gameDisplay = arrayOf("Gamecube", "Wild World", "City Folk", "New Leaf", "New Horizons")
        val seasonDisplay = arrayOf("(no filter)", "January", "February", "March", "April", "May", "June", "July", "August - 1st Half", "August - 2nd Half", "September - 1st Half", "September - 2nd Half", "October", "November", "December")
        val selectedDisplay = arrayOf("All items", "Checked items", "Unchecked items")

        var tableDisplayACGC = emptyArray<String>()
        var tableDisplayACWW = emptyArray<String>()
        var tableDisplayACCF = emptyArray<String>()
        var tableDisplayACNL = emptyArray<String>()
        var tableDisplayACNH = emptyArray<String>()

        var started = false

        val gameValues = arrayOf("acgc_", "acww_", "accf_", "acnl_", "acnh_")
        val seasonValues = arrayOf("", "jan_", "feb_", "mar_", "apr_", "may_", "jun_", "jul_", "aug1_", "aug2_", "sep1_", "sep2_", "oct_", "nov_", "dec_")
    }
    lateinit var progressBar: ProgressBar
    var firstTime = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("default", Context.MODE_PRIVATE)
        getDefaults(prefs)
        getThisSeason()
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        if (!firstTime) {
            setupViews(prefs)
        }
        else {
            var alert: AlertDialog
            val alertBuilder = AlertDialog.Builder(this)
            var btn: Button?
            alert = alertBuilder.apply {
                val layout = LayoutInflater.from(context).inflate(R.layout.loading_alert, null)
                setView(layout)
                val msg = layout.findViewById<TextView>(R.id.loadingMsg)
                btn = layout.findViewById<Button>(R.id.button)
                msg.text = "This App needs to run First-Time setup. This takes approximately 10 seconds. DO NOT close the application while this is happening."
                setCancelable(false)
                setOnDismissListener {
                    setupViews(prefs)
                    prefs.edit().putBoolean("first_time", false).commit()
                    firstTime = false
                }
            }.show()
            btn?.setOnClickListener {
                alert.dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard(this)
    }

    fun setupViews(prefs: SharedPreferences) {
        val db = DBHelper(this)

        findViewById<ImageView>(R.id.settingsBtn).setOnClickListener {
            settingsBtnPressed(db, prefs)
        }

        val filterBtn = findViewById<ImageView>(R.id.filterBtn)
        filterBtn.setOnClickListener {
            filterBtnPressed(db, prefs)
        }

        if(!useSeasonData()) {
            seasonIndex = 0
        }
        getData(db)

        tableDisplayACGC = db.getTableData(gameValues[0] + "table").toTypedArray()
        tableDisplayACWW = db.getTableData(gameValues[1] + "table").toTypedArray()
        tableDisplayACCF = db.getTableData(gameValues[2] + "table").toTypedArray()
        tableDisplayACNL = db.getTableData(gameValues[3] + "table").toTypedArray()
        tableDisplayACNH = db.getTableData(gameValues[4] + "table").toTypedArray();

        val gameSpinner = findViewById<Spinner>(R.id.gameSpinner)
        val tableSpinner = findViewById<Spinner>(R.id.tableSpinner)
        val tableDisplay = getItemTypeDisplayTable()

        val gameAdapter = ArrayAdapter<String>(
            this@MainActivity,
            R.layout.spinner_layout,
            gameDisplay
        )
        val tableAdapter = ArrayAdapter<String>(
            this@MainActivity,
            R.layout.spinner_layout,
            tableDisplay
        )

        gameSpinner.adapter = gameAdapter
        tableSpinner.adapter = tableAdapter

        gameAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        tableAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        gameSpinner.setSelection(qualifierIndex)
        tableSpinner.setSelection(tableIndex)

        gameSpinner.onItemSelectedListener =
            GameSpinnerListener(this, prefs, tableSpinner, gameSpinner)

        tableSpinner.onItemSelectedListener =
            TableSpinnerListener(this, db, prefs, tableSpinner)

        viewManager = LinearLayoutManager(this)
        viewAdapter = RecViewAdapter(myDataset, db, this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val searchBar = findViewById<EditText>(R.id.search_bar)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
                filter(db, this@MainActivity)
            }
        })

        findViewById<Button>(R.id.clearBtn).setOnClickListener {
            searchBar.text.clear()
        }
        filter(db, this)
        progressBar.visibility = View.GONE
    }

    fun getThisSeason() {
        thisSeason = Calendar.getInstance().get(Calendar.MONTH)
        thisSeason++
        //august
        if (thisSeason == 8) {
            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 15) {
                thisSeason++
            }
        } else if (thisSeason == 9) {
            thisSeason++
            if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 15) {
                thisSeason++
            }
        } else if (thisSeason > 9) {
            thisSeason += 2
        }
        if (useCurrentSeason) {
            seasonIndex = thisSeason
            selectedSeasonIndex = seasonIndex
        }
        else {
            seasonIndex = selectedSeasonIndex
        }
    }

    fun getDefaults(prefs: SharedPreferences) {
        qualifierIndex = prefs.getInt("selected_game", qualifierIndex)
        tableIndex = prefs.getInt("item_type", tableIndex)
        selectedFilter = prefs.getInt("selected_filter", selectedFilter)
        firstTime = prefs.getBoolean("first_time", true)
        qualifier = prefs.getString("qualifier", qualifier) ?: "acnh_"
        itemType = prefs.getString("table", itemType) ?: "furniture"
        useCurrentSeason = prefs.getBoolean("use_current_date", true)
        useCritterWarningColors = prefs.getBoolean("use_critter_warning_colors", true)
        selectedSeasonIndex = prefs.getInt("selected_season", 0)
    }

    class TableSpinnerListener(val context: Context, val db: DBHelper, val prefs: SharedPreferences, val tableSpinner: Spinner) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (!started) {
                started = true
                return
            }
            tableIndex = position

            val selectedTable = tableSpinner.selectedItem.toString()

            if (selectedTable != itemType) {
                itemType = selectedTable

                prefs.edit().putInt("item_type", tableIndex).apply()
                prefs.edit().putString("table", itemType).apply()

                if (useSeasonData()) {
                    seasonIndex = selectedSeasonIndex
                } else {
                    seasonIndex = 0
                }

                getData(db)
            }
            if (started)
                filter(db, context)
        }
    }

    class GameSpinnerListener(val context: Context, val prefs: SharedPreferences, val tableSpinner: Spinner, val gameSpinner: Spinner) : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (!started) {
                return
            }
            qualifierIndex = position
            var tableDisplay = getItemTypeDisplayTable()
            val tableAdapter = ArrayAdapter<String>(
                context,
                R.layout.spinner_layout,
                tableDisplay
            )
            tableSpinner.adapter = tableAdapter
            tableAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            if (tableIndex >= tableDisplay.size)
                tableIndex = 0

            qualifierIndex = gameSpinner.selectedItemPosition

            val selectedGame = gameValues[gameSpinner.selectedItemPosition]
            qualifier = selectedGame
            prefs.edit().putInt("selected_game", qualifierIndex).apply()
            prefs.edit().putString("qualifier", qualifier).apply()
            if (started)
                tableSpinner.setSelection(tableIndex)
        }
    }

    fun filterBtnPressed(db: DBHelper, prefs: SharedPreferences) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.apply {
            val layout = LayoutInflater.from(context).inflate(R.layout.filter_alert, null)
            setView(layout)

            val seasonLbl = layout.findViewById<TextView>(R.id.seasonLbl)
            val fromSearchBar = layout.findViewById<EditText>(R.id.fromSearchBar)
            val seasonSpinner = layout.findViewById<Spinner>(R.id.seasonSpinner)
            val selectedSpinner = layout.findViewById<Spinner>(R.id.selectedSpinner)

            val seasonAdapter = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                seasonDisplay
            )

            seasonSpinner.adapter = seasonAdapter
            seasonAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

            val selectedAdapter = ArrayAdapter<String>(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                selectedDisplay
            )

            selectedSpinner.adapter = selectedAdapter
            selectedAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            selectedSpinner.setSelection(selectedFilter)

            fromSearchBar.setText(from)

            if (useSeasonData()) {
                seasonSpinner.visibility = View.VISIBLE
                seasonLbl.visibility = View.VISIBLE
                seasonSpinner.setSelection(if (useCurrentSeason) thisSeason else seasonIndex)
            } else {
                seasonSpinner.visibility = View.GONE
                seasonLbl.visibility = View.GONE
                seasonSpinner.setSelection(0)
            }

            setPositiveButton("OK") { alert, _ ->
                seasonIndex = seasonSpinner.selectedItemPosition
                if (seasonSpinner.visibility == View.VISIBLE) {
                    selectedSeasonIndex = seasonIndex
                    prefs.edit().putInt("selected_season", selectedSeasonIndex).apply()
                }

                val selectedSeason = seasonSpinner.selectedItem.toString()
                from = fromSearchBar.text.toString()
                selectedFilter = selectedSpinner.selectedItemPosition

                if (Companion.selectedSeason != selectedSeason) {

                    prefs.edit().putInt("selected_filter", selectedFilter).apply()
                    prefs.edit().putString("qualifier", qualifier).apply()

                    if(!useSeasonData()) {
                        seasonIndex = 0
                    }

                    getData(db)
                }
                filter(db, this@MainActivity)
                alert.dismiss()
            }
        }.show()
    }

    fun settingsBtnPressed(db: DBHelper, prefs: SharedPreferences) {
        val alertBuilder = AlertDialog.Builder(this@MainActivity)

        alertBuilder.apply {
            val layout = LayoutInflater.from(context).inflate(R.layout.settings_alert, null)
            setView(layout)
            val useCurrentDateCheckBox = layout.findViewById<CheckBox>(R.id.useCurrentDate)
            val useCritterWarningColorsCheckbox = layout.findViewById<CheckBox>(R.id.useCritterWarningColors)
            val devBtn = layout.findViewById<Button>(R.id.devBtn)

            useCurrentDateCheckBox.isChecked = useCurrentSeason
            useCritterWarningColorsCheckbox.isChecked = useCritterWarningColors

            devBtn.setOnClickListener {
                var alert: AlertDialog = showAlert(this@MainActivity, "Recreating Database. Please wait.")
                createAsync(db, WeakReference(this@MainActivity), alert).execute()
            }
            setPositiveButton("OK") { alert, _ ->
                prefs.edit().putBoolean("use_current_date", useCurrentDateCheckBox.isChecked).apply()
                prefs.edit().putBoolean("use_critter_warning_colors", useCritterWarningColorsCheckbox.isChecked).apply()
                useCurrentSeason = useCurrentDateCheckBox.isChecked
                useCritterWarningColors = useCritterWarningColorsCheckbox.isChecked
                if (useSeasonData()) {
                    if (useCurrentSeason) {
                        seasonIndex = thisSeason
                        selectedSeasonIndex = seasonIndex
                        prefs.edit().putInt("selected_season", selectedSeasonIndex).apply()
                    }
                    getData(db)
                    filter(db, this@MainActivity)
                }
            }
        }.create().show()
    }
}

class RecViewAdapter(private val values : MutableList<MutableMap<String, String>>, private val db: DBHelper, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VHItem(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = values[position]
        (holder as VHItem).titleView.text = item["Name"]
        holder.checkBtn.isChecked = item["Selected"] == "1"

        if (useCritterWarningColors && item.containsKey("GoneNextMonth") && item["GoneNextMonth"].equals("true")) {
            holder.layoutBackground.setCardBackgroundColor(context.getColor(R.color.lightRed))
        }
        else if (useCritterWarningColors && item.containsKey("GonePreviousMonth") && item["GonePreviousMonth"].equals("true")) {
            holder.layoutBackground.setCardBackgroundColor(context.getColor(R.color.lightGreen))
        } else {
            holder.layoutBackground.setCardBackgroundColor(context.getColor(R.color.colorWhite))
        }

        holder.checkBtn.setOnClickListener {
            val index = item["Index"]!!
            item["Selected"] = if (item["Selected"] == "1") "0" else "1"
            db.checkItem(index, item["Type"]!!.replace(" ", "_"), item["Selected"])
            if (selectedFilter != ALL_ITEMS)
                filter(db, context)
        }
        holder.detailsBtn.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(context)
            alertBuilder.apply {
               val layout = LayoutInflater.from(context).inflate(R.layout.more_info, null)
                setView(layout)

                val viewManager = LinearLayoutManager(context)
                val adapter = infoRecViewAdapter(item.keys.toList(), item, context)

                val infoRecView = layout.findViewById<RecyclerView>(R.id.info_recycler_view)
                infoRecView.apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    this.adapter = adapter
                }

                setPositiveButton("OK", { a, b ->
                    a.dismiss()
                })
            }.show()
        }
    }
    override fun getItemCount(): Int = values.size

    class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutBackground: CardView = itemView.findViewById(R.id.layoutBackground)
        val titleView: TextView = itemView.findViewById(R.id.textView)
        val checkBtn: AppCompatCheckBox = itemView.findViewById(R.id.checkBtn)
        val detailsBtn: ImageView = itemView.findViewById(R.id.detailsBtn)
    }

    class infoRecViewAdapter(private val keys: List<String>, private val values : MutableMap<String, String>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val NORMAL = 0
        val HIDDEN = 1

        val HIDDEN_VALUES = arrayOf("Selected", "Index", "Type", "GoneNextMonth", "GonePreviousMonth")

        override fun getItemViewType(position: Int): Int {
            return if (HIDDEN_VALUES.contains(keys[position])) HIDDEN else NORMAL
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return VHItem(
                LayoutInflater.from(context)
                    .inflate(if (viewType == NORMAL) R.layout.info_item else R.layout.empty_item, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return keys.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val key = keys[position]
            val value = values[key]

            (holder as VHItem).infoText.text = key + ": " + value
        }

        class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val infoText: TextView = itemView.findViewById(R.id.infoText)
        }
    }
}

fun showAlert(context: Context, txt: String?) : AlertDialog {
    val alertBuilder = AlertDialog.Builder(context)
    return alertBuilder.apply {
        val layout = LayoutInflater.from(context).inflate(R.layout.loading_alert, null)
        setView(layout)
        val msg = layout.findViewById<TextView>(R.id.loadingMsg)
        if (txt != null)
            msg.text = txt
        setCancelable(false)
    }.show()
}

class createAsync(private val db: DBHelper, private val weakContext: WeakReference<Context>, private val alertDialog: AlertDialog) : AsyncTask<String, Void, String?>() {

    override fun doInBackground(vararg params: String?): String? {
        db.recreate()
        return null
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        alertDialog.cancel()
    }
}
