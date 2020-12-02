package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterList extends ArrayAdapter<Novelas> {

    private ArrayList<Novelas> novelas;


    public AdapterList(@NonNull Context context, @NonNull ArrayList<Novelas> objects) {
        super(context, 0, objects);
        this.novelas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        TextView texto;
        ImageView img;
        ViewHolder holder;

        if(row==null) {

            row = LayoutInflater.from(getContext()).inflate(R.layout.row2, parent, false);

            texto = row.findViewById(R.id.equipo);
            img = row.findViewById((R.id.logo));

            holder = new ViewHolder(texto, img);

            row.setTag(holder);



        } else {


            holder = (ViewHolder) row.getTag();

        }

        Novelas novelas = getItem(position);

        if(novelas != null) {

            holder.getText().setText(novelas.getNombre());
            holder.getImg().setImageResource(novelas.getImagen());

        }

        return row;

    }




    class ViewHolder {

        TextView text;
        ImageView img;

        public ViewHolder(TextView text, ImageView img) {
            this.text = text;
            this.img = img;
        }

        public TextView getText() {
            return text;
        }

        public ImageView getImg() {
            return img;
        }
    }


}
