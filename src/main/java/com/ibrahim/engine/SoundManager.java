package com.ibrahim.engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    Clip clip ;
    URL soundURL[] = new URL[30] ;

    public SoundManager() {
        soundURL[0] = getClass().getResource("/sound/bgm.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip() ;
            clip.open(ais) ;

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        clip.start() ;
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY) ;
    }

    public void stop() {
        clip.stop() ;
    }
}
