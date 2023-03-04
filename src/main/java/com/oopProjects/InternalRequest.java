package com.oopProjects;

public class InternalRequest {
    private int destination;

    public InternalRequest(int destination) {
        this.destination = destination;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "InternalRequest{" +
                "The destinationFloor is - " + destination +
                '}';
    }
}
