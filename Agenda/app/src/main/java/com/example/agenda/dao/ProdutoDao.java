package com.example.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agenda.modelo.Produto;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

public class ProdutoDao implements Closeable {

    private static final String TABELA = "produto";
    private static final String ID = "id";
    private static final String LIVRO = "livro";
    private static final String QUANTIDADE = "quantidade";
    private static final String AUTOR = "autor";
    private static final String VALOR = "valor";
    private static final String EDITORA = "editora";

    private HelperDao dao;
    private Context context;



    public ProdutoDao(Context context) {
        this.context = context;
        dao = new HelperDao(context);
    }



    public long salvarProduto(Produto p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(LIVRO, p.getLivro());
        values.put(QUANTIDADE, p.getQuantidade());
        values.put(AUTOR, p.getAutor());
        values.put(EDITORA, p.getEditora());
        values.put(VALOR, p.getValor());

        retornoDB = dao.getWritableDatabase().insert(TABELA, null, values);
        return retornoDB;
    }



    public long alterarProduto(Produto p){

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(LIVRO, p.getLivro());
        values.put(QUANTIDADE, p.getQuantidade());
        values.put(AUTOR, p.getAutor());
        values.put(EDITORA, p.getEditora());
        values.put(VALOR, p.getValor());

        String[] args = {String.valueOf(p.getId())};
        retornoDB = dao.getWritableDatabase().update(TABELA, values, "id=?", args);
        return retornoDB;
    }

    public long excluirProduto(Produto p){
        long retornoDB;

        String[] args = {String.valueOf(p.getId())};
        retornoDB = dao.getWritableDatabase().delete(TABELA,"id=?", args);
        return retornoDB;
    }

    public ArrayList<Produto> selectAllProduto(){

        String[] coluns = {ID, LIVRO, QUANTIDADE, AUTOR, EDITORA, VALOR};
        Cursor cursor = dao.getWritableDatabase().query(TABELA, coluns, null, null,null,null,"upper(livro)",null);
        ArrayList<Produto> listProduto = new ArrayList<Produto>();

        while(cursor.moveToNext()){
            Produto p = new Produto();
            p.setId(cursor.getInt(0));
            p.setLivro(cursor.getString(1));
            p.setQuantidade(cursor.getInt(2));
            p.setAutor(cursor.getString(3));
            p.setEditora(cursor.getString(4));
            p.setValor(cursor.getString(5));

            listProduto.add(p);

        }

        return listProduto;
    }

    private Produto criaProdutoCursor(Cursor cursor) {

        Produto produto = new Produto();

        produto.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        produto.setLivro(cursor.getString(cursor.getColumnIndex(LIVRO)));
        produto.setQuantidade(cursor.getInt(cursor.getColumnIndex(QUANTIDADE)));
        produto.setAutor(cursor.getString(cursor.getColumnIndex(AUTOR)));
        produto.setEditora(cursor.getString(cursor.getColumnIndex(EDITORA)));
        produto.setValor(cursor.getString(cursor.getColumnIndex(VALOR)));
        return produto;
    }

    public Produto recuperaProduto(Long id) {

        Cursor cursor = dao.getReadableDatabase().rawQuery("Select * from " + TABELA + " where " + ID + " = ?", new String[]{id.toString()});
        cursor.moveToNext();
        Produto produto = criaProdutoCursor(cursor);

        cursor.close();
        return produto;
    }

    @Override
    public void close(){
        dao.close();
    }
}
