package com.izv.android.actividadesextraescolares;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity {

    private Adaptador ad;
    private ListView lv;
    private TextView tvFecha, tvGrupo, tvProfe, tvHora, tvDesc, tvLugar;
    private Actividad ac;
    private String fechai, fechaf, horai, horaf;
    private ArrayList<Grupo> grupo = new ArrayList<Grupo>();
    private ArrayList<ActividadGrupo> actividadgrupo = new ArrayList<ActividadGrupo>();
    private ArrayList<Profesor> profe = new ArrayList<Profesor>();
    private ArrayList<Actividad> actividades;
    private final static int ANADIR = 1;
    private final static String URLBASE = "http://ieszv.x10.bz/restful/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        actividades = new ArrayList<Actividad>();
        lv = (ListView) findViewById(R.id.listView);
        ad = new Adaptador(this, R.layout.detalle, actividades);
        lv.setAdapter(ad);
        cargarActividades();

        registerForContextMenu(lv);

        final PrincipalEventos fdos = (PrincipalEventos) getFragmentManager().findFragmentById(R.id.fragment4);
        final boolean horizontal = fdos != null && fdos.isInLayout(); //Saber que orientación tengo
        int v = getResources().getConfiguration().orientation;

        switch (v) {

            case Configuration.ORIENTATION_LANDSCAPE:
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                break;
        }

        lv = (ListView)findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(horizontal){
                    ac = (Actividad)lv.getItemAtPosition(position);
                    initComponents();
                    //cargarLista();
                }else{
                    Actividad ac = (Actividad)lv.getItemAtPosition(position);
                    Intent i = new Intent(MainActivity.this,PrincipalEventosPor.class);
                    i.putExtra("actividad", ac);
                    startActivityForResult(i, 4);
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){

            case R.id.elimiar:
                eliminar(index);
                ad.notifyDataSetChanged();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            int v = getResources().getConfiguration().orientation;

            switch (v) {

                case Configuration.ORIENTATION_LANDSCAPE:
                    Intent il = new Intent(this, Horizontal.class);
                    startActivityForResult(il, ANADIR);
                    break;
                case Configuration.ORIENTATION_PORTRAIT:
                    Intent iv = new Intent(this, Vertical.class);
                    startActivityForResult(iv, ANADIR);
                    break;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ANADIR:
                    ad.notifyDataSetChanged();

                    break;
            }
        } else {
            //coverSeleccionada = false;
        }
    }

    /*private void agregarActividades(Actividad a){
        //Actividad a = new Actividad("1","0","complementaria","2015-01-29","2015-01-29","Alhambra","Alhambra","Nos vamos de vistia a donde seaaaaa","juanito");
        PostRestful post = new PostRestful();
        ParametrosPost pp = new ParametrosPost();
        pp.url=URLBASE+"actividad";
        pp.json=a.getJSON();
        post.execute(pp);
    }*/

    /* Cargar actividades */

    public void cargarActividades() {

        String[] peticiones = new String[4];
        peticiones[0] = "actividad/ivan";
        peticiones[1] = "profesor";
        peticiones[2] = "grupo";
        peticiones[3] = "actividadgrupo";
        GetRestful get = new GetRestful();
        get.execute(peticiones);

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
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            JSONTokener token = new JSONTokener(r);
            try {
                JSONObject objeto = new JSONObject(token);
                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
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
            Toast.makeText(MainActivity.this, r[0], Toast.LENGTH_SHORT).show();

            JSONTokener token = new JSONTokener(r[0]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Actividad a = new Actividad(objeto);
                    //actividades.add(a.toString());
                    actividades.add(a);
                }
                ad.notifyDataSetChanged();
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


            JSONTokener tokeng = new JSONTokener(r[2]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(tokeng);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Grupo g = new Grupo(objeto);
                    grupo.add(g);
                }
                //ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }

            JSONTokener tokenag = new JSONTokener(r[3]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(tokenag);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    ActividadGrupo ag = new ActividadGrupo(objeto);
                    actividadgrupo.add(ag);
                }
                Collections.sort(actividades);
            } catch (JSONException e) {

            }

        }
    }






    /*-------------------------------------*/
    /*          METODOS HORIZONTAL         */
    /*-------------------------------------*/

    public void initComponents(){
        tvFecha = (TextView)findViewById(R.id.tvFecha);
        tvGrupo = (TextView)findViewById(R.id.tvGrupo);
        tvProfe = (TextView)findViewById(R.id.tvProfesor);
        tvHora = (TextView)findViewById(R.id.tvHora);
        tvLugar = (TextView)findViewById(R.id.tvLugar);
        tvDesc = (TextView)findViewById(R.id.tvDesc);

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



    /*----------------------------------------*/
    /*          OBTENER LOS DATOS             */
    /*----------------------------------------*/


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

    /*----------------------------------------*/
    /*          ELIMINAR UN REGISTRO          */
    /*----------------------------------------*/

    public void eliminar(final int index){

        Actividad a = (Actividad)lv.getItemAtPosition(index);
        String[] peticiones = new String[1];
        peticiones[0] = "actividad/"+a.getId();
        DeleteRestFul del = new DeleteRestFul();
        del.execute(peticiones);


    }


    private class DeleteRestFul extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String[] params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params) {
                r[contador] = ClienteRestFul.delete(URLBASE + params[0]);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPostExecute(String[] r) {
            super.onPostExecute(r);
            Toast.makeText(MainActivity.this, r[0], Toast.LENGTH_SHORT).show();

            JSONTokener token = new JSONTokener(r[0]);
            try {
                //JSONObject objeto = new JSONObject(token);
                JSONArray array = new JSONArray(token);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Actividad a = new Actividad(objeto);
                    //actividades.add(a.toString());
                    actividades.add(a);
                }
                ad.notifyDataSetChanged();
            } catch (JSONException e) {

            }
        }
    }
}