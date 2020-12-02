package com.example.BurguerControl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BurguerControl.objetos.Conexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class criarConta extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    EditText etEmail;
    EditText etSenha, etConfirmaSenha;
    Spinner spnFuncao;
    private ArrayAdapter<CharSequence> funcaoAdapter;
    private Button btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
       etEmail = (EditText)findViewById(R.id.edtEmail);
       etSenha = (EditText)findViewById(R.id.edtSenha);
       etConfirmaSenha = (EditText)findViewById(R.id.edtConfirmaSenha);
       spnFuncao = (Spinner)findViewById(R.id.spnFuncao);
       btnCriar = (Button)findViewById(R.id.btSalvarConta);

       funcaoAdapter = ArrayAdapter.createFromResource(this,R.array.funcao_array,android.R.layout.simple_spinner_item);
       funcaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spnFuncao.setAdapter(funcaoAdapter);

       etEmail.addTextChangedListener(criarTextWatcher);
       etSenha.addTextChangedListener(criarTextWatcher);
       etConfirmaSenha.addTextChangedListener(criarTextWatcher);
    }

    private TextWatcher criarTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = etEmail.getText().toString().trim();
            String senha = etSenha.getText().toString().trim();
            String confirma = etConfirmaSenha.getText().toString().trim();

            btnCriar.setEnabled(!email.isEmpty() && !senha.isEmpty() && !confirma.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        etEmail.setText("");
        etSenha.setText("");
        etConfirmaSenha.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
    }

    public void criandoConta(View view){
        if((etSenha.getText().toString().trim())==(etConfirmaSenha.getText().toString().trim())){
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
        }else{
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Erro ao salvar");
            msgBox.setMessage("Os campos de senha não são iguais");
            AlertDialog alert = msgBox.create();
            alert.show();
        }
    }

    private void limparCampos() {
        etEmail.setText("");
        etSenha.setText("");
        etConfirmaSenha.setText("");
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
