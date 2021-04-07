package me.JohnWhiting.rnc;

import javax.swing.*;

public class Checkbox extends JCheckBox {
    String text = "";
    int x = 0;
    int y = 0;
    int w = 0;
    int h = 0;
    boolean fromBottom = false;
    boolean fromRight = false;
    public Checkbox(String text, int x, int y, int w, int h) {
        this.text = text;
        this.setText(text);
        this.setVerticalTextPosition(AbstractButton.CENTER);
        this.setHorizontalTextPosition(AbstractButton.CENTER);
        this.setLayout(null);
        this.setHorizontalTextPosition(SwingConstants.RIGHT);
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
}
