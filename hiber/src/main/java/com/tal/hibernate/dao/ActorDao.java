package com.tal.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.tal.hibernate.HibernateUtil;
import com.tal.hibernate.model.Actor;

public class ActorDao {

	public List<Actor> getAllActors() {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		List<Actor> actors = session.createQuery("FROM Actor").list();
		session.close();
		
		return actors;
	}

	public Actor getWithId(int id) {
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Actor.class);
		cr.add(Restrictions.eq("actor_id", id));
		Actor actor = (Actor) cr.uniqueResult();
		session.close();
		return actor;
	}

}
