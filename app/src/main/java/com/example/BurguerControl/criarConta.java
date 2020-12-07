package com.example.BurguerControl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BurguerControl.objetos.Conexao;
import com.example.BurguerControl.objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class criarConta extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    EditText etEmail;
    EditText etSenha, etConfirmaSenha;
    Spinner spnFuncao;
    private ArrayAdapter<CharSequence> funcaoAdapter;
    private Button btnCriar;
    private static FirebaseUser firebaseUser;
    private String funcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        inicializarFirebase();

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

       spnFuncao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               funcao = String.valueOf(parent.getItemAtPosition(position));
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
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

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(criarConta.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

        public void criandoConta(View view){
        final String x =etSenha.getText().toString().trim();
        final String y =etConfirmaSenha.getText().toString().trim();
        final String z = etEmail.getText().toString().trim();
        if(x.equals(y)){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Criando conta");
            msgBox.setMessage("Tem certeza que deseja criar essa conta?");
            msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    firebaseAuth.createUserWithEmailAndPassword(z,x).addOnCompleteListener(criarConta.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Usuario usuarios = new Usuario();
                                usuarios.setIdUsuario(UUID.randomUUID().toString());
                                usuarios.setEmailUsuario(z);
                                usuarios.setSenhaUsuario(x);
                                usuarios.setFuncao(funcao);
                                databaseReference.child("Usuarios").child(usuarios.getIdUsuario()).setValue(usuarios);
                                userProfile();
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

    private void userProfile(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(funcao).build();

            firebaseUser.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
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
