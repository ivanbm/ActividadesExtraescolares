package com.izv.android.actividadesextraescolares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Ivan on 29/11/2014.
 */
public class Adaptador extends ArrayAdapter<Actividad> {

    private ArrayList<Actividad> lista;
    private Context contexto;
    private int recurso;
    private static LayoutInflater i;


    public Adaptador(Context context, int resource, ArrayList<Actividad> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recurso = resource;
        this.i = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public TextView tv1, tv2, tv3;
        public ImageView iv;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = i.inflate(recurso, null);


            vh = new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tvDeporte);
            vh.tv2 = (TextView)convertView.findViewById(R.id.tvFecha);
            vh.tv3 = (TextView)convertView.findViewById(R.id.tvDireccion);
            vh.iv = (ImageView)convertView.findViewById(R.id.ivIcono);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        vh.position = position;
        vh.tv1.setTag(position);

        vh.tv1.setText(lista.get(position).getTipo());
        vh.tv2.setText(lista.get(position).getFechaI());
        vh.tv3.setText(lista.get(position).getDescripcion());

        if("tenis"=="uno"){
            vh.iv.setImageResource(R.drawable.tenis);
        }else if("futbol"=="uno") {
            vh.iv.setImageResource(R.drawable.futbol);
        }else if("baloncesto"=="uno"){
            vh.iv.setImageResource(R.drawable.baloncesto);
        }else if("atletismo"=="uno"){
            vh.iv.setImageResource(R.drawable.atletismo);
        }else if("conduccion"=="uno"){
            vh.iv.setImageResource(R.drawable.conduccion);
        }else if("running"=="uno"){
            vh.iv.setImageResource(R.drawable.running);
        }else if("esqui"=="uno"){
            vh.iv.setImageResource(R.drawable.esqui);
        }else{
            vh.iv.setImageResource(R.drawable.ic_launcher);
        }

        return convertView;
    }
}