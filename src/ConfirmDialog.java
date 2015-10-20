import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridLayout;

/*
 * This class displays a small dialog that shows a message to the user,
 * then gives them the option to select a confirm or deny button. The text
 * of the message and buttons are supplied as arguments upon construction.
 */

public class ConfirmDialog extends JDialog{
	private JButton confirm_button;
	private JButton deny_button;
	private JLabel text_label;
	private Confirm_Status status;
	
	public static enum Confirm_Status {CONFIRM, DENY}; 

	// Constructor 1 - User supplies initial text label arguments.
	public ConfirmDialog(String label, String confirm_text, String deny_text) {
		text_label = new JLabel(label, SwingConstants.CENTER);
		confirm_button = new JButton(confirm_text);
		deny_button = new JButton(deny_text);
		setupDialog();
	}
	
	// Constructor 2 - User doesn't supply initial text label arguments.
	public ConfirmDialog() {
		text_label = new JLabel("", SwingConstants.CENTER);
		confirm_button = new JButton("");
		deny_button = new JButton("");
		setupDialog();
	}
	
	private void setupDialog() {
		
		JPanel button_panel = new JPanel();

		button_panel.add(confirm_button);
		button_panel.add(deny_button);
		
		confirm_button.addActionListener(new ButtonListener());
		deny_button.addActionListener(new ButtonListener());
		
		this.setTitle("Confirmation Message");
		this.setLayout(new GridLayout(2, 1));
		this.setSize(new Dimension(400, 200));
		this.add(text_label);
		this.add(button_panel);
	}
	
	// Returns the current status value
	public Confirm_Status getValue() {
		return status;
	}
		
	// Updates the text_label and button labels for the object
	public void updateMessages(String label, String confirm_text, String deny_text) {
		text_label.setText(label);
		text_label.setAlignmentX(SwingConstants.CENTER);
		confirm_button.setText(confirm_text);
		deny_button.setText(deny_text);
	}
	
	/*
	 * The ButtonListener private class is used for events originating from
	 * the ConfirmDialog's two buttons.
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(confirm_button)) {
				status = Confirm_Status.CONFIRM;
				ConfirmDialog.this.setVisible(false);
			}
			else if(e.getSource().equals(deny_button)) {
				status = Confirm_Status.DENY;
				ConfirmDialog.this.setVisible(false);
			}
		}
	}
}
