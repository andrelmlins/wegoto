package com.simbora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private EditText email;
    private EditText password;
    private Button btnLogin;
    private Button btnRegister;
    private Requests requests;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        if(!pref.getString("name","").equals("")){
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
            finish();
        }

        this.requests = Requests.getInstance(this);

        this.email = (EditText) this.findViewById(R.id.email);
        this.password = (EditText) this.findViewById(R.id.password);
        this.btnLogin = (Button) this.findViewById(R.id.login);
        this.btnRegister = (Button) this.findViewById(R.id.register);

        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        this.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.loading = ProgressDialog.show(this, "", "Entrando...", true);
        this.loading.show();
        JSONObject json = new JSONObject();
        try {
            json.put("email",this.email.getText());
            json.put("password",this.password.getText());
        } catch (JSONException e) {
            this.loading.dismiss();
            e.printStackTrace();
        }

        requests.post("/entrar",json,this,this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.loading.dismiss();
        Toast.makeText(this,getString(R.string.erro_conexao),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject user = response.getJSONObject("user");
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putString("id", user.getString("id"));
            editor.putString("name", user.getString("name"));
            editor.putString("email", user.getString("email"));
            editor.putString("token", user.getString("token"));
            editor.commit();
            this.loading.dismiss();

            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            this.loading.dismiss();
            e.printStackTrace();
        }
    }
}
