package com.jasrsir.xmljasr;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jasrsir.xmljasr.clases.AdapterWeather;
import com.jasrsir.xmljasr.clases.Analisis;
import com.jasrsir.xmljasr.clases.RestClient;
import com.jasrsir.xmljasr.clases.Weather;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.R.id.list;

public class Weather_Activity extends ListActivity {
    static ProgressDialog progreso;
    public static final String TIEMPO = "http://www.aemet.es/xml/municipios/localidad_29067.xml";
    public static final String FICHEROTIEMPO = "eltiempo.xml";
    ArrayList<Weather> arrayTiempo;
    ArrayAdapter<Weather> adapterTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        progreso = new ProgressDialog(this);
    }

    public void onClickTiempo(View v) {
            descarga(TIEMPO,FICHEROTIEMPO);
    }

    private void descarga(final String tiempo, String ficheronew) {

        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ficheronew);
        try {
            miFichero.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestClient.get(tiempo, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Weather_Activity.this,"Fallo:"+statusCode+"\n"+throwable.getMessage(),Toast.LENGTH_SHORT);
                progreso.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    //informacion.setText(Analisis.analizarRSS(file));
                    arrayTiempo = Analisis.analizarTiempo(file);
                    ArrayList<Weather> tiempodefinitivo = new ArrayList<>();
                    for (int i = 0; i < arrayTiempo.size(); i++) {
                        if (arrayTiempo.get(i) != null)
                            tiempodefinitivo.add(arrayTiempo.get(i));
                    }
                    mostrar(tiempodefinitivo);
                    progreso.dismiss();
                } catch (XmlPullParserException e) {
                    Toast.makeText(Weather_Activity.this,"Excepción XML:"+e.getLineNumber(),Toast.LENGTH_LONG);
                    progreso.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando y analizando el tiempo  . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

        });
    }
    private void mostrar(ArrayList<Weather> tiempo) {
        if (tiempo != null) {
            adapterTiempo = new AdapterWeather(this, tiempo);
            getListView().setDividerHeight(20);
            getListView().setAdapter(adapterTiempo);
        } else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

}
