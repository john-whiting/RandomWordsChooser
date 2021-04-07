package me.JohnWhiting.rnc;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomPicker {
    static ArrayList<NamePlate> plates = new ArrayList<>();
    static ArrayList<NamePlate> notSelectedPlates = new ArrayList<>();
    static NamePlate prevSelectedPlate;
    static NamePlate prevFinalPlate;
    static ArrayList<NamePlate> finalSelectedPlates = new ArrayList<>();

    public static int getLength() {
        return plates.size();
    }
    public static void reset() {
        notSelectedPlates = new ArrayList<>();
        finalSelectedPlates = new ArrayList<>();
        prevSelectedPlate = null;
        prevFinalPlate = null;
        for (NamePlate np : plates) {
            np.status = "default";
            notSelectedPlates.add(np);
        }
        Frame.mpanel.repaint();
    }
    public static void recordNamePlate(NamePlate np) {
        plates.add(np);
        notSelectedPlates.add(np);
    }

    public void runRandomCycle() {
        if (!Frame.checkboxes.get(0).isSelected()) reset();
        Frame.disableAllButtons();
        int counter = 0;
        Timer timer = new Timer();

        if (notSelectedPlates.size() == 0){
            Frame.enableAllButtons();
            return;
        }
        for (int i=0; i < 8; i++) {
            if (i<7) {
                timer.schedule(new ttMidSel(), (long) 150*(i));
            } else {
                timer.schedule(new ttFinSel(), (long) 150*(i));
            }
        }
    }

    static class selectRandomPlate implements Runnable {
        public void run() {
            Random rand = new Random( System.currentTimeMillis() );
            int index = rand.nextInt( plates.size() );
            NamePlate np = plates.get(index);
            if (np.status.equalsIgnoreCase("selected")) {
                Frame.mpanel.repaint();
            }
            if (prevFinalPlate != null) {
                prevFinalPlate.status = "selected";
            }
            if (prevSelectedPlate != null) {
                if (!prevSelectedPlate.status.equalsIgnoreCase("selected")) prevSelectedPlate.status = "default";
            }
            if (np.status.equalsIgnoreCase("selected")) Frame.mpanel.repaint();
            else np.status = "curselected";
            prevSelectedPlate = np;
            Frame.mpanel.repaint();

            Clip midSelSound;
            try {
                AudioInputStream midSelSoundIS = AudioSystem.getAudioInputStream(ResourceManager.getResource("midselected_sound.wav"));
                midSelSound = AudioSystem.getClip();
                midSelSound.open(midSelSoundIS);
                midSelSound.setMicrosecondPosition(0L);
                midSelSound.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    static class selectFinalPlate implements Runnable {
        public void run() {
            Random rand = new Random( System.currentTimeMillis() );
            int index = rand.nextInt( notSelectedPlates.size() );
            NamePlate np = notSelectedPlates.get(index);

            if (prevSelectedPlate != null) {
                if (!prevSelectedPlate.status.equalsIgnoreCase("selected")) prevSelectedPlate.status = "default";
            }
            addFinalSelection(np);
            Frame.mpanel.repaint();

            Clip finSelSound;
            try {
                AudioInputStream finSelSoundIS = AudioSystem.getAudioInputStream(ResourceManager.getResource("finalselected_sound.wav"));
                finSelSound = AudioSystem.getClip();
                finSelSound.open(finSelSoundIS);
                finSelSound.setMicrosecondPosition(0L);
                finSelSound.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addFinalSelection(NamePlate np) {
        prevFinalPlate = np;
        finalSelectedPlates.add(np);
        notSelectedPlates.remove(np);
        np.status = "finselected";
        Frame.enableAllButtons();
    }

    class ttMidSel extends TimerTask {
        @Override
        public void run() {
            Thread t = new Thread(new selectRandomPlate());
            t.start();
        }
    }
    class ttFinSel extends TimerTask {
        @Override
        public void run() {
            Thread t = new Thread(new selectFinalPlate());
            t.start();
        }
    }
}
