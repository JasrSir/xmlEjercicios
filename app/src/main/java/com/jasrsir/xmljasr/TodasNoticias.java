package com.jasrsir.xmljasr;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.jasrsir.xmljasr.clases.AdapterNoticias;
import com.jasrsir.xmljasr.clases.Analisis;
import com.jasrsir.xmljasr.clases.Noticia;
import com.jasrsir.xmljasr.clases.RestClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TodasNoticias extends ListActivity {
    private ProgressDialog progreso;
    private AdapterNoticias adapterNoticiasEnteras;
    private ArrayList<Noticia> noticiasLink = new ArrayList<>();
    public static String NOTICIARIO;
    public static final String  FICHERONOTICIA = "noticias.xml";
    private TextView titular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_noticias);
        titular = (TextView) findViewById(R.id.txvTitularNoticia);
        NOTICIARIO = getIntent().getExtras().getString("url");
        titular.setText(titular.getText() + " "+ getIntent().getExtras().getString("title"));
        descargar(NOTICIARIO,FICHERONOTICIA);
        initAdapter();
    }

    private void initAdapter() {
        getListView().setDividerHeight(10);
        adapterNoticiasEnteras = new AdapterNoticias(this,noticiasLink);
        getListView().setAdapter(adapterNoticiasEnteras);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((Noticia) adapterView.getItemAtPosition(i)).getUri()));
                startActivity(browserIntent);
            }
        });
    }



    private void descargar(String uriNoticias, String ficheroNoticias) {
        progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), ficheroNoticias);
        try {
            miFichero.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestClient.get(uriNoticias, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(TodasNoticias.this,"Fallo:"+statusCode+"\n"+throwable.getMessage(),Toast.LENGTH_SHORT);
                progreso.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    //informacion.setText(Analisis.analizarRSS(file));
                    noticiasLink = Analisis.analizarNoticias(file);
                    getListView().setDividerHeight(10);
                    adapterNoticiasEnteras = new AdapterNoticias(TodasNoticias.this,noticiasLink);
                    getListView().setAdapter(adapterNoticiasEnteras);
                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((Noticia) adapterView.getItemAtPosition(i)).getUri()));
                            startActivity(browserIntent);
                        }
                    });
                    progreso.dismiss();
                } catch (XmlPullParserException e) {
                    Toast.makeText(TodasNoticias.this,"Excepción XML:"+e.getLineNumber(),Toast.LENGTH_LONG);
                    progreso.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando y analizando las noticias  . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

        });
    }

}
