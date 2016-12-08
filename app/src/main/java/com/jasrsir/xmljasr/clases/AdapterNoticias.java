package com.jasrsir.xmljasr.clases;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasrsir.xmljasr.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jasrsir on 8/12/16.
 */

public class AdapterNoticias extends ArrayAdapter {
    private Context mContext;

    //Class to content a view
    class CardViewHolder {
        TextView mTxvPortada;

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Variables vista y contenedor de la vista
        View item = convertView;
        CardViewHolder cardViewHolder;

        //Preguntamos si la vista es nula, si es nula ya lo inicializamos
        if (item == null) {
            //Creamos un objeto inflater que inicializamos al LayoutInflater del contexto
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Inflar la vista, crear en memoria el objeto VIew   que contiene los widgets del XML
            item = inflater.inflate(R.layout.card_bizis_estacion, null);
            cardViewHolder = new CardViewHolder();

            // Asignamos a las vbariables los widgets mediante el metodo findViewById
            cardViewHolder.mTxvPortada = (TextView) item.findViewById(R.id.txvNombreEstacion);

            item.setTag(cardViewHolder);
        } else
            cardViewHolder = (CardViewHolder) item.getTag();
        // Asignamos los datos del adapter a los widgets
        cardViewHolder.mTxvPortada.setText(((Noticia)getItem(position)).getTitle());
        return item;
    }

    public AdapterNoticias(Context context, List<Noticia> noticias) {
        super(context, R.layout.card_bizis_estacion ,noticias);
        this.mContext = context;
    }
}
