import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TirarOcultoEDeletarAtalhos extends JFrame {
    private Tutorial tutorial = new Tutorial();
    private JFileChooser jFileChooser_seletor = new JFileChooser();
    private String[] TodosOcultos;
    private String caminho;
    private String formato;
    private File file;
    private boolean execucao = false;
    private boolean sub_pasta = false;
    private JButton jButton_abrir;
    private JButton jButton_ajuda;
    private JButton jButton_executar;
    private JButton jButton_limpar;
    private JButton jButton_mostar_caminho;
    private JButton jButton_restaurar;
    private JCheckBox jCheckBox_apagar_atalhos;
    private JCheckBox jCheckBox_executar_sub_pasta;
    private JCheckBox jCheckBox_formato;
    private JCheckBox jCheckBox_remover_oculto;
    private JLabel jLabel_estado;
    private JLabel jLabel_meu_nome;
    private JLabel jLabel_titulo;
    private JPanel jPanel_operacoes;
    private JPanel jPanel_outros;
    private JPanel jPanel_painel;
    private JTextField jTextField_caminho;
    private JTextField jTextField_formato;

    public TirarOcultoEDeletarAtalhos() {
        initComponents();
        this.jFileChooser_seletor.setFileSelectionMode(1);
        this.jTextField_formato.setVisible(false);
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
                new TirarOcultoEDeletarAtalhos().setVisible(true);
            }
        });
    }

    public String[] pegaOculto() {
        setCaminho(getjTextField_caminho().getText());
        setFile(new File(getCaminho()));
        File[] TodosArquivos = getFile().listFiles();
        String dir_array = "";
        int qtd_arquivos = TodosArquivos.length;
        for (int i = 0; i < qtd_arquivos; i++) {
            if (TodosArquivos[i].isHidden()) {
                if (getjCheckBox_formato().isSelected()) {
                    String[] divisao_nome = TodosArquivos[i].getName().split("\\.");
                    if (divisao_nome[(divisao_nome.length - 1)].equals(getFormato()))
                        dir_array = dir_array + TodosArquivos[i].getName() + "|||";
                } else {
                    dir_array = dir_array + TodosArquivos[i].getName() + "|||";
                }
            }
        }
        return dir_array.split(Pattern.quote("|||"));
    }

    public String retornarFormatoValido() {
        String retorno = "";
        if (!"".equals(getjTextField_formato().getText())) {
            if (".".equals(getjTextField_formato().getText().substring(0, 1)))
                retorno = getjTextField_formato().getText().substring(1, getjTextField_formato().getText().length());
            else {
                retorno = getjTextField_formato().getText();
            }
        }
        return retorno;
    }

    public void removerOculto(int v) {
        if (v == 1) {
            int i = 0;
            if (getjCheckBox_executar_sub_pasta().isSelected()) {
                try {
                    Process p;
                    if (getjCheckBox_formato().isSelected())
                        p = Runtime.getRuntime().exec("cmd /C attrib -R -S -H +A \"" + getCaminho() + "/*." + getFormato() + "\" /S /D");
                    else {
                        p = Runtime.getRuntime().exec("cmd /C attrib -R -S -H +A \"" + getCaminho() + "/*\" /S /D");
                    }
                    p.getOutputStream().close();
                } catch (Exception e) {
                    Logger.getLogger(TirarOcultoEDeletarAtalhos.class.getName()).log(Level.SEVERE, null, e);
                }
                setSub_pasta(false);
            } else {
                String[] Ocultos = pegaOculto();
                for (int j = Ocultos.length; i < j; i++) {
                    Process p = null;
                    try {
                        p = Runtime.getRuntime().exec("cmd /C attrib /S /D -R -S -H +A \"" + getCaminho() + "\\" + Ocultos[i] + "\"");
                        p.getOutputStream().close();
                    } catch (Exception e) {
                        Logger.getLogger(TirarOcultoEDeletarAtalhos.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                setTodosOcultos(Ocultos);
                setSub_pasta(true);
            }
        } else if (v == 2) {
            int i = 0;
            for (int j = getTodosOcultos().length; i < j; i++) {
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec("cmd /C attrib /S /D +H +A \"" + getCaminho() + "\\" + getTodosOcultos()[i] + "\"");
                    p.getOutputStream().close();
                } catch (Exception e) {
                    Logger.getLogger(TirarOcultoEDeletarAtalhos.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    public void apagarAtalhos(int v) {
        if (v == 1) {
            setCaminho(getjTextField_caminho().getText());
            setFile(new File(getCaminho()));
            String formato_atalho = ".lnk";
            File[] TodosArquivos = getFile().listFiles();
            int i = 0;
            for (int j = TodosArquivos.length; i < j; i++)
                if ((TodosArquivos[i].getName().length() > 4) && (TodosArquivos[i].getName().substring(TodosArquivos[i].getName().length() - 4, TodosArquivos[i].getName().length()).equals(formato_atalho))) {
                    File deletar = new File(getCaminho() + "\\" + TodosArquivos[i].getName());
                    deletar.delete();
                }
        }
    }

    private void initComponents() {
        this.jPanel_painel = new JPanel();
        this.jPanel_operacoes = new JPanel();
        this.jCheckBox_apagar_atalhos = new JCheckBox();
        this.jCheckBox_remover_oculto = new JCheckBox();
        this.jButton_ajuda = new JButton();
        this.jButton_abrir = new JButton();
        this.jButton_executar = new JButton();
        this.jTextField_caminho = new JTextField();
        this.jButton_limpar = new JButton();
        this.jLabel_titulo = new JLabel();
        this.jLabel_meu_nome = new JLabel();
        this.jLabel_estado = new JLabel();
        this.jButton_restaurar = new JButton();
        this.jButton_mostar_caminho = new JButton();
        this.jPanel_outros = new JPanel();
        this.jCheckBox_executar_sub_pasta = new JCheckBox();
        this.jCheckBox_formato = new JCheckBox();
        this.jTextField_formato = new JTextField();
        setDefaultCloseOperation(3);
        setTitle("Tirar Oculto e Deletar Atalhos");
        setName("frame");
        setResizable(false);
        this.jPanel_operacoes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Operações"));
        this.jCheckBox_apagar_atalhos.setFont(new Font("Tahoma", 1, 12));
        this.jCheckBox_apagar_atalhos.setText("Apagar atalhos");
        this.jCheckBox_remover_oculto.setFont(new Font("Tahoma", 1, 12));
        this.jCheckBox_remover_oculto.setSelected(true);
        this.jCheckBox_remover_oculto.setText("Remover ocultação de arquivos");
        this.jCheckBox_remover_oculto.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jCheckBox_remover_ocultoStateChanged(evt);
            }
        });
        GroupLayout jPanel_operacoesLayout = new GroupLayout(this.jPanel_operacoes);
        this.jPanel_operacoes.setLayout(jPanel_operacoesLayout);
        jPanel_operacoesLayout.setHorizontalGroup(jPanel_operacoesLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel_operacoesLayout.createSequentialGroup().addContainerGap(-1, 32767).addGroup(jPanel_operacoesLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jCheckBox_remover_oculto).addComponent(this.jCheckBox_apagar_atalhos))));
        jPanel_operacoesLayout.setVerticalGroup(jPanel_operacoesLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel_operacoesLayout.createSequentialGroup().addContainerGap().addComponent(this.jCheckBox_remover_oculto).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jCheckBox_apagar_atalhos).addContainerGap(14, 32767)));
        this.jButton_ajuda.setFont(new Font("Tahoma", 0, 14));
        this.jButton_ajuda.setText("Ajuda");
        this.jButton_ajuda.setToolTipText("Tutorial de ajuda");
        this.jButton_ajuda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_ajudaActionPerformed(evt);
            }
        });
        this.jButton_abrir.setFont(new Font("Tahoma", 0, 14));
        this.jButton_abrir.setText("Abrir");
        this.jButton_abrir.setToolTipText("Abrir diretório com os arquivos ocultos");
        this.jButton_abrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_abrirActionPerformed(evt);
            }
        });
        this.jButton_executar.setFont(new Font("Tahoma", 0, 14));
        this.jButton_executar.setText("Executar");
        this.jButton_executar.setToolTipText("Executar operações selecionadas");
        this.jButton_executar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_executarActionPerformed(evt);
            }
        });
        this.jTextField_caminho.setFont(new Font("Tahoma", 0, 18));
        this.jButton_limpar.setFont(new Font("Tahoma", 0, 14));
        this.jButton_limpar.setText("Limpar");
        this.jButton_limpar.setToolTipText("Limpar todos os dados");
        this.jButton_limpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_limparActionPerformed(evt);
            }
        });
        this.jLabel_titulo.setFont(new Font("Tahoma", 1, 24));
        this.jLabel_titulo.setHorizontalAlignment(0);
        this.jLabel_titulo.setText("Peek a Boo");
        this.jLabel_meu_nome.setFont(new Font("Tahoma", 2, 10));
        this.jLabel_meu_nome.setText("Autor: Hirohito P. Gonçalves");
        this.jLabel_estado.setFont(new Font("Tahoma", 0, 28));
        this.jButton_restaurar.setFont(new Font("Tahoma", 0, 12));
        this.jButton_restaurar.setText("Restaurar Padrão");
        this.jButton_restaurar.setToolTipText("Restaurar modo antes da execução das operações");
        this.jButton_restaurar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_restaurarActionPerformed(evt);
            }
        });
        this.jButton_mostar_caminho.setText("...");
        this.jButton_mostar_caminho.setToolTipText("Mostrar diretório completo");
        this.jButton_mostar_caminho.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jButton_mostar_caminhoActionPerformed(evt);
            }
        });
        this.jCheckBox_executar_sub_pasta.setFont(new Font("Tahoma", 1, 14));
        this.jCheckBox_executar_sub_pasta.setText("Executar em sub-pastas");
        this.jCheckBox_formato.setFont(new Font("Tahoma", 1, 14));
        this.jCheckBox_formato.setText("Informar extensão");
        this.jCheckBox_formato.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                TirarOcultoEDeletarAtalhos.this.jCheckBox_formatoStateChanged(evt);
            }
        });
        GroupLayout jPanel_outrosLayout = new GroupLayout(this.jPanel_outros);
        this.jPanel_outros.setLayout(jPanel_outrosLayout);
        jPanel_outrosLayout.setHorizontalGroup(jPanel_outrosLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel_outrosLayout.createSequentialGroup().addContainerGap().addGroup(jPanel_outrosLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jCheckBox_executar_sub_pasta).addComponent(this.jCheckBox_formato, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel_outrosLayout.setVerticalGroup(jPanel_outrosLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel_outrosLayout.createSequentialGroup().addContainerGap().addComponent(this.jCheckBox_executar_sub_pasta).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jCheckBox_formato, -2, 25, -2).addContainerGap(-1, 32767)));
        this.jTextField_formato.setFont(new Font("Tahoma", 0, 13));
        GroupLayout jPanel_painelLayout = new GroupLayout(this.jPanel_painel);
        this.jPanel_painel.setLayout(jPanel_painelLayout);
        jPanel_painelLayout.setHorizontalGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel_painelLayout.createSequentialGroup().addContainerGap().addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel_estado, -1, -1, 32767).addComponent(this.jLabel_titulo, -1, -1, 32767).addComponent(this.jTextField_caminho, -2, 426, -2).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jPanel_operacoes, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel_painelLayout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.jTextField_formato, -2, 150, -2)).addComponent(this.jPanel_outros, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jButton_ajuda, GroupLayout.Alignment.TRAILING, -1, -1, 32767).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jButton_mostar_caminho, -2, 30, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton_abrir, -1, -1, 32767)).addComponent(this.jLabel_meu_nome, GroupLayout.Alignment.TRAILING).addGroup(jPanel_painelLayout.createSequentialGroup().addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton_restaurar, -2, 170, -2).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jButton_limpar).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton_executar, -1, -1, 32767))).addGap(0, 0, 32767))).addContainerGap()));
        jPanel_painelLayout.setVerticalGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel_painelLayout.createSequentialGroup().addContainerGap().addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jLabel_titulo, -2, 32, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField_caminho, -2, 39, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel_operacoes, -2, -1, -2).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jPanel_outros, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTextField_formato))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jLabel_estado, -2, 57, -2).addContainerGap(-1, 32767)).addGroup(jPanel_painelLayout.createSequentialGroup().addComponent(this.jButton_ajuda, -2, 32, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton_abrir, -1, 39, 32767).addComponent(this.jButton_mostar_caminho, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel_painelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton_limpar, -2, 40, -2).addComponent(this.jButton_executar, -2, 40, -2)).addGap(6, 6, 6).addComponent(this.jButton_restaurar).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel_meu_nome)))));
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel_painel, -2, -1, -2));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel_painel, -2, -1, -2));
        pack();
        setLocationRelativeTo(null);
    }

    private void jCheckBox_formatoStateChanged(ChangeEvent evt) {
        if (getjCheckBox_formato().isSelected()) {
            getjTextField_formato().setVisible(true);
            getjTextField_formato().setSize(150, 28);
        } else {
            getjTextField_formato().setVisible(false);
        }
    }

    private void jButton_mostar_caminhoActionPerformed(ActionEvent evt) {
        if ((new File(getjTextField_caminho().getText()).isDirectory()) && (new File(getjTextField_caminho().getText()).exists()))
            JOptionPane.showMessageDialog(null, "Diretório " + getjTextField_caminho().getText() + "\n\nPasta onde será executada as operações!", "Diretório", 1);
        else if ("".equals(getjTextField_caminho().getText()))
            JOptionPane.showMessageDialog(null, "Selecione um diretório!", "Erro", 0);
        else JOptionPane.showMessageDialog(null, "Diretório inválido!", "Erro", 0);
    }

    private void jButton_restaurarActionPerformed(ActionEvent evt) {
        if (isExecucao()) {
            if (isSub_pasta()) try {
                removerOculto(2);
                setExecucao(false);
                setSub_pasta(false);
                JOptionPane.showMessageDialog(null, "Restauração executada com êxito!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao executar restauração!", "Erro", 0);
            }
            else JOptionPane.showMessageDialog(null, "Operação não permitida!", "Erro", 0);
        } else
            JOptionPane.showMessageDialog(null, "Nenhuma operação executada!\nExecute pelo menos uma operação!", "Erro", 0);
    }

    private void jButton_limparActionPerformed(ActionEvent evt) {
        getjLabel_estado().setText("");
        getjTextField_caminho().setText("");
        getjCheckBox_apagar_atalhos().setSelected(false);
        getjCheckBox_remover_oculto().setSelected(false);
        getjCheckBox_executar_sub_pasta().setSelected(false);
        getjCheckBox_formato().setSelected(false);
        getjTextField_formato().setText("");
        setExecucao(false);
        setSub_pasta(false);
    }

    private void jButton_executarActionPerformed(ActionEvent evt) {
        if ((!getjCheckBox_apagar_atalhos().isSelected()) && (!getjCheckBox_remover_oculto().isSelected())) {
            setExecucao(false);
            setSub_pasta(false);
            JOptionPane.showMessageDialog(null, "Marque em operações pelo menos\numa caixa de seleção!", "Erro", 0);
        } else {
            setCaminho(getjTextField_caminho().getText());
            setFile(new File(getCaminho()));
            if (getFile().isDirectory()) {
                if (getFile().exists()) {
                    getjLabel_estado().setText("Diretório selecionado com êxito!");
                    try {
                        boolean verificacao = true;
                        if (getjCheckBox_remover_oculto().isSelected()) {
                            if (getjCheckBox_formato().isSelected()) {
                                setFormato(retornarFormatoValido());
                                if (!"".equals(this.formato)) {
                                    removerOculto(1);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Favor informe um formato válido!", "Erro", 0);
                                    verificacao = false;
                                }
                            } else {
                                removerOculto(1);
                            }
                        }
                        if (getjCheckBox_apagar_atalhos().isSelected()) {
                            apagarAtalhos(1);
                        }
                        if (verificacao) {
                            setExecucao(true);
                            JOptionPane.showMessageDialog(null, "Comandos executados com êxito!");
                        }
                    } catch (Exception e) {
                        setExecucao(false);
                        setSub_pasta(false);
                        JOptionPane.showMessageDialog(null, "Erro ao executar os comandos!", "Erro", 0);
                    }
                }
            } else {
                getjLabel_estado().setText("Erro ao selecionar um diretório!");
                setExecucao(false);
                setSub_pasta(false);
                JOptionPane.showMessageDialog(null, "Favor selecionar um diretório válido!", "Erro", 0);
            }
        }
    }

    private void jButton_abrirActionPerformed(ActionEvent evt) {
        int retorno = getjFileChooser_seletor().showOpenDialog(null);
        if (retorno == 0) {
            getjTextField_caminho().setText(getjFileChooser_seletor().getSelectedFile().getAbsolutePath());
            getjLabel_estado().setText("Diretório selecionado com êxito!");
        } else {
            getjTextField_caminho().setText("");
            getjLabel_estado().setText("Erro ao selecionar um diretório!");
        }
    }

    private void jButton_ajudaActionPerformed(ActionEvent evt) {
        getTutorial().show();
    }

    private void jCheckBox_remover_ocultoStateChanged(ChangeEvent evt) {
        if (getjCheckBox_remover_oculto().isSelected()) {
            getjCheckBox_formato().setVisible(true);
            if (getjCheckBox_formato().isSelected()) {
                getjTextField_formato().setSize(150, 28);
                getjTextField_formato().setVisible(true);
            }
        } else {
            getjCheckBox_formato().setVisible(false);
            getjTextField_formato().setVisible(false);
        }
    }

    public Tutorial getTutorial() {
        return this.tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public JFileChooser getjFileChooser_seletor() {
        return this.jFileChooser_seletor;
    }

    public void setjFileChooser_seletor(JFileChooser jFileChooser_seletor) {
        this.jFileChooser_seletor = jFileChooser_seletor;
    }

    public String[] getTodosOcultos() {
        return this.TodosOcultos;
    }

    public void setTodosOcultos(String[] TodosOcultos) {
        this.TodosOcultos = TodosOcultos;
    }

    public String getCaminho() {
        return this.caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getFormato() {
        return this.formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isExecucao() {
        return this.execucao;
    }

    public void setExecucao(boolean execucao) {
        this.execucao = execucao;
    }

    public boolean isSub_pasta() {
        return this.sub_pasta;
    }

    public void setSub_pasta(boolean sub_pasta) {
        this.sub_pasta = sub_pasta;
    }

    public JButton getjButton_abrir() {
        return this.jButton_abrir;
    }

    public void setjButton_abrir(JButton jButton_abrir) {
        this.jButton_abrir = jButton_abrir;
    }

    public JButton getjButton_ajuda() {
        return this.jButton_ajuda;
    }

    public void setjButton_ajuda(JButton jButton_ajuda) {
        this.jButton_ajuda = jButton_ajuda;
    }

    public JButton getjButton_executar() {
        return this.jButton_executar;
    }

    public void setjButton_executar(JButton jButton_executar) {
        this.jButton_executar = jButton_executar;
    }

    public JButton getjButton_limpar() {
        return this.jButton_limpar;
    }

    public void setjButton_limpar(JButton jButton_limpar) {
        this.jButton_limpar = jButton_limpar;
    }

    public JButton getjButton_mostar_caminho() {
        return this.jButton_mostar_caminho;
    }

    public void setjButton_mostar_caminho(JButton jButton_mostar_caminho) {
        this.jButton_mostar_caminho = jButton_mostar_caminho;
    }

    public JButton getjButton_restaurar() {
        return this.jButton_restaurar;
    }

    public void setjButton_restaurar(JButton jButton_restaurar) {
        this.jButton_restaurar = jButton_restaurar;
    }

    public JCheckBox getjCheckBox_apagar_atalhos() {
        return this.jCheckBox_apagar_atalhos;
    }

    public void setjCheckBox_apagar_atalhos(JCheckBox jCheckBox_apagar_atalhos) {
        this.jCheckBox_apagar_atalhos = jCheckBox_apagar_atalhos;
    }

    public JCheckBox getjCheckBox_executar_sub_pasta() {
        return this.jCheckBox_executar_sub_pasta;
    }

    public void setjCheckBox_executar_sub_pasta(JCheckBox jCheckBox_executar_sub_pasta) {
        this.jCheckBox_executar_sub_pasta = jCheckBox_executar_sub_pasta;
    }

    public JCheckBox getjCheckBox_formato() {
        return this.jCheckBox_formato;
    }

    public void setjCheckBox_formato(JCheckBox jCheckBox_formato) {
        this.jCheckBox_formato = jCheckBox_formato;
    }

    public JCheckBox getjCheckBox_remover_oculto() {
        return this.jCheckBox_remover_oculto;
    }

    public void setjCheckBox_remover_oculto(JCheckBox jCheckBox_remover_oculto) {
        this.jCheckBox_remover_oculto = jCheckBox_remover_oculto;
    }

    public JLabel getjLabel_estado() {
        return this.jLabel_estado;
    }

    public void setjLabel_estado(JLabel jLabel_estado) {
        this.jLabel_estado = jLabel_estado;
    }

    public JLabel getjLabel_meu_nome() {
        return this.jLabel_meu_nome;
    }

    public void setjLabel_meu_nome(JLabel jLabel_meu_nome) {
        this.jLabel_meu_nome = jLabel_meu_nome;
    }

    public JLabel getjLabel_titulo() {
        return this.jLabel_titulo;
    }

    public void setjLabel_titulo(JLabel jLabel_titulo) {
        this.jLabel_titulo = jLabel_titulo;
    }

    public JPanel getjPanel_operacoes() {
        return this.jPanel_operacoes;
    }

    public void setjPanel_operacoes(JPanel jPanel_operacoes) {
        this.jPanel_operacoes = jPanel_operacoes;
    }

    public JPanel getjPanel_outros() {
        return this.jPanel_outros;
    }

    public void setjPanel_outros(JPanel jPanel_outros) {
        this.jPanel_outros = jPanel_outros;
    }

    public JPanel getjPanel_painel() {
        return this.jPanel_painel;
    }

    public void setjPanel_painel(JPanel jPanel_painel) {
        this.jPanel_painel = jPanel_painel;
    }

    public JTextField getjTextField_caminho() {
        return this.jTextField_caminho;
    }

    public void setjTextField_caminho(JTextField jTextField_caminho) {
        this.jTextField_caminho = jTextField_caminho;
    }

    public JTextField getjTextField_formato() {
        return this.jTextField_formato;
    }

    public void setjTextField_formato(JTextField jTextField_formato) {
        this.jTextField_formato = jTextField_formato;
    }
}