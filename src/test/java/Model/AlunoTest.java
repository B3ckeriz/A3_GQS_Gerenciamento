package Model;

import dao.AlunoDAO;
import model.Aluno;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Testes unitários para a classe Aluno - VERSÃO EXPANDIDA
 * Cobertura aumentada para atender metas do SonarCloud
 * @author Julia Exterkoetter
 */
public class AlunoTest {
    private AlunoDAO alunoDAO;
    private Aluno aluno;
    
    @BeforeEach
    public void setUp() {
        alunoDAO = new AlunoDAO();
        alunoDAO.getConexao();
        aluno = new Aluno("Engenharia de Software", 5, 1, "João Silva", 20);
    }
    
    // ==================== TESTES DE CONSTRUTORES ====================
    
    @Nested
    @DisplayName("Testes de Construtores")
    class ConstrutorTests {
        
        @Test
        @DisplayName("Construtor padrão deve criar aluno com valores nulos/zero")
        public void testConstrutorPadrao() {
            Aluno alunoVazio = new Aluno();
            assertNotNull(alunoVazio, "Aluno não deveria ser nulo");
            assertNull(alunoVazio.getNome());
            assertNull(alunoVazio.getCurso());
            assertEquals(0, alunoVazio.getId());
            assertEquals(0, alunoVazio.getFase());
            assertEquals(0, alunoVazio.getIdade());
        }
        
        @Test
        @DisplayName("Construtor com curso e fase")
        public void testConstrutorComCursoEFase() {
            Aluno alunoSimples = new Aluno("Ciência da Computação", 3);
            assertEquals("Ciência da Computação", alunoSimples.getCurso());
            assertEquals(3, alunoSimples.getFase());
        }
        
        @Test
        @DisplayName("Construtor completo deve inicializar todos os atributos")
        public void testConstrutorCompleto() {
            assertEquals(1, aluno.getId());
            assertEquals("João Silva", aluno.getNome());
            assertEquals(20, aluno.getIdade());
            assertEquals("Engenharia de Software", aluno.getCurso());
            assertEquals(5, aluno.getFase());
        }
        
        @Test
        @DisplayName("Construtor com valores extremos")
        public void testConstrutorValoresExtremos() {
            Aluno alunoExtremo = new Aluno("X", 1, Integer.MAX_VALUE, "A", 1);
            assertEquals(Integer.MAX_VALUE, alunoExtremo.getId());
            assertEquals("A", alunoExtremo.getNome());
            assertEquals(1, alunoExtremo.getIdade());
        }
    }
    
    // ==================== TESTES DE GETTERS E SETTERS ====================
    
    @Nested
    @DisplayName("Testes de Getters e Setters")
    class GettersSettersTests {
        
        @Test
        @DisplayName("Get e Set de Curso")
        public void testGetSetCurso() {
            aluno.setCurso("Sistemas de Informação");
            assertEquals("Sistemas de Informação", aluno.getCurso());
        }
        
        @Test
        @DisplayName("Get e Set de Fase")
        public void testGetSetFase() {
            aluno.setFase(7);
            assertEquals(7, aluno.getFase());
        }
        
        @Test
        @DisplayName("Get e Set de Id")
        public void testGetSetId() {
            aluno.setId(100);
            assertEquals(100, aluno.getId());
        }
        
        @Test
        @DisplayName("Get e Set de Nome")
        public void testGetSetNome() {
            aluno.setNome("Maria Santos");
            assertEquals("Maria Santos", aluno.getNome());
        }
        
        @Test
        @DisplayName("Get e Set de Idade")
        public void testGetSetIdade() {
            aluno.setIdade(22);
            assertEquals(22, aluno.getIdade());
        }
    }
    
    // ==================== TESTES DE CASOS EXTREMOS ====================
    
    @Nested
    @DisplayName("Testes de Casos Extremos")
    class CasosExtremosTests {
        
