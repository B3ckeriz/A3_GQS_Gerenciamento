package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Classe utilitária para exportação de tabelas JTable para arquivos Excel (.xls).
 * Elimina código duplicado entre GerenciaAlunos e GerenciaProfessores.
 */
public class ExcelExporter {
    
    private static final Logger LOGGER = Logger.getLogger(ExcelExporter.class.getName());
    
    /**
     * Exporta uma JTable para um arquivo Excel.
     * 
     * @param table A tabela a ser exportada
     * @param parentComponent Componente pai para exibir diálogos (pode ser null)
     * @throws IOException se houver erro ao criar ou escrever no arquivo
     */
    public static void exportToExcel(JTable table, java.awt.Component parentComponent) throws IOException {
        
        // Cria seletor de arquivos
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Excel", "xls");
        
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setAcceptAllFileFilterUsed(false);
        
        // Verifica se o usuário aprovou o salvamento
        if (chooser.showSaveDialog(parentComponent) == JFileChooser.APPROVE_OPTION) {
            
            String path = chooser.getSelectedFile().toString();
            if (!path.toLowerCase().endsWith(".xls")) {
                path = path + ".xls";
            }
            
            try {
                File fileXLS = new File(path);
                
                // Se o arquivo já existe, tenta deletá-lo primeiro
                if (fileXLS.exists()) {
                    boolean deleted = fileXLS.delete();
                    if (!deleted) {
                        throw new IOException("Não foi possível substituir o arquivo existente.");
                    }
                }
                
                // Cria o novo arquivo
                if (!fileXLS.createNewFile()) {
                    throw new IOException("Não foi possível criar o arquivo.");
                }
                
                // Try-with-resources garante que os recursos sejam fechados automaticamente
                try (Workbook book = new HSSFWorkbook();
                     FileOutputStream fileOut = new FileOutputStream(fileXLS)) {
                    
                    Sheet sheet = book.createSheet("Dados");
                    sheet.setDisplayGridlines(true);
                    
                    // Cria linha de cabeçalho com os nomes das colunas
                    Row headerRow = sheet.createRow(0);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        Cell cell = headerRow.createCell(j);
                        cell.setCellValue(table.getColumnName(j));
                    }
                    
                    // Preenche as células com os dados da tabela
                    for (int linha = 0; linha < table.getRowCount(); linha++) {
                        Row dataRow = sheet.createRow(linha + 1);
                        for (int coluna = 0; coluna < table.getColumnCount(); coluna++) {
                            
                            Cell cell = dataRow.createCell(coluna);
                            Object value = table.getValueAt(linha, coluna);
                            
                            // Verifica o tipo de dado e insere apropriadamente
                            if (value instanceof Double) {
                                cell.setCellValue((Double) value);
                            } else if (value instanceof Float) {
                                cell.setCellValue((Float) value);
                            } else if (value instanceof Integer) {
                                cell.setCellValue((Integer) value);
                            } else {
                                cell.setCellValue(value != null ? value.toString() : "");
                            }
                        }
                    }
                    
                    // Escreve o conteúdo no arquivo
                    book.write(fileOut);
                }
                
                // Mensagem de sucesso
                JOptionPane.showMessageDialog(
                    parentComponent,
                    "Arquivo Excel exportado com sucesso em:\n" + path,
                    "Exportação Concluída",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro de IO ao exportar dados para Excel", e);
                
                JOptionPane.showMessageDialog(
                    parentComponent,
                    "Não foi possível exportar o arquivo.\nVerifique se você tem permissão de escrita no local selecionado.",
                    "Erro na Exportação",
                    JOptionPane.ERROR_MESSAGE
                );
                
                throw new RuntimeException("Erro ao exportar para Excel", e);
                
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Erro ao formatar números durante exportação para Excel", e);
                
                JOptionPane.showMessageDialog(
                    parentComponent,
                    "Erro ao processar dados numéricos da tabela.\nVerifique se todos os valores estão formatados corretamente.",
                    "Erro de Formatação",
                    JOptionPane.ERROR_MESSAGE
                );
                
                throw new RuntimeException("Erro de formatação ao exportar para Excel", e);
            }
        }
    }
}