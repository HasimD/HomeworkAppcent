package com.example.homeworkappcent.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.database.GameItem
import com.example.homeworkappcent.ui.home.HomeViewModel

class RecyclerViewAdapter(
    private val viewModel: HomeViewModel,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() {
            viewModel.gameItemList.value?.apply {
                if (this.size > 3) return this.subList(3, this.size)
            }
            return emptyList()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerViewViewHolder(inflater.inflate(R.layout.fragment_home_row, parent, false))

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val gameItem = gameItemList[position]

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