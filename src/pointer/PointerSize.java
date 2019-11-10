package pointer;

public enum PointerSize {
	LIGHT 	(1, "LIGHT", "Light"), 
	MEDIUM 	(2, "MEDIUM", "Medium"), 
	LARGE 	(3, "LARGE", "Large");
	
	// Enlargement ratio
	private final int ratio;
	// Id
	private final String id;
	// Description
	private final String desc;
	
	PointerSize(int ratio, String id, String desc) {
		this.ratio = ratio;
		this.id = id;
		this.desc = desc;
	}

	public int getRatio() {
		return ratio;
	}

	public String getId() {
		return id;
	}
	
	public String getDesc() {
		return desc;
	}
}
