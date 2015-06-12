//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Program        : Shopper
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
//   Shoppers arrive into the store at random intervals and are assigned
// to buy specific items. The shopping time is mathematically dependent
// on this number of items. After finishing, the shopper gets into the
// shortest line that is allowed for that shopper's number of items.
// Eventually, that shopper reaches the head of the line and is
// checked-out, computing final statistics in the process.
//
// Known Bugs     : None
//
// Future Plans   : Allow different probability distributions for
//                    arriving shoppers and the number of items they
//                    buy.
//
// Program History:
//   9/28/04: R. Pattis - Operational for 15-200
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package supermarket;


import java.util.Iterator;
import edu.cmu.cs.pattis.cs151xx.Prompt;
import edu.cmu.cs.pattis.cs151xx.orderedCollections.*;



public class Shopper {

  //States, in order, that shopper occupies; see state intance variable
  public static final int OUTSIDE  = 0;  //Not yet arrived
  public static final int SHOPPING = 1;  //Entered store and shopping
  public static final int IN_LINE  = 2;  //Done shopping, in checkout line
  public static final int AT_FRONT = 3;  //Being served at front of line
  public static final int DONE     = 4;  //Left store
  
  //Instance variables
  private int id;             //shopper's unique id
  private int nextEventTime;  //when does this shopper next change state
  private int state;          //shopper's current state
  private int shopItems;      //number of items this shopper is shopping for
  private int lineNumber;     //what line number this shopper chooses
  private int waitTime;       //Amount of time waiting in line
  
  //Static fields
  private static boolean  tracing      = Prompt.forBoolean("Trace Shoppers",true);
  private static int      nextId       = 0;
  private static int      lastArrival  = 0;
  private static double   upsetness    = 0;
  private static int      doneCount    = 0;
  private static String[] stateDecoder = new String[]{"OUTSIDE","SHOPPING","IN_LINE","AT_FRONT","DONE"};

  
  
  //Constructor: assign an id, initialize state/nextEventTime/shopItems
  //now; other instance variables are filled in later
  public Shopper ()
  {
    id          = nextId++;
    state       = OUTSIDE;
    lastArrival = nextEventTime = lastArrival + arriveTime(); 
    shopItems   = (int)(1+ 40*Math.random());
  }
  
  
  //Mathematical formulas for computing various times (of arrival, or
  //events within the store); subject to change
  
  int arriveTime()
  {return (int)(1+ 10*Math.random())*20;}
  
  int shopTime()
  {return 120 + 30*shopItems;}  //2 minutes + 30 seconds/item
  
  int activeCheckoutTime()
  {return 60 + 3*shopItems;}    //1 minute + 3 seconds/item
  
  
  //Return the time at which this line would be empty
  //(the last person in the line is done checking out).
  //That is the time a new shopper would start checking out.
  int timeWhenLineEmpty (AbstractQueue line, int currentTime)
  {
    Shopper last = null;
    //Iterate to last person in line
    //This could be cached and be O(1)
    Iterator it = line.iterator();
    while (it.hasNext())
      last = (Shopper)it.next();
      
    if (last == null)
      return currentTime;
    else
      return last.nextEventTime + last.activeCheckoutTime();
  }
  
  
  
  //Return the index of the best line for this shopper to enter.
  //Only allowable lines (based on maxItems) are examined.
  //There is always some best line (assuming some line allows
  //any number of items).
  int findBestLine(int[] maxItems, AbstractQueue[] lines, int currentTime)
  {
    int bestLine = -1;
    int bestTime = Integer.MAX_VALUE;
    for (int i=0; i<maxItems.length; i++)
      if (shopItems <= maxItems[i]) {
        int lineTime = timeWhenLineEmpty(lines[i], currentTime);
        if (lineTime < bestTime) {
          bestLine = i;
          bestTime = lineTime;
        }
      }
    return bestLine;
  }
  
  
  
  //Is this shopper done? No need to put it back on the priority queue
  boolean isDone()
  {return state == DONE;}
  
  
  
  //For compartor; if we used an anonymous class inside the Shopper class,
  //it could access this private instance variable directly.
  int getNextEventTime()
  {return nextEventTime;}


  
  //Move the shopper to the next state, updating whatever instance
  //variables are useful. If tracing, print appropriate information
  //for each state chang.
  void nextState (int[] maxItems, AbstractQueue[] lines)
  {
    if (tracing)
      System.out.print("Time = " + nextEventTime);
      
    switch (state) {
      case OUTSIDE : if (tracing)
                       System.out.print(": Starting to Shop: ");
                       
                     state         =  SHOPPING;
                     nextEventTime += shopTime();
                     
                     if (tracing)
                        System.out.println(this);
                     break;
                     
                     
      case SHOPPING: if (tracing)
                       System.out.print(": Entering Checkout line#");

                     state         = IN_LINE;
                     lineNumber    = findBestLine(maxItems,lines,nextEventTime);
                     
                     int oldTime = nextEventTime;
                     nextEventTime = timeWhenLineEmpty(lines[lineNumber],nextEventTime);

                     lines[lineNumber].add(this);
                     waitTime = nextEventTime - oldTime;
                     
                     if (tracing)
                        System.out.println(lineNumber + ": " + this.toString());
                     break;
                     
                     
      case IN_LINE : if (tracing)
                       System.out.print(": At Front of Checkout Line#" + lineNumber+": ");
                       
                     state = AT_FRONT;
                     nextEventTime += activeCheckoutTime();
                     
                     if (tracing)
                        System.out.println(this);
                     break;
                     
                     
      case AT_FRONT: if (tracing)
                       System.out.print(": Exiting Store: ");
                       
                     state = DONE;
                     nextEventTime = Integer.MAX_VALUE;
                     lines[lineNumber].remove();
                     upsetness += (double)waitTime/shopTime();
                     doneCount++;
                     
                     if (tracing)
                        System.out.println(this);
                     break;
                     
      case DONE    : /*Never should happen!*/
                     
    }
      
  }
  
  
  //Adaptive toString: its result is based on the current state of the
  //shopper (selected instance variables are elided). Names are abbreviated.
  public String toString()
  {
    return "Shopper[id="+id+
                    ",state="+stateDecoder[state]+
                    (state == DONE ? "" : ",event="+nextEventTime)+
                    ",items="+shopItems+
                    (state != IN_LINE ? "" : ",lineNumber="+lineNumber)+
                    (state != DONE ? "" : ",wait="+waitTime)+
           "]";
  }
  
  
  
  //Report the final statistics
  static void reportStatistics()
  {System.out.println("Average Upsetness = " + upsetness/doneCount);}
}
