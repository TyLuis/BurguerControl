package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    private FirebaseAuth autentica;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private EditText etNome = (EditText)findViewById(R.id.edtNomeUsuario);
    private EditText etEmail = (EditText)findViewById(R.id.edtEmail);
    private EditText etSenha = (EditText)findViewById(R.id.edtSenha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        autentica = FirebaseAuth.getInstance();
    }

    public void voltarInicio(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void criandoConta(final View view){
        final DatabaseReference usuarios = referencia.child("usuarios");
        final Usuario usuario = new Usuario();
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        final String email= etEmail.getText().toString();
        final String senha= etSenha.getText().toString();
        final String nomeUsuario = etNome.getText().toString();
        msgBox.setTitle("Criando conta");
        msgBox.setMessage("Tem certeza que quer criar a conta?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
                                    View view = null;
                                    voltarInicio(view);
                                }else{
                                    String falha = "Erro ao cadastrar o usuário!";
                                    Log.i("CreateUser","Erro ao cadastrar usuário!");
                                    Toast toast = Toast.makeText(getApplicationContext(),falha,Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etNome.setText("");
                etEmail.setText("");
                etSenha.setText("");
            }
        });
    }


    public void alertCancelar(View view){
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Cancelar criação");
        msgBox.setMessage("Tem certeza que deseja sair da criação de conta?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(intent);
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etNome.setText("");
                etEmail.setText("");
                etSenha.setText("");
            }
        });
    }
}
