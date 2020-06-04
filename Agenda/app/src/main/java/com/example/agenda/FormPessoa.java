package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.dao.PessoaDao;
import com.example.agenda.modelo.Pessoa;

public class FormPessoa extends AppCompatActivity {

    EditText editLivro, editQuantidade, editAutor, editEditora, editValor;
    Button btnVariavel;
    Pessoa pessoa, altpessoa;
    PessoaDao pessoaDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pessoa);

        Intent i = getIntent();
        altpessoa = (Pessoa) i.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaDao = new PessoaDao(FormPessoa.this);

        editLivro = (EditText) findViewById(R.id.editLivro);
        editQuantidade = (EditText) findViewById(R.id.editQuantidade);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditora = (EditText) findViewById(R.id.editEditora);
        editValor = (EditText) findViewById(R.id.editValor);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altpessoa != null){
            btnVariavel.setText("Alterar");
            editLivro.setText(altpessoa.getLivro());
            editQuantidade.setText(altpessoa.getQuantidade() + "");
            editAutor.setText(altpessoa.getAutor());
            editEditora.setText(altpessoa.getEditora());
            editValor.setText(altpessoa.getValor());

            pessoa.setId(altpessoa.getId());

        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setLivro(editLivro.getText().toString());
                pessoa.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));
                pessoa.setAutor(editAutor.getText().toString());
                pessoa.setEditora(editEditora.getText().toString());
                pessoa.setValor(editValor.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = pessoaDao.salvarPessoa(pessoa);
                    pessoaDao.close();
                    if(retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = pessoaDao.alterarPessoa(pessoa);
                    pessoaDao.close();
                    if(retornoDB == -1){
                        alert("Erro ao alterar");
                    }else{
                        alert("Ataulização realizada com sucesso");
                    }
                }
                finish();
            }
        });

    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
