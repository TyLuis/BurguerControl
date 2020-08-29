package com.example.BurguerControl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autentica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autentica = FirebaseAuth.getInstance();
    }

    public void criarContaChamada(View view){
        Intent intent = new Intent(this, criarConta.class);
        startActivity(intent);
    }

    public void logarUsuario(View view){
        final Intent vaiMenu = new Intent(this,principalMenu.class);
        EditText etEmail = (EditText)findViewById(R.id.edtLogin);
        EditText etSenha = (EditText)findViewById(R.id.edtSenha);
        String email = etEmail.getText().toString();
        String senha = etSenha.getText().toString();
        autentica.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Context context = getApplicationContext();
                if(task.isSuccessful()){
                    Log.i("signIn","Sucesso ao logar o usuário!");
                    CharSequence texto = "Usuário logado com sucesso!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,texto,duration);
                    startActivity(vaiMenu);
                }else{
                    Log.i("signIn","Erro ao logar o usuário!");
                    CharSequence texto = "Erro ao realizar o login!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context,texto,duration);
                }
            }
        });
    }
}
