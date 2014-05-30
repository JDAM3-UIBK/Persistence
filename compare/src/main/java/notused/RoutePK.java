package notused;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This class realizes the primary key
 * for the class RouteDB.
 * 
 * @author Manuel Kapferer
 *
 */
@Embeddable 
public class RoutePK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//@Column(name="DATE", nullable = true)
	public String date;
	
	//@Column(name = "COSTUMER_NAME", nullable = true)
	public String user_name_id;
	
	public RoutePK() {
	}

	public RoutePK(String date,String user_name_id) {
	
	this.date = date;
	this.user_name_id = user_name_id;
	}

	public String getDate(){
		return date;
	}

	public String getUser_name_id() {
		return user_name_id;
	}

	public void setUser_name_id(String user_name_id) {
		this.user_name_id = user_name_id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + date.hashCode();
		result = prime * result + ((user_name_id == null) ? 0 : user_name_id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (getClass() != obj.getClass())
			return false;

		RoutePK otherPk = (RoutePK) obj;
		
		if(this.date.compareTo(otherPk.date) != 0)
			return false;
		
		if(this.user_name_id == null){
			if(otherPk.user_name_id == null)
				return false;
		}
		else if(this.user_name_id.compareTo(otherPk.user_name_id) != 0)
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "RoutePK [date=" + date + ", user_name_id=" + user_name_id + "]";
	}

	
	
}