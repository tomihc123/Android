package com.example.room;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTutorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTutorial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    private  LinearLayout indicadoresViewPager;

    private MaterialButton botton;

    private NovelaViewModel novelaViewModel;

    public FragmentTutorial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTutorial.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTutorial newInstance(String param1, String param2) {
        FragmentTutorial fragment = new FragmentTutorial();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        novelaViewModel = new ViewModelProvider(getActivity()).get(NovelaViewModel.class);

        rellenarViewPager();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.tutorial, container, false);


        botton = v.findViewById(R.id.botonViewPager);
        indicadoresViewPager = v.findViewById(R.id.viewPagerIndicador);

        viewPager2 = v.findViewById(R.id.viewPager);
        viewPager2.setAdapter(viewPagerAdapter);

        viewPagerIndicadores();
        coloresIndicadoresActual(0);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                coloresIndicadoresActual(position);
            }
        });

        botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager2.getCurrentItem() + 1 < viewPagerAdapter.getItemCount()) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    novelaViewModel.setVisualizacion(getResources().getString(R.string.VISUALIZACION_LISTA));
                }
            }
        });

        return v;

    }

    private void rellenarViewPager() {

        List<ViewPagerItem> viewPagerItemList = new ArrayList<>();

        viewPagerItemList.add(new ViewPagerItem("Gestiona las novelas",  "read.json", "Podras modificar, anadir, eliminar, filtrar las novelas de la aplicacion"));
        viewPagerItemList.add(new ViewPagerItem("Gestiona sus opiniones",  "speak.json", "Anade, elimina, y modifica los comentarios asociados a su novela"));
        viewPagerItemList.add(new ViewPagerItem("Descarga las novelas",  "download.json", "Verifica tu identidad medienta la huella biometrica para poder descargar la novela que mas te guste"));

        viewPagerAdapter = new ViewPagerAdapter(viewPagerItemList);

    }

    private void viewPagerIndicadores() {

        ImageView[] indicadores = new ImageView[viewPagerAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i < indicadores.length; i++)  {
            indicadores[i] = new ImageView(getActivity().getApplicationContext());
            indicadores[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.indicador_view_pager_inactivo));
            indicadores[i].setLayoutParams(layoutParams);
            indicadoresViewPager.addView(indicadores[i]);

        }

    }


    private void coloresIndicadoresActual(int indice) {

        int hijos =  indicadoresViewPager.getChildCount();

        for(int i = 0; i < hijos; i++) {

            ImageView imageView = (ImageView) indicadoresViewPager.getChildAt(i);

            if(i == indice) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.indicador_view_pager));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.indicador_view_pager_inactivo));
            }

        }
        if(indice == viewPagerAdapter.getItemCount() - 1) {
            botton.setText("Empezar");
        } else {
            botton.setText("Siguiente");
        }

    }

}