package com.example.agenda.activity.Produto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agenda.Adapter.ListaProdutoAdapter;
import com.example.agenda.R;
import com.example.agenda.dao.ProdutoDao;
import com.example.agenda.modelo.Produto;

import java.util.List;

public class list_produto extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Produto produto;
    ProdutoDao produtoDao;
    List<Produto> arrayListProduto;
    ListaProdutoAdapter arrayAdapterProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produto);
        listVisivel = (ListView) findViewById(R.id.listProdutos);
        registerForContextMenu(listVisivel);

        btnNovoCadastro = (Button) findViewById(R.id.btn_NovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_produto.this, FormProduto.class);
                startActivity(i);
            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoEnviada = (Produto) arrayAdapterProduto.getItem(position);

                Intent i = new Intent(list_produto.this, FormProduto.class);
                i.putExtra("produto-enviada", produtoEnviada);
                startActivity(i);
            }
        });

        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = (Produto) arrayAdapterProduto.getItem(position);
                return false;
            }
        });

    }

    public void populaLista(){
        produtoDao = new ProdutoDao(list_produto.this);
        arrayListProduto = produtoDao.selectAllProduto();
        produtoDao.close();

        if(listVisivel != null){
            arrayAdapterProduto = new ListaProdutoAdapter(getApplicationContext(), arrayListProduto);
            listVisivel.setAdapter(arrayAdapterProduto);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        populaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Livro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                produtoDao = new ProdutoDao(list_produto.this);
                retornoDB = produtoDao.excluirProduto(produto);
                produtoDao.close();

                if(retornoDB == -1){
                    alert("Erro de exclusão");
                }else{
                    alert("Registro excluido com sucesso!");
                }
                populaLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
