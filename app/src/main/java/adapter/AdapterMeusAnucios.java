package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rlds.olxclone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Anucio;

public class AdapterMeusAnucios  extends RecyclerView.Adapter<AdapterMeusAnucios.MyViewHolder>{
    private List<Anucio> anucios;
    private Context context;
    public AdapterMeusAnucios(List<Anucio> anuncios, Context context) {
        this.anucios = anuncios;
        this.context = context;

    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_meusanuncios,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Anucio anucio = anucios.get(position);
        holder.titulo.setText(anucio.getTitulo());
        holder.valor.setText(anucio.getValor());

        // pegar a primeira imagem da lista
        List<String> urlFotos = anucio.getFotos();
        String urlCapa = urlFotos.get(0);
        Picasso.get().load(urlCapa).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return anucios.size();
    }

    public  class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView titulo, valor;
        ImageView foto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewTitulo);
            valor = itemView.findViewById(R.id.textViewPreco);
            foto = itemView.findViewById(R.id.imageViewMeusAnuncios);
        }
    }
}
