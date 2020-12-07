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
import com.example.BurguerControl.objetos.Pedidos;

import java.util.List;

public class CozinhaAdapter extends ArrayAdapter<Pedidos> {
    private Activity context;
    private List<Pedidos> pedidosList;

    public CozinhaAdapter(Activity context, List<Pedidos> pedidosList){
        super(context, R.layout.cozinhalayout,pedidosList);
        this.context = context;
        this.pedidosList = pedidosList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.cozinhalayout,null,true);

        TextView numeroMesa = (TextView)listViewItem.findViewById(R.id.numeroMesa);

        Pedidos pedidos = pedidosList.get(position);

        numeroMesa.setText("Mesa: "+String.valueOf(pedidos.getMesa()));

        return listViewItem;
    }
}
