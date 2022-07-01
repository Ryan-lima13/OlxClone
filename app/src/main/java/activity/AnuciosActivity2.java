package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.rlds.olxclone.R;

import helper.CongiguracaoFirebase;

public class AnuciosActivity2 extends AppCompatActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rlds.olxclone.R.layout.activity_anucios2);
        // configurações iniciais
        autenticacao = CongiguracaoFirebase.getFirebaseAuth();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(autenticacao.getCurrentUser() == null){ //usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado,true);

        }else {// usuario logado
            menu.setGroupVisible(R.id.group_logado, true);

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_entrar:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_anucios:
                startActivity(new Intent(getApplicationContext(),MeusAnuciosActivity2.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}