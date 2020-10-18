package com.example.homeworkappcent.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkappcent.ui.database.AppRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FavoriteModel(
    private val activity: AppCompatActivity,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) {

    suspend fun getFavoriteGameItemList() = withContext(coroutineContext) {
        AppRoomDatabase.getDatabase(activity).gameDao().getFavoriteGameItemList()
    }
}