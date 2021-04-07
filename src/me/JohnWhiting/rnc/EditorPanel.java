package me.JohnWhiting.rnc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorPanel extends JPanel implements ActionListener {

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = new Color( 66, 73, 131 );
        Color color2 = new Color( 89, 174, 232 );
        GradientPaint gp = new GradientPaint( 0, 0, color1, this.getWidth(), this.getHeight(), color2 );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Button btn : EditorFrame.buttons) {
            btn.updateBounds();
            btn.repaint();
        }
        for (TextField tf : EditorFrame.textfields) {
            tf.updateBounds();
            tf.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("selectRand")) {
            (new RandomPicker()).runRandomCycle();
        }
    }
}
