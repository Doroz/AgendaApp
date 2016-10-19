package com.example.usuario.agendaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Usuario on 08/05/2016.
 */
public class FormularioHelper {

    private Contato contato;

    private EditText nome;
    private EditText email;
    private EditText endereco;
    private EditText site;
    private EditText telefone;

    private Button botaoFoto;

    private ImageView imagemContato;

    public FormularioHelper(Formulario activity) {

        this.contato = new Contato();

        this.nome = (EditText) activity.findViewById(R.id.nomeFormulario);
        this.email = (EditText) activity.findViewById(R.id.emailFormulario);
        this.endereco = (EditText) activity.findViewById(R.id.enderecoFormulario);
        this.site = (EditText) activity.findViewById(R.id.siteFormulario);
        this.telefone = (EditText) activity.findViewById(R.id.telefoneFormulario);

        this.botaoFoto = (Button) activity.findViewById(R.id.botaoFotoFormulario);
        this.imagemContato = (ImageView) activity.findViewById(R.id.imagemFormulario);

    }

    public Button getBotaoFoto() {
        return botaoFoto;
    }

    public Contato getContatoDoFormulario() {
        contato.setNome(nome.getText().toString());
        contato.setEmail(email.getText().toString());
        contato.setEndereco(endereco.getText().toString());
        contato.setSite(site.getText().toString());
        contato.setTelefone(telefone.getText().toString());

        contato.setFoto((String)imagemContato.getTag());

        return contato;
    }

    public void populaFormulario(Contato contato){
        nome.setText(contato.getNome());
        email.setText(contato.getEmail());
        endereco.setText(contato.getEndereco());
        site.setText(contato.getSite());
        telefone.setText(contato.getTelefone());

        imagemContato.setTag(contato.getFoto());
        carregaImagem(contato.getFoto());

        this.contato = contato;
    }

    public void carregaImagem(String localFoto){

        if (localFoto != null){
            Bitmap imagemFoto = BitmapFactory.decodeFile(localFoto);
            imagemContato.setImageBitmap(imagemFoto);
            imagemContato.setTag(localFoto);
        }

    }
}

