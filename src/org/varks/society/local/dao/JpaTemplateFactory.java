package org.varks.society.local.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.orm.jpa.JpaTemplate;

public class JpaTemplateFactory {
	public JpaTemplate getInatance() {
		EntityManagerFactory emf = getEMF();
		JpaTemplate template = new JpaTemplate(emf);
		return template;
	}

	private EntityManagerFactory getEMF() {
		return Persistence.createEntityManagerFactory("Hibernate");
	}
}
