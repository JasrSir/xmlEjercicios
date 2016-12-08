package com.jasrsir.xmljasr.clases;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Xml;
import android.widget.ImageView;

import com.jasrsir.xmljasr.R;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.WeakHashMap;

/**
 * Created by usuario on 21/11/16.
 */

public class Analisis {
    static Context context;
    public Analisis(Context context){
        this.context = context;
    }

  public static String[] analizarEmpleados(XmlResourceParser xmlEmpleados) throws XmlPullParserException, IOException {

      String[] analisisEmpleados = {"","","","",""};//0=nombres, 1=Edadmedia,2="salMini",3=salMax, 4=salMed
      Double edadmedia = 0.0;
      String empleados = "";
      double sueldoMin = 9999.00;
      double sueldoMax = 0.00;
      double sueldoMedio = 0.0;
      int contaEmpleados = 0;
      boolean esEmpleado = false;
      int eventType = xmlEmpleados.getEventType();

      while (eventType != XmlPullParser. END_DOCUMENT ) {

          switch (eventType) {

              case XmlPullParser.START_TAG :
                  if (xmlEmpleados.getName().equals("empleado")) {
                      esEmpleado = true;
                      contaEmpleados++;
                  }

                  if (xmlEmpleados.getName().equals("nombre") && esEmpleado) {
                      empleados += xmlEmpleados.nextText()+" , ";
                  }
                  if (xmlEmpleados.getName().equals("puesto") && esEmpleado) {
                      empleados += xmlEmpleados.nextText()+"\n";
                  }
                  if (xmlEmpleados.getName().equals("edad") && esEmpleado) {
                      edadmedia += Double.parseDouble(xmlEmpleados.nextText());
                  }
                  if (xmlEmpleados.getName().equals("sueldo") && esEmpleado) {
                      double sueldo = Double.parseDouble(xmlEmpleados.nextText());
                      if (sueldoMax < sueldo)
                          sueldoMax = sueldo;
                      else if (sueldoMin > sueldo)
                          sueldoMin = sueldo;

                      sueldoMedio += sueldo;
                  }


                  break;
              case XmlPullParser.END_TAG :
                  if (xmlEmpleados.getName().equals("empleado"))
                      esEmpleado = false;

                  break;
          }
          eventType = xmlEmpleados.next();
      }
      analisisEmpleados[0] = empleados;
      analisisEmpleados[1] = String.valueOf((edadmedia/contaEmpleados));
      analisisEmpleados[2] = String.valueOf((sueldoMin));
      analisisEmpleados[3] = String.valueOf((sueldoMax));
      analisisEmpleados[4] = String.valueOf((sueldoMedio/contaEmpleados));
      return analisisEmpleados;
  }


