package br.edu.unidavi.jessicapeixe.unidaviandoidtodolist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class NewBeerActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper;
    private File file;
    private ImageView vwImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_beer);

        dbHelper = new DataBaseHelper(this);

        vwImagem = findViewById(R.id.img_new);

        addClickSalvar();
        addClickFoto();
    }

    private void addClickSalvar(){
        Button botaoSalvar = findViewById(R.id.botao_new);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edtTipo = findViewById(R.id.tipo_beer);
                String tipo = edtTipo.getText().toString();

                EditText edtMarca = findViewById(R.id.marca_beer);
                String marca = edtMarca.getText().toString();

                EditText edtNota = findViewById(R.id.nota_beer);
                String nota = edtNota.getText().toString();

                String foto = file != null && file.getAbsolutePath().length() > 0 ? file.getAbsolutePath() : "";

                EditText edtValor = findViewById(R.id.valor_beer);
                String valor = edtValor.getText().toString();

                if (!tipo.isEmpty() && !marca.isEmpty() && !nota.isEmpty()) {
                    dbHelper.createBeer(tipo, marca, "", Integer.parseInt(nota), foto, Double.parseDouble(valor));
                    file = null;
                    finish();
                }
            }
        });
    }

    private void addClickFoto() {

        vwImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    tirarFoto();
                }
                else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(NewBeerActivity.this, Manifest.permission.CAMERA)) {
                        new AlertDialog.Builder(NewBeerActivity.this)
                                .setTitle("Permitir Acesso Câmera")
                                .setPositiveButton("OK", null)
                                .show();
                    }

                    ActivityCompat.requestPermissions(NewBeerActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && permissions[0].equals(Manifest.permission.CAMERA)) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permissão Concedida!", Toast.LENGTH_SHORT).show();
                tirarFoto();
            }
        }
    }

    private void tirarFoto() {
        file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "photo_" + new Random() + ".jpg");
        Uri outPutDir = FileProvider.getUriForFile(NewBeerActivity.this, BuildConfig.APPLICATION_ID, file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutDir);

        startActivityForResult(intent, 1_000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1_000) {
            if (data != null && data.hasExtra("data")) {
                Bitmap thumbnail = data.getParcelableExtra("data");
                vwImagem.setImageBitmap(thumbnail);
            }
            else {
                vwImagem.setImageBitmap(CarregadorDeFoto.carrega(file.getPath(), vwImagem));
            }
        }
    }
}
