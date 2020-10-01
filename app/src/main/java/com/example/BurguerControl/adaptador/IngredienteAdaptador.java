package com.example.BurguerControl.adaptador;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.Ingrediente;

import java.util.ArrayList;

public class IngredienteAdaptador extends BaseAdapter {
    private final ArrayList<Ingrediente> ingredientes;
    private final Activity activity;

    public IngredienteAdaptador(ArrayList<Ingrediente> ingredientes, Activity activity) {
        this.ingredientes = ingredientes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.ingredientes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ingredientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.ingredientes.get(position).getIdIngrediente());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;
        if (linha==null){
            linha = activity.getLayoutInflater().inflate(R.layout.celula_ingrediente,parent,false);
        }
        Ingrediente ingrediente = ingredientes.get(position);

        TextView nomeIngrediente = (TextView)linha.findViewById(R.id.tvCelulaDescIngrediente);
        TextView valorIngrediente = (TextView)linha.findViewById(R.id.tvCelulaValorIngrediente);

        nomeIngrediente.setText(ingrediente.getDescricaoIngrediente());
        valorIngrediente.setText(String.valueOf(ingrediente.getValorIngrediente()));

        return linha;
    }
}
