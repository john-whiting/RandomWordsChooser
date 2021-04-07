package me.JohnWhiting.rnc;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame extends JFrame {
    static Panel mpanel;
    static ArrayList<Button> buttons = new ArrayList<>();
    static ArrayList<Checkbox> checkboxes = new ArrayList<>();
    static ArrayList<Button> hideableButtons = new ArrayList<>();
    static ArrayList<Button> settingsButtons = new ArrayList<>();
    public Frame() {
        this.setTitle("Random Word Chooser");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);

        mpanel = new Panel();
        mpanel.setBackground(Color.BLUE);
        mpanel.setLayout(null);
        this.setContentPane(mpanel);
        this.setVisible(true);

        Checkbox cb_norepeat = new Checkbox("No-Repeat", 475, 40, 150, 30);
        cb_norepeat.fromBottom = true;
        cb_norepeat.fromRight = true;
        cb_norepeat.setSelected(true);
        checkboxes.add(cb_norepeat);
        mpanel.add(cb_norepeat);

        Button but_rand = new Button("Select", 325, 40, 150, 30);
        but_rand.setActionCommand("selectRand");
        but_rand.fromBottom = true;
        but_rand.fromRight = true;
        buttons.add(but_rand);
        mpanel.add(but_rand);
        but_rand.addActionListener(mpanel);

        Button but_reset = new Button("Reset", 165, 40, 150, 30);
        but_reset.setActionCommand("resetSel");
        but_reset.fromBottom = true;
        but_reset.fromRight = true;
        buttons.add(but_reset);
        mpanel.add(but_reset);
        but_reset.addActionListener(mpanel);

        Button but_settings = new Button("Settings", 140, 10, 125, 30);
        but_settings.setActionCommand("toggleHiddenButtons");
        but_settings.fromRight = true;
        buttons.add(but_settings);
        mpanel.add(but_settings);
        but_settings.addActionListener(mpanel);

        Button but_edit = new Button("Edit Profiles", 140, 50, 125, 30);
        but_edit.setActionCommand("openEditor");
        but_edit.fromRight = true;
        but_edit.setVisible(false);
        buttons.add(but_edit);
        hideableButtons.add(but_edit);
        mpanel.add(but_edit);
        but_edit.addActionListener(mpanel);

        addSettingsMenu();
    }

    public static void addSettingsMenu() {
        int counter = 0;
        for (JSONObject obj : ConfigManager.getProfiles()) {
            Button but_prof = new Button("Load "+ConfigManager.getCleanName(obj), 140, 90+40*counter, 125, 30);
            but_prof.setActionCommand(ConfigManager.getCleanName(obj));
            but_prof.fromRight = true;
            but_prof.setVisible(false);
            buttons.add(but_prof);
            hideableButtons.add(but_prof);
            settingsButtons.add(but_prof);
            mpanel.add(but_prof);
            but_prof.addActionListener(new ProfileManager());
            counter++;
        }
    }

    public static void reloadSettingsMenu() {
        for (Button btn : settingsButtons) {
            mpanel.remove(btn);
            buttons.remove(btn);
            hideableButtons.remove(btn);
        }
        settingsButtons = new ArrayList<>();
        addSettingsMenu();
        if (areHiddenButtonsVisible()) showHiddenButtons();
        else hideHiddenButtons();
    }

    public static boolean areHiddenButtonsVisible() {
        return hideableButtons.get(0).isVisible();
    }
    public static void showHiddenButtons() {
        for (Button btn : hideableButtons) {
            btn.setVisible(true);
        }
    }

    public static void hideHiddenButtons() {
        for (Button btn : hideableButtons) {
            btn.setVisible(false);
        }
    }

    public static void disableAllButtons() {
        for (Button btn : buttons) {
            btn.setEnabled(false);
        }
    }

    public static void enableAllButtons() {
        for (Button btn : buttons) {
            btn.setEnabled(true);
        }
    }
}
