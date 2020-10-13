package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class HamburguerActivity extends AppCompatActivity{
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    ListView listBurguer;
    private List<Burguer> listaBurguer = new ArrayList<Burguer>();
    private ArrayAdapter<Burguer> arrayAdapterBurger;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etDescricaoBurguer, etQuantBurguer,etValorBurguer;
    Burguer burguerSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburguer);
        listBurguer = (ListView)findViewById(R.id.lvBurguer);
        etDescricaoBurguer = findViewById(R.id.edtNomeProduto);
        etQuantBurguer = findViewById(R.id.edtQuantidadeProduto);
        etValorBurguer = findViewById(R.id.edtValorProduto);

        inicializarFirebase();
        eventoDataBaseBurguer();

        listBurguer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                burguerSelecionado = (Burguer)parent.getItemAtPosition(position);
                etDescricaoBurguer.setText(burguerSelecionado.getDescricaoBurguer());
                etQuantBurguer.setText(String.valueOf(burguerSelecionado.getEstoqueBurguer()));
                etValorBurguer.setText(String.valueOf(burguerSelecionado.getValorBurguer()));
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(HamburguerActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onResume() {
        eventoDataBaseBurguer();
        inicializarFirebase();
        super.onResume();
    }

    private void eventoDataBaseBurguer() {
        databaseReference.child("Burguer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBurguer.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Burguer burguer = objSnapshot.getValue(Burguer.class);
                    listaBurguer.add(burguer);
                }
                arrayAdapterBurger = new ArrayAdapter<Burguer>(HamburguerActivity.this,android.R.layout.simple_list_item_single_choice,listaBurguer);
                listBurguer.setAdapter(arrayAdapterBurger);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HamburguerActivity.this,"Erro ao atualizar a lista de visualição!",Toast.LENGTH_LONG).show();
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
                Toast.makeText(HamburguerActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        finish();
        Intent intent = new Intent(this, gerencia.class);
        startActivity(intent);
    }

    public void salvarBurguer(View view){
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("Salvando hambúrguer");
            msg.setMessage("Deseja realmente salvar esse hambúrguer?");
            msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Burguer burguer = new Burguer();
                    burguer.setIdBurguer(UUID.randomUUID().toString());
                    burguer.setDescricaoBurguer(etDescricaoBurguer.getText().toString());
                    burguer.setEstoqueBurguer(Integer.valueOf(etQuantBurguer.getText().toString()));
                    burguer.setValorBurguer(Float.valueOf(etValorBurguer.getText().toString()));

                    databaseReference.child("Burguer").child(burguer.getIdBurguer()).setValue(burguer);
                    limparcampos();
                }
            });
            msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Toast.makeText(HamburguerActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                }
            });
            AlertDialog alert = msg.create();
            alert.show();
    }

    private void limparcampos() {
            etValorBurguer.setText("");
            etQuantBurguer.setText("");
            etDescricaoBurguer.setText("");
            Toast.makeText(HamburguerActivity.this,"Hambúrguer salvo com sucesso!",Toast.LENGTH_LONG).show();
    }

    public void editaBurguer(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Editando hambúrguer");
        msg.setMessage("Deseja realmente editar esse hambúrguer?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Burguer b = new Burguer();
                b.setIdBurguer(burguerSelecionado.getIdBurguer());
                b.setDescricaoBurguer(etDescricaoBurguer.getText().toString().trim());
                b.setEstoqueBurguer(Integer.valueOf(etQuantBurguer.getText().toString().trim()));
                b.setValorBurguer(Float.valueOf(etValorBurguer.getText().toString().trim()));

                databaseReference.child("Burguer").child(b.getIdBurguer()).setValue(b);
                limparcampos();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(HamburguerActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }

    public void excluirBurguer(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Excluindo hambúrguer");
        msg.setMessage("Deseja realmente excluir esse hambúrguer?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Burguer b = new Burguer();
                b.setIdBurguer(burguerSelecionado.getIdBurguer());
                databaseReference.child("Burguer").child(b.getIdBurguer()).removeValue();
                limparcampos();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(HamburguerActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }
}
