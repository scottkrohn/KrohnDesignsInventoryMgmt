import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainDisplay extends JPanel {
	
	final static String ORDER_LIST_FILENAME = "master_order_list.obj";
	final static String COMPLETED_LIST_FILENAME = "completed_order_list.obj";
	
	private JLabel banner_picture;
	private JButton view_button;
	private JButton complete_button;
	private JButton search_button;
	private JButton add_button;
	private JButton update_button;
	private JButton quit_button;
	private BufferedImage background_img;
	
	OrderList master_order_list;
	OrderList completed_order_list;	
	
	public MainDisplay() {
		ImageIcon banner_image = new ImageIcon("src/banner.jpg");
		banner_picture = new JLabel(banner_image);
		
		master_order_list = loadOrders(ORDER_LIST_FILENAME);
		completed_order_list = loadOrders(COMPLETED_LIST_FILENAME);
		
		view_button = new JButton("View/Edit Orders");
		complete_button = new JButton("View Completed Orders");
		search_button = new JButton("Search All Orders");
		add_button = new JButton("Add New Order");
		update_button = new JButton("Update Order");

		quit_button = new JButton("Quit");
		
		quit_button.addActionListener(new ButtonListener());
		view_button.addActionListener(new ButtonListener());
		add_button.addActionListener(new ButtonListener());
		complete_button.addActionListener(new ButtonListener());
		update_button.addActionListener(new ButtonListener());
		search_button.addActionListener(new ButtonListener());
		
		this.setLayout(new GridLayout(6, 1));
			
		this.add(banner_picture);
		this.add(add_button);
		this.add(view_button);
		this.add(complete_button);
		this.add(search_button);
		this.add(quit_button);
		this.setVisible(true);
	}
	
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/background.jpg"));
		}
		catch(IOException e) {
			e.printStackTrace();;
		}
		
		super.paintComponent(g);
		g.drawImage(background_img, 0, 0, getWidth(), getHeight(), this);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(quit_button)) {
				JFrame top_frame = (JFrame) SwingUtilities.getWindowAncestor(MainDisplay.this);
				top_frame.setVisible(false);
				System.exit(0);
			}
			else if(e.getSource().equals(view_button)) {
				JFrame frame = new JFrame();
				frame.setSize(900,800);
				frame.add(new ViewOrdersDialog(master_order_list, completed_order_list));
				frame.setLocationRelativeTo(MainDisplay.this);
				frame.setVisible(true);
			}
			else if(e.getSource().equals(complete_button)) {
				JFrame frame = new JFrame();
				frame.setSize(900, 800);
				frame.add(new ViewCompletedOrders(master_order_list, completed_order_list));
				frame.setLocationRelativeTo(MainDisplay.this);
				frame.setVisible(true);
			}
			else if(e.getSource().equals(add_button)) {
				JFrame frame = new JFrame();
				frame.setSize(700,800);
				frame.add(new AddOrderDialog(master_order_list));
				frame.setLocationRelativeTo(MainDisplay.this);
				frame.setVisible(true);
			}
			else if(e.getSource().equals(search_button)) {
				JFrame frame = new JFrame();
				frame.setSize(700,800);
				frame.add(new SearchDialog(master_order_list, completed_order_list));
				frame.setLocationRelativeTo(MainDisplay.this);
				frame.setVisible(true);
			}
		}
	}
	
	private OrderList loadOrders(String filename) {
		OrderList order_list = new OrderList();
		order_list.load(filename);
		return order_list;
	}
	
}
