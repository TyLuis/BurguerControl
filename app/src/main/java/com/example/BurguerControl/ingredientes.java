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
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BurguerControl.adapter.IngredienteAdapter;
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
import java.util.UUID;

public class ingredientes extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    EditText etDescricaoIngrediente, etValorIngrediente;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listIngredientes;
    private ArrayList<Ingrediente> listaIngrediente = new ArrayList<Ingrediente>();
    private ArrayAdapter<Ingrediente> arrayAdapterIngrediente;
    Ingrediente ingredienteSelecionado;
    private Button btnSalvar, btnEditar, btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        etDescricaoIngrediente = (EditText)findViewById(R.id.edtNomeIngrediente);
        listIngredientes = (ListView)findViewById(R.id.lvIngredientes);
        etValorIngrediente = (EditText)findViewById(R.id.edtValorIngrediente);
        btnSalvar = (Button)findViewById(R.id.btAddIngrediente);
        btnEditar = (Button)findViewById(R.id.btEditarIngrediente);
        btnExcluir = (Button)findViewById(R.id.btExcluirIngrediente);

        inicializarFirebase();
        eventoDataBase();

        listIngredientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ingredienteSelecionado = (Ingrediente)parent.getItemAtPosition(position);
                etDescricaoIngrediente.setText(ingredienteSelecionado.getDescricaoIngrediente());
                etValorIngrediente.setText(String.valueOf(ingredienteSelecionado.getValorIngrediente()));
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnSalvar.setEnabled(false);
            }
        });

        etValorIngrediente.addTextChangedListener(ingredienteTextWatcher);
        etDescricaoIngrediente.addTextChangedListener(ingredienteTextWatcher);
    }

    private TextWatcher ingredienteTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String descri = etDescricaoIngrediente.getText().toString().trim();
            String valor = etValorIngrediente.getText().toString().trim();

            btnSalvar.setEnabled(!descri.isEmpty() && !valor.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onResume() {
        inicializarFirebase();
        eventoDataBase();
        super.onResume();
    }

    private void eventoDataBase() {
        databaseReference.child("Ingrediente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaIngrediente.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Ingrediente ingrediente = objSnapshot.getValue(Ingrediente.class);
                    listaIngrediente.add(ingrediente);
                }
                IngredienteAdapter ingredienteAdapter = new IngredienteAdapter(ingredientes.this,listaIngrediente);
                /*arrayAdapterIngrediente = new ArrayAdapter<Ingrediente>(ingredientes.this,android.R.layout.simple_list_item_single_choice,listaIngrediente);*/
                listIngredientes.setAdapter(ingredienteAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ingredientes.this,"Erro ao atualizar a lista de visualição!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ingredientes.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
      ;
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
                Toast.makeText(ingredientes.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, gerencia.class);
        startActivity(intent);
    }

    public void addIngrediente (View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(ingredientes.this);
        msg.setTitle("Salvando ingrediente");
        msg.setMessage("Deseja realmente salvar ingrediente?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(UUID.randomUUID().toString());
                ingrediente.setDescricaoIngrediente(etDescricaoIngrediente.getText().toString().trim());
                ingrediente.setValorIngrediente(Float.valueOf(etValorIngrediente.getText().toString().trim()));

                databaseReference.child("Ingrediente").child(ingrediente.getIdIngrediente()).setValue(ingrediente);
                limparcampos();
                Toast.makeText(ingredientes.this,"Ingrediente salvo com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ingredientes.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void editarIngrediente(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Editando ingrediente");
        msg.setMessage("Deseja realmente editar esse ingrediente?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Burguer b = new Burguer();
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(ingredienteSelecionado.getIdIngrediente());
                ingrediente.setDescricaoIngrediente(etDescricaoIngrediente.getText().toString().trim());
                ingrediente.setValorIngrediente(Float.valueOf(etValorIngrediente.getText().toString().trim()));
                databaseReference.child("Ingrediente").child(ingrediente.getIdIngrediente()).setValue(ingrediente);
                limparcampos();
                Toast.makeText(ingredientes.this,"Ingrediente editado com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ingredientes.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }

    public void excluirIngrediente(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Excluindo ingrediente");
        msg.setMessage("Deseja realmente excluir esse ingrediente?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setIdIngrediente(ingredienteSelecionado.getIdIngrediente());
                databaseReference.child("Ingrediente").child(ingrediente.getIdIngrediente()).removeValue();
                limparcampos();
                Toast.makeText(ingredientes.this,"Ingrediente excluído com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(ingredientes.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }

    private void limparcampos() {
        etDescricaoIngrediente.setText("");
        etValorIngrediente.setText("");
    }
}
