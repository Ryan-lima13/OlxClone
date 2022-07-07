package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.rlds.olxclone.R;
import com.santalu.maskara.widget.MaskEditText;

import java.util.Currency;
import java.util.Locale;

import helper.Permissoes;

public class CadastroAnuciosActivity2 extends AppCompatActivity {
    private EditText campoTitulo, campoDescricao;
    private CurrencyEditText campoValor;
    private MaskEditText campoTelefone;
    private  String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anucios2);
        // validar permissões
        Permissoes.validarPermissoes(permissoes, this,1);


        // configurações iniciais
        campoDescricao = findViewById(R.id.editDescricao);
        campoTitulo = findViewById(R.id.editTitulo);
        campoValor = findViewById(R.id.edittextValor);
        campoTelefone = findViewById(R.id.editTelefone);
        // configurar localidade para portugues pt -Br
        Locale locale = new Locale( "pt","BR");
        campoValor.setLocale(locale);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado: grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();


            }
        }
    }
    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas!");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}