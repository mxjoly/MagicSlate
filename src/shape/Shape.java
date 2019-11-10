package shape;

public enum Shape {
	OVAL 		("OVAL"),
	RECTANGLE	("RECTANGLE"),
	TRIANGLE	("TRIANGLE"),
	DIAMOND		("DIAMOND"),
	ARROW_RIGHT	("ARROW_RIGHT"),
	ARROW_UP	("ARROW_UP"),
	STAR		("STAR"),
	HEART		("HEART");
	
	private final String id;
	
	Shape (String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
