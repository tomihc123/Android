package com.example.room;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListComentarioAdapter extends RecyclerView.Adapter<ListComentarioAdapter.ViewHolder> {

    private List<Comentario> comentarios = new ArrayList<>();
    private final OnItemClickListener listener;

    public ListComentarioAdapter(OnItemClickListener onItemClickListener, List<Comentario> comentarios) {
        this.listener = onItemClickListener;
        this.comentarios = comentarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comentario comentario = comentarios.get(position);
        holder.nombre.setText(comentario.getTexto());
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.comentarios.size();
    }

    public Comentario getItemAtPosition(int position) {
        return comentarios.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.comentario);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(comentarios.get(getAdapterPosition()));
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Comentario comentario);
    }


}