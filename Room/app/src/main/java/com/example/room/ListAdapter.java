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

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Novela> novelas = new ArrayList<>();
    private final OnItemClickListener listener;

    public ListAdapter(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Novela novela = novelas.get(position);
        holder.nombre.setText(novela.getNombre());
        holder.imagen.setImageResource(novela.getImagen());
    }

    public void setNovelas(List<Novela> novelas) {
        this.novelas = novelas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.novelas.size();
    }

    public Novela getItemAtPosition(int position) {
        return novelas.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()));
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Novela novela);
    }


}


















/*import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Novela> {

    public ListAdapter(@NonNull Context context, @NonNull List<Novela> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        TextView nombre;
        ImageView imagen;
        ViewHolder holder;

        if(row == null) {

            row = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            nombre = row.findViewById(R.id.nombre);
            imagen = row.findViewById(R.id.logo);

            holder = new ViewHolder(nombre, imagen);

            row.setTag(holder);

        } else {

            holder = (ViewHolder)row.getTag();
        }

        Novela novela = getItem(position);
        holder.getNombre().setText(novela.getNombre());
        holder.getImagen().setImageResource(novela.getImagen());

        //TODO implementar animacion

        return row;

    }

    class ViewHolder {

        TextView nombre;
        ImageView imagen;

        public ViewHolder(TextView nombre, ImageView imagen) {
            this.nombre = nombre;
            this.imagen = imagen;
        }


        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public ImageView getImagen() {
            return imagen;
        }

        public void setImagen(ImageView imagen) {
            this.imagen = imagen;
        }
    }

} */
