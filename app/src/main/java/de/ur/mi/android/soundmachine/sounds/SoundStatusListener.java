package de.ur.mi.android.soundmachine.sounds;

public interface SoundStatusListener {

    // TODO Check if method will be used
    // Will be called when sound was initially prepared/loaded
    void onSoundPrepared(Sound sound);

    // Will be called when state of sound has changed
    void onSoundStateChanged(Sound sound);
}
