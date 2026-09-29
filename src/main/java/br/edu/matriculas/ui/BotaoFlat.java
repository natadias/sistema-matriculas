package br.edu.matriculas.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

class BotaoFlat extends JButton {

    private final Color corFundo;
    private final Color corFundoHover;
    private final Color corBorda;
    private boolean sobreOBotao;

    BotaoFlat(String texto, Color corTexto, Color corFundo, Color corFundoHover, Color corBorda) {
        super(texto);
        this.corFundo = corFundo;
        this.corFundoHover = corFundoHover;
        this.corBorda = corBorda;

        setForeground(corTexto);
        setFont(Estilos.FONTE_BOTAO);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sobreOBotao = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sobreOBotao = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(sobreOBotao ? corFundoHover : corFundo);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        if (corBorda != null) {
            g2.setColor(corBorda);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
