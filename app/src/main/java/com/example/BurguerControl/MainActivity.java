package com.example.BurguerControl;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etSenha;
    private FirebaseAuth autentica;
    private Button btnLogar;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onResume() {
        super.onResume();
        etSenha.setText("");
        etEmail.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autentica = FirebaseAuth.getInstance();

        etEmail=(EditText)findViewById(R.id.edtLogin);
        etSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btLogar);

        etSenha.addTextChangedListener(loginTextWatcher);
        etEmail.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = etEmail.getText().toString().trim();
            String senha = etSenha.getText().toString().trim();

            btnLogar.setEnabled((!senha.isEmpty()) && (!email.isEmpty()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void criarContaChamada(View view){
        Intent intent = new Intent(this, criarConta.class);
        startActivity(intent);
    }

    public void logarUsuario(View view){
        final Intent vaiMenu = new Intent(this,principalMenu.class);
        EditText etEmail = (EditText)findViewById(R.id.edtLogin);
        EditText etSenha = (EditText)findViewById(R.id.edtSenha);
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
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
