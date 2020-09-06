package com.example.BurguerControl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.BurguerControl.objetos.Bebida;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class addEditBebida extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etDescricaoBebida, etQuantidadeBebida, etValorBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_bebida);

        etDescricaoBebida = (EditText)findViewById(R.id.edtNomeBebida);
        etQuantidadeBebida = (EditText)findViewById(R.id.edtQuantidadeBebida);
        etValorBebida = (EditText)findViewById(R.id.edtValorBebida);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(addEditBebida.this);
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
                Toast.makeText(addEditBebida.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this,BebidaActivity.class);
        startActivity(intent);
    }

    public void salvarBebida(View view){
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
                Toast.makeText(addEditBebida.this,"Ação cancelada",Toast.LENGTH_LONG).show();
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
        Toast.makeText(addEditBebida.this,"Bebida salva com sucesso",Toast.LENGTH_LONG).show();
    }
}
