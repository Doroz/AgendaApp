package com.example.usuario.agendaapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;


public class Formulario extends AppCompatActivity {

    FormularioHelper helper;
    Contato contato;

    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;

    private Bitmap bitmap;

    private ImageView imagemContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            toolbar.setTitle("Editar Contato");
            toolbar.setNavigationIcon(R.drawable.imagem_voltar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(Formulario.this);
                }
            });
        }

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();
        this.contato = (Contato) intent.getSerializableExtra("contatoSelecionado");

        if (this.contato != null) {
            this.helper.populaFormulario(contato);
        }

        final Button botaoFoto = helper.getBotaoFoto();
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //carregaFotoCamera();
                alertaSourceImagem();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Contato contato = helper.getContatoDoFormulario();
                ContatoDAO dao = new ContatoDAO(Formulario.this);

                if (contato.getId() == null) {
                    dao.inserirContato(contato);
                } else {
                    dao.alteraContato(contato);
                }

                dao.close();

                finish();

                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregaFotoCamera() {
        fotoResource = true;
        localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg"; //Cria um caminho unico para se salvar a imagem, utilizando o currentTimeMillis.
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localArquivoFoto)));
        startActivityForResult(intentCamera, 123);
    }

    public void carregaFotoBiblioteca() {
        fotoResource = false;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 1);
    }

    public void alertaSourceImagem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleciona a fonte da imagem:");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregaFotoCamera();
            }
        });
        builder.setNegativeButton("Biblioteca", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                carregaFotoBiblioteca();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!fotoResource) {
            if (resultCode == -1) {
                InputStream stream = null;
                try {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    stream = getContentResolver().openInputStream(data.getData());
                    bitmap = BitmapFactory.decodeStream(stream);
                    imagemContato.setImageBitmap(bitmap);
                    helper.carregaImagem(this.localArquivoFoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (requestCode == TIRA_FOTO) {
                    if (resultCode == Activity.RESULT_OK) {
                        helper.carregaImagem(this.localArquivoFoto);
                    } else {
                        this.localArquivoFoto = null;
                    }
                }
            }
        }
    }
}
