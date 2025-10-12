package com.example.t1_everson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PoltronaActivity extends AppCompatActivity implements View.OnClickListener {

    private final int TOTAL_POLTRONAS = 20;
    private TextView[] poltronas;
    private List<Integer> poltronasReservadas = new ArrayList<>();
    private double precoUnitario = 0.0;
    private TextView txtValorTotal;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    // Poltronas que já estão ocupadas por outros passageiros
    private static final List<Integer> POLTRONAS_OCUPADAS = Arrays.asList(5, 6, 15, 16);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout XML para esta atividade
        setContentView(R.layout.activity_poltrona);

        // Recupera o preço unitário da passagem da Intent, com um valor padrão
        precoUnitario = getIntent().getDoubleExtra("VIAGEM_PRECO", 58.80);

        // Associa as views do layout
        txtValorTotal = findViewById(R.id.txtValorTotal);
        Button btnConfirmarReserva = findViewById(R.id.btnConfirmarReserva);

        // Define o listener de clique para o botão de confirmação
        btnConfirmarReserva.setOnClickListener(v -> confirmarReserva());

        // Inicializa todas as views de poltrona e define seus estados
        inicializarPoltronas();

        // Atualiza o texto do valor total inicial
        atualizarValorTotal();
    }

    private void inicializarPoltronas() {
        poltronas = new TextView[TOTAL_POLTRONAS];
        for (int i = 0; i < TOTAL_POLTRONAS; i++) {
            int resId = getResources().getIdentifier("tvPoltrona_" + (i + 1), "id", getPackageName());
            TextView tv = findViewById(resId);

            if (tv != null) {
                poltronas[i] = tv;
                int numeroPoltrona = i + 1;

                if (POLTRONAS_OCUPADAS.contains(numeroPoltrona)) {
                    tv.setBackgroundColor(Color.RED);
                    tv.setEnabled(false); // Desabilita o clique
                } else {
                    tv.setBackgroundColor(Color.parseColor("#3F51B5"));
                    tv.setOnClickListener(this); // Define o listener de clique
                    tv.setTag(numeroPoltrona); // Armazena o número da poltrona na tag
                }
                tv.setText(String.valueOf(numeroPoltrona));
            }
        }
    }

    // Método chamado quando uma poltrona é clicada (implementação da interface View.OnClickListener)
    @Override
    public void onClick(View v) {
        TextView poltronaClicada = (TextView) v;
        int numeroPoltrona = (int) poltronaClicada.getTag();

        if (poltronasReservadas.contains(numeroPoltrona)) {
            poltronasReservadas.remove((Integer) numeroPoltrona);
            poltronaClicada.setBackgroundColor(Color.parseColor("#3F51B5"));
        } else {
            poltronasReservadas.add(numeroPoltrona);
            poltronaClicada.setBackgroundColor(Color.parseColor("#FFC107"));
        }

        atualizarValorTotal();
    }

    private void atualizarValorTotal() {
        double total = poltronasReservadas.size() * precoUnitario;
        txtValorTotal.setText("Valor Total: " + formatter.format(total));
    }

    private void confirmarReserva() {
        if (poltronasReservadas.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos uma poltrona.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DadosPassageiroActivity.class);
        intent.putExtra("PRECO_UNITARIO", precoUnitario);
        intent.putIntegerArrayListExtra("POLTRONAS_RESERVADAS", (ArrayList<Integer>) poltronasReservadas);
        startActivity(intent);
    }
}