import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class SearchDialog extends JPanel {
	private static enum QUERY_TYPE {ORDER_NUMBER, SHIP_DATE, NAME, SIZE, DUE_BY_DATE};
	private static enum SEARCH_TYPE {COMPLETED, ACTIVE, BOTH, NONE};
	
	private JLabel banner_picture;
	private BufferedImage background_img;
	private JTextField search_box;
	private JLabel search_label;
	private JList<String> results_list;
	private DefaultListModel<String> list_model;
	private JScrollPane scroll_pane;
	private JButton search_button;
	private JButton close_button;
	private JButton edit_button;
	private JButton view_button;
	private ButtonGroup radio_button_group;
	private JCheckBox completed_checkbox;
	private JCheckBox active_checkbox;
	private JRadioButton order_number_button;
	private JRadioButton ship_date_button;
	private JRadioButton name_button;
	private JRadioButton size_button;
	private JRadioButton due_by_button;
	
	OrderList master_list;
	OrderList completed_list;
	
	public SearchDialog(OrderList master_order_list, OrderList completed_order_list) {
		master_list = master_order_list;
		completed_list = completed_order_list;
		banner_picture = new JLabel(new ImageIcon("src/searchbanner.jpg"));
		search_box = new JTextField(45);
		search_button = new JButton("Search");
		search_button.setBackground(Color.BLACK);
		search_button.setPreferredSize(new Dimension(150, 40));
		close_button = new JButton("Close");
		close_button.setPreferredSize(new Dimension(150, 40));
		close_button.setBackground(Color.BLACK);
		edit_button = new JButton("Edit Order");
		edit_button.setPreferredSize(new Dimension(150, 40));
		edit_button.setBackground(Color.BLACK);
		view_button = new JButton("View Order");
		view_button.setPreferredSize(new Dimension(150, 40));
		view_button.setBackground(Color.BLACK);
		
		completed_checkbox = new JCheckBox("Search Completed Orders");
		completed_checkbox.setForeground(Color.WHITE);
		completed_checkbox.setBackground(Color.BLACK);
		active_checkbox = new JCheckBox("Search Active Orders");
		active_checkbox.setForeground(Color.WHITE);
		active_checkbox.setBackground(Color.BLACK);
		active_checkbox.setSelected(true);

		scroll_pane = new JScrollPane();
		list_model = new DefaultListModel<String>();
		results_list = new JList<String>(list_model);
		scroll_pane.setPreferredSize(new Dimension(600, 300));
		scroll_pane.setViewportView(results_list);

		search_label = new JLabel("Search Query:");
		search_label.setForeground(Color.WHITE);
		
		order_number_button = new JRadioButton("Order Number");
		order_number_button.setForeground(Color.WHITE);
		order_number_button.setBackground(Color.BLACK);
		ship_date_button = new JRadioButton("Ship Date");
		ship_date_button.setForeground(Color.WHITE);
		ship_date_button.setBackground(Color.BLACK);
		name_button = new JRadioButton("Name");
		name_button.setForeground(Color.WHITE);
		name_button.setBackground(Color.BLACK);
		size_button = new JRadioButton("Size");
		size_button.setForeground(Color.WHITE);
		size_button.setBackground(Color.BLACK);
		due_by_button = new JRadioButton("Due By Date");
		due_by_button.setForeground(Color.WHITE);
		due_by_button.setBackground(Color.BLACK);
		
		radio_button_group = new ButtonGroup();
		radio_button_group.add(order_number_button);
		radio_button_group.add(ship_date_button);
		radio_button_group.add(name_button);
		radio_button_group.add(size_button);
		radio_button_group.add(due_by_button);
		order_number_button.setSelected(true);
		
		search_button.addActionListener(new ButtonListener());
		close_button.addActionListener(new ButtonListener());
		edit_button.addActionListener(new ButtonListener());
		search_box.addActionListener(new ButtonListener());
		
		JPanel button_panel = new JPanel(new GridLayout(1, 4));
		button_panel.add(search_button);
		button_panel.add(edit_button);
		button_panel.add(view_button);
		button_panel.add(close_button);
		button_panel.setBackground(Color.BLACK);
		
		JPanel search_type_panel = new JPanel(new GridLayout(1, 2));
		search_type_panel.add(active_checkbox);
		search_type_panel.add(completed_checkbox);
		search_type_panel.setBackground(Color.BLACK);
		
		this.add(banner_picture);
		this.add(search_label);
		this.add(search_box);
		this.add(order_number_button);
		this.add(ship_date_button);
		this.add(name_button);
		this.add(size_button);
		this.add(due_by_button);
		this.add(scroll_pane);
		this.add(button_panel);
		this.add(search_type_panel);
	}
	
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/searchbackground.jpg"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		super.paintComponent(g);
		g.drawImage(background_img, 0, 0, getWidth(), getHeight(), this);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(close_button)) {
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(SearchDialog.this);
				top_frame.setVisible(false);
			}
			else if(e.getSource().equals(search_button) || e.getSource().equals(search_box)) {
				QUERY_TYPE query_type = getQueryType();
				SEARCH_TYPE search_type = getSearchType();
				if(query_type == null) {
					displayAlert("No query type was selected.");
				}
				else if(search_type.equals(SEARCH_TYPE.NONE)) {
					displayAlert("No order type (active/complete) selected.");
				}
				else if(search_box.getText().equals("")) {
					displayAlert("No search term was entered.");
				}
				else {
					processSearch(query_type, search_type);
				}
			}
			else if(e.getSource().equals(view_button)) {
				Order selected_order = getSelectedOrder();
				if(selected_order == null) {
					displayAlert("No order selected");
				}
				else {
					// TODO: code else
				}
			}
			else if(e.getSource().equals(edit_button)) {
				Order selected_order = getSelectedOrder();
				if(selected_order == null) {
					displayAlert("No order selected");
				}
				else {
					JFrame frame = new JFrame();
					frame.setSize(700, 800);
					// TODO: set editable to false for EditOrdersDialog if searching completed orders
					frame.add(new EditOrdersDialog(master_list, selected_order, true));
					frame.setLocationRelativeTo(SearchDialog.this);
					frame.addWindowListener(new WinListener());
					frame.setVisible(true);
				}
			}
		}
	}
	
	/*
	 * This listener reloads the orders after the edit window is deactivated.
	 * This is done so that if the user edits an order selected through
	 * the ViewOrdersDialog then it updates when the user saves the order.
	 */
	private class WinListener implements WindowListener {
		public void windowDeactivated(WindowEvent event) {
			loadResults(master_list.getOrders());
		};
		public void windowClosed(WindowEvent event) {}
		public void windowOpened(WindowEvent event) {}
		public void windowClosing(WindowEvent event) {}	
		public void windowDeiconified(WindowEvent event) {}
		public void windowActivated(WindowEvent event) {}
		public void windowIconified(WindowEvent event) {}
		
	}
	
	private Order getSelectedOrder() {
		String selected_line = results_list.getSelectedValue();
		if(selected_line == null) {
			return null;
		}
		Scanner scan = new Scanner(selected_line);
		String order_num = scan.next();
		Order return_order = master_list.getOrder(Integer.parseInt(order_num));
		scan.close();
		if(return_order == null) {
			return null;
		}
		return return_order;
	}
	
	/*
	 * Call a specific search processing method based on the QUERY_TYPE selected.
	 */
	private void processSearch(QUERY_TYPE query_type, SEARCH_TYPE search_type) {
		if(query_type == QUERY_TYPE.ORDER_NUMBER) {
			processOrderNumberQuery(search_type);
		}
		else if(query_type == QUERY_TYPE.SHIP_DATE) {
			processShipDateQuery(search_type);
		}
		else if(query_type == QUERY_TYPE.NAME) {
			processNameQuery(search_type);
		}
		else if(query_type == QUERY_TYPE.SIZE) {
			processSizeQuery(search_type);
		}
		else if(query_type == QUERY_TYPE.DUE_BY_DATE) {
			processDueByQuery(search_type);
		}
	}
	
	/*
	 * Find and load all orders that match a specific order number.
	 */
	private void processOrderNumberQuery(SEARCH_TYPE search_type) {
		Vector<Order> search_results = new Vector<Order>();
		String str_order_number = search_box.getText();
		int order_number;
		if(!checkValidOrderNumber(str_order_number)) {
			displayAlert("Invalid input format.");
		}
		else {
			order_number = Integer.parseInt(str_order_number);
			Order matching_order = null;

			/*
			 * Check if active, completed or both types of search are selected. 
			 */
			if(search_type.equals(SEARCH_TYPE.ACTIVE)) {
				matching_order = master_list.getOrder(order_number);
			}
			else if(search_type.equals(SEARCH_TYPE.COMPLETED)) {
				matching_order = completed_list.getOrder(order_number);
			}
			else if(search_type.equals(SEARCH_TYPE.BOTH)) {
				matching_order = master_list.getOrder(order_number);
				// Only check the completed list if nothing is found in the active
				// list because order numbers are unique and won't be in both lists.
				if(matching_order == null) {
					matching_order = completed_list.getOrder(order_number);
				}
			}
			
			if(matching_order != null) {
				loadResults(search_results);
			}
		}
	}
	
	// TODO: make all search functions check for active/completed checked boxes.
	
	/*
	 * Find and load all orders that match a specific ship date.
	 */
	private void processShipDateQuery(SEARCH_TYPE search_type) {
		Vector<Order> search_results = new Vector<Order>();
        String str_ship_date = search_box.getText();
        ShippingDate date;
        try {
            date = parseDateString(str_ship_date);
        }
        catch(InputMismatchException e) {
            displayAlert("Invalid format for search: Ship Date");
            return;
        }
        
        if(active_checkbox.isSelected() && completed_checkbox.isSelected()) {
        	search_results.addAll(master_list.getOrder(date));
        	search_results.addAll(completed_list.getOrder(date));
        }
        else if(active_checkbox.isSelected() && !completed_checkbox.isSelected()) {
        	search_results.addAll(master_list.getOrder(date));
        }
        else if(!active_checkbox.isSelected() && completed_checkbox.isSelected()) {
        	search_results.addAll(completed_list.getOrder(date));
        }
		loadResults(search_results);
	}
	
	/*
	 * Search for and load orders based on the name input from the user.
	 */
	private void processNameQuery(SEARCH_TYPE search_type) {
		Vector<Order> search_results = new Vector<Order>();
        String name = search_box.getText();
        Order matching_order = master_list.getOrder(name);
        if(matching_order != null) {
            search_results.add(matching_order);
        }
        loadResults(search_results);
	}
	
	private void processDueByQuery(SEARCH_TYPE search_type) {
		Vector<Order> search_results = new Vector<Order>();
		String str_due_by_date = search_box.getText();
		ShippingDate date;
		try {
			date = parseDateString(str_due_by_date);
		}
		catch(InputMismatchException e) {
			displayAlert("Invalid format for search: Due By Date");
			return;
		}
		
		Vector<Order> all_orders = master_list.getOrders();
		for(int i = 0; i < all_orders.size(); i++) {
			if(all_orders.get(i).getShipDate().comesBeforeDate(date)) {
				search_results.add(all_orders.get(i));
			}
		}
		loadResults(search_results);
	}
	
	/*
	 * Search for and load orders based on the size input from the user.
	 */
	private void processSizeQuery(SEARCH_TYPE search_type) {
		Vector<Order> search_results = new Vector<Order>();
        String str_size = search_box.getText();
        Pair<Integer> dimensions;
        try {
          dimensions = parseDimensions(str_size);
        }
        catch(InputMismatchException e) {
            displayAlert("Invalid format for search: Size");
            return;
        }
        search_results = master_list.getOrder(dimensions.getValueOne(), dimensions.getValueTwo());
		loadResults(search_results);
	}
	
	/*
	 * Check if the order number entered by the user is valid.
	 */
	private boolean checkValidOrderNumber(String order_number) {
		try {
			Integer.parseInt(order_number);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/*
	 * Insert each element of the Vector<Order> into the list_model to be displayed in the JList.
	 */
	private void loadResults(Vector<Order> orders) {
		list_model.clear();
		if(orders.size() != 0) {
			String format = "%1$10s %2$20s %3$45s %4$30s";
            for(int i = 0; i < orders.size(); i++) {
                String customer_name = orders.get(i).getCustomerName();
                String order_number = String.valueOf(orders.get(i).getOrderNumber());
                String ship_date = orders.get(i).getShipDate().getMonth() + "/" +
                                   orders.get(i).getShipDate().getDay() + "/" +
                                   orders.get(i).getShipDate().getYear();
                String item_name = createItemString(orders.get(i).getChalkboard(0));
                String formatted_string = String.format(format,  order_number, customer_name, item_name, ship_date);
                list_model.addElement(formatted_string);
            }
		}
		else {
			list_model.addElement("No orders found.");
		}
	}
	
	/*
	 * Creates a String that has information about an order (size, style, stain)
	 */
	public String createItemString(Chalkboard board) {
		String size_str = board.getChalkboardWidth() + "x" + board.getChalkboardHeight();
		String stain_str = board.getFrame().getStainTypeString();
		String frame_str = board.getFrame().getFrameTypeString();
		return size_str + " - " + stain_str + " - " + frame_str;
	}

	/*
	 * Display an alert in a pop-up dialog box.
	 */
	protected void displayAlert(String message) {
        AlertDialog alert_dialog = new AlertDialog(message);
        alert_dialog.setLocationRelativeTo(SearchDialog.this);
        alert_dialog.setSize(250, 100);
        alert_dialog.setVisible(true);
	}
	
	/*
	 * Return a ShippingDate object with the date entered in the search_box;
	 */
	private ShippingDate parseDateString(String entered_string) throws InputMismatchException{
		Scanner scan = new Scanner(entered_string);
		scan.useDelimiter("[/-]");
		int month = scan.nextInt();
		int day = scan.nextInt();
		int year = scan.nextInt();
		scan.close();
		
		ShippingDate date = new ShippingDate(month, day, year);
		return date;
	}
	
	/*
	 * Return a Pair<T> object with the dimensions entered in the search_box.
	 */
	private Pair<Integer> parseDimensions(String entered_string) throws InputMismatchException{
		Scanner scan = new Scanner(entered_string);
		scan.useDelimiter("[xX]");
		int height = scan.nextInt();
		int width = scan.nextInt();
		scan.close();
		return new Pair<Integer>(height, width);
	}
	
	/*
	 * Check which radio button is selected. Return the enum type QUERY_TYPE.
	 */
	private QUERY_TYPE getQueryType() {
		// Loop through the radio buttons to check which is selected, return enum if found.
		for(Enumeration<AbstractButton> buttons = radio_button_group.getElements(); buttons.hasMoreElements();) {
			JRadioButton button = (JRadioButton) buttons.nextElement();
			if(button.isSelected()) {
				return checkQueryType(button.getText());
			}
		}
		//return null if no button was selected
		return null;
	}
	
	/*
	 * Determine and return the QUERY_TYPE selected based on the text of the radio button selected.
	 */
	private QUERY_TYPE checkQueryType(String button_text) {
		if(button_text.equals(order_number_button.getText())) {
			return QUERY_TYPE.ORDER_NUMBER;
		}
		else if(button_text.equals(ship_date_button.getText())) {
			return QUERY_TYPE.SHIP_DATE;
		}
		else if(button_text.equals(name_button.getText())) {
			return QUERY_TYPE.NAME;
		}
		else if(button_text.equals(size_button.getText())) {
			return QUERY_TYPE.SIZE;
		}
		else if(button_text.equals(due_by_button.getText())) {
			return QUERY_TYPE.DUE_BY_DATE;
		}
		else {
			return null;
		}
	}
	
	/*
	 * Check which order type the user wants to search for, completed or active.
	 */
	private SEARCH_TYPE getSearchType() {
		if(active_checkbox.isSelected() && completed_checkbox.isSelected()) {
			return SEARCH_TYPE.BOTH;
		}
		else if(active_checkbox.isSelected()) {
			return SEARCH_TYPE.ACTIVE;
		}
		else if(completed_checkbox.isSelected()) {
			return SEARCH_TYPE.COMPLETED;
		}
		else {
			return SEARCH_TYPE.NONE;
		}
	}
}