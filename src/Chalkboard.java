import java.io.Serializable;


public class Chalkboard implements Serializable{
	
	public static enum Hanging_Type {VERTICAL, HORIZONTAL, BOTH, UNKNOWN};
	
	private Frame frame;
	private BackingBoard backing_board;
	private int chalkboard_width;
	private int chalkboard_height;
	
	private boolean is_complete;
	private Chalkboard.Hanging_Type hanging_type;
	
	public Chalkboard(Frame.Frame_Type type, Frame.Stain_Type stain, 
			Chalkboard.Hanging_Type hanging_type, BackingBoard.Chalkboard_Type chalkboard_type,
			int width, int height) {
		frame = new Frame(type, stain);
		backing_board = new BackingBoard(chalkboard_type);
		this.hanging_type = hanging_type;
		chalkboard_width = width;
		chalkboard_height = height;
		is_complete = false;
	}
	
	public boolean sizeEquals(int height, int width) {
		if(height == chalkboard_height && width == chalkboard_width) {
			return true;
		}
		else if(height == chalkboard_width && width == chalkboard_height) {
			return true;
		}
		return false;
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public BackingBoard getBackingBoard() {
		return backing_board;
	}
	
	public int getChalkboardWidth() {
		return chalkboard_width;
	}
	
	public void setChalkboardWidth(int width) {
		chalkboard_width = width;
	}
	
	public int getChalkboardHeight() {
		return chalkboard_height;
	}
	
	public void setChalkboardHeight(int height) {
		chalkboard_height = height;
	}
	
	public boolean frameComplete() {
		return frame.isComplete();
	}
	
	public boolean backingBoardComplete() {
		return backing_board.isComplete();
	}
	
	public boolean isComplete() {
		return is_complete;
	}
	
	public Chalkboard.Hanging_Type getHangingType() {
		return hanging_type;
	}

	public boolean addStain() {
		boolean result = frame.addStain();
		this.checkIfCompelete();
		return result;
	}
	
	public boolean addSealer() {
		boolean result = frame.addSealer();
		this.checkIfCompelete();
		return result;
	}

	public boolean addVinyl() {
		boolean result = backing_board.addVinyl();
		this.checkIfCompelete();
		return result;
	}
	
	public boolean addMetal() {
		boolean result = backing_board.addMetal();
		this.checkIfCompelete();
		return result;
	}
	
	public boolean addCut() {
		boolean result = backing_board.addCut();
		this.checkIfCompelete();
		return result;
	}
	
	public void setHangingType(Chalkboard.Hanging_Type hanging_type) {
		this.hanging_type = hanging_type;
	}
	
	public boolean isVinyled() {
		return backing_board.isVinyled();
	}
	
	public boolean isMetaled() {
		return backing_board.isMetaled();
	}
	
	public boolean isCut() {
		return backing_board.isCut();
	}
	
	public boolean isMagnetic() {
		return backing_board.isMagnetic();
	}
	
	public boolean isSealed() {
		return frame.isSealed();
	}
	
	public boolean isStained() {
		return frame.isStained();
	}
	
	private boolean checkIfCompelete() {
		if(frame.isComplete() == true && backing_board.isComplete() == true) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String chalkboard_size = "Chalkboard Size: " + chalkboard_width + " x " + chalkboard_height + "\n";
		String return_string = chalkboard_size + frame.toString() + "\n" + backing_board.toString();
		String complete;
		if(checkIfCompelete() == true) {
			complete = "COMPLETE";
		}
		else {
			complete = "NOT COMPLETE";
		}
		return_string += " \nENTIRE CHALKBOARD: " + complete;
		return return_string;
	}
}
