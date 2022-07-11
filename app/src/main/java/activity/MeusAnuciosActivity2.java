package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import helper.CongiguracaoFirebase;
import model.Anucio;

public class MeusAnuciosActivity2 extends AppCompatActivity {
    private FloatingActionButton floatingAdd;
    private RecyclerView recyclerViewMeusAnucios;
    private List<Anucio> anuncios = new ArrayList<>();
    private AdapterMeusAnucios adapterMeusAnucios;
    private DatabaseReference anunciosUsuarioRef;

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
        recuperarAnuncios();




    }



    private void recuperarAnuncios(){
        anunciosUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                anuncios.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    anuncios.add(ds.getValue(Anucio.class));
                }

                adapterMeusAnucios.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}