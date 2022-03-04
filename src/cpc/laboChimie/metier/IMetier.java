/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.metier;

import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.EauCategorie;
import cpc.laboChimie.jpa.GroupClassEau;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.jpa.Utilisateur;
import cpc.laboChimie.jpa.ZoneClimatique;
import cpc.laboChimie.ui.JDialogGroupClass;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author MAXWELL
 */
public interface IMetier {

    /**
    * opérations sur les utilisateurs de l'application
    */

    //on récupère la liste
    public List<Utilisateur> getAllUtilisateur();

    /**
     * Opérations sur le client
     */
    public void saveClient(Client c);
    public void updateClient(Client c);
    public void deleteClient(Client c);
    public Client getClientByID(long id);
    public List<Client> getAllClient();
public Client getClientByNom(String nomCli);
    /**
     * Opérations sur les eaux
     */

    public void saveEau(Eau e);
    public void updateEau(Eau e);
    public Eau getEauByID(long id, String typeEau);
    public Eau getEauByID(long id);
    public Eau getEauByNumHydro( String numHydro);
    public List<Eau> getEauByClient(Client c);
    public void deleteEau(Eau e);
    public List<TypeEau> getAllTypeEau();
    public List<ZoneClimatique> getAllZoneClim();
    public List<EauCategorie> getAllEauCategorie();
    public List<Eau> getAllEauPeriode( Date d1, Date d2);
    public Vector<Eau> getAllEauPeriode2 ( Date d1, Date d2,String typeau);
    public void saveGroupClass(GroupClassEau grpclass);
    public List<GroupClassEau>getAllGroupClass();
    
    public void saveParametre(Parametre p);
    public void updateParametre(Parametre p);
    public Parametre getParametre(long idParam);
    public Parametre getParametre(String nomP);
    
    
    public void saveEauCategorie(EauCategorie ec);

    
    
    /**
     * Opérations sur les Type d'eau
     */
    public void saveTypeEau(TypeEau t);
    public void updateTypeEau(TypeEau t);
    public TypeEau getTypeEau(long idTypeEau);
    public TypeEau getTypeEau(String nomT);

    
    public void saveNorme(Norme n);
    public void updateNorme(Norme n);
    public Norme getNorme(long idNorme);
    public Norme getNorme(Norme nomN);
    public List<Norme> getAllNorme();
    /**
     * Opérations sur les parametres
     */
    public List<Parametre> getAllParametre();
    public List<Norme> getParametreEau(long idTypeEau);
public List getClasseParamTypeEau(long idTypeEau);
public Parametre getParamFromNorme(long idParam);

    public void saveAnalyse(Analyse a);
    public Analyse getAnalyseById(long id);
    public void updateAnalyse(Analyse a);
    public List<Analyse> getAllAnalysesEau(long idEau);
    public List<Analyse> getAllanalyseEauByClass(String classe, long idEau );
    public void deleteAnalyseEau(Analyse a);
    public void deleteAllAnalyseEau(long idEau);
public List<Analyse> getAllanalyse();
public Norme getNormeById(long idNorme);

    public List<Eau> getAllEau();
    public List<Eau> getAllTypeauByRegion(String region, String typeeau);

}
