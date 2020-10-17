package com.example.homeworkappcent.ui.game

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.utils.Cache
import kotlinx.android.synthetic.main.game.*

class GameView : AppCompatActivity() {

    private val viewModel by lazy { GameViewModel(GameModel(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        if (Cache.currentGameItem == null) {
            finish()
            return
        }

        val gameItem = Cache.currentGameItem!!

        loadImageByGlide(gameItem.image, imageView_game)
        textView_name.text = gameItem.name
        textView_metacritic.text = gameItem.metacritic
        textView_released.text = gameItem.releaseDate
        textView_description.text = gameItem.description

        imageView_fav.apply {
            imageView_fav.setImageResource(if (gameItem.favorite) R.drawable.favorite else R.drawable.favorite_not)

            this.setOnClickListener {
                gameItem.favorite = !gameItem.favorite
                imageView_fav.setImageResource(if (gameItem.favorite) R.drawable.favorite else R.drawable.favorite_not)
                viewModel.updateFavorite(gameItem)
            }
        }
    }

    private fun loadImageByGlide(url: String, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}