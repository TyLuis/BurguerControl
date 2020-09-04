package com.example.BurguerControl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class principalMenu extends AppCompatActivity {
    private FirebaseAuth autentica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);
        autentica = FirebaseAuth.getInstance();
    }

    public void vaiGerencia(View view){
        Intent intent = new Intent(this,gerencia.class);
        startActivity(intent);
    }

    public void vaiCaixa(View view){
        Intent intent = new Intent(this,caixa.class);
        startActivity(intent);
    }

    public void vaiPedido(View view){
        Intent intent = new Intent(this,Pedido.class);
        startActivity(intent);
    }

    public void vaiCozinha (View view){
        Intent intent = new Intent(this, cozinha.class);
        startActivity(intent);
    }
}
