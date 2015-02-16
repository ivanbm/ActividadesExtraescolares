package com.izv.android.actividadesextraescolares;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by Ivan on 14/12/2014.
 */
public class Vertical extends Activity {
    private EditText fechaI, horaI, fechaF, horaF, lugarI, lugarF, descripcion;
    private RadioGroup tipo;
    private ArrayList<Actividad> lista;
    private ArrayList<Grupo> grupo = new ArrayList<Grupo>();
    private ArrayList<Profesor> profe = new ArrayList<Profesor>();
    private ArrayList<String> grupoNom = new ArrayList<String>();
    private ArrayList<String> profeNom = new ArrayList<String>();
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";
    private Spinner spPro, spCur;
    private String tip, fi, hi, ff, hf, li, lf, des, ti, pro, gru;
    private String idInsertado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical);

        spPro = (Spinner)findViewById(R.id.spProfe);
        spCur = (Spinner)findViewById(R.id.spCurso);

        cargarSpinner();
        System.out.println(grupo.toString());

    }

    public void anadirPor(View v){


        fechaI = (EditText)findViewById(R.id.etFechaI);
        fechaF = (EditText)findViewById(R.id.etFechaF);
        horaI = (EditText)findViewById(R.id.etHoraI);
        horaF = (EditText)findViewById(R.id.etHoraF);
        lugarI = (EditText)findViewById(R.id.etLugarI);
        lugarF = (EditText)findViewById(R.id.etLugarF);
        descripcion = (EditText)findViewById(R.id.etDescripcion);
        tipo = (RadioGroup)findViewById(R.id.rgTipo);
        gru = spCur.getSelectedItem().toString();
        pro = spPro.getSelectedItem().toString();


        tip = ((RadioButton)findViewById(tipo.getCheckedRadioButtonId())).getText().toString();
        fi = fechaI.getText().toString();
        ff = fechaF.getText().toString();
        hi = horaI.getText().toString();
        hf = horaF.getText().toString();
        li = lugarI.getText().toString();
        lf = lugarF.getText().toString();
        des = descripcion.getText().toString();

        Actividad a = new Actividad(obtenerIdProfesor(), tip, fi + " "+hi, ff + " "+hf, li, lf, des, "ivan");
        //Actividad a = new Actividad("2","extraescolar","2016-01-15","2016-01-15","Granada","Granada","Vamos a visitar la alhambra","manolito");
        //Actividad a = new Actividad("1","0","complementaria","2015-01-29","2015-01-29","Alhambra","Alhambra","Nos vamos de vistia a donde seaaaaa","juanito");
        System.out.println(tip + " -- "+obtenerIdProfesor());
        //anadirActividad(a);

        this.finish();

        Toast.makeText(getApplicationContext(), "Actividad añadida", Toast.LENGTH_SHORT).show();
    }


    public void cargarSpinner() {

        String[] peticiones = new String[3];
        peticiones[0] = "grupo";
        peticiones[1] = "profesor";
        peticiones[2] = "actividad/ivan";
        GetRestful get = new GetRestful();
        get.execute(peticiones);

    }

    /*-------------------------------------------*/
    /*             OBTENER LOS DATOS             */
    /*-------------------------------------------*/


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
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Grupo g = new Grupo(objeto);
                    grupo.add(g);
                    grupoNom.add(g.getGrupo());
                }

                System.out.println(grupo.toString());
                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }

            JSONTokener tokenp = new JSONTokener(r[1]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(tokenp);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Profesor p = new Profesor(objeto);
                    //actividades.add(a.toString());
                    profe.add(p);
                    profeNom.add(p.getNombre());
                }
                System.out.println(profe.toString());
                llenarSpinner();
                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
    }

    public void llenarSpinner() {
        ArrayAdapter<String> dataAdapterGru = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, grupoNom );
        dataAdapterGru.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCur.setAdapter(dataAdapterGru);

        ArrayAdapter<String> dataAdapterPro = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, profeNom );
        dataAdapterPro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPro.setAdapter(dataAdapterPro);
    }




    /*-------------------------------------------*/
    /*             GRABAR LOS DATOS              */
    /*-------------------------------------------*/

    //a este lo llamo para insertar la actividad y obtener el id generado por el server
    public void anadirActividad(Actividad a){
        PostRestful post = new PostRestful();
        ParametrosPost pp = new ParametrosPost();
        pp.url=URLBASE+"actividad";
        pp.json=a.getJSON();
        post.execute(pp);

        anadirActividadGrupo();
    }


    //a este lo llamo con el id que me devuelve el server para añadirlo en la API actividad grupo
    // y poder sacar posteriormente todos los datos en el detalle.
    // lo llamo en el onPost de "anadirActividad"
    public void anadirActividadGrupo(){
        System.out.println("ANADIRACTIVIDADGRUPO "+idInsertado);
        String idgrupo = obtenerIdGrupo()+"";

        ActividadGrupo ag = new ActividadGrupo(idInsertado, idgrupo);

        PostRestful post = new PostRestful();
        ParametrosPost pp = new ParametrosPost();
        pp.url=URLBASE+"actividadgrupo";
        pp.json=ag.getJSON();
        post.execute(pp);
    }


    public int obtenerIdGrupo(){
        for(int i=0; i < grupo.size();i++){
            if(gru.equals(grupo.get(i).getGrupo())){
                return  grupo.get(i).getId();
            }
        }
        return 0;
    }


    public String obtenerIdProfesor(){
        for(int i=0; i < profe.size();i++){
            if(pro.equals(profe.get(i).getNombre())){
                return  profe.get(i).getId()+"";
            }
        }
        return null;
    }


    static class ParametrosPost{
        String url;
        JSONObject json;
    }

    private class PostRestful extends AsyncTask<ParametrosPost, Void, String> {

        @Override
        protected String doInBackground(ParametrosPost[] params) {
            String r = ClienteRestFul.post(params[0].url,params[0].json);
            return r;
        }

        @Override
        protected void onPostExecute(String r) {
            super.onPostExecute(r);

            Toast.makeText(Vertical.this, r, Toast.LENGTH_SHORT).show();
            System.out.println("TOKEN "+r);
            JSONTokener token = new JSONTokener(r);
            try {
                JSONObject objeto = new JSONObject(token);
                idInsertado = objeto.getString("r").toString();

                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
    }
}
