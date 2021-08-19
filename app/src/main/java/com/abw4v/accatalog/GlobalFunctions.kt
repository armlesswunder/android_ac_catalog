package com.abw4v.accatalog

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

const val ALL_ITEMS = 0
const val SELECTED_ITEMS = 1
const val UNSELECTED_ITEMS = 2

fun useSeasonData() : Boolean {
    return MainActivity.itemType == "fish" || MainActivity.itemType == "insect" || MainActivity.itemType == "seafood" || MainActivity.itemType == "sea creature"
}

fun getData(db: DBHelper) {
    if (MainActivity.seasonIndex == 0) {
        MainActivity.myDataset = db.getData(MainActivity.qualifier + MainActivity.itemType.replace(" ", "_"))
        MainActivity.masterDataset = MainActivity.myDataset
    } else {
        MainActivity.myDataset =
            db.getSeasonData(MainActivity.qualifier, MainActivity.itemType.replace(" ", "_"), MainActivity.seasonValues[MainActivity.seasonIndex])
        MainActivity.masterDataset = MainActivity.myDataset
    }

    MainActivity.fromDataset = db.getFromData(MainActivity.qualifier + MainActivity.itemType.replace(" ", "_"))
    if (MainActivity.fromDataset.size == 1 && MainActivity.fromDataset.contains("From")) {
        MainActivity.fromDataset = emptyList()
    }
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
    when (MainActivity.qualifierIndex) {
        0 -> return MainActivity.tableDisplayACGC
        1 -> return MainActivity.tableDisplayACWW
        2 -> return MainActivity.tableDisplayACCF
        3 -> return MainActivity.tableDisplayACNL
        4 -> return MainActivity.tableDisplayACNH
        else -> return MainActivity.tableDisplayACNH
    }
}

fun filter(db: DBHelper, context: Context) {

    if (MainActivity.selectedFilter != ALL_ITEMS) {
        val sel = if (MainActivity.selectedFilter == SELECTED_ITEMS) "1" else "0"
        MainActivity.myDataset = MainActivity.masterDataset.filter { element -> element["Selected"] == sel
        }.toMutableList()

        MainActivity.myDataset = MainActivity.myDataset.filter { element ->
            element["Name"]!!.toLowerCase().replace("-", " ").contains(MainActivity.name.toLowerCase().replace("-", " "))
        }.toMutableList()


        if (MainActivity.masterDataset[0].containsKey("From")) {
            MainActivity.myDataset = MainActivity.myDataset.filter { element ->
                element["From"] != null && element["From"]!!.toLowerCase().contains(MainActivity.from.toLowerCase())
            }.toMutableList()
        }
    }
    else {

        MainActivity.myDataset = MainActivity.masterDataset.filter { element ->
            element["Name"]!!.toLowerCase().replace("-", " ").contains(MainActivity.name.toLowerCase().replace("-", " "))
        }.toMutableList()


        if (MainActivity.masterDataset[0].containsKey("From") && MainActivity.from.isNotEmpty()) {
            MainActivity.myDataset = MainActivity.myDataset.filter { element ->
                element["From"] != null && element["From"]!!.toLowerCase().contains(MainActivity.from.toLowerCase())
            }.toMutableList()
        }
    }

    val recyclerViewState = MainActivity.recyclerView.layoutManager!!.onSaveInstanceState()
    MainActivity.recyclerView.adapter = RecViewAdapter(MainActivity.myDataset, db, context)
    MainActivity.recyclerView.adapter!!.notifyDataSetChanged()
    MainActivity.recyclerView.layoutManager!!.onRestoreInstanceState(recyclerViewState)
}