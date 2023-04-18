package com.dto;

import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerAccountAddDto {
	
	@NotNull(message = "Le nom est nul !")
	@Size(min = 3, max = 50, message = "Le nom doit être comprise entre 3 et 50 caractères !")
	private String name;
	
	private Integer id;

	@Size(max = 10, message = "CIN dépasse 7 caractères !")
	private String cin;

	@Size(max = 10, message = "Le téléphone de bureau dépasse 10 caractères !")
	private String phoneBureau;

	@Size(max = 10, message = "Le téléphone de contact dépasse 10 caractères !")
	private String phoneContact;

	private String fax;

	@Size(max = 50, message = "L'email dépasse 50 caractères !")
	private String email;

	private String rib;

	private String patent;

	private Date createAt;
	
	private String raisonSociale;
	
	private String responsableReglement;
	
	private String identifiantFiscal;
	
	private String numeroRC;

	// Attachment !
    @JsonIgnore
	private MultipartFile signatureDemander;
	
    // Attachment !
 	@JsonIgnore
	private MultipartFile signatureDirecteur;
	
 	@JsonIgnore
	private boolean haveSignatureDemander;
	
 	@JsonIgnore
	private boolean haveSignatureDirecteur;
	
	@NotNull(message = "commercial est nul !")
	private Integer commercial;
	
	@Size(max = 200, message = "L'adresse dépasse 200 caractères!")
	private String adresse;
	
	private String banque;
	
	private String modeReglement;
	
	@DecimalMin(value = "0", message = "Délai de règlement est inférieur à 0 !")
	private int delaiReglement;

	@DecimalMin(value = "0", message = "Montant de la caution est inférieur à 0 !")
	private double montantCaution;
	
	@DecimalMin(value = "0", message = "Encours demande est inférieur à 0 !")
	private double encoursDemande;
	
	private String projetObjetDemande;
	
	private String maitreOuvrage;

	private String numeroMarchePublic;

	@DecimalMin(value = "0", message = "Le cout global du projet est inférieur à 0 !")
	private double coutGlobalProjet;

	@DecimalMin(value = "0", message = "CA potentiel est inférieur à 0 !")
	private double caPotentiel;
	
	@DecimalMin(value = "0", message = "Délai du projet est inférieur à 0 !")
	private int delaiProjet;
	
	private String referencePartenairesClients;
	
	private String partenairesFournisseursAssocies;
	
	private String demandeType;
	
	@NotNull(message = "La Référence est nul !")
	@Size(min = 3, max = 50, message = "La Référence doit être comprise entre 3 et 50 caractères !")
	private String reference;

	public CustomerAccountAddDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public MultipartFile getSignatureDemander() {
		return signatureDemander;
	}

	public void setSignatureDemander(MultipartFile signatureDemander) {
		this.signatureDemander = signatureDemander;
	}

	public MultipartFile getSignatureDirecteur() {
		return signatureDirecteur;
	}

	public void setSignatureDirecteur(MultipartFile signatureDirecteur) {
		this.signatureDirecteur = signatureDirecteur;
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

	public Integer getCommercial() {
		return commercial;
	}

	public void setCommercial(Integer commercial) {
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
