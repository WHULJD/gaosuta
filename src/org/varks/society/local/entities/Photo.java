package org.varks.society.local.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Photo implements Serializable{
	@Id @GeneratedValue
	private Long id;
	
	private String title;
	
	private long generateTime;
	
	private String description;
	
	private String previewUrl;
	
	private String url;
	
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, name="photoAlbum_id",referencedColumnName="id")
	private PhotoAlbum photoAlbum;

	public Photo() {
		super();
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
		Photo other = (Photo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public long getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(long date) {
		this.generateTime = date;
	}

	public PhotoAlbum getPhotoAlbum() {
		return photoAlbum;
	}

	public void setPhotoAlbum(PhotoAlbum photoAlbum) {
		this.photoAlbum = photoAlbum;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", title=" + title + ", date=" + generateTime
				+ ", description=" + description + ", photoAlbum=" + photoAlbum
				+ "]";
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}
	
	
}
