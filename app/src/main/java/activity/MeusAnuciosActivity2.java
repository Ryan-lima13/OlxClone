package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rlds.olxclone.R;

public class MeusAnuciosActivity2 extends AppCompatActivity {
    private FloatingActionButton floatingAdd;
    private RecyclerView recyclerViewMeusAnucios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rlds.olxclone.R.layout.activity_meus_anucios2);
        // configurações iniciais
        floatingAdd = findViewById(R.id.floatingAdd);
        recyclerViewMeusAnucios = findViewById(R.id.recyclerMeusAnucios);

        floatingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastroAnuciosActivity2.class));


            }
        });
    }
}