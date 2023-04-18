package com.entity;

import java.util.Comparator;
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
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commercial_id")
	private Commercial commercial;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tiers_id")
	private Tiers tiers;

	@Column(name = "description")
	private String description;

	@Column(name = "address")
	private String address;

	@Column(name = "date")
	private Date date;

	public Appointment() {
	}

	public Appointment(Integer id, Commercial commercial, Tiers tiers, String description, String address, Date date) {
		this.id = id;
		this.commercial = commercial;
		this.tiers = tiers;
		this.description = description;
		this.address = address;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Commercial getCommercial() {
		return commercial;
	}

	public void setCommercial(Commercial commercial) {
		this.commercial = commercial;
	}

	public Tiers getTiers() {
		return tiers;
	}

	public void setTiers(Tiers tiers) {
		this.tiers = tiers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Comparator to sort APPOINTMENT list in order of date
	 */
	public static Comparator<Appointment> DateComparator = new Comparator<Appointment>() {
		@Override
		public int compare(Appointment a1, Appointment a2) {
			return a2.getDate().compareTo(a1.getDate());
		}
	};

}
