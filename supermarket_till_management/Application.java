//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Program        : Supermarket Simulator
//
// Author         : Richard E. Pattis
//                  Computer Science Department
//                  Carnegie Mellon University
//                  5000 Forbes Avenue
//                  Pittsburgh, PA 15213-3891
//                  e-mail: pattis@cs.cmu.edu
//
// Maintainer     : Author
//
//
// Description:
//
//   This program simulates a supermarket with many check-out lines,
// each specifying a maximum number of items that a shopper can have
// and still use that line. It computes statistics about the simulation.
// Changing the max-item value will change the statistics.
// 
//   Shoppers arrive into the store at random intervals and are assigned
// to buy specific items. The shopping time is mathematically dependent
// on this number of items. After finishing, the shopper gets into the
// shortest line that is allowed for that shopper's number of items.
// Eventually, that shopper reaches the head of the line and is
// checked-out, computing final statistics in the process.
//
//   This program shows how a priority queue and regular queues are
// used in real-world simulations.
//
// Known Bugs     : None
//
// Future Plans   : See Shopper
//
// Program History:
//   9/28/04: R. Pattis - Operational for 15-200
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package supermarket;


import java.util.Comparator;
import edu.cmu.cs.pattis.cs151xx.Prompt;
import edu.cmu.cs.pattis.cs151xx.Timer;
import edu.cmu.cs.pattis.cs151xx.orderedCollections.*;





public class Application {


//The shopper with the smallest next event time (closest in the future)
//is considered by this Comparator to be the one with the highest
//priority.

public static class ByEventTime implements Comparator {

  public int compare (Object o1, Object o2)
  {
    Shopper s1 = (Shopper)o1;
    Shopper s2 = (Shopper)o2;
    return s2.getNextEventTime() - s1.getNextEventTime();
  }
}





  //The maxItems arrays specifies the maximum number of items allowable
  //at each possible checkout line. The lines array is declared to have
  //the same length. Modify the initial values here for different
  //strategies.
  
  private static int[] maxItems = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
  
  
  //The shopper priority queue contains all shoppers who are in
  //the store (or will enter the store) but not those who have
  //left. Every shopper is in some state, and has a state-changing
  //event scheduled sometime in the future. Shoppers are removed from
  //the priority queue with the lowest next event time having the
  //highest priority. If they are not done (are scheduled for another
  //event) they are put back in this priority queue. 
  
  private static AbstractPriorityQueue shoppers = new ArrayPriorityQueue(new ByEventTime());
  
  
  //The lines queues collect together all shoppers waiting in the same
  //checkout line. Eventually, each shopper makes its way to the front
  //of the line, then gets "checked-out" and leaves the supermarket
  private static AbstractQueue[] lines = new ArrayQueue[maxItems.length];


	public static void main(String[] args)
	{
     //Allocate empty queues for each line
     for (int i=0; i<lines.length; i++)
       lines[i] = new ArrayQueue();
     
    
     //Enqueue all shoppers for this simulation: the constructor
     //schedules them to enter the store at random (but statistically
     //regular) intervals. 
     int shopperCount = Prompt.forInt("Enter # of Shoppers to Simulate");
     for (int i=0; i<shopperCount; i++)
       shoppers.add(new Shopper());
     
      
     //Simulate the supermarket until there are no more events to
     //process. The shopper having the next event is removed, their
     //state is advanced, and then they are put back in the priority
     //queue (if they have another event in their future).
     Timer t = new Timer();
     t.start();
     int eventCount = 0;
     while (!shoppers.isEmpty()) {
       eventCount++;
       Shopper current = (Shopper)shoppers.remove();
       current.nextState(maxItems,lines);
       if (!current.isDone())
         shoppers.add(current);  
     }
     t.stop();
     System.out.println("Simulation time  = " + t.getElapsed() + " seconds");
     System.out.println("Simulation speed = " + (int)(eventCount/t.getElapsed()) + " events/second");
     
     
     //Collect/Report the statistics
     Shopper.reportStatistics();
	}


}
