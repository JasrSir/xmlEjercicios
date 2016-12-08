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

public class AdapterWeather extends ArrayAdapter<Weather> {

    private Context mcontext;

    //Class to content a view
    class CardViewHolder {
        ImageView mImageTiempo;
        TextView mTxvTemp;
        TextView mTxvPreci;
        TextView mTxvdiaEstado;
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
            item = inflater.inflate(R.layout.card_weather, null);
            cardViewHolder = new CardViewHolder();

            // Asignamos a las vbariables los widgets mediante el metodo findViewById
            cardViewHolder.mImageTiempo = (ImageView) item.findViewById(R.id.iconoTiempo);
            cardViewHolder.mTxvTemp = (TextView) item.findViewById(R.id.txvTempyMaxMin);
            cardViewHolder.mTxvPreci = (TextView) item.findViewById(R.id.txvPrecip);
            cardViewHolder.mTxvdiaEstado = (TextView) item.findViewById(R.id.txvIntervalo);


            item.setTag(cardViewHolder);
        } else
            cardViewHolder = (CardViewHolder)item.getTag();
        // Asignamos los datos del adapter a los widgets
        cardViewHolder.mImageTiempo.setImageURI(Uri.parse(getItem(position).getImagen()));
        cardViewHolder.mTxvTemp.setText(getItem(position).getTemp() +"\n"+ getItem(position).getTempMax() +"\n"+ getItem(position).getTempMin());
        cardViewHolder.mTxvPreci.setText(getItem(position).getPrecipiacion());
        cardViewHolder.mTxvdiaEstado.setText(getItem(position).getPeriodo() + getItem(position).getEstadoCielo());
        return item;
    }

    public AdapterWeather(Context context, List<Weather> tiempo) {
        super(context, R.layout.card_weather, tiempo);
        this.mcontext = context;
    }
}

