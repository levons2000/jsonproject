package com.example.levon.jsonproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.levon.jsonproject.R;
import com.example.levon.jsonproject.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class BigPictureDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.big_picture_style, container, false);
        ImageView imageView = view.findViewById(R.id.big_picture);
        Picasso.get().load(((MainActivity) Objects.requireNonNull(getActivity())).getJsonModel().getUrl()).into(imageView);
        return view;
    }
}
