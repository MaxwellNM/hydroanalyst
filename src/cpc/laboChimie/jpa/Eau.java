/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author FIDEL & MAXWELL
 */
@Entity
public class Eau implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String typeEau;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePrelevement;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateReceptionLabo;
    private String condConserv;
    private String lieuPrelevement;
    private String eauCategorie;
    private String numEnrGlims;
    private String numEngLabo;
    private String zoneClimatique;
    private String etiquetageDemandeur;
    private String prelevePar;
    private int nbreEchantillon;
 //   int nbreParametreAnalyse;
    private String responssable;

    private String conclusion;

    @Temporal(TemporalType.DATE)
    private Date dateLivraison;
    /**
     * ifos sur le paiement
     */
    private String typeDePayement; //par chèque/comptant, par dévis, bon de commande, convention,
    private String numPaiement;

     //classe de l'eau

    private String eauClassification;
        /*String eauGroupeA2;
        String eauGroupeA3;
        String eauGroupeA4;*/
        String aEtoile;
        String NDEtoile;

    @Temporal(TemporalType.DATE)
    private Date dateEffetPaiement;

    /**
     * Un client demande l'analyse d'une ou de plusieurs eaux
     * @return
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client c)
    {
      this.client = c;
    }
/**
 * liste des analyses d'une eau
 */

    @OneToMany(mappedBy="eau")
    private List<Analyse> listAnalyse;

    public List getListAnalyse()
    {
       return this.listAnalyse;
     }

    public void setListAnalyse(List<Analyse> ls)
    {
      this.listAnalyse = ls;
    }


     @ManyToOne(fetch = FetchType.EAGER)
    private Localite loc;



   public Localite getEauLocalite()
    {
        return this.loc;
    }
   public void setEauLocalite(Localite l)
   {
     this.loc = l;
    }



    public String getNDEtoile() {
        return NDEtoile;
    }

    public void setNDEtoile(String NDEtoile) {
        this.NDEtoile = NDEtoile;
    }

    public String getaEtoile() {
        return aEtoile;
    }

    public void setaEtoile(String aEtoile) {
        this.aEtoile = aEtoile;
    }

    public String getEauGroupe() {
        return eauClassification;
    }
    public void setEauGroupe(String eauGroupeA1) {
        this.eauClassification = eauGroupeA1;
    }

  /*  public String getEauGroupeA2() {
        return eauGroupeA2;
    }

    public void setEauGroupeA2(String eauGroupeA2) {
        this.eauGroupeA2 = eauGroupeA2;
    }

    public String getEauGroupeA3() {
        return eauGroupeA3;
    }

    public void setEauGroupeA3(String eauGroupeA3) {
        this.eauGroupeA3 = eauGroupeA3;
    }

    public String getEauGroupeA4() {
        return eauGroupeA4;
    }

    public void setEauGroupeA4(String eauGroupeA4) {
        this.eauGroupeA4 = eauGroupeA4;
    }
*/


    public String getNumPaiement() {
        return numPaiement;
    }

    public String getPrelevePar() {
        return prelevePar;
    }

/*    public int getNombreParametre()
    {
      return nbreParametreAnalyse;
    }

    public void setNombreParametre(int p)
    {
      this.nbreParametreAnalyse = p;
    }*/
    public int getNombreEchantillon() {
        return nbreEchantillon;
    }

    public void setNombreEchantillon(int nbre) {
        this.nbreEchantillon = nbre;
    }

    public void setPrelevePar(String prelevePar) {
        this.prelevePar = prelevePar;
    }

    public void setNumPaiement(String numPaiement) {
        this.numPaiement = numPaiement;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getCondConserv() {
        return condConserv;
    }

    public void setCondConserv(String condConserv) {
        this.condConserv = condConserv;
    }

    public Date getDateEffetPaiement() {
        return dateEffetPaiement;
    }

    public void setDateEffetPaiement(Date dateEffetPaiement) {
        this.dateEffetPaiement = dateEffetPaiement;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public Date getDatePrelevement() {
        return datePrelevement;
    }

    public void setDatePrelevement(Date datePrelevement) {
        this.datePrelevement = datePrelevement;
    }

    public Date getDateReceptionLabo() {
        return dateReceptionLabo;
    }

    public void setDateReceptionLabo(Date dateReceptionLabo) {
        this.dateReceptionLabo = dateReceptionLabo;
    }

    public String getEtiquetageDemandeur() {
        return etiquetageDemandeur;
    }

    public void setEtiquetageDemandeur(String etiquetageDemandeur) {
        this.etiquetageDemandeur = etiquetageDemandeur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieuPrelevement() {
        return lieuPrelevement;
    }

    public void setLieuPrelevement(String lieuPrelevement) {
        this.lieuPrelevement = lieuPrelevement;
    }

    public String getNumEngLabo() {
        return numEngLabo;
    }

    public void setNumEngLabo(String numEngLabo) {
        this.numEngLabo = numEngLabo;
    }

    public String getNumEnrGlims() {
        return numEnrGlims;
    }

    public void setNumEnrGlims(String numEnrGlims) {
        this.numEnrGlims = numEnrGlims;
    }

    public String getResponssable() {
        return responssable;
    }

    public void setResponssable(String responssable) {
        this.responssable = responssable;
    }

    public String getTypeDePayement() {
        return typeDePayement;
    }

    public void setTypeDePayement(String typeDePayement) {
        this.typeDePayement = typeDePayement;
    }

    public String getTypeEau() {
        return typeEau;
    }

    public void setTypeEau(String typeEau) {
        this.typeEau = typeEau;
    }

    public String geteauCategorie() {
        return eauCategorie;
    }

    public void seteauCategorie(String eauCategorie) {
        this.eauCategorie = eauCategorie;
    }

    public String getZoneClimatique() {
        return zoneClimatique;
    }

    public void setZoneClimatique(String zoneClimatique) {
        this.zoneClimatique = zoneClimatique;
    }


}
