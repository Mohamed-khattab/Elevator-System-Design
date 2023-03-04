package com.oopProjects;

public class TestElevator {
    public static void main(String[] args){
        Elevator elevator = new Elevator() ;
        ProcessJobWorker processJobWorker = new ProcessJobWorker(elevator) ;
        Thread t1 = new Thread(processJobWorker) ;
        t1.start();
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }

        ExternalRequest externalRequest= new ExternalRequest(0,Direction.UP) ;
        InternalRequest internalRequest = new InternalRequest(5 ) ;
        Request request =new Request(externalRequest, internalRequest) ;

        new Thread(new AddJobWorker(elevator, request)).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
