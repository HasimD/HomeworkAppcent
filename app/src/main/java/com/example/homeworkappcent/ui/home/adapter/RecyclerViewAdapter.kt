package com.example.homeworkappcent.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.database.GameItem
import com.example.homeworkappcent.ui.game.GameView
import com.example.homeworkappcent.ui.home.HomeViewModel
import com.example.homeworkappcent.ui.utils.Cache
import com.example.homeworkappcent.ui.utils.CommonUtils.loadImageByGlide

class RecyclerViewAdapter(
    private val viewModel: HomeViewModel,
    private val activity: AppCompatActivity
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() {
            viewModel.gameItemList.value?.apply {
                // if lists are not empty, and filteredGameItemList's size is not equal to gameItemList's size, then it means the search is in progress.
                if (this.isNotEmpty() && this.size != filteredGameItemList.size) {
                    return filteredGameItemList
                } // or maybe both of the lists are equal in size, then we can do our normal process. Which means, take the next sublist from index 3.
                else if (this.size > 3) {
                    return this.subList(3, this.size)
                }
            }
            return emptyList()
        }

    private val filteredGameItemList: List<GameItem>
        get() = viewModel.filteredGameItemList.value ?: emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerViewViewHolder(inflater.inflate(R.layout.home_row, parent, false))

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val gameItem = gameItemList[position]

        holder.row.setOnClickListener {
            Cache.currentGameItem = gameItem
            val intent = Intent(activity, GameView::class.java)
            activity.startActivity(intent)
        }

        holder.gameImage.apply { loadImageByGlide(gameItem.image, this, activity) }
        holder.name.text = gameItem.name
        holder.rating.text = gameItem.rating
        holder.released.text = gameItem.releaseDate
    }

    override fun getItemCount() = gameItemList.size
}