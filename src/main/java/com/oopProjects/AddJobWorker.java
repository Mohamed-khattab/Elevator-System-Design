package com.oopProjects;

public class AddJobWorker implements Runnable {
    private final Elevator elevator ;
    private final Request request ;

    public AddJobWorker(Elevator elevator, Request request) {
        this.elevator = elevator;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        elevator.addJob(request);
    }
}
