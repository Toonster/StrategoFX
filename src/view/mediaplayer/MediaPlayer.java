package view.mediaplayer;

import javafx.scene.media.AudioClip;

public final class MediaPlayer {

    private static AudioClip music = new AudioClip(MediaPlayer.class.getResource("/files/videoplayback.m4a").toString());
    private static boolean isMusicPlaying;

    public static void playMusic() {
        music.play();
        music.setCycleCount(100);
        isMusicPlaying = true;
    }

    public static void stopMusic() {
        music.stop();
        isMusicPlaying = false;
    }

    public static boolean isMusicPlaying() {
        return isMusicPlaying;
    }
}
