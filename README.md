# Elevator System Design
 
This repo contains a solution for the elevator system design problem using multithreading and object-oriented programming concept

## Problem Statement

The problem is to design an elevator system that can handle multiple requests from different floors and move the elevators efficiently.

## Solution Approach

The solution consists of three main components: Elevator, Request (External or internal) and threads classes .

 - Elevator is a class that represents an individual elevator with its attributes (such as current floor, direction, status multiple Treesets to store diffrent types of requests ) and methods (such as proces up requests , proces down requests and others ).
 
- The solution consists of two classes that implement the Runnable interface: ProcessJobWorker and AddJobWorker.
 
&nbsp - AddJobWorker is a class that creates events( requests) and puts them in a shared Treeset . An event is represented by a class that has some data as an attribute.

 &nbsp - ProcessJobWorker is a class that continuously check for event in Treeset and process them.

## How to Run

To run the solution, you need to have Java installed on your machine. You can clone this repo or download the source code as a zip file. Then navigate to the src folder and run TestElvator file 
