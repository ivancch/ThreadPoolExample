
package com.ivanch.ThreadPoolExample;


public class Manager implements Runnable{
    
    private final int manager;
    private final int howManyRepeatWork;
    private final Monitor[] mons;
    static public int counterManager=1;

    
    public Manager(int tasks, int howManyRepeatWork, Monitor[] mons) {
        this.manager = counterManager;
        this.howManyRepeatWork = howManyRepeatWork;
        counterManager++;
        this.mons = mons;
    }

    public int getManager() { return manager; }
    
    public void getWork() {
        
        try{
            
            int i;
            for (i = 0; i < howManyRepeatWork; i++) {
                synchronized(mons[0].getMon()){
                    mons[0].getMon().wait();
                    System.out.println("Manager#" + manager + " checks work");
                    mons[0].CountIteration();
                    mons[1].CountToZero();
                }
                synchronized(mons[1].getMon()){
                    mons[1].getMon().notifyAll();
                }
            }
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        getWork();
    }   
    
    @Override
    public String toString() {
        return "Manager#" + manager;
    }
    
}
