package de.ur.mi.android.soundmachine.sounds;

public class SoundProxy {

    public final int id;
    public final String title;
    public final SoundState state;

    public SoundProxy(int id, String title, SoundState state) {
        this.id = id;
        this.title = title;
        this.state = state;
    }
}
