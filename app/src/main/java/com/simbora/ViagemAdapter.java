package com.simbora;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 20/05/2018.
 */

public class ViagemAdapter extends BaseAdapter {
    private List<Viagem> viagens;
    private ArrayList<Viagem> arraylist;
    private Activity act;

    public ViagemAdapter(List<Viagem> bandeiras, Activity act) {
        this.viagens = bandeiras;
        this.act = act;
        this.arraylist = new ArrayList<Viagem>();
        this.arraylist.addAll(this.viagens);
    }

    @Override
    public int getCount() {
        return viagens.size();
    }

    @Override
    public Object getItem(int position) {
        return viagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.viagem_adapter, parent, false);
        Viagem viagem = viagens.get(position);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView modo = (TextView) view.findViewById(R.id.modo);
        TextView autor = (TextView) view.findViewById(R.id.autor);
        TextView data = (TextView) view.findViewById(R.id.data);

        name.setText(viagem.getGroupName());
        modo.setText(viagem.getDepMod());
        autor.setText(viagem.getUserAdmin().getName());
        data.setText(viagem.getDepDate().split("-")[0]);

        return view;
    }
}