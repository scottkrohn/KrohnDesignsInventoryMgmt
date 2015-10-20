import java.io.Serializable;


public class Frame implements Serializable{
	
	final static int STAIN_COATS = 1;
	final static int SEALER_COATS = 2;

	public static enum Frame_Type {MITERED, STRAIGHT};
	public static enum Stain_Type {EBONY, RED_MAHOGANY, DARK_RED_MAHOGANY, GREY, WHITE_PAINT};
	
	private Frame_Type frame_type;
	private Stain_Type stain_type;
	
	private int stain_coats;
	private int sealer_coats;
	
	private boolean is_complete;
	private boolean is_stained;
	private boolean is_sealed;
	
	public Frame(Frame_Type frame_type, Stain_Type stain_type) {
		this.frame_type = frame_type;
		this.stain_type = stain_type;
		stain_coats = 0;
		sealer_coats = 0;
		is_complete = false;
		is_sealed = false;
		is_sealed = false;
	}
	
	public boolean isStained() {
		return is_stained;
	}
	
	public boolean isSealed() {
		return is_sealed;
	}
	
	public boolean isComplete() {
		return is_complete;
	}
	
	public Stain_Type getStainType() {
		return stain_type;
	}
	
	public Frame_Type getFrameType() {
		return frame_type;
	}
	
	public String getFrameTypeString() {
		if(frame_type == Frame_Type.MITERED) {
			return "Mitered";
		}
		else {
			return "Straight";
		}
	}
	
	public String getStainTypeString() {
		if(stain_type == Stain_Type.DARK_RED_MAHOGANY) {
			return "Dark Red Mahogany";
		}
		else if(stain_type == Stain_Type.EBONY) {
			return "Ebony";
		}
		else if(stain_type == Stain_Type.GREY) {
			return "Grey";
		}
		else if(stain_type == Stain_Type.RED_MAHOGANY) {
			return "Red Mahogany";
		}
		else {
			return "Distressed White";
		}
	}
	
	public int getStainCoats() {
		return stain_coats;
	}
	
	public int getSealerCoats() {
		return sealer_coats;
	}
	
	public boolean addStain() {
		// If the frame already has STAIN_COATS or more stains, return false and don't increment stain_coats.
		if(stain_coats >= STAIN_COATS) {
			return false;
		}
		
		// If this coat of stain completes the staining, set boolean is_stained to true.
		if(++stain_coats == STAIN_COATS) {
			is_stained = true;
			is_complete = checkIfComplete();
		}
		// Returns true if adding stain was successful.
		return true;
	}
	
	public boolean addSealer() {
		if(sealer_coats >= SEALER_COATS) {
			return false;
		}
		
		if(++sealer_coats == SEALER_COATS) {
			is_sealed = true;
			is_complete = checkIfComplete();
		}
		return true;
	}
	
	private boolean checkIfComplete() {
		if(is_sealed == true && is_stained == true) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String sealed;
		if(is_sealed == true) {
			sealed = "DONE";
		}
		else {
			sealed = "NOT DONE";
		}
		
		String stained;
		if(is_stained == true) {
			stained = "DONE";
		}
		else {
			stained = "NOT DONE";
		}
		
		String complete;
		if(is_complete == true) {
			complete = "COMPLETE";
		}
		else {
			complete = "NOT COMPLETE";
		}
		
		String return_string = "Stain: " + stained + " (" + stain_coats + " coats)\n" +
							   "Sealer: " + sealed + " (" + sealer_coats + " coats)\n" +
							   "Frame Complete: " + complete;
		return return_string;
	}
}
