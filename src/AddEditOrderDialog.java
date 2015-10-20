import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

// TEST TEST TEST
public abstract class AddEditOrderDialog extends JPanel{
	protected JLabel title_label;
	protected JLabel customer_name_label;
	protected JLabel username_label;
	protected JLabel order_number_label;
	protected JLabel ship_label;
	protected JLabel size_label;
	protected JLabel frame_label;
	protected JLabel stain_label;
	protected JLabel magnetic_label;
	protected JLabel hanging_label;
	protected JLabel notes_label;
	protected BufferedImage background_img;
	protected JLabel banner_picture;
	
	protected JTextField name_textbox;
	protected JTextField username_textbox;
	protected JTextField number_textbox;
	protected JTextField date_month_textbox;
	protected JTextField date_day_textbox;
	protected JTextField date_year_textbox;
	protected JTextField width_textbox;
	protected JTextField height_textbox;
	
	protected JTextArea notes_text;
	
	protected JComboBox<String> stain_combo;
	protected JComboBox<String> frame_combo;
	protected JComboBox<String> hanging_combo;
	protected JComboBox<String> magnetic_combo;
	
	protected JButton submit_button;
	protected JButton cancel_button;
	
	protected OrderList master_order_list;
	
	public AddEditOrderDialog(OrderList master_list) {
		
		master_order_list = master_list;
		
		submit_button = new JButton("Submit Order");
		cancel_button = new JButton("Cancel");
		
		notes_text = new JTextArea(10, 25);
		notes_text.setLineWrap(true);
		
		submit_button.setPreferredSize(new Dimension(200, 50));
		cancel_button.setPreferredSize(new Dimension(200, 50));
		
		banner_picture = new JLabel(new ImageIcon("src/addbanner.jpg"));
		customer_name_label = new JLabel("Customer Name: ", SwingConstants.CENTER);
		customer_name_label.setForeground(Color.WHITE);
		username_label = new JLabel("Customer Username: ", SwingConstants.CENTER);
		username_label.setForeground(Color.WHITE);
		order_number_label = new JLabel("Order Number: ", SwingConstants.CENTER);
		order_number_label.setForeground(Color.WHITE);
		ship_label = new JLabel("Ship Date: ", SwingConstants.CENTER);
		ship_label.setForeground(Color.WHITE);
		size_label = new JLabel("Chalkboard Size: ", SwingConstants.CENTER);
		size_label.setForeground(Color.WHITE);
		frame_label = new JLabel("Frame Type: ", SwingConstants.CENTER);
		frame_label.setForeground(Color.WHITE);
		stain_label = new JLabel("Stain Type: ", SwingConstants.CENTER);
		stain_label.setForeground(Color.WHITE);
		magnetic_label = new JLabel("Magneitc: ", SwingConstants.CENTER);
		magnetic_label.setForeground(Color.WHITE);
		hanging_label = new JLabel("Hanging Direction: ", SwingConstants.CENTER);
		hanging_label.setForeground(Color.WHITE);
		notes_label = new JLabel("Notes: ", SwingConstants.CENTER);
		notes_label.setForeground(Color.WHITE);
		
		name_textbox = new JTextField(25);
		username_textbox = new JTextField(25);
		number_textbox = new JTextField(25);
		date_month_textbox = new JTextField(7);
		date_day_textbox = new JTextField(7);
		date_year_textbox = new JTextField(7);
		width_textbox = new JTextField(11);
		height_textbox = new JTextField(11);
		
		String[] frame_types = {"Mitered", "Straight"};
		String[] magnetic_types = {"Magnetic", "Non-Magnetic"};
		String[] hanging_types = {"Vertical", "Horizontal", "Both", "Unknown"};
		String[] stain_types = {"Ebony", "Red Mahogany", "Dark Red Mahogany", 
								"Grey", "Distressed White"};

		stain_combo = new JComboBox<String>(stain_types);
		stain_combo.setPreferredSize(new Dimension(300, 30));
		frame_combo = new JComboBox<String>(frame_types);
		frame_combo.setPreferredSize(new Dimension(300, 30));
		hanging_combo = new JComboBox<String>(hanging_types);
		hanging_combo.setPreferredSize(new Dimension(300, 30));
		magnetic_combo = new JComboBox<String>(magnetic_types);
		magnetic_combo.setPreferredSize(new Dimension(300, 30));
		
		JPanel name_panel = new JPanel(new GridLayout(1, 2));
		JPanel username_panel = new JPanel(new GridLayout(1, 2));
		JPanel number_panel = new JPanel(new GridLayout(1, 2));
		JPanel date_panel = new JPanel(new GridLayout(1, 4));
		JPanel size_panel = new JPanel(new GridLayout(1, 2));
		JPanel stain_panel = new JPanel(new GridLayout(1, 2));
		JPanel frame_panel = new JPanel(new GridLayout(1, 2));
		JPanel magnetic_panel = new JPanel(new GridLayout(1, 2));
		JPanel hanging_panel = new JPanel(new GridLayout(1, 2));
		JPanel button_panel = new JPanel(new GridLayout(1, 2));
		JPanel notes_panel = new JPanel(new GridLayout(1, 2));
		JPanel enclosing_panel = new JPanel();
		
		JPanel size_box_panel = new JPanel(new GridLayout(1, 2));
		JPanel ship_box_panel = new JPanel(new GridLayout(1, 3));
		
		name_panel.add(customer_name_label);
		name_panel.add(name_textbox);
		name_panel.setBackground(Color.BLACK);
		
		username_panel.add(username_label);
		username_panel.add(username_textbox);
		username_panel.setBackground(Color.BLACK);
		
		number_panel.add(order_number_label);
		number_panel.add(number_textbox);
		number_panel.setBackground(Color.BLACK);
		
		date_panel.add(ship_label);
		ship_box_panel.add(date_month_textbox);
		ship_box_panel.add(date_day_textbox);
		ship_box_panel.add(date_year_textbox);
		ship_box_panel.setBackground(Color.BLACK);
		date_panel.add(ship_box_panel);
		date_panel.setBackground(Color.BLACK);
		
		size_panel.add(size_label);
		size_box_panel.add(width_textbox);
		size_box_panel.add(height_textbox);
		size_panel.add(size_box_panel);
		size_box_panel.setBackground(Color.BLACK);
		size_panel.setBackground(Color.BLACK);

		stain_panel.add(stain_label);
		stain_panel.add(stain_combo);
		stain_panel.setBackground(Color.BLACK);

		frame_panel.add(frame_label);
		frame_panel.add(frame_combo);
		frame_panel.setBackground(Color.BLACK);
		
		magnetic_panel.add(magnetic_label);
		magnetic_panel.add(magnetic_combo);
		magnetic_panel.setBackground(Color.BLACK);
		
		hanging_panel.add(hanging_label);
		hanging_panel.add(hanging_combo);
		hanging_panel.setBackground(Color.BLACK);

		button_panel.add(submit_button);
		button_panel.add(cancel_button);
		button_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		button_panel.setBackground(Color.BLACK);
		
		notes_panel.add(notes_label);
		notes_panel.add(notes_text);
		notes_panel.setBackground(Color.BLACK);

		enclosing_panel.add(name_panel);
		enclosing_panel.add(username_panel);
		enclosing_panel.add(number_panel);
		enclosing_panel.add(date_panel);
		enclosing_panel.add(size_panel);
		enclosing_panel.add(stain_panel);
		enclosing_panel.add(frame_panel);
		enclosing_panel.add(magnetic_panel);
		enclosing_panel.add(hanging_panel);
		enclosing_panel.add(notes_panel);
		enclosing_panel.add(button_panel);
		enclosing_panel.setBackground(Color.BLACK);
		enclosing_panel.setLayout(new FlowLayout());
		enclosing_panel.setPreferredSize(new Dimension(630, 550));
		
		this.add(banner_picture);
		this.add(enclosing_panel);
		this.setVisible(true);
	}
	
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/addbackground.jpg"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		super.paintComponent(g);
		g.drawImage(background_img, 0, 0, getWidth(), getHeight(), this);
	}
	
	protected void clearFields() {
		name_textbox.setText("");
		username_textbox.setText("");
		number_textbox.setText("");
		date_day_textbox.setText("");
		date_month_textbox.setText("");
		date_year_textbox.setText("");
		width_textbox.setText("");
		height_textbox.setText("");
		notes_text.setText("");
	}
	
	protected void displayAlert(String message) {
        AlertDialog alert_dialog = new AlertDialog(message);
        alert_dialog.setLocationRelativeTo(AddEditOrderDialog.this);
        alert_dialog.setSize(250, 100);
        alert_dialog.setVisible(true);
	}
	
	protected Order createOrder() {
		if(getFrameType() == null || getStainType() == null || 
				getHardwareType() == null || getChalkboardType() == null) {
			return null;
		}
		
		Chalkboard new_chalkboard = null;
		try {
			new_chalkboard = new Chalkboard(getFrameType(), getStainType(), getHardwareType(), 
											getChalkboardType(), getChalkboardWidth(), 
											getChalkboardHeight());
		}
		catch(Exception e) {
			return null;
		}
		Order new_order = new Order();
		new_order.addChalkboard(new_chalkboard);
		return new_order;
	}
	
	protected ShippingDate getDate() {
		int year = Integer.parseInt(date_year_textbox.getText());
		int month = Integer.parseInt(date_month_textbox.getText());
		int day = Integer.parseInt(date_day_textbox.getText());
		return new ShippingDate(month, day, year);
	}
	
	protected boolean checkValidInput() {
		if(name_textbox.getText().length() == 0 || number_textbox.getText().length() == 0 || 
				username_textbox.getText().length() == 0 || date_month_textbox.getText().length() == 0|| 
				date_day_textbox.getText().length() == 0 || date_year_textbox.getText().length() == 0 ||
				checkValidOrderNumber() == false) {
			return false;
		}
		return true;
	}
	
	protected boolean checkValidOrderNumber() {
		try {
			Integer.parseInt(number_textbox.getText());
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	protected int getChalkboardWidth() throws NumberFormatException{
		int return_width = 0;
		return_width = Integer.parseInt(width_textbox.getText());
		return return_width;
	}
	
	protected int getChalkboardHeight() throws NumberFormatException{
		int return_height= 0;
		return_height = Integer.parseInt(height_textbox.getText());
		return return_height;
	}
	
	protected Frame.Frame_Type getFrameType() {
		String frame_type_str = (String) frame_combo.getSelectedItem();
		if(frame_type_str.equals("Mitered")) {
			return Frame.Frame_Type.MITERED;
		}
		else if(frame_type_str.equals("Straight")){
			return Frame.Frame_Type.STRAIGHT;
		}
		else
		{
			return null;
		}
	}
	
	protected Frame.Stain_Type getStainType() {
		String stain_type_str = (String) stain_combo.getSelectedItem();
		if(stain_type_str.equals("Ebony")) {
			return Frame.Stain_Type.EBONY;
		}
		else if(stain_type_str.equals("Red Mahogany")) {
			return Frame.Stain_Type.RED_MAHOGANY;
		}
		else if(stain_type_str.equals("Grey")) {
			return Frame.Stain_Type.GREY;
		}
		else if(stain_type_str.equals("Dark Red Mahogany")) {
			return Frame.Stain_Type.DARK_RED_MAHOGANY;
		}
		else if(stain_type_str.equals("Distressed White")) {
			return Frame.Stain_Type.WHITE_PAINT;
		}
		else {
			return null;
		}
	}
	
	protected Chalkboard.Hanging_Type getHardwareType() {
		String hanging_type_str = (String) hanging_combo.getSelectedItem();
		if(hanging_type_str.equals("Vertical")) {
			return Chalkboard.Hanging_Type.VERTICAL;
		}
		else if(hanging_type_str.equals("Horizontal")) {
			return Chalkboard.Hanging_Type.HORIZONTAL;
		}
		else if(hanging_type_str.equals("Both")) {
			return Chalkboard.Hanging_Type.BOTH;
		}
		else if(hanging_type_str.equals("Unknown")) {
			return Chalkboard.Hanging_Type.UNKNOWN;
		}
		else {
			return null;
		}
	}
	
	protected BackingBoard.Chalkboard_Type getChalkboardType() {
		String chalkboard_type_str = (String) magnetic_combo.getSelectedItem();
		if(chalkboard_type_str.equals("Magnetic")) {
			return BackingBoard.Chalkboard_Type.MAGNETIC;
		}
		else if(chalkboard_type_str.equals("Non-Magnetic")) {
			return BackingBoard.Chalkboard_Type.NON_MAGNETIC;
		}
		else {
			return null;
		}
	}
}

