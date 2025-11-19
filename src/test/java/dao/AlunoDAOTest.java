package dao;

import model.Aluno;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoDAOTest {

    private static AlunoDAO dao;

    @BeforeAll
    static void setupDatabase() throws Exception {
        dao = new AlunoDAO();
        dao.setTestDatabase("jdbc:sqlite::memory:");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            conn.createStatement().execute(
                "CREATE TABLE tb_alunos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT, idade INTEGER, curso TEXT, fase INTEGER)"
            );
        }
    }

    @Test
    void testInsertAluno() {
        Aluno a = new Aluno();
        a.setNome("Kaio");
        a.setIdade(25);
        a.setCurso("SI");
        a.setFase(5);

        assertTrue(dao.insertAluno(a));
    }

    @Test
    void testGetMinhaLista() {
        List<Aluno> lista = dao.getMinhaLista();
        assertNotNull(lista);
    }
}