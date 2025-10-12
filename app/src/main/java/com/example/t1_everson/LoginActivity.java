package com.example.t1_everson;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.BuscaActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario, edtSenha;
    private Button btnAcessar, btnFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        btnAcessar = findViewById(R.id.btnAcessar);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        btnAcessar.setOnClickListener(v -> {
            String usuario = edtUsuario.getText().toString();
            String senha = edtSenha.getText().toString();

            if (usuario.equals("admin") && senha.equals("123")) {
                Intent i = new Intent(LoginActivity.this, BuscaActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinalizar.setOnClickListener(v -> finishAffinity());
    }
}

