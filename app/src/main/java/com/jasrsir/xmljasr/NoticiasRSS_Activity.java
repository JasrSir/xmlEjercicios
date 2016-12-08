package com.jasrsir.xmljasr;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.jasrsir.xmljasr.clases.AdapterNoticias;
import com.jasrsir.xmljasr.clases.Noticia;

import java.util.ArrayList;
import java.util.List;

public class NoticiasRSS_Activity extends ListActivity {

    private AdapterNoticias adapterNoticias;
    private List<Noticia> sitiosWeb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_rss);

        init();
    }

    private void init() {
        createSitios();
        getListView().setDividerHeight(10);
        adapterNoticias = new AdapterNoticias(this,sitiosWeb);
        getListView().setAdapter(adapterNoticias);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NoticiasRSS_Activity.this, TodasNoticias.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",((Noticia)adapterView.getItemAtPosition(i)).getUri());
                bundle.putString("title",((Noticia)adapterView.getItemAtPosition(i)).getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void createSitios() {
        sitiosWeb.add(new Noticia("El Pais", "http://ep00.epimg.net/rss/elpais/portada.xml"));
        sitiosWeb.add(new Noticia("El Mundo", "http://estaticos.elmundo.es/elmundo/rss/espana.xml"));
        sitiosWeb.add(new Noticia("Linux Magazine", "http://www.linux-magazine.com/rss/feed/lmi_news"));
        sitiosWeb.add(new Noticia("PcWorld", "http://www.pcworld.com/index.rss"));
        sitiosWeb.add(new Noticia("ABC.es", "http://www.abc.es/rss/feeds/abc_ultima.xml"));
        sitiosWeb.add(new Noticia("Antena 3", "http://www.antena3.com/rss/348723.xml"));
        sitiosWeb.add(new Noticia("ElPeriodico: Ciencia", "http://www.elperiodico.com/es/rss/ciencia/rss.xml"));
    }
}
