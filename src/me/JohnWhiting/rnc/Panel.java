package me.JohnWhiting.rnc;

import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener {
    JSONArray words = ConfigManager.getWords(ProfileManager.curProfile);
    EditorFrame ep;
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color color1 = new Color( 66, 73, 131 );
        Color color2 = new Color( 89, 174, 232 );
        GradientPaint gp = new GradientPaint( 0, 0, color1, this.getWidth(), this.getHeight(), color2 );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        //g.setColor(Color.BLUE);
        //g.fillRect(0,0,this.getWidth(),this.getHeight());

        for (Button btn : Frame.buttons) {
            btn.updateBounds();
            btn.repaint();
        }for (Checkbox cb : Frame.checkboxes) {
            cb.updateBounds();
            cb.repaint();
        }

        int initX = 50;
        int initY = 50;
        int disX = 210;
        int disY = 34;
        int column = 0;
        int bottomBuffer = 60;
        NamePlate curSel = null;

        if (RandomPicker.getLength() == 0) {
            ArrayList<Integer> colRef = new ArrayList<Integer>();
            colRef.add(0, 0);
            for (int i = 0; i < words.length(); i++) {
                if ((30 + initY + (disY * (i - colRef.get(column)))) > (this.getHeight() - bottomBuffer)) {
                    column++;
                    colRef.add(column, i);
                }
                NamePlate np = new NamePlate(words.get(i).toString(), initX + (disX * column), initY + (disY * (i - colRef.get(column))), 200, 30);
                np.paint(g);
                RandomPicker.recordNamePlate(np);
            }
        } else {
            ArrayList<Integer> colRef = new ArrayList<Integer>();
            colRef.add(0, 0);
            for (int i = 0; i < RandomPicker.plates.size(); i++) {
                if ((30 + initY + (disY * (i - colRef.get(column)))) > (this.getHeight() - bottomBuffer)) {
                    column++;
                    colRef.add(column, i);
                }
                NamePlate np = RandomPicker.plates.get(i);
                np.updateBounds(initX + (disX * column), initY + (disY * (i - colRef.get(column))), 200, 30);
                if (np.status.equalsIgnoreCase("curselected") || np.status.equalsIgnoreCase("finselected")) curSel = np;
                np.paint(g);
            }
            if (curSel != null) {
                curSel.paint(g);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("selectRand")) {
            Frame.hideHiddenButtons();
            (new RandomPicker()).runRandomCycle();
        } else if(e.getActionCommand().equalsIgnoreCase("resetSel")) {
            RandomPicker.reset();
        } else if(e.getActionCommand().equalsIgnoreCase("toggleHiddenButtons")) {
            if (Frame.areHiddenButtonsVisible()) {
                Frame.hideHiddenButtons();
            } else {
                Frame.showHiddenButtons();
            }
        } else if(e.getActionCommand().equalsIgnoreCase("openEditor")) {
            if (ep == null) {
                ep = new EditorFrame();
            } else {
                ep.setVisible(true);
            }
        }
    }
}
