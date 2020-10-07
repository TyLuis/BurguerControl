package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Pedido extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Spinner spnBurguer, spnAddIngrediente, spnRemoveIngrediente, spnBebida, spnMesa;
    private EditText quantBurguer, quantBebida, infoPedido;
    private Button addBurguer, addIngrediente, removeIngrediente, addBebida;
    private ArrayList<Burguer> listasBurguer = new ArrayList<Burguer>();
    private ArrayAdapter<Burguer> arrayAdapterBurguer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        spnBurguer = (Spinner) findViewById(R.id.spnProduto);
        spnAddIngrediente = (Spinner)findViewById(R.id.spnAdicao);
        spnRemoveIngrediente = (Spinner)findViewById(R.id.spnRemocao);
        spnBebida = (Spinner)findViewById(R.id.spnBebida);
        quantBurguer = (EditText)findViewById(R.id.edtProduto);
        quantBebida = (EditText)findViewById(R.id.edtBebida);
        infoPedido = (EditText)findViewById(R.id.edtPedido);
        addBurguer = (Button)findViewById(R.id.btAddBurguer);
        addIngrediente = (Button)findViewById(R.id.btAddAdicao);
        removeIngrediente = (Button)findViewById(R.id.btnRemocao);
        addBebida = (Button)findViewById(R.id.btAddBebida);
        spnMesa = (Spinner)findViewById(R.id.cbMesa);


        inicializarFirebase();
        popularSpinner();
    }

    private void popularSpinner() {
        databaseReference.child("Burguer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasBurguer.clear();
                for(DataSnapshot areaSnapshot:dataSnapshot.getChildren()){
                    String nomeBurguer = areaSnapshot.getValue(String.class);
                    Burguer burguer = areaSnapshot.getValue(Burguer.class);
                    listasBurguer.add(burguer);
                }
                arrayAdapterBurguer = new ArrayAdapter<Burguer>(Pedido.this, android.R.layout.simple_spinner_item, listasBurguer);
                arrayAdapterBurguer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnBurguer.setAdapter(arrayAdapterBurguer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Pedido.this);
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;
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
                Toast.makeText(Pedido.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        finish();
        Intent intent = new Intent(this, principalMenu.class);
        startActivity(intent);
    }
}
