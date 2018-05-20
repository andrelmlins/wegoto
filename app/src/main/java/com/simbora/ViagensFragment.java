package com.simbora;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AndreLucas on 20/05/2018.
 */

public class ViagensFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private View rootView;
    private ProgressDialog progress;
    private boolean favorito = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.viagens_fragment, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando Dados..", true);
        Requests r = Requests.getInstance(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        r.get(Requests.ROOT+"/usuario/"+pref.getString("id","")+"/corridas",this,this);
        return this.rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onResponse(JSONObject response) {
        try {
            JSONObject result = response.getJSONObject("bandeira");

            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        this.progress.dismiss();
        Toast.makeText(getActivity(), getString(R.string.erro_conexao),
                Toast.LENGTH_SHORT).show();
    }
}
