import java.io.Serializable;


public class BackingBoard implements Serializable{
	
	public static enum Chalkboard_Type {MAGNETIC, NON_MAGNETIC};
	
	private boolean is_vinyled;
	private boolean is_cut;
	private boolean is_metaled;
	private boolean is_magnetic;
	private boolean is_complete;
	
	public BackingBoard(Chalkboard_Type chalkboard_type) {
		is_vinyled = false;
		is_cut = false;
		is_metaled = false;
		is_complete = false;
		
		if(chalkboard_type == Chalkboard_Type.MAGNETIC) {
			is_magnetic = true;
		}
		else {
			is_magnetic = false;
		}
	}
	
	public boolean isComplete() {
		return is_complete;
	}
	
	public boolean isVinyled() {
		return is_vinyled;
	}
	
	public boolean isCut() {
		return is_cut;
	}
	
	public boolean isMetaled() {
		return is_metaled;
	}
	
	public boolean isMagnetic() {
		return is_magnetic;
	}
	
	public boolean addVinyl() {
		if(isVinyled()) {
			return false;
		}
		is_vinyled = true;
		checkIfComplete();
		return true;
	}
	
	public boolean addMetal() {
		if(!is_magnetic || isMetaled()) {
			return false;
		}
		is_metaled = true;
		checkIfComplete();
		return true;
	}
	
	public boolean addCut() {
		if(isCut()) {
			return false;
		}
		is_cut = true;
		checkIfComplete();
		return true;
	}
	
	private boolean checkIfComplete() {
		if(is_cut == true && is_vinyled == true) {
			if(is_magnetic == true) {
				if(is_metaled == true) {
					is_complete = true;
					return true;
				}
			}
			else {
				is_complete = true;
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String vinyled;
		if(is_vinyled == true) {
			vinyled = "DONE"; 
		}
		else {
			vinyled = "NOT DONE";
		}
		
		String cut;
		if(is_cut == true) {
			cut = "DONE";
		}
		else {
			cut = "NOT DONE";
		}
		
		String metaled;
		if(is_metaled == true && is_magnetic == true) {
			metaled = "DONE";
		}
		else if(is_magnetic == false) {
			metaled = "N/A";
		}
		else {
			metaled = "NOT DONE";
		}
		
		String complete;
		if(is_complete == true) {
			complete = "COMPLETE";
		}
		else {
			complete = "NOT COMPLETE";
		}
		
		String return_string = "Vinyl: " + vinyled + "\n" +
 							   "Cut: " + cut + "\n" +
							   "Metal: " + metaled + "\n" +
							   "Backing Board Complete: " + complete;
		return return_string;
	}

}
