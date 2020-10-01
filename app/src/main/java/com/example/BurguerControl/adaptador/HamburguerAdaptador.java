package com.example.BurguerControl.adaptador;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.Burguer;

import java.util.ArrayList;
import java.util.List;

public class HamburguerAdaptador extends BaseAdapter {
    private final List<Burguer> burguers;
    private final Activity activity;

    public HamburguerAdaptador(List<Burguer> burguers, Activity activity) {
        this.burguers = burguers;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.burguers.size();
    }

    @Override
    public Object getItem(int position) {
        return this.burguers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.burguers.get(position).getIdBurguer());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;
        if(linha==null){
            linha = activity.getLayoutInflater().inflate(R.layout.celula_hamburguer,parent,false);
        }

        Burguer burguer = burguers.get(position);

        TextView nome = (TextView)linha.findViewById(R.id.tvCelulaDescricao);
        TextView quantidade = (TextView)linha.findViewById(R.id.tvCelulaQuantidade);
        TextView valor = (TextView)linha.findViewById(R.id.tvCelulaValor);

        nome.setText(burguer.getDescricaoBurguer());
        quantidade.setText(String.valueOf(burguer.getEstoqueBurguer()));
        valor.setText(String.valueOf(burguer.getValorBurguer()));
        return linha;
    }
}
