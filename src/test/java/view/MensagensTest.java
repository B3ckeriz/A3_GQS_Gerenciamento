package view;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MensagensTest {

    @Test
    void testMensagem() {
        Mensagens m = new Mensagens("Erro encontrado");
        assertEquals("Erro encontrado", m.getMessage());
    }
}