package org.varks.society.local.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.varks.society.local.dao.PhotoAlbumDAO;
import org.varks.society.local.entities.PhotoAlbum;
import org.varks.society.local.entities.User;

public class PhotoAlbumJpaImpl implements PhotoAlbumDAO {
	private JpaTemplate template;

	public JpaTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}

	@Override
	public PhotoAlbum findById(final Object id) {
		try {
			return template.execute(new JpaCallback<PhotoAlbum>() {

				@Override
				public PhotoAlbum doInJpa(EntityManager arg0)
						throws PersistenceException {
					PhotoAlbum entity = arg0.find(PhotoAlbum.class, id);
					entity.setPhotos(null);
//					entity.setUser(null);
					return entity;
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;

		}
	}

	@Override
	public Long insert(final PhotoAlbum photoAlbum) {
		try {
			return template.execute(new JpaCallback<Long>() {

				@Override
				public Long doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					PhotoAlbum entity = arg0.merge(photoAlbum);
					arg0.getTransaction().commit();

					return entity.getId();
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(final PhotoAlbum photoAlbum) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					arg0.merge(photoAlbum);
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
	public boolean delete(final PhotoAlbum photoAlbum) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					PhotoAlbum managed = arg0.merge(photoAlbum);
					arg0.remove(managed);
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
	public List<PhotoAlbum> findAll() {
		try {
			return template.execute(new JpaCallback<List<PhotoAlbum>>() {

				@Override
				public List<PhotoAlbum> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString = "select PhotoAlbum from PhotoAlbum photoAlbum";
					TypedQuery<PhotoAlbum> query = arg0.createQuery(
							queryString, PhotoAlbum.class);
					final List<PhotoAlbum> photoAlbums = query.getResultList();

					/*Iterator<PhotoAlbum> i = photoAlbums.iterator();
					for (PhotoAlbum album; i.hasNext();) {
						album = i.next();
						album.setPhotos(null);
						album.setUser(null);
					}*/
					
					for(PhotoAlbum album: photoAlbums) {
						album.setPhotos(null);
//						album.setUser(null);
					}

					return photoAlbums;
				}

			});

		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PhotoAlbum> findAll(final int pageNumber, final int pageCapacity) {
		try {
			return template.execute(new JpaCallback<List<PhotoAlbum>>() {

				@Override
				public List<PhotoAlbum> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString = "select PhotoAlbum from PhotoAlbum PhotoAlbum";
					TypedQuery<PhotoAlbum> query = arg0.createQuery(
							queryString, PhotoAlbum.class);
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					final List<PhotoAlbum> photoAlbums = query.getResultList();
					for(PhotoAlbum album: photoAlbums) {
						album.setPhotos(null);
//						album.setUser(null);
					}
					return photoAlbums;
				}

			});

		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteById(final Object o) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					PhotoAlbum entity = arg0.find(PhotoAlbum.class, o);
					arg0.remove(entity);
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
	public List<PhotoAlbum> findByUser(final User user) {
		try {
			return template.execute(new JpaCallback<List<PhotoAlbum>>() {

				@Override
				public List<PhotoAlbum> doInJpa(EntityManager arg0)
						throws PersistenceException {

					String queryString = "select p from PhotoAlbum p where p.user = :a";
					TypedQuery<PhotoAlbum> query = arg0.createQuery(
							queryString, PhotoAlbum.class);
					query.setParameter("a", user);
					final List<PhotoAlbum> photoAlbums = query.getResultList();
					for(PhotoAlbum album: photoAlbums) {
						album.setPhotos(null);
//						album.setUser(null);
					}
					return photoAlbums;
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PhotoAlbum> findByUser(final User user, final int pageNumber,
			final int pageCapacity) {
		try {
			return template.execute(new JpaCallback<List<PhotoAlbum>>() {

				@Override
				public List<PhotoAlbum> doInJpa(EntityManager arg0)
						throws PersistenceException {

					String queryString = "select p from PhotoAlbum p where p.user = :a";
					TypedQuery<PhotoAlbum> query = arg0.createQuery(
							queryString, PhotoAlbum.class);
					query.setParameter("a", user);
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					final List<PhotoAlbum> photoAlbums = query.getResultList();
					for(PhotoAlbum album: photoAlbums) {
						album.setPhotos(null);
//						album.setUser(null);
					}
					return photoAlbums;
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
