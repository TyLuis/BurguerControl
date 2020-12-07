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

public class OutrosAdapter extends ArrayAdapter<OutroProduto> {

    private Activity context;
    private List<OutroProduto> outroList;

    public OutrosAdapter(Activity context, List<OutroProduto> outroList){
        super(context, R.layout.outroslayout,outroList);
        this.context=context;
        this.outroList = outroList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.outroslayout,null,true);

        TextView nomeOutro = (TextView)listViewItem.findViewById(R.id.nomeOutro);
        TextView quantOutro = (TextView)listViewItem.findViewById(R.id.quantOutro);
        TextView valorOutro = (TextView)listViewItem.findViewById(R.id.valorOutro);

        OutroProduto outroProduto = outroList.get(position);

        nomeOutro.setText(outroProduto.getDescricaoOutro());
        quantOutro.setText("Quantidade: "+String.valueOf(outroProduto.getQuantidadeOutro()));
        valorOutro.setText("Valor: "+String.valueOf(outroProduto.getValorOutro()));

        return listViewItem;
    }
}
