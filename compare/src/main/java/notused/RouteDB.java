package notused;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class realizes the entity class for
 * the hibernate database access.
 * 
 * @author Manuel Kapferer
 *
 */

@Entity
@Table(name = "ROUTE")
public class RouteDB {
	
	@EmbeddedId
	private RoutePK id;
	
	//@Column(name="CO_2")
	private double CO2;		
	
	//@Column(name="COSTS")
	private double costs;
	
	//@Column(name="DURATION")
	private int duration;	
	
	//@Column(name="LENGTH")
	private int length;	
	
	//@Column(name="DRIVE_TYPE")
	private String type;	
	
	//@Column(name="CAR_CO_2")
	private double referenceco2;		
	
	//@Column(name="CAR_COSTS")
	private double referencecosts;
	
	//@Column(name="CAR_LENGTH")
	private int referencelength;
		
	/**
	 * empty constructor for hibernate
	 */
	public RouteDB() {
	}

	/**
	 * Constructor to initialize all parameters
	 * @param id				primary key of the entity
	 * @param CO2				co2 emission for the chosen route
	 * @param costs				costs for the chosen route
	 * @param duration			time for the chosen route
	 * @param length			length of the chosen route
	 * @param type				type of vehicle 
	 * @param referenceco2		co2 emission of a car for the chosen route
	 * @param referencecosts	costs of a car for the chosen route
	 * @param referencelength 	length of a car for the chosen route
	 */
	public RouteDB(RoutePK id, double CO2, double costs, int duration, int length, String type, double referenceco2, double referencecosts, int referencelength) {
		super();
		this.id = id;
		this.CO2 = CO2;
		this.costs = costs;
		this.duration = duration;
		this.length = length;
		this.referenceco2 = referenceco2;
		this.referencecosts = referencecosts;
		this.referencelength = referencelength;
		this.type = type;
	}
		
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getCO2() {
		return CO2;
	}


	public void setCO2(double cO2) {
		CO2 = cO2;
	}


	public double getReferenceco2() {
		return referenceco2;
	}


	public void setReferenceco2(double referenceco2) {
		this.referenceco2 = referenceco2;
	}


	public double getReferencecosts() {
		return referencecosts;
	}


	public void setReferencecosts(double referencecosts) {
		this.referencecosts = referencecosts;
	}


	public int getReferencelength() {
		return referencelength;
	}


	public void setReferencelength(int referencelength) {
		this.referencelength = referencelength;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public RoutePK getId() {
		return id;
	}


	public void setId(RoutePK id) {
		this.id = id;
	}
	

	public double getCosts() {
		return costs;
	}


	public void setCosts(double costs) {
		this.costs = costs;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	@Override
	public String toString() {
		return "RouteDB [id=" + id + ", CO2=" + CO2 + ", costs=" + costs
				+ ", duration=" + duration + ", length=" + length + ", type="
				+ type + ", referenceco2=" + referenceco2 + ", referencecosts="
				+ referencecosts + ", referencelength=" + referencelength + "]";
	}

}
