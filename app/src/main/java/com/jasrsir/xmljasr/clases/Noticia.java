package com.jasrsir.xmljasr.clases;

/**
 * Created by jasrsir on 8/12/16.
 */

public class Noticia {

    private String title;
    private String uri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.startsWith("<![CDATA["))
            this.title = title.substring("<![CDATA[".length(),(title.length()-"]]>".length()));
        else
            this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {

        if (uri.startsWith("<![CDATA["))
            this.uri = uri.substring("<![CDATA[".length(),(uri.length()-"]]>".length()));
        else
            this.uri = uri;
    }


    public Noticia(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public Noticia() {
        title = null;
        uri = null;
    }
}
