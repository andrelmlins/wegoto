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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AndreLucas on 20/05/2018.
 */

public class ViagensFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private View rootView;
    private ProgressDialog progress;
    private boolean favorito = false;
    private List<Viagem> list = new ArrayList<Viagem>();
    private ViagemAdapter adapter;
    private ListView listView;

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
        this.listView = (ListView) rootView.findViewById(R.id.list);
        Requests r = Requests.getInstance(getActivity());
        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        r.get("/usuario/"+pref.getString("id","")+"/corridas",this,this);
        return this.rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onResponse(JSONObject response) {
        try {
            JSONArray json = response.getJSONArray("orders");

            for(int i=0; i<json.length();i++){
                JSONObject viagem = json.getJSONObject(i);
                this.list.add(new Viagem(
                        viagem.getJSONObject("departure").getString("lat"),
                        viagem.getJSONObject("departure").getString("lon"),
                        viagem.getString("mode"),
                        viagem.getString("20/05/2018"),
                        viagem.getJSONObject("group").getString("name"),
                        new User(viagem.getJSONObject("group").getJSONObject("userAdmin").getString("name"),
                                viagem.getJSONObject("group").getJSONObject("userAdmin").getString("email"),
                                viagem.getJSONObject("group").getJSONObject("userAdmin").getString("id"))));
            }
            this.adapter = new ViagemAdapter(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            this.progress.dismiss();
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
