import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tutorial extends JFrame {
    private JScrollPane jScrollPane;
    private JTextArea jTextArea_ajuda;

    public Tutorial() {
        initComponents();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (Exception e) {
            Logger.getLogger(TirarOcultoEDeletarAtalhos.class.getName()).log(Level.SEVERE, null, e);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tutorial().setVisible(true);
            }
        });
    }

    private void initComponents() {
        this.jScrollPane = new JScrollPane();
        this.jTextArea_ajuda = new JTextArea();
        setTitle("Peek a Boo - Ajuda");
        setBackground(new Color(255, 255, 255));
        setBounds(new Rectangle(0, 0, 0, 0));
        setCursor(new Cursor(0));
        setName("Tutorial");
        setResizable(false);
        this.jScrollPane.setCursor(new Cursor(0));
        this.jTextArea_ajuda.setEditable(false);
        this.jTextArea_ajuda.setColumns(20);
        this.jTextArea_ajuda.setFont(new Font("Times New Roman", 0, 18));
        this.jTextArea_ajuda.setLineWrap(true);
        this.jTextArea_ajuda.setRows(5);
        this.jTextArea_ajuda.setText("\t\tTutorial de Ajuda\n\nPrimeiramente entre com o diretório (Pasta) a ser processado. Existem duas maneiras de se fazer isto:\n1º - Clicando no botão “Abrir”, e selecionar o diretório desejado;\n2º - Inserir manualmente o diretório completo, caso não encontre na opção acima. Obs.: informe apenas um diretório, sem nenhum arquivo acompanhado.\n\nEm “Operações” nas caixas de seleção, marque quais operações deverá ser feita. É obrigatório ser marcada pelo menos uma das opções.\n\nA caixa de seleção que se encontra ao lado do quadro “Operações”, existe para especificar se a operação de remover o atributo oculto dos arquivos, deve ser apenas na pasta atual selecionada ou em todas as subpastas também.\n\nApós isso, acione o botão “Executar” para concluir o procedimento. O programa irá limpar todos os atributos ocultos (não visíveis) dos arquivos da pasta e subpastas caso for selecionado esta opção, assim os tornando-os visível, e excluir todos os atalhos criados do mesmo.\n\nO botão “Restaurar Padrão” serve para reverter às operações realizadas ao acionar o botão “Executar” para forma inicial (antes de ser alterada). Obs.: apenas os arquivos ocultos (não visíveis) da pasta atual alterados serão repostos na forma inicial, ou seja, se marcado a opção para executar em subpastas também, não será permitida a restauração. Os atalhos apagados não são restaurados.\n\nO botão “Limpar” zera todos os componentes do programa.\n\n\n\t\tProdução de Tutorial: Hirohito P. Gonçalves.");
        this.jTextArea_ajuda.setWrapStyleWord(true);
        this.jScrollPane.setViewportView(this.jTextArea_ajuda);
        this.jTextArea_ajuda.getAccessibleContext().setAccessibleParent(this);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 649, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane, -2, 649, -2).addGap(0, 0, 32767))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 615, 32767).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane, -1, 615, 32767)));
        pack();
        setLocationRelativeTo(null);
    }
}