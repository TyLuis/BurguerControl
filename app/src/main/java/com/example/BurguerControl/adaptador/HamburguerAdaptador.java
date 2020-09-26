package com.example.BurguerControl.adaptador;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.Burguer;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HamburguerAdaptador extends BaseAdapter {
    private final ArrayList<Burguer> burguers;
    private final Activity activity;

    public HamburguerAdaptador(ArrayList<Burguer> burguers, Activity activity) {
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
        Burguer burguer = burguers.get(position);

        if (linha==null){
            linha = this.activity.getLayoutInflater().inflate(R.layout.hamburguer,parent,false);
        }

        TextView nome = (TextView)linha.findViewById(R.id.edtNomeProduto);
        TextView quantidade = (TextView)linha.findViewById(R.id.edtQuantidadeProduto);
        TextView valor = (TextView)linha.findViewById(R.id.edtValorProduto);

        nome.setText(burguer.getDescricaoBurguer());
        valor.setText(String.valueOf(burguer.getValorBurguer()));
        return linha;
    }
}
