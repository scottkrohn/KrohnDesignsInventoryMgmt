import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class AlertDialog extends JFrame{
	JButton ok_button;
	JLabel message_label;
	
	public AlertDialog(String message) {
		this.setLayout(new FlowLayout());
		ok_button = new JButton("OK");
		ok_button.setPreferredSize(new Dimension(100, 40));
		message_label = new JLabel(message, SwingConstants.CENTER);
		
		ok_button.addActionListener(new ButtonListener());
		
		this.add(message_label);
		this.add(ok_button);
		this.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(ok_button)) {
				AlertDialog.this.setVisible(false);
			}
		}
	}
}
