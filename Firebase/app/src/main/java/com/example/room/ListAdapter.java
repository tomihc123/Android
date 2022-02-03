/*package com.example.room;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NovelaConComentarios> novelas = new ArrayList<>();
    private final OnItemClickListener listener;

    public ListAdapter(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(novelas.get(position).getNovela().getNombre().toLowerCase().equals("reverend insanity")) {
            return 0;
        } else {
            return 1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
            return new ViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder2(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Novela novela = novelas.get(position).getNovela();
        if(getItemViewType(position) == 0) {
            ((ViewHolder)holder).nombre.setText(novela.getNombre());
            ((ViewHolder)holder).imagen.setImageResource(novela.getImagen());
            ((ViewHolder)holder).descripcion.setText(novela.getDescripcion());
            ((ViewHolder)holder).autor.setText(novela.getAutor());
        } else {
            ((ViewHolder2)holder).nombre.setText(novela.getNombre());
            ((ViewHolder2)holder).imagen.setImageResource(novela.getImagen());
        }
    }


    public void setNovelas(List<NovelaConComentarios> novelas) {
        this.novelas = novelas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.novelas.size();
    }

    public Novela getItemAtPosition(int position) {
        return novelas.get(position).getNovela();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre, descripcion, autor, leerMas, editar;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
            descripcion = itemView.findViewById(R.id.descripcion);
            autor = itemView.findViewById(R.id.autor);
            leerMas = itemView.findViewById(R.id.leerMas);
            editar = itemView.findViewById(R.id.editar);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), leerMas.getId());
                }
            });

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), editar.getId());
                }
            });

        }

    }


     class ViewHolder2 extends RecyclerView.ViewHolder {

       private TextView nombre;
       private ImageView imagen;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NovelaConComentarios novela, int id);
    }

} */



package com.example.room;


import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.room.Model.Novela;
import com.example.room.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Novela> novelas = new ArrayList<>();
    private final OnItemClickListener listener;
    private final Context context;

    public ListAdapter(OnItemClickListener onItemClickListener, Context context) {
        this.listener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Novela novela = novelas.get(position);
        holder.nombre.setText(novela.getNombre());
        Glide.with(holder.itemView).load(novela.getImagen()).into(holder.imagen);
        holder.descripcion.setText(novela.getDescripcion());
        holder.autor.setText(novela.getAutor());
        holder.numLikes.setText(novela.getLikes()+"");
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transaction));

      FirebaseFirestore.getInstance().collection("Users").document(novela.getPublicador()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    holder.nombrePublicador.setText(user.getUsername());
                    Glide.with(holder.itemView).load(FirebaseStorage.getInstance().getReference().child("images/"+user.getImage())).into(holder.imagePublicador);
                }
            }
        });


        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                if(user.getIdNovelasLikes().contains(novela.getId())) {
                    holder.iconLike.setColorFilter(Color.parseColor("#26D5F8"), PorterDuff.Mode.SRC_IN);
                    holder.iconLike.setTag("Azul");
                } else {
                    holder.iconLike.setColorFilter(Color.parseColor("#C3C3C3"), PorterDuff.Mode.SRC_IN);
                    holder.iconLike.setTag("Gris");
                }
            }
        });

      /*  holder.iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(holder.iconLike.getTag().equals("Gris")) {
                    novela.setLikes(novela.getLikes() + 1);
                    holder.numLikes.setText(novela.getLikes() + "");
                    holder.iconLike.setColorFilter(Color.parseColor("#26D5F8"), PorterDuff.Mode.SRC_IN);
                    holder.iconLike.setTag("Azul");
                    FirebaseFirestore.getInstance().collection(("Users")).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            User user = task.getResult().toObject(User.class);
                            if(!user.getIdNovelasLikes().contains(novela.getId())) {
                                user.getIdNovelasLikes().add(novela.getId());
                                FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("idNovelasLikes", user.getIdNovelasLikes());
                            }
                        }
                    });
                } else if (holder.iconLike.getTag().equals("Azul")) {
                    if(novela.getLikes() >= 1) {
                        novela.setLikes(novela.getLikes() + -1);
                        holder.numLikes.setText(novela.getLikes() + "");
                        holder.iconLike.setColorFilter(Color.parseColor("#C3C3C3"), PorterDuff.Mode.SRC_IN);
                        holder.iconLike.setTag("Gris");
                        FirebaseFirestore.getInstance().collection(("Users")).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                User user = task.getResult().toObject(User.class);
                                if(user.getIdNovelasLikes().contains(novela.getId())) {
                                    user.getIdNovelasLikes().remove(novela.getId());
                                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("idNovelasLikes", user.getIdNovelasLikes());
                                }
                            }
                        });

                    }
                }


            }
        }); */
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
        private TextView nombre, descripcion, autor, leerMas, numLikes, nombrePublicador;
        private ImageView imagen, iconDownload, iconLike, imagePublicador;
        private CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.logo);
            descripcion = itemView.findViewById(R.id.descripcion);
            autor = itemView.findViewById(R.id.autor);
            leerMas = itemView.findViewById(R.id.leerMas);
            iconDownload = itemView.findViewById(R.id.download);
            numLikes = itemView.findViewById(R.id.numLikes);
            iconLike = itemView.findViewById(R.id.likes);
            nombrePublicador = itemView.findViewById(R.id.nombreUsuarioPublica);
            imagePublicador = itemView.findViewById(R.id.profilePictureUser);
            cardView = itemView.findViewById(R.id.cardViwLista);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), leerMas.getId());
                }
            });


            iconDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(novelas.get(getAdapterPosition()), iconDownload.getId());
                }
            });

            iconLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Novela novela = novelas.get(getAdapterPosition());


                    if(iconLike.getTag().equals("Gris")) {
                                novela.setLikes(novela.getLikes() + 1);
                                numLikes.setText(novela.getLikes() + "");
                                iconLike.setColorFilter(Color.parseColor("#26D5F8"), PorterDuff.Mode.SRC_IN);
                                iconLike.setTag("Azul");
                                FirebaseFirestore.getInstance().collection(("Users")).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        User user = task.getResult().toObject(User.class);
                                        if(!user.getIdNovelasLikes().contains(novela.getId())) {
                                            user.getIdNovelasLikes().add(novela.getId());
                                            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("idNovelasLikes", user.getIdNovelasLikes());
                                        }
                                    }
                                });
                            } else if (iconLike.getTag().equals("Azul")) {
                        if (novela.getLikes() >= 1) {
                            novela.setLikes(novela.getLikes() + -1);
                            numLikes.setText(novela.getLikes() + "");
                            iconLike.setColorFilter(Color.parseColor("#C3C3C3"), PorterDuff.Mode.SRC_IN);
                            iconLike.setTag("Gris");
                            FirebaseFirestore.getInstance().collection(("Users")).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    User user = task.getResult().toObject(User.class);
                                    if (user.getIdNovelasLikes().contains(novela.getId())) {
                                        user.getIdNovelasLikes().remove(novela.getId());
                                        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update("idNovelasLikes", user.getIdNovelasLikes());
                                    }
                                }

                            });
                        }

                    }

                    listener.onItemClick(novelas.get(getAdapterPosition()), iconLike.getId());

                        }

                    });
                }
        }

    public interface OnItemClickListener {
        void onItemClick(Novela novela, int id);
    }

}