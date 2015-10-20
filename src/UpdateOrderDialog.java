import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UpdateOrderDialog extends JPanel {
	private JLabel banner_picture;
	private BufferedImage background_img;

	public UpdateOrderDialog(OrderList master_order_list, Order current_order) {
		banner_picture = new JLabel(new ImageIcon("src/viewupdatebanner.jpg"));
		
		this.add(banner_picture);
	}
	
	protected void paintComponent(Graphics g) {
		try {
			background_img = ImageIO.read(new File("src/viewupdatebackground.jpg"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		super.paintComponent(g);
		g.drawImage(background_img, 0, 0, getWidth(), getHeight(), this);
	}
}
