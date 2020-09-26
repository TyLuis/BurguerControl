package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import com.example.BurguerControl.objetos.Conexao;
import com.example.BurguerControl.objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class criarConta extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    EditText etEmail;
    EditText etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
       etEmail = (EditText)findViewById(R.id.edtEmail);
       etSenha = (EditText)findViewById(R.id.edtSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
    }

    public void criandoConta(View view){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Criando conta");
        msgBox.setMessage("Tem certeza que deseja criar essa conta?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = etEmail.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(criarConta.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Usuario cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Erro ao cadastrar usuário!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(criarConta.this,"Ação Cancelada",Toast.LENGTH_LONG);
                dialog.dismiss();
            }
        });
        AlertDialog alert = msgBox.create();
        alert.show();
        limparCampos();
    }

    private void limparCampos() {
        etEmail.setText("");
        etSenha.setText("");
    }

    public void alertCancelar(View view){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Cancelar criação");
        msgBox.setMessage("Tem certeza que deseja sair da criação de conta?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(criarConta.this,"Ação Cancelada",Toast.LENGTH_LONG);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = msgBox.create();
        alert.show();
    }
}
