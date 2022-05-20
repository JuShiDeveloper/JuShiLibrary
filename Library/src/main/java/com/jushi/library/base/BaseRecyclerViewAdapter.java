package com.jushi.library.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的Adapter基类，节省写相同代码的时间
 *
 * @param <Data>       显示的数据
 * @param <ViewHolder> 继承{@link RecyclerView.ViewHolder}的类
 * @author jushi
 */
public abstract class BaseRecyclerViewAdapter<Data, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {
    protected Context context;
    private List<Data> data = new ArrayList<>();
    private OnItemClickListener clickListener;
    protected int curIndex = -1;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    /**
     * 首次加载或刷新数据时调用该方法
     *
     * @param data
     */
    public void setData(List<Data> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据时调用该方法
     *
     * @param data
     */
    public void addData(List<Data> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return createOnViewHolder(LayoutInflater.from(context), viewGroup);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vho, int index) {
        onBindData(vho, data.get(index), index);
        final int posi = index;
        vho.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                this.curIndex = posi;
                clickListener.onListItemClick(data.get(index), index);
                notifyDataSetChanged();
            }
        });
    }

    /**
     * 创建ViewHolder
     *
     * @param inflater
     * @param viewGroup
     * @return
     */
    protected abstract ViewHolder createOnViewHolder(LayoutInflater inflater, ViewGroup viewGroup);

    /**
     * 绑定数据
     *
     * @param vho
     * @param data
     * @param index
     */
    protected abstract void onBindData(ViewHolder vho, Data data, int index);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnItemClickListener<Data> {
        void onListItemClick(Data data, int index);
    }
}
