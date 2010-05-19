package assemblerSim;

import java.util.*;

/**
 * @author Marco "Don" Kaulea
 *
 */
public class Clock
{
	
	/*
	 * Variables
	 */
	int stepTime = 1000;
	VonNeumannRechner rechner;
	Timer timer;
	ClockTask task;
	boolean isRunning = false;

	

	public Clock(VonNeumannRechner nrechner)
	{
		timer = new Timer();
		task = new ClockTask();
		rechner = nrechner;
	}
	
	public class ClockTask extends TimerTask
	{
	    public void run() 
	    {
	      step();
	    }
	}

	protected void step()
	{
		rechner.step();
	}
	
	protected void run()
	{
		isRunning = true;
		timer.scheduleAtFixedRate(task = new ClockTask(), 500, stepTime);
	}

	protected void halt()
	{
		isRunning = false;
		task.cancel();
		timer.purge();
	}
	
	/**
	 * @param nStepTime is the time between the start of two steps in milliseconds
	 */
	protected void setStepTime(int nStepTime)
	{
		stepTime = nStepTime;
		if(isRunning)
		{
			this.halt();
			this.run();
		}
	}
}
