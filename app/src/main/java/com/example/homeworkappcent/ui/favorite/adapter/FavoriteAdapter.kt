package com.example.homeworkappcent.ui.favorite.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.database.GameItem
import com.example.homeworkappcent.ui.favorite.FavoriteViewModel
import com.example.homeworkappcent.ui.game.GameView
import com.example.homeworkappcent.ui.utils.Cache

class FavoriteAdapter(
    private val viewModel: FavoriteViewModel,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() = viewModel.gameItemList.value ?: emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(inflater.inflate(R.layout.favorite_row, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val gameItem = gameItemList[position]

        holder.row.setOnClickListener {
            Cache.currentGameItem = gameItem
            val intent = Intent(activity, GameView::class.java)
            activity.startActivity(intent)
        }

        holder.gameImage.apply { loadImageByGlide(gameItem.image, this) }
        holder.name.text = gameItem.name
        holder.rating.text = gameItem.rating
        holder.released.text = gameItem.releaseDate
    }

    override fun getItemCount() = gameItemList.size

    private fun loadImageByGlide(url: String, imageView: ImageView) {
        Glide.with(activity)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}