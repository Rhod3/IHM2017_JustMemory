package app;

import java.util.TimerTask;

public class TargetStateUpdater extends TimerTask {

    AppContext appContext = AppContext.getInstance();

    @Override
    public void run() {
        System.out.println("Update Color");
        if (appContext.figureToCheck != null) {
            for (int i = 0; i < appContext.figureToCheck.targets.size(); i++) {
                if (appContext.figureToCheck.targets.get(i).isSkeletonsInTarget(appContext.getSkeletons())) {
                    appContext.figureToCheck.targets.get(i).setOK(true);
                } else {
                    appContext.figureToCheck.targets.get(i).setOK(false);
                }
            }
        }
    }
}
