package com.jushi.library.amap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jushi.library.R;

import java.util.ArrayList;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.VH> {
    private Context context;
    private List<PoiItem> poiItems = new ArrayList<>();
    private OnItemClickListener listener;
    private String administrativeDivision = "";

    public LocationsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PoiItem> poiItems) {
        this.poiItems.clear();
        this.poiItems.addAll(poiItems);
        notifyDataSetChanged();
    }

    /**
     * 设置行政区划
     *
     * @param str
     */
    public void setAdministrativeDivision(String str) {
        this.administrativeDivision = str;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.location_item, viewGroup, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.title.setText(poiItems.get(i).getTitle());
        vh.content.setText(administrativeDivision + poiItems.get(i).getSnippet());
        vh.line.setVisibility(i < poiItems.size() - 1 ? View.VISIBLE : View.INVISIBLE);
        vh.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(poiItems.get(i));
        });
    }

    @Override
    public int getItemCount() {
        return poiItems.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public View line;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            line = itemView.findViewById(R.id.v_location_line);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(PoiItem poiItem);
    }
}
