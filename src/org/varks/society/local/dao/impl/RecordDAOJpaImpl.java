package org.varks.society.local.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.varks.society.local.dao.RecordDAO;
import org.varks.society.local.entities.Record;

public class RecordDAOJpaImpl implements RecordDAO {
	private JpaTemplate template;
	
	public RecordDAOJpaImpl() {}

	public JpaTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JpaTemplate template) {
		this.template = template;
	}

	@Override
	public Record findById(final Object o) {
		
		try {
			return template.execute(new JpaCallback<Record>() {

				@Override
				public Record doInJpa(EntityManager arg0)
						throws PersistenceException {
					Record entity = arg0.find(Record.class, o);
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
	public Long insert(final Record record) {
		try {
			return template.execute(new JpaCallback<Long>() {

				@Override
				public Long doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					Record entity = arg0.merge(record);
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
	public boolean update(final Record record) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					arg0.merge(record);
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
	public boolean delete(final Record record) {
		try {
			return template.execute(new JpaCallback<Boolean>() {

				@Override
				public Boolean doInJpa(EntityManager arg0)
						throws PersistenceException {
					arg0.getTransaction().begin();
					Record managedRecord = arg0.merge(record);
					arg0.remove(managedRecord);
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
	public List<Record> findAll() {
		try{
			return template.execute(new JpaCallback<List<Record> >(){

				@Override
				public List<Record> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select Record from Record record";
					TypedQuery<Record> query = arg0.createQuery(queryString, Record.class);
					final List<Record> records = query.getResultList();
//					for(Record record: records){
//						record.setUser(null);
//					}

					return records;
				}
				
			});
			
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Record> findAll(final int pageNumber, final int pageCapacity) {
		try{
			return template.execute(new JpaCallback<List<Record> >(){

				@Override
				public List<Record> doInJpa(EntityManager arg0)
						throws PersistenceException {
					final String queryString ="select record from Record record";
					TypedQuery<Record> query = arg0.createQuery(queryString, Record.class);
					query.setFirstResult(pageNumber * pageCapacity);
					query.setMaxResults(pageCapacity);
					final List<Record> records = query.getResultList();
//					for(Record record: records){
//						record.setUser(null);
//					}
					return records;
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
					Record entity = arg0.find(Record.class, o);
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
	public List<Record> findByUserId(final Long id, final int pageNumber, final int pageCapacity) {
		try{
			return template.execute(new JpaCallback<List<Record>>(){

				@Override
				public List<Record> doInJpa(EntityManager arg0)
						throws PersistenceException {
				final String queryString ="Select record from Record record where record.user.id=:id";
				TypedQuery<Record> query=arg0.createQuery(queryString, Record.class);
				query.setParameter("id", id);
				query.setFirstResult(pageCapacity*pageNumber);
				query.setMaxResults(pageCapacity);
				final List<Record> records=query.getResultList();
//				for(Record record: records)
				return records;
 					
				}
				
			});
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Record> findByUserId(final Long id) {
		try{
			return template.execute(new JpaCallback<List<Record>>(){

				@Override
				public List<Record> doInJpa(EntityManager arg0)
						throws PersistenceException {
				final String queryString ="Select record from Record record where record.user.id=:id";
				TypedQuery<Record> query=arg0.createQuery(queryString, Record.class);
				query.setParameter("id", id);
				final List<Record> records=query.getResultList();
//				for(Record record: records){
//					record.setUser(null);
//				}
				return records;
 					
				}
				
			});
		}catch(DataAccessException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	
}


