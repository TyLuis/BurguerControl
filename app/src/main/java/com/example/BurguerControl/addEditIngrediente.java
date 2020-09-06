package com.example.BurguerControl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.BurguerControl.objetos.Ingrediente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class addEditIngrediente extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    EditText etDescricaoIngrediente;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingrediente);
        etDescricaoIngrediente = (EditText)findViewById(R.id.edtNomeIngrediente);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(addEditIngrediente.this);
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
                Toast.makeText(addEditIngrediente.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, ingredientes.class);
        startActivity(intent);
    }

    public void salvarIngrediente(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(addEditIngrediente.this);
        msg.setTitle("Salvando ingrediente");
        msg.setMessage("Deseja realmente salvar ingrediente?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(UUID.randomUUID().toString());
                ingrediente.setDescricaoIngrediente(etDescricaoIngrediente.getText().toString());

                databaseReference.child("Ingrediente").child(ingrediente.getIdIngrediente()).setValue(ingrediente);
                limparcampos();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(addEditIngrediente.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    private void limparcampos() {
        etDescricaoIngrediente.setText("");
        Toast.makeText(addEditIngrediente.this,"Ingrediente salvo com sucesso",Toast.LENGTH_LONG).show();
    }
}
