package com.example.listviewconautocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterList extends ArrayAdapter<Novela> {

    public AdapterList(@NonNull Context context, @NonNull ArrayList<Novela> objects) {
        super(context, 0, objects);
    }


   @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

            if (getItem(position).getNombre().toLowerCase().equals("reverend insanity")) {
                return 1;
            } else {
                return 0;
            }
    }

    public int getItemLayout(int position) {

        switch (getItemViewType(position)) {

            case 0:
                return R.layout.row;

            case 1:
                return R.layout.row2;


            default:
                return 0;

        }

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        TextView nombre;
        ImageView imagen;
        ViewHolder holder;

        if(row == null) {

           row = LayoutInflater.from(getContext()).inflate(getItemLayout(position), parent, false);
           nombre = row.findViewById(R.id.nombre);
           imagen = row.findViewById(R.id.logo);

           holder = new ViewHolder(nombre, imagen);

           row.setTag(holder);

        } else {

            holder = (ViewHolder) row.getTag();

        }

        Novela novela = getItem(position);

        holder.getNombre().setText(novela.getNombre());
        holder.getImagen().setImageResource(novela.getImagen());

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.face_jn);
        row.startAnimation(animation);

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
