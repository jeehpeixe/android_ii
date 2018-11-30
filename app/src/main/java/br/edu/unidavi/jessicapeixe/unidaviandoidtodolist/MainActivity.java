package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BeersAdapter adapter = new BeersAdapter(new BeersAdapter.OnBeerClickListener() {
        @Override
        public void onClick(Beer beer) {
            Intent intent = new Intent(getApplicationContext(), BeerDetailActivity.class);
            intent.putExtra("beer", beer);
            startActivity(intent);
        }
    });

    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DataBaseHelper(this);

        RecyclerView beerList = findViewById(R.id.beer_list);
        beerList.setLayoutManager(new LinearLayoutManager(this));
        beerList.setAdapter(adapter);
        beerList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton botaoAdicionar = findViewById(R.id.botao_adicionar);
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewBeerActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Beer> beers = helper.fetchBeers();
        adapter.setup(beers);
    }
}
