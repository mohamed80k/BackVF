package com.entity;

import java.io.Serializable;
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
@Table(name = "customer_account")
public class CustomerAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "cin")
	private String cin;

	@Column(name = "phone_bureau")
	private String phoneBureau;

	@Column(name = "phone_contact")
	private String phoneContact;

	@Column(name = "fax")
	private String fax;

	@Column(name = "email")
	private String email;

	@Column(name = "rib")
	private String rib;

	@Column(name = "patent")
	private String patent;

	@Column(name = "create_at")
	private Date createAt;
	
	@Column(name = "raison_sociale")
	private String raisonSociale;
	
	@Column(name = "responsable_reglement")
	private String responsableReglement;
	
	@Column(name = "identifiant_fiscal")
	private String identifiantFiscal;
	
	@Column(name = "numero_rC")
	private String numeroRC;
	
	@Column(name = "have_signature_demander", columnDefinition = "tinyint(1) default 0")
	private boolean haveSignatureDemander;
	
	@Column(name = "have_signature_directeur", columnDefinition = "tinyint(1) default 0")
	private boolean haveSignatureDirecteur;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_account_commercial_id")
	private Commercial commercial;
	
	@Column(name = "adresse")
	private String adresse;
	
	@Column(name = "banque")
	private String banque;
	
	@Column(name = "mode_reglement")
	private String modeReglement;
	
	@Column(name = "delai_reglement")
	private int delaiReglement;

	@Column(name= "montant_caution")
	private double montantCaution;
	
	@Column(name="encours_demande")
	private double encoursDemande;
	
	@Column(name="projet_objet_demande")
	private String projetObjetDemande;
	
	@Column(name="maitre_ouvrage")
	private String maitreOuvrage;

	@Column(name="numero_marche_public")
	private String numeroMarchePublic;
	
	@Column(name="cout_global_projet")
	private double coutGlobalProjet;
	
	@Column(name="ca_potentiel")
	private double caPotentiel;
	
	@Column(name = "delai_projet")
	private int delaiProjet;
	
	@Column(name = "reference_partenaires_clients")
	private String referencePartenairesClients;
	
	@Column(name = "partenaires_fournisseurs_associes")
	private String partenairesFournisseursAssocies;
	
	@Column(name = "demande_type")
	private String demandeType;
	
	@Column(name = "reference", unique = true, nullable = false)
	private String reference;

	public CustomerAccount() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPhoneBureau() {
		return phoneBureau;
	}

	public void setPhoneBureau(String phoneBureau) {
		this.phoneBureau = phoneBureau;
	}

	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getPatent() {
		return patent;
	}

	public void setPatent(String patent) {
		this.patent = patent;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	public String getResponsableReglement() {
		return responsableReglement;
	}

	public void setResponsableReglement(String responsableReglement) {
		this.responsableReglement = responsableReglement;
	}

	public String getIdentifiantFiscal() {
		return identifiantFiscal;
	}

	public void setIdentifiantFiscal(String identifiantFiscal) {
		this.identifiantFiscal = identifiantFiscal;
	}

	public String getNumeroRC() {
		return numeroRC;
	}

	public void setNumeroRC(String numeroRC) {
		this.numeroRC = numeroRC;
	}

	public boolean isHaveSignatureDemander() {
		return haveSignatureDemander;
	}

	public void setHaveSignatureDemander(boolean haveSignatureDemander) {
		this.haveSignatureDemander = haveSignatureDemander;
	}

	public boolean isHaveSignatureDirecteur() {
		return haveSignatureDirecteur;
	}

	public void setHaveSignatureDirecteur(boolean haveSignatureDirecteur) {
		this.haveSignatureDirecteur = haveSignatureDirecteur;
	}

	public Commercial getCommercial() {
		return commercial;
	}

	public void setCommercial(Commercial commercial) {
		this.commercial = commercial;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public String getModeReglement() {
		return modeReglement;
	}

	public void setModeReglement(String modeReglement) {
		this.modeReglement = modeReglement;
	}

	public int getDelaiReglement() {
		return delaiReglement;
	}

	public void setDelaiReglement(int delaiReglement) {
		this.delaiReglement = delaiReglement;
	}

	public double getMontantCaution() {
		return montantCaution;
	}

	public void setMontantCaution(double montantCaution) {
		this.montantCaution = montantCaution;
	}

	public double getEncoursDemande() {
		return encoursDemande;
	}

	public void setEncoursDemande(double encoursDemande) {
		this.encoursDemande = encoursDemande;
	}

	public String getProjetObjetDemande() {
		return projetObjetDemande;
	}

	public void setProjetObjetDemande(String projetObjetDemande) {
		this.projetObjetDemande = projetObjetDemande;
	}

	public String getMaitreOuvrage() {
		return maitreOuvrage;
	}

	public void setMaitreOuvrage(String maitreOuvrage) {
		this.maitreOuvrage = maitreOuvrage;
	}

	public String getNumeroMarchePublic() {
		return numeroMarchePublic;
	}

	public void setNumeroMarchePublic(String numeroMarchePublic) {
		this.numeroMarchePublic = numeroMarchePublic;
	}

	public double getCoutGlobalProjet() {
		return coutGlobalProjet;
	}

	public void setCoutGlobalProjet(double coutGlobalProjet) {
		this.coutGlobalProjet = coutGlobalProjet;
	}

	public double getCaPotentiel() {
		return caPotentiel;
	}

	public void setCaPotentiel(double caPotentiel) {
		this.caPotentiel = caPotentiel;
	}

	public int getDelaiProjet() {
		return delaiProjet;
	}

	public void setDelaiProjet(int delaiProjet) {
		this.delaiProjet = delaiProjet;
	}

	public String getReferencePartenairesClients() {
		return referencePartenairesClients;
	}

	public void setReferencePartenairesClients(String referencePartenairesClients) {
		this.referencePartenairesClients = referencePartenairesClients;
	}

	public String getPartenairesFournisseursAssocies() {
		return partenairesFournisseursAssocies;
	}

	public void setPartenairesFournisseursAssocies(String partenairesFournisseursAssocies) {
		this.partenairesFournisseursAssocies = partenairesFournisseursAssocies;
	}

	public String getDemandeType() {
		return demandeType;
	}

	public void setDemandeType(String demandeType) {
		this.demandeType = demandeType;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
