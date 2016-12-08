package com.jasrsir.xmljasr.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by jasrsir on 19/11/16.
 */

public class Streaming {

    //Errores
    public static final int OK = 0;
    public static final int NOARCHIVO = 1;
    public static final int IOERROR = 2;
    public static final int EXISTS = 4;


    private String ruta;

    //Getters and Setters
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String path) {
        this.ruta = path;
    }

    //Constuctor
    public Streaming(String path) {
        this.ruta = path;
    }


    public int newArchivo(String fileName){

        File archivo = new File(ruta, fileName);
        try {
            if(archivo.createNewFile())
                return OK;
            else return EXISTS;

        } catch (IOException e) {
            return IOERROR;
        }
    }


    public int escribeArchivo(String fileName, String text, boolean append){

        File file = new File(ruta, fileName);
        int resultado = OK;
        BufferedWriter bufWriter = null;


        try {
            bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
            bufWriter.write(text);
            resultado = OK;
        } catch (FileNotFoundException e) {
            resultado = NOARCHIVO;
        } catch (IOException e) {
            resultado = IOERROR;
        }finally {

            if(bufWriter != null){

                try {
                    bufWriter.close();
                } catch (IOException e) {

                }
            }
            return resultado;
        }
    }


    public int leerArchivo(String fileName, String[] buffer){

        File file;
        String linea = null;
        int resultado = 0;
        BufferedReader bufReader = null;


        file = new File(ruta, fileName);
        linea = "";
        try {
            bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            for (int i = 0; i < buffer.length;i++){
                linea = bufReader.readLine();

                if(linea != null)
                    buffer[i] = linea;
            }
            resultado = OK;
        } catch (FileNotFoundException e) {
            resultado = NOARCHIVO;
        } catch (IOException e) {
            resultado = IOERROR;
        }finally {
            if(bufReader != null)
                try {
                    bufReader.close();
                } catch (IOException e) {

                }
            return resultado;
        }
    }

    public boolean existeArchivo(String fileName){

        File file = new File(ruta, fileName);
        return file.exists();
    }

}
