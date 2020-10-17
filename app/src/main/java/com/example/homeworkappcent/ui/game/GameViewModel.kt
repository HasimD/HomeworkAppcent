package com.example.homeworkappcent.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkappcent.ui.database.GameItem
import kotlinx.coroutines.launch

class GameViewModel(private val model: GameModel) : ViewModel() {

    fun updateFavorite(gameItem: GameItem) = viewModelScope.launch {
        model.updateGameItem(gameItem)
    }
}