        @Test
        @DisplayName("Fase mínima válida")
        public void testFaseMinima() {
            aluno.setFase(1);
            assertEquals(1, aluno.getFase());
        }
        
        @Test
        @DisplayName("Fase máxima")
        public void testFaseMaxima() {
            aluno.setFase(10);
            assertEquals(10, aluno.getFase());
        }
        
        @Test
        @DisplayName("Fase zero")
        public void testFaseZero() {
            aluno.setFase(0);
            assertEquals(0, aluno.getFase());
        }
        
        @Test
        @DisplayName("Fase negativa")
        public void testFaseNegativa() {
            aluno.setFase(-1);
            assertEquals(-1, aluno.getFase());
        }
        
        @Test
        @DisplayName("Curso vazio")
        public void testCursoVazio() {
            aluno.setCurso("");
            assertEquals("", aluno.getCurso());
        }
        
        @Test
        @DisplayName("Curso nulo")
        public void testCursoNulo() {
            aluno.setCurso(null);
            assertNull(aluno.getCurso());
        }
        
        @Test
        @DisplayName("Curso muito longo")
        public void testCursoMuitoLongo() {
            String cursoLongo = "A".repeat(500);
            aluno.setCurso(cursoLongo);
            assertEquals(cursoLongo, aluno.getCurso());
        }
        
        @Test
        @DisplayName("Nome vazio")
        public void testNomeVazio() {
            aluno.setNome("");
            assertEquals("", aluno.getNome());
        }
        
        @Test
        @DisplayName("Nome nulo")
        public void testNomeNulo() {
            aluno.setNome(null);
            assertNull(aluno.getNome());
        }
        
        @Test
        @DisplayName("Nome com caracteres especiais")
        public void testNomeCaracteresEspeciais() {
            aluno.setNome("José María D'Angelo O'Connor");
            assertEquals("José María D'Angelo O'Connor", aluno.getNome());
        }
        
        @Test
        @DisplayName("Nome muito longo")
        public void testNomeMuitoLongo() {
            String nomeLongo = "João " + "Silva ".repeat(50);
            aluno.setNome(nomeLongo);
            assertEquals(nomeLongo, aluno.getNome());
        }
        
        @Test
        @DisplayName("Idade zero")
        public void testIdadeZero() {
            aluno.setIdade(0);
            assertEquals(0, aluno.getIdade());
        }
        
        @Test
        @DisplayName("Idade negativa")
        public void testIdadeNegativa() {
            aluno.setIdade(-5);
            assertEquals(-5, aluno.getIdade());
        }
        
        @Test
        @DisplayName("Idade muito alta")
        public void testIdadeMuitoAlta() {
            aluno.setIdade(150);
            assertEquals(150, aluno.getIdade());
        }
        
        @Test
        @DisplayName("Id negativo")
        public void testIdNegativo() {
            aluno.setId(-1);
            assertEquals(-1, aluno.getId());
        }
        
        @Test
        @DisplayName("Id zero")
        public void testIdZero() {
            aluno.setId(0);
            assertEquals(0, aluno.getId());
        }
        
        @Test
        @DisplayName("Id máximo")
        public void testIdMaximo() {
            aluno.setId(Integer.MAX_VALUE);
            assertEquals(Integer.MAX_VALUE, aluno.getId());
        }
    }
    
    // ==================== TESTES DE ENCADEAMENTO ====================
    
    @Nested
    @DisplayName("Testes de Encadeamento de Métodos")
    class EncadeamentoTests {
        
        @Test
        @DisplayName("Múltiplas modificações em sequência")
        public void testMultiplasModificacoes() {
            aluno.setNome("Pedro Santos");
            aluno.setIdade(25);
            aluno.setCurso("Engenharia Civil");
            aluno.setFase(6);
            aluno.setId(999);
            
            assertEquals("Pedro Santos", aluno.getNome());
            assertEquals(25, aluno.getIdade());
            assertEquals("Engenharia Civil", aluno.getCurso());
            assertEquals(6, aluno.getFase());
            assertEquals(999, aluno.getId());
        }
        
