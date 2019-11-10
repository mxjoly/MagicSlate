package utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shape.Point;

public class History {
	
	private Map<Integer, Date> dates;
	private Map<Integer, List<Point>> saves;
	
	public History(){
		dates = new HashMap<Integer, Date>();
		saves = new HashMap<Integer, List<Point>>();
	}
	
	/**
	 * Clear the history
	 */
	public void clear() {
		dates.clear();
		saves.clear();
	}
	
	/**
	 * Add a state in the history
	 * @param index of chronological placement
	 * @param date the points creation date
	 * @param points a list
	 */
	public void put(int index, Date date, List<Point> points){
		dates.put(index, date);
		saves.put(index, points);
	}
	
	/**
	 * Remove a state
	 * @param index to remove
	 */
	public void delete(int index){
		dates.remove(index);
		saves.remove(index);
	}
	
	/**
	 * update the history in case of some actions "Undo"
	 * @param index the current index
	 */
	public void update(int index){
		int n = getNumberSaves();		
		if (index < n - 1){
			for (int i = index + 1 ; i <= n ; i++){
				delete(i);
			}
		}
	}
	
	/**
	 * @param index
	 * @return the date at the specified index
	 */
	public Date getDate(int index){
		return dates.get(index);
	}
	
	/**
	 * @param index
	 * @return the list of points at the specified index
	 */
	public List<Point> getListPoints(int index){
		return saves.get(index);
	}
	
	/**
	 * @return the numbers of saves done
	 */
	public int getNumberSaves(){
		return dates.size();
	}

}
