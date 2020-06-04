package com.example.agenda.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDao extends SQLiteOpenHelper {

    private static final String NOME = "LivrariaDB";
    private static final int VERSION = 1;

    public HelperDao(Context context) {
        super(context, NOME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlProduto = "Create table Produto (" +
                "id integer primary key AUTOINCREMENT," +
                "livro text not null, " +
                "quantidade int not null, " +
                "autor text not null, " +
                "editora text not null, " +
                "valor text not null );";

        String sqlCliente = "Create table Cliente (" +
                "id integer primary key AUTOINCREMENT," +
                "nome text not null, " +
                "idade int not null, " +
                "endereco text not null, " +
                "telefone text not null );";

        String sqlVendas = "Create table Vendas (" +
                " id integer primary key," +
                " idCliente integer not null, " +
                " data text not null, " +
                "FOREIGN KEY (idCliente) REFERENCES Cliente(id) );";

        String sqlVendasProduto = "Create table VendasProduto (" +
                " id integer primary key," +
                " idVenda integer not null, " +
                " idProduto integer not null, " +
                " quantidade integer not null, " +
                " valorUnitarioVendido real not null, " +
                " FOREIGN KEY (idVenda) REFERENCES Vendas(id)," +
                " FOREIGN KEY (idProduto) REFERENCES Produto(id) ) ;";

        db.execSQL(sqlCliente);
        db.execSQL(sqlProduto);
        db.execSQL(sqlVendasProduto);
        db.execSQL(sqlVendas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlProduto = "DROP TABLE IF EXISTS Produto;";
        db.execSQL(sqlProduto);

        String sqlCliente = "DROP TABLE IF EXISTS Cliente;";
        db.execSQL(sqlCliente);

        String sqlVendasProduto = "DROP TABLE IF EXISTS VendasProduto;";
        db.execSQL(sqlVendasProduto);

        String sqlVendas = "DROP TABLE IF EXISTS Vendas;";
        db.execSQL(sqlVendas);

        onCreate(db);
    }
}
