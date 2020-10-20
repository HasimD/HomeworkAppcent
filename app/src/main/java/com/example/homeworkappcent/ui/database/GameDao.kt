package com.example.homeworkappcent.ui.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class GameDao {

    @Insert
    abstract fun insert(gameItem: GameItem)

    @Update
    abstract fun update(gameItem: GameItem)

    @Query("SELECT * FROM GAME WHERE ID = :id")
    abstract fun getGameItemById(id: String): GameItem?

    @Query("SELECT * FROM GAME")
    abstract fun getGameItemList(): MutableList<GameItem>?


    @Query("SELECT * FROM GAME WHERE FAVORITE = 1")
    abstract fun getFavoriteGameItemList(): MutableList<GameItem>?

    @Query("SELECT * FROM GAME WHERE NAME LIKE '%' || :searchString || '%' ")
    abstract fun getGameItemListByNameSearch(searchString: String): MutableList<GameItem>?

    fun addGameItem(gameItem: GameItem) = insert(gameItem)

    fun updateGameItem(gameItem: GameItem) = update(gameItem)
}