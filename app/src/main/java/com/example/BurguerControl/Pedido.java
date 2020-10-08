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

import com.example.BurguerControl.objetos.Bebida;
import com.example.BurguerControl.objetos.Burguer;
import com.example.BurguerControl.objetos.Ingrediente;
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
    private ArrayList<Bebida> listasBebida = new ArrayList<Bebida>();
    private ArrayList<Ingrediente> listasIngrediente = new ArrayList<Ingrediente>();
    private ArrayAdapter<Burguer> arrayAdapterBurguer;
    private ArrayAdapter<Bebida> arrayAdapterBebida;
    private ArrayAdapter<Ingrediente> arrayAdapterIngrediente;

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
        popularSpinnerBurguer();
        popularSpinnerBebida();
        popularSpinnerIngrediente();
    }

    @Override
    protected void onResume() {
        inicializarFirebase();
        popularSpinnerBurguer();
        super.onResume();
    }

    @Override
    protected void onStart() {
        inicializarFirebase();
        popularSpinnerBurguer();
        super.onStart();
    }

    private void popularSpinnerIngrediente() {
        databaseReference.child("Ingrediente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasIngrediente.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Ingrediente ingrediente = objSnapshot.getValue(Ingrediente.class);
                    listasIngrediente.add(ingrediente);
                }
                arrayAdapterIngrediente = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasIngrediente);
                arrayAdapterIngrediente.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnAddIngrediente.setAdapter(arrayAdapterIngrediente);
                spnRemoveIngrediente.setAdapter(arrayAdapterIngrediente);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void popularSpinnerBebida() {
        databaseReference.child("Bebida").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasBebida.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Bebida bebida = objSnapshot.getValue(Bebida.class);
                    listasBebida.add(bebida);
                }
                arrayAdapterBebida = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasBebida);
                arrayAdapterBebida.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnBebida.setAdapter(arrayAdapterBebida);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void popularSpinnerBurguer() {
        databaseReference.child("Burguer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasBurguer.clear();
                for(DataSnapshot areaSnapshot:dataSnapshot.getChildren()){
                    Burguer burguer = areaSnapshot.getValue(Burguer.class);
                    listasBurguer.add(burguer);
                }
                arrayAdapterBurguer = new ArrayAdapter<Burguer>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasBurguer);
                arrayAdapterBurguer.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnBurguer.setAdapter(arrayAdapterBurguer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Pedido.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
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
