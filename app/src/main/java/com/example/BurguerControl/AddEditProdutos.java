package com.example.BurguerControl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.BurguerControl.objetos.Burguer;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddEditProdutos extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    EditText etDescricaoBurguer, etQuantBurguer,etValorBurguer;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_produtos);
        etDescricaoBurguer = findViewById(R.id.edtNomeProduto);
        etQuantBurguer = findViewById(R.id.edtQuantidadeProduto);
        etValorBurguer = findViewById(R.id.edtValorProduto);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(AddEditProdutos.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
                Toast.makeText(AddEditProdutos.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, HamburguerActivity.class);
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
                Toast.makeText(AddEditProdutos.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    private void limparcampos() {
        etValorBurguer.setText("");
        etQuantBurguer.setText("");
        etDescricaoBurguer.setText("");
        Toast.makeText(AddEditProdutos.this,"Hambúrguer salvo com sucesso!",Toast.LENGTH_LONG).show();
    }

}
