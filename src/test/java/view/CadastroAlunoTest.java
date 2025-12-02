package view;

import com.toedter.calendar.JDateChooser;
import model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CadastroAlunoTest {
    private static final String CURSO_ADMINISTRACAO = "Administração";
    private static final int PRIMEIRA_FASE = 1;
    private static final String NOME_ALUNO = "João";
    private static final int ANO_NASCIMENTO = 2000;

    @Mock
    private Aluno alunoMock;

    @InjectMocks
    private CadastroAluno cadastroAluno;

    private JTextField campoNome;
    private JComboBox<String> comboCurso;
    private JComboBox<String> comboFase;
    private JDateChooser seletorData;
    private JButton botaoConfirmar;

    @BeforeEach
    void configurarTeste() throws Exception {
        MockitoAnnotations.openMocks(this);
        cadastroAluno.objetoAluno = alunoMock;

        Field[] campos = CadastroAluno.class.getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            switch (campo.getName()) {
                case "nome":
                    campoNome = (JTextField) campo.get(cadastroAluno);
                    break;
                case "curso":
                    comboCurso = (JComboBox<String>) campo.get(cadastroAluno);
                    break;
                case "fase":
                    comboFase = (JComboBox<String>) campo.get(cadastroAluno);
                    break;
                case "idade":
                    seletorData = (JDateChooser) campo.get(cadastroAluno);
                    break;
                case "bConfirmar":
                    botaoConfirmar = (JButton) campo.get(cadastroAluno);
                    break;
            }
        }
    }

    @Test
    void deveRealizarCadastroComSucesso() throws SQLException, AWTException, InterruptedException {
        when(alunoMock.insertAluno(anyString(), anyInt(), anyString(), anyInt()))
                .thenReturn(true);

        preencherFormularioCadastro();

        Robot robot = new Robot();

        SwingUtilities.invokeLater(() -> {
            botaoConfirmar.doClick();
        });

        Thread.sleep(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(500);
        verify(alunoMock).insertAluno(
                eq(CURSO_ADMINISTRACAO),
                eq(PRIMEIRA_FASE),
                eq(NOME_ALUNO),
                anyInt()
        );
    }

    private void preencherFormularioCadastro() {
        campoNome.setText(NOME_ALUNO);
        comboCurso.setSelectedIndex(1);
        comboFase.setSelectedIndex(0);

        Calendar dataNascimento = new GregorianCalendar();
        dataNascimento.set(Calendar.YEAR, ANO_NASCIMENTO);
        dataNascimento.set(Calendar.MONTH, Calendar.JANUARY);
        dataNascimento.set(Calendar.DAY_OF_MONTH, 1);
        seletorData.setDate(dataNascimento.getTime());
    }


}