    /**

    public static String analizarRSS(File file) throws NullPointerException, XmlPullParserException, IOException
    {
        boolean dentroItem = false;
        boolean dentroTitle = false;

        StringBuilder builder = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item"))
                        dentroItem = true;
                    if (xpp.getName().equals("title")&&dentroItem)
                       // dentroTitle = true;
                        builder.append("El titulo es ->"+xpp.nextText()+"\n");
                    break;
               *//* case XmlPullParser.TEXT:
                    builder.append(xpp.getText()+"\n");
                    break;*//*
                case XmlPullParser.END_TAG:
                   *//* if (xpp.getName().equals("item"))
                        dentroItem = false;*//*
                    if (xpp.getName().equals("title")&&dentroItem)
                        dentroTitle = false;
                    break;
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }*/
    public static ArrayList<Weather> analizarTiempo(File file) throws XmlPullParserException, IOException {
        int eventType;

        ArrayList<Weather> todayTomorrow =  new ArrayList<>();
        Weather[] hoyT = null;
        Weather[] manianaT = null;
        boolean dentrohoy = false;
        boolean dentromaniana = false;
        boolean dentroTemp = false;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();
        int dias = 0;
        int hora = Calendar.getInstance().getTime().getHours();

        while (eventType!=XmlPullParser.END_DOCUMENT){

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    hoyT = new Weather[4];
                    hoyT[0] = new Weather();
                    hoyT[1] = new Weather();
                    hoyT[2] = new Weather();
                    hoyT[3] = new Weather();
                    manianaT = new Weather[4];
                    manianaT[0] = new Weather();
                    manianaT[1] = new Weather();
                    manianaT[2] = new Weather();
                    manianaT[3] = new Weather();
                    break;
                
                case XmlPullParser.START_TAG:
                    //region DIA DE HOY
                    if (xpp.getName().equals("dia") && dias == 0){
                        dentrohoy = true;
                        break;
                    }

                    //1º intervalo
                    if (hora > 0 && hora<= 6 && dentrohoy) {
                        //precipitaciones
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "00-06" ) {
                            hoyT[0].setPrecipiacion(xpp.nextText());
                            hoyT[0].setPeriodo("00-06");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "06-12" ) {
                            hoyT[1].setPrecipiacion(xpp.nextText());
                            hoyT[1].setPeriodo("06-12");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setPrecipiacion(xpp.nextText());
                            hoyT[2].setPeriodo("12-18");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setPrecipiacion(xpp.nextText());
                            hoyT[3].setPeriodo("18-24");
                        }
                        //estado del cielo
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "00-06" ) {
                            hoyT[0].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[0].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "06-12" ) {
                            hoyT[1].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[1].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[2].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[3].setImagen(xpp.nextText());
                        }
                        //Temperaturas
                        if (xpp.getName().equals("temperatura")&& dentrohoy ) {
                            dentroTemp = true;
                        }

                        if (xpp.getName().equals("maxima")&& dentrohoy && dentroTemp) {
                            hoyT[0].setTempMax(xpp.nextText());
                            hoyT[1].setTempMax(xpp.nextText());
                            hoyT[2].setTempMax(xpp.nextText());
                            hoyT[3].setTempMax(xpp.nextText());
                        }
                        if (xpp.getName().equals("minima")&& dentrohoy && dentroTemp) {
                            hoyT[0].setTempMin(xpp.nextText());
                            hoyT[1].setTempMin(xpp.nextText());
                            hoyT[2].setTempMin(xpp.nextText());
                            hoyT[3].setTempMin(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "06") {
                            hoyT[0].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "12") {
                            hoyT[1].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "18") {
                            hoyT[2].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "24") {
                            hoyT[3].setTemp(xpp.nextText());
                        }
                        
                    }
                    //2º intervalo
                    if (hora > 6 && hora <= 12 && dentrohoy) {
                        hoyT[0] = null;
                        //precipitaciones
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "06-12" ) {
                            hoyT[1].setPrecipiacion(xpp.nextText());
                            hoyT[1].setPeriodo("06-12");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setPrecipiacion(xpp.nextText());
                            hoyT[2].setPeriodo("12-18");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setPrecipiacion(xpp.nextText());
                            hoyT[3].setPeriodo("18-24");
                        }
                        //estado del cielo
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "06-12" ) {
                            hoyT[1].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[1].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[2].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[3].setImagen(xpp.nextText());
                        }
                        //temperaturas
                        if (xpp.getName().equals("maxima")&& dentrohoy && dentroTemp) {
                            hoyT[1].setTempMax(xpp.nextText());
                            hoyT[2].setTempMax(xpp.nextText());
                            hoyT[3].setTempMax(xpp.nextText());
                        }
                        if (xpp.getName().equals("minima")&& dentrohoy && dentroTemp) {
                            hoyT[1].setTempMin(xpp.nextText());
                            hoyT[2].setTempMin(xpp.nextText());
                            hoyT[3].setTempMin(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "12") {
                            hoyT[1].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "18") {
                            hoyT[2].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "24") {
                            hoyT[3].setTemp(xpp.nextText());
                        }
                    }
                    //3º intervalo
                    if (hora > 12 && hora <= 18 && dentrohoy) {
                        hoyT[0] = null;
                        hoyT[1] = null;
                        //precipitaciones
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setPrecipiacion(xpp.nextText());
                            hoyT[2].setPeriodo("12-18");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setPrecipiacion(xpp.nextText());
                            hoyT[3].setPeriodo("18-24");
                        }
                        //estado del cielo
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "12-18" ) {
                            hoyT[2].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[2].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentrohoy && xpp.getAttributeValue(0) == "18-24" ) {
                            hoyT[3].setEstadoCielo(xpp.getAttributeValue(1));
                            hoyT[3].setImagen(xpp.nextText());
                        }
                        //temperaturas
                        if (xpp.getName().equals("maxima")&& dentrohoy && dentroTemp) {
                            hoyT[2].setTempMax(xpp.nextText());
                            hoyT[3].setTempMax(xpp.nextText());
                        }
                        if (xpp.getName().equals("minima")&& dentrohoy && dentroTemp) {
                            hoyT[2].setTempMin(xpp.nextText());
                            hoyT[3].setTempMin(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "18") {
                            hoyT[2].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "24") {
                            hoyT[3].setTemp(xpp.nextText());
                        }
                    }
                    //4º intervalo
                    if (hora > 18 && hora <= 24 && dentrohoy) {
                        hoyT[0] = null;
                        hoyT[1] = null;
                        hoyT[2] = null;
                        //precipitaciones
                        if (xpp.getName().equals("prob_precipitacion") && dentrohoy && xpp.getAttributeValue(0) == "18-24") {
                            hoyT[3].setPrecipiacion(xpp.nextText());
                            hoyT[3].setPeriodo("18-24");
                        }
                        //Estado cielo
                        if (xpp.getName().equals("estado_cielo") && dentrohoy && xpp.getAttributeValue(0) == "18-24") {
                            hoyT[3].setEstadoCielo(xpp.getAttributeValue(1));
                           hoyT[3].setImagen(xpp.nextText());
                        }
                        //temperaturas
                        if (xpp.getName().equals("maxima")&& dentrohoy && dentroTemp) {
                            hoyT[3].setTempMax(xpp.nextText());
                        }
                        if (xpp.getName().equals("minima")&& dentrohoy && dentroTemp) {
                            hoyT[3].setTempMin(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentrohoy && dentroTemp && xpp.getAttributeValue(0) == "24") {
                            hoyT[3].setTemp(xpp.nextText());
                        }
                    }
                    
                //endregion
                    //region MAÑANA
                    if (dias == 1 && dentromaniana){
                        //precipitaciones
                        if (xpp.getName().equals("prob_precipitacion")&& dentromaniana && xpp.getAttributeValue(0) == "00-06" ) {
                            manianaT[0].setPrecipiacion(xpp.nextText());
                            manianaT[0].setPeriodo("00-06");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentromaniana && xpp.getAttributeValue(0) == "06-12" ) {
                            manianaT[1].setPrecipiacion(xpp.nextText());
                            manianaT[1].setPeriodo("06-12");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentromaniana && xpp.getAttributeValue(0) == "12-18" ) {
                            manianaT[2].setPrecipiacion(xpp.nextText());
                            manianaT[2].setPeriodo("12-18");
                        }
                        if (xpp.getName().equals("prob_precipitacion")&& dentromaniana && xpp.getAttributeValue(0) == "18-24" ) {
                            manianaT[3].setPrecipiacion(xpp.nextText());
                            manianaT[3].setPeriodo("18-24");
                        }
                        //estado del cielo
                        if (xpp.getName().equals("estado_cielo")&& dentromaniana && xpp.getAttributeValue(0) == "00-06" ) {
                            manianaT[0].setEstadoCielo(xpp.getAttributeValue(1));
                            manianaT[0].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentromaniana && xpp.getAttributeValue(0) == "06-12" ) {
                            manianaT[1].setEstadoCielo(xpp.getAttributeValue(1));
                            manianaT[1].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentromaniana && xpp.getAttributeValue(0) == "12-18" ) {
                            manianaT[2].setEstadoCielo(xpp.getAttributeValue(1));
                            manianaT[2].setImagen(xpp.nextText());
                        }
                        if (xpp.getName().equals("estado_cielo")&& dentromaniana && xpp.getAttributeValue(0) == "18-24" ) {
                            manianaT[3].setEstadoCielo(xpp.getAttributeValue(1));
                            manianaT[3].setImagen(xpp.nextText());
                        }
                        //Temperaturas
                        if (xpp.getName().equals("temperatura")&& dentromaniana ) {
                            dentroTemp = true;
                        }

                        if (xpp.getName().equals("maxima")&& dentromaniana && dentroTemp) {
                            manianaT[0].setTempMax(xpp.nextText());
                            manianaT[1].setTempMax(xpp.nextText());
                            manianaT[2].setTempMax(xpp.nextText());
                            manianaT[3].setTempMax(xpp.nextText());
                        }
                        if (xpp.getName().equals("minima")&& dentromaniana && dentroTemp) {
                            manianaT[0].setTempMin(xpp.nextText());
                            manianaT[1].setTempMin(xpp.nextText());
                            manianaT[2].setTempMin(xpp.nextText());
                            manianaT[3].setTempMin(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentromaniana && dentroTemp && xpp.getAttributeValue(0) == "06") {
                            manianaT[0].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentromaniana && dentroTemp && xpp.getAttributeValue(0) == "12") {
                            manianaT[1].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentromaniana && dentroTemp && xpp.getAttributeValue(0) == "18") {
                            manianaT[2].setTemp(xpp.nextText());
                        }
                        if (xpp.getName().equals("dato")&& dentromaniana && dentroTemp && xpp.getAttributeValue(0) == "24") {
                            manianaT[3].setTemp(xpp.nextText());
                        }

                    }
                    //endregion
                    break;
                    
                
                //"http://www.aemet.es/imagenes/png/estado_cielo/"+imagen +"_g.png"

                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("dia")) {
                        if (dias == 0) {
                            dentrohoy = false;
                            for (int i = 0; i < hoyT.length; i++) {
                                todayTomorrow.add(hoyT[i]);
                            }
                            dentromaniana = true;
                        } else if (dias==1){
                            dentromaniana = false;
                            for (int i = 0; i < manianaT.length; i++) {
                                todayTomorrow.add(manianaT[i]);
                            }
                        }
                            
                        
                        dias++;
                    }
                    break;
            }
            if (dias != 2)
                eventType = xpp.next();
            else
                break;
        }
        //devolver el array de noticias
        return todayTomorrow;
    }
    /*public static void crearXML(ArrayList<Noticia> noticias, String fichero) throws IOException {
        FileOutputStream fout;
        fout = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero));
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(fout, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true); //poner tabulación
        serializer.startTag(null, "titulares");
        for (int i = 0; i < noticias.size(); i++) {

            serializer.startTag(null,"item");
            serializer.startTag(null,"titulo");
            serializer.attribute(null,"fecha",noticias.get(i).getPubDate().toString());
            serializer.text(noticias.get(i).getTitle().toString());
            serializer.endTag(null,"titulo");
            serializer.startTag(null,"enlace");
            serializer.text(noticias.get(i).getLink().toString());
            serializer.endTag(null,"enlace");
            serializer.startTag(null,"descripcion");
            serializer.text(noticias.get(i).getDescription().toString());
            serializer.endTag(null,"descripcion");
            serializer.endTag(null,"item");
        }
        serializer.endTag(null, "titulares");
        serializer.endDocument();
        serializer.flush();
        fout.close();
    }*/
}
