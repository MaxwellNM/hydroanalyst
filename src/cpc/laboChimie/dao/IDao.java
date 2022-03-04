/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.dao;

import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.EauCategorie;
import cpc.laboChimie.jpa.GroupClassEau;
import cpc.laboChimie.jpa.Localite;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.jpa.TypeEau;
//import cpc.laboChimie.jpa.Unite;
import cpc.laboChimie.jpa.Utilisateur;
import cpc.laboChimie.jpa.ZoneClimatique;
//import cpc.laboChimie.ui.JDialogGroupClass;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 *
 * @author  MAXWELL
 */
public interface IDao {


    /**
     * Opérations sur les utilisateurs
     */

      public List<Utilisateur> getAllUtilisateur();

    /**
     * Opérations sur le client
     */

    public void saveClient(Client c);  //1
    public void updateClient(Client c);  //2
    public void deleteClient(Client c);  //3
    public Client getClientByID(long id); //4
    public List<Client> getAllClient();  //5
    public Client getClientByNom(String nomCli);

    /**
     * Opérations sur les eaux
     */

    public void saveEau(Eau e);  //6
    public void updateEau(Eau e);  //7
    public Eau getEauByID(long id, String typeEau);  //8
    public Eau getEauByNumHydro( String numHydro);  //8
    public List<Eau> getEauByClient(Client c); // 9
    public void deleteEau(Eau e); // 10
    public Eau getEauByID(long idEau);  //8
    public List<Eau> getAllEau();
    public List<Eau> getAllEauPeriode( Date d1, Date d2);
    public Vector<Eau> getAllEauPeriode2( Date d1, Date d2,String typeau);
    public List<Eau> getAllTypeauByRegion(String region, String typeeau);
    /*
     *Recupère les autres   
     */
    public List<Eau> getAllEauParamRegion(String nomParam);
    
    public void saveGroupClass(GroupClassEau grpclass);
    public List<GroupClassEau>getAllGroupClass();
    
    // public List<Eau> getAllEauAnnuel(long idEau);
    
    
    
    public void saveAnalyse(Analyse a);
    public Analyse getAnalyseById(long id);
    public void updateAnalyse(Analyse a);
    public List<Analyse> getAllAnalysesEau(long idEau);
    public void deleteAnalyseEau(Analyse a);
    public void deleteAllAnalyseEau(long idEau);
    public List<Analyse> getAllanalyseEauByClass(String classe, long idEau );
    public List<Analyse> getAllanalyse();
    public List<TypeEau> getAllTypeEau(); //12

    public List<ZoneClimatique> getAllZoneClim(); //13

    public List<EauCategorie> getAllEauCategorie();
    
    public void saveLocalite(Localite l);
    
    


    /**
     * Opérations sur les categories d'eau
     */
    
    public void saveEauCategorie(EauCategorie ec);
    
/**
 * les autres
 */
    
//    public List<Unite> getAllUnite(); //11

    /**
     * Opérations sur les normes
     */
    
  public List<Norme> getAllNorme(); //13
  public Norme getNormeById(long idNorme);
  public void saveNorme(Norme n);
  public void updateNorme(Norme n);
  public Norme getNorme(long idNorme);
  public Norme getNorme(Norme nomN);

  
  /**
     * Opérations sur les types d'eau
     */
  public void saveTypeEau(TypeEau t);
  public void updateTypeEau(TypeEau t);
  public TypeEau getTypeEau(long idTypeEau);
  public TypeEau getTypeEau(String nomT);
  
    /**
     * Opérations sur les Paramètres
     */
public void saveParametre(Parametre p);
public void updateParametre(Parametre p);
public Parametre getParametre(long idParam);
public Parametre getParametre(String nomP);


public List<Parametre> getAllParametre();
public List<Norme> getParametreEau(long idTypeEau);

public List getClasseParamTypeEau(long idTypeEau);

//recupere un parametre à partir de la norme
public Parametre getParamFromNorme(long idParam);
}

