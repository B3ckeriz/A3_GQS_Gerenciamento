package model;

import java.util.List;
import dao.ProfessorDAO;
import java.sql.SQLException;

public class Professor {

    private int id;
    private String nome;
    private int idade;
    private String campus;
    private String cpf;
    private String contato;
    private String titulo;
    private int salario;

    private final ProfessorDAO dao;

    // Construtor padrão
    public Professor() {
        this.dao = new ProfessorDAO();
    }

    // Construtor completo com ID
    public Professor(int id, String nome, int idade, String campus,
                     String cpf, String contato, String titulo, int salario) {

        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.campus = campus;
        this.cpf = cpf;
        this.contato = contato;
        this.titulo = titulo;
        this.salario = salario;
        this.dao = new ProfessorDAO();
    }

    // Construtor completo sem ID
    public Professor(String nome, int idade, String campus,
                     String cpf, String contato, String titulo, int salario) {

        this.nome = nome;
        this.idade = idade;
        this.campus = campus;
        this.cpf = cpf;
        this.contato = contato;
        this.titulo = titulo;
        this.salario = salario;
        this.dao = new ProfessorDAO();
    }

    // ==============================
    // GETTERS E SETTERS
    // ==============================

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }

    public void setIdade(int idade) { this.idade = idade; }

    public String getCampus() { return campus; }

    public void setCampus(String campus) { this.campus = campus; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getContato() { return contato; }

    public void setContato(String contato) { this.contato = contato; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getSalario() { return salario; }

    public void setSalario(int salario) { this.salario = salario; }

    // ==============================
    // toString
    // ==============================
    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", campus='" + campus + '\'' +
                ", cpf='" + cpf + '\'' +
                ", contato='" + contato + '\'' +
                ", titulo='" + titulo + '\'' +
                ", salario=" + salario +
                '}';
    }

    // ==============================
    // MÉTODOS DELEGADOS AO DAO
    // ==============================

    public List<Professor> getMinhaLista() {
        return dao.getMinhaLista();
    }

    public boolean insertProfessor(String nome, int idade, String campus,
                                   String cpf, String contato, String titulo, int salario)
            throws SQLException {

        int id = this.maiorID() + 1;
        Professor p = new Professor(id, nome, idade, campus, cpf, contato, titulo, salario);
        return dao.insertProfessor(p);
    }

    public boolean updateProfessor(String nome, int idade, String campus,
                                   String cpf, String contato, String titulo,
                                   int salario, int id) {

        Professor p = new Professor(id, nome, idade, campus, cpf, contato, titulo, salario);
        return dao.updateProfessor(p);
    }

    public boolean deleteProfessor(int id) {
        return dao.deleteProfessor(id);
    }

    public int maiorID() throws SQLException {
        return dao.maiorID();
    }
}