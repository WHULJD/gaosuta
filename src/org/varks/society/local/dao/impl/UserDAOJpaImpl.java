package org.varks.society.local.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.varks.society.local.dao.UserDAO;
import org.varks.society.local.entities.Login;
import org.varks.society.local.entities.User;

public class UserDAOJpaImpl implements UserDAO {
	private JpaTemplate template;

	@Override
	public User findById(final Object o) {
		try {
			return template.execute(new JpaCallback<User>() {

				@Override
				public User doInJpa(EntityManager arg0)
						throws PersistenceException {
					User entity = arg0.find(User.class, o);
					entity.setPhotoAlbums(null);
					entity.setRecords(null);
					return entity;
				}

			});
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			return null;

		}
	}

	@Override
	public Long insert(final User user) {
		try {
			return template.execute(new JpaCallback<Long>() {

				@Override
				public Long doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					User entity = arg0.merge(user);
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
	public boolean update(final User user) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					arg0.merge(user);
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
	public boolean delete(final User user) {
		try{
			return template.execute(new JpaCallback<Boolean>(){

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					
					User managedUser = arg0.merge(user);
					arg0.remove(managedUser);
					
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
	public List<User> findAll() {
		try{
			return template.execute(new JpaCallback<List<User> >(){

				@Override
				public List<User> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select user from User user";
					TypedQuery<User> query = arg0.createQuery(queryString, User.class);
					final List<User> users = query.getResultList();
					for(User user: users){
						user.setPhotoAlbums(null);
						user.setRecords(null);
					}
					

					return users;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<User> findAll(final int pageNumber, final int pageCapacity) {
		try{
			return template.execute(new JpaCallback<List<User> >(){

				@Override
				public List<User> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select user from User user";
					TypedQuery<User> query = arg0.createQuery(queryString, User.class);
					
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					
					final List<User> users = query.getResultList();
					for(User user: users){
						user.setPhotoAlbums(null);
						user.setRecords(null);
					}
					return users;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByLogin(final Login login) {
		try {
			return template.execute(new JpaCallback<User>() {
				@Override
				public User doInJpa(EntityManager em)
						throws PersistenceException {
					String queryString = "select user from User user where user.login = :login";
					TypedQuery<User> query = em.createQuery(queryString, User.class);
					query.setParameter("login", login);
					User user = query.getSingleResult();
					return user;
				}
			});
		} catch(DataAccessException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}

	public JpaTemplate getTemplate() {
		return template;
	}

	@Override
	public boolean deleteById(final Object o) {
		try{
			return template.execute(new JpaCallback<Boolean>(){

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					User entity = arg0.find(User.class, o);
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
	public User findByUserName(final String userName) {
		try{
			return  template.execute(new JpaCallback<User>(){

				@Override
				public User doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String QueryString ="select user from User user where user.userName=:userName";
					TypedQuery<User> query=arg0.createQuery(QueryString,User.class);
					query.setParameter("userName", userName);
					User user=query.getSingleResult();
					user.setPhotoAlbums(null);
					user.setRecords(null);
					return user;
					
					}
				
			});
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

}
