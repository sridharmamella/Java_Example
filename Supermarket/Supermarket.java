import java.util.*;
import myutil.*;

/**
 * Class simulating a supermarket, with customers, items and checkout counters
 * 
 * @author Mikael Kindborg, Sara Stymne, Johan Janzen
 *
 */
public class Supermarket{
    private MyList<Checkout> checkoutCounters;
    private MyList<Customer> customers;
    private MyList<String> items;
    private MyList<String> givenName;
    private MyList<String> surName;
    
    /**
     * Starts the supermarket simulation
     * 
     * @param args not used
     */
    public static void main(String[] args){
        // Create the supermarket
        Supermarket supermarket = new Supermarket();
        
        // Present the items available in the supermarket
        supermarket.presentItems();
        waitMS(1000);
        
        // Run the simulation for 100 steps
        for (int i = 0; i < 100; i++)
        {
            supermarket.simulateOneStep();
            waitMS(300);
        }

        supermarket.showTerminalStatus();
    }
    
    /**
     * Creates a supermarket
     * Adds items and possible customer names
     */
    public Supermarket(){
        // Create list with checkout counters
        checkoutCounters = new MyList<Checkout>();
        for (int i = 1; i <= 5; i++){
            checkoutCounters.add(new Checkout(i));
        }
        
        // Create list for the customers
        customers = new MyList<Customer>();
        
        // Create list with items
        items = new MyList<String>();
        items.add("mini-TV");
        items.add("galaklänning");
        items.add("kalkon");
        items.add("literförpackning glass");
        items.add("läsk");
        items.add("fiskgratäng");
        items.add("apelsin");
        items.add("pulversoppa");
        items.add("rostbiff");
        items.add("påse potatis");

        // Create list with possible customer given names
        givenName = new MyList<String>();
        givenName.add("Erik");
        givenName.add("Anna");
        givenName.add("Bo");
        givenName.add("Sandra");
        givenName.add("Pelle");
        givenName.add("Ann-Marie");
        givenName.add("August");
        givenName.add("Ewa");
        givenName.add("Stefan");
        givenName.add("Malin");
        givenName.add("Gunnar");
        givenName.add("Gunnel");
        
        // Create a list with possible customer surnames
        surName = new MyList<String>();
        surName.add("Eriksson");
        surName.add("Ek");
        surName.add("Bolin");
        surName.add("Sirén");
        surName.add("Granlund");
        surName.add("Andersson");
        surName.add("Fors");
        surName.add("Strand");
        surName.add("Jonsson");
        surName.add("Krans");
        surName.add("Smith");
        surName.add("Hjulin");
    }
    
    /**
     * Prints the items available in the supermarket
     */
    public void presentItems()
    {
        System.out.println("Följande varor finns i Stormarknden:");
        for(String item : items){
            System.out.println(item);
        }
    }
    
    /**
     * Makes a pause in the simulation
     * 
     * @param milliseconds the time to pause, in milli seconds
     */
    public static void waitMS(int milliseconds) {
        try{
            Thread.sleep(milliseconds);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Performs one step of the simulation
     */
    public void simulateOneStep(){
        System.out.println();
        theCustomersAreArriving();
        customersAreShopping();
        customersWalkToCheckout();
        customersPaysAndLeaves();
    }
    
    /**
     * Picks a random item
     * 
     * @return the chosen item
     */
    public String pickArandomItem(){
        return items.getRandomElement();
    }
    
    /**
     * Will with 70% probability simulate a new customer entering
     */
    private void theCustomersAreArriving(){
       
        if (Math.random() < 0.7){
            String name = givenName.getRandomElement() +
	                      ' ' +
	                      surName.getRandomElement();      
            customers.add(new Customer(name));
            System.out.println("En ny kund vid namn " +
                               name +
                               " kommer in.");
        }
    }
    
    /**
     * Lets all customers shop for items
     */
    private void customersAreShopping(){
    	for(Customer customer : customers){
    		customer.shop(this);
    	}
    }
    
    /**
     * The customers that are done shopping are moved to the 
     * checkout counters.
     */
    private void customersWalkToCheckout(){
    	// Notice the difference between this iteration structure and
    	// customersAreShopping. Why is this one different?
        Iterator<Customer> i = customers.iterator();
        while (i.hasNext()){
        	
            Customer customer = i.next();
            if (Customer.wantsToCheckout() && customer.hasItems()){
                customerWalksToCheckout(customer);
                
                i.remove();
            }
        }
    }
    
    /**
     * Moves a single customer to a checkout counter
     * 
     * @param customer the customer to be moved
     */
    private void customerWalksToCheckout(Customer customer)
    {
        // Choose a checkout counter at random and
        // place the customer in that queue
        Checkout checkout = checkoutCounters.getRandomElement();
        checkout.addCustomer(customer);

        System.out.println(customer.getCustomerName() +
			               " ställer sig i kö till kassa " +
			               checkout.getCheckoutNumber());
    }
    
    /**
     * Perform a simulation step for customers in the checkout counters
     */
    private void customersPaysAndLeaves(){
    	for(Checkout checkout : checkoutCounters){
            checkout.attendCustomer();
        }
    }

    /**
     * Prints information of the status of the supermarket
     */
    public void showTerminalStatus(){
      System.out.println();
      System.out.println("Vid simuleringens slut finns det " +
                         customers.size() +
			             " kunder kvar i stormarknaden.");
      
      System.out.println("Så här är situationen i kassorna:");
      for (Checkout k : checkoutCounters){
    	  k.printCustomerInfo();
      }
    }
    
}