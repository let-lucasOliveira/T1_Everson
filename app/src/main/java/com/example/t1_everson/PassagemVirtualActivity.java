package com.example.t1_everson;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.R;
import com.example.t1_everson.model.Passageiro;

import java.util.ArrayList;
import java.util.List;

public class PassagemVirtualActivity extends AppCompatActivity {

    private TextView txtDetalhesViagem, txtPassageirosResumo;
    private ImageView imgQrCode;
    private Button btnSalvarPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passagem_virtual);

        txtDetalhesViagem = findViewById(R.id.txtDetalhesViagem);
        txtPassageirosResumo = findViewById(R.id.txtPassageirosResumo);
        imgQrCode = findViewById(R.id.imgQrCode);
        btnSalvarPdf = findViewById(R.id.btnSalvarPdf);

        List<Passageiro> listaPassageiros = (List<Passageiro>) getIntent().getSerializableExtra("LISTA_PASSAGEIROS");

        if (listaPassageiros != null && !listaPassageiros.isEmpty()) {
            exibirDetalhes(listaPassageiros);
            // Simula a exibição de um QR Code (RF07)
            imgQrCode.setImageResource(R.mipmap.ic_launcher);
        } else {
            Toast.makeText(this, "Nenhum dado de passageiro recebido.", Toast.LENGTH_LONG).show();
            finish();
        }

        btnSalvarPdf.setOnClickListener(v -> Toast.makeText(this, "Passagem salva como PDF!", Toast.LENGTH_SHORT).show());
    }

    private void exibirDetalhes(List<Passageiro> listaPassageiros) {
        // Detalhes da viagem (simplificado, mas você pode passar mais dados)
        txtDetalhesViagem.setText("Rota: [ORIGEM] para [DESTINO] \nHorário: [HORÁRIO DE SAÍDA]");

        StringBuilder sb = new StringBuilder("Passageiros:\n");
        for (Passageiro p : listaPassageiros) {
            sb.append("- ").append(p.getNome())
                    .append(" | CPF: ").append(p.getCpf())
                    .append(" | Poltrona: ").append(p.getNumeroPoltrona()).append("\n");
        }
        txtPassageirosResumo.setText(sb.toString());
    }
}
