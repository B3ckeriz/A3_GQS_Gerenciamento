package dao;

import model.Professor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfessorDAOTest {

    private static ProfessorDAO dao;

    @BeforeAll
    public static void iniciar() {
        dao = new ProfessorDAO();
        dao.setTestDatabase(); // usa sqlite em memória
    }

    @Test
    @Order(1)
    public void testInsertProfessor() {
        Professor p = new Professor(
                0, "Carlos Silva", 40, "Ilha",
                "12345678900", "(48) 99999-1111",
                "Mestrado", 5200
        );

        assertTrue(dao.insertProfessor(p));
        assertEquals(1, dao.getMinhaLista().size());
    }

    @Test
    @Order(2)
    public void testCarregaProfessor() {
        Professor p = dao.carregaProfessor(1);

        assertNotNull(p);
        assertEquals("Carlos Silva", p.getNome());
        assertEquals("Ilha", p.getCampus());
    }

    @Test
    @Order(3)
    public void testUpdateProfessor() {
        Professor p = dao.carregaProfessor(1);

        p.setNome("Carlos Atualizado");
        p.setSalario(7000);

        assertTrue(dao.updateProfessor(p));

        Professor atualizado = dao.carregaProfessor(1);

        assertEquals("Carlos Atualizado", atualizado.getNome());
        assertEquals(7000, atualizado.getSalario());
    }

    @Test
    @Order(4)
    public void testGetMinhaLista() {
        ArrayList<Professor> lista = dao.getMinhaLista();

        assertNotNull(lista);
        assertEquals(1, lista.size());
    }

    @Test
    @Order(5)
    public void testDeleteProfessor() {
        assertTrue(dao.deleteProfessor(1));
        assertEquals(0, dao.getMinhaLista().size());
    }
}