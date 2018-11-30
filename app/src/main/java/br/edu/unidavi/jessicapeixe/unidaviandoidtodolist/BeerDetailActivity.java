package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class BeerDetailActivity extends AppCompatActivity {

    private Beer beer;
    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);
        setTitle("Detalhes");

        helper = new DataBaseHelper(this);

        beer = getIntent().getParcelableExtra("beer");

        TextView edtTipo = findViewById(R.id.tipo_beer_vis);
        edtTipo.setText(beer.getTipo());

        TextView edtMarca = findViewById(R.id.marca_beer_vis);
        edtMarca.setText(beer.getMarca());

        TextView edtNota = findViewById(R.id.nota_beer_vis);
        edtNota.setText(String.valueOf(beer.getNota()));

        ImageView vwImagem = findViewById(R.id.foto_cerveja);
        if (beer.getFoto().length() > 0) {
            vwImagem.setImageBitmap(CarregadorDeFoto.carrega(beer.getFoto(), vwImagem));
        }

        Locale ptBr = new Locale("pt", "BR");
        TextView edtValor = findViewById(R.id.valor_beer_vis);
        edtValor.setText(String.valueOf(NumberFormat.getCurrencyInstance(ptBr).format(beer.getValorPago())));

        Button botaoDelete = findViewById(R.id.botton_delete);
        botaoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteBeer(beer);
                finish();
            }
        });
    }
}
