package com.example.homeworkappcent.ui.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
abstract class GameDao {

    @Insert
    abstract fun insert(gameItem: GameItem)

    @Delete
    abstract fun delete(gameItem: GameItem)

    @Query("SELECT * FROM GAME WHERE ID = :id")
    abstract fun getGameItemById(id: String): GameItem?

    @Query("SELECT * FROM GAME")
    abstract fun getGameItemList(): MutableList<GameItem>

    fun addGameItem(gameItem: GameItem) = insert(gameItem)

    fun removeGameItem(gameItem: GameItem) = delete(gameItem)
}