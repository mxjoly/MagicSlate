package pointer;

public enum PointerState {
	
	// Painting material
	BRUSH 			(10, "BRUSH"),
	PENCIL 			(3, "PENCIL"),
	FOUNTAIN_PEN 	(5, "FOUNTAIN_PEN"), 
	FELT_TIP 		(8, "FELT_TIP"), 
	HIGHLIGHTER 	(15, "HIGHLIGHTER"), 
	BIG_BRUSH 		(15, "BIG_BRUSH"), 
	ERASER 			(15, "ERASER"),
	
	// Tools material
	PIPETTE 		(1, "PIPETTE"),
	
	// Stamp shape
	OVAL 			(1, "OVAL"),
	RECTANGLE 		(1, "RECTANGLE"),
	TRIANGLE 		(1, "TRIANGLE"),
	DIAMOND 		(1, "DIAMOND"),
	ARROW_RIGHT 	(1, "ARROW_RIGHT"),
	ARROW_UP 		(1, "ARROW_UP"),
	STAR 			(1, "STAR"),
	HEART 			(1, "HEART");
	
	private final int size;
	private final String id;
	
	PointerState(int size, String id) {
		this.size = size;
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public String getId() {
		return id;
	}
}
