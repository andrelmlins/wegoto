package com.simbora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

public class ViagemAdd extends AppCompatActivity implements View.OnClickListener {
    private TextView nome;
    private RadioGroup partida;
    private AutoCompleteTextView de;
    private AutoCompleteTextView para;
    private Button btnSend;
    private Requests requests;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.requests = Requests.getInstance(this);
        this.nome = (TextView) findViewById(R.id.name);
        this.partida = (RadioGroup) findViewById(R.id.tipo_partida);
        this.de = (AutoCompleteTextView) findViewById(R.id.de);
        this.para = (AutoCompleteTextView) findViewById(R.id.para);
        this.de.setThreshold(3);
        this.para.setThreshold(3);
        this.btnSend = (Button) findViewById(R.id.btnSend);

        this.btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnSend){

        }
    }
}
