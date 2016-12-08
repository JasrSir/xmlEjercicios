package com.jasrsir.xmljasr;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.tv.TvView;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jasrsir.xmljasr.clases.AdapterBizis;
import com.jasrsir.xmljasr.clases.Analisis;
import com.jasrsir.xmljasr.clases.Bizi;
import com.jasrsir.xmljasr.clases.RestClient;
import com.jasrsir.xmljasr.clases.Weather;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Bizis_Activity extends ListActivity {
    static ProgressDialog progreso;
    public static final String ZARABIZI = "http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.xml";
    public static final String FICHEROBIZI = "bizis.xml";

    ArrayList<Bizi> arrayEstaciones;
    ArrayAdapter<Bizi> adapterBizi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bizis);
        init();
    }

    private void init() {
        arrayEstaciones = new ArrayList<>();
        adapterBizi = new AdapterBizis(this,arrayEstaciones);
        progreso = new ProgressDialog(this);
        descargar(ZARABIZI,FICHEROBIZI);
    }

    private void descargar(String zarabizi, String ficherobizi) {
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ficherobizi);
        try {
            miFichero.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestClient.get(zarabizi, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(Bizis_Activity.this,"Fallo:"+statusCode+"\n"+throwable.getMessage(),Toast.LENGTH_SHORT);
                progreso.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    //informacion.setText(Analisis.analizarRSS(file));
                    arrayEstaciones = Analisis.analizarBizis(file);

                    mostrar(arrayEstaciones);
                    progreso.dismiss();
                } catch (XmlPullParserException e) {
                    Toast.makeText(Bizis_Activity.this,"Excepción XML:"+e.getLineNumber(),Toast.LENGTH_LONG);
                    progreso.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando y analizando las bicis  . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

        });
    }

    private void mostrar(ArrayList<Bizi> estaciones) {

        if (estaciones != null) {
            adapterBizi = new AdapterBizis(this, estaciones);
            getListView().setDividerHeight(20);
            getListView().setAdapter(adapterBizi);
        } else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Bizis_Activity.this, EstacionSeleccionada.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",((Bizi)adapterView.getItemAtPosition(i)).getTitle());
                bundle.putString("id",((Bizi)adapterView.getItemAtPosition(i)).getId());
                bundle.putString("bicisD",((Bizi)adapterView.getItemAtPosition(i)).getBicisDisponibles());
                bundle.putString("anclajeD",((Bizi)adapterView.getItemAtPosition(i)).getAnclajesDisponibles());
                bundle.putString("estado",((Bizi)adapterView.getItemAtPosition(i)).getEstado());
                bundle.putString("lastU",((Bizi)adapterView.getItemAtPosition(i)).getLastUpdate());
                bundle.putString("uri",((Bizi)adapterView.getItemAtPosition(i)).getUri());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}
