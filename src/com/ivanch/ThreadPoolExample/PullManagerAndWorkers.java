
package com.ivanch.ThreadPoolExample;

import java.util.concurrent.TimeUnit;


public class PullManagerAndWorkers implements Runnable{
    
    private Manager manager;
    private Thread managerThread;
    private Worker[] workers;
    private Thread[] selfThreadsWorkers;
    protected Thread selfThread;
    static private int selfThreadCount=1;
    private Monitor[] mons;
    
    /**
     * 
     * @param Number of running threads to work with
     * @param The number of completed tasks in one iteration
     * @param Number of iterations
     */
    public PullManagerAndWorkers(int workers, int tasks, int howManyRepeatWork){
        newSelfThread();
        selfThreadCount++;
        fillArrayMonitor();
        this.manager = new Manager(tasks, howManyRepeatWork, mons);
        newThreadForManager();
        this.workers = new Worker[workers];
        fillArrayWorkers(tasks, howManyRepeatWork);
        selfThreadsWorkers = new Thread[workers];
        fillArrayThreads();
        Worker.resetCounterWorkers();
    }
    
    public Manager getManager() { return manager; }
    
    public Worker[] getWorkers() { return workers; }
    
    private void fillArrayMonitor() {
        mons = new Monitor[2];
        int i;
        for (i = 0; i < mons.length; i++) {
            mons[i] = new Monitor();
        }
    }
    
    private void newSelfThread() {
        selfThread = new Thread(this, "pull 'ManagerAndWorkers'#"
                                                        + selfThreadCount);
    }
    
    private void newThreadForManager() {
        managerThread = new Thread(manager, "Мanager#" + manager);
    }
    
    private void fillArrayWorkers(int tasks, int howManyRepeatWork) {
        int i;
        for (i = 0; i < workers.length; i++) {
            workers[i] = new Worker(tasks, howManyRepeatWork, mons);
        }
    }
    
    private void fillArrayThreads() {
        int i;
        for (i = 0; i < selfThreadsWorkers.length; i++) {
            selfThreadsWorkers[i] = new Thread(workers[i], "Worker#" + workers[i]);
        }
    }
    
    public void startNewWorkingDay(){
        selfThread.start();
    }
    
    public void joinWorkingDay(){
        try { selfThread.join(); }
        catch (InterruptedException e) { }
    }
    
    private void startManagerChecksWork(){
        managerThread.start();
    }
    
    private void joinManagerDay(){
        try { managerThread.join(); }
        catch (InterruptedException e) {}
    }
    
    private void startWorkersWork(){
        int i;
        for (i = 0; i < selfThreadsWorkers.length; i++) {
            selfThreadsWorkers[i].start();
        }
    }
    
    private void joinWorkersChecksWork(){
        int i;
        try {
            for (i = 0; i < selfThreadsWorkers.length; i++) {
                    selfThreadsWorkers[i].join();
            }
        } catch (InterruptedException e) {}
    }

    @Override
    public void run() {
        
        
        startManagerChecksWork();
        
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        // This timer is optional, but it ensures that the "Manager" is started
        // before all the "Workers".
        } catch (InterruptedException e) {}
        
        startWorkersWork();
        joinManagerDay();
        joinWorkersChecksWork();
    }

}






