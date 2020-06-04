package com.example.agenda.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.modelo.Venda;

import java.util.List;

public class ListaVendaAdapter extends BaseAdapter {

    private Context contexct;
    private List<Venda> listVenda;

    public ListaVendaAdapter(Context context, List<Venda> listVenda) {
        this.contexct = context;
        this.listVenda = listVenda;
    }



    @Override
    public int getCount() {
        return listVenda.size();
    }

    @Override
    public Object getItem(int position) {
        return listVenda.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(contexct, R.layout.activity_item_venda, null);



        v.setTag(listVenda.get(position).getId());

        return v;
    }
}
