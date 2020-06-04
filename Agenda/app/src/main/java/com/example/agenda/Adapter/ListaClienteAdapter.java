package com.example.agenda.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.modelo.Cliente;

import java.util.List;

public class ListaClienteAdapter extends BaseAdapter {

    private Context contexct;
    private List<Cliente> listCliente;

    public ListaClienteAdapter(Context contexct, List<Cliente> listCliente) {
        this.contexct = contexct;
        this.listCliente = listCliente;
    }

    @Override
    public int getCount() {
        return listCliente.size();
    }

    @Override
    public Object getItem(int position) {
        return listCliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(contexct, R.layout.item_cliente, null);

        TextView tvNome = (TextView)v.findViewById(R.id.textNome);
        TextView tvIdade = (TextView)v.findViewById(R.id.textIdade);
        TextView tvEndereço = (TextView)v.findViewById(R.id.textEndereço);
        TextView tvTelefone = (TextView)v.findViewById(R.id.textTelefone);

        tvNome.setText(listCliente.get(position).getNome());
        tvIdade.setText("Idade: " + String.valueOf(listCliente.get(position).getIdade()) + " anos");
        tvEndereço.setText("Endereço: " + listCliente.get(position).getEndereco());
        tvTelefone.setText("Telefone: " + listCliente.get(position).getTelefone());

        v.setTag(listCliente.get(position).getId());

        return v;
    }
}
