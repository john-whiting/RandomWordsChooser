package me.JohnWhiting.rnc;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EditorFrame extends JFrame {
    static EditorPanel mpanel;
    static ArrayList<Button> buttons = new ArrayList<>();
    static ArrayList<TextField> textfields = new ArrayList<>();
    static ArrayList<JComponent> editingTools = new ArrayList<>();
    static ArrayList<Button> editButtons = new ArrayList<>();
    public EditorFrame() {
        this.setTitle("RWC Editor");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setBackground(Color.BLUE);

        mpanel = new EditorPanel();
        mpanel.setBackground(Color.BLUE);
        mpanel.setLayout(null);
        this.setContentPane(mpanel);
        this.setVisible(true);

        addEditButtons();

        addEditingTools();

        Button but_createnew = new Button("Create New Profile", 10, 40, 145, 30);
        but_createnew.setActionCommand("createNewProfile");
        but_createnew.fromBottom = true;
        buttons.add(but_createnew);
        mpanel.add(but_createnew);
        but_createnew.addActionListener(new ConfigManager());
    }

    public static void addEditButtons() {
        int counter = 0;
        for (JSONObject obj : ConfigManager.getProfiles()) {
            Button but_prof = new Button("Edit "+ConfigManager.getCleanName(obj), 10, 10+40*counter, 125, 30);
            but_prof.setActionCommand(ConfigManager.getCleanName(obj));
            but_prof.setVisible(true);
            buttons.add(but_prof);
            editButtons.add(but_prof);
            mpanel.add(but_prof);
            but_prof.addActionListener(new ConfigManager());
            counter++;
        }
    }

    public static void reloadEditButtons() {
        for (Button btn : editButtons) {
            mpanel.remove(btn);
            buttons.remove(btn);
        }
        editButtons = new ArrayList<>();
        addEditButtons();
    }

    public static void addEditingTools() {
        Button but_del = new Button("Delete Profile", 150, 90, 125, 30);
        but_del.setActionCommand("deleteProfile");
        but_del.fromBottom = true;
        but_del.fromRight = true;
        but_del.setVisible(false);
        buttons.add(but_del);
        editingTools.add(but_del);
        mpanel.add(but_del);
        but_del.addActionListener(new ConfigManager());

        Button but_save = new Button("Save Profile", 150, 40, 125, 30);
        but_save.setActionCommand("saveProfile");
        but_save.fromBottom = true;
        but_save.fromRight = true;
        but_save.setVisible(false);
        buttons.add(but_save);
        editingTools.add(but_save);
        mpanel.add(but_save);
        but_save.addActionListener(new ConfigManager());

        TextField tf_cleanname = new TextField(ConfigManager.curESelectedProf, 155, 10, 400, 30);
        tf_cleanname.setVisible(false);
        textfields.add(tf_cleanname);
        editingTools.add(tf_cleanname);
        mpanel.add(tf_cleanname);

        TextField tf_wordlist = new TextField("", 155, 50, 500, 30);
        tf_wordlist.setVisible(false);
        textfields.add(tf_wordlist);
        editingTools.add(tf_wordlist);
        mpanel.add(tf_wordlist);
    }

    public static void showEditingTools() {
        for (JComponent c : EditorFrame.editingTools) {
            c.setVisible(true);
        }
    }

    public static void hideEditingTools() {
        for (JComponent c : EditorFrame.editingTools) {
            c.setVisible(false);
        }
    }
}
