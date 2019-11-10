package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import event.EditionControlsEvent;
import event.ProjectControlsEvent;
import event.SlateControlsListener;
import event.SlateEvents;
import pointer.PointerState;
import shape.Line;
import shape.Oval;
import shape.Point;
import shape.Rectangle;
import shape.Stamp;

public class Slate extends JFrame implements SlateControlsListener {

	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Magic Slate";
	
	private static final int WIDTH 	= 1240;
	private static final int HEIGHT = 720;
	
	private MenuBar menuBar 	= new MenuBar(this);
	private ToolBar toolBar 	= new ToolBar(this);
	private DrawingBoard board 	= new DrawingBoard(this);
	
	private File projectFile = null;
	
	public static void main(String[] args) {
		new Slate();
	}
	
	public Slate() {
		super(TITLE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		setJMenuBar(menuBar);
		add(toolBar, BorderLayout.NORTH);
		add(board, BorderLayout.CENTER);
		setVisible(true);	
		setIconImage(Toolkit.getDefaultToolkit().getImage("slate.png"));
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					exit();				
				}
		});
	}
	
	// ============================================================================ //
	
	
	private void exit() {
		if (getTitle().endsWith("*")) {
			
			String message = (projectFile == null) ? 
					"Do you want to save the modifications for \"untitled\" ?" : 
					"Do you want to save the modifications for \"" + projectFile.getName() + "\" ?" ;	
			String[] options = {"Enregistrer", "Ne pas enregistrer", "Annuler"};
			
			
			int choice = JOptionPane.showOptionDialog(null, message, getTitle(), JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			
			switch (choice){
				case JOptionPane.OK_OPTION : // Save
					saveProject(new ProjectControlsEvent(this));
					
				case JOptionPane.NO_OPTION : // Do not save
					System.exit(0);
					
				case JOptionPane.CANCEL_OPTION : // CANCEL
					break;
			}
		}
		else {
			System.exit(0);
		}
	}
	
	/**
	 * Update the buttons back and next
	 * @param count
	 */
	public void updateBackNext(int count) {
		boolean backEnabled = true, 
				nextEnabled = true;
		
		if (count == 0) {
			backEnabled = false;
			ProjectControlsEvent e = new ProjectControlsEvent(this);
			e.setProjectUpToDate(true);
			setProjectTitle(e);
		}
		else if (count == board.getHistory().getNumberSaves() - 1) {
			nextEnabled = false;
		}
		
		toolBar.getControlButton(SlateEvents.BACK).setEnabled(backEnabled);
		toolBar.getControlButton(SlateEvents.NEXT).setEnabled(nextEnabled);
		menuBar.getMenuItem(SlateEvents.BACK).setEnabled(backEnabled);
		menuBar.getMenuItem(SlateEvents.NEXT).setEnabled(nextEnabled);
	}

	/**
	 * Create a file project
	 * @param path of the file project
	 */
	private void createProjectFile(String path) {
		
		try {
			FileWriter writer = new FileWriter(path + (path.endsWith(".csv") ? "" : ".csv"));
			BufferedWriter buffer = new BufferedWriter(writer);
			
			for(Point p : board.getPoints()) {
				buffer.write(p.toString());
				buffer.newLine();
			}				

			buffer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Create the image file
	 * @param path of the output file
	 * @param extension of the output file
	 */
	private void createImageFile(String path, String extension) {
		
		try {
			// Dimension of the board = (1235, 565)
			//BufferedImage buffer = new BufferedImage(1235, 565, BufferedImage.TYPE_INT_RGB); 
			BufferedImage buffer = new BufferedImage(WIDTH, HEIGHT - toolBar.getHeight(), BufferedImage.TYPE_INT_RGB); 
			
			// Initialization : set every pixels white
			for (int y = 0 ; y < buffer.getHeight() ; y++) {
				for (int x = 0 ; x < buffer.getWidth() ; x++) {
					int rgb = Color.WHITE.getRGB();
					buffer.setRGB(x, y, rgb); 
				}
			}
			
			Graphics g = buffer.getGraphics();
			for (Point p : board.getPoints()) {
				g.setColor(p.getColor());
				p.draw(g);
			}
			
			File file = new File(path + (path.endsWith("." + extension) ? "" : "." + extension));
			ImageIO.write(buffer, extension, file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the project and read it
	 * @param path of the file project
	 * @throws  
	 * @throws IOException 
	 */
	private void loadProjectFile(String path) throws IOException, Exception {
			FileReader reader = new FileReader(path);
			BufferedReader buffer = new BufferedReader(reader);
			List<Point> list = new ArrayList<Point>();
			String line;

			while((line = buffer.readLine()) != null){
				String[] tab = line.split(";");
				list.add(buildPoints(tab));
			}
			
			buffer.close();
			board.setPoints(list);
			board.repaint();		
			projectFile = new File(path);
	}
	
	/**
	 * Points builder
	 * @param tab a table of String
	 * @return a point
	 */
	private Point buildPoints(String[] tab) throws Exception {	
		try {
			String type = tab[0];
			int x = Integer.parseInt(tab[1]);
			int y = Integer.parseInt(tab[2]);
			
			float red = Float.parseFloat(tab[3]);
			float green = Float.parseFloat(tab[4]);
			float blue = Float.parseFloat(tab[5]);
			float alpha = Float.parseFloat(tab[6]);
			Color color = new Color(red, green, blue, alpha);
			
			switch(type){
				case "OVAL" : 
					int diameter = Integer.parseInt(tab[7]);
					return new Oval(x, y, diameter, color);
					
				case "LINE" :
					int length = Integer.parseInt(tab[7]);
					int degree = Integer.parseInt(tab[8]);
					return new Line(x, y, length, degree, color);
					
				case "RECTANGLE" :
					int width = Integer.parseInt(tab[7]);
					int height = Integer.parseInt(tab[8]);
					return new Rectangle(x, y, width, height, color);
					
				case "STAMP" :
					int x1 = Integer.parseInt(tab[7]);
					int y1 = Integer.parseInt(tab[8]);
					int stroke = Integer.parseInt(tab[9]);
					String shape = tab[10];
					return new Stamp(x, y, x1, y1, stroke, shape, color);
					
				default : 
					throw new Exception();
			}
		} catch(Exception ex) {
			throw new Exception();
		}
	}
	
	/**
	 * Modify alpha value of a color
	 * @param color the color to modify
	 * @param alpha the value of alpha to set
	 */
	private Color setAlphaColor(Color color, float alpha) {
		float red = (float) color.getRed() / 255f;
		float green = (float) color.getGreen() / 255f;
		float blue = (float) color.getBlue() / 255f;
		Color alphaColor = new Color(red, green, blue, alpha);
		return alphaColor;
	}
	
	// ============================================================================ //

	
	@Override
	public void newProject(ProjectControlsEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("New Project");
		chooser.setCurrentDirectory(new File("."));
		chooser.changeToParentDirectory(); 
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filtrer = new FileNameExtensionFilter("Project (*.csv)", "csv");	
		chooser.addChoosableFileFilter(filtrer);
		chooser.setFileFilter(filtrer);
		
		int userSelection = chooser.showSaveDialog(null);
		
		if(userSelection == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				FileWriter writer = new FileWriter(file.getPath() + (file.getPath().endsWith(".csv") ? "" : ".csv"));
				BufferedWriter buffer = new BufferedWriter(writer);
				buffer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			projectFile = file;
			e.setProjectUpToDate(true);
			setProjectTitle(e);
			
			// When we start a new project, all that been drawn before are removed,
			// the history and counter are reset
			List<Point> list = board.getPoints();
			board.clearPoints();
			board.getHistory().clear();
			board.setActionCount(0);
			board.storePoints(list);
			updateBackNext(0);
		}
	}

	@Override
	public void openProject(ProjectControlsEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Open Project");
		chooser.setCurrentDirectory(new File("."));
		chooser.changeToParentDirectory(); 
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter[] filtrers = {new FileNameExtensionFilter("Project (*.csv)", "csv")};	
		for(int i = 0 ; i < filtrers.length ; i++){
			chooser.addChoosableFileFilter(filtrers[i]);
			chooser.setFileFilter(filtrers[i]);
		}
		
		int userSelection = chooser.showOpenDialog(this);	
		if(userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				loadProjectFile(chooser.getSelectedFile().getPath());
				e.setProjectUpToDate(true);
				setProjectTitle(e);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error when opening the file", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Unable to load the file (endommaged file)", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void saveProject(ProjectControlsEvent e) {
		// If there is no current save
		if(projectFile == null) {
					
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Save Project");
			chooser.setCurrentDirectory(new File("."));
			chooser.changeToParentDirectory(); 
			chooser.setAcceptAllFileFilterUsed(false);
					
			FileNameExtensionFilter[] filtrers = {
				new FileNameExtensionFilter("Project file (*.csv)", "csv"),
				new FileNameExtensionFilter("Image file (*.jpg)", "jpg"),	
				new FileNameExtensionFilter("Image file (*.png)", "png"),
				new FileNameExtensionFilter("Image file (*.bmp)", "bmp"),	
				new FileNameExtensionFilter("Image file (*.gif)", "gif")	
			};
					
			for(int i = 0 ; i < filtrers.length ; i++){
				chooser.addChoosableFileFilter(filtrers[i]);
				chooser.setFileFilter(filtrers[i]);
			}
			
			int userSelection = chooser.showSaveDialog(null);
					
			if(userSelection == JFileChooser.APPROVE_OPTION) {
				switch(((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions()[0]){
					case "csv" : 
						createProjectFile(chooser.getSelectedFile().getPath());
						projectFile = chooser.getSelectedFile(); // Update the frame title
						e.setProjectUpToDate(true);
						setProjectTitle(e);
						break;
					case "jpg" : createImageFile(chooser.getSelectedFile().getPath(), "jpg"); break;
					case "png" : createImageFile(chooser.getSelectedFile().getPath(), "png"); break;
					case "bmp" : createImageFile(chooser.getSelectedFile().getPath(), "bmp"); break;
					case "gif" : createImageFile(chooser.getSelectedFile().getPath(), "gif"); break;
					default : break;
				}
			}
		}
		else {
			// Update the frame title
			createProjectFile(projectFile.getPath());
			e.setProjectUpToDate(true);
			setProjectTitle(e);
		}
	}

	@Override
	public void saveAsProject(ProjectControlsEvent e) {
		projectFile = null;
		saveProject(e);
	}

	@Override
	public void exitProject(ProjectControlsEvent e) {
		exit();
	}
	
	@Override
	public void printProject(ProjectControlsEvent e) {
		
	}

	@Override
	public void propertiesProject(ProjectControlsEvent e) {
		
	}

	@Override
	public void backSelected(EditionControlsEvent e) {
		int count = board.getActionCount();
		board.setPoints(board.getHistory().getListPoints(count - 1));
		board.setActionCount(count - 1);
		board.repaint();
		updateBackNext(count - 1);
	}

	@Override
	public void nextSelected(EditionControlsEvent e) {
		int count = board.getActionCount();
		List<Point> list = new ArrayList<Point>();
		board.getHistory().getListPoints(count + 1).forEach(points -> list.add(points));
		board.setPoints(list);
		board.setActionCount(count + 1);
		board.repaint();
		updateBackNext(count + 1);
	}

	@Override
	public void refreshSelected(ProjectControlsEvent e) {
		List<Point> list = board.getPoints();
		if(!list.isEmpty()){
			int count = board.getActionCount() + 1;
			board.getHistory().update(count);
			board.setActionCount(count);
			board.clearPoints();
			board.storePoints(list);
			updateBackNext(count);
			e.setProjectUpToDate(false);
			setProjectTitle(e);
		}
	}

	@Override
	public void setPointerState(EditionControlsEvent e) {
		toolBar.getShapePallet().clear();
		toolBar.getSizeComboBox().reset(); // When we change the material, the stroke are reset
		board.setPointerState(e.getPointer().getState());
		// Control if the change of state require a change of alpha value for the color
		e.getPointer().setColor(board.getPointerColor());
		setPointerColor(e);
	}

	@Override
	public void setPointerSize(EditionControlsEvent e) {
		 board.setPointerSize(e.getPointer().getSize());
	}

	@Override
	public void setPointerColor(EditionControlsEvent e) {
		Color c = e.getPointer().getColor();
		// For the felt tip and highlighter, color with a specific alpha value is better
		if (board.getPointerState().equals(PointerState.FELT_TIP) 
				|| board.getPointerState().equals(PointerState.HIGHLIGHTER)) {
			c = setAlphaColor(c, 0.5f);
		}
		else {
			c = setAlphaColor(c, 1f);
		}
		board.setPointerColor(c);
	}

	@Override
	public void setProjectTitle(ProjectControlsEvent e) {
		if (e.isProjectUpToDate()) {
			if (projectFile == null)
				setTitle(TITLE + " - untitled");
			else
				setTitle(TITLE + " - " + projectFile.getPath()); 
		}
		else {
			if(projectFile == null)
				setTitle(TITLE + " - untitled *");
			else
				setTitle(TITLE + " - " + projectFile.getPath() + " *"); 
		}
	}

	@Override
	public void setPointerColorByChooser(EditionControlsEvent e) {
		board.setPointerColor(JColorChooser.showDialog(null, "Color selection", board.getPointerColor()));
		// Control if the current state require a change of alpha value for the selected color
		e.getPointer().setColor(board.getPointerColor());
		setPointerColor(e);
	}

}
