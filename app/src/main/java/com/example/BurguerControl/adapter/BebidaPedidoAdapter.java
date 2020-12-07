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
import com.example.BurguerControl.objetos.Bebida;

import java.util.List;

public class BebidaPedidoAdapter extends ArrayAdapter<Bebida> {

    private Activity context;
    private List<Bebida> bebidaList;

    public BebidaPedidoAdapter(Activity context, List<Bebida> bebidaList){
        super(context, R.layout.bebidapedidolayout,bebidaList);
        this.context = context;
        this.bebidaList = bebidaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.bebidapedidolayout,null,true);

        TextView nomeBebida = (TextView)listViewItem.findViewById(R.id.nomePedidoBebida);

        Bebida bebida = bebidaList.get(position);

        nomeBebida.setText(bebida.getDescricaoBebida());

        return listViewItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.bebidapedidolayout,null,true);

        TextView nomeBebida = (TextView)listViewItem.findViewById(R.id.nomePedidoBebida);

        Bebida bebida = bebidaList.get(position);

        nomeBebida.setText(bebida.getDescricaoBebida());

        return listViewItem;
    }
}
