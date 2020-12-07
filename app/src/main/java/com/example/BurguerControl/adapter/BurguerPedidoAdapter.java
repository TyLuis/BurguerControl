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
import com.example.BurguerControl.objetos.Burguer;

import java.util.List;

public class BurguerPedidoAdapter extends ArrayAdapter<Burguer> {

    private Activity context;
    private List<Burguer> burguerList;

    public BurguerPedidoAdapter(Activity context, List<Burguer> burguerList) {
        super(context, R.layout.burguerpedidolayout, burguerList);
        this.context = context;
        this.burguerList = burguerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.burguerpedidolayout,null,true);

        TextView nomeBurguer = (TextView)listViewItem.findViewById(R.id.nomePedidoBurguer);

        Burguer burguer = burguerList.get(position);

        nomeBurguer.setText(burguer.getDescricaoBurguer());

        return listViewItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.burguerpedidolayout,null,true);

        TextView nomeBurguer = (TextView)listViewItem.findViewById(R.id.nomePedidoBurguer);

        Burguer burguer = burguerList.get(position);

        nomeBurguer.setText(burguer.getDescricaoBurguer());

        return listViewItem;
    }
}
