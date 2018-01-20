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

        // First time we run
        if (state == 0) {
            appContext.figureToDisplay = appContext.figures.get(runNumber);

            int index = runNumber - offset;
            if (index >= 0) {
                appContext.figureToCheck = appContext.figures.get(runNumber);
            }
            state = 1;
        }
        else if (state == 1) {
            boolean targetOK = true;

            for (Target target : appContext.figureToCheck.targets){
                targetOK &= target.isSkeletonsInTarget(appContext.getSkeletons());
            }
            if (targetOK) {
                runNumber++;
                appContext.figureToDisplay = appContext.figures.get(runNumber);

                int index = runNumber - offset;
                if (index >= 0) {
                    appContext.figureToCheck = appContext.figures.get(runNumber);
                }
            } else {
                state = 2;
                System.out.println("YOU LOSE");
            }
        }
        else if (state == 2) {
            System.exit(1);
        }
    }
}
