package com.example.agenda.activity.Produto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.dao.ProdutoDao;
import com.example.agenda.modelo.Produto;

public class FormProduto extends AppCompatActivity {

    EditText editLivro, editQuantidade, editAutor, editEditora, editValor;
    Button btnVariavel;
    Produto produto, altproduto;
    ProdutoDao produtoDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        Intent i = getIntent();
        altproduto = (Produto) i.getSerializableExtra("produto-enviada");
        produto = new Produto();
        produtoDao = new ProdutoDao(FormProduto.this);

        editLivro = (EditText) findViewById(R.id.editLivro);
        editQuantidade = (EditText) findViewById(R.id.editQuantidade);
        editAutor = (EditText) findViewById(R.id.editAutor);
        editEditora = (EditText) findViewById(R.id.editEditora);
        editValor = (EditText) findViewById(R.id.editValor);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altproduto != null){
            btnVariavel.setText("Alterar");
            editLivro.setText(altproduto.getLivro());
            editQuantidade.setText(altproduto.getQuantidade() + "");
            editAutor.setText(altproduto.getAutor());
            editEditora.setText(altproduto.getEditora());
            editValor.setText(altproduto.getValor());

            produto.setId(altproduto.getId());

        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setLivro(editLivro.getText().toString());
                produto.setQuantidade(Integer.parseInt(editQuantidade.getText().toString()));
                produto.setAutor(editAutor.getText().toString());
                produto.setEditora(editEditora.getText().toString());
                produto.setValor(editValor.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = produtoDao.salvarProduto(produto);
                    produtoDao.close();
                    if(retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = produtoDao.alterarProduto(produto);
                    produtoDao.close();
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
