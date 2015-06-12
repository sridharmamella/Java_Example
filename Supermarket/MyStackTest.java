import java.util.*;
import myUtil.*;

/**
 * Testklass för MyStack
 * 
 * @author Sara Stymne
 * 
 */
public class MyStackTest {

    public static void main(String args[]) {

      MyStack<Integer> talStack = new MyStack<Integer>();
      MyStack<String>  wordStack = new MyStack<String>();
      Scanner keyboard = new Scanner(System.in);

      //Testning med heltal!
      System.out.println("Testar med heltal!\n" +
      					 "skriv in 5 heltal");
      
      for(int i = 1; i <= 5; i++) {
    	  talStack.push(keyboard.nextInt());
      }

      System.out.println("Nu ska de fyra sista talen du skrev in" +
      					 " skrivas ut i omvänd ordning:");
      try {
    	  for(int i = 1; i <= 4; i++) {
    		  System.out.print(talStack.pop() + " ");
    	  }
      }
      catch (Exception e) {
    	  System.out.println("loop 1: kunde inte poppa");
      }
      
      System.out.println();

      System.out.println("skriv in ett nytt heltal");
      talStack.push(keyboard.nextInt());
      
      System.out.println("Nu ska det sista talet du skrev in " +
			 			 "skrivas ut, sedan det allra första");
      try {
    	  for(int i = 1; i <= 3; i++) {
    		  System.out.print(talStack.pop() + " ");
    	  }
      }
      catch (Exception e) {
    	  System.out.println("loop 2: kunde inte poppa");
      }
      System.out.println();

      if (talStack.empty())
    	  System.out.println("Nu var stacken helt korrekt tom.");
      else
    	  System.out.println("Nu skulle empty returnerat false" + 
    			  			 " eftersom stacken var tom....");
      


          
     //Testning med strngar
     System.out.println("Testar med strängar!\n" +
			 "skriv in 5 ord");
      
      for(int i = 1; i <= 5; i++) {
    	  wordStack.push(keyboard.next());
      }

      System.out.println("Nu ska de fyra sista orden du skrev in " +
			 			 "skrivas ut i omvänd ordning:");
      try {
    	  for(int i = 1; i <= 4; i++) {
    		  System.out.print(wordStack.pop() + " ");
    	  }
      }
      catch (Exception e) {
    	  System.out.println("loop 3: kunde inte poppa");
      }
      System.out.println();

      System.out.println("skriv in ett nytt ord");
      wordStack.push(keyboard.next());
      
      System.out.println("Nu ska det sista ordet du skrev in" +
			 			 "skrivas ut, sedan det allra första");
      try {
    	  for(int i = 1; i <= 2; i++) {
    		  System.out.print(wordStack.pop() + " ");
    	  }
      }
      catch (Exception e) {
    	  System.out.println("loop 4: kunde inte poppa");
      }
      System.out.println();

    
      if (wordStack.empty())
    	  System.out.println("Nu var stacken helt korrekt tom.");
      else
    	  System.out.println("Nu skulle empty returnerat false" + 
    			  			 " eftersom stacken var tom....");
    }
}
