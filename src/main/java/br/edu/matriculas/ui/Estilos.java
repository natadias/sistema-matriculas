package br.edu.matriculas.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public final class Estilos {

    static final Color PRIMARIA = new Color(0x25, 0x63, 0xEB);
    static final Color PRIMARIA_ESCURA = new Color(0x1D, 0x4E, 0xD8);
    static final Color FUNDO = new Color(0xF8, 0xFA, 0xFC);
    static final Color FUNDO_CARTAO = Color.WHITE;
    static final Color BORDA = new Color(0xE2, 0xE8, 0xF0);
    static final Color TEXTO = new Color(0x1E, 0x29, 0x3B);
    static final Color TEXTO_CLARO = Color.WHITE;
    static final Color TEXTO_SECUNDARIO = new Color(0x64, 0x74, 0x8B);
    static final Color ZEBRA = new Color(0xF1, 0xF5, 0xF9);
    static final Color PRIMARIA_HOVER_CLARO = new Color(0xEF, 0xF4, 0xFF);

    static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    static final Font FONTE_CABECALHO = new Font("Segoe UI", Font.BOLD, 16);
    static final Font FONTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);

    private Estilos() {
    }

    public static void aplicarLookAndFeel() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                } catch (Exception ignorada) {
                    break;
                }
            }
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorada) {
        }
    }

    static JPanel cabecalho(String texto) {
        JPanel painel = new JPanel();
        painel.setBackground(PRIMARIA);
        painel.setBorder(new EmptyBorder(16, 20, 16, 20));
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_CABECALHO);
        label.setForeground(TEXTO_CLARO);
        painel.add(label);
        return painel;
    }

    static void atualizarCabecalho(JPanel cabecalho, String texto) {
        ((JLabel) cabecalho.getComponent(0)).setText(texto);
    }

    static JButton botaoPrimario(String texto) {
        return new BotaoFlat(texto, TEXTO_CLARO, PRIMARIA, PRIMARIA_ESCURA, null);
    }

    static JButton botaoSecundario(String texto) {
        return new BotaoFlat(texto, PRIMARIA, FUNDO_CARTAO, PRIMARIA_HOVER_CLARO, PRIMARIA);
    }

    static JPanel painelConteudo() {
        JPanel painel = new JPanel();
        painel.setBackground(FUNDO);
        painel.setBorder(new EmptyBorder(20, 24, 20, 24));
        return painel;
    }

    static JLabel rotulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_NORMAL);
        label.setForeground(TEXTO_SECUNDARIO);
        return label;
    }

    static <T> void estilizarLista(JList<T> lista) {
        lista.setFont(FONTE_NORMAL);
        lista.setFixedCellHeight(30);
        lista.setBackground(FUNDO_CARTAO);
        lista.setSelectionBackground(PRIMARIA);
        lista.setSelectionForeground(TEXTO_CLARO);
        lista.setBorder(new EmptyBorder(4, 8, 4, 8));
        ListCellRenderer<Object> original = new JList<>().getCellRenderer();
        lista.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            Component c = original.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (c instanceof JLabel label) {
                label.setBorder(new EmptyBorder(4, 10, 4, 10));
                if (!isSelected) {
                    label.setBackground(index % 2 == 0 ? FUNDO_CARTAO : ZEBRA);
                }
            }
            return c;
        });
    }

    static JScrollPane comBorda(Component conteudo) {
        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(BorderFactory.createLineBorder(BORDA, 1));
        return scroll;
    }

    static JLabel titulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(FONTE_TITULO);
        label.setForeground(TEXTO);
        return label;
    }
}
