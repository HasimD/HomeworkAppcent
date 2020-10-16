package com.example.homeworkappcent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.home.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeView : Fragment() {

    private val viewModel by lazy { HomeViewModel(HomeModel(activity as AppCompatActivity)) }
    private val adapter by lazy { ViewPagerAdapter(viewModel, activity as AppCompatActivity) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = adapter
        viewModel.gameItemList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }
}