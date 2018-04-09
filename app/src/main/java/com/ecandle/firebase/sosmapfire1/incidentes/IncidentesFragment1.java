/*
 * Copyright 2015, The Android Open Source Project
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

package com.ecandle.firebase.sosmapfire1.incidentes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecandle.firebase.sosmapfire1.R;
import com.ecandle.firebase.sosmapfire1.activities.MapaActivity;
import com.ecandle.firebase.sosmapfire1.addincidente.AddIncidenteActivity;
import com.ecandle.firebase.sosmapfire1.editincidente.EditIncidenteActivity;
import com.ecandle.firebase.sosmapfire1.models.DataIncidente;
import com.ecandle.firebase.sosmapfire1.models.Incidente;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.ecandle.firebase.sosmapfire1.R.id.fab;

/**
 * Display a grid of {@link DataIncidente}s
 */
public class IncidentesFragment1 extends Fragment {

    private String LOG_TAG = IncidentesFragment1.class.getSimpleName();
    public static final String INCIDENTES_CHILD = "incidente";
    private View root;
    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<DataIncidente, IncidenteViewHolder>
            mFirebaseAdapter;

    private ProgressBar mProgressBar;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private FloatingActionButton mFab;

    public IncidentesFragment1() {
        // Requires empty public constructor
    }

    public static IncidentesFragment1 newInstance() {
        return new IncidentesFragment1();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_incidentes, container, false);

        mMessageRecyclerView = (RecyclerView) root.findViewById(R.id.ListaIncindencias);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(false);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        //

