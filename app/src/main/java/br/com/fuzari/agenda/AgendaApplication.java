package br.com.fuzari.agenda;

import android.app.Application;

import br.com.fuzari.agenda.dao.AlunoDAO;
import br.com.fuzari.agenda.models.Aluno;

public class AgendaApplication extends Application {
    private final AlunoDAO dao = new AlunoDAO();
    @Override
    public void onCreate() {
        super.onCreate();
        criarAlunosExemplo();
    }

    private void criarAlunosExemplo() {
        dao.salva(new Aluno("Vanete Fuzari Barbosa", "85989497121", "naolembro@gmail.com",94));
        dao.salva(new Aluno("Nariane Monique Mendes de Lima", "85988877762", "narianemonique@gmail.com",80));
        dao.salva(new Aluno("Markus Vinicius Fuzari Barbosa", "85989329779", "markusfuzari@gmail.com",110));
    }
}
