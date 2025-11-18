package Model;

import DAO.AlunoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Aluno {
    private int id;
    private String nome;
    private int idade;
    private String curso;
    private int fase;
    
    private AlunoDAO dao;

    // Construtores
    public Aluno() {
        this.dao = new AlunoDAO();
    }

    // Construtor simplificado com curso e fase
    public Aluno(String curso, int fase) {
        this.curso = curso;
        this.fase = fase;
        this.dao = new AlunoDAO();
    }

    // Construtor completo
    public Aluno(String curso, int fase, int id, String nome, int idade) {
        this.curso = curso;
        this.fase = fase;
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.dao = new AlunoDAO();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    // Métodos de DAO - CORRIGIDOS com tipos genéricos adequados
    

// Métodos que retornam boolean (não void)
public boolean insertAlunoBD() {
    return dao.insertAlunoBD(this);
}

public boolean updateAlunoBD() {
    return dao.updateAlunoBD(this);
}

public boolean deleteAlunoBD(int id) {
    return dao.deleteAlunoBD(id);
}

// Retornar List ao invés de ArrayList
public List<Aluno> getMinhaLista() {
    return dao.getMinhaLista();
}

    public Optional<Aluno> carregaAluno(int id) {
        return dao.carregaAluno(id);
    }

    public int maiorID() {
        return dao.maiorID();
    }
    
    public List<Aluno> buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", curso='" + curso + '\'' +
                ", fase=" + fase +
                '}';
    }
}