package com.entity;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "commercial")
public class Commercial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@ManyToMany(mappedBy = "commercials", fetch = FetchType.LAZY)
	private Set<Tiers> tiers;

	@ManyToMany(mappedBy = "commercials", fetch = FetchType.LAZY)
	private Set<Project> projets;
	
	@ManyToMany(mappedBy = "commercials", fetch = FetchType.LAZY)
	private Set<PointInterest> pointInterests;

	@ManyToOne
	@JoinColumn(name="id_region")
	private Region region;

	@OneToMany(mappedBy = "commercial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Decision> decisions;

	@OneToMany(mappedBy = "commercial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FollowerProject> followedProjects;

	@OneToMany(mappedBy = "commercial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments;

//	@OneToMany(mappedBy = "commercial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Sale> sales;

	@OneToMany(mappedBy = "commercial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users;

	@ManyToMany(mappedBy = "commercials", fetch = FetchType.LAZY)
	private Set<Event> events;

	public Commercial() {
	}

	public Commercial(Integer id, String name, String address, String email, String phone, Set<Tiers> tiers,
			Set<Project> projets, Region region, Set<Decision> decisions, Set<FollowerProject> followedProjects,
			Set<Appointment> appointments, Set<User> users, Set<PointInterest> pointInterests, Set<Event> events) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.tiers = tiers;
		this.projets = projets;
		this.region = region;
		this.decisions = decisions;
		this.followedProjects = followedProjects;
		this.appointments = appointments;

		this.users = users;
		this.events = events;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Tiers> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tiers> tiers) {
		this.tiers = tiers;
	}

	public Set<Project> getProjets() {
		return projets;
	}

	public void setProjets(Set<Project> projets) {
		this.projets = projets;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Set<Decision> getDecisions() {
		return decisions;
	}

	public void setDecisions(Set<Decision> decisions) {
		this.decisions = decisions;
	}

	public Set<FollowerProject> getFollowedProjects() {
		return followedProjects;
	}

	public void setFollowedProjects(Set<FollowerProject> followedProjects) {
		this.followedProjects = followedProjects;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}


	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<PointInterest> getPointInterests() {
		return pointInterests;
	}

	public void setPointInterests(Set<PointInterest> pointInterests) {
		this.pointInterests = pointInterests;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	/**
	 * Comparator to sort commercials list in order of Name
	 */
	public static Comparator<Commercial> NameComparator = new Comparator<Commercial>() {
		@Override
		public int compare(Commercial c1, Commercial c2) {
			return c1.getName().toUpperCase().compareTo(c2.getName().toUpperCase());
		}
	};

	/**
	 * Comparator to sort commercials list in order of id
	 */
	public static Comparator<Commercial> IdComparator = new Comparator<Commercial>() {
		@Override
		public int compare(Commercial c1, Commercial c2) {
			return c1.getId().compareTo(c2.getId());
		}
	};

}
