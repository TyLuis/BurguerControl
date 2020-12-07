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

public class BurguerAdapter extends ArrayAdapter<Burguer> {

    private Activity context;
    private List<Burguer> burguerList;

    public BurguerAdapter(Activity context, List<Burguer> burguerList){
        super(context, R.layout.burguerlayout,burguerList);
        this.context=context;
        this.burguerList=burguerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.burguerlayout,null,true);

        TextView nomeBurguer = (TextView)listViewItem.findViewById(R.id.edtNomeBurguer);
        TextView quantBurguer = (TextView)listViewItem.findViewById(R.id.valorIngrediente);
        TextView valorBurguer = (TextView)listViewItem.findViewById(R.id.edtValorBurguer);

        Burguer burguer = burguerList.get(position);

        nomeBurguer.setText(burguer.getDescricaoBurguer());
        quantBurguer.setText("Quantidade: "+String.valueOf(burguer.getEstoqueBurguer()));
        valorBurguer.setText("Valor: "+ String.valueOf(burguer.getValorBurguer()));

        return listViewItem;
    }
}
