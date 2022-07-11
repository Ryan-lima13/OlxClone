package activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rlds.olxclone.R;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import helper.CongiguracaoFirebase;
import helper.Permissoes;
import model.Anucio;

public class CadastroAnuciosActivity2 extends AppCompatActivity implements View.OnClickListener {
    private EditText campoTitulo, campoDescricao;
    private CurrencyEditText campoValor;
    private MaskEditText campoTelefone;
    private Spinner spinnerEstado, spinnerCategoria;
    private Anucio anucio;
    private StorageReference storage;
    private ImageView imageView1,imageView2,imageView3;
    private  String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private List<String> listaFotosREcuperadas = new ArrayList<>();
    private List<String> listaUrlFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anucios2);
        // validar permissões
        Permissoes.validarPermissoes(permissoes, this,1);


        // configurações iniciais
        storage = CongiguracaoFirebase.getStorageReference();
        campoDescricao = findViewById(R.id.editDescricao);
        campoTitulo = findViewById(R.id.editTitulo);
        campoValor = findViewById(R.id.edittextValor);
        campoTelefone = findViewById(R.id.editTelefone);
        // configurar localidade para portugues pt -Br
        Locale locale = new Locale( "pt","BR");
        campoValor.setLocale(locale);
        imageView1 = findViewById(R.id.imageCadastro1);
        imageView2 = findViewById(R.id.imageCadastro2);
        imageView3 = findViewById(R.id.imageCadastro3);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        // carregar spiner
        carregarDadosSpinner();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageCadastro1:
                escolherImagem(1);
                break;
            case R.id.imageCadastro2:
                escolherImagem(2);
                break;
            case R.id.imageCadastro3:
                escolherImagem(3);
                break;

        }
    }

    public  void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            // Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();
            // configurar imagem no ImageView
            if(requestCode ==1){
                imageView1.setImageURI(imagemSelecionada);

            }else if(requestCode == 2){
                imageView2.setImageURI(imagemSelecionada);
            }else if(resultCode == 3){
                imageView3.setImageURI(imagemSelecionada);
            }
            listaFotosREcuperadas.add(caminhoImagem);


        }
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

    private void carregarDadosSpinner(){
        // configurar spinner estados
        String[] estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);

        // configurar spinner categoria
        String[] categoria = getResources().getStringArray(R.array.categoria);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoria);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spinnerCategoria.setAdapter(adapterCategoria);



    }
    public  void validarDadosAnucios(View view){
         anucio = configurarAnucios();

        if(listaFotosREcuperadas.size()!= 0){
            if(!anucio.getEstado().isEmpty()){
                if(!anucio.getCategoria().isEmpty()){
                    if(!anucio.getTitulo().isEmpty()){
                        if(!anucio.getValor().isEmpty() && !anucio.getValor().equals("0")){
                            if(!anucio.getTelefone().isEmpty()){
                                if(!anucio.getDescricao().isEmpty()){
                                    // salvar anucios
                                    salvarAnucio();

                                }else {
                                    exibirMensagemErro("Preencha o campo descrição!");
                                }

                            }else {
                                exibirMensagemErro("Preencha o campo telefone");

                            }

                        }else {
                            exibirMensagemErro("Preencha o campo valor!");
                        }

                    }else {
                        exibirMensagemErro("Preencha o campo titulo!");
                    }

                }else {
                    exibirMensagemErro("Preencha o campo categoria!");
                }

            }else {
                exibirMensagemErro("Preencha o campo estado!");

            }
            

        }else {
            exibirMensagemErro("Selecione ao menos uma foto!");


        }



    }
    private  void exibirMensagemErro(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }
    public  void salvarAnucio(){
        // salvar imagem no storage
        for (int i = 0;  i < listaFotosREcuperadas.size(); i++){
            String urlImagem = listaFotosREcuperadas.get(i);
            int tamanhoLista = listaFotosREcuperadas.size();
            salvarFotoStorage(urlImagem,tamanhoLista, i);
        }




    }
    private Anucio configurarAnucios(){
        String estado = spinnerEstado.getSelectedItem().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String valor = String.valueOf(campoValor.getRawValue());
        String telefone = campoTelefone.getText().toString();
        String descricao = campoDescricao.getText().toString();
         Anucio anucio = new Anucio();
        anucio.setEstado(estado);
        anucio.setCategoria(categoria);
        anucio.setTitulo(titulo);
        anucio.setValor(valor);
        anucio.setTelefone(telefone);
        anucio.setDescricao(descricao);
        return  anucio;

    }
    private void salvarFotoStorage(String url,int totalFotos, int contador){
        // criar referencia no storage
        StorageReference imagemAnucio = storage.child("imagens")
                .child("anucios")
                .child(anucio.getIdAnucios())
                .child("imagem" + contador);
        // fazer upload do arquivo
        UploadTask uploadTask = imagemAnucio.putFile(Uri.parse(url));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               imagemAnucio.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                   @Override
                   public void onComplete(@NonNull Task<Uri> task) {
                       Uri url = task.getResult();
                       String urlConvertida = url.toString();
                       listaUrlFotos.add(urlConvertida);
                       if(totalFotos == listaUrlFotos.size()){
                           anucio.setFotos(listaUrlFotos);
                           anucio.salvar();

                       }



                   }
               });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload!");

            }
        });




    }



}