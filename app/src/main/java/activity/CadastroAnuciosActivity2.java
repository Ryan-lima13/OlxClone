package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.rlds.olxclone.R;

import java.util.Currency;
import java.util.Locale;

public class CadastroAnuciosActivity2 extends AppCompatActivity {
    private EditText campoTitulo, campoDescricao;
    private CurrencyEditText campoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anucios2);
        // configurações iniciais
        campoDescricao = findViewById(R.id.editDescricao);
        campoTitulo = findViewById(R.id.editTitulo);
        campoValor = findViewById(R.id.edittextValor);
        // configurar localidade para portugues pt -Br
        Locale locale = new Locale( "pt","BR");
        campoValor.setLocale(locale);



    }
    public  void salvarAnucio(View view){
        Long valor = campoValor.getRawValue();
        Log.d("valor","salvar anucio" + valor);

    }
}