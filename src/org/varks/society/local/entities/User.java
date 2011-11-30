package org.varks.society.local.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	
	private Login login;
	
	private String name ;
	
	@Lob
	private byte[] photo;

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private Collection<Record> records;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private Collection<PhotoAlbum> photoAlbums;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public User() {
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", name=" + name
				+ "]";
	}

	public void setRecords(Collection<Record> records) {
		this.records = records;
	}

	public Collection<Record> getRecords() {
		return records;
	}

	public void setPhotoAlbums(Collection<PhotoAlbum> photoAlbums) {
		this.photoAlbums = photoAlbums;
	}

	public Collection<PhotoAlbum> getPhotoAlbums() {
		return photoAlbums;
	}
	
	
}
