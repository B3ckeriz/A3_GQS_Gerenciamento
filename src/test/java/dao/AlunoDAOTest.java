package dao;

import model.Aluno;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoDAOTest {

    private static AlunoDAO dao;

    @BeforeEach
    public void prepararBancoDeTestes() {
        dao = new AlunoDAO();
        dao.setTestDatabase("jdbc:sqlite:database_test.db");

        // Garantir que a tabela esteja criada e limpa
        try (Connection conn = dao.getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS tb_alunos");
            stmt.execute("""
                CREATE TABLE tb_alunos (
                    id INTEGER PRIMARY KEY,
                    nome TEXT NOT NULL,
                    idade INTEGER,
                    curso TEXT,
                    fase INTEGER
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao preparar banco de testes", e);
        }
    }

    @Test
    public void testInsertAluno() {
        Aluno aluno = new Aluno("Engenharia", 3, 1, "João Silva", 22);

        boolean resultado = dao.insertAluno(aluno);
        assertTrue(resultado, "Falha ao inserir aluno");

        // Verificar se o aluno foi inserido
        List<Aluno> alunos = dao.getMinhaLista();
        assertEquals(1, alunos.size());
        assertEquals("João Silva", alunos.get(0).getNome());
    }

    @Test
    public void testGetMinhaLista() {
        // Inserir registros de teste
        dao.insertAluno(new Aluno("Engenharia", 3, 1, "João Silva", 22));

        // Testar se os registros foram retornados corretamente
        List<Aluno> alunos = dao.getMinhaLista();
        assertEquals(1, alunos.size());
        assertEquals("João Silva", alunos.get(0).getNome());
    }
}
