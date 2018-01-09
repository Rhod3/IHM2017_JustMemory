package app;

import edu.ufl.digitalworlds.j4k.Skeleton;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class AppContext {

    public static final int VIDEOFRAME_WIDTH = 730;
    public static final int VIDEOFRAME_HEIGHT = 570;

    private static AppContext context = null;

    private LinkedList<Target> targets = new LinkedList<>();
    private Skeleton[] skeletons;

    private AppContext() {
        skeletons = new Skeleton[6];
        // targets.add(new Target());
    }

    public static AppContext getInstance() {
        if (context == null) {
            context = new AppContext();
        }
        return context;
    }

    public void addSkeleton(Skeleton skeleton, int index) {
        skeletons[index] = skeleton;
    }

    public Skeleton[] getSkeletons() {
        return skeletons;
    }

    public void start() {
        TimerTask timerTask = new ScheduledCheck();

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000, 5000);
    }
}
