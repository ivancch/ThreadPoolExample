
package com.ivanch.ThreadPoolExample;


public class Worker implements Runnable{
    
    private final int worker;
    private final int howManyRepeatWork;
    private final Monitor[] mons;
    private final int tasks;
    static public int counterWorkers=1;
    

    public Worker(int tasks, int howManyRepeatWork, Monitor[] mons) {
        this.worker = counterWorkers;
        this.howManyRepeatWork = howManyRepeatWork;
        counterWorkers++;
        this.tasks = tasks;
        this.mons = mons;
    }

    public int getWorker() { return worker; }
    
    public void getWork() {
        try {
            while (mons[0].getCount() < howManyRepeatWork){
                while (mons[1].getCount() <= tasks){
                	
                    synchronized(mons[1].getMon()){
                        mons[1].CountIteration();
                        if(mons[1].getCount() <= tasks){
                            System.out.println("Worker#" + worker + " is working...");
                            if(mons[1].getCount() >= tasks){
                                synchronized(mons[0].getMon()){
                                    mons[0].getMon().notify();
                                    break;
                                }
                            }
                        } else break;
                    }
                }
                synchronized(mons[1].getMon()){
                    mons[1].getMon().wait();
                }
            } 
        }catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    public static void resetCounterWorkers()
    {
    	counterWorkers = 1;
    }
        
    @Override
    public void run() {
        getWork();
    } 
    
    @Override
    public String toString() {
        return "Worker#" + worker;
    }
    
}


