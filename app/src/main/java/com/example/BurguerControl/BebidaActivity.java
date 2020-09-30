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
import com.example.BurguerControl.objetos.Burguer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BebidaActivity extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    ListView listBebida;
    private ArrayList<Bebida> listaBebida = new ArrayList<Bebida>();
    private ArrayAdapter<Bebida> arrayAdapterBebida;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etDescricaoBebida, etQuantidadeBebida, etValorBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida);

        etDescricaoBebida = (EditText)findViewById(R.id.edtNomeBebida);
        etQuantidadeBebida = (EditText)findViewById(R.id.edtQuantidadeBebida);
        etValorBebida = (EditText)findViewById(R.id.edtValorBebida);
        listBebida = (ListView)findViewById(R.id.lvBebida);

        inicializarFirebase();
        eventoDataBase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(BebidaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
       ;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void eventoDataBase() {
        databaseReference.child("Bebida").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBebida.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Bebida bebida = objSnapshot.getValue(Bebida.class);
                    listaBebida.add(bebida);
                }
                arrayAdapterBebida = new ArrayAdapter<Bebida>(BebidaActivity.this,android.R.layout.simple_list_item_1,listaBebida);
                listBebida.setAdapter(arrayAdapterBebida);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BebidaActivity.this,"Erro ao atualizar a lista de visualição!",Toast.LENGTH_LONG).show();
            }
        });
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
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, gerencia.class);
        startActivity(intent);
    }

    public void addBebida(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Salvando bebida");
        msg.setMessage("Deseja realmente salvar bebida?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bebida bebida = new Bebida();
                bebida.setIdBebida(UUID.randomUUID().toString());
                bebida.setDescricaoBebida(etDescricaoBebida.getText().toString());
                bebida.setQuantidadeBebida(Integer.valueOf(etQuantidadeBebida.getText().toString()));
                bebida.setValorBebida(Float.valueOf(etValorBebida.getText().toString()));

                databaseReference.child("Bebida").child(bebida.getIdBebida()).setValue(bebida);
                limparcampos();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    private void limparcampos() {
        etDescricaoBebida.setText("");
        etQuantidadeBebida.setText("");
        etValorBebida.setText("");
        Toast.makeText(BebidaActivity.this,"Bebida salva com sucesso",Toast.LENGTH_LONG).show();
    }
}
