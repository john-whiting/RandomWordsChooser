package me.JohnWhiting.rnc;

import java.awt.*;

public class NamePlate {
    String name = "";
    String status = "default";
    int x = 50;
    int y = 50;
    int w = 100;
    int h = 20;

    public NamePlate(String name) {
        this.name = name;
    }

    public NamePlate(String name, int x, int y, int w, int h) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void updateBounds(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void paint(Graphics g) {
        switch (this.status) {
            case "default":
                g.setColor(Color.WHITE);
                g.fillRoundRect(x, y, w, h, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, y, w, h, 20, 20);
                break;
            case "selected":
                g.setColor(new Color(145, 115, 218));
                g.fillRoundRect(x, y, w, h, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, y, w, h, 20, 20);
                break;
            case "curselected":
                int xDis = 10;
                int yDis = 5;
                int wDis = 30;
                int hDis = 10;
                g.setColor(new Color(145, 115, 218));
                g.fillRoundRect(x - xDis, y - yDis, w + wDis, h + hDis, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x - xDis, y - yDis, w + wDis, h + hDis, 20, 20);
                break;
            case "finselected":
                int xdis = 10;
                int ydis = 5;
                int wdis = 20;
                int hdis = 10;
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                Color color1 = new Color( 183, 233, 189 );
                Color color2 = new Color( 77, 152, 90 );
                GradientPaint gp = new GradientPaint( x+w/2, y, color1, x+w/2, y+h, color2 );
                g2d.setPaint(gp);
                g2d.fillRoundRect(x - xdis, y - ydis, w + wdis, h + hdis, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x - xdis, y - ydis, w + wdis, h + hdis, 20, 20);
                break;
        }
        g.setColor(Color.BLACK);

        FontMetrics metrics = g.getFontMetrics();

        int sw = metrics.stringWidth(this.name);
        int sh = metrics.getHeight();
        int px = this.x + (this.w - sw) / 2;
        int py = this.y + ((this.h - sh) / 2) + metrics.getAscent();
        g.drawString(this.name, px, py);
    }
}
