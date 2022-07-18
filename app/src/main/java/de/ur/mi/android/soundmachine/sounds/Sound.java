package de.ur.mi.android.soundmachine.sounds;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class Sound implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static int nextId = 0;
    private final int id;
    private final String title;
    private SoundState state;
    private final MediaPlayer player;
    private SoundStatusListener listener;

    public Sound(AssetFileDescriptor file, String title, SoundStatusListener listener) {
        this.title = title;
        this.id = nextId++;
        this.listener = listener;
        this.state = SoundState.LOADING;
        this.player = createMediaPLayerFromFile(file);
    }

    private MediaPlayer createMediaPLayerFromFile(AssetFileDescriptor file) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);
            player.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getDeclaredLength());
            player.prepareAsync();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return player;
    }

    public int getId() {
        return id;
    }

    public SoundState getState() {
        return state;
    }

    public SoundProxy getProxyForCurrentState() {
        return new SoundProxy(id, title, state);
    }

    public void play() {
        if (state != SoundState.READY) {
            return;
        }
        player.start();
        state = SoundState.PLAYING;
        listener.onSoundStateChanged(this);
    }

    public void stop() {
       if (state != SoundState.PLAYING) {
           return;
       }
       player.pause();
       player.seekTo(0);
       state = SoundState.READY;
       listener.onSoundStateChanged(this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        state = SoundState.READY;
        // listener.onSoundPrepared(this);
        listener.onSoundStateChanged(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        state = SoundState.READY;
        listener.onSoundStateChanged(this);
    }
}
