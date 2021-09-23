package com.example.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<ViewPagerItem> items = new ArrayList<>();

    public ViewPagerAdapter(List<ViewPagerItem> items) {
        this.items = items;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager, parent, false);
        return new ViewPagerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem item = items.get(position);
        holder.titulo.setText(item.getTitulo());
        holder.lottieAnimationView.setAnimation(item.getRutaAnimacion());
        holder.descripcion.setText(item.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo, descripcion;
        private LottieAnimationView lottieAnimationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titulo = itemView.findViewById(R.id.pagerName);
            this.descripcion = itemView.findViewById(R.id.pagerDescripcion);
            lottieAnimationView = itemView.findViewById(R.id.animation);
        }

    }

}
