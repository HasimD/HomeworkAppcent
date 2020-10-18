package com.example.homeworkappcent.ui.favorite.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkappcent.R

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val row: LinearLayout = view.findViewById(R.id.linearLayout_row)
    val gameImage: ImageView = view.findViewById(R.id.imageView_game)
    val name: TextView = view.findViewById(R.id.textView_name)
    val rating: TextView = view.findViewById(R.id.textView_rating)
    val released: TextView = view.findViewById(R.id.textView_released)
}