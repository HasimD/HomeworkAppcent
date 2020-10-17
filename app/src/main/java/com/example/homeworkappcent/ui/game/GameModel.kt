package com.example.homeworkappcent.ui.game

import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkappcent.ui.database.AppRoomDatabase
import com.example.homeworkappcent.ui.database.GameItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GameModel(
    private val activity: AppCompatActivity,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) {

    suspend fun updateGameItem(gameItem: GameItem) = withContext(coroutineContext) {
        AppRoomDatabase.getDatabase(activity).gameDao().updateGameItem(gameItem)
    }
}