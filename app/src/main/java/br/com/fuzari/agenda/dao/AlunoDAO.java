package br.com.fuzari.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.fuzari.agenda.R;
import br.com.fuzari.agenda.models.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static Long contadorDeIds = 1L;

    public void salva(Aluno aluno) {
        aluno.setId(contadorDeIds);
        alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorDeIds++;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public Aluno recuperaAlunoDaLista(Long id){
        for (Aluno aluno:alunos){
            if(aluno.getId().equals(id)){
                return aluno;
            }
        }
        return null;
    }

    public int salvar(Aluno aluno) {
        if(aluno.getId() != null) {
            atualiza(aluno);
            return R.string.atualizado_com_sucesso;
        }else{
            salva(aluno);
            return R.string.salvo_com_sucesso;
        }
    }

    public void atualiza(Aluno alunoAlterado) {
        Aluno alunoLista = recuperaAlunoDaLista(alunoAlterado.getId());
        if(alunoLista != null){
            alunos.set(alunos.indexOf(alunoLista),alunoAlterado);
        }
    }

    public void removerAluno(Aluno alunoRemover){
        Aluno alunoLista = recuperaAlunoDaLista(alunoRemover.getId());
        alunos.remove(alunoLista);
    }
}
