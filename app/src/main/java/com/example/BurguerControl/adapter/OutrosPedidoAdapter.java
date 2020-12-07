package com.example.BurguerControl.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.BurguerControl.R;
import com.example.BurguerControl.objetos.OutroProduto;

import java.util.List;

public class OutrosPedidoAdapter extends ArrayAdapter<OutroProduto> {

    private Activity context;
    private List<OutroProduto> outroList;

    public OutrosPedidoAdapter(Activity context, List<OutroProduto> outroList){
        super(context, R.layout.outrospedidolayout,outroList);
        this.context=context;
        this.outroList = outroList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.outrospedidolayout,null,true);

        TextView nomeOutro = (TextView)listViewItem.findViewById(R.id.outrosPedidoNome);

        OutroProduto outroProduto = outroList.get(position);

        nomeOutro.setText(outroProduto.getDescricaoOutro());

        return listViewItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.outrospedidolayout,null,true);

        TextView nomeOutro = (TextView)listViewItem.findViewById(R.id.outrosPedidoNome);

        OutroProduto outroProduto = outroList.get(position);

        nomeOutro.setText(outroProduto.getDescricaoOutro());

        return listViewItem;
    }
}
