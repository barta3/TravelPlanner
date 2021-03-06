package model.entity;

import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import model.config.StreetType;

import common.Constants;

@Entity
public class Street {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Node start;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Node end;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private StreetType streetType;
	
	@Transient
	private boolean isClosed = false;
	
	private boolean isOneWay = false;
	
	private boolean isNoPassing = false;
	
	@Transient
	private Set<Vehicle> vehicles = new HashSet<Vehicle>();
	
	public Street() {}
	
	public Street(Node start) {
		this.start = start;
	}
	
	public Street(Node start, Node end) {
		this.start = start;
		this.end = end;
	}
	
	public Node getStart() {
		return start;
	}
	public void setStart(Node start) {
		this.start = start;
	}
	public Node getEnd() {
		return end;
	}
	public void setEnd(Node end) {
		this.end = end;
	}
	
	public StreetType getStreetType() {
		return streetType;
	}
	
	public void setStreetType(StreetType streetType) {
		this.streetType = streetType;
	}
	
	
	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	/**
	 * Calculate length of street based on start, end and heigt
	 * 
	 * @return Lenth of street in km
	 */
	public int getLenth() {
		
		int a = start.getX() - end.getX();
		int b = start.getY() - end.getY();
		
		int c = end.getHeight() - start.getHeight();
		
		return (int) Math.sqrt(a*a + b*b + c*c);
	}
	
	/**
	 * Checks if Point is on street
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @return true, if selected Point is on the Street, otherwise false 
	 */
	public boolean isPointOnStreet(int x, int y) {
		double distance = Line2D.ptSegDist(start.getX(), start.getY(), end.getX(), end.getY(), x, y);
		distance = Math.abs(distance);
		
		return distance <= Constants.CLICK_TOLERANCE_STREET;
		
	}
	
	/**
	 * Calulate incline based on start and end node (45° is 100% -> return 1.0)
	 * 
	 * @return negative if descending, 0 if flat, positive if ascending
	 */
	public double getIncline() {
		return getHeightDiff() / (double) getFlatLength();
	}
	
	/**
	 * Calculate vertical difference from start to end
	 * 
	 * @return vertival difference
	 */
	public int getHeightDiff() {
		return this.end.getHeight() - this.start.getHeight();
	}
	
	/**
	 * Calculate lenth without height (view from top)
	 * 
	 * @return length
	 */
	public int getFlatLength() {
			
			int a = start.getX() - end.getX();
			int b = start.getY() - end.getY();
			
			
			return (int) Math.sqrt(a*a + b*b);
	}
	
	public Node getMiddle() {
		int x = (start.getX() + end.getX()) / 2;
		int y = (start.getY() + end.getY()) / 2;
		
		Node middle = new Node(x, y);
		
		return middle;
	}
	
	/**
	 * Calculate for Solver, avoid negative weights on Graph
	 * inline -0.2 -> 1/1.2
	 * 		   0.2 -> 1.2
	 * @return
	 */
	public double getInclineFactor() {
		
		double incline = this.getIncline();
		
		if(incline < 0){
			incline = 1 / (-1 * incline +1);
		}else {
			incline += 1;
		}
		
		return incline;
	}
	
	public boolean isOneWay() {
		return isOneWay;
	}

	public void setOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay;
	}
	
	public Node getAtEnd() {
		float beta = 30 / (float)this.getFlatLength();
		
		int x = (int) (end.getX() + beta * (start.getX() - end.getX()));
		int y = (int) (end.getY() + beta * (start.getY() - end.getY()));
		
		
		return new Node(x, y);
	}

	public boolean isNoPassing() {
		return isNoPassing;
	}

	public void setNoPassing(boolean isNoPassing) {
		this.isNoPassing = isNoPassing;
	}

	public Set<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