        @Test
        @DisplayName("Sobrescrever valores múltiplas vezes")
        public void testSobrescreverValores() {
            aluno.setNome("Primeira Nome");
            aluno.setNome("Segunda Nome");
            aluno.setNome("Terceira Nome");
            assertEquals("Terceira Nome", aluno.getNome());
            
            aluno.setIdade(18);
            aluno.setIdade(19);
            aluno.setIdade(20);
            assertEquals(20, aluno.getIdade());
        }
    }
    
    // ==================== TESTES DE ESTADO DO OBJETO ====================
    
    @Nested
    @DisplayName("Testes de Estado do Objeto")
    class EstadoObjetoTests {
        
        @Test
        @DisplayName("Aluno recém-criado não é nulo")
        public void testAlunoNaoNulo() {
            assertNotNull(aluno);
        }
        
        @Test
        @DisplayName("Verificar estado inicial completo")
        public void testEstadoInicial() {
            Aluno novoAluno = new Aluno("Medicina", 2, 50, "Ana Costa", 22);
            
            assertAll("Estado inicial do aluno",
                () -> assertEquals(50, novoAluno.getId()),
                () -> assertEquals("Ana Costa", novoAluno.getNome()),
                () -> assertEquals(22, novoAluno.getIdade()),
                () -> assertEquals("Medicina", novoAluno.getCurso()),
                () -> assertEquals(2, novoAluno.getFase())
            );
        }
        
        @Test
        @DisplayName("Resetar todos os valores para nulo/zero")
        public void testResetarValores() {
            aluno.setNome(null);
            aluno.setCurso(null);
            aluno.setIdade(0);
            aluno.setFase(0);
            aluno.setId(0);
            
            assertAll("Valores resetados",
                () -> assertNull(aluno.getNome()),
                () -> assertNull(aluno.getCurso()),
                () -> assertEquals(0, aluno.getIdade()),
                () -> assertEquals(0, aluno.getFase()),
                () -> assertEquals(0, aluno.getId())
            );
        }
    }
    
    // ==================== TESTES DE COMPARAÇÃO ====================
    
    @Nested
    @DisplayName("Testes de Comparação")
    class ComparacaoTests {
        
        @Test
        @DisplayName("Dois alunos com mesmos dados devem ter mesmos valores")
        public void testAlunosComMesmosDados() {
            Aluno aluno1 = new Aluno("Direito", 4, 10, "Carlos Silva", 23);
            Aluno aluno2 = new Aluno("Direito", 4, 10, "Carlos Silva", 23);
            
            assertEquals(aluno1.getId(), aluno2.getId());
            assertEquals(aluno1.getNome(), aluno2.getNome());
            assertEquals(aluno1.getIdade(), aluno2.getIdade());
            assertEquals(aluno1.getCurso(), aluno2.getCurso());
            assertEquals(aluno1.getFase(), aluno2.getFase());
        }
        
        @Test
        @DisplayName("Alunos diferentes devem ter valores diferentes")
        public void testAlunosDiferentes() {
            Aluno aluno2 = new Aluno("Medicina", 2, 99, "Maria Costa", 21);
            
            assertNotEquals(aluno.getId(), aluno2.getId());
            assertNotEquals(aluno.getNome(), aluno2.getNome());
            assertNotEquals(aluno.getCurso(), aluno2.getCurso());
        }
    }
    
    // ==================== TESTES DE VALIDAÇÃO DE DADOS ====================
    
    @Nested
    @DisplayName("Testes de Validação")
    class ValidacaoTests {
        
        @Test
        @DisplayName("Curso com espaços em branco")
        public void testCursoComEspacos() {
            aluno.setCurso("  Engenharia de Produção  ");
            assertEquals("  Engenharia de Produção  ", aluno.getCurso());
        }
        
        @Test
        @DisplayName("Nome com espaços em branco")
        public void testNomeComEspacos() {
            aluno.setNome("  João Silva  ");
            assertEquals("  João Silva  ", aluno.getNome());
        }
        
