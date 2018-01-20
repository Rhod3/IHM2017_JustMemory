package app;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A figure is simply an aggregation of Targets. It modelises a position a player
 * has to be in.
 */
public class Figure {
    ArrayList<Target> targets;

    public Figure(Target... targets) {
        this.targets = new ArrayList<>(Arrays.asList(targets));
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void addTarget(Target target){
        targets.add(target);
    }

    public void displayGL(GL2 gl) {
        for(Target target : targets){
            target.drawInGL(gl);
        }
    }
}
