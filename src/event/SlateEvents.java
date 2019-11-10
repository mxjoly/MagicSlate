package event;

public enum SlateEvents {
	NEW				("NEW", 		"New"), 
	OPEN 			("OPEN", 		"Open"), 
	SAVE 			("SAVE", 		"Save"),
	SAVE_AS 		("SAVE_AS", 	"Save as"), 
	PRINT 			("PRINT", 		"Print"), 
	BACK 			("BACK", 		"Back"), 
	NEXT 			("NEXT", 		"Next"), 
	REFRESH 		("REFRESH", 	"Refresh"),
	PROPERTIES		("PROPERTIES", 	"Properties"),
	EXIT			("EXIT", 		"Exit"),
	COLOR_SELECTION	("COLOR", 		"Color selection");
	
	// Id
	private final String id;
	// Description
	private final String desc;
	
	SlateEvents(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public String getId() {
		return id;
	}
	
	public String getDesc() {
		return desc;
	}
}
