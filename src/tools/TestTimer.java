package tools;

import java.util.Timer;
import java.util.TimerTask;

public final class TestTimer {
	//fields
	
	//constructors
	private TestTimer() {
		super();
	}
	
	//methods
	public static Timer startTimer(final int seconds, long delay, long period) { 
		final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	private int sec = (seconds < 1 ? 1 : seconds);
        	private int getSec() {
        		return this.sec;
        	}
        	private int decrementSec() {
        		return this.sec--;
        	}
            public void run() {
                System.out.println(this.decrementSec());
                if (this.getSec() < 0)
                    timer.cancel();
            }
        }, delay, period);
        return timer;
	}
}
