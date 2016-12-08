package com.jasrsir.xmljasr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jasrsir.xmljasr.clases.Bizi;

public class EstacionSeleccionada extends AppCompatActivity {

    private TextView txvTitleId;
    private TextView txvbicis;
    private TextView txvanclajes;
    private TextView txvUpdate;
    private TextView txvEstado;
    private String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estacion_seleccionada);
        init(getIntent().getExtras());
    }

    private void init(Bundle bundle) {

        txvTitleId = (TextView) findViewById(R.id.txvNombreEst);
        txvanclajes = (TextView) findViewById(R.id.txvAnclaDisp);
        txvbicis = (TextView) findViewById(R.id.txvBicisDisp);
        txvUpdate = (TextView) findViewById(R.id.txvLastUpdate);
        txvEstado = (TextView) findViewById(R.id.txvEstado);
        uri = bundle.getString("uri");

        txvTitleId.setText(txvTitleId.getText() + ": \n" + bundle.getString("title")+ " \n id estacion : " + bundle.getString("id"));;
        txvbicis.setText(txvbicis.getText() + bundle.getString("bicisD"));
        txvEstado.setText(txvEstado.getText() + bundle.getString("estado"));
        txvUpdate.setText(txvUpdate.getText() + bundle.getString("lastU"));
        txvanclajes.setText(txvanclajes.getText() + bundle.getString("anclajeD"));

    }

    public void onClickLlevameMapa(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);

    }
}
