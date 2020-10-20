package com.example.homeworkappcent

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.homeworkappcent.ui.database.AppRoomDatabase
import com.example.homeworkappcent.ui.database.GameDao
import com.example.homeworkappcent.ui.database.GameItem
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var gameDao: GameDao
    private lateinit var db: AppRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppRoomDatabase::class.java
        ).build()
        gameDao = db.gameDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addItemAndFindById() {
        val gameItem = GameItem("id", "test", "test", "test", false, "test", "test", "test")
        gameDao.addGameItem(gameItem)

        val gameItemById = gameDao.getGameItemById(gameItem.id)
        assertThat(gameItemById, equalTo(gameItem))
    }

    @Test
    @Throws(Exception::class)
    fun notAddItemAndFindById() {
        val gameItemById = gameDao.getGameItemById("id")
        assertNull(gameItemById)
    }

    @Test
    @Throws(Exception::class)
    fun addItemsAndGetItemList() {
        gameDao.apply {
            addGameItem(GameItem("id", "test", "test", "test", false, "test", "test", "test"))
            addGameItem(GameItem("id2", "test", "test", "test", false, "test", "test", "test"))
        }
        val gameItemList = gameDao.getGameItemList()

        assertNotNull(gameItemList)
        assertThat(gameItemList!!.size, equalTo(2))
    }

    @Test
    @Throws(Exception::class)
    fun addItemsAndGetFavoriteList() {
        val gameItemFavorite = GameItem("id", "test", "test", "test", true, "test", "test", "test")
        val gameItemNotFavorite =
            GameItem("id2", "test", "test", "test", false, "test", "test", "test")
        gameDao.apply {
            addGameItem(gameItemFavorite)
            addGameItem(gameItemNotFavorite)
        }

        val gameItemByFavoriteList = gameDao.getFavoriteGameItemList()
        assertNotNull(gameItemByFavoriteList)
        assertThat(gameItemByFavoriteList!!.size, equalTo(1))
        assertThat(gameItemByFavoriteList[0], equalTo(gameItemFavorite))
    }

    @Test
    @Throws(Exception::class)
    fun addItemsAndGetByNameSearch() {
        gameDao.apply {
            addGameItem(GameItem("id1", "test1", "test", "test", false, "test", "test", "test"))
            addGameItem(GameItem("id2", "test2", "test", "test", false, "test", "test", "test"))
            addGameItem(GameItem("id3", "test3", "test", "test", false, "test", "test", "test"))
        }

        val gameItemsByNameSearch = gameDao.getGameItemListByNameSearch("test")
        assertNotNull(gameItemsByNameSearch)
        assertThat(gameItemsByNameSearch!!.size, equalTo(3))
        assertThat(gameItemsByNameSearch[0].name, containsString("test"))

        val gameItemsByNameSearchFalse = gameDao.getGameItemListByNameSearch("shouldNotBeFound")
        assertTrue(gameItemsByNameSearchFalse!!.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun addItemAndUpdateTest() {
        val gameItem = GameItem("id", "test1", "test", "test", false, "test", "test", "test")
        gameDao.apply {
            addGameItem(gameItem)
        }

        val gameItemNotUpdated = gameDao.getGameItemById(gameItem.id)
        assertFalse(gameItemNotUpdated!!.favorite)

        gameItem.favorite = true
        gameDao.update(gameItem)
        val gameItemUpdated = gameDao.getGameItemById(gameItem.id)
        assertTrue(gameItemUpdated!!.favorite)
    }
}