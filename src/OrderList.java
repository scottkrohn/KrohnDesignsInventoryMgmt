import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class OrderList implements Serializable{
	protected Vector<Order> orders;
	
	public OrderList() {
		orders = new Vector<Order>();
	}
	
	public OrderList(Vector<Order> order_container) {
		orders = new Vector(order_container);
	}
	
	public void save(String filename) {
		FileOutputStream file_out = null;
		ObjectOutputStream object_out = null;
		try {
			file_out = new FileOutputStream(filename);
			object_out = new ObjectOutputStream(file_out);
			object_out.writeObject(this);
			object_out.close();
			file_out.close();
		}
		catch(Exception e) {
			// TODO: code exception
			System.out.println(e.toString());
		}
	}
	
	public void load(String filename) {
		FileInputStream file_in = null;
		ObjectInputStream object_in = null;
		OrderList loaded_orders = null;
		try {
			file_in = new FileInputStream(filename);
			object_in = new ObjectInputStream(file_in);
			loaded_orders = (OrderList) object_in.readObject();
			this.orders = loaded_orders.getOrders();
			file_in.close();
			object_in.close();
		}
		catch(Exception e) {
			// TODO: code exception
		}
	}
	
	public int getNumberSold() {
		return orders.size();
	}
	
	/*
	 * Adds a new order to the Vector of sold orders. Returns true if 
	 * adding the new order was successful. Returns false if adding fails
	 * or if the order already exists inside the vector.
	 */
	public boolean addSoldOrder(Order new_order) {
		if(orderAlreadyExists(new_order.getOrderNumber())) {
			return false;
		}
		return orders.add(new_order);
	}
	
	/*
	 * Returns true if an order with an orders number matching
	 * the argument 'number' is already in the vector. False otherwise.
	 */
	private boolean orderAlreadyExists(int number) {
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getOrderNumber() == number) {
				System.out.println(orders.toString());
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Returns the entire vector of sold orders.
	 */
	public Vector<Order> getOrders() {
		return orders;
	}
	
	/*
	 * Searches the orders for an Order object with a customer_name or customer_username
	 * value that matches the argument 'name', or contains a portion of it. 
	 * Returns that object if found. Returns null if no match is found.
	 */
	public Order getOrder(String name) {
		String lowercase_name = name.toLowerCase();
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getCustomerName().toLowerCase().contains(lowercase_name)) {
				return orders.get(i);
			}
			else if(orders.get(i).getCustomerUsername().equalsIgnoreCase(name)) {
				return orders.get(i);
			}
		}
		return null;
	}
	
	/*
	 * Searches the orders for an Order object with an order_number that
	 * matches the argument 'number'. Returns that Order if found, returns 
	 * null if it's not found.
	 */
	public Order getOrder(int number) {
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getOrderNumber() == number) {
				return orders.get(i);
			}
		}
		return null;
	}
	
	/*
	 * Searches the orders for Order objects with a ShippingDate that matches date.
	 * If found, it adds them to a vector and then return the vector. If no orders are found,
	 * the vector is return empty.
	 */
	public Vector<Order> getOrder(ShippingDate date) {
		Vector<Order> found_orders = new Vector<Order>();
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getShipDate().equals(date)) {
				found_orders.add(orders.get(i));
			}
		}
		return found_orders;
	}
	
	public Vector<Order> getOrder(int height, int width) {
		Vector<Order> found_orders = new Vector<Order>();
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getChalkboard(0).sizeEquals(height, width)) {
				found_orders.add(orders.get(i));
			}
		}
		return found_orders;
	}
	
	/*
	 * Searches the vector of orders for one with an order_number
	 * matching the argument "number". Removes and returns true if found,
	 * does nothing and returns false if not found.
	 */
	public boolean removeOrder(int number) {
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getOrderNumber() == number) {
				return orders.remove(orders.get(i));
			}
		}
		return false;
	}
	
	/*
	 * Searches the vector of orders for one with a customer_name
	 * or customer_username that matches the argument 'Name". Removes
	 * the order and returns true if found, does nothing and returns false
	 * if not found.
	 */
	public boolean removeOrder(String name) {
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getCustomerName().equals(name) ||
					orders.get(i).getCustomerUsername().equals(name)) {
				return orders.remove(orders.get(i));
			}
		}
		return false;
	}
	
	public boolean removeOrder(Order order) {
		return orders.remove(order);
	}
	
	public String toString() {
		String return_string = "";
		for(int i = 0; i < orders.size(); i++) {
			return_string += orders.get(i).toString();
		}
		return return_string;
	}
}
