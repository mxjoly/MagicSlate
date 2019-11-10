package event;

import java.awt.Event;

public class ProjectControlsEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	// If the current drawing are saved in a project
	private Boolean projectUpToDate;
	
	public ProjectControlsEvent(Object source) {
		super(source, 0, null);
	}
	
	public Boolean isProjectUpToDate() {
		return projectUpToDate;
	}

	public void setProjectUpToDate(Boolean projectUpToDate) {
		this.projectUpToDate = projectUpToDate;
	}
}
