import myutil.*;

/**
 * Class simulating a checout counter in a supermarket
 * @author Mikael Kindborg, Sara Stymne, Johan Janzen
 *
 */
public class Checkout{
	
	private MyQueue<Customer> queue;
	private Customer currentCustomer;
	private int checkoutNumber;

	/**
	 * Creates a new checkout counter with number i
	 * 
	 * @param i the number of the checkout counter
	 */
	public Checkout(int i){
		queue = new MyQueue<Customer>();
		currentCustomer = null;
		checkoutNumber = i;
	}

	/**
	 * Adds a new customer to the checkout
	 * 
	 * @param customer the new customer
	 */
	public void addCustomer(Customer customer){
		queue.enqueue(customer);
	}

	/**
	 * Attends to one customer in the checkout, or adds
	 * a new if the checkout is empty
	 */
	public void attendCustomer() {
		// Either a new customer is called forward
		// or the current customer pays for one item
		if (currentCustomer == null){
			if (!queue.empty()) {
				currentCustomer = queue.dequeue();

				System.out.println("Nu betjänas " +
						currentCustomer.getCustomerName() +
						" i kassa " + checkoutNumber);
			}
		}
		else {
			if (currentCustomer.hasItems()){
				String item = currentCustomer.pickUpNextItem();
				System.out.println(currentCustomer.getCustomerName() +
						" betalar en " + item +
						" i kassa " + checkoutNumber);
			}
			else{
				System.out.println(currentCustomer.getCustomerName() +
						" har handlat klart och går ut.");

				currentCustomer = null;
			}
		}
	}

	/**
	 * Prints information about the checkout disk and the current customer
	 */
	public void printCustomerInfo(){
		System.out.print("I kassa " +
				checkoutNumber +
				" är det " +
				queue.size() +
				" personer i kö. " +
				"Just nu handlar ");
		if (currentCustomer != null){
			System.out.println(currentCustomer.getCustomerName());
		}
		else if (!queue.empty()){
			System.out.println(queue.dequeue().getCustomerName());
		}
		else{ 
			System.out.println("ingen");
		}
	}

	/**
	 * Gets the number of the checkout
	 * 
	 * @return the number of the number of the checkour
	 */
	public int getCheckoutNumber()
	{
		return checkoutNumber;
	}

}