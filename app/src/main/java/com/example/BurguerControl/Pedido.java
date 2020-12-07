package com.example.BurguerControl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BurguerControl.adapter.BebidaPedidoAdapter;
import com.example.BurguerControl.adapter.BurguerPedidoAdapter;
import com.example.BurguerControl.adapter.IngredientePedidoAdapter;
import com.example.BurguerControl.adapter.OutrosPedidoAdapter;
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
    private Button addBurguer, addIngrediente, removeIngrediente, addBebida, salvarPedido, addOutros;
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
    private Integer mesaNumero, ajudaBurguer=0, ajudaBebida=0;

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
        addBurguer = (Button)findViewById(R.id.btAddProduto);
        addIngrediente = (Button)findViewById(R.id.btAddAdicao);
        removeIngrediente = (Button)findViewById(R.id.btnRemocao);
        addBebida = (Button)findViewById(R.id.btAddBebida);
        spnMesa = (Spinner)findViewById(R.id.cbMesa);
        valorTotalPedido = (EditText)findViewById(R.id.edtTotalPedido);
        spnOutro = (Spinner)findViewById(R.id.spnOutros);
        quantOutro = (EditText)findViewById(R.id.edtQuantOutros);
        valorTotal = Float.valueOf(0);
        ajuda = Float.valueOf(0);
        infoFinal = "";
        salvarPedido = (Button)findViewById(R.id.btSalvar);
        addOutros = (Button)findViewById(R.id.btnAddOutros);

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

        quantBurguer.addTextChangedListener(pedidoTextWatcher);
        quantOutro.addTextChangedListener(pedidoTextWatcher);
        quantBebida.addTextChangedListener(pedidoTextWatcher);
        infoPedido.addTextChangedListener(pedidoTextWatcher);
        if(ajudaBurguer>0){

        }
    }

    private TextWatcher pedidoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String quantsBurguer = quantBurguer.getText().toString().trim();
            String quantsBebida = quantBebida.getText().toString().trim();
            String quantsOutro = quantOutro.getText().toString().trim();
            String info = infoPedido.getText().toString().trim();

            addBurguer.setEnabled(!quantsBurguer.isEmpty());
            addBebida.setEnabled(!quantsBebida.isEmpty());
            addOutros.setEnabled(!quantsOutro.isEmpty());
            salvarPedido.setEnabled(!info.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
                OutrosPedidoAdapter outrosPedidoAdapter = new OutrosPedidoAdapter(Pedido.this,listasOutros);
                outrosPedidoAdapter.setDropDownViewResource(R.layout.outrospedidolayout);
                /*arrayAdapterOutro = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item,listasOutros);
                arrayAdapterOutro.setDropDownViewResource(android.R.layout.simple_spinner_item);*/
                spnOutro.setAdapter(outrosPedidoAdapter);
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
        ajudaBurguer++;
        addIngrediente.setEnabled(true);
        removeIngrediente.setEnabled(true);
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
        ajudaBebida++;
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

                ajudaBurguer=0;
                ajudaBebida=0;
                infoPedido.setText("");
                valorTotalPedido.setText("");
                quantBurguer.setText("");
                quantBebida.setText("");
                quantOutro.setText("");
                addIngrediente.setEnabled(false);
                removeIngrediente.setEnabled(false);
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
                IngredientePedidoAdapter ingredientePedidoAdapter = new IngredientePedidoAdapter(Pedido.this,listasIngrediente);
                ingredientePedidoAdapter.setDropDownViewResource(R.layout.ingredientepedidolayout);
                /*arrayAdapterIngrediente = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasIngrediente);
                arrayAdapterIngrediente.setDropDownViewResource(android.R.layout.simple_spinner_item);*/
                spnAddIngrediente.setAdapter(ingredientePedidoAdapter);
                spnRemoveIngrediente.setAdapter(ingredientePedidoAdapter);
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
                BebidaPedidoAdapter bebidaPedidoAdapter = new BebidaPedidoAdapter(Pedido.this,listasBebida);
                bebidaPedidoAdapter.setDropDownViewResource(R.layout.bebidapedidolayout);
                /*arrayAdapterBebida = new ArrayAdapter<>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasBebida);
                arrayAdapterBebida.setDropDownViewResource(android.R.layout.simple_spinner_item);*/
                spnBebida.setAdapter(bebidaPedidoAdapter);
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
                BurguerPedidoAdapter burguerPedidoAdapter = new BurguerPedidoAdapter(Pedido.this,listasBurguer);
                burguerPedidoAdapter.setDropDownViewResource(R.layout.burguerpedidolayout);
                /*arrayAdapterBurguer = new ArrayAdapter<Burguer>(Pedido.this, android.R.layout.simple_spinner_dropdown_item, listasBurguer);
                arrayAdapterBurguer.setDropDownViewResource(android.R.layout.simple_spinner_item);*/
                spnBurguer.setAdapter(burguerPedidoAdapter);
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