        //mMessageRecyclerView.setHasFixedSize(true);

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<DataIncidente,
                IncidenteViewHolder>(
                DataIncidente.class,
                R.layout.fragment_detail1,
                IncidenteViewHolder.class,
                mFirebaseDatabaseReference.child(INCIDENTES_CHILD)) {

            @Override
            protected DataIncidente parseSnapshot(DataSnapshot snapshot) {
                DataIncidente dataIncidente = super.parseSnapshot(snapshot);
                if (dataIncidente != null) {
                    dataIncidente.setIncidente_id(snapshot.getKey());
                }
                return dataIncidente;
            }
            @Override
            protected void populateViewHolder(final IncidenteViewHolder viewHolder,
                                              final DataIncidente dataIncidente, int position) {
                //mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                if (dataIncidente.getDescripcion_incidente() != null) {
                    viewHolder.textIncidenteName.setText(dataIncidente.tipo_incidente);
                    viewHolder.textDescripcion.setText("Descripción: " + dataIncidente.descripcion_incidente);
                    viewHolder.textFecha.setText("Fecha: " + dataIncidente.fecha_incidente);
                    viewHolder.textHora.setText("Hora " + dataIncidente.hora_incidente);
                    //viewHolder.textHora.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                    //viewHolder.ivTipoIncidente.setVisibility(ImageView.GONE);
                    Glide.with(viewHolder.ivTipoIncidente.getContext())
                            .load(dataIncidente.tipo_incidente_img)
                            .into(viewHolder.ivTipoIncidente);

//                    viewHolder.ivTipoIncidente.setImageDrawable(ContextCompat.getDrawable(getContext(),
//                            R.drawable.ic_account_circle_black_36dp));
                    viewHolder.ivEditIncidente.setImageDrawable(ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_edit_black_24dp));
                    viewHolder.ivMapIncidente.setImageDrawable(ContextCompat.getDrawable(getContext(),
                            R.drawable.ic_location_on_black_24dp));
                } else {
                    String imageUrl = dataIncidente.getTipo_incidente_img();
                    if (imageUrl.startsWith("gs://")) {
                        StorageReference storageReference = FirebaseStorage.getInstance()
                                .getReferenceFromUrl(imageUrl);
                        storageReference.getDownloadUrl().addOnCompleteListener(
                                new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()) {
                                            String downloadUrl = task.getResult().toString();
                                            Glide.with(viewHolder.ivTipoIncidente.getContext())
                                                    .load(downloadUrl)
                                                    .into(viewHolder.ivTipoIncidente);
                                        } else {
                                            Log.w(LOG_TAG, "Getting download url was not successful.",
                                                    task.getException());
                                        }
                                    }
                                });
                    } else {
                        Glide.with(viewHolder.ivTipoIncidente.getContext())
                                .load(dataIncidente.getTipo_incidente_img())
                                .into(viewHolder.ivTipoIncidente);
                    }
                    viewHolder.ivTipoIncidente.setVisibility(ImageView.VISIBLE);
                    viewHolder.ivTipoIncidente.setVisibility(TextView.GONE);
                }

                viewHolder.ivMapIncidente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.i(LOG_TAG, "ivMapIncidente:Clicked");
                        //Toast.makeText(v.getContext(), "Clicked " + "mImageInfo Clicked", Toast.LENGTH_SHORT).show();
                        //mSubTitulo.setVisibility(View.GONE);

                        Bundle extras = new Bundle();
                        Intent myIntent = new Intent(v.getContext(), MapaActivity.class);

                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        extras.putString("lat", dataIncidente.getLatitud());
                        extras.putString("lon", dataIncidente.getLongitud());
                        myIntent.putExtras(extras);
                        v.getContext().startActivity(myIntent);


                    }
                });

                viewHolder.ivEditIncidente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Log.i(LOG_TAG, "ivMapIncidente:Clicked");
                        //Toast.makeText(v.getContext(), "Clicked " + "mImageInfo Clicked", Toast.LENGTH_SHORT).show();
                        //mSubTitulo.setVisibility(View.GONE);

                        Bundle extras = new Bundle();
                        Intent myIntent = new Intent(v.getContext(), EditIncidenteActivity.class);

                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        extras.putString("incidente_id", dataIncidente.getIncidente_id());
                        //extras.putString("lon", dataIncidente.getLongitud());
                        myIntent.putExtras(extras);
                        v.getContext().startActivity(myIntent);


                    }
                });
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int DataIncidenteCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (DataIncidenteCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        // Set up floating action button
        mFab = (FloatingActionButton) getActivity().findViewById(fab);

        mFab.setImageResource(R.drawable.ic_add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddIncidenteActivity.class);
                startActivity(i);

            }
        });
        return root;
    }
    /**
     * Listener for clicks on incidentes in the RecyclerView.
     */
    IncidenteItemListener mItemListener = new IncidenteItemListener() {
        @Override
        public void onIncidenteClick(Incidente clickedIncidente) {
            //mActionsListener.openIncidenteDetails(clickedIncidente);
        }
    };

    public interface IncidenteItemListener {

        void onIncidenteClick(Incidente clickedIncidente);
    }
    public static class IncidenteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textIncidenteName;
        public ImageView ivTipoIncidente;
        public TextView textDescripcion;
        public TextView textFecha;
        public TextView textHora;
        public ImageView ivEditIncidente;
        public ImageView ivMapIncidente;

        // create constructor to get widget reference
        public IncidenteViewHolder(View itemView) {
            super(itemView);
            textIncidenteName = (TextView) itemView.findViewById(R.id.textIncidenteName);
            ivTipoIncidente = (ImageView) itemView.findViewById(R.id.ivTipoIncidente);
            textDescripcion = (TextView) itemView.findViewById(R.id.textDescripcion);
            textFecha = (TextView) itemView.findViewById(R.id.textFecha);
            textHora = (TextView) itemView.findViewById(R.id.textHora);
            ivEditIncidente = (ImageView) itemView.findViewById(R.id.ivEditIncidente);
            ivMapIncidente = (ImageView) itemView.findViewById(R.id.ivMapIncidente);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            DataIncidente incidente = getItem(position);
//            //mItemListener.onIncidenteClick(incidente);
//            showIncidenteDetailUi(incidente,v);

        }

    }


}
