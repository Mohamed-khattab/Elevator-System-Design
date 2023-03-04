package com.oopProjects;

import java.util.TreeSet;

public class Elevator {
    private int currentFloor =0 ;
    private Direction  currentDirection = Direction.UP ; 
    private State currentState = State.IDLE;
    
    private volatile TreeSet<Request> currentJobs = new TreeSet<>();

    /**
     * up jobs which cannot be processed now so put in pending queue
     */
    private final TreeSet<Request> upPendingJobs = new TreeSet<>();

    private final TreeSet<Request> downPendingJobs = new TreeSet<>();

    public void startElevator(){
        System.out.println("The Elevator has started functioning");
        while(true){
            if(checkIfJob()){
                if(currentDirection == Direction.UP){
                    Request request = currentJobs.pollFirst() ;
                    processUpRequest(request);
                    if(currentJobs.isEmpty()){
                        addPendingDownJobsToCurrentJobs();
                    }
                }
                if(currentDirection == Direction.DOWN){
                    Request request = currentJobs.pollLast() ;
                    processDownRequest(request);
                    if(currentJobs.isEmpty()){
                        addPendingUpJobsToCurrentJobs();
                    }
                }
            }
        }
    }
    private boolean checkIfJob() {

        return !currentJobs.isEmpty();
    }
    private void processUpRequest(Request request) {
        int startFloor = currentFloor ;
        if(startFloor < request.getExternalRequest().getSourceFloor()){
            for(int i =startFloor ; i <= request.getExternalRequest().getSourceFloor(); i++){
                try{
                    Thread.sleep(1000);

                }catch ( InterruptedException e ){
                    e.printStackTrace();

                }
                System.out.println("We have reached floor  :  " + i);
                currentFloor = i;

            }
        }
        // here the elevator reached the floor in which it has been requested
        System.out.println("Reached the floor  -- opening the door ");
        startFloor = currentFloor ;
        for (int i = startFloor; i < request.getInternalRequest().getDestination(); i++) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("We have reached floor  :  " + i);
            currentFloor = i;

            if (checkIfNewJobCanBeProcessed(request)) {
                break;
            }
        }
    }

    private void processDownRequest(Request request) {
        int startFloor = currentFloor ;
        if(startFloor < request.getExternalRequest().getSourceFloor()){
            for(int i =startFloor ; i <= request.getExternalRequest().getSourceFloor(); i++){
                try{
                    Thread.sleep(1000);

                }catch ( InterruptedException e ){
                    e.printStackTrace();

                }
                System.out.println("We have reached floor  :  " + i);
                currentFloor = i;

            }
        }
        // here the elevator reached the floor in which it has been requested
        System.out.println("Reached the floor  opening the door ");
        startFloor = currentFloor ;

        for (int i = startFloor-1 ; i >= request.getInternalRequest().getDestination(); i--) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("We have reached floor  :  " + i);
            currentFloor = i;

            if (checkIfNewJobCanBeProcessed(request)) {
                break;
            }
        }

    }
    private boolean checkIfNewJobCanBeProcessed(Request currentRequest) {
        if(checkIfJob()) {
            if (currentRequest.getExternalRequest().getDirectionToGo() == Direction.UP) {
                Request request = currentJobs.pollLast();
                if (request.getInternalRequest().getDestination() < currentRequest.getInternalRequest().getDestination()){
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);
            }
            if (currentRequest.getExternalRequest().getDirectionToGo() == Direction.DOWN) {
                Request request = currentJobs.pollFirst();
                if (request.getInternalRequest().getDestination() > currentRequest.getInternalRequest().getDestination()) {
                    currentJobs.add(request);
                    currentJobs.add(currentRequest);
                    return true;
                }
                currentJobs.add(request);
            }
        }
        return false;
    }
    private void addPendingDownJobsToCurrentJobs() {
        if(!downPendingJobs.isEmpty()){
            System.out.println("Pick a pending down job and execute it by putting in current job");
            currentJobs = downPendingJobs;
            currentDirection= Direction.DOWN;
        }else {
            currentState = State.IDLE;
            System.out.println("The elevator is in Idle state");
        }
    }
    private void addPendingUpJobsToCurrentJobs() {
        if(!upPendingJobs.isEmpty()){
            System.out.println("Pick a pending up job and execute it by putting in current job");
            currentJobs = upPendingJobs ;
            currentDirection= Direction.UP ;
        }else{
            currentState =State.IDLE ;
            System.out.println("The elevator is in Idle state");
        }
    }
    public void addJob(Request request){
        if(currentState == State.IDLE){
            if (currentFloor == request.getExternalRequest().getSourceFloor()) {
                System.out.println("Added current queue job  lift state is :  " + currentState + "  && location is : "
                        + currentFloor + " && is moving to floor : " + request.getInternalRequest().getDestination());
            }
            // check if the elevator is already on the floor on which the user
            // is if no then elevator first needs to move to source floor
            else {
                System.out.println("Added current queue job  lift state is :  " + currentState + "  && location is : "
                        + currentFloor + " && is moving to floor : " + request.getExternalRequest().getSourceFloor());
            }
            currentJobs.add(request) ;
            currentState = State.MOVING ;
            currentDirection= request.getExternalRequest().getDirectionToGo() ;
        }else if( currentState == State.MOVING){
            if(request.getExternalRequest().getDirectionToGo() != currentDirection){
                addToPendingJob(request) ;
            } else if ( request.getExternalRequest().getDirectionToGo() ==currentDirection) {
                if(currentDirection ==Direction.UP && request.getInternalRequest().getDestination() < currentFloor){
                    addToPendingJob(request);
                } else if ( currentDirection ==Direction.DOWN && request.getInternalRequest().getDestination() > currentFloor) {
                    addToPendingJob(request);
                }else {
                    currentJobs.add(request) ;
                }
            }
        }
    }
    public void addToPendingJob(Request request) {
        if (request.getExternalRequest().getDirectionToGo() == Direction.UP) {
            System.out.println("Add to pending up jobs");
            upPendingJobs.add(request);
        } else {
            System.out.println("Add to pending down jobs");
            downPendingJobs.add(request);
        }
    }

}

