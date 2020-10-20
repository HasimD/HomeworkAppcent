package com.example.homeworkappcent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkappcent.ui.database.GameItem
import kotlinx.coroutines.launch

class HomeViewModel(private val model: HomeModel) : ViewModel() {

    private val mutableGameItemList = MutableLiveData<List<GameItem>>()

    private val mutableFilteredGameItemList = MutableLiveData<List<GameItem>>()

    val gameItemList: LiveData<List<GameItem>>
        get() = mutableGameItemList

    val filteredGameItemList: LiveData<List<GameItem>>
        get() = mutableFilteredGameItemList

    fun loadData() = viewModelScope.launch {
        mutableFilteredGameItemList.value = model.getGameItemList()
        mutableGameItemList.value = model.getGameItemList()
    }

    fun searchByName(searchString: String) = viewModelScope.launch {
        mutableFilteredGameItemList.value = model.getGameItemListByNameSearch(searchString)
        mutableGameItemList.value = model.getGameItemList()
    }
}