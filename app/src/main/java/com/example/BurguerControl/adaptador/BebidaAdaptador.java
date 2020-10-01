package com.example.BurguerControl.adaptador;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.Bebida;

import java.util.ArrayList;

public class BebidaAdaptador extends BaseAdapter {
    private final ArrayList<Bebida> bebidas;
    private final Activity activity;

    public BebidaAdaptador(ArrayList<Bebida> bebidas, Activity activity) {
        this.bebidas = bebidas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.bebidas.size();
    }

    @Override
    public Object getItem(int position) {
        return this.bebidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.bebidas.get(position).getIdBebida());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;
        if (linha==null){
            linha = activity.getLayoutInflater().inflate(R.layout.celula_bebida,parent,false);
        }
        Bebida bebida = bebidas.get(position);

        TextView nomeBebida = (TextView)linha.findViewById(R.id.tvCelulaDesBebida);
        TextView quantBebida = (TextView)linha.findViewById(R.id.tvCelulaQtdBebida);
        TextView valorBebida = (TextView)linha.findViewById(R.id.tvCelulaValorBebida);

        nomeBebida.setText(bebida.getDescricaoBebida());
        quantBebida.setText(String.valueOf(bebida.getQuantidadeBebida()));
        valorBebida.setText(String.valueOf(bebida.getValorBebida()));

        return linha;
    }
}
