/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.platform.ui;

import org.mei.securesim.platform.PlatformView;

/**
 *
 * @author CIAdmin
 */
public class GUI {

    public static void showSimulationNrOfNodes (int value){
        PlatformView.getInstance().setSimulationNrNodes(value);
    }

    public static void showSimulationTimeMilisecs(String value){
        PlatformView.getInstance().setSimulationTime(value);
    }

    public static void showStatusMessage(String message){
        PlatformView.getInstance().setStatusMessage(message);
    }
    public static void showSimulationEvents(int value){
        PlatformView.getInstance().setSimulationEvents(value);
    }
}
