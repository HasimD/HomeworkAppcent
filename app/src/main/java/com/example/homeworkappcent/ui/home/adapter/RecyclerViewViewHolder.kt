package com.example.homeworkappcent.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkappcent.R

class RecyclerViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val gameImage: ImageView = view.findViewById(R.id.imageView_game)
    val name: TextView = view.findViewById(R.id.textView_name)
    val rating: TextView = view.findViewById(R.id.textView_rating)
    val released: TextView = view.findViewById(R.id.textView_released)
}