package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorSpinner extends ArrayAdapter<Lugar> {


    public AdaptadorSpinner(@NonNull Context context, @NonNull ArrayList<Lugar> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
        TextView texto;
        ImageView img;

        if(row == null) {

            row = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            texto = row.findViewById(R.id.equipo);
            img = row.findViewById(R.id.logo);

            holder = new ViewHolder(texto, img);

            row.setTag(holder);

        } else {

                holder = (ViewHolder)row.getTag();

        }

        Lugar lugar = getItem(position);

        holder.getNombre().setText(lugar.getNombre());
        holder.getImg().setImageResource((lugar.getImagen()));

        return row;

    }

    class ViewHolder {

        private TextView nombre;
        private ImageView img;

        public ViewHolder(TextView nombre, ImageView img) {
            this.nombre = nombre;
            this.img = img;
        }

        public TextView getNombre() {
            return nombre;
        }

        public ImageView getImg() {
            return img;
        }
    }

}
