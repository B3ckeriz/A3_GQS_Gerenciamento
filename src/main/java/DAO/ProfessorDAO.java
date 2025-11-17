package DAO;

import Model.Professor;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

public class ProfessorDAO {

    private static final Logger logger = Logger.getLogger(ProfessorDAO.class.getName());
    private static final ArrayList<Professor> MinhaLista2 = new ArrayList<>();

    public ProfessorDAO() {
        criarTabelaSeNecessario();
    }

    public int maiorID() throws SQLException {
        int maiorID = 0;
        String sql = "SELECT MAX(id) id FROM tb_professores";

        try (Connection conn = this.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            if (res.next()) {
                maiorID = res.getInt("id");
            }
        } catch (SQLException ex) {
            logger.severe("Erro ao buscar maior ID: " + ex.getMessage());
            throw ex;
        }
        return maiorID;
    }

    // Conexão com SQLite
    public static Connection getConnection() {
        try {
            // SQL em memórias
            String url = System.getenv("DATABASE_URL");
            if (url == null || url.isEmpty()) {
                //SQL Local
                url = "jdbc:sqlite:database.db";
            }

            logger.info("URL utilizada: " + url);
            return DriverManager.getConnection(url);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    public Connection getConexao() {
        return getConnection();
    }

// Cria tabela se necessário
    private void criarTabelaSeNecessario() {
        String sqlProfessores = "CREATE TABLE IF NOT EXISTS tb_professores (" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "idade INTEGER, " +
                "campus TEXT, " +
                "cpf TEXT, " +
                "contato TEXT, " +
                "titulo TEXT, " +
                "salario INTEGER)";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlProfessores);
            logger.info("Tabela tb_professores verificada/criada!");

        } catch (SQLException e) {
            logger.severe("Erro ao criar tabelas: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela", e);
        }
    }

    public ArrayList<Professor> getMinhaLista() {
        MinhaLista2.clear();
        String sql = "SELECT * FROM tb_professores";

        try (Connection conn = this.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                String campus = res.getString("campus");
                String cpf = res.getString("cpf");
                String contato = res.getString("contato");
                String titulo = res.getString("titulo");
                int salario = res.getInt("salario");
                int id = res.getInt("id");
                String nome = res.getString("nome");
                int idade = res.getInt("idade");

                Professor objeto = new Professor(campus, cpf, contato, titulo, salario, id, nome, idade);

                MinhaLista2.add(objeto);
            }

        } catch (SQLException ex) {
            logger.severe("Erro ao buscar lista de professores: " + ex.getMessage());
            throw new RuntimeException("Erro ao buscar professores", ex);
        }

        return MinhaLista2;
    }

    public boolean InsertProfessorBD(Professor objeto) {
        String sql = "INSERT INTO tb_professores(id,nome,idade,campus,cpf,contato,titulo,salario) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setInt(3, objeto.getIdade());
            stmt.setString(4, objeto.getCampus());
            stmt.setString(5, objeto.getCpf());
            stmt.setString(6, objeto.getContato());
            stmt.setString(7, objeto.getTitulo());
            stmt.setInt(8, objeto.getSalario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            logger.severe("Erro ao inserir professor: " + erro.getMessage());
            throw new RuntimeException("Erro ao inserir professor", erro);
        }
    }

    public boolean DeleteProfessorBD(int id) {
        String sql = "DELETE FROM tb_professores WHERE id = ?";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException erro) {
            logger.severe("Erro ao deletar professor ID " + id + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao deletar professor", erro);
        }
    }

    public boolean UpdateProfessorBD(Professor objeto) {

        String sql = "UPDATE tb_professores set nome = ? ,idade = ? ,campus = ? ,cpf = ? ,contato = ? ,titulo = ? ,salario = ? WHERE id = ?";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getIdade());
            stmt.setString(3, objeto.getCampus());
            stmt.setString(4, objeto.getCpf());
            stmt.setString(5, objeto.getContato());
            stmt.setString(6, objeto.getTitulo());
            stmt.setInt(7, objeto.getSalario());
            stmt.setInt(8, objeto.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            logger.severe("Erro ao atualizar professor ID " + objeto.getId() + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao atualizar professor", erro);
        }
    }

    public Professor carregaProfessor(int id) {
        String sql = "SELECT * FROM tb_professores WHERE id = ?";
        Professor objeto = new Professor();
        objeto.setId(id);

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setNome(res.getString("nome"));
                    objeto.setIdade(res.getInt("idade"));
                    objeto.setCampus(res.getString("campus"));
                    objeto.setCpf(res.getString("cpf"));
                    objeto.setContato(res.getString("contato"));
                    objeto.setTitulo(res.getString("titulo"));
                    objeto.setSalario(res.getInt("salario"));
                }
            }

        } catch (SQLException erro) {
            logger.severe("Erro ao carregar professor ID " + id + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao carregar professor", erro);
        }
        return objeto;
    }
}