package com.jasrsir.xmljasr;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jasrsir.xmljasr.clases.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class Empleados_Activity extends AppCompatActivity {

    private TextView salMin;
    private TextView salMax;
    private TextView edadMed;
    private TextView empleados;
    private TextView salMedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);
        init();
    }


    public void onClickEmpleado(View v) {
        try {
            actualizar(Analisis.analizarEmpleados(getResources().getXml(R.xml.empleados)));
        } catch (XmlPullParserException e) {
            Snackbar.make(findViewById(R.id.cardEmpleados),"Error : " + e.getMessage(),Snackbar.LENGTH_LONG).show();
        } catch (IOException e) {
            Snackbar.make(findViewById(R.id.cardEmpleados),"Error : " + e.getMessage(),Snackbar.LENGTH_LONG).show();
        }
    }

    private void init() {
        salMax = (TextView) findViewById(R.id.txvSalarioMax);
        salMedio = (TextView) findViewById(R.id.txvSalarioMedio);
        salMin =(TextView) findViewById(R.id.txvSalarioMin);
        edadMed = (TextView) findViewById(R.id.txvEdadMedia);
        empleados =  (TextView) findViewById(R.id.txvListaEmpleados);


    }
    private void actualizar(String[] recursosEmpleados) {
        empleados.setText(recursosEmpleados[0]);
        //0=nombres, 1=Edadmedia,2="salMini",3=salMax, 4=salMed
        edadMed.setText(edadMed.getText() + recursosEmpleados[1] + " años");
        salMin.setText(salMin.getText() + recursosEmpleados[2]+ " €");
        salMax.setText(salMax.getText() + recursosEmpleados[3]+ " €");
        salMedio.setText(salMedio.getText() + String.format("%.2f", Double.parseDouble(recursosEmpleados[4])) + " €");
    }
}
