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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.BurguerControl.objetos.Bebida;
import com.example.BurguerControl.objetos.Burguer;
import com.example.BurguerControl.objetos.Ingrediente;
import com.example.BurguerControl.objetos.OutroProduto;
import com.example.BurguerControl.objetos.Pedidos;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Pedido extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Spinner spnBurguer, spnAddIngrediente, spnRemoveIngrediente, spnBebida, spnMesa, spnOutro;
    private EditText quantBurguer, quantBebida, infoPedido, valorTotalPedido, quantOutro;
    private Button addBurguer, addIngrediente, removeIngrediente, addBebida;
    private ArrayList<Burguer> listasBurguer = new ArrayList<Burguer>();
    private ArrayList<Bebida> listasBebida = new ArrayList<Bebida>();
    private ArrayList<Ingrediente> listasIngrediente = new ArrayList<Ingrediente>();
    private ArrayList<OutroProduto> listasOutros = new ArrayList<OutroProduto>();
    private ArrayAdapter<Burguer> arrayAdapterBurguer;
    private ArrayAdapter<Bebida> arrayAdapterBebida;
    private ArrayAdapter<Ingrediente> arrayAdapterIngrediente;
    private ArrayAdapter<CharSequence> mesaAdapter;
    private ArrayAdapter<OutroProduto> arrayAdapterOutro;
    private Burguer burguerSelecionado;
    private Bebida bebidaSelecionado;
    private OutroProduto outroSelecionado;
    private Ingrediente ingreAddSelecionado, ingreRemoveSelecionado;
    private String spinnerBurguerText, infoFinal, spinnerIngreAddText, spinnerIngreRemoveText, spinnerBebidaText, mesa, spinnerOutroText;
    private Float valorTotal, ajuda;
    private Integer mesaNumero;

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
        valorTotalPedido = (EditText)findViewById(R.id.edtTotalPedido);
        spnOutro = (Spinner)findViewById(R.id.spnOutros);
        quantOutro = (EditText)findViewById(R.id.edtQuantOutros);
        valorTotal = Float.valueOf(0);
        ajuda = Float.valueOf(0);
        infoFinal ="";

        inicializarFirebase();
        popularSpinnerBurguer();
        popularSpinnerBebida();
        popularSpinnerIngrediente();
        popularSpinnerOutro();

        spnBurguer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                burguerSelecionado = (Burguer)parent.getItemAtPosition(position);
                spinnerBurguerText = burguerSelecionado.getDescricaoBurguer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAddIngrediente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingreAddSelecionado = (Ingrediente)parent.getItemAtPosition(position);
                spinnerIngreAddText = ingreAddSelecionado.getDescricaoIngrediente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnRemoveIngrediente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ingreRemoveSelecionado = (Ingrediente)parent.getItemAtPosition(position);
                spinnerIngreRemoveText = ingreRemoveSelecionado.getDescricaoIngrediente();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnBebida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bebidaSelecionado = (Bebida)parent.getItemAtPosition(position);
                spinnerBebidaText = bebidaSelecionado.getDescricaoBebida();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnOutro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                outroSelecionado = (OutroProduto)parent.getItemAtPosition(position);
                spinnerOutroText = outroSelecionado.getDescricaoOutro();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mesaAdapter = ArrayAdapter.createFromResource(this,R.array.mesa_array,android.R.layout.simple_spinner_item);
        mesaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMesa.setAdapter(mesaAdapter);

        spnMesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mesa = String.valueOf(parent.getItemAtPosition(position));
                mesaNumero = Integer.parseInt(mesa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        inicializarFirebase();
        popularSpinnerBurguer();
        popularSpinnerBebida();
        popularSpinnerIngrediente();
        popularSpinnerOutro();
        super.onResume();
    }

    @Override
    protected void onStart() {
        inicializarFirebase();
        popularSpinnerBurguer();
        popularSpinnerBebida();
        popularSpinnerIngrediente();
        popularSpinnerOutro();
        super.onStart();
    }

    private void popularSpinnerOutro() {
        databaseReference.child("OutroProduto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasOutros.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    OutroProduto outroProduto = objSnapshot.getValue(OutroProduto.class);
                    listasOutros.add(outroProduto);
                }
                arrayAdapterOutro = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item,listasOutros);
                arrayAdapterOutro.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnOutro.setAdapter(arrayAdapterOutro);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addBurguerPedido(View view){
        ajuda =burguerSelecionado.getValorBurguer()*Float.parseFloat(quantBurguer.getText().toString());
        valorTotal += ajuda;
        infoFinal += quantBurguer.getText().toString()+"x"+" Burguer: "+ spinnerBurguerText+"\n";
        infoPedido.setText(infoFinal);
        valorTotalPedido.setText(String.valueOf(valorTotal));
    }

    public void addIngredientePedido(View view){
        valorTotal = valorTotal + ingreAddSelecionado.getValorIngrediente();
        infoFinal += "+Ingrediente: "+spinnerIngreAddText+"\n";
        infoPedido.setText(infoFinal);
        valorTotalPedido.setText(String.valueOf(valorTotal));
    }

    public void removeIngredientePedido(View view){
        valorTotal = valorTotal - ingreRemoveSelecionado.getValorIngrediente();
        infoFinal += "-Ingrediente: "+spinnerIngreRemoveText+"\n";
        infoPedido.setText(infoFinal);
        valorTotalPedido.setText(String.valueOf(valorTotal));
    }

    public void addBebidaSelecionado(View view){
        ajuda = bebidaSelecionado.getValorBebida() *Float.parseFloat(quantBebida.getText().toString());
        valorTotal+=ajuda;
        infoFinal += quantBebida.getText().toString()+"x"+" Bebida: "+spinnerBebidaText+"\n";
        infoPedido.setText(infoFinal);
        valorTotalPedido.setText(String.valueOf(valorTotal));
    }

    public void salvarPedido(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Salvando pedido");
        msg.setMessage("Deseja realmente salvar esse pedido?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Pedidos pedidos = new Pedidos();
                pedidos.setIdPedido(UUID.randomUUID().toString());
                pedidos.setMesa(mesaNumero);
                pedidos.setInfoPedido(infoPedido.getText().toString());
                pedidos.setValorPedido(Float.parseFloat(valorTotalPedido.getText().toString()));

                databaseReference.child("Pedido").child(pedidos.getIdPedido()).setValue(pedidos);

                infoPedido.setText("");
                valorTotalPedido.setText("");
                quantBurguer.setText("");
                quantBebida.setText("");
                quantOutro.setText("");
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

     public void addOutroProduto(View view){
        ajuda = outroSelecionado.getValorOutro() * Float.parseFloat(quantOutro.getText().toString());
        valorTotal+=ajuda;
        infoFinal+=quantOutro.getText().toString()+"x "+spinnerOutroText+"\n";
        infoPedido.setText(infoFinal);
        valorTotalPedido.setText(String.valueOf(valorTotal));
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
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Saindo do pedido");
        msg.setMessage("Deseja realmente sair do pedido?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent intent = new Intent(Pedido.this, principalMenu.class);
                startActivity(intent);
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(Pedido.this, "Ação cancelada", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }
}
