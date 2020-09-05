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

        if((email!="")&&(senha!="")){
            autentica.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String sucesso = "Usuário logado com sucesso!";
                        Log.i("signIn","Sucesso ao logar o usuário!");
                        Toast toast = Toast.makeText(getApplicationContext(),sucesso,Toast.LENGTH_SHORT);
                        toast.show();
                        startActivity(vaiMenu);
                    }else{
                        String falha = "Erro ao logar com o usuário!";
                        Log.i("signIn","Erro ao logar o usuário!");
                        Toast toast = Toast.makeText(getApplicationContext(),falha,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }else{
            String erroVazio="Por favor preencha os campos de senha e login corretamente!";
            Log.i("signIn","Login ou/e Senha vazios");
            Toast toast = Toast.makeText(getApplicationContext(),erroVazio,Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
