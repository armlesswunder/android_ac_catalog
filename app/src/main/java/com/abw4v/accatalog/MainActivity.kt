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
import com.abw4v.accatalog.MainActivity.Companion.filter
import com.abw4v.accatalog.MainActivity.Companion.itemType
import com.abw4v.accatalog.MainActivity.Companion.qualifier
import com.abw4v.accatalog.MainActivity.Companion.selectedFilter
import java.lang.ref.WeakReference
import java.util.*
import android.app.Activity
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager

//TODO
//improve seasons selected logic

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var myDataset: MutableList<MutableMap<String, String>>
        lateinit var masterDataset: MutableList<MutableMap<String, String>>
        var name = ""
        var from = ""
        var useCurrentSeason = false
        var selected = false
        var selectedFilter = false
        var qualifier = "acnh_"
        var selectedSeason = ""
        var qualifierIndex = 4
        var tableIndex = 0
        var seasonIndex = 0
        var thisSeason = 0
        var itemType = "accessory"

        lateinit var recyclerView: RecyclerView
        lateinit var viewAdapter: RecyclerView.Adapter<*>
        lateinit var viewManager: RecyclerView.LayoutManager

        val gameDisplay = arrayOf("Gamecube", "Wild World", "City Folk", "New Leaf", "New Horizons")
        val seasonDisplay = arrayOf("(no filter)", "January", "February", "March", "April", "May", "June", "July", "August - 1st Half", "August - 2nd Half", "September - 1st Half", "September - 2nd Half", "October", "November", "December")

        var tableDisplayACGC = emptyArray<String>()
        var tableDisplayACWW = emptyArray<String>()
        var tableDisplayACCF = emptyArray<String>()
        var tableDisplayACNL = emptyArray<String>()
        var tableDisplayACNH = emptyArray<String>()

        var started = false

        val gameValues = arrayOf("acgc_", "acww_", "accf_", "acnl_", "acnh_")
        val seasonValues = arrayOf("", "jan_", "feb_", "mar_", "apr_", "may_", "jun_", "jul_", "aug1_", "aug2_", "sep1_", "sep2_", "oct_", "nov_", "dec_")

        fun filter(db: DBHelper, context: Context) {

            if (selectedFilter) {
                val sel = if (selected) "1" else "0"
                myDataset = masterDataset.filter { element -> element["Selected"] == sel
                }.toMutableList()

                myDataset = myDataset.filter { element ->
                    element["Name"]!!.toLowerCase().contains(name.toLowerCase())
                }.toMutableList()


                if (masterDataset[0].containsKey("From")) {
                    myDataset = myDataset.filter { element ->
                        element["From"]!!.toLowerCase().contains(from.toLowerCase())
                    }.toMutableList()
                }
            }
            else {

                myDataset = masterDataset.filter { element ->
                    element["Name"]!!.toLowerCase().contains(name.toLowerCase())
                }.toMutableList()


                if (masterDataset[0].containsKey("From")) {
                    myDataset = myDataset.filter { element ->
                        element["From"]!!.toLowerCase().contains(from.toLowerCase())
                    }.toMutableList()
                }
            }

            val recyclerViewState = recyclerView.layoutManager!!.onSaveInstanceState()
            recyclerView.adapter = RecViewAdapter(myDataset, db, context)
            recyclerView.adapter!!.notifyDataSetChanged()
            recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState)
        }
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

            val alertBuilder = AlertDialog.Builder(this@MainActivity)

            alertBuilder.apply {
                val layout = LayoutInflater.from(context).inflate(R.layout.settings_alert, null)
                setView(layout)
                val useCurrentDateCheckBox = layout.findViewById<CheckBox>(R.id.useCurrentDate)
                val devBtn = layout.findViewById<Button>(R.id.devBtn)

                useCurrentDateCheckBox.isChecked = useCurrentSeason

                devBtn.setOnClickListener {
                    var alert: AlertDialog = showAlert(this@MainActivity, "Recreating Database. Please wait.")
                    createAsync(db, WeakReference(this@MainActivity), alert).execute()
                }
                setPositiveButton("OK") { alert, _ ->
                    prefs.edit().putBoolean("use_current_date", useCurrentDateCheckBox.isChecked).apply()
                    useCurrentSeason = useCurrentDateCheckBox.isChecked
                }
            }.create().show()
        }

        val filterBtn = findViewById<ImageView>(R.id.filterBtn)
        filterBtn.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.apply {
                val layout = LayoutInflater.from(context).inflate(R.layout.filter_alert, null)
                setView(layout)

                val seasonLbl = layout.findViewById<TextView>(R.id.seasonLbl)
                val filterCheckBox = layout.findViewById<CheckBox>(R.id.filter)
                val selectedCheckBox = layout.findViewById<CheckBox>(R.id.selected)
                val fromSearchBar = layout.findViewById<EditText>(R.id.fromSearchBar)
                val seasonSpinner = layout.findViewById<Spinner>(R.id.seasonSpinner)

                val seasonAdapter = ArrayAdapter<String>(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    seasonDisplay
                )

                seasonSpinner.adapter = seasonAdapter
                seasonAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                filterCheckBox.isChecked = selectedFilter
                selectedCheckBox.isChecked = selected
                fromSearchBar.setText(from)

                seasonSpinner.setSelection(if (useCurrentSeason) thisSeason else seasonIndex)

                if (itemType == "fish" || itemType == "insect" || itemType == "seafood") {
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
                    val selectedSeason = seasonSpinner.selectedItem.toString()
                    from = fromSearchBar.text.toString()
                    selectedFilter = filterCheckBox.isChecked
                    selected = selectedCheckBox.isChecked

                    if (Companion.selectedSeason != selectedSeason) {

                        prefs.edit().putBoolean("filter", selectedFilter).apply()
                        prefs.edit().putBoolean("selected", selected).apply()
                        prefs.edit().putString("qualifier", qualifier).apply()

                        if (seasonIndex == 0) {
                            myDataset = db.getData(qualifier + itemType.replace(" ", "_"))
                            masterDataset = myDataset
                        } else {
                            myDataset =
                                db.getSeasonData(qualifier, itemType.replace(" ", "_"), seasonValues[seasonIndex])
                            masterDataset = myDataset
                        }
                    }
                    filter(db, this@MainActivity)
                    alert.dismiss()
                }
            }.show()
        }

        myDataset = db.getData(qualifier + itemType.replace(" ", "_"))
        masterDataset = myDataset

        tableDisplayACGC = db.getTableData(gameValues[0] + "table").toTypedArray()
        tableDisplayACWW = db.getTableData(gameValues[1] + "table").toTypedArray()
        tableDisplayACCF = db.getTableData(gameValues[2] + "table").toTypedArray()
        tableDisplayACNL = db.getTableData(gameValues[3] + "table").toTypedArray()
        tableDisplayACNH = db.getTableData(gameValues[4] + "table").toTypedArray()

        val gameSpinner = findViewById<Spinner>(R.id.gameSpinner)
        val tableSpinner = findViewById<Spinner>(R.id.tableSpinner)
        var tableDisplay = getItemTypeDisplayTable()

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
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    qualifierIndex = position
                    var tableDisplay = getItemTypeDisplayTable()
                    val tableAdapter = ArrayAdapter<String>(
                        this@MainActivity,
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

        tableSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    tableIndex = position

                    val selectedTable = tableSpinner.selectedItem.toString()

                    if (selectedTable != itemType) {
                        itemType = selectedTable

                        prefs.edit().putInt("item_type", tableIndex).apply()
                        prefs.edit().putString("table", itemType).apply()

                        if (itemType == "fish" || itemType == "insect" || itemType == "seafood") {
                            seasonIndex = if (useCurrentSeason) thisSeason else seasonIndex
                        } else {
                            seasonIndex = 0
                        }

                        if (seasonIndex == 0) {
                            myDataset = db.getData(qualifier + itemType.replace(" ", "_"))
                            masterDataset = myDataset
                        } else {
                            myDataset =
                                db.getSeasonData(qualifier, itemType.replace(" ", "_"), seasonValues[seasonIndex])
                            masterDataset = myDataset
                        }
                    }
                    if (started)
                        filter(db, this@MainActivity)
                }
            }

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
        started = true
        progressBar.visibility = View.GONE
    }

    fun getThisSeason() {
        if (useCurrentSeason) {
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
            seasonIndex = thisSeason
        }
        else {
            seasonIndex = 0
        }
    }

    fun getDefaults(prefs: SharedPreferences) {
        qualifierIndex = prefs.getInt("selected_game", qualifierIndex)
        tableIndex = prefs.getInt("item_type", tableIndex)
        selectedFilter = prefs.getBoolean("filter", selectedFilter)
        selected = prefs.getBoolean("selected", selected)
        firstTime = prefs.getBoolean("first_time", true)
        qualifier = prefs.getString("qualifier", qualifier) ?: "acnl_"
        itemType = prefs.getString("table", itemType) ?: "furniture"
        useCurrentSeason = prefs.getBoolean("use_current_date", true)
    }

    fun hideKeyboard(activity: AppCompatActivity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getItemTypeDisplayTable(): Array<String> {
        when (qualifierIndex) {
            0 -> return tableDisplayACGC
            1 -> return tableDisplayACWW
            2 -> return tableDisplayACCF
            3 -> return tableDisplayACNL
            4 -> return tableDisplayACNH
            else -> return tableDisplayACNL
        }
    }
}

class RecViewAdapter(private val values : MutableList<MutableMap<String, String>>, private val db: DBHelper, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        this.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

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

        holder.checkBtn.setOnClickListener {
            val index = item["Index"]!!
            item["Selected"] = if (item["Selected"] == "1") "0" else "1"
            db.checkItem(index, qualifier + itemType.replace(" ", "_"), item["Selected"])
            if (selectedFilter)
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
        holder.setIsRecyclable(false)
    }
    override fun getItemCount(): Int = values.size

    class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView: TextView = itemView.findViewById(R.id.textView)
        val checkBtn: AppCompatCheckBox = itemView.findViewById(R.id.checkBtn)
        val detailsBtn: ImageView = itemView.findViewById(R.id.detailsBtn)
    }

    class infoRecViewAdapter(private val keys: List<String>, private val values : MutableMap<String, String>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val NORMAL = 0
        val HIDDEN = 1

        override fun getItemViewType(position: Int): Int {
            return if (keys[position] == "Selected") HIDDEN else NORMAL
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

fun isChecked(value: String?): Boolean {
    if (value == null)
        return false
    else {
        return value == "1"
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
