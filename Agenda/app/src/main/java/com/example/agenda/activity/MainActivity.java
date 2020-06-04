package com.example.agenda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agenda.R;
import com.example.agenda.activity.Cliente.ListaCliente;
import com.example.agenda.activity.Produto.list_produto;
import com.example.agenda.activity.Venda.FormVenda;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Cliente = (Button) findViewById(R.id.btn_Cliente);
        btn_Cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, list_produto.class);
                startActivity(i);
            }
        });

        Button btn_NovoCliente = (Button) findViewById(R.id.btn_NovoCliente);
        btn_NovoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaCliente.class);
                startActivity(i);
            }
        });

        Button btn_Venda = (Button) findViewById(R.id.btn_Venda);
        btn_Venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FormVenda.class);
                startActivity(i);
            }
        });



    }

}
