package at.compare.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import flexjson.JSONSerializer;


@Entity
@Table(name = "auto")
public class Auto {
	//http://download.oracle.com/otndocs/jcp/persistence-2.0-fr-oth-JSpec/
	
	 @Id
	 @GeneratedValue
	 private Long id;
	 
	 private String model;
	 
	 private Double co2verbrauch;
	 
	 private String treibstoff;
	 
	
	 public Auto(){
		 
	 }
	 /*
	  * CREATE TABLE auto (
     id MEDIUMINT NOT NULL AUTO_INCREMENT,
     model VARCHAR(60) NOT NULL,
     co2verbrauch FLOAT(30) NOT NULL,
     treibstoff VARCHAR(30) NOT NULL,
     PRIMARY KEY (id)
		)ENGINE=InnoDB DEFAULT CHARSET=utf8
	*/
	 
	

	public Auto(String model, Double co2verbrauch, String treibstoff) {
		super();
		this.model = model;
		this.co2verbrauch = co2verbrauch;
		this.treibstoff = treibstoff;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Double getCo2verbrauch() {
		return co2verbrauch;
	}

	public void setCo2verbrauch(Double co2verbrauch) {
		this.co2verbrauch = co2verbrauch;
	}

	public String getTreibstoff() {
		return treibstoff;
	}

	public void setTreibstoff(String treibstoff) {
		this.treibstoff = treibstoff;
	}

	@Override
	public String toString() {
			
			
			return "Auto [id:" + id + ", model:" + model + ", co2verbrauch:"
					+ co2verbrauch + ", treibstoff:" + treibstoff + "]";
	}
	public String toJson() {
		   return new JSONSerializer().exclude("*.class").serialize(this);
	}
	
}
