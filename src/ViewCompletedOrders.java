import java.awt.Dimension;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

import java.util.Vector;
import java.util.Scanner;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ViewCompletedOrders  extends JPanel{
	private JList<String> order_list;
	private JLabel title_label;
	private JButton close_button;
	private JButton view_button;
	private JButton uncomplete_button;
	private JScrollPane scroll_pane;
	private DefaultListModel<String> list_model;
	private OrderList completed_order_list;
	private OrderList master_order_list;
	private ConfirmDialog confirm_window;
	private BufferedImage background_img;
	
	// Constructor
	public ViewCompletedOrders(OrderList master_orders, OrderList completed_orders) {
		completed_order_list = completed_orders;
		master_order_list = master_orders;
		
		confirm_window = new ConfirmDialog();
		
		ImageIcon banner_image = new ImageIcon("src/completedbanner.jpg");
		title_label = new JLabel(banner_image);
		
		close_button = new JButton("Close");
		view_button = new JButton("View Order");
		uncomplete_button = new JButton("Un-Complete Order");
		close_button.setPreferredSize(new Dimension(150, 40));
		view_button.setPreferredSize(new Dimension(150, 40));
		uncomplete_button.setPreferredSize(new Dimension(150, 40));

		scroll_pane = new JScrollPane();
		list_model = new DefaultListModel<String>();
		order_list = new JList<String>(list_model);
		scroll_pane.setViewportView(order_list);
		order_list.setFont(new Font("Andale Mono", Font.PLAIN, 12));
		
		JPanel button_panel = new JPanel(new GridLayout(1, 3));
		button_panel.add(view_button);
		button_panel.add(uncomplete_button);
		button_panel.add(close_button);
		
		close_button.addActionListener(new ButtonListener());
		view_button.addActionListener(new ButtonListener());
		uncomplete_button.addActionListener(new ButtonListener());
		
		this.add(title_label);
		this.add(scroll_pane);
		this.add(button_panel);
		scroll_pane.setPreferredSize(new Dimension(800, 500));
		
		loadOrders(completed_orders);
		this.setVisible(true);
	}
	
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/background.jpg"));
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
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(ViewCompletedOrders.this);
				top_frame.setVisible(false);
				master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
				completed_order_list.save(MainDisplay.COMPLETED_LIST_FILENAME);
			}
			else if(e.getSource().equals(view_button)) {
				Order selected_order = getSelectedOrder();
				if(selected_order == null) {
					displayAlert("No order selected to view.");
					return;
				}
				JFrame frame = new JFrame();
				frame.setSize(700, 800);
				frame.add(new EditOrdersDialog(master_order_list, getSelectedOrder(), false));
				frame.setLocationRelativeTo(ViewCompletedOrders.this);
				frame.setVisible(true);
			}
			else if(e.getSource().equals(uncomplete_button)) {
				Order selected_order = getSelectedOrder();
				if(selected_order == null) {
					displayAlert("No order was selected.");
					return;
				}
				if(displayConfirmDialog("Are you sure you want to make this listing active?",
										"Yes", "No").equals(ConfirmDialog.Confirm_Status.CONFIRM)) {
					uncompleteOrder(selected_order);
					loadOrders(completed_order_list);
				}
			}
		}
	}
	
	/*
	 * Attempts to remove the selected order from the completed order list. If
	 * that succeeds, it attempts to add it to the master_order_list. Returns
	 * true if both actions are successful.
	 */
	private boolean uncompleteOrder(Order order) {
		if(!completed_order_list.removeOrder(order)) {
			return false;
		}
		else if(!master_order_list.addSoldOrder(order)) {
			return false;
		}
		return true;
	}
	
	
	
	private void displayAlert(String message) {
		AlertDialog alert_dialog = new AlertDialog(message);
		alert_dialog.setSize(250, 100);
		alert_dialog.setLocationRelativeTo(ViewCompletedOrders.this);
		alert_dialog.setVisible(true);
	}
	
	private Order getSelectedOrder() {
		String selected_line = order_list.getSelectedValue();
		if(selected_line == null) {
			return null;
		}
		Scanner scan = new Scanner(selected_line);
		String order_number = scan.next();
		Order return_order = completed_order_list.getOrder(Integer.parseInt(order_number));
		scan.close();
		if(return_order == null) {
			return null;
		}
		return return_order;
	}
	
	private ConfirmDialog.Confirm_Status displayConfirmDialog(String message, String confirm, String deny) {
		confirm_window.updateMessages(message, confirm, deny);
		confirm_window.setModal(true);
		confirm_window.setLocationRelativeTo(ViewCompletedOrders.this);
		confirm_window.setVisible(true);
		return confirm_window.getValue();
	}
	
	private void loadOrders(OrderList order_list) {
		list_model.clear();
		Vector<Order> orders = completed_order_list.getOrders();
		String format = "%1$10s %2$20s %3$35s %4$30s";
		for(int i = 0; i < orders.size(); i++) {
			String customer_name = orders.get(i).getCustomerName();
			String order_number = String.valueOf(orders.get(i).getOrderNumber());
			String ship_date = orders.get(i).getShipDate().getMonth() + "/" +
							   orders.get(i).getShipDate().getDay() + "/" +
							   orders.get(i).getShipDate().getYear();
			String item_name = ViewOrdersDialog.createItemString(orders.get(i).getChalkboard(0));
			String formatted_string = String.format(format, order_number, customer_name, item_name, ship_date);
			list_model.addElement(formatted_string);
		}
	}
}
