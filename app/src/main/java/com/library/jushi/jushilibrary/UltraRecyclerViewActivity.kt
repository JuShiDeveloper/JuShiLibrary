package com.library.jushi.jushilibrary

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.jushi.library.base.BaseFragmentActivity
import kotlinx.android.synthetic.main.ultra_rv_layout.*

class UltraRecyclerViewActivity : BaseFragmentActivity() {

    private val data: ArrayList<Int> = arrayListOf()

    override fun getLayoutResId(): Int = R.layout.ultra_rv_layout

    override fun initView() {

    }

    override fun initData() {
        for (i in 0..50) {
            data.add(i)
        }
        UltraRecyclerView.layoutManager = LinearLayoutManager(baseContext)
        UltraRecyclerView.adapter = TestAdapter()

    }

    override fun setListener() {
        test_btn.setOnClickListener { v ->
            val li: LinearInterpolator = LinearInterpolator()
            UltraRecyclerView.smoothScrollToPosition(30, 9000, null)
        }
    }


    inner class TestAdapter : RecyclerView.Adapter<TestAdapter.VH>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH = VH(LayoutInflater.from(baseContext).inflate(R.layout.test_item, p0, false))

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(p0: VH, p1: Int) {
            p0.textView.text = data[p1].toString()
        }

        inner class VH : RecyclerView.ViewHolder {
            lateinit var textView: TextView

            constructor(itemView: View) : super(itemView) {
                textView = itemView.findViewById(R.id.tv_test)
            }
        }
    }
}