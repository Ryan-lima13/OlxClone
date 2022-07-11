package model;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import helper.CongiguracaoFirebase;

public class Anucio {
    private String idAnucios;
    private  String estado;
    private String categoria;
    private  String titulo;
    private String valor;
    private String telefone;
    private String descricao;
    private List<String> fotos;

    public Anucio() {
        DatabaseReference anucioRef = CongiguracaoFirebase.getDatabaseReference()
                .child("meus_anucios");
        setIdAnucios(anucioRef.push().getKey());
    }
    public  void salvar(){
        String idUsuario = CongiguracaoFirebase.getIdUsuario();
        DatabaseReference anucioRef = CongiguracaoFirebase.getDatabaseReference()
                .child("meus_anucios");
       anucioRef.child(idUsuario)
               .child(getIdAnucios())
               .setValue(this);

    }


    public String getIdAnucios() {
        return idAnucios;
    }

    public void setIdAnucios(String idAnucios) {
        this.idAnucios = idAnucios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
