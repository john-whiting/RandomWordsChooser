package me.JohnWhiting.rnc;

import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileManager implements ActionListener {
    static JSONObject curProfile = ConfigManager.getProfiles().get(0);

    public static void loadProfile(JSONObject obj) {
        curProfile = obj;
        RandomPicker.plates = new ArrayList<>();
        Frame.mpanel.words = ConfigManager.getWords(obj);
        RandomPicker.reset();
    }

    public static JSONObject getJObjFromName(String cleanname) {
        for (JSONObject obj : ConfigManager.getProfiles()) {
            if (cleanname.equals(obj.getString("cleanname"))) {
                return obj;
            }
        }
        return ConfigManager.getProfiles().get(0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConfigManager.loadConfig("./config.json",ResourceManager.getResource("config.json").toURI());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        loadProfile(getJObjFromName(e.getActionCommand()));
    }
}
