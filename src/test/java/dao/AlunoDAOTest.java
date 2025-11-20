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

            // Inserir registro inicial para o teste testRegistroInicial
            stmt.executeUpdate("""
            INSERT INTO tb_alunos (id, nome, idade, curso, fase)
            VALUES (1, 'Aluno Teste', 20, 'Curso', 1)
        """);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao preparar banco de testes", e);
        }
    }



    @Test
    public void testConfiguracaoBanco() {
        try (Connection conn = dao.getConexao();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='tb_alunos'");
            assertTrue(rs.next(), "A tabela tb_alunos deveria existir.");
        } catch (SQLException e) {
            fail("Erro ao validar configuração do banco: " + e.getMessage());
        }
    }

    @Test
    public void testRegistroInicial() {
        Aluno aluno = dao.carregaAluno(1);
        assertNotNull(aluno, "Aluno com ID 1 deveria existir.");
        assertEquals("Aluno Teste", aluno.getNome());
    }

    @Test
    public void testInsertAluno() {
        // Criando objeto Aluno com construtor válido
        Aluno aluno = new Aluno("Engenharia", 2, 123, "Novo Aluno", 21);

        // Inserção do aluno no banco de testes
        assertTrue(dao.insertAluno(aluno), "A inserção do aluno deveria ter sucesso.");
    }

    @Test
    public void testGetMinhaLista() {
        // Inserir registros de teste
        assertTrue(dao.insertAluno(new Aluno("Engenharia", 3, 2, "João Silva", 22)), "A inserção do aluno deveria ter sucesso.");

        // Testar se os registros foram retornados corretamente
        List<Aluno> alunos = dao.getMinhaLista();
        assertEquals(2, alunos.size(), "A lista deveria conter dois registros."); // Inclui o registro de ID 1 do @BeforeEach
        assertEquals("Aluno Teste", alunos.get(0).getNome());
        assertEquals("João Silva", alunos.get(1).getNome());
    }

    @Test
    public void testDeleteAluno() {
        Aluno aluno = dao.carregaAluno(1);
        assertNotNull(aluno, "Aluno com ID 1 deveria existir antes da deleção.");
        assertTrue(dao.deleteAluno(1), "O método deleteAluno deveria retornar true para uma remoção bem-sucedida.");
        Aluno alunoRemovido = dao.carregaAluno(1);
        assertNull(alunoRemovido, "O aluno com ID 1 não deveria mais existir após a remoção.");
        assertTrue(dao.deleteAluno(1), "O método deleteAluno deveria retornar true mesmo quando não há registro a ser removido.");
    }

    @Test
    public void testUpdateAluno() {
        Aluno aluno = dao.carregaAluno(1);
        assertNotNull(aluno, "Aluno com ID 1 deveria existir para ser atualizado.");

        aluno.setNome("Aluno Atualizado");
        aluno.setIdade(25);
        aluno.setCurso("Novo Curso");
        aluno.setFase(4);

        assertTrue(dao.updateAluno(aluno), "O método updateAluno deveria retornar true para um update bem-sucedido.");

        Aluno alunoAtualizado = dao.carregaAluno(1);
        assertNotNull(alunoAtualizado, "O registro atualizado deveria existir.");
        assertEquals("Aluno Atualizado", alunoAtualizado.getNome());
        assertEquals(25, alunoAtualizado.getIdade());
        assertEquals("Novo Curso", alunoAtualizado.getCurso());
        assertEquals(4, alunoAtualizado.getFase());

        // Tentar atualizar um registro inexistente (por exemplo, ID 999)
        Aluno alunoInexistente = new Aluno("Teste", 1, 999, "Inexistente", 30);
        assertTrue(dao.updateAluno(alunoInexistente), "O método updateAluno deve retornar true mesmo que o ID não exista.");
    }
}
