package com.izv.android.actividadesextraescolares;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Ivan on 14/02/2015.
 */
public class Grupo implements Serializable {
    private int id;
    private String grupo;

    public Grupo() {
    }

    public Grupo(int id, String grupo) {
        this.id = id;
        this.grupo = grupo;
    }

    public Grupo(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.grupo = object.getString("grupo");
        }catch (JSONException ex){

        }
    }

    public JSONObject getJSON(){
        JSONObject objetoJSON = new JSONObject();
        try{
            objetoJSON.put("id",this.id);
            objetoJSON.put("grupo",this.grupo);
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "id=" + id +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}
