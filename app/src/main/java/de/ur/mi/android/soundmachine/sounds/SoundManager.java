package de.ur.mi.android.soundmachine.sounds;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.util.ArrayList;

public class SoundManager implements SoundStatusListener {

    private final Context context;
    private final SoundStatusListener listener;
    private final ArrayList<Sound> sounds;

    public SoundManager(Context context, SoundStatusListener listener) {
        this.context = context;
        this.listener = listener;
        this.sounds = new ArrayList<>();
    }

    public void loadSounds() {
        sounds.clear();
        for (SoundAsset asset : SoundAsset.values()) {
            AssetFileDescriptor file = context.getResources().openRawResourceFd(asset.id);
            Sound sound = new Sound(file, asset.title, this);
            sounds.add(sound);
        }
    }

    public void toggleSound(int id) {
        Sound sound = getSoundById(id);
        if (sound == null) {
            return;
        }
        if (sound.getState() == SoundState.PLAYING) {
            sound.stop();
        } else if (sound.getState() == SoundState.READY) {
            sound.play();
        }
    }

    private Sound getSoundById(int id) {
        for (Sound sound : sounds) {
            if (sound.getId() == id) {
                return sound;
            }
        }
        return null;
    }

    @Override
    public void onSoundPrepared(Sound sound) {
        listener.onSoundPrepared(sound);
    }

    @Override
    public void onSoundStateChanged(Sound sound) {
        listener.onSoundStateChanged(sound);
    }
}
