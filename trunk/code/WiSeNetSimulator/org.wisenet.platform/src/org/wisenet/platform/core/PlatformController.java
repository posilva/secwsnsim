package org.wisenet.platform.core;

import org.wisenet.platform.PlatformView;

/**
 *
 * @author Pedro Marques da SIlva
 */
public class PlatformController {

    protected static PlatformController instance;
    protected PlatformView platformView;
    protected boolean newSimulation;
    protected boolean changed;
    protected String activeSimulationPath;
    protected String activeSimulationName;
    protected String applicationDefaultTitle="WiSeNet Simulator";

    /**
     * Singleton Instance
     * @return
     */
    public static PlatformController getInstance() {
        if (instance == null) {
            instance = new PlatformController();
        }
        return instance;
    }

    public PlatformView getPlatformView() {
        return platformView;
    }

    public void setPlatformView(PlatformView platformView) {
        this.platformView = platformView;
    }

    public String getActiveSimulationName() {
        return activeSimulationName;
    }

    public void setActiveSimulationName(String activeSimulationName) {
        this.activeSimulationName = activeSimulationName;
    }

    public String getActiveSimulationPath() {
        return activeSimulationPath;
    }

    public void setActiveSimulationPath(String activeSimulationPath) {
        this.activeSimulationPath = activeSimulationPath;
    }

    public boolean isNewSimulation() {
        return newSimulation;
    }

    public void setNewSimulation(boolean newSimulation) {
        this.newSimulation = newSimulation;
        this.changed=true;
    }


    public void showSimulationName(String name){
        if (isNewSimulation()){
            name = "untitled simulation";
        }
        String title=applicationDefaultTitle + " - ["+name+"]";


        getPlatformView().getFrame().setTitle(title);
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

}
