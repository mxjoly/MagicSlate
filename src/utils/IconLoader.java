package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Tool that allows to load every PNG images in a specified folder
 * => Used to load the image files of the application
 */
public class IconLoader {
	
	private Map<String, Icon> icons = new HashMap<String, Icon>();
	private Map<String, Image> images = new HashMap<String, Image>();
	
	private final File folder; 
	
	public IconLoader(int width, int height, String type){
		
		this.folder = new File("../res/" + type);
		
		// List all files having an extension
		File[] files = folder.listFiles(new FilenameFilter() {
		    public boolean accept(File folder, String fileName) {
		        return fileName.endsWith(".png");
		    }
		});
		
		
		// Add each images of the folder in a collection
		for (File file : files) {
			Image img = Toolkit.getDefaultToolkit().getImage(file.getPath()).getScaledInstance(width, height, 1);
			String name = file.getName().substring(0, file.getName().indexOf('.')); // Filename without extension
			icons.put(name.toUpperCase(), new ImageIcon(img));
			images.put(name.toUpperCase(), img);
		}
	}
	
	/**
	 * Return an icon
	 * @param name the name of icon desired (name = filename to upper case)
	 * @return a icon
	 */
	public Icon getIcon(String name){
		return icons.get(name);
	}
	
	/**
	 * Return all icons in a table
	 * @return a table of icon
	 */
	public Icon[] getIcons(){
		Icon[] tab = new Icon[icons.size()];
		int i = 0;
		for (Entry<String, Icon> entry : icons.entrySet()) {
			tab[i++] = entry.getValue();
		}
		return tab;
	}
	
	/**
	 * Return an image
	 * @param name the name of image desired (name = filename to upper case)
	 * @return une image
	 */
	public Image getImage(String name){
		return images.get(name);
	}
	
	/**
	 * Return all images in a table
	 * @return a table of image
	 */
	public Image[] getImages(){
		Image[] tab = new Image[images.size()];
		int i = 0;
		for (Entry<String, Image> entry : images.entrySet()) {
			tab[i++] = entry.getValue();
		}
		return tab;
	}
	
	/**
	 * Return a resized image
	 * @param name the name of image desired (name = filename to upper case)
	 * @param width the width desired
	 * @param height the height desired
	 * @return an resized image
	 */
	public Image getScaledImage(String name, int width, int height){
		return images.get(name).getScaledInstance(width, height, 0);
	}

}
