package com.example.homeworkappcent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.favorite.adapter.FavoriteAdapter
import com.example.homeworkappcent.ui.utils.animateHidden
import com.example.homeworkappcent.ui.utils.animateShown
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.favorite.*

class FavoriteView : Fragment() {

    private val viewModel by lazy { FavoriteViewModel(FavoriteModel(activity as AppCompatActivity)) }
    private val adapter by lazy { FavoriteAdapter(viewModel, activity as AppCompatActivity) }

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Game")
        }

        viewModel.gameItemList.observe(viewLifecycleOwner, {
            if (viewModel.gameItemList.value?.size == 0) {
                text_notFound.visibility = View.VISIBLE
            } else {
                text_notFound.visibility = View.GONE
            }

            linearLayout_gameList.animateShown()
            linearLayout_progress.animateHidden()
            adapter.notifyDataSetChanged()
        })

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = this@FavoriteView.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }
}