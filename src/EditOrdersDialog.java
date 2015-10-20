import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class EditOrdersDialog extends AddEditOrderDialog{
	
	final static int DEFAULT_CHALKBOARD_INDEX = 0;
	
	private Order current_order;
	private OrderList master_order_list;
	private boolean editable;

	public EditOrdersDialog(OrderList master_list, Order order, boolean editable) {
		super(master_list);
		current_order = order;
		master_order_list = master_list;
		this.editable = editable;
		
		// Update the text displayed on some of the components.
		submit_button.setText("Save Order");
		cancel_button.setText("Close");
		
		banner_picture.setIcon(new ImageIcon("src/editbanner.jpg"));
		
		try {
			populateFields();
		}
		catch(InvalidOrderDataException e) {
			
		}
		
		cancel_button.addActionListener(new ButtonListener());
		submit_button.addActionListener(new ButtonListener());
		banner_picture.repaint();
	}
	
	private void populateFields() throws InvalidOrderDataException{
		name_textbox.setText(current_order.getCustomerName());
		username_textbox.setText(current_order.getCustomerUsername());
		number_textbox.setText(String.valueOf(current_order.getOrderNumber()));
		date_month_textbox.setText(String.valueOf(current_order.getShipDate().getMonth()));
		date_day_textbox.setText(String.valueOf(current_order.getShipDate().getDay()));
		date_year_textbox.setText(String.valueOf(current_order.getShipDate().getYear()));
		width_textbox.setText(String.valueOf(current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getChalkboardWidth()));
		height_textbox.setText(String.valueOf(current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getChalkboardHeight()));
		
		if(!editable) {
			name_textbox.setEditable(false);
			username_textbox.setEditable(false);
			number_textbox.setEditable(false);
			date_month_textbox.setEditable(false);
			date_day_textbox.setEditable(false);
			date_year_textbox.setEditable(false);
			width_textbox.setEditable(false);
			height_textbox.setEditable(false);
			hanging_combo.setEnabled(false);
			stain_combo.setEnabled(false);
			frame_combo.setEnabled(false);
			magnetic_combo.setEnabled(false);
			notes_text.setEditable(false);
		}
		
		try {
			stain_combo.setSelectedItem(getStainInput());
			frame_combo.setSelectedItem(getFrameInput());
			hanging_combo.setSelectedItem(getHardwareInput());
			magnetic_combo.setSelectedItem(getMagneticInput());
		}
		catch(InvalidOrderDataException e) {
			throw new InvalidOrderDataException("Order data error");
		}
	}
	
	private String getStainInput() throws InvalidOrderDataException{
		Frame.Stain_Type stain_type = current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getFrame().getStainType();
		if(stain_type == Frame.Stain_Type.EBONY) {
			return "Ebony";
		}
		else if(stain_type == Frame.Stain_Type.DARK_RED_MAHOGANY) {
			return "Dark Red Mahogany";
		}
		else if(stain_type == Frame.Stain_Type.RED_MAHOGANY) {
			return "Red Mahogany";
		}
		else if(stain_type == Frame.Stain_Type.GREY) {
			return "Grey";
		}
		else if(stain_type == Frame.Stain_Type.WHITE_PAINT) {
			return "Distressed White";
		}
		else {
			throw new InvalidOrderDataException("Invalid stain");
		}
	}
	
	private String getFrameInput() throws InvalidOrderDataException {
		Frame.Frame_Type frame_type = current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getFrame().getFrameType();
		if(frame_type == Frame.Frame_Type.MITERED) {
			return "Mitered";
		}
		else if(frame_type == Frame.Frame_Type.STRAIGHT) {
			return "Straight";
		}
		else {
			throw new InvalidOrderDataException("Invalid frame");
		}
	}
	
	private String getHardwareInput() throws InvalidOrderDataException {
		Chalkboard.Hanging_Type hanging_type = current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getHangingType();
		if(hanging_type == Chalkboard.Hanging_Type.HORIZONTAL) {
			return "Horizontal";
		}
		else if(hanging_type == Chalkboard.Hanging_Type.VERTICAL) {
			return "Vertical";
		}
		else if(hanging_type == Chalkboard.Hanging_Type.BOTH) {
			return "Both";
		}
		else if(hanging_type == Chalkboard.Hanging_Type.UNKNOWN) {
			return "Unknown";
		}
		else {
			throw new InvalidOrderDataException("Invalid hardware");
		}
	}
	
	private String getMagneticInput() throws InvalidOrderDataException {
		boolean is_magnetic = current_order.getChalkboard(DEFAULT_CHALKBOARD_INDEX).getBackingBoard().isMagnetic();
		if(is_magnetic == true) {
			return "Magnetic";
		}
		else if(is_magnetic == false) {
			return "Non-Magnetic";
		}
		else {
			throw new InvalidOrderDataException("Invalid chalkboard type");
		}
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(cancel_button)) {
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(EditOrdersDialog.this);
				top_frame.setVisible(false);
			}
			else if(e.getSource().equals(submit_button)) {
				Order updated_order = EditOrdersDialog.this.createOrder();
				if(updated_order == null || checkValidInput() == false) {
					displayAlert("Invalid input, please try again.");
				}
				else {
					updated_order.setCustomerName(name_textbox.getText());
					updated_order.setCustomerUsername(username_textbox.getText());
					updated_order.setOrderNumber(Integer.parseInt(number_textbox.getText()));
					updated_order.setShipDate(getDate());
					current_order.copyOrder(updated_order);
					master_order_list.save(MainDisplay.ORDER_LIST_FILENAME);
					displayAlert("Order Saved!");
				}
			}
		}
	}
}
