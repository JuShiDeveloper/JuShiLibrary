package com.jushi.library.amap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.jushi.library.R;
import com.jushi.library.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.VH> {
    private Context context;
    private List<PoiItem> poiItems = new ArrayList<>();
    private OnItemClickListener listener;
    private String administrativeDivision = "";
    private int currentSelect = -1;

    public LocationsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PoiItem> poiItems) {
        this.poiItems.clear();
        this.poiItems.addAll(poiItems);
        Collections.sort(this.poiItems, (o1, o2) -> o1.getDistance()>o2.getDistance()?0:-1);
        notifyDataSetChanged();
    }

    public void setCurrentSelect(int index){
        currentSelect = index;
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
        vh.distance.setText((poiItems.get(i).getDistance()==-1?0:poiItems.get(i).getDistance())+"m");
        vh.line.setVisibility(i < poiItems.size() - 1 ? View.VISIBLE : View.INVISIBLE);
        vh.itemView.setOnClickListener(v -> {
            if (listener != null)
                listener.onItemClick(poiItems.get(i));
            currentSelect = poiItems.indexOf(poiItems.get(i));
        });
        vh.ivSelect.setVisibility(currentSelect==i?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return poiItems.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public TextView distance;
        public View line;
        public ImageView ivSelect;

        public VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            distance = itemView.findViewById(R.id.tv_distance);
            line = itemView.findViewById(R.id.v_location_line);
            ivSelect = itemView.findViewById(R.id.iv_select);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(PoiItem poiItem);
    }
}
