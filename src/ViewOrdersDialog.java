import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class ViewOrdersDialog extends JPanel{
	private JList<String> order_list;
	private JLabel title_label;
	private JButton close_button;
	private JButton view_button;
	private JButton delete_button;
	private JButton edit_button;
	private JButton complete_button;
	private JScrollPane scroll_pane;
	private DefaultListModel<String> list_model;
	private OrderList master_order_list;
	private OrderList completed_order_list;
	private BufferedImage background_img;
	private ConfirmDialog confirm_window;

	// Constructor
	public ViewOrdersDialog(OrderList master_order_list, OrderList completed_order_list) {
		this.master_order_list = master_order_list;
		this.completed_order_list = completed_order_list;
		
		ImageIcon banner_image = new ImageIcon("src/viewbanner.jpg");
		title_label = new JLabel(banner_image);
		
		confirm_window = new ConfirmDialog("", "", "");
		close_button = new JButton("Close");
		view_button = new JButton("View/Update Order");
		complete_button = new JButton("Complete Order");
		delete_button = new JButton("Delete Order");
		edit_button = new JButton("Edit Order");
		edit_button.setPreferredSize(new Dimension(160, 40));
		close_button.setPreferredSize(new Dimension(160, 40));
		view_button.setPreferredSize(new Dimension(160, 40));
		complete_button.setPreferredSize(new Dimension(160, 40));
		delete_button.setPreferredSize(new Dimension(160, 40));

		scroll_pane = new JScrollPane();
		list_model = new DefaultListModel<String>();
		order_list = new JList<String>(list_model);
		scroll_pane.setViewportView(order_list);
		
		order_list.setFont(new Font("Andale Mono", Font.PLAIN, 12));
		
		JPanel button_panel = new JPanel(new GridLayout(1, 3));
		button_panel.add(view_button);
		button_panel.add(complete_button);
		button_panel.add(edit_button);
		button_panel.add(delete_button);
		button_panel.add(close_button);
		button_panel.setBackground(Color.BLACK);
		
		close_button.addActionListener(new ButtonListener());
		view_button.addActionListener(new ButtonListener());
		edit_button.addActionListener(new ButtonListener());
		complete_button.addActionListener(new ButtonListener());
		delete_button.addActionListener(new ButtonListener());
		
		this.add(title_label);
		this.add(scroll_pane);
		this.add(button_panel);
		scroll_pane.setPreferredSize(new Dimension(800, 500));
		
		loadOrders(master_order_list);
		
		this.setVisible(true);
	}
	
	// Paints the frame using a background_img. Attempts to read from file,
	// prints error to log if the file isn't found.
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/viewbackground.jpg"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		super.paintComponent(g);
		g.drawImage(background_img, 0, 0, getWidth(), getHeight(), this);
	}
	
	/*
	 * Calls the appropriate processing method when the user selects a button.
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(close_button)) {
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(ViewOrdersDialog.this);
				top_frame.setVisible(false);
			}
			else if(e.getSource().equals(view_button)) {
				processView();
			}
			else if(e.getSource().equals(edit_button)) {
				processEdit();
			}
			else if(e.getSource().equals(delete_button)) {
				processDelete();
			}
			else if(e.getSource().equals(complete_button)) {
				processComplete();
			}
		}
	}
	
	/*
	 * Transfers an order from the master_order_list to the completed_order_list.
	 * Prompts user with a QDialog to confirm their choice, then reloads orders on the
	 * current screen if necessary.
	 */
	private void processComplete() {
        Order selected_order = getSelectedOrder();
        if(selected_order == null) {
            displayAlert("No order was selected for completion");
        }
        else {
            if(displayConfirmDialog("Are you sure you want to mark this order as complete?", 
                                "Yes", "No").equals(ConfirmDialog.Confirm_Status.CONFIRM)) {
                addToCompletedOrders(selected_order);
                master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
                completed_order_list.save(MainDisplay.COMPLETED_LIST_FILENAME);
                loadOrders(master_order_list);
            }
            else {
                return;
            }
        }
	}
	
	/*
	 * Displays a dialog that allows the user to view/update an order in progress.
	 */
	private void processView() {
		Order selected_order = getSelectedOrder();
        if(selected_order == null) {
            displayAlert("No order was selected to view");
        }
        else {
            JFrame frame = new JFrame();
            frame.setSize(700, 800);	
            frame.add(new UpdateOrderDialog(master_order_list, selected_order));
            frame.setLocationRelativeTo(ViewOrdersDialog.this);
            frame.setVisible(true);
		}
	}
	
	/*
	 * Displays a dialog that allows the user to edit the details of an order (size, stain, etc) 
	 */
	private void processEdit() {
        Order selected_order = getSelectedOrder();
        if(selected_order == null) {
            displayAlert("No order was selected for editing");
        }
        else {
            JFrame frame = new JFrame();
            frame.setSize(700, 800);
            frame.add(new EditOrdersDialog(master_order_list, selected_order, true));
            frame.setLocationRelativeTo(ViewOrdersDialog.this);
            frame.addWindowListener(new WinListener());
            frame.setVisible(true);
		}
	}
	
	/*
	 * Deletes the selected active order from the master_order list. 
	 * Prompts the user with a QDialog box to confirm their choice to 
	 * delete an order.
	 */
	private void processDelete() {
		Order selected_order = getSelectedOrder();
            if(selected_order == null) {
                displayAlert("No order was selected for removal.");
                return;
            }
            else {
                if(displayConfirmDialog("<html>Are you sure you want to delete this order?<br>This cannot be undone!<html>",
                        "Yes", "No").equals(ConfirmDialog.Confirm_Status.CONFIRM)) {
                    if(!master_order_list.removeOrder(selected_order)) {
                        displayAlert("Order was NOT successfully deleted");
                        return;
                    }
                    master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
                    loadOrders(master_order_list);
                }
            }
	}
	
	/*
	 * Displays a confirmation QDialog to the user with two options, confirm and deny. Returns the
	 * result of the users choice.
	 */
	private ConfirmDialog.Confirm_Status displayConfirmDialog(String message, String confirm, String deny) {
		confirm_window.updateMessages(message, confirm, deny);
		confirm_window.setModal(true);
		confirm_window.setLocationRelativeTo(ViewOrdersDialog.this);
		confirm_window.setVisible(true);
		return confirm_window.getValue();
	}
	
	/*
	 * Adds the argument order to the Vector of completed orders, then saves.
	 * Also removes the argument order from the master_order list object.
	 */

	private void addToCompletedOrders(Order order) {
		// Display an alert if adding the order to completed orders fails.
		if(!completed_order_list.addSoldOrder(order)) {
			displayAlert("Order not successfully completed.");
			return;
		}
		else {
			master_order_list.removeOrder(order);
			master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
			completed_order_list.save(MainDisplay.COMPLETED_LIST_FILENAME);
		}
	}
	
	/*
	 * This listener reloads the orders after the edit window is deactivated.
	 * This is done so that if the user edits an order selected through
	 * the ViewOrdersDialog then it updates when the user saves the order.
	 */
	private class WinListener implements WindowListener {
		public void windowDeactivated(WindowEvent event) {
			loadOrders(master_order_list);

		};
		public void windowClosed(WindowEvent event) {}
		public void windowOpened(WindowEvent event) {}
		public void windowClosing(WindowEvent event) {}	
		public void windowDeiconified(WindowEvent event) {}
		public void windowActivated(WindowEvent event) {}
		public void windowIconified(WindowEvent event) {}
		
	}

	/*
	 * Checks if the user has selected an order from the JList. Returns the order
	 * they've selected, or returns null if nothing was selected. This function retrieves
	 * the order based on the order number since all order numbers are unique.
	 */
	private Order getSelectedOrder() {
		String selected_line = order_list.getSelectedValue();
		if(selected_line == null) {
			return null;
		}
		Scanner scan = new Scanner(selected_line);
		String order_num = scan.next();
		Order return_order = master_order_list.getOrder(Integer.parseInt(order_num));
		scan.close();
		if(return_order == null) {
			return null;
		}
		return return_order;
	}
	
	/*
	 * Display a simple dialog box with an OK button and a message;
	 */
	private void displayAlert(String message) {
		AlertDialog alert_dialog = new AlertDialog(message);
        alert_dialog.setLocationRelativeTo(ViewOrdersDialog.this);
        alert_dialog.setSize(250, 100);
        alert_dialog.setVisible(true);
	}
	
	/*
	 * Create a string representing the details of an Order (size, stain, frame type)
	 */
	public static String createItemString(Chalkboard board) {
		String size_str = board.getChalkboardWidth() + "x" + board.getChalkboardHeight();
		String stain_str = board.getFrame().getStainTypeString();
		String frame_str = board.getFrame().getFrameTypeString();
		return size_str + " - " + stain_str + " - " + frame_str;
	}
	
	/*
	 * Format the orders in master_order_list and then load the in the JList to be displayed.
	 */
	private void loadOrders(OrderList master_order_list) {
		list_model.clear();
		String format = "%1$10s %2$20s %3$45s %4$30s";
		Vector<Order> orders = master_order_list.getOrders();
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
}
