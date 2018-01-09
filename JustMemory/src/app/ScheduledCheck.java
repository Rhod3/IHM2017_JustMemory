package app;

import java.util.TimerTask;

public class ScheduledCheck extends TimerTask {

    // Cette fonction sera appelée toutes les 5 secondes
    @Override
    public void run() {
        AppContext appContext = AppContext.getInstance();

        // Check si les JointPosition sont dans les targets
            // Si c'est le cas, continue
            // Sinon, GameOver

        // On change les targets à afficher (supposément, on prend les suivantes dans la liste

    }
}
