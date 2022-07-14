package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import helper.RecyclerItemClickListener;
import model.Anucio;

public class MeusAnuciosActivity2 extends AppCompatActivity {
    private FloatingActionButton floatingAdd;
    private RecyclerView recyclerViewMeusAnucios;
    private List<Anucio> anuncios = new ArrayList<>();
    private AdapterMeusAnucios adapterMeusAnucios;
    private DatabaseReference anunciosUsuarioRef;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rlds.olxclone.R.layout.activity_meus_anucios2);
        // configurações iniciais
        floatingAdd = findViewById(R.id.floatingAdd);
        recyclerViewMeusAnucios = findViewById(R.id.recyclerMeusAnucios);
        anunciosUsuarioRef = CongiguracaoFirebase.getDatabaseReference().child("meus_anucios")
                .child(CongiguracaoFirebase.getIdUsuario());

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastroAnuciosActivity2.class));


            }
        });
        // configurar recyclerView
        recyclerViewMeusAnucios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMeusAnucios.setHasFixedSize(true);
        adapterMeusAnucios = new AdapterMeusAnucios(anuncios,this);
        recyclerViewMeusAnucios.setAdapter(adapterMeusAnucios);
        // recuprar anúncios para o usuarios
        recuperarAnuncios();
        // adicionar evento de clique no recyclerview excluir
        recyclerViewMeusAnucios.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerViewMeusAnucios,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Anucio anunciosSelecionado = anuncios.get(position);
                                anunciosSelecionado.remover();
                                adapterMeusAnucios.notifyDataSetChanged();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }

                )
        );





    }



    private void recuperarAnuncios(){
        dialog = new SpotsDialog.Builder().setContext(this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();
        anunciosUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                anuncios.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    anuncios.add(ds.getValue(Anucio.class));
                }
                Collections.reverse(anuncios);
                adapterMeusAnucios.notifyDataSetChanged();

                dialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}