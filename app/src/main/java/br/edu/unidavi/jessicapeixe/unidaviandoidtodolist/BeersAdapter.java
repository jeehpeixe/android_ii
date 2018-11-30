package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BeersAdapter extends RecyclerView.Adapter<LineHolder> {

    private List<Beer> beers = new ArrayList<>();
    private final OnBeerClickListener listener;

    public BeersAdapter(OnBeerClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public LineHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new LineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LineHolder holder, int position) {
        final Beer beer = beers.get(position);
        holder.title.setText(beer.getMarca() + " - " + beer.getTipo());

        if (beer.getFoto().length() > 0) {
            holder.image.setImageBitmap(CarregadorDeFoto.carrega(beer.getFoto(), holder.image));
        }

        Locale ptBr = new Locale("pt", "BR");

        holder.subtitle.setText(String.valueOf(NumberFormat.getCurrencyInstance(ptBr).format(beer.getValorPago())));

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onClick(beer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    public void setup(List<Beer> beers) {
        this.beers.clear();
        this.beers.addAll(beers);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
        }
    }

    interface OnBeerClickListener{
        void onClick(Beer beer);
    }
}
