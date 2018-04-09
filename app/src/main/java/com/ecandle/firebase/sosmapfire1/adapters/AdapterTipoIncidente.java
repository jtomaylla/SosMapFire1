package com.ecandle.firebase.sosmapfire1.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecandle.firebase.sosmapfire1.R;


/**
 * Created by juantomaylla on 20/01/17.
 */

public class AdapterTipoIncidente extends CursorAdapter {

    public AdapterTipoIncidente(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_tipo_incidente, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvNombre = (TextView) view.findViewById(R.id.tvNombre);
        TextView tvImagen = (TextView) view.findViewById(R.id.tvImagen);
        ImageView ivTipoIncidenteImg = (ImageView) view.findViewById(R.id.ivTipoIncidenteImg);
        // Extract properties from cursor
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
        String imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"));
        // Populate fields with extracted properties
        Glide.with(context).load("http://ecandlemobile.com/Test/images/" + imagen)
                .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(ivTipoIncidenteImg);
        tvNombre.setText(nombre);
        tvImagen.setText(imagen);
    }
}
