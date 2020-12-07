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

import com.example.BurguerControl.adapter.BebidaAdapter;
import com.example.BurguerControl.objetos.Bebida;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BebidaActivity extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    ListView listBebida;
    private List<Bebida> listaBebida = new ArrayList<Bebida>();
    private ArrayAdapter<Bebida> arrayAdapterBebida;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etDescricaoBebida, etQuantidadeBebida, etValorBebida;
    Bebida bebidaSelecionada;
    private Button btnSalvar, btnEditar, btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebida);

        etDescricaoBebida = (EditText)findViewById(R.id.edtNomeBebida);
        etQuantidadeBebida = (EditText)findViewById(R.id.edtQuantidadeBebida);
        etValorBebida = (EditText)findViewById(R.id.edtValorBebida);
        listBebida = (ListView)findViewById(R.id.lvBebida);
        btnSalvar = (Button)findViewById(R.id.btAddBebida);
        btnEditar = (Button)findViewById(R.id.btEditarBebida);
        btnExcluir = (Button)findViewById(R.id.btExcluirBebida);

        inicializarFirebase();
        eventoDataBase();

        listBebida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bebidaSelecionada = (Bebida)parent.getItemAtPosition(position);
                etDescricaoBebida.setText(bebidaSelecionada.getDescricaoBebida());
                etQuantidadeBebida.setText(String.valueOf(bebidaSelecionada.getQuantidadeBebida()));
                etValorBebida.setText(String.valueOf(bebidaSelecionada.getValorBebida()));
                btnExcluir.setEnabled(true);
                btnEditar.setEnabled(true);
                btnSalvar.setEnabled(false);
            }
        });

        etDescricaoBebida.addTextChangedListener(bebidaTextWatcher);
        etQuantidadeBebida.addTextChangedListener(bebidaTextWatcher);
        etValorBebida.addTextChangedListener(bebidaTextWatcher);
    }

    private TextWatcher bebidaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String descri = etDescricaoBebida.getText().toString().trim();
            String quant = etQuantidadeBebida.getText().toString().trim();
            String valor = etValorBebida.getText().toString().trim();

            btnSalvar.setEnabled(!descri.isEmpty() && !quant.isEmpty() && !valor.isEmpty());
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(BebidaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void eventoDataBase() {
        databaseReference.child("Bebida").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBebida.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Bebida bebida = objSnapshot.getValue(Bebida.class);
                    listaBebida.add(bebida);
                }
                BebidaAdapter bebidaAdapter = new BebidaAdapter(BebidaActivity.this, listaBebida);
                /*arrayAdapterBebida = new ArrayAdapter<Bebida>(BebidaActivity.this,android.R.layout.simple_list_item_single_choice,listaBebida);*/
                listBebida.setAdapter(bebidaAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BebidaActivity.this,"Erro ao atualizar a lista de visualição!",Toast.LENGTH_LONG).show();
            }
        });
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
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void voltar(View view){
        Intent intent = new Intent(this, gerencia.class);
        startActivity(intent);
    }

    public void addBebida(View view){
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
                Toast.makeText(BebidaActivity.this,"Bebida salva com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        AlertDialog alert = msg.create();
        alert.show();
    }

    public void editarBebida(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Editando bebida");
        msg.setMessage("Deseja realmente editar essa bebida?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bebida bebi = new Bebida();
                bebi.setIdBebida(bebidaSelecionada.getIdBebida());
                bebi.setDescricaoBebida(etDescricaoBebida.getText().toString().trim());
                bebi.setQuantidadeBebida(Integer.valueOf(etQuantidadeBebida.getText().toString().trim()));
                bebi.setValorBebida(Float.valueOf(etValorBebida.getText().toString().trim()));

                databaseReference.child("Bebida").child(bebi.getIdBebida()).setValue(bebi);
                limparcampos();
                Toast.makeText(BebidaActivity.this,"Bebida editada com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }

    public void excluirBebida(View view){
        AlertDialog.Builder msg = new AlertDialog.Builder(this);
        msg.setTitle("Excluindo bebida");
        msg.setMessage("Deseja realmente excluir essa bebida?");
        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bebida bebida = new Bebida();
                bebida.setIdBebida(bebidaSelecionada.getIdBebida());
                databaseReference.child("Bebida").child(bebida.getIdBebida()).removeValue();
                limparcampos();
                Toast.makeText(BebidaActivity.this,"Bebida excluída com sucesso",Toast.LENGTH_LONG).show();
            }
        });
        msg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(BebidaActivity.this,"Ação cancelada",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = msg.create();
        alertDialog.show();
    }

    private void limparcampos() {
        etDescricaoBebida.setText("");
        etQuantidadeBebida.setText("");
        etValorBebida.setText("");

    }
}
