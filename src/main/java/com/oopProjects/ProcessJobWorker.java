package com.oopProjects;

public class ProcessJobWorker implements Runnable {
    private final Elevator elevator ;

    public ProcessJobWorker(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        elevator.startElevator();
    }
}
