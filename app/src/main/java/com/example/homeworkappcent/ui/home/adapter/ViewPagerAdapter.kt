package com.example.homeworkappcent.ui.home.adapter

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
import com.example.homeworkappcent.ui.game.GameView
import com.example.homeworkappcent.ui.home.HomeViewModel
import com.example.homeworkappcent.ui.utils.Cache

class ViewPagerAdapter(
    private val viewModel: HomeViewModel,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<ViewPagerViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() {
            viewModel.gameItemList.value?.apply {
                if (this.size > 3) return this.subList(0, 3)
            }
            return emptyList()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewPagerViewHolder(inflater.inflate(R.layout.home_viewpager_item, parent, false))

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val gameItem = gameItemList[position]

        holder.imageView.apply {
            loadImageByGlide(gameItem.image, this)

            this.setOnClickListener {
                Cache.currentGameItem = gameItem
                val intent = Intent(activity, GameView::class.java)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = gameItemList.size

    private fun loadImageByGlide(url: String, imageView: ImageView) {
        Glide.with(activity)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}