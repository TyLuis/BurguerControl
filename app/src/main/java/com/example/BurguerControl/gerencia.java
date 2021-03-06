package com.example.BurguerControl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class gerencia extends AppCompatActivity {

    FirebaseAuth autentica = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerencia);
    }

    public void burguer(View view){
        Intent intent = new Intent(this, HamburguerActivity.class);
        startActivity(intent);
    }

    public void bebida(View view){
        Intent intent = new Intent(this, BebidaActivity.class);
        startActivity(intent);
    }

    public void outrosProdutos(View view){
        Intent intent = new Intent(this, outrosProdutos.class);
        startActivity(intent);
    }

    public void ingredientes(View view){
        Intent intent = new Intent(this, ingredientes.class);
        startActivity(intent);
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
                Toast.makeText(gerencia.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, principalMenu.class);
        startActivity(intent);
    }
}
