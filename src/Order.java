import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class Order implements Serializable{
	private int number_of_items;
	private String customer_name;
	private String customer_username;
	private int order_number;
	private ShippingDate ship_date;
	private Vector<Chalkboard> items;
	private boolean is_shipped;
	private String order_notes;
	
	public Order() {
		number_of_items = -1;
		customer_name = "unknown";
		customer_username = "unknown";
		order_number = -1;
		ship_date = new ShippingDate();
		items = new Vector<Chalkboard>();
		is_shipped = false;
		order_notes = new String("");
	}
	
	/*
	 * Returns a Chalkboard object from the Vector of items in the order.
	 * Checks to make sure the index passed to the function is a valid
	 * index for the Vector.
	 */
	public Chalkboard getChalkboard(int item_number) {
		if(item_number >= 0 && item_number < items.size()) {
			return items.get(item_number);
		}
		return null;
	}
	
	public void copyOrder(Order new_order) {
		this.number_of_items = new_order.number_of_items;
		this.customer_name = new_order.customer_name;
		this.customer_username = new_order.customer_username;
		this.order_number = new_order.order_number;
		this.ship_date = new_order.ship_date;
		this.items = new_order.items;
		this.is_shipped = new_order.is_shipped;
		this.order_notes = new_order.order_notes;
	}
	
	public void addChalkboard(Chalkboard board) {
		items.add(board);
	}
	
	public boolean isShipped() {
		return is_shipped;
	}
	
	public int getItemCount() {
		return number_of_items;
	}
	
	public String getCustomerName() {
		return customer_name;
	}
	
	public String getCustomerUsername() {
		return customer_username;
	}
	
	public int getOrderNumber() {
		return order_number;
	}
	
	public ShippingDate getShipDate() {
		return ship_date;
	}
	
	public String getOrderNotes() {
		return order_notes;
	}
	
	public void setItemCount(int count) {
		number_of_items = count;
	}
	
	public void setCustomerName(String name) {
		customer_name = name;
	}
	
	public void setCustomerUsername(String username) {
		customer_username = username;
	}

	public void setOrderNumber(int number) {
		order_number = number;
	}
	
	public void setShipDate(ShippingDate date) {
		ship_date = date;
	}
	
	public void addOrderNotes(String notes) {
		order_notes = notes;
	}
	
	public String toString() {
		String return_string = "Name: " + customer_name + "\n" +
							   "Username: " + customer_username + "\n" +
							   "Order #: " + order_number + "\n" +
							   "Items: " + number_of_items + "\n" +
							   "Ship Date: " + ship_date.getMonth() + "/" + ship_date.getDay() + "/" + ship_date.getYear() + "\n";
		for(int i = 0; i < items.size(); i++) {
			return_string += items.get(i).toString() + "\n";
		}
		return return_string;
	}
	
	
}
