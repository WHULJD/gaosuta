package org.varks.society.local.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@SuppressWarnings("serial")
@Entity
public class PhotoAlbum implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	private String description;
	
	private long generateTime;
	
	@OneToMany(mappedBy="photoAlbum",cascade=CascadeType.ALL)
	private Collection<Photo> photos;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;

	public PhotoAlbum() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<Photo> photos) {
		this.photos = photos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotoAlbum other = (PhotoAlbum) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "PhotoAlbum [id=" + id + ", title=" + title + ", Description="
				+ description + ", generateTime=" + generateTime + ", photos=" + photos
				+ ", user=" + user + "]";
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setGenerateTime(long generateTime) {
		this.generateTime = generateTime;
	}

	public long getGenerateTime() {
		return generateTime;
	}
	
	


}
