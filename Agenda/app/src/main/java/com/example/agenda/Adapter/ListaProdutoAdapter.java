package com.example.agenda.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.modelo.Produto;

import java.util.List;

public class ListaProdutoAdapter extends BaseAdapter {

    private Context contexct;
    private List<Produto> listProduto;

    public ListaProdutoAdapter(Context contexct, List<Produto> listProduto) {
        this.contexct = contexct;
        this.listProduto = listProduto;
    }

    @Override
    public int getCount() {
        return listProduto.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(contexct, R.layout.item_produto, null);

        TextView tvLivro = (TextView)v.findViewById(R.id.textLivro);
        TextView tvQuantidade = (TextView)v.findViewById(R.id.textQuantidade);
        TextView tvAutor = (TextView)v.findViewById(R.id.textAutor);
        TextView tvEditora = (TextView)v.findViewById(R.id.textEditora);
        TextView tvValor = (TextView)v.findViewById(R.id.textValor);

        tvLivro.setText(listProduto.get(position).getLivro());
        tvQuantidade.setText("Quantidade: " + String.valueOf(listProduto.get(position).getQuantidade()));
        tvAutor.setText("Autor: " + listProduto.get(position).getAutor());
        tvEditora.setText("Editora: " + listProduto.get(position).getEditora());
        tvValor.setText("Valor: R$ " + listProduto.get(position).getValor());

        v.setTag(listProduto.get(position).getId());

        return v;
    }
}
