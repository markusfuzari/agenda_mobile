package br.com.fuzari.agenda.ui.activity.aluno;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.fuzari.agenda.R;
import br.com.fuzari.agenda.dao.AlunoDAO;
import br.com.fuzari.agenda.models.Aluno;
import br.com.fuzari.agenda.ui.activity.aluno.adapters.ListaAlunosAdapter;

import static br.com.fuzari.agenda.ui.activity.aluno.ContantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private final AlunoDAO dao = new AlunoDAO();
    private ListaAlunosAdapter adapter;

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
        atualizaListaDeAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_context_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoSelecionado = adapter.getItem(menuInfo.position);
            confirmarRemocao(alunoSelecionado);
        }
        return super.onContextItemSelected(item);
    }

    private void atualizaListaDeAlunos() {
        adapter.atualiza(dao.todos());
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
        configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }

    private void confirmarRemocao(Aluno aluno){
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.lista_alunos_removendo_aluno))
                .setMessage(getString(R.string.lista_alunos_deseja_remover_aluno))
                .setPositiveButton(getString(R.string.sim), (dialogInterface, i) -> removeAlunoDaLista(aluno))
                .setNegativeButton(getString(R.string.nao), null)
                .show();
    }

    private void removeAlunoDaLista(Aluno aluno) {
        dao.removerAluno(aluno);
        adapter.remove(aluno);
        Toast.makeText(ListaAlunosActivity.this, getString(R.string.removido_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    private void configuraListenerDeCliquePorItem(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener((adapterView, view, posicaoElemento, id) -> {
            Aluno aluno = (Aluno) adapterView.getItemAtPosition(posicaoElemento);
            abrirFormulario(aluno);
        });
    }

    private void configuraAdapter(ListView listaAlunos) {
        adapter = new ListaAlunosAdapter(this);
        listaAlunos.setAdapter(adapter);
    }
}
