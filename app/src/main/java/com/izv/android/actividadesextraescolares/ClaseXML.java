package com.izv.android.actividadesextraescolares;

/**
 * Created by Ivan on 14/12/2014.
 */
import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ivan on 16/11/2014.
 */
public class ClaseXML {

    public static void nuevoArchivo(Context contexto, ArrayList<ActividadExtra> lista){
        try{
            FileOutputStream fosxml = new FileOutputStream(new File(contexto.getFilesDir(),"actividades.xml"));

            XmlSerializer docxml= Xml.newSerializer();
            docxml.setOutput(fosxml, "UTF-8");
            docxml.startDocument(null, Boolean.valueOf(true));
            docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            docxml.startTag(null, "actividades");
            for(int i = 0; i<lista.size();i++){
                //System.out.println("ESCRIBIENDO "+lista.get(i).getPrecio());
                docxml.startTag(null, "actividad");
                docxml.attribute(null, "titulo", "" + lista.get(i).getTitulo());
                docxml.attribute(null, "descripcion", lista.get(i).getDescripcion());
                docxml.attribute(null, "categoria", lista.get(i).getCategoria());
                docxml.attribute(null, "fecha", ""+lista.get(i).getFecha());
                docxml.attribute(null, "asistentes", ""+lista.get(i).getInscripciones());
                docxml.attribute(null, "asistentes", ""+lista.get(i).getDireccion());
                docxml.endTag(null, "actividad");
            }
            docxml.endTag(null, "actividades");
            docxml.endDocument();
            docxml.flush();
            fosxml.close();
        }catch(Exception e){
            System.out.println("ERROR AL ESCRIBIR "+e);
        }
    }

    public static void eliminar(Context con, ArrayList<ActividadExtra> lista , ActividadExtra ae){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(ae)){
                lista.remove(i);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }

    }

    public static void modificar(Context con, ArrayList<ActividadExtra> lista, ActividadExtra aNuevo, ActividadExtra aOld){
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).equals(aOld)){
                lista.remove(i);
                lista.add(aNuevo);
                nuevoArchivo(con, lista);
                Collections.sort(lista);
                break;
            }
        }
    }

    public static ArrayList<ActividadExtra> leer(Context contexto){
        ArrayList<ActividadExtra> lista = new ArrayList<ActividadExtra>();

        try{
            XmlPullParser lectorxml= Xml.newPullParser();
            lectorxml.setInput(new FileInputStream(new File(contexto.getFilesDir(),"actividades.xml")),"utf-8");
            int evento = lectorxml.getEventType();

            while(evento!= XmlPullParser.END_DOCUMENT){
                if(evento== XmlPullParser.START_TAG){
                    String etiqueta = lectorxml.getName();
                    if(etiqueta.compareTo("actividad")==0){
                        //System.out.println("LEYENDO "+lectorxml.getAttributeValue(null, "direccion"));
                        lista.add(new ActividadExtra(lectorxml.getAttributeValue(null, "titulo"),
                                lectorxml.getAttributeValue(null, "ddescripcion"),
                                lectorxml.getAttributeValue(null, "categoria"),
                                lectorxml.getAttributeValue(null, "fecha"),
                                lectorxml.getAttributeValue(null, "inscripciones"),
                                lectorxml.getAttributeValue(null, "direccion")
                        ));
                    }
                }
                evento = lectorxml.next();
            }

        }catch (Exception e) {
            System.out.println("ERROR AL LEER "+e);
        }

        if(lista.size()!=0){
            return lista;
        }else{
            return new ArrayList<ActividadExtra>();
        }

    }

}

