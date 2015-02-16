package com.izv.android.actividadesextraescolares;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Ivan on 13/02/2015.
 */
public class Profesor implements Serializable{

    private int id;
    private String nombre, apellidos, departamento;

    public Profesor() {
    }

    public Profesor(int id, String nombre, String apellidos, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.departamento = departamento;
    }

    public Profesor(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.nombre = object.getString("nombre");
            this.apellidos = object.getString("apellidos");
            this.departamento = object.getString("departamento");
        }catch (JSONException ex){

        }
    }

    public JSONObject getJSON(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id",this.id);
            objetoJSON.put("nombre",this.nombre);
            objetoJSON.put("apellidos",this.apellidos);
            objetoJSON.put("departamento",this.departamento);
            return objetoJSON;

        }catch (Exception e){
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
