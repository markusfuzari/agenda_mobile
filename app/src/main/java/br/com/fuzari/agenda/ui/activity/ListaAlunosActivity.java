package br.com.fuzari.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.fuzari.agenda.R;
import br.com.fuzari.agenda.models.Aluno;
import br.com.fuzari.agenda.ui.views.ListaAlunosView;

import static br.com.fuzari.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private final ListaAlunosView listaAlunosView = new ListaAlunosView(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        configurarFabNovoAluno();
        configuraLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizaListaDeAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_context_remover) {
            listaAlunosView.confirmarRemocao(item);
        }
        return super.onContextItemSelected(item);
    }

    private void configurarFabNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        fab.setOnClickListener(view -> abrirFormulario(null));
    }

    private void abrirFormulario(Aluno aluno) {
        Intent intent = new Intent(this, FormularioAlunoActivity.class);
        if (aluno != null) {
            intent.putExtra(CHAVE_ALUNO, aluno);
        }
        startActivity(intent);
    }

    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaAlunosView.configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    private void configuraListenerDeCliquePorItem(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener((adapterView, view, posicaoElemento, id) -> {
            Aluno aluno = (Aluno) adapterView.getItemAtPosition(posicaoElemento);
            abrirFormulario(aluno);
        });
    }

}