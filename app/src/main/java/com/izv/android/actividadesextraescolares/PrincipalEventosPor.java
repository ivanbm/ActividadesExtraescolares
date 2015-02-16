package com.izv.android.actividadesextraescolares;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by Ivan on 09/02/2015.
 */
public class PrincipalEventosPor extends Activity {

    private TextView tvFecha, tvGrupo, tvProfe, tvHora, tvDesc, tvLugar;
    private Actividad ac;
    private String fechai, fechaf, horai, horaf;
    private ArrayList<Grupo> grupo = new ArrayList<Grupo>();
    private ArrayList<ActividadGrupo> actividadgrupo = new ArrayList<ActividadGrupo>();
    private ArrayList<Profesor> profe = new ArrayList<Profesor>();
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_eventosportrait);

        tvFecha = (TextView)findViewById(R.id.tvFecha);
        tvGrupo = (TextView)findViewById(R.id.tvGrupo);
        tvProfe = (TextView)findViewById(R.id.tvProfesor);
        tvHora = (TextView)findViewById(R.id.tvHora);
        tvLugar = (TextView)findViewById(R.id.tvLugar);
        tvDesc = (TextView)findViewById(R.id.tvDesc);


        ac = (Actividad) getIntent().getSerializableExtra("actividad");
        final PrincipalEventos fdos = (PrincipalEventos)getFragmentManager().findFragmentById(R.id.fragment);


        cargarLista();

    }





    /*----------------------------------------*/
    /*          OBTENER LOS DATOS             */
    /*----------------------------------------*/

    public void cargarLista() {

        String[] peticiones = new String[3];
        peticiones[0] = "grupo";
        peticiones[1] = "profesor";
        peticiones[2] = "actividadgrupo";
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
                    grupo.add(g);
                }
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
                    profe.add(p);
                }

            } catch (JSONException e) {

            }


            JSONTokener tokenag = new JSONTokener(r[2]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(tokenag);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    ActividadGrupo ag = new ActividadGrupo(objeto);
                    actividadgrupo.add(ag);
                }
                initComponents();
            } catch (JSONException e) {

            }


        }
    }

    public void initComponents(){
        fechai = ac.getFechaI().split(" ")[0];
        fechaf = ac.getFechaF().split(" ")[0];
        horai = ac.getFechaI().split(" ")[1];
        horaf = ac.getFechaF().split(" ")[1];
        tvFecha.setText(fechai + " - " + fechaf);
        tvHora.setText(horai + " - " + horaf);
        tvLugar.setText(ac.getLugarI() + " - " + ac.getLugarF());
        tvDesc.setText(ac.getDescripcion());
        tvGrupo.setText(getGrupo(ac.getId()));
        tvProfe.setText(getProfesor(ac.getIdProfesor()));
    }



    public String getProfesor(String id){
        for(int i=0;i<profe.size();i++){

            if(id.equals(profe.get(i).getId()+"")){
                return profe.get(i).getNombre() + " " + profe.get(i).getApellidos();
            }
        }
        return null;
    }


    public String getGrupo(String idact){
        for(int i=0;i<actividadgrupo.size();i++){

            if(idact.equals(actividadgrupo.get(i).getIdActividad()+"")){
                String idg = actividadgrupo.get(i).getIdGrupo();
                int idgr = Integer.parseInt(idg);
                System.out.println("IDG "+idg+" -- grupo: "+grupo.get(idgr).getGrupo());
                return grupo.get(idgr).getGrupo();
            }
        }
        return null;
    }

}
