package com.example.room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.room.Model.User;
import com.example.room.viewmodel.AuthViewModel;
import com.example.room.viewmodel.NovelaViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfileSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileSettings extends Fragment {

    private NovelaViewModel novelaViewModel;
    private AuthViewModel authViewModel;

    private TextView username, joindate;
    private ImageView imageProfile, editImageProfile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfileSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfileSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfileSettings newInstance(String param1, String param2) {
        FragmentProfileSettings fragment = new FragmentProfileSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        novelaViewModel = new ViewModelProvider(getActivity()).get(NovelaViewModel.class);
        authViewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);

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
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        username = v.findViewById(R.id.usernameProfile);
        imageProfile = v.findViewById(R.id.settingProfilePicture);
        joindate = v.findViewById(R.id.joindateprofile);
        editImageProfile = v.findViewById(R.id.editProfilePicture);


        FirebaseFirestore.getInstance().collection("Users").document(authViewModel.getUser().getValue().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    User user = task.getResult().toObject(User.class);
                    username.setText(user.getUsername());
                    Glide.with(getActivity()).load(user.getImage()).into(imageProfile);
                    joindate.setText(user.getJoinDate());

                }
            }
        });






        return v;
    }
}