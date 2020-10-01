package com.example.BurguerControl.adaptador;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.Ingrediente;
import com.example.BurguerControl.objetos.OutroProduto;

import java.util.ArrayList;

public class OutrosProdutosAdaptador extends BaseAdapter {
    private final ArrayList<OutroProduto> outroProdutos;
    private final Activity activity;

    public OutrosProdutosAdaptador(ArrayList<OutroProduto> outroProdutos, Activity activity) {
        this.outroProdutos = outroProdutos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return this.outroProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.outroProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.outroProdutos.get(position).getIdOutroProduto());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = convertView;
        if (linha==null){
            linha = activity.getLayoutInflater().inflate(R.layout.celula_outros_produtos,parent,false);
        }

        OutroProduto outroProduto = outroProdutos.get(position);

        TextView nomeOutro = (TextView)linha.findViewById(R.id.tvCelulaDescOutros);
        TextView quantOutro = (TextView)linha.findViewById(R.id.tvCelulaQtdOutros);
        TextView valorOutro = (TextView)linha.findViewById(R.id.tvCelulaValorOutros);

        nomeOutro.setText(outroProduto.getDescricaoOutro());
        quantOutro.setText(String.valueOf(outroProduto.getQuantidadeOutro()));
        valorOutro.setText(String.valueOf(outroProduto.getValorOutro()));

        return linha;
    }
}
