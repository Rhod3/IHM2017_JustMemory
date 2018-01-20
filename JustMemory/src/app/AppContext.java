package app;

import edu.ufl.digitalworlds.j4k.Skeleton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AppContext {

    // Singleton
    private static AppContext context = null;

    // Video resolution
    public static final int VIDEOFRAME_WIDTH = 730;
    public static final int VIDEOFRAME_HEIGHT = 570;


    // ===== Var for the game logic =====

    // Initialized offset
    private int memoryOffset;
    // Currently tracked skeletons
    private Skeleton[] skeletons;
    // All the figures that will take part in the games
    private ArrayList<Figure> figures = new ArrayList<>();
    // Which figure to display and to check
    public Figure figureToCheck, figureToDisplay;


    /**
     * Init the game variables
     */
    private AppContext() {
        skeletons = new Skeleton[6];

        Target targetTest = new Target(new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f});

        Figure figure = new Figure(targetTest);

        figures.add(figure);

        figureToDisplay = figures.get(0);
    }

    /**
     * Get the singleton
     *
     * @return The app contexr
     */
    public static AppContext getInstance() {
        if (context == null) {
            context = new AppContext();
        }
        return context;
    }

    // ==== Getters and Setters
    public void addSkeleton(Skeleton skeleton, int index) {
        skeletons[index] = skeleton;
    }

    public Skeleton[] getSkeletons() {
        return skeletons;
    }

    public int getMemoryOffset() {
        return memoryOffset;
    }

    public void setMemoryOffset(int memoryOffset) {
        this.memoryOffset = memoryOffset;
    }


    // Start the scheduler to change the game state
    public void start() {
        TimerTask timerTask = new ScheduledCheck();

        Timer timer = new Timer();
        timer.schedule(timerTask, 2000, 5000);
    }
}
