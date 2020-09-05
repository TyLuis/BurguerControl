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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class criarConta extends AppCompatActivity {
    FirebaseAuth autentica = FirebaseAuth.getInstance();
    /*DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    EditText etNome = (EditText)findViewById(R.id.edtNomeUsuario);
    EditText etEmail = (EditText)findViewById(R.id.edtEmail);
    EditText etSenha = (EditText)findViewById(R.id.edtSenha);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
    }

    public void voltarInicio(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

   /* public void criandoConta(View view){
        final DatabaseReference usuarios = referencia.child("usuarios");
        final Usuario usuario = new Usuario();
        final String email= etEmail.getText().toString();
        final String senha= etSenha.getText().toString();
        final String nomeUsuario = etNome.getText().toString();
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setEmailUsuario(email);
        usuario.setSenhaUsuario(senha);
        usuarios.push().setValue(usuario);
        autentica.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(criarConta.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String sucesso = "Usuário cadastrado com sucesso!";
                            Log.i("CreateUser","Sucesso ao cadastrar usuário!");
                            Toast toast = Toast.makeText(getApplicationContext(),sucesso,Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            String falha = "Erro ao cadastrar o usuário!";
                            Log.i("CreateUser","Erro ao cadastrar usuário!");
                            Toast toast = Toast.makeText(getApplicationContext(),falha,Toast.LENGTH_SHORT);
                            toast.show();
                                }
                            }
                        });
    }

    public void alertCancelar(View view){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Cancelar criação");
        msgBox.setMessage("Tem certeza que deseja sair da criação de conta?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
    }*/
}
