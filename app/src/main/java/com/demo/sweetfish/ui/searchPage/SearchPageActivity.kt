package com.demo.sweetfish.ui.searchPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.ui.component.TagLayout
import com.demo.sweetfish.ui.searchResultPage.SearchResultPageActivity
import com.example.sweetfish.R

class SearchPageActivity : AppCompatActivity() {

    private val viewModel: SearchPageActivityViewModel by lazy {
        ViewModelProvider(
            this, SearchPageActivityViewModel.SearchPageActivityViewModelFactory()
        )[SearchPageActivityViewModel::class.java]
    }
    private val historyTagLayout: TagLayout by lazy { findViewById(R.id.SearchPageHistoryTagLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)
        initComponent()
        initHistory()
    }

    private fun initComponent() {
        val returnButton = findViewById<ImageView>(R.id.SearchPageReturnButton)
        returnButton.setOnClickListener { finish() }
        val searchButton = findViewById<TextView>(R.id.SearchButton_Button)
        val searchBarEditView = findViewById<EditText>(R.id.SearchContent_EditText)
        searchButton.setOnClickListener {
            search(searchBarEditView.text.toString())
        }
        historyTagLayout.setOnClickEvent(::search)
    }

    private fun search(searchContent: String) {
        viewModel.addOrUpdateHistory(searchContent)
        val newIntent = Intent(this, SearchResultPageActivity::class.java)
        newIntent.putExtra("SearchContent", searchContent)
        startActivity(newIntent)
    }

    private fun initHistory() {
        viewModel.historyList.observe(this) { data ->
            val mapData: MutableMap<String, String> = mutableMapOf()
            for (item in data) {
                mapData[item] = item
            }
            historyTagLayout.init(mapData)
        }
    }
}