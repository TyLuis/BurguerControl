package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    public void criandoConta(View view){
        DatabaseReference usuarios = referencia.child("usuarios");
        Usuario usuario = new Usuario();
        EditText etNome = (EditText)findViewById(R.id.edtNomeUsuario);
        EditText etEmail = (EditText)findViewById(R.id.edtEmail);
        EditText etSenha = (EditText)findViewById(R.id.edtSenha);
        String email= etEmail.getText().toString();
        String senha= etSenha.getText().toString();
        String nomeUsuario = etNome.getText().toString();

        usuario.setNomeUsuario(nomeUsuario);
        usuario.setEmailUsuario(email);
        usuario.setSenhaUsuario(senha);

        usuarios.push().setValue(usuario);
        autentica.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(criarConta.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser","Sucesso ao cadastrar usuário!");
                        }else{
                            Log.i("CreateUser","Erro ao cadastrar usuário!");
                        }
                    }
                });
    }
}
