package org.varks.society.local.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.varks.society.local.dao.PhotoDAO;
import org.varks.society.local.entities.Photo;

public class PhotoDAOJpaImpl implements PhotoDAO {
	private JpaTemplate template;

	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}

	public JpaTemplate getTemplate() {
		return template;
	}

	@Override
	public Photo findById(final Object id) {
		try {
			return template.execute(new JpaCallback<Photo>() {

				@Override
				public Photo doInJpa(EntityManager arg0)
						throws PersistenceException {
					Photo entity = arg0.find(Photo.class, id);
//					entity.setPhotoAlbum(null);
	
					return entity;
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;

		}
	}

	public Long  insert(final Photo photo) {
		try {
			return template.execute(new JpaCallback<Long>() {

				@Override
				public Long doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					Photo entity = arg0.merge(photo);
					arg0.getTransaction().commit();

					return entity.getId();
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public boolean update(final Photo photo) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					arg0.merge(photo);
					arg0.getTransaction().commit();

					return true;
				}

			});
		} catch (DataAccessException ex) {

			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(final Photo photo) {
		try{
			return template.execute(new JpaCallback<Boolean>(){

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					Photo managedPhoto = arg0.merge(photo);
					arg0.remove(managedPhoto);
					arg0.getTransaction().commit();
					return true;
				}
				
			});
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Photo> findAll() {
		try{
			return template.execute(new JpaCallback<List<Photo> >(){

				@Override
				public List<Photo> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select photo from Photo photo";
					TypedQuery<Photo> query = arg0.createQuery(queryString, Photo.class);
					final List<Photo> photos = query.getResultList();
					for(Photo photo :photos){
						photo.setPhotoAlbum(null);
					
					}

					return photos;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Photo> findAll(final int pageNumber, final int pageCapacity) {
		try{
			return template.execute(new JpaCallback<List<Photo> >(){

				@Override
				public List<Photo> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select photo from Photo photo";
					TypedQuery<Photo> query = arg0.createQuery(queryString, Photo.class);
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					final List<Photo> photos = query.getResultList();
//					for(Photo photo :photos){
//						photo.setPhotoAlbum(null);
//					
//					}
					return photos;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(final Object o) {
		try{
			return template.execute(new JpaCallback<Boolean>(){

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					Photo entity = arg0.find(Photo.class, o);
					arg0.remove(entity);
					arg0.getTransaction().commit();
					return true;
				}
				
			});
		
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Photo> findByPhotoAlbumId(final Long photoAlbumId, final int pageNumber, final int pageCapacity) {
		try{
			return template.execute(new JpaCallback<List<Photo> >(){

				@Override
				public List<Photo> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select photo from Photo photo where photo.photoAlbum.id = :photoAlbumId";
					TypedQuery<Photo> query = arg0.createQuery(queryString, Photo.class);
					query.setParameter("photoAlbumId", photoAlbumId);
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					final List<Photo> photos = query.getResultList();
					return photos;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	
	
	

}
