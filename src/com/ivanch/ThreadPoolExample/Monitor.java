
package com.ivanch.ThreadPoolExample;

public class Monitor {
    
    private Object mon;
    private volatile int Count;

    public Monitor() {
        mon = new Object();
        Count = 0;
    }

    public Object getMon() {
        return mon;
    }

    public int getCount() {
        return Count;
    }
    
    public void CountIteration(){
        Count++;
    }
    
    public void CountToZero(){
        Count = 0;
    }
    
}
