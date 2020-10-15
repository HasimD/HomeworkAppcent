package com.example.homeworkappcent.ui.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GAME")
data class GameItem(
    @JvmField @PrimaryKey var id: String,
    @JvmField @ColumnInfo(name = "NAME") var name: String,
    @JvmField @ColumnInfo(name = "RATING") var rating: String,
    @JvmField @ColumnInfo(name = "METACRITIC") var metacritic: String,
    @JvmField @ColumnInfo(name = "FAVORITE") var favorite: Boolean,
    @JvmField @ColumnInfo(name = "IMAGE") var image: String,
    @JvmField @ColumnInfo(name = "RELEASE_DATE") var releaseDate: String,
    @JvmField @ColumnInfo(name = "DESCRIPTION") var description: String
) {
    fun getId() = id

    fun getName() = name

    fun getRating() = rating

    fun getMetacritic() = metacritic

    fun getFavorite() = favorite

    fun getImage() = image

    fun getReleaseDate() = releaseDate

    fun getDescription() = description

    fun setId(id: String) {
        this.id = id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setRating(rating: String) {
        this.rating = rating
    }

    fun setMetacritic(metacritic: String) {
        this.metacritic = metacritic
    }

    fun setFavorite(favorite: Boolean) {
        this.favorite = favorite
    }

    fun setImage(image: String) {
        this.image = image
    }

    fun setReleaseDate(releaseDate: String) {
        this.releaseDate = releaseDate
    }

    fun setDescription(description: String) {
        this.description = description
    }
}