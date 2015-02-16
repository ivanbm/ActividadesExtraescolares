package com.izv.android.actividadesextraescolares;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ivan on 09/02/2015.
 */
public class PrincipalListado extends Fragment {
    private View v;

    public PrincipalListado() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragmento_principal_listado, container, false);
        return v;
    }

}
