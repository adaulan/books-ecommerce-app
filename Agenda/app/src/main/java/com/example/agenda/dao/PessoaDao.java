package com.example.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agenda.modelo.Pessoa;

import java.util.ArrayList;

public class PessoaDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBPessoa.db";
    private static final int VERSION = 1;
    private static final String TABELA = "pessoa";
    private static final String ID = "id";
    private static final String LIVRO = "livro";
    private static final String QUANTIDADE = "quantidade";
    private static final String AUTOR = "autor";
    private static final String VALOR = "valor";
    private static final String EDITORA = "editora";



    public PessoaDao(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " ( " +
                " " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " " + LIVRO + " TEXT, " + QUANTIDADE + " INTEGER, " +
                " " + AUTOR + " TEXT, " + EDITORA + " TEXT, " + VALOR + " TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP  TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public long salvarPessoa(Pessoa p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(LIVRO, p.getLivro());
        values.put(QUANTIDADE, p.getQuantidade());
        values.put(AUTOR, p.getAutor());
        values.put(EDITORA, p.getEditora());
        values.put(VALOR, p.getValor());

        retornoDB = getWritableDatabase().insert(TABELA, null, values);
        return retornoDB;
    }

    public long alterarPessoa(Pessoa p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(LIVRO, p.getLivro());
        values.put(QUANTIDADE, p.getQuantidade());
        values.put(AUTOR, p.getAutor());
        values.put(EDITORA, p.getEditora());
        values.put(VALOR, p.getValor());

        String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().update(TABELA, values, "id=?", args);
        return retornoDB;
    }

    public long excluirPessoa(Pessoa p){
        long retornoDB;

        String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().delete(TABELA,"id=?", args);
        return retornoDB;
    }

    public ArrayList<Pessoa> selectAllPessoa(){

        String[] coluns = {ID, LIVRO, QUANTIDADE, AUTOR, EDITORA, VALOR};
        Cursor cursor = getWritableDatabase().query(TABELA, coluns, null, null,null,null,"upper(livro)",null);
        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        while(cursor.moveToNext()){
            Pessoa p = new Pessoa();
            p.setId(cursor.getInt(0));
            p.setLivro(cursor.getString(1));
            p.setQuantidade(cursor.getInt(2));
            p.setAutor(cursor.getString(3));
            p.setEditora(cursor.getString(4));
            p.setValor(cursor.getString(5));

            listPessoa.add(p);

        }

        return listPessoa;
    }

}
