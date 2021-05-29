package com.example.whiletrue.ui.profil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.whiletrue.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        CircleImageView ava=root.findViewById(R.id.prof);
        Glide.with(getContext()).load("https://bipbap.ru/wp-content/uploads/2015/02/99ce2806d226f18.jpg")
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ava);

        Button izb=root.findViewById(R.id.izb);
        izb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FavoritesActivity.class));
            }
        });
        return root;
    }
}