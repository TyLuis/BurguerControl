package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.BurguerControl.objetos.Bebida;
import com.example.BurguerControl.objetos.OutroProduto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class outrosProdutos extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etDescricaoOutro, etQuantidadeOutro,etValorOutro;
    ListView listOutros;
    private ArrayList<OutroProduto> listaOutros = new ArrayList<OutroProduto>();
    private ArrayAdapter<OutroProduto> arrayAdapterOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outros_produtos);

        etDescricaoOutro = (EditText)findViewById(R.id.edtNomeOutros);
        etQuantidadeOutro = (EditText)findViewById(R.id.edtQuantidadeOutros);
        etValorOutro = (EditText)findViewById(R.id.edtValorOutros);
        listOutros = (ListView)findViewById(R.id.lvOutrosProdutos);

        inicializarFirebase();
        eventoDataBase();
    }

    private void eventoDataBase() {
        databaseReference.child("OutroProduto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaOutros.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    OutroProduto outreProducts = objSnapshot.getValue(OutroProduto.class);
                    listaOutros.add(outreProducts);
                }
                arrayAdapterOutros = new ArrayAdapter<OutroProduto>(outrosProdutos.this,android.R.layout.simple_list_item_1,listaOutros);
                listOutros.setAdapter(arrayAdapterOutros);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(outrosProdutos.this,"Erro ao atualizar a lista de visualição!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(outrosProdutos.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void sairSistema(View view) {
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Saindo do sistema");
        msg.setMessage("Realmente tem certeza que deseja sair e deslogar do sistema?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                autentica.signOut();
                finishAffinity();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(outrosProdutos.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, gerencia.class);
        startActivity(intent);
    }

    public void addOutros(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Salvando outro produto");
        msg.setMessage("Deseja realmente salvar outro produto");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OutroProduto outroProduto = new OutroProduto();
                outroProduto.setIdOutroProduto(UUID.randomUUID().toString());
                outroProduto.setDescricaoOutro(etDescricaoOutro.getText().toString());
                outroProduto.setQuantidadeOutro(Integer.valueOf(etQuantidadeOutro.getText().toString()));
                outroProduto.setValorOutro(Float.valueOf(etValorOutro.getText().toString()));

                databaseReference.child("OutroProduto").child(outroProduto.getIdOutroProduto()).setValue(outroProduto);
                limparcampos();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(outrosProdutos.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    private void limparcampos() {
        etDescricaoOutro.setText("");
        etValorOutro.setText("");
        etQuantidadeOutro.setText("");
        Toast.makeText(outrosProdutos.this,"Outro produto salvo com sucesso",Toast.LENGTH_LONG).show();
    }
}
