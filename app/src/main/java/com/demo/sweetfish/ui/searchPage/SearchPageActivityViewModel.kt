package com.demo.sweetfish.ui.searchPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import kotlin.concurrent.thread

class SearchPageActivityViewModel : ViewModel() {

    private val _historyList: MutableLiveData<List<String>> =
        MutableLiveData<List<String>>()

    val historyList: LiveData<List<String>>
        get() = _historyList

    init {
        thread {
            _historyList.postValue(
                AppDatabase.getDatabase(SweetFishApplication.context).searchHistoryDao().findAll()
            )
        }
    }

    fun addOrUpdateHistory(searchContent: String) {
        thread {
            AppDatabase.getDatabase(SweetFishApplication.context).searchHistoryDao()
                .insertOrUpdate(searchContent)
            _historyList.postValue(
                AppDatabase.getDatabase(SweetFishApplication.context).searchHistoryDao().findAll()
            )
        }
    }

    fun removeHistory(searchContent: String) {
        thread {
            AppDatabase.getDatabase(SweetFishApplication.context).searchHistoryDao()
                .delete(searchContent)
            _historyList.postValue(
                AppDatabase.getDatabase(SweetFishApplication.context).searchHistoryDao().findAll()
            )
        }
    }

    class SearchPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchPageActivityViewModel() as T
        }
    }
}