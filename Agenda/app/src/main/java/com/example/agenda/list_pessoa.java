package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agenda.dao.PessoaDao;
import com.example.agenda.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class list_pessoa extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaDao pessoaDao;
    List<Pessoa> arrayListPessoa;
    ListaPessoaAdapter arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pessoa);
        listVisivel = (ListView) findViewById(R.id.listPessoas);
        registerForContextMenu(listVisivel);

        btnNovoCadastro = (Button) findViewById(R.id.btn_NovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(list_pessoa.this, FormPessoa.class);
                startActivity(i);
            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

                Intent i = new Intent(list_pessoa.this, FormPessoa.class);
                i.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(i);
            }
        });

        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = (Pessoa) arrayAdapterPessoa.getItem(position);
                return false;
            }
        });

    }

    public void populaLista(){
        pessoaDao = new PessoaDao(list_pessoa.this);
        arrayListPessoa = pessoaDao.selectAllPessoa();
        pessoaDao.close();

        if(listVisivel != null){
            arrayAdapterPessoa = new ListaPessoaAdapter (getApplicationContext(), arrayListPessoa);
            listVisivel.setAdapter(arrayAdapterPessoa);
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
                pessoaDao = new PessoaDao(list_pessoa.this);
                retornoDB = pessoaDao.excluirPessoa(pessoa);
                pessoaDao.close();

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
