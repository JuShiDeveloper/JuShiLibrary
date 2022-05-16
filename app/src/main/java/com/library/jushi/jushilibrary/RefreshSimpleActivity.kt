package com.library.jushi.jushilibrary

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jushi.library.base.BaseFragmentActivity
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.activity_refresh_simple_layout.*

class RefreshSimpleActivity : BaseFragmentActivity(), OnRefreshListener, OnLoadMoreListener {

    override fun navigationBar(): Boolean = true

    override fun getLayoutResId(): Int = R.layout.activity_refresh_simple_layout

    override fun initView() {
        recyclerView.adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(this@RefreshSimpleActivity) as RecyclerView.LayoutManager?
    }

    override fun initData() {
    }

    override fun setListener() {
        smartRefreshLayout.setOnRefreshListener(this)
        smartRefreshLayout.setOnLoadMoreListener(this)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.VH>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
            return VH(LayoutInflater.from(this@RefreshSimpleActivity).inflate(R.layout.refresh_item, p0, false))
        }

        override fun getItemCount(): Int = 20


        override fun onBindViewHolder(p0: VH, p1: Int) {
            p0.textView.text = "测试列表项$p1"
        }

        inner class VH(view: View) : RecyclerView.ViewHolder(view) {

            var textView: TextView = view.findViewById(R.id.textView)
        }
    }
}