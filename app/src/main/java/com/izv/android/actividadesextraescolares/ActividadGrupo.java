package com.izv.android.actividadesextraescolares;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Ivan on 15/02/2015.
 */
public class ActividadGrupo implements Serializable {
    private int id;
    private String idActividad;
    private String idGrupo;

    public ActividadGrupo(int id, String idActividad, String idGrupo) {
        this.id = id;
        this.idActividad = idActividad;
        this.idGrupo = idGrupo;
    }

    public ActividadGrupo(String idActividad, String idGrupo) {
        this.idActividad = idActividad;
        this.idGrupo = idGrupo;
    }

    public ActividadGrupo(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.idActividad = object.getString("idactividad");
            this.idGrupo = object.getString("idgrupo");
        }catch (JSONException ex){

        }
    }

    public JSONObject getJSON(){
        JSONObject objetoJSON = new JSONObject();
        try{
            //objetoJSON.put("id",this.id);
            objetoJSON.put("idactividad",this.idActividad);
            objetoJSON.put("idgrupo",this.idGrupo);
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

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public String toString() {
        return "ActividadGrupo{" +
                "id=" + id +
                ", idActividad=" + idActividad +
                ", idGrupo=" + idGrupo +
                '}';
    }
}
