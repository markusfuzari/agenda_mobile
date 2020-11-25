package br.com.fuzari.agenda.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.fuzari.agenda.R;
import br.com.fuzari.agenda.dao.AlunoDAO;
import br.com.fuzari.agenda.models.Aluno;
import br.com.fuzari.agenda.ui.adapters.ListaAlunosAdapter;

public class ListaAlunosView {

    private final AlunoDAO dao;
    private final ListaAlunosAdapter adapter;
    private final Context context;

    public ListaAlunosView(Context context){
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        this.dao = new AlunoDAO();
    }

    public void configuraAdapter(ListView listaAlunos) {
        listaAlunos.setAdapter(this.adapter);
    }

    public void confirmarRemocao(final MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoSelecionado = adapter.getItem(menuInfo.position);
        new AlertDialog.Builder(this.context)
                .setTitle(context.getString(R.string.lista_alunos_removendo_aluno))
                .setMessage(context.getString(R.string.lista_alunos_deseja_remover_aluno))
                .setPositiveButton(context.getString(R.string.sim), (dialogInterface, i) -> this.removeAlunoDaLista(alunoSelecionado))
                .setNegativeButton(context.getString(R.string.nao), null)
                .show();
    }

    private void removeAlunoDaLista(Aluno aluno) {
        dao.removerAluno(aluno);
        adapter.remove(aluno);
        Toast.makeText(this.context, context.getString(R.string.removido_com_sucesso), Toast.LENGTH_SHORT).show();
    }

    public void atualizaListaDeAlunos() {
        this.adapter.atualiza(dao.todos());
    }

}
