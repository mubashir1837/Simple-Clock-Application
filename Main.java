import java.text.SimpleDateFormat;

import java.util.Date;

// Clock class: stores and updates the current time 

class Clock {

    private volatile String currentTime; // shared variable between threads

    public Clock() {

        currentTime = ""; // initialize to empty string

    }

    // Updates currentTime every second (runs in background thread)

    public void updateTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

        while (true) {

            currentTime = formatter.format(new Date());

            try {

                Thread.sleep(1000); // sleep 1 second between updates

            } catch (InterruptedException e) {

                System.out.println("Update thread interrupted: " + e.getMessage());

            }

        }

    }

    // Displays currentTime to console every second

    public void displayTime() {

        while (true) {

            if (!currentTime.isEmpty()) {

                System.out.println("Current Time: " + currentTime);

            }

            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                System.out.println("Display thread interrupted: " + e.getMessage());

            }

        }

    }

}

// Thread that runs the background time update

class TimeUpdateThread extends Thread {

    private Clock clock;

    public TimeUpdateThread(Clock clock) {

        this.clock = clock;

    }

    @Override

    public void run() {

        clock.updateTime();

    }

}

// Thread that handles console display

class DisplayThread extends Thread {

    private Clock clock;

    public DisplayThread(Clock clock) {

        this.clock = clock;

    }

    @Override

    public void run() {

        clock.displayTime();

    }

}

// Main entry point

public class Main {

    public static void main(String[] args) {

        Clock clock = new Clock();

        TimeUpdateThread updateThread = new TimeUpdateThread(clock);

        DisplayThread displayThread = new DisplayThread(clock);

        // Lower priority for background update, higher for display

        updateThread.setPriority(Thread.MIN_PRIORITY); // priority = 1

        displayThread.setPriority(Thread.MAX_PRIORITY); // priority = 10

        updateThread.start();

        displayThread.start();

    }

}