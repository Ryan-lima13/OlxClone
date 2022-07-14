package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rlds.olxclone.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.AdapterMeusAnucios;
import dmax.dialog.SpotsDialog;
import helper.CongiguracaoFirebase;
import model.Anucio;

public class AnuciosActivity2 extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private Button buttonRegiao, buttonCategoria;
    private RecyclerView recyclerViewAnunciosPublicos;
    private List<Anucio> listaAnuncios = new ArrayList<>();
    private AdapterMeusAnucios adapterMeusAnucios;
    private DatabaseReference anuncioPublicoRef;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rlds.olxclone.R.layout.activity_anucios2);
        // configurações iniciais
        anuncioPublicoRef = CongiguracaoFirebase.getDatabaseReference().child("anuncios");
        buttonCategoria = findViewById(R.id.buttonCategoria);
        buttonRegiao = findViewById(R.id.buttonRegiao);
        recyclerViewAnunciosPublicos = findViewById(R.id.recyclerAnuncios);
        autenticacao = CongiguracaoFirebase.getFirebaseAuth();

        recyclerViewAnunciosPublicos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnunciosPublicos.setHasFixedSize(true);
        adapterMeusAnucios = new AdapterMeusAnucios(listaAnuncios,this);
        recyclerViewAnunciosPublicos.setAdapter(adapterMeusAnucios);
        recuperarAnunciosPublicos();

    }
    public  void recuperarAnunciosPublicos(){
        dialog = new SpotsDialog.Builder().setContext(this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();
        listaAnuncios.clear();
        anuncioPublicoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot estados:snapshot.getChildren()){
                    for (DataSnapshot categorias:estados.getChildren()){
                        for (DataSnapshot anuncios: categorias.getChildren()){
                            Anucio anucio = anuncios.getValue(Anucio.class);
                            listaAnuncios.add(anucio);




                        }

                    }
                }
                Collections.reverse(listaAnuncios);
                adapterMeusAnucios.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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