package com.example.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agenda.modelo.Cliente;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements Closeable {

    private static final String TABELA = "cliente";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String IDADE = "idade";
    private static final String ENDERECO = "endereco";
    private static final String TELEFONE = "telefone";

    private HelperDao dao;
    private Context context;

    public ClienteDao(Context context) {
        this.context = context;
        dao = new HelperDao(context);
    }


    public long salvarCliente(Cliente c){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME, c.getNome());
        values.put(IDADE, c.getIdade());
        values.put(ENDERECO, c.getEndereco());
        values.put(TELEFONE, c.getTelefone());

        retornoDB = dao.getWritableDatabase().insert(TABELA, null, values);
        return retornoDB;
    }

    public long alterarCliente(Cliente c){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME, c.getNome());
        values.put(IDADE, c.getIdade());
        values.put(ENDERECO, c.getEndereco());
        values.put(TELEFONE, c.getTelefone());

        String[] args = {String.valueOf(c.getId())};
        retornoDB = dao.getWritableDatabase().update(TABELA, values, "id=?", args);
        return retornoDB;
    }

    public long excluirCliente(Cliente c){
        long retornoDB;

        String[] args = {String.valueOf(c.getId())};
        retornoDB = dao.getWritableDatabase().delete(TABELA,"id=?", args);
        return retornoDB;
    }



    public ArrayList<Cliente> selectAllCliente(){

        String[] coluns = {ID, NOME, IDADE, ENDERECO, TELEFONE};
        Cursor cursor = dao.getWritableDatabase().query(TABELA, coluns, null, null,null,null,"upper(nome)",null);
        ArrayList<Cliente> listCliente = new ArrayList<Cliente>();

        while(cursor.moveToNext()){
            Cliente c = new Cliente();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setIdade(cursor.getInt(2));
            c.setEndereco(cursor.getString(3));
            c.setTelefone(cursor.getString(4));

            listCliente.add(c);

        }

        return listCliente;
    }

    @Override
    public void close(){
        dao.close();
    }

}
