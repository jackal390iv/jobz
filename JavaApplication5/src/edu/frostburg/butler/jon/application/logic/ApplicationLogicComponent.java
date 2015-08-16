package edu.frostburg.butler.jon.application.logic;


interface ApplicationLogicComponent extends Runnable {
    /**
     * Returns a description for the application logic
     * 
     * @return description for application logic
     */
    Description getDescriptor();
}