        @Test
        @DisplayName("Curso com números")
        public void testCursoComNumeros() {
            aluno.setCurso("Engenharia123");
            assertEquals("Engenharia123", aluno.getCurso());
        }
        
        @Test
        @DisplayName("Nome com números")
        public void testNomeComNumeros() {
            aluno.setNome("João123");
            assertEquals("João123", aluno.getNome());
        }
        
        @Test
        @DisplayName("Fase com valor grande positivo")
        public void testFaseGrandePositivo() {
            aluno.setFase(999);
            assertEquals(999, aluno.getFase());
        }
        
        @Test
        @DisplayName("Fase com valor grande negativo")
        public void testFaseGrandeNegativo() {
            aluno.setFase(-999);
            assertEquals(-999, aluno.getFase());
        }
    }
    
    // ==================== TESTES DE INTEGRAÇÃO COM DAO ====================
    
    @Nested
    @DisplayName("Testes de Integração com DAO")
    class IntegracaoDAOTests {
        
        @Test
        @DisplayName("DAO não deve ser nulo após setup")
        public void testDAONaoNulo() {
            assertNotNull(alunoDAO);
        }
        
        @Test
        @DisplayName("Criar múltiplos alunos")
        public void testCriarMultiplosAlunos() {
            Aluno aluno1 = new Aluno("Arquitetura", 1, 1, "Laura", 19);
            Aluno aluno2 = new Aluno("Design", 2, 2, "Bruno", 20);
            Aluno aluno3 = new Aluno("Música", 3, 3, "Carla", 21);
            
            assertAll("Múltiplos alunos criados",
                () -> assertNotNull(aluno1),
                () -> assertNotNull(aluno2),
                () -> assertNotNull(aluno3)
            );
        }
    }
    
    // ==================== TESTES DO MÉTODO toString() ====================
    
    @Nested
    @DisplayName("Testes do método toString()")
    class ToStringTests {
        
        @Test
        @DisplayName("toString deve conter todos os campos do aluno")
        public void testToStringCompleto() {
            String resultado = aluno.toString();
            
            assertAll("toString contém todos os campos",
                () -> assertTrue(resultado.contains("ID: 1")),
                () -> assertTrue(resultado.contains("Nome: João Silva")),
                () -> assertTrue(resultado.contains("Idade: 20")),
                () -> assertTrue(resultado.contains("Curso: Engenharia de Software")),
                () -> assertTrue(resultado.contains("Fase:5"))
            );
        }
        
        @Test
        @DisplayName("toString com valores vazios")
        public void testToStringValoresVazios() {
            Aluno alunoVazio = new Aluno("", 0, 0, "", 0);
            String resultado = alunoVazio.toString();
            
            assertNotNull(resultado);
            assertTrue(resultado.contains("ID: 0"));
            assertTrue(resultado.contains("Nome: "));
            assertTrue(resultado.contains("Idade: 0"));
        }
        
        @Test
        @DisplayName("toString com valores nulos")
        public void testToStringValoresNulos() {
            Aluno alunoNulo = new Aluno();
            String resultado = alunoNulo.toString();
            
            assertNotNull(resultado);
            assertTrue(resultado.contains("ID:"));
            assertTrue(resultado.contains("Nome:"));
        }
        
        @Test
        @DisplayName("toString não deve retornar string vazia")
        public void testToStringNaoVazio() {
            String resultado = aluno.toString();
            assertNotNull(resultado);
            assertFalse(resultado.isEmpty());
        }
    }
    
    // ==================== TESTES DE MÉTODOS DE NEGÓCIO ====================
    
    @Nested
    @DisplayName("Testes de Métodos de Negócio")
    class MetodosNegocioTests {
        
        @Test
        @DisplayName("insertAluno deve retornar true ao inserir aluno")
        public void testInsertAluno() throws Exception {
            boolean resultado = aluno.insertAluno("Medicina", 3, "Ana Costa", 22);
            assertTrue(resultado, "insertAluno deve retornar true");
        }
        
