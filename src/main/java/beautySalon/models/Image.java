package beautySalon.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String imagePath;
	
	private String name;
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	private Owner owner;
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
	private Administrator administratorr;
	
	public Image() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Administrator getAdministratorr() {
		return administratorr;
	}

	public void setAdministratorr(Administrator administratorr) {
		this.administratorr = administratorr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
