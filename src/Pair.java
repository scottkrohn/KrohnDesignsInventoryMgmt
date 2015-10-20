
public class Pair<T> {
	private T value_one;
	private T value_two;
	
	public Pair(T one, T two) {
		value_one = one;
		value_two = two;
	}
	
	public T getValueOne() {
		return value_one;
	}
	
	public T getValueTwo() {
		return value_two;
	}
	
	public void setValueOne(T value) {
		value_one = value;
	}
	
	public void setValueTwo(T value) {
		value_two = value;
	}
}
