package com.example.BurguerControl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BurguerControl.objetos.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class principalMenu extends AppCompatActivity {
    private FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    private List<Usuario> usuarioList = new ArrayList<Usuario>();
    private String usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);

        inicializarFirebase();
        usuarioLogado = firebaseUser.getDisplayName();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(principalMenu.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void vaiGerencia(View view){
        if(usuarioLogado.equals("Gerente")){
            Intent intent = new Intent(this,gerencia.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            Toast.makeText(principalMenu.this,"Você não tem permissão para essa função!",Toast.LENGTH_LONG).show();
            AlertDialog alert = msg.create();
            alert.show();
        }
    }

    public void vaiCaixa(View view){
        Intent intent = new Intent(this,caixa.class);
        startActivity(intent);
    }

    public void vaiPedido(View view){
        if(usuarioLogado.equals("Garçom")){
            Intent intent = new Intent(this,Pedido.class);
            startActivity(intent);
        }else{
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            Toast.makeText(principalMenu.this,"Você não tem permissão para essa função!",Toast.LENGTH_LONG).show();
            AlertDialog alert = msg.create();
            alert.show();
        }

    }

    public void vaiCozinha (View view){
        if(usuarioLogado.equals("Garçom")){
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            Toast.makeText(principalMenu.this,"Você não tem permissão para essa função!",Toast.LENGTH_LONG).show();
            AlertDialog alert = msg.create();
            alert.show();
        }else{
            Intent intent = new Intent(this, cozinha.class);
            startActivity(intent);
        }

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
                Toast.makeText(principalMenu.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }
}
