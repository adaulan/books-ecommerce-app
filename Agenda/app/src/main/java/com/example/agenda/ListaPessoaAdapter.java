package com.example.agenda;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.modelo.Pessoa;

import java.util.List;

public class ListaPessoaAdapter extends BaseAdapter {

    private Context contexct;
    private List<Pessoa> listPessoa;

    public ListaPessoaAdapter(Context contexct, List<Pessoa> listPessoa) {
        this.contexct = contexct;
        this.listPessoa = listPessoa;
    }

    @Override
    public int getCount() {
        return listPessoa.size();
    }

    @Override
    public Object getItem(int position) {
        return listPessoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(contexct, R.layout.item_pessoa, null);

        TextView tvLivro = (TextView)v.findViewById(R.id.textLivro);
        TextView tvQuantidade = (TextView)v.findViewById(R.id.textQuantidade);
        TextView tvAutor = (TextView)v.findViewById(R.id.textAutor);
        TextView tvEditora = (TextView)v.findViewById(R.id.textEditora);
        TextView tvValor = (TextView)v.findViewById(R.id.textValor);

        tvLivro.setText(listPessoa.get(position).getLivro());
        tvQuantidade.setText("Quantidade: " + String.valueOf(listPessoa.get(position).getQuantidade()));
        tvAutor.setText("Autor: " + listPessoa.get(position).getAutor());
        tvEditora.setText("Editora: " + listPessoa.get(position).getEditora());
        tvValor.setText("Valor: R$ " + listPessoa.get(position).getValor());

        v.setTag(listPessoa.get(position).getId());

        return v;
    }
}
