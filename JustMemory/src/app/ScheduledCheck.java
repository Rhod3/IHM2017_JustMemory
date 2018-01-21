package app;

import java.util.TimerTask;

public class ScheduledCheck extends TimerTask {

    private int state = 0;
    AppContext appContext = AppContext.getInstance();

    private int runNumber = 0;
    private int offset = appContext.getMemoryOffset();

    // Cette fonction sera appelée toutes les 5 secondes
    @Override
    public void run() {
        System.out.println("ScheduledTask RUN");

        // First time we run
        if (state == 0) {

            appContext.figureToDisplay = appContext.figures.get(runNumber);

            if (offset == 0){
                appContext.figureToCheck = appContext.figures.get(0);
            }

            state = 1;
            System.out.println("ScheduledTask INITIALIZED");
        }
        // Standard checking rules
        else if (state == 1) {
            System.out.println("ScheduledTask CHECKING");

            boolean targetOK = true;

            if (appContext.figureToCheck != null) {

                for (Target target : appContext.figureToCheck.targets) {
                    targetOK &= target.isSkeletonsInTarget(appContext.getSkeletons());
                }

                if (targetOK) {
                    System.out.println("ScheduledTask TARGETS OK");

                    runNumber = (runNumber + 1) % appContext.figures.size();
                    appContext.figureToDisplay = appContext.figures.get(runNumber);

                    // If we already have a figure to check, we can go "backward" in the array
                    if (appContext.figureToCheck != null) {
                        appContext.figureToCheck =
                                appContext.figures.get((runNumber - offset)%appContext.figures.size());
                    }
                } else {
                    state = 2;
                    System.out.println("YOU LOSE");
                }
            } else {
                // Advance in the figure to display
                runNumber = (runNumber + 1) % appContext.figures.size();
                appContext.figureToDisplay = appContext.figures.get(runNumber);

                // Handle the first figure to check
                if (runNumber > offset) {
                    appContext.figureToCheck =
                            appContext.figures.get((runNumber - offset) % appContext.figures.size());
                }
            }
        }
        // Check failed earlier :(
        else if (state == 2) {
            System.exit(1);
        }
    }
}
