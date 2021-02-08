package com.example.room;

import android.content.Context;
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

}
