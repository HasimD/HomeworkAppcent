package com.example.homeworkappcent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkappcent.ui.database.GameItem
import kotlinx.coroutines.launch

class HomeViewModel(private val model: HomeModel) : ViewModel() {

    private val mutableGameItemList = MutableLiveData<List<GameItem>>()

    val gameItemList: LiveData<List<GameItem>>
        get() = mutableGameItemList

    fun loadData() = viewModelScope.launch {
        mutableGameItemList.value = model.getGameItemList()
    }
}