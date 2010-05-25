package assemblerSim;

import java.util.*;

/**
 * This clock is used to execute steps in the virtual maschine
 * @author Marco "Don" Kaulea
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

	
	/**
	 * Constructor
	 * @param nrechner is used for function calls to the virtual maschine
	 */
	public Clock(VonNeumannRechner nrechner)
	{
		timer = new Timer();
		task = new ClockTask();
		rechner = nrechner;
	}
	
	/**
	 * executes the step function in Clock when run
	 * @author Marco "Don" Kaulea
	 */
	public class ClockTask extends TimerTask
	{
		/**
		 * executes the step function in Clock when called
		 */
	    public void run() 
	    {
	      step();
	    }
	}

	/**
	 * executes the next step in the virtual maschine
	 */
	protected void step()
	{
		rechner.step();
	}
	
	/**
	 * starts the timer
	 */
	protected void run()
	{
		isRunning = true;
		timer.scheduleAtFixedRate(task = new ClockTask(), 500, stepTime);//use 500ms as debounce when changing delay while running
	}

	/**
	 * stops the timer
	 */
	protected void halt()
	{
		isRunning = false;
		task.cancel();
		timer.purge();
	}
	
	/**
	 * sets the delay between two executions
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
