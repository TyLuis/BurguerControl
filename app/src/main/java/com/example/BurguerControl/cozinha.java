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
import com.example.BurguerControl.objetos.Pedidos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class cozinha extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ListView lstCozinha;
    private EditText edtMesa, edtInfo, edtValor;
    private ArrayList<Pedidos> listasPedido = new ArrayList<Pedidos>();
    private ArrayAdapter<Pedidos> arrayAdapterPedido;
    private Pedidos pedidoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cozinha);

        lstCozinha = (ListView)findViewById(R.id.livPedido);
        edtMesa = (EditText)findViewById(R.id.edtMesa);
        edtInfo = (EditText)findViewById(R.id.edtInfo);
        edtValor = (EditText)findViewById(R.id.edtValorPedido);

        inicializarFirebase();
        popularListViewPedido();

        lstCozinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pedidoSelecionado = (Pedidos)parent.getItemAtPosition(position);
                edtMesa.setText(String.valueOf(pedidoSelecionado.getMesa()));
                edtInfo.setText(pedidoSelecionado.getInfoPedido());
                edtValor.setText(String.valueOf(pedidoSelecionado.getValorPedido()));
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(cozinha.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void popularListViewPedido() {
        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasPedido.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Pedidos pedidos = objSnapshot.getValue(Pedidos.class);
                    listasPedido.add(pedidos);
                }
                arrayAdapterPedido = new ArrayAdapter<Pedidos>(cozinha.this,android.R.layout.simple_list_item_single_choice,listasPedido);
                lstCozinha.setAdapter(arrayAdapterPedido);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        inicializarFirebase();
        popularListViewPedido();
        super.onResume();
    }

    @Override
    protected void onStart() {
        inicializarFirebase();
        popularListViewPedido();
        super.onStart();
    }

    public void finalizarPedido(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Finalizando pedido");
        msg.setMessage("Deseja realmente finalizar esse pedido?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Pedidos p = new Pedidos();
                p.setIdPedido(pedidoSelecionado.getIdPedido());
                databaseReference.child("Pedido").child(p.getIdPedido()).removeValue();

                edtMesa.setText("");
                edtInfo.setText("");
                edtValor.setText("");
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(cozinha.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
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
                Toast.makeText(cozinha.this,"Ação cancelada",Toast.LENGTH_LONG).show();
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
