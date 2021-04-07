package me.JohnWhiting.rnc;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.io.IOException;

public class Main extends Applet {
    public static void main(String[] args) throws Exception {
        ConfigManager.loadConfig("./config.json",ResourceManager.getResource("config.json").toURI());

        // Load sounds before hand
        try {
            Clip midSelSound;
            AudioInputStream midSelSoundIS = AudioSystem.getAudioInputStream(ResourceManager.getResource("midselected_sound.wav"));
            midSelSound = AudioSystem.getClip();
            midSelSound.open(midSelSoundIS);
            midSelSound.setMicrosecondPosition(0L);

            Clip finSelSound;
            AudioInputStream finSelSoundIS = AudioSystem.getAudioInputStream(ResourceManager.getResource("finalselected_sound.wav"));
            finSelSound = AudioSystem.getClip();
            finSelSound.open(finSelSoundIS);
            finSelSound.setMicrosecondPosition(0L);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        Frame p = new Frame();
    }
}
