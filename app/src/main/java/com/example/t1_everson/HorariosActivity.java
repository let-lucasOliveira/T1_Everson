package com.example.t1_everson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.t1_everson.PoltronaActivity;
import com.example.t1_everson.R;
import com.example.t1_everson.model.Viagem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HorariosActivity extends AppCompatActivity {

    private List<Viagem> listaViagens;
    private String origem, destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        Intent intent = getIntent();
        origem = intent.getStringExtra("ORIGEM");
        destino = intent.getStringExtra("DESTINO");

        TextView txtRota = findViewById(R.id.txtRota);
        txtRota.setText("Viagens de " + origem + " para " + destino);

        listaViagens = new ArrayList<>();
        listaViagens.add(new Viagem("17:00", "23:00", origem, destino, 58.80));
        listaViagens.add(new Viagem("17:30", "23:30", origem, destino, 62.50));

        ListView listViewHorarios = findViewById(R.id.listViewHorarios);
        ViagemAdapter adapter = new ViagemAdapter(this, listaViagens);
        listViewHorarios.setAdapter(adapter);

        listViewHorarios.setOnItemClickListener((parent, view, position, id) -> {
            Viagem viagemSelecionada = listaViagens.get(position);

            Intent i = new Intent(HorariosActivity.this, PoltronaActivity.class);
            i.putExtra("VIAGEM_HORARIO", viagemSelecionada.getHorarioSaida());
            i.putExtra("VIAGEM_PRECO", viagemSelecionada.getPreco());
            startActivity(i);
        });
    }

    private class ViagemAdapter extends BaseAdapter {
        private final List<Viagem> viagens;
        private final LayoutInflater inflater;
        private final NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        public ViagemAdapter(Context context, List<Viagem> viagens) {
            this.viagens = viagens;
            this.inflater = LayoutInflater.from(context);
        }

        @Override public int getCount() { return viagens.size(); }
        @Override public Object getItem(int position) { return viagens.get(position); }
        @Override public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.itemhorario, parent, false);
            }

            Viagem viagem = viagens.get(position);

            TextView txtHoraSaida = convertView.findViewById(R.id.txtHoraSaida);
            TextView txtHoraChegada = convertView.findViewById(R.id.txtHoraChegada);
            TextView txtOrigemInfo = convertView.findViewById(R.id.txtOrigemInfo);
            TextView txtDestinoInfo = convertView.findViewById(R.id.txtDestinoInfo);
            TextView txtPreco = convertView.findViewById(R.id.txtPreco);

            txtHoraSaida.setText(viagem.getHorarioSaida());
            txtHoraChegada.setText(viagem.getHorarioChegada());
            txtOrigemInfo.setText(viagem.getOrigem());
            txtDestinoInfo.setText(viagem.getDestino());
            txtPreco.setText(formatter.format(viagem.getPreco()));

            return convertView;
        }
    }
}