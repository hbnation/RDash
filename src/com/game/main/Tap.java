package com.game.main;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;

/**
 * Created by tluo on 7/7/2017.
 */
public class Tap extends NoteObject {

    public static ArrayList spaceNotes = new ArrayList(5);
    public static ArrayList fNotes = new ArrayList(5);
    public static ArrayList gNotes = new ArrayList(5);
    public static ArrayList hNotes = new ArrayList(5);
    public static ArrayList jNotes = new ArrayList(5);

    public Tap(Lane lane, int y, Note note, double hitTime, Handler handler) {
        super(lane, y, note);
        this.handler = handler;
        this.setHitTime(hitTime);
        switch (lane) {
            case LEFTLEFT: spaceNotes.add(this.getHitTime());
                break;
            case LEFT: fNotes.add(this.getHitTime());
                break;
            case LEFTCENTER:gNotes.add(this.getHitTime());
                break;
            case RIGHTCENTER: hNotes.add(this.getHitTime());
                break;
            case RIGHT: jNotes.add(this.getHitTime());
                break;
            default: spaceNotes.add(this.getHitTime());
        }
    }

    public void tick() {

        this.setY((int) Math.round((Conductor.songPosition + 1.0 - hitTime) / 1 * 500));
        if (this.y > 543.75) {
            switch (lane) {
                case LEFTLEFT:
                    spaceNotes.remove(this.getHitTime());
                    KeyInput.spaceResult = "MISS";
                    break;
                case LEFT:
                    fNotes.remove(this.getHitTime());
                    KeyInput.fResult = "MISS";
                    break;
                case LEFTCENTER:
                    gNotes.remove(this.getHitTime());
                    KeyInput.gResult = "MISS";
                    break;
                case RIGHTCENTER:
                    hNotes.remove(this.getHitTime());
                    KeyInput.hResult = "MISS";
                    break;
                case RIGHT:
                    jNotes.remove(this.getHitTime());
                    KeyInput.jResult = "MISS";
                    break;
            }
            KeyInput.combo = 0;
            handler.removeNote(this);
        }
        if (!KeyInput.hitLanes.isEmpty()) //removing notes after hit
            for (int i = 0; i < KeyInput.hitSetTimes.size(); i++) {
                if (KeyInput.hitLanes.get(i) == this.lane && KeyInput.hitSetTimes.get(i).equals(this.hitTime)) {
                    handler.removeNote(this);
                    KeyInput.hitLanes.remove(i);
                    KeyInput.hitSetTimes.remove(i);
                }
            }
    }


    public void render(Graphics g) {

        int x;
        switch (lane) {
            case LEFTLEFT: x = 105;
                break;
            case LEFT: x = 205;
                break;
            case LEFTCENTER: x = 305;
                break;
            case RIGHTCENTER: x = 405;
                break;
            case RIGHT: x = 505;
                break;
            default: x = 0;
        }
        Color seaBlue = new Color(102, 255, 255);
        Color orangeRed = new Color(251, 60, 7);
        g.setColor(seaBlue);
        if (lane == Lane.LEFTLEFT)
            g.setColor(orangeRed);
        g.fillRoundRect(x, y, 90, 20, 15, 5);

    }
}
