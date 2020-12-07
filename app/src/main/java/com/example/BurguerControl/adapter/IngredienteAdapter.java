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
import com.example.BurguerControl.objetos.Ingrediente;

import java.util.List;

public class IngredienteAdapter extends ArrayAdapter<Ingrediente> {

    private Activity context;
    private List<Ingrediente> ingredienteList;

    public IngredienteAdapter(Activity context, List<Ingrediente> ingredienteList){
        super(context, R.layout.ingredientelayout,ingredienteList);
        this.context = context;
        this.ingredienteList = ingredienteList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.ingredientelayout,null,true);

        TextView nomeIngrediente = (TextView)listViewItem.findViewById(R.id.nomeIngrediente);
        TextView valorIngrediente = (TextView)listViewItem.findViewById(R.id.valorIngrediente);

        Ingrediente ingrediente = ingredienteList.get(position);

        nomeIngrediente.setText(ingrediente.getDescricaoIngrediente());
        valorIngrediente.setText("Valor: "+String.valueOf(ingrediente.getValorIngrediente()));

        return listViewItem;
    }
}
