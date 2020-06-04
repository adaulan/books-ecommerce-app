package com.example.agenda.activity.Venda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.dao.ClienteDao;
import com.example.agenda.dao.ProdutoDao;
import com.example.agenda.dao.VendasDao;
import com.example.agenda.modelo.Cliente;
import com.example.agenda.modelo.ItemVenda;
import com.example.agenda.modelo.Produto;
import com.example.agenda.modelo.Venda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormVenda extends AppCompatActivity {


    Spinner listaClientes;
    Spinner listaProdutos;
    TextView nomeCliente;
    TextView nomeProduto;
    TextView qtdEstoque;
    EditText qtdVendida;
    TextView valorProduto;
    EditText valorVendido;
    ListView listaProdutosVendidos;
    Button add_venda;
    Button btn_menu;

    private Cliente cliente;
    private Produto produto;
    private Venda venda;

    private List<ItemVenda> itens = new ArrayList<>();
    private ArrayAdapter<ItemVenda> adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_venda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ClienteDao clienteDao = new ClienteDao(this);
        List<Cliente> clientes = clienteDao.selectAllCliente();

        listaClientes = (Spinner) findViewById(R.id.formulario_vendas_lista_clientes);
        listaProdutos = (Spinner) findViewById(R.id.formulario_vendas_lista_produtos);
        nomeCliente = (TextView) findViewById(R.id.formulario_vendas_nome_cliente);
        nomeProduto = (TextView) findViewById(R.id.formulario_vendas_nome_produto);
        qtdEstoque = (TextView) findViewById(R.id.formulario_vendas_quantidade_estoque);
        qtdVendida = (EditText) findViewById(R.id.formulario_vendas_quantidade_produto);
        valorProduto = (TextView) findViewById(R.id.formulario_vendas_preco_produto);
        valorVendido = (EditText) findViewById(R.id.formulario_vendas_preco_venda);
        listaProdutosVendidos = (ListView) findViewById(R.id.formulario_vendas_lista_produtos_vendidos);
        add_venda = (Button) findViewById(R.id.formulario_vendas_botao_add_produto);
        btn_menu = (Button) findViewById(R.id.formulario_menu_salvar);

        listaProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                selecionaProduto(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listaClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                selecionaCliente(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        add_venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionaProdutoNaLista();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        clienteDao.close();

        ProdutoDao produtoDao = new ProdutoDao(this);
        List<Produto> produtos = produtoDao.selectAllProduto();
        produtoDao.close();


        listaClientes.setAdapter(new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes));
        listaProdutos.setAdapter(new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, produtos));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itens);
        listaProdutosVendidos.setAdapter(adapter);
    }


    public void selecionaCliente(int position) {
        cliente = (Cliente) listaClientes.getItemAtPosition(position);
        nomeCliente.setText(cliente.getNome());
    }

    public void selecionaProduto(int position) {
        produto = (Produto) listaProdutos.getItemAtPosition(position);
        nomeProduto.setText(produto.getLivro());
        valorProduto.setText("R$ " + produto.getValor() + "  ");
    }

    public void adicionaProdutoNaLista() {
        try {
            Integer quantidadeVendida = Integer.valueOf(qtdVendida.getText().toString());
            Double precoDoProdutoVendido = Double.valueOf(valorVendido.getText().toString());



                ItemVenda itemVenda = new ItemVenda(produto, quantidadeVendida, precoDoProdutoVendido);

                itens.add(itemVenda);
                adapter.notifyDataSetChanged();

                valorVendido.setText("");
                qtdVendida.setText("");

        } catch (NumberFormatException e) {
            Log.d("NumberFormatException", e.toString());
            Toast.makeText(this, "Informe valores antes de adicionar na lista", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validaQtdVendida(Integer quantidadeVendida) {
        return !(quantidadeVendida > Integer.valueOf(qtdEstoque.getText().toString()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_salvar:
                if (validaCampos()) {
                    venda = criaVenda();
                    VendasDao vendasDao = new VendasDao(this);
                    vendasDao.insere(venda);
                    vendasDao.close();
                    finish();
                }
                return true;
            default:
                return false;
        }
    }

    private boolean validaCampos() {
        if (cliente == null) {
            Toast.makeText(this, "Você precisa de um cliente ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (itens.isEmpty()) {
            Toast.makeText(this, "Nenhum produto foi adicionado", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private Venda criaVenda() {
        venda = new Venda();
        venda.setItens(itens);
        venda.setIdCliente((long) cliente.getId());
        return venda;
    }


}
