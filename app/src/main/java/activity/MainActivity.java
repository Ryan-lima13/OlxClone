package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.ktx.Firebase;
import com.rlds.olxclone.R;

import helper.CongiguracaoFirebase;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText campoEmail, campoSenha;
    private Button buttonAcessar;
    private Switch tipoAcesso;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // configurações iniciais
        campoSenha = findViewById(R.id.editSenha);
        campoEmail = findViewById(R.id.editEmail);
        buttonAcessar = findViewById(R.id.buttonAcessar);
        tipoAcesso = findViewById(R.id.tipoAcesso);
        autenticacao = CongiguracaoFirebase.getFirebaseAuth();

        buttonAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        if(tipoAcesso.isChecked()){// cadastro
                            autenticacao.createUserWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(
                                                MainActivity.this,
                                                "Cadastro feito com sucesse!",
                                                Toast.LENGTH_LONG
                                        ).show();

                                    }else {
                                        String erroExecao = "";
                                        try {
                                            throw  task.getException();

                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExecao = "Digite uma senha mais forte!";

                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            erroExecao = "Por favor, digite um email válido!";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            erroExecao = "Conta já cadastrada!";
                                        }catch (Exception e){
                                            erroExecao= "Erro ao cadastrar usuário:" + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(
                                                MainActivity.this,
                                                erroExecao,
                                                Toast.LENGTH_LONG
                                        ).show();

                                    }
                                }
                            });


                        }else {// login
                            autenticacao.signInWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(),AnuciosActivity2.class));


                                    }else {
                                        Toast.makeText(
                                                MainActivity.this,
                                                "Erro ao fazer login!",
                                                Toast.LENGTH_LONG
                                        ).show();

                                    }
                                }
                            });

                        }

                    }else {
                        Toast.makeText(
                                MainActivity.this,
                                "Preencha a senha!",
                                Toast.LENGTH_LONG
                        ).show();
                    }

                }else {
                    Toast.makeText(
                            MainActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }
}