package DAO;

import Model.Aluno;
import java.util.*;
import java.sql.*;
import java.util.logging.Logger;

public class AlunoDAO {

    private static final Logger logger = Logger.getLogger(AlunoDAO.class.getName());
    private static final ArrayList<Aluno> MinhaLista = new ArrayList<>();

    public AlunoDAO() {
        criarTabelaSeNecessario();
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        String sql = "SELECT MAX(id) id FROM tb_alunos";

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
            // SQL em memória
            String url = System.getenv("DATABASE_URL");
            if (url == null || url.isEmpty()) {
                // SQL local
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
        String sqlAlunos = "CREATE TABLE IF NOT EXISTS tb_alunos (" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "idade INTEGER, " +
                "curso TEXT, " +
                "fase INTEGER)";

        try (Connection conn = getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlAlunos);
            logger.info("Tabela tb_alunos verificada/criada!");

        } catch (SQLException e) {
            logger.severe("Erro ao criar tabelas: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela", e);
        }
    }


    public ArrayList<Aluno> getMinhaLista() {

        MinhaLista.clear();
        String sql = "SELECT * FROM tb_alunos";

        try (Connection conn = this.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {

            while (res.next()) {
                String curso = res.getString("curso");
                int fase = res.getInt("fase");
                int id = res.getInt("id");
                String nome = res.getString("nome");
                int idade = res.getInt("idade");

                Aluno objeto = new Aluno(curso, fase, id, nome, idade);

                MinhaLista.add(objeto);
            }

        } catch (SQLException ex) {
            logger.severe("Erro ao buscar lista de alunos: " + ex.getMessage());
            throw new RuntimeException("Erro ao buscar alunos", ex);
        }

        return MinhaLista;
    }


    public boolean InsertAlunoBD(Aluno objeto) {
        String sql = "INSERT INTO tb_alunos(id,nome,idade,curso,fase) VALUES(?,?,?,?,?)";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setInt(3, objeto.getIdade());
            stmt.setString(4, objeto.getCurso());
            stmt.setInt(5, objeto.getFase());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            logger.severe("Erro ao inserir aluno: " + erro.getMessage());
            throw new RuntimeException("Erro ao inserir aluno", erro);
        }
    }


    public boolean DeleteAlunoBD(int id) {
        String sql = "DELETE FROM tb_alunos WHERE id = ?";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            logger.severe("Erro ao deletar aluno ID " + id + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao deletar aluno", erro);
        }
    }


    public boolean UpdateAlunoBD(Aluno objeto) {

        String sql = "UPDATE tb_alunos SET nome=?, idade=?, curso=?, fase=? WHERE id=?";

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getIdade());
            stmt.setString(3, objeto.getCurso());
            stmt.setInt(4, objeto.getFase());
            stmt.setInt(5, objeto.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException erro) {
            logger.severe("Erro ao atualizar aluno ID " + objeto.getId() + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao atualizar aluno", erro);
        }
    }


    public Aluno carregaAluno(int id) {
        String sql = "SELECT * FROM tb_alunos WHERE id = ?";
        Aluno objeto = new Aluno();
        objeto.setId(id);

        try (Connection conn = this.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    objeto.setNome(res.getString("nome"));
                    objeto.setIdade(res.getInt("idade"));
                    objeto.setCurso(res.getString("curso"));
                    objeto.setFase(res.getInt("fase"));
                }
            }

        } catch (SQLException erro) {
            logger.severe("Erro ao carregar aluno ID " + id + ": " + erro.getMessage());
            throw new RuntimeException("Erro ao carregar aluno", erro);
        }

        return objeto;
    }
}
