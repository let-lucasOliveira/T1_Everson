package com.example.t1_everson;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.model.Passageiro;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.Random; // Importação adicionada

public class PassagemVirtualActivity extends AppCompatActivity {

    // Adicione um TextView para o número da passagem no seu XML (ex: com id @+id/txtNumeroPassagem)
    private TextView txtDetalhesViagem, txtPassageirosResumo, txtNumeroPassagem;
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
        // Assumindo que você adicionou este TextView no seu layout XML
        txtNumeroPassagem = findViewById(R.id.txtNumeroPassagem);

        List<Passageiro> listaPassageiros = (List<Passageiro>) getIntent().getSerializableExtra("LISTA_PASSAGEIROS");

        if (listaPassageiros != null && !listaPassageiros.isEmpty()) {
            // 1. Gera o token único da passagem
            String tokenDaPassagem = gerarTokenPassagem();

            // 2. Exibe os detalhes (passageiros, rota, etc.)
            exibirDetalhes(listaPassageiros, tokenDaPassagem);

            // 3. Gera o QR Code usando APENAS o token
            gerarQrCode(tokenDaPassagem);

        } else {
            Toast.makeText(this, "Nenhum dado de passageiro recebido.", Toast.LENGTH_LONG).show();
            finish();
        }

        btnSalvarPdf.setOnClickListener(v -> Toast.makeText(this, "Funcionalidade não implementada.", Toast.LENGTH_SHORT).show());
    }

    /**
     * Ajustado para também exibir o número da passagem.
     */
    private void exibirDetalhes(List<Passageiro> listaPassageiros, String token) {
        String origem = getIntent().getStringExtra("ORIGEM");
        String destino = getIntent().getStringExtra("DESTINO");
        txtDetalhesViagem.setText("Rota: " + origem + " para " + destino);

        // Exibe o número da passagem gerado
        txtNumeroPassagem.setText("Nº da Passagem: " + token);

        StringBuilder sb = new StringBuilder("Passageiros:\n");
        for (Passageiro p : listaPassageiros) {
            sb.append("- ").append(p.getNome())
                    .append(" | CPF: ").append(p.getCpf())
                    .append(" | Poltrona: ").append(p.getNumeroPoltrona()).append("\n");
        }
        txtPassageirosResumo.setText(sb.toString());
    }

    /**
     * Gera um número aleatório de 14 dígitos como uma String.
     */
    private String gerarTokenPassagem() {
        Random random = new Random();
        // Gera um número long com 14 dígitos
        long numero = (long) (random.nextDouble() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;
        return String.valueOf(numero).substring(0, 14);
    }

    /**
     * Esta função permanece a mesma, mas agora recebe apenas o token.
     * Recebe o texto, gera a imagem e a coloca no ImageView.
     */
    private void gerarQrCode(String texto) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            // O tamanho pode ser ajustado conforme necessário
            Bitmap bitmap = barcodeEncoder.encodeBitmap(texto, BarcodeFormat.QR_CODE, 250, 250);
            imgQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e("QRCodeError", "Erro ao gerar o QR Code: " + e.getMessage());
            Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show();
            imgQrCode.setImageResource(R.drawable.ic_launcher_background);
        }
    }
}
