package utils;

import java.awt.Color;

/**
 * Generates 64 different colors
 */
public class ColorsGenerator {

	/**
	 * Generate 64 colors and return them in a table
	 * @return a table of colors
	 */
	public static Color[] getColors(){
		
		int i = 0;
		Color[] colors = new Color[64];
		
		for (int red = 0 ; red <= 15 ; red += 5) {
			for (int green = 0 ; green <= 15 ; green += 5) {
				for (int blue = 0 ; blue <= 15 ; blue += 5) {
					
					// Hexadecimal code
					String hexRed = Integer.toString(red, 16) + Integer.toString(red, 16);
					String hexGreen = Integer.toString(green, 16) + Integer.toString(green, 16);
					String hexBlue = Integer.toString(blue, 16) + Integer.toString(blue, 16);
					
					// Conversion to decimal
					int decRed = Integer.valueOf(hexRed, 16);  
					int decGreen = Integer.valueOf(hexGreen, 16);  
					int decBlue = Integer.valueOf(hexBlue, 16); 
					
					// Conversion to float (value between 0 and 1)
					float floatRed = (float) decRed / 255f;
					float floatGreen = (float) decGreen / 255f;
					float floatBlue = (float) decBlue / 255f;
					
					colors[i++] = new Color(floatRed, floatGreen, floatBlue, 1f);
				}
			}
		}		
		return colors;
	}
	
}
