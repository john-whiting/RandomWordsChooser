package me.JohnWhiting.rnc;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    String text = "";
    int x = 0;
    int y = 0;
    int w = 0;
    int h = 0;
    boolean fromBottom = false;
    boolean fromRight = false;
    public Button(String text, int x, int y, int w, int h) {
        this.text = text;
        this.setText(text);
        this.setVerticalTextPosition(AbstractButton.CENTER);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setLayout(null);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.setLocation(Frame.mpanel.getWidth() - x, Frame.mpanel.getHeight() - y);
        this.setSize(w, h);
        this.setContentAreaFilled(false);
    }

    public void updateBounds() {
        if (this.fromBottom && this.fromRight) {
            this.setLocation(Frame.mpanel.getWidth() - this.x, Frame.mpanel.getHeight() - this.y);
        } else if (this.fromBottom) {
            this.setLocation(this.x, Frame.mpanel.getHeight() - this.y);
        } else if (this.fromRight) {
            this.setLocation(Frame.mpanel.getWidth() - this.x, this.y);
        } else {
            this.setLocation( this.x, this.y );
        }
        this.setSize(this.w, this.h);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = new Color( 66, 73, 131 );
        Color color2 = new Color( 89, 174, 232 );
        GradientPaint gp = new GradientPaint(0, 0, color1, this.getWidth(), this.getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.dispose();

        g.setColor(Color.WHITE);
        FontMetrics metrics = g.getFontMetrics();

        int sw = metrics.stringWidth(this.text);
        int sh = metrics.getHeight();
        int px = (this.w - sw) / 2;
        int py = ((this.h - sh) / 2) + metrics.getAscent();
        g.drawString(this.text, px, py);
        //super.paintComponent(g);
    }
}
