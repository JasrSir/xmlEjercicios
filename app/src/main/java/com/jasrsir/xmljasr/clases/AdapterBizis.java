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

import java.util.List;

/**
 * Created by jasrsir on 7/12/16.
 */

public class AdapterBizis extends ArrayAdapter<Bizi> {

    private Context mcontext;

    //Class to content a view
    class CardViewHolder {
        TextView mTxvNombreEstacion;

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

            cardViewHolder.mTxvNombreEstacion = (TextView) item.findViewById(R.id.txvNombreEstacion);
            item.setTag(cardViewHolder);
        } else
            cardViewHolder = (CardViewHolder)item.getTag();
        // Asignamos los datos del adapter a los widgets
        cardViewHolder.mTxvNombreEstacion.setText(getItem(position).getTitle());
        return item;
    }

    public AdapterBizis(Context context, List<Bizi> estaciones) {
        super(context, R.layout.card_bizis_estacion, estaciones);
        this.mcontext = context;
    }

}

