package com.example.agenda.activity.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.dao.ClienteDao;
import com.example.agenda.modelo.Cliente;

public class FormCliente extends AppCompatActivity {

    EditText editNome, editIdade, editEndereco, editTelefone;
    Button btnVariavel;
    Cliente cliente, altcliente;
    ClienteDao clienteDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cliente);

        Intent i = getIntent();
        altcliente = (Cliente) i.getSerializableExtra("cliente-enviada");
        cliente = new Cliente();
        clienteDao = new ClienteDao(FormCliente.this);

        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEndereco = (EditText) findViewById(R.id.editEndereco);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altcliente != null){
            btnVariavel.setText("Alterar");
            editNome.setText(altcliente.getNome());
            editIdade.setText(altcliente.getIdade() + "");
            editEndereco.setText(altcliente.getEndereco());
            editTelefone.setText(altcliente.getTelefone());

            cliente.setId(altcliente.getId());

        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente.setNome(editNome.getText().toString());
                cliente.setIdade(Integer.parseInt(editIdade.getText().toString()));
                cliente.setEndereco(editEndereco.getText().toString());
                cliente.setTelefone(editTelefone.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = clienteDao.salvarCliente(cliente);
                    clienteDao.close();
                    if(retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = clienteDao.alterarCliente(cliente);
                    clienteDao.close();
                    if(retornoDB == -1){
                        alert("Erro ao alterar");
                    }else{
                        alert("Atualização realizada com sucesso");
                    }
                }
                finish();
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

}
