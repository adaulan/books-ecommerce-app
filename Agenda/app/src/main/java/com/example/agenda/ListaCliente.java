package com.example.agenda;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.dao.ClienteDao;
import com.example.agenda.modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListaCliente extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Cliente cliente;
    ClienteDao clienteDao;
    List<Cliente> arrayListCliente;
    ListaClienteAdapter arrayAdapterCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        listVisivel = (ListView) findViewById(R.id.listClientes);
        registerForContextMenu(listVisivel);

        btnNovoCadastro = (Button) findViewById(R.id.btn_NovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaCliente.this, FormCliente.class);
                startActivity(i);
            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente clienteEnviada = (Cliente) arrayAdapterCliente.getItem(position);

                Intent i = new Intent(ListaCliente.this, FormCliente.class);
                i.putExtra("cliente-enviada", clienteEnviada);
                startActivity(i);
            }
        });

        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cliente = (Cliente) arrayAdapterCliente.getItem(position);
                return false;
            }
        });

    }

    public void populaLista(){
        clienteDao = new ClienteDao(ListaCliente.this);
        arrayListCliente = clienteDao.selectAllCliente();
        clienteDao.close();

        if(listVisivel != null){
            arrayAdapterCliente = new ListaClienteAdapter (getApplicationContext(), arrayListCliente);
            listVisivel.setAdapter(arrayAdapterCliente);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        populaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Cliente");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                clienteDao = new ClienteDao(ListaCliente.this);
                retornoDB = clienteDao.excluirCliente(cliente);
                clienteDao.close();

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

