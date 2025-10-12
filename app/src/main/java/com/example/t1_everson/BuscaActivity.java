package com.example.t1_everson;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.HorariosActivity;
import com.example.t1_everson.R;

public class BuscaActivity extends AppCompatActivity {

    private EditText edtOrigem, edtDestino;
    private CalendarView calendarViagem;
    private Button btnBuscarViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        edtOrigem = findViewById(R.id.edtOrigem);
        edtDestino = findViewById(R.id.edtDestino);
        calendarViagem = findViewById(R.id.calendarViagem);
        btnBuscarViagem = findViewById(R.id.btnBuscarViagem);

        btnBuscarViagem.setOnClickListener(v -> buscarViagem());
    }

    private void buscarViagem() {
        String origem = edtOrigem.getText().toString().trim();
        String destino = edtDestino.getText().toString().trim();

        if (origem.isEmpty() || destino.isEmpty()) {
            Toast.makeText(this, "Preencha a origem e o destino.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(BuscaActivity.this, HorariosActivity.class);
        intent.putExtra("ORIGEM", origem);
        intent.putExtra("DESTINO", destino);
        // A data selecionada pode ser passada aqui
        startActivity(intent);
    }
}
