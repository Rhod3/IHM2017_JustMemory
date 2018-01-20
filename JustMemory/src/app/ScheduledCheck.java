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
        } else {
            
        }

        // Check si les JointPosition sont dans les targets
            // Si c'est le cas, continue
            // Sinon, GameOver

        // On change les targets à afficher (supposément, on prend les suivantes dans la liste

    }
}
