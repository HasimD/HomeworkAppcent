package com.example.homeworkappcent.ui.home

import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkappcent.ui.database.AppRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomeModel(
    private val activity: AppCompatActivity,
    private val coroutineContext: CoroutineContext = Dispatchers.Default
) {

    suspend fun getGameItemList() = withContext(coroutineContext) {
        AppRoomDatabase.getDatabase(activity).gameDao().getGameItemList()
    }

    suspend fun getGameItemList(searchString: String) = withContext(coroutineContext) {
        AppRoomDatabase.getDatabase(activity).gameDao().getGameItemList(searchString)
    }
}