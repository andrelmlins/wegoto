package com.simbora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdicionarViagem extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private ImageButton emailButton;
    private EditText emailText;
    private EditText name;
    private List<String> list;
    private List<Long> listUserId;
    private Requests requests;
    private ProgressDialog loading;
    private boolean sendEmail;
    private CheckBox check;
    private Float latitudePara;
    private Float longitudePara;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_viagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();

        this.longitudePara = b.getFloat("lng");
        this.latitudePara = b.getFloat("lat");
        this.data = b.getString("data");

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_send);
        toolbar.setNavigationIcon(drawable);
        setSupportActionBar(toolbar);

        this.requests = Requests.getInstance(this);

        this.emailButton = (ImageButton) findViewById(R.id.emailButton);
        this.emailText = (EditText) findViewById(R.id.emailText);
        this.list = new ArrayList<>();
        this.listUserId = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(arrayAdapter);

        this.emailButton.setOnClickListener(this);
        this.check = (CheckBox) findViewById(R.id.agrupado);
        this.name = (EditText) findViewById(R.id.name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(AdicionarViagem.this, "", "Cadastrando Viagem...", true);
                loading.show();
                JSONObject json = new JSONObject();
                SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                try {
                    json.put("groupName",name.getText());
                    json.put("depMod",check.isChecked()? "1" : "0");
                    json.put("desLat",latitudePara);
                    json.put("desLon",longitudePara);
                    json.put("depDate",data);
                    json.put("userAdmin",pref.getString("id",""));
                    list.add(pref.getString("email",""));
                    JSONArray array = new JSONArray();
                    for(Long id : listUserId){
                        array.put(id);
                    }
                    json.put("userIds",array);

                    requests.post("/usuario/"+pref.getString("id","")+"/corridas",json,AdicionarViagem.this,AdicionarViagem.this);
                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        this.loading = ProgressDialog.show(this, "", "Adicionando usuário...", true);
        this.loading.show();
        sendEmail=true;
        requests.get("/usuario?email="+this.emailText.getText().toString(), this, this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.loading.dismiss();
        Toast.makeText(this,getString(R.string.erro_conexao),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        if(sendEmail){
            try {
                if(response.getBoolean("result")){
                    list.add(this.emailText.getText().toString());
                    this.emailText.setText("");
                    this.listUserId.add(response.getJSONObject("user").getLong("id"));
                    this.loading.dismiss();
                    Toast.makeText(this,"E-mail adicionado", Toast.LENGTH_SHORT).show();
                } else {
                    this.loading.dismiss();
                    Toast.makeText(this,"E-mail não existe", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                this.loading.dismiss();
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
            finish();
            loading.dismiss();
        }
    }
}
