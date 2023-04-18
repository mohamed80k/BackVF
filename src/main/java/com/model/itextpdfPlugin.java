package com.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

import com.dto.CustomerAccountInfoDto;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.DocumentException;

public class itextpdfPlugin {
	
	public String fileDir;
	
	public String getFileDir() {
		return fileDir;
	}



	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}



	/************
	 * Customer Account
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 **************/

	public ByteArrayInputStream customerAccountParReference(CustomerAccountInfoDto customerAccountInfoDto,
			File htmlTemplateFile, String pathTemplatePdf)
			throws FileNotFoundException, IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");

		String htmlString = null;
		try {
			htmlString = FileUtils.readFileToString(htmlTemplateFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			htmlString = htmlString.replace("%name%", customerAccountInfoDto.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			htmlString = htmlString.replace("%ref%", customerAccountInfoDto.getReference());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			htmlString = htmlString.replace("%date%", formatter.format(customerAccountInfoDto.getCreateAt()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (customerAccountInfoDto.getAdresse() != null)
				htmlString = htmlString.replace("%adresse%", customerAccountInfoDto.getAdresse());
			else
				htmlString = htmlString.replace("%adresse%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (customerAccountInfoDto.getRaisonSociale() != null)
				htmlString = htmlString.replace("%raisonSociale%", customerAccountInfoDto.getRaisonSociale());
			else
				htmlString = htmlString.replace("%raisonSociale%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (customerAccountInfoDto.getDemandeType().equals("Nouvelle création")) {
				htmlString = htmlString.replace("%checkedCreate%", "checked");
				htmlString = htmlString.replace("%checkedRev%", " ");
			} else {
				htmlString = htmlString.replace("%checkedCreate%", " ");
				htmlString = htmlString.replace("%checkedRev%", "checked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (customerAccountInfoDto.getBanque() != null)
				htmlString = htmlString.replace("%banque%", customerAccountInfoDto.getBanque());
			else
				htmlString = htmlString.replace("%banque%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			htmlString = htmlString.replace("%caPotentiel%", String.valueOf(customerAccountInfoDto.getCaPotentiel()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			htmlString = htmlString.replace("%coutGlobalProjet%", String.valueOf(customerAccountInfoDto.getCoutGlobalProjet()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			htmlString = htmlString.replace("%delaiProjet%", String.valueOf(customerAccountInfoDto.getDelaiProjet()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			htmlString = htmlString.replace("%delaiReglement%", String.valueOf(customerAccountInfoDto.getDelaiReglement()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			htmlString = htmlString.replace("%MontantCaution%", String.valueOf(customerAccountInfoDto.getMontantCaution()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			htmlString = htmlString.replace("%encoursDemande%", String.valueOf(customerAccountInfoDto.getEncoursDemande()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getCin() != null)
			htmlString = htmlString.replace("%cin%", customerAccountInfoDto.getCin());
			else
				htmlString = htmlString.replace("%cin%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getEmail() != null)
			htmlString = htmlString.replace("%email%", customerAccountInfoDto.getEmail());
			else
				htmlString = htmlString.replace("%email%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getModeReglement() != null)
			htmlString = htmlString.replace("%modeReglement%", customerAccountInfoDto.getModeReglement());
			else
				htmlString = htmlString.replace("%modeReglement%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}		

		try {
			if(customerAccountInfoDto.getFax() != null)
			htmlString = htmlString.replace("%fax%", customerAccountInfoDto.getFax());
			else
				htmlString = htmlString.replace("%fax%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getIdentifiantFiscal() != null)
			htmlString = htmlString.replace("%identifiantFiscal%", customerAccountInfoDto.getIdentifiantFiscal());
			else
				htmlString = htmlString.replace("%identifiantFiscal%","&nbsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getMaitreOuvrage() != null)
			htmlString = htmlString.replace("%maitreOuvrage%", customerAccountInfoDto.getMaitreOuvrage());
			else
				htmlString = htmlString.replace("%maitreOuvrage%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getNumeroMarchePublic() != null)
			htmlString = htmlString.replace("%numeroMarchePublic%", customerAccountInfoDto.getNumeroMarchePublic());
			else
				htmlString = htmlString.replace("%numeroMarchePublic%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getNumeroRC() != null)
			htmlString = htmlString.replace("%numeroRC%", customerAccountInfoDto.getNumeroRC());
			else
				htmlString = htmlString.replace("%numeroRC%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getPartenairesFournisseursAssocies() != null)
			htmlString = htmlString.replace("%partenairesFournisseursAssocies%", customerAccountInfoDto.getPartenairesFournisseursAssocies());
			else
				htmlString = htmlString.replace("%partenairesFournisseursAssocies%","&nbsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(customerAccountInfoDto.getReferencePartenairesClients() != null)
			htmlString = htmlString.replace("%referencePartenairesClients%", customerAccountInfoDto.getReferencePartenairesClients());
			else
				htmlString = htmlString.replace("%referencePartenairesClients%","&nbsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(customerAccountInfoDto.getPatent() != null)
			htmlString = htmlString.replace("%patent%", customerAccountInfoDto.getPatent());
			else
				htmlString = htmlString.replace("%patent%","&nbsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getPhoneBureau() != null)
			htmlString = htmlString.replace("%phoneBureau%", customerAccountInfoDto.getPhoneBureau());
			else
				htmlString = htmlString.replace("%phoneBureau%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getPhoneContact() != null)
			htmlString = htmlString.replace("%phoneContact%", customerAccountInfoDto.getPhoneContact());
			else
				htmlString = htmlString.replace("%phoneContact%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getProjetObjetDemande() != null)
			htmlString = htmlString.replace("%projetObjetDemande%", customerAccountInfoDto.getProjetObjetDemande());
			else
				htmlString = htmlString.replace("%projetObjetDemande%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getResponsableReglement() != null)
			htmlString = htmlString.replace("%responsableReglement%", customerAccountInfoDto.getResponsableReglement());
			else
				htmlString = htmlString.replace("%responsableReglement%","&nbsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getRib() != null)
			htmlString = htmlString.replace("%rib%", customerAccountInfoDto.getRib());
			else
				htmlString = htmlString.replace("%rib%","&nbsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getSignatureDemandeurUri() != null)
			htmlString = htmlString.replace("%signatureDemander%", fileDir + customerAccountInfoDto.getSignatureDemandeurUri());
			else
			htmlString = htmlString.replace("%signatureDemander%", " ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(customerAccountInfoDto.getSignatureDirecteurUri() != null) {
			htmlString = htmlString.replace("%signatureDirecteur%", fileDir + customerAccountInfoDto.getSignatureDirecteurUri());
			}else {
			htmlString = htmlString.replace("%signatureDirecteur%", " ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		HtmlConverter.convertToPdf(htmlString, baos);

		ByteArrayInputStream pdfInputStream = new ByteArrayInputStream(baos.toByteArray());

		return pdfInputStream;

	}

}
