package com.tal.hibernate.model;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "actor")
public class Actor {

	private int actor_id;
	private String first_name;
	private String last_name;
	private Timestamp last_update;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getActor_id() {
		return actor_id;
	}

	public void setActor_id(int actor_id) {
		this.actor_id = actor_id;
	}

	@Basic
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	@Basic
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Column(columnDefinition = "java.sql.Timestamp")
	public Timestamp getLast_update() {
		return last_update;
	}

	public void setLast_update(Timestamp last_updated) {
		this.last_update = last_updated;
	}

	@Override
	public String toString() {
		return "Actor{" + "actor_id=" + actor_id + ", first_name='" + first_name + '\'' + ", last_name='" + last_name
				+ '\'' + '}';
	}

}
