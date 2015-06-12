import myutil.*;

/**
 * Class simulating a customer
 * 
 * @author Mikael Kindborg, Sara Stymne, Johan Janzen
 *
 */
public class Customer{
	private MyStack<String> shoppingBasket;
	private String name;

	/**
	 * Creates a new customer, with a name
	 * 
	 * @param namn the name of the customer
	 */
	public Customer(String namn)
	{
		shoppingBasket = new MyStack<String>();
		this.name = namn;
	}

	/**
	 * The customer will pick something with a probability of 45% 
	 * and put it in the shopping basket
	 * @param supermarket den stormarknad där man shoppar
	 */
	public void shop(Supermarket supermarket){
		// For every step in the simulation there is a 45% chance the customer
		// will shop something

		if (Math.random() < 0.25){
			String item = supermarket.pickArandomItem();

			shoppingBasket.push(item);
			System.out.println(name + " lägger en " + item +  " i varukorgen.");
		}
	}

	/**
	 * Determines whether the customer should go the the checkout
	 * (25% probability)
	 * 
	 * @return <code>true</code> if the customer should go to checkout, 
	 * <code>false</code> otherwise
	 */
	public static boolean wantsToCheckout(){
		// For every step in the simulation there is a 25% chance the customer
		// will go to the checkout

		return Math.random() < 0.25;
	}

	/**
	 * Picks up the item on top of the shopping basket
	 * 
	 * @return the chosen item
	 */
	public String pickUpNextItem(){
		return shoppingBasket.pop();
	}

	/**
	 * Checks whether the customer has any items in the 
	 * shopping basket
	 *     
	 * @return <code>true</code> if there are any items, 
	 * <code>false</code> otherwise
	 */
	public boolean hasItems(){
		return !shoppingBasket.empty();
	}

	/**
	 * Gets the customer name
	 * 
	 * @return the customer name
	 */
	public String getCustomerName(){
		return name;
	}
}