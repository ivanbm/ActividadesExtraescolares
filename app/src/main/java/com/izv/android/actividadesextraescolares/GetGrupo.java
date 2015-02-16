package com.izv.android.actividadesextraescolares;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by Ivan on 14/02/2015.
 */
public class GetGrupo {
    private ArrayList<Grupo> grupo = new ArrayList<Grupo>();
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";



    public void cargarGrupos() {

        String[] peticiones = new String[1];
        peticiones[0] = "grupo";
        GetRestful get = new GetRestful();
        get.execute(peticiones);
    }


    private class GetRestful extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String[] params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params) {
                r[contador] = ClienteRestFul.get(URLBASE + s);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            //Toast.makeText(MainActivity.this, r[0], Toast.LENGTH_SHORT).show();

            JSONTokener token = new JSONTokener(r[0]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Grupo g = new Grupo(objeto);
                    //actividades.add(a.toString());
                    grupo.add(g);
                }
                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
    }


}
