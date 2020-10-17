package com.example.homeworkappcent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.homeworkappcent.R
import com.example.homeworkappcent.ui.home.adapter.RecyclerViewAdapter
import com.example.homeworkappcent.ui.home.adapter.ViewPagerAdapter
import com.example.homeworkappcent.ui.utils.AnimationUtils
import kotlinx.android.synthetic.main.home.*

class HomeView : Fragment() {

    private val viewModel by lazy { HomeViewModel(HomeModel(activity as AppCompatActivity)) }
    private val adapter by lazy { ViewPagerAdapter(viewModel, activity as AppCompatActivity) }
    private val recyclerviewAdapter by lazy {
        RecyclerViewAdapter(
            viewModel,
            activity as AppCompatActivity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = this@HomeView.adapter
        viewModel.gameItemList.observe(viewLifecycleOwner, {
            AnimationUtils.showUI(linearLayout_gameList, linearLayout_progress)
            setUpIndicators()
            setCurrentIndicator(0)
            viewPager.setCurrentItem(0, true)
            adapter.notifyDataSetChanged()
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = recyclerviewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    private fun setUpIndicators() {
        if (linearLayout_indicators.size > 0) return

        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(activity)
            indicators[i].apply {
                this?.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_inactive
                    )
                })
                this?.layoutParams = layoutParams
            }
            linearLayout_indicators.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = linearLayout_indicators.childCount
        for (i in 0 until childCount) {
            val imageView = linearLayout_indicators[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_active
                    )
                })
            } else {
                imageView.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_inactive
                    )
                })
            }
        }
    }
}