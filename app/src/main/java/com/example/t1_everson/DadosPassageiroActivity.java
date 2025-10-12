package com.example.t1_everson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.PassagemVirtualActivity;
import com.example.t1_everson.R;
import com.example.t1_everson.model.Passageiro;

import java.util.ArrayList;
import java.util.List;

public class DadosPassageiroActivity extends AppCompatActivity {

    private List<Integer> poltronasReservadas;
    private List<Passageiro> listaPassageiros;
    private double precoUnitario;

    private LinearLayout containerPassageiros;
    private RadioGroup rgFormaPagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_passageiro);

        poltronasReservadas = getIntent().getIntegerArrayListExtra("POLTRONAS_RESERVADAS");
        precoUnitario = getIntent().getDoubleExtra("PRECO_UNITARIO", 0.0);
        listaPassageiros = new ArrayList<>();

        containerPassageiros = findViewById(R.id.containerPassageiros);
        rgFormaPagamento = findViewById(R.id.rgFormaPagamento);
        Button btnConfirmarPagamento = findViewById(R.id.btnConfirmarPagamento);

        gerarCamposPassageiros();

        btnConfirmarPagamento.setOnClickListener(v -> {
            if (validarEsalvarDados()) {
                prosseguirParaPassagemVirtual();
            }
        });
    }

    private void gerarCamposPassageiros() {
        for (int i = 0; i < poltronasReservadas.size(); i++) {
            int numPoltrona = poltronasReservadas.get(i);

            Passageiro p = new Passageiro();
            p.setNumeroPoltrona(numPoltrona);
            listaPassageiros.add(p);

            TextView titulo = new TextView(this);
            titulo.setText("Passageiro " + (i + 1) + " (Poltrona: " + numPoltrona + ")");
            titulo.setTextSize(16);
            titulo.setTextColor(Color.BLACK);
            containerPassageiros.addView(titulo);

            EditText edtNome = new EditText(this);
            edtNome.setHint("Nome Completo");
            edtNome.setTag("nome_" + numPoltrona);
            containerPassageiros.addView(edtNome);

            EditText edtCpf = new EditText(this);
            edtCpf.setHint("CPF (Apenas números)");
            edtCpf.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            edtCpf.setTag("cpf_" + numPoltrona);
            containerPassageiros.addView(edtCpf);

            View separator = new View(this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2));
            separator.setBackgroundColor(Color.LTGRAY);
            containerPassageiros.addView(separator);
        }
    }

    private boolean validarEsalvarDados() {
        for (int i = 0; i < listaPassageiros.size(); i++) {
            int numPoltrona = poltronasReservadas.get(i);

            EditText edtNome = containerPassageiros.findViewWithTag("nome_" + numPoltrona);
            EditText edtCpf = containerPassageiros.findViewWithTag("cpf_" + numPoltrona);

            String nome = edtNome.getText().toString();
            String cpf = edtCpf.getText().toString();

            if (nome.isEmpty() || cpf.isEmpty()) {
                Toast.makeText(this, "Preencha todos os dados para o Passageiro " + (i + 1), Toast.LENGTH_SHORT).show();
                return false;
            }

            listaPassageiros.get(i).setNome(nome);
            listaPassageiros.get(i).setCpf(cpf);
        }

        int selectedId = rgFormaPagamento.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecione uma forma de pagamento.", Toast.LENGTH_SHORT).show();
            return false;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String formaPagamento = selectedRadioButton.getText().toString();
        Toast.makeText(this, "Pagamento via " + formaPagamento + " processado.", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void prosseguirParaPassagemVirtual() {
        Intent intent = new Intent(this, PassagemVirtualActivity.class);
        intent.putExtra("LISTA_PASSAGEIROS", (ArrayList) listaPassageiros);
        startActivity(intent);
        finish();
    }
}