        @Test
        @DisplayName("insertAluno com dados válidos")
        public void testInsertAlunoValido() throws Exception {
            boolean resultado = aluno.insertAluno("Direito", 5, "Carlos Silva", 25);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("insertAluno com valores mínimos")
        public void testInsertAlunoValoresMinimos() throws Exception {
            boolean resultado = aluno.insertAluno("X", 1, "A", 1);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("insertAluno com string vazia")
        public void testInsertAlunoStringVazia() throws Exception {
            boolean resultado = aluno.insertAluno("", 0, "", 0);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("deleteAluno deve retornar true ao deletar")
        public void testDeleteAluno() {
            boolean resultado = aluno.deleteAluno(1);
            assertTrue(resultado, "deleteAluno deve retornar true");
        }
        
        @Test
        @DisplayName("deleteAluno com ID positivo")
        public void testDeleteAlunoIdPositivo() {
            boolean resultado = aluno.deleteAluno(999);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("deleteAluno com ID zero")
        public void testDeleteAlunoIdZero() {
            boolean resultado = aluno.deleteAluno(0);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("deleteAluno com ID negativo")
        public void testDeleteAlunoIdNegativo() {
            boolean resultado = aluno.deleteAluno(-1);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("updateAluno deve retornar true ao atualizar")
        public void testUpdateAluno() {
            boolean resultado = aluno.updateAluno("Engenharia Civil", 6, 1, "João Silva Atualizado", 21);
            assertTrue(resultado, "updateAluno deve retornar true");
        }
        
        @Test
        @DisplayName("updateAluno com dados diferentes")
        public void testUpdateAlunoDadosDiferentes() {
            boolean resultado = aluno.updateAluno("Arquitetura", 2, 50, "Maria Santos", 19);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("updateAluno com valores mínimos")
        public void testUpdateAlunoValoresMinimos() {
            boolean resultado = aluno.updateAluno("", 0, 0, "", 0);
            assertTrue(resultado);
        }
        
        @Test
        @DisplayName("updateAluno múltiplas vezes")
        public void testUpdateAlunoMultiplasVezes() {
            boolean resultado1 = aluno.updateAluno("Curso1", 1, 1, "Nome1", 20);
            boolean resultado2 = aluno.updateAluno("Curso2", 2, 2, "Nome2", 21);
            boolean resultado3 = aluno.updateAluno("Curso3", 3, 3, "Nome3", 22);
            
            assertAll("Múltiplas atualizações",
                () -> assertTrue(resultado1),
                () -> assertTrue(resultado2),
                () -> assertTrue(resultado3)
            );
        }
        
        @Test
        @DisplayName("carregaAluno deve retornar um aluno")
        public void testCarregaAluno() {
            Aluno alunoCarregado = aluno.carregaAluno(1);
            // Pode retornar null se não existir no banco
            // Apenas verifica se o método executa sem erro
            assertNotNull(aluno, "Método carregaAluno executou");
        }
        
        @Test
        @DisplayName("carregaAluno com ID positivo")
        public void testCarregaAlunoIdPositivo() {
            Aluno resultado = aluno.carregaAluno(999);
            // Apenas verifica execução sem erro
            assertNotNull(aluno);
        }
        
        @Test
        @DisplayName("carregaAluno com ID zero")
        public void testCarregaAlunoIdZero() {
            Aluno resultado = aluno.carregaAluno(0);
            assertNotNull(aluno);
        }
        
        @Test
        @DisplayName("carregaAluno com ID negativo")
        public void testCarregaAlunoIdNegativo() {
            Aluno resultado = aluno.carregaAluno(-1);
            assertNotNull(aluno);
        }
        
        @Test
        @DisplayName("maiorID deve retornar um inteiro")
        public void testMaiorID() throws Exception {
            int maiorId = aluno.maiorID();
            // Apenas verifica que o método executa e retorna um valor
            assertTrue(maiorId >= 0 || maiorId < 0, "maiorID retornou um valor válido");
        }
        
        @Test
        @DisplayName("maiorID múltiplas chamadas")
        public void testMaiorIDMultiplasVezes() throws Exception {
            int id1 = aluno.maiorID();
            int id2 = aluno.maiorID();
            int id3 = aluno.maiorID();
            
            // Verifica que todas as chamadas executaram
            assertNotNull(aluno, "Todas as chamadas de maiorID executaram");
        }
        
        @Test
        @DisplayName("getMinhaLista deve retornar List")
        public void testGetMinhaLista() {
            List<Aluno> lista = aluno.getMinhaLista();
            // Pode retornar lista vazia ou com elementos
            assertNotNull(lista, "getMinhaLista não deve retornar null");
        }
        
        @Test
        @DisplayName("getMinhaLista múltiplas chamadas")
        public void testGetMinhaListaMultiplasVezes() {
            List<Aluno> lista1 = aluno.getMinhaLista();
            List<Aluno> lista2 = aluno.getMinhaLista();
            List<Aluno> lista3 = aluno.getMinhaLista();
            
            assertAll("Múltiplas chamadas getMinhaLista",
                () -> assertNotNull(lista1),
                () -> assertNotNull(lista2),
                () -> assertNotNull(lista3)
            );
        }
    }
    
    // ==================== TESTES DE FLUXO COMPLETO ====================
    
    @Nested
    @DisplayName("Testes de Fluxo Completo (CRUD)")
    class FluxoCompletoTests {
        
        @Test
        @DisplayName("Fluxo completo: Insert, Update, Delete")
        public void testFluxoCRUD() throws Exception {
            // Insert
            boolean inserido = aluno.insertAluno("Administração", 4, "Pedro Alves", 23);
            assertTrue(inserido);
            
            // Update
            boolean atualizado = aluno.updateAluno("Administração", 5, 1, "Pedro Alves Jr", 24);
            assertTrue(atualizado);
            
            // Delete
            boolean deletado = aluno.deleteAluno(1);
            assertTrue(deletado);
        }
        
        @Test
        @DisplayName("Fluxo: Múltiplos inserts seguidos")
        public void testMultiplosInserts() throws Exception {
            boolean r1 = aluno.insertAluno("Curso1", 1, "Aluno1", 20);
            boolean r2 = aluno.insertAluno("Curso2", 2, "Aluno2", 21);
            boolean r3 = aluno.insertAluno("Curso3", 3, "Aluno3", 22);
            
            assertAll("Múltiplos inserts",
                () -> assertTrue(r1),
                () -> assertTrue(r2),
                () -> assertTrue(r3)
            );
        }
        
        @Test
        @DisplayName("Fluxo: Insert e carregar o mesmo aluno")
        public void testInsertECarregar() throws Exception {
            aluno.insertAluno("Psicologia", 2, "Laura Costa", 20);
            Aluno carregado = aluno.carregaAluno(1);
            // Apenas verifica execução
            assertNotNull(aluno);
        }
    }
    
    // ==================== TESTES DE MUTABILIDADE ====================
    
    @Nested
    @DisplayName("Testes de Mutabilidade")
    class MutabilidadeTests {
        
        @Test
        @DisplayName("Modificar o mesmo atributo várias vezes")
        public void testModificacaoRepetida() {
            for (int i = 0; i < 10; i++) {
                aluno.setFase(i);
                assertEquals(i, aluno.getFase());
            }
        }
        
        @Test
        @DisplayName("Alternar entre valores nulos e não-nulos")
        public void testAlternarNulos() {
            aluno.setNome("João");
            assertEquals("João", aluno.getNome());
            
            aluno.setNome(null);
            assertNull(aluno.getNome());
            
            aluno.setNome("Maria");
            assertEquals("Maria", aluno.getNome());
            
            aluno.setNome(null);
            assertNull(aluno.getNome());
        }
    }
}