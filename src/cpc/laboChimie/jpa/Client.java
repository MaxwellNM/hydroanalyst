/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author FIDEL & MAXWELL
 */
@Entity
public class Client implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
/**
 * Infos sur le demandeur
 */
    String nomDemandeur;
    String telDemandeur;
    String emailDemandeur;
    String faxDemandeur;
    String bp;
    String ville;

    /**
     * Infos sur le payeur
     * @return
     */

    String nomPayeur;
    String telPayeur;
    String faxPayeur;
    String emailPayeur;

    /**
     * Infos sur la personne à contacter si besoin
     * @return
     */

    String nomPersContact;
    String telPersContact;
    String faxPersContact;
    String emailPersContact;


    @OneToMany(mappedBy="client")
    private List<Eau>  listEaux;

    public List getListEaux()
    {
       return this.listEaux;
     }

    public void setListEaux(List<Eau> ls)
    {
      this.listEaux = ls;
    }

    public Long getId() {
        return id;
    }

    public String getEmailDemandeur() {
        return emailDemandeur;
    }

    public void setEmailDemandeur(String emailDemandeur) {
        this.emailDemandeur = emailDemandeur;
    }

    public String getEmailPayeur() {
        return emailPayeur;
    }

    public void setEmailPayeur(String emailPayeur) {
        this.emailPayeur = emailPayeur;
    }

    public String getEmailPersContact() {
        return emailPersContact;
    }

    public void setEmailPersContact(String emailPersContact) {
        this.emailPersContact = emailPersContact;
    }

    public String getFaxDemandeur() {
        return faxDemandeur;
    }

    public void setFaxDemandeur(String faxDemandeur) {
        this.faxDemandeur = faxDemandeur;
    }

    public String getFaxPayeur() {
        return faxPayeur;
    }

    public void setFaxPayeur(String faxPayeur) {
        this.faxPayeur = faxPayeur;
    }

    public String getFaxPersContact() {
        return faxPersContact;
    }

    public void setFaxPersContact(String faxPersContact) {
        this.faxPersContact = faxPersContact;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public String getNomPayeur() {
        return nomPayeur;
    }

    public void setNomPayeur(String nomPayeur) {
        this.nomPayeur = nomPayeur;
    }

    public String getNomPersContact() {
        return nomPersContact;
    }

    public void setNomPersContact(String nomPersContact) {
        this.nomPersContact = nomPersContact;
    }

    public String getTelDemandeur() {
        return telDemandeur;
    }

    public void setTelDemandeur(String telDemandeur) {
        this.telDemandeur = telDemandeur;
    }

    public String getTelPayeur() {
        return telPayeur;
    }

    public void setTelPayeur(String telPayeur) {
        this.telPayeur = telPayeur;
    }

    public String getTelPersContact() {
        return telPersContact;
    }

    public void setTelPersContact(String telPersContact) {
        this.telPersContact = telPersContact;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "jpa.Client[id=" + id + "]";
    }



}
