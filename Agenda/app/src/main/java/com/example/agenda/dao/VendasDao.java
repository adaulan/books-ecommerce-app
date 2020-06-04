package com.example.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.agenda.modelo.ClienteQtd;
import com.example.agenda.modelo.ItemVenda;
import com.example.agenda.modelo.Venda;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class VendasDao implements Closeable {

    protected static final String VENDAS = "Vendas";
    protected static final String ID_CLIENTE = "idCliente";
    protected static final String ID = "id";
    private HelperDao dao;
    private Context context;

    public VendasDao(Context context) {
        this.context = context;
        dao = new HelperDao(context);
    }

    public long insere(Venda venda) {

        ContentValues values = new ContentValues();

        values.put(ID_CLIENTE, venda.getIdCliente());

        long idVenda = dao.getWritableDatabase().insert(VENDAS, null, values);

        VendasProdutoDao vendasProdutoDao = new VendasProdutoDao(context);

        for (ItemVenda item : venda.getItens()) {
            vendasProdutoDao.insere(item, idVenda);
        }

        vendasProdutoDao.close();
        return idVenda;
    }

    public List<Venda> listar() {

        List<Venda> vendas = new ArrayList<>();

        Cursor cursor = dao.getReadableDatabase().rawQuery("select * from " + VENDAS, null);

        while (cursor.moveToNext())
            vendas.add(criarVenda(cursor));

        cursor.close();
        return vendas;
    }

    private Venda criarVenda(Cursor cursor) {

        Venda venda = new Venda();

        venda.setId(cursor.getLong(cursor.getColumnIndex(ID)));
        venda.setIdCliente(cursor.getLong(cursor.getColumnIndex(ID_CLIENTE)));

        VendasProdutoDao dao = new VendasProdutoDao(context);
        List<ItemVenda> itensDaVenda = dao.getItensDaVenda(venda.getId());

        venda.setItens(itensDaVenda);

        return venda;
    }

    public Venda buscaVendaPelo(Long id) {

        String sql = "Select * from " + VENDAS + " where id = ?";

        Cursor cursor = dao.getReadableDatabase().rawQuery(sql, new String[]{id.toString()});
        cursor.moveToNext();

        Venda venda = criarVenda(cursor);

        cursor.close();
        return venda;
    }

    @Override
    public void close() {

        dao.close();
    }

    public List<ClienteQtd> listarClienteQtdVenda() {

        List<ClienteQtd> list = new ArrayList<>();

        Cursor cursor = dao.getReadableDatabase().rawQuery("Select " + ID_CLIENTE + ", count(" + ID_CLIENTE + ") as qtd from " + VENDAS + " group by " + ID_CLIENTE, null);

        while (cursor.moveToNext()) {
            ClienteQtd clienteQtd = new ClienteQtd();
            clienteQtd.setId(cursor.getLong(cursor.getColumnIndex(ID_CLIENTE)));
            clienteQtd.setQtd(cursor.getLong(cursor.getColumnIndex("qtd")));
            list.add(clienteQtd);
        }

        cursor.close();

        return list;
    }

}
