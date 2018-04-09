/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ecandle.firebase.sosmapfire1.incidentedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecandle.firebase.sosmapfire1.R;


/**
 * Main UI for the incidente detail screen.
 */
public class IncidenteDetailFragment extends Fragment {

    public static final String ARGUMENT_INCIDENTE_ID = "INCIDENTE_ID";

//    private IncidenteDetailContract.UserActionsListener mActionsListener;

//    private TextView mDetailTitle;
//
//    private TextView mDetailDescription;
//
//    private ImageView mDetailImage;

    TextView textIncidenteName;
    ImageView ivTipoIncidente;
    TextView textDescripcion;
    TextView textFecha;
    TextView textHora;

    public static IncidenteDetailFragment newInstance(String IncidenteName, String TipoIncidenteImg, String Descripcion, String Fecha, String Hora) {
        Bundle arguments = new Bundle();
        //arguments.putString(ARGUMENT_INCIDENTE_ID, incidenteId);
        arguments.putString("IncidenteName", IncidenteName);
        arguments.putString("TipoIncidenteImg", TipoIncidenteImg);
        arguments.putString("Descripcion",Descripcion );
        arguments.putString("Fecha", Fecha);
        arguments.putString("Hora", Hora);
        IncidenteDetailFragment fragment = new IncidenteDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mActionsListener = new IncidenteDetailPresenter(Injection.provideIncidentesRepository(),
//                this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
//        mDetailTitle = (TextView) root.findViewById(R.id.incidente_detail_title);
//        mDetailDescription = (TextView) root.findViewById(R.id.incidente_detail_description);
//        mDetailImage = (ImageView) root.findViewById(R.id.incidente_detail_image);

        textIncidenteName = (TextView) root.findViewById(R.id.textIncidenteName);
        ivTipoIncidente = (ImageView) root.findViewById(R.id.ivTipoIncidente);
        textDescripcion = (TextView) root.findViewById(R.id.textDescripcion);
        textFecha = (TextView) root.findViewById(R.id.textFecha);
        textHora = (TextView) root.findViewById(R.id.textHora);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //String incidenteId = getArguments().getString(ARGUMENT_INCIDENTE_ID);
        String Descripcion = getArguments().getString("Descripcion");
        String Fecha = getArguments().getString("Fecha");
        String Hora = getArguments().getString("Hora");
        String Tipo_incidente = getArguments().getString("IncidenteName");
        String Tipo_incidente_img = getArguments().getString("TipoIncidenteImg");
        
        textIncidenteName.setText(Tipo_incidente);
        textDescripcion.setText("Descripción: " + Descripcion);
        textFecha.setText("Fecha: " + Fecha);
        textHora.setText("Hora " + Hora);
        showImage(Tipo_incidente_img);
        //mActionsListener.openIncidente(incidenteId);
    }

//    @Override
//    public void setProgressIndicator(boolean active) {
//        if (active) {
//            mDetailTitle.setText("");
//            mDetailDescription.setText(getString(R.string.loading));
//        }
//    }
//
//    @Override
//    public void hideDescription() {
//        mDetailDescription.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void hideTitle() {
//        mDetailTitle.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showDescription(String description) {
//        mDetailDescription.setVisibility(View.VISIBLE);
//        mDetailDescription.setText(description);
//    }
//
//    @Override
//    public void showTitle(String title) {
//        mDetailTitle.setVisibility(View.VISIBLE);
//        mDetailTitle.setText(title);
//    }

    //@Override
    public void showImage(String imageUrl) {
        // The image is loaded in a different thread so in order to UI-test this, an idling resource
        // is used to specify when the app is idle.
        //EspressoIdlingResource.increment(); // App is busy until further notice.

        ivTipoIncidente.setVisibility(View.VISIBLE);

        // This app uses Glide for image loading
//        Glide.with(this)
//                .load(imageUrl)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .into(new GlideDrawableImageViewTarget(ivTipoIncidente) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource,
//                                                GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        //EspressoIdlingResource.decrement(); // App is idle.
//                    }
//                });

        // load image into imageview using glide
        Glide.with(getContext()).load("http://ecandlemobile.com/Test/images/" + imageUrl)
                .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(ivTipoIncidente);
    }
//
//    @Override
//    public void hideImage() {
//        mDetailImage.setImageDrawable(null);
//        mDetailImage.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void showMissingIncidente() {
//        mDetailTitle.setText("");
//        mDetailDescription.setText(getString(R.string.no_data));
//    }
}
