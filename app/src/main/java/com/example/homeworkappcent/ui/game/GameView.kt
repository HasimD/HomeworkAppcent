package com.example.homeworkappcent.ui.game

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.utils.Cache
import com.example.homeworkappcent.ui.utils.CommonUtils.loadImageByGlide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.game.*

class GameView : AppCompatActivity() {

    private val viewModel by lazy { GameViewModel(GameModel(this)) }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        title = "Game Details"

        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Game")
        }

        if (Cache.currentGameItem == null) {
            finish()
            return
        }

        val gameItem = Cache.currentGameItem!!

        loadImageByGlide(gameItem.image, imageView_game, this)
        textView_name.text = gameItem.name
        textView_metacritic.text = gameItem.metacritic
        textView_released.text = gameItem.releaseDate
        textView_description.text = gameItem.description

        imageView_fav.apply {
            imageView_fav.setImageResource(if (gameItem.favorite) R.drawable.favorite else R.drawable.favorite_not)

            this.setOnClickListener {
                gameItem.favorite = !gameItem.favorite
                imageView_fav.setImageResource(if (gameItem.favorite) R.drawable.favorite else R.drawable.favorite_not)

                if (gameItem.favorite) {
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                        param(FirebaseAnalytics.Param.ITEM_NAME, "FavoriteSelected")
                    }
                }

                viewModel.updateFavorite(gameItem)
            }
        }
    }
}