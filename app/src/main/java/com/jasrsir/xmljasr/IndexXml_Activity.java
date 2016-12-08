package com.jasrsir.xmljasr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class IndexXml_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_xml);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnXmlEmpleado:
                startActivity(new Intent(IndexXml_Activity.this, Empleados_Activity.class));
                break;
            case R.id.btntiempo:
                startActivity(new Intent(IndexXml_Activity.this, Weather_Activity.class));
                break;
            case R.id.btnBicis:
                startActivity(new Intent(IndexXml_Activity.this,Bizis_Activity.class));
                break;
            case R.id.btnNoticias:
                startActivity(new Intent(IndexXml_Activity.this,NoticiasRSS_Activity.class));
                break;
        }
    }
}
