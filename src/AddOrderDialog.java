import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class AddOrderDialog extends AddEditOrderDialog{

	public AddOrderDialog(OrderList master_list) {
		super(master_list);
		submit_button.setText("Add Order");
		cancel_button.setText("Close");

		submit_button.addActionListener(new ButtonListener());
		cancel_button.addActionListener(new ButtonListener());
	}

	protected class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(submit_button)) {
				Order order = AddOrderDialog.this.createOrder();
				if(order == null || checkValidInput() == false) {
					displayAlert("Invalid input, please try again");
				}
				else {
					order.setCustomerName(name_textbox.getText());
					order.setCustomerUsername(username_textbox.getText());
					order.setOrderNumber(Integer.parseInt(number_textbox.getText()));
					order.setShipDate(getDate());
					master_order_list.addSoldOrder(order);
					master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
					displayAlert("Order successfully added!");
					clearFields();
				}
			}
			else if(e.getSource().equals(cancel_button)) {
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(AddOrderDialog.this);
				top_frame.setVisible(false);
			}
		}
	}
}
