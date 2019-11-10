package event;

import java.util.EventListener;


public interface SlateControlsListener extends EventListener {
	
	/**
	 * Run a new project
	 */
	public void newProject(ProjectControlsEvent e);
	
	/**
	 * Open an existing project
	 */
	public void openProject(ProjectControlsEvent e);
	
	/**
	 * Save the current project
	 */
	public void saveProject(ProjectControlsEvent e);
	
	/**
	 * Save as the current project
	 */
	public void saveAsProject(ProjectControlsEvent e);
	
	/**
	 * Exit the project / Close the application
	 */
	public void exitProject(ProjectControlsEvent e);
	
	/**
	 * Print the project
	 */
	public void printProject(ProjectControlsEvent e);
	
	/**
	 * Open the properties of project
	 */
	public void propertiesProject(ProjectControlsEvent e);
	
	
	/**
	 * The Back-button is selected
	 */
	public void backSelected(EditionControlsEvent e);
	
	/**
	 * The Next-button is selected
	 */
	public void nextSelected(EditionControlsEvent e);
	
	/**
	 * The Refresh-button is selected
	 */
	public void refreshSelected(ProjectControlsEvent e);
	
	/**
	 * Set the pointer state (material or shape)
	 */
	public void setPointerState(EditionControlsEvent e);
	
	/**
	 * Modify the pointer size (small, medium, large)
	 */
	public void setPointerSize(EditionControlsEvent e);
	
	/**
	 * Modify the pointer color
	 */
	public void setPointerColor(EditionControlsEvent e);
	
	/**
	 * Modify the title of project
	 */
	public void setProjectTitle(ProjectControlsEvent e);
	
	/**
	 * Set the color by the color chooser
	 */
	public void setPointerColorByChooser(EditionControlsEvent e);

}
