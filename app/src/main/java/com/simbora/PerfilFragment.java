package com.simbora;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by AndreLucas on 20/05/2018.
 */

public class PerfilFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private Button btnSair;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {
        this.rootView = inflater.inflate(R.layout.perfil_fragment, container, false);

        this.btnSair= this.rootView.findViewById(R.id.sair);
        this.btnSair.setOnClickListener(this);

        SharedPreferences settings = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        TextView nome = this.rootView.findViewById(R.id.nome);
        TextView email = this.rootView.findViewById(R.id.email);

        nome.setText(settings.getString("name",""));
        email.setText(settings.getString("email",""));

        return this.rootView;
    }

    @Override
    public void onClick(View view) {
        SharedPreferences settings = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        getActivity().finish();
    }
}