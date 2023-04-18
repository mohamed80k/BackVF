package com.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "follower_project")
public class FollowerProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "object")
	private String object;

	@Column(name = "note")
	private String note;

	@Column(name = "create_at")
	private Date createAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commercial_id")
	private Commercial commercial;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nature_follower_id")
	private NatureFollowerProject natureFollower;

	public FollowerProject() {
	}

	public FollowerProject(Integer id, String object, String note, Date createAt, Project project,
			Commercial commercial, Customer customer, NatureFollowerProject natureFollower) {
		this.id = id;
		this.object = object;
		this.note = note;
		this.createAt = createAt;
		this.project = project;
		this.commercial = commercial;
		this.customer = customer;
		this.natureFollower = natureFollower;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Commercial getCommercial() {
		return commercial;
	}

	public void setCommercial(Commercial commercial) {
		this.commercial = commercial;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public NatureFollowerProject getNatureFollower() {
		return natureFollower;
	}

	public void setNatureFollower(NatureFollowerProject natureFollower) {
		this.natureFollower = natureFollower;
	}

}
