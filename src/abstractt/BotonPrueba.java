/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

/**
 *
 * @author Home
 */
public class BotonPrueba extends JButton {
int ax = 30, ay = 30;
    public BotonPrueba() {
        this.setBackground(FormatoControles.color1);
        this.setForeground(FormatoControles.color2);
        this.setFont(FormatoControles.Fuente1);
        setContentAreaFilled(false);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(new Color(94, 67, 34));
        } else {
            g.setColor(new Color(64, 37, 34));
        }
            g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, ax, ay);
        super.paintComponent(g);
    }
//Sobreescritura del borde 

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, ax, ay);
    }

    Shape figura;

    @Override
    public boolean contains(int x, int y) {
            figura = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            figura = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ax, ay);
        return (figura.contains(x, y));
    }
}

