package me.JohnWhiting.rnc;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;

public class ConfigManager implements ActionListener {
    static JSONObject ajson;
    static File jsonf;
    static String curESelectedProf;

    public static void loadConfig(String name, URI backup) throws Exception {
        if ((System.getProperty("DevMode")) != null) {
            InputStream is = backup.toURL().openStream();
            String jsontxt = IOUtils.toString(is, "UTF-8");
            ajson = new JSONObject(jsontxt);
            return;
        }else {
            jsonf = new File(name);
            if (!jsonf.exists()) {
                InputStream is = backup.toURL().openStream();
                OutputStream os;
                String jarFolder = new File(ConfigManager.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
                os = new FileOutputStream(jarFolder + name);

                int readBytes;
                byte[] buffer = new byte[4096];
                while ((readBytes = is.read(buffer)) > 0) {
                    os.write(buffer, 0, readBytes);
                }
            }
            InputStream is = jsonf.toURI().toURL().openStream();
            String jsontxt = IOUtils.toString(is, "UTF-8");
            ajson = new JSONObject(jsontxt);
        }
    }

    public static void saveFile() {
        FileWriter fw;
        try {
            //ObjectMapper mapper = new ObjectMapper();
            //mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            fw = new FileWriter(jsonf); //mapper.writerWithDefaultPrettyPrinter();
            //fw.writeValue(jsonf, ajson);
            fw.write(ajson.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<JSONObject> getProfiles() {
        Iterator<String> keys = ajson.keys();
        ArrayList<JSONObject> objects = new ArrayList<>();
        while(keys.hasNext()) {
            String key = keys.next();
            objects.add(ajson.getJSONObject(key));
        }
        return objects;
    }

    public static String getCleanName(JSONObject obj) {
        return obj.getString("cleanname");
    }

    public static JSONArray getWords(JSONObject obj) {
        return obj.getJSONArray("words");
    }

    public static void createNewProfile() {
        String key = ""+(ajson.keySet().size());
        JSONObject obj = new JSONObject();
        obj = profileSetCleanName(obj, "New Profile");
        obj = profileSetWords(obj, "Separate Words, With Comma");

        ajson.put(key, obj);
        saveFile();
    }

    public static void deleteProfile(String key) {
        ajson.remove(key);
        saveFile();
    }

    public static void submitProfileChanges(JSONObject obj) {
        String cleanName = EditorFrame.textfields.get(0).getText();
        String sValues = EditorFrame.textfields.get(1).getText();
        profileSetCleanName(obj, cleanName);
        profileSetWords(obj, sValues);
    }

    public static void submitEditedProfile(String key, JSONObject obj) {
        ajson.put(key, obj);
        saveFile();
    }

    public static JSONArray convertStringToJSONArray(String sValues) {
        String[] values = sValues.split(",");
        JSONArray arr = new JSONArray();
        for (String v : values) {
            arr.put(v);
        }
        return arr;
    }

    public static JSONObject profileSetWords(JSONObject obj, String sValues) {
        JSONArray arr = convertStringToJSONArray(sValues);
        obj.put("words", arr);
        return obj;
    }

    public static JSONObject profileSetCleanName(JSONObject obj, String cleanName) {
        obj.put("cleanname", cleanName);
        return obj;
    }

    public static JSONObject getJObjByName(String cleanName) {
        for (JSONObject obj : getProfiles()) {
            if (getCleanName(obj).equals(cleanName)) {
                return obj;
            }
        }
        return null;
    }
    public static String getKeyByName(String cleanName) {
        Iterator<String> keys = ajson.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (getCleanName(ajson.getJSONObject(key)).equals(cleanName)) {
                return key;
            }
        }
        return null;
    }

    public static JSONObject saveJSONObj(String key, JSONObject obj) {
        submitProfileChanges(obj);
        submitEditedProfile(key, obj);
        return obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "createNewProfile":
                createNewProfile();
                EditorFrame.mpanel.repaint();
                EditorFrame.reloadEditButtons();
                Frame.mpanel.repaint();
                Frame.reloadSettingsMenu();
                return;
            case "deleteProfile":
                deleteProfile(getKeyByName(curESelectedProf));
                EditorFrame.hideEditingTools();
                EditorFrame.mpanel.repaint();
                EditorFrame.reloadEditButtons();
                Frame.mpanel.repaint();
                Frame.reloadSettingsMenu();
                return;
            case "saveProfile":
                JSONObject obj = saveJSONObj(getKeyByName(curESelectedProf), getJObjByName(curESelectedProf));
                EditorFrame.hideEditingTools();
                EditorFrame.mpanel.repaint();
                EditorFrame.reloadEditButtons();
                Frame.mpanel.repaint();
                Frame.reloadSettingsMenu();
                Frame.mpanel.ep.setVisible(false);
                ProfileManager.loadProfile(obj);
                return;
        }
        curESelectedProf = e.getActionCommand();
        EditorFrame.showEditingTools();
        EditorFrame.textfields.get(0).setText(curESelectedProf);
        EditorFrame.textfields.get(1).setText(getWords(getJObjByName(curESelectedProf)).toString().replace("[", "").replace("]","").replace("\"",""));
    }
}
