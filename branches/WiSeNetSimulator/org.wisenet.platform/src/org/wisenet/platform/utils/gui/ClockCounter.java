/*
 ***  Wireless Sensor Network Simulator
 * The next generation for WSN Simulations
 */
package org.wisenet.platform.utils.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

/**
 *
* @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class ClockCounter {

    long counter = 0;
    Task0 task;
    Timer timer = new Timer();
    private long start;
    private IClockDisplay display;

    public IClockDisplay getDisplay() {
        return display;
    }

    public void setDisplay(IClockDisplay display) {
        this.display = display;
    }

    public long getStart() {
        return start;

    }

    public void stop() {
        try {
            task.cancel();
        } catch (Exception e) {
        }

    }

    public void start() {
        start = System.currentTimeMillis();
        task = new Task0();
        timer.schedule(task, 0L, 1000);
    }

    class Task0 extends java.util.TimerTask {

        private int elapsed = Integer.MAX_VALUE;
        String time = "0";

        public void run() {
            elapsed = (int) (((System.currentTimeMillis()) - getStart()));// / (1000L));

            DateFormat df = new SimpleDateFormat("HH':'mm':'ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            displayClock(df.format(new Date(elapsed)));

        }
    }

    private void displayClock(String time) {
        this.display.updateClock(time);
    }

    public static void main(String[] a) {

        ClockCounter c = new ClockCounter();
        c.start();
    }
}
