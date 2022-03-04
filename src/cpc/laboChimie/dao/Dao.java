/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.dao;

import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.EauCategorie;
import cpc.laboChimie.jpa.GroupClassEau;
import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.jpa.Localite;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.jpa.Utilisateur;
import cpc.laboChimie.jpa.ZoneClimatique;
import cpc.laboChimie.ui.JDialogGroupClass;
import java.util.Date;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 *
 * @author  MAXWELL
 */
@Repository
class Dao implements IDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em = null;



    /**
     * les  utilisateurs
     * @param u
     */

    public List<Utilisateur> getAllUtilisateur()
    {
       return em.createQuery("select u from Utilisateur u").getResultList();
    }


    /**
     * le client
     * @param c
     */
    public void saveClient(Client c) {
        em.persist(c);
    }

    public void updateClient(Client c) {
        em.merge(c);
    }

    public void deleteClient(Client c) { //supprime tous les eaux du client avant de le supprimer de la base de données

       List<Eau> e = getEauByClient(c);
        try{
            for (Eau eau : e) {
                //suppression des analyses
                deleteAllAnalyseEau(eau.getId().longValue());
                //suppression des eaux
                deleteEau(eau);
            }

            em.remove(c);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
    
    
    
    public Client getClientByNom(String nomCli)
    {
        String    query2= "select  c from Client c where c.nomDemandeur=:param";

      Query req = em.createQuery(query2);
      req.setParameter("param", nomCli);
      
      List<Client> ls=req.getResultList();
       for(Client e:ls)
      //Eau e = ls.get(0);
          return e;
          return null;
    
    }
    
    
    
    public Client getClientByID(long id) {
        return em.find(Client.class, id);
    }

    public List<Client> getAllClient() {
        return em.createQuery("select c from Client c").getResultList();
    }

    /**
     * les eaux
     * @param e
     */
   
    public void saveEau(Eau e) {
        em.persist(e);
        //ajouter les méthodes pour remplir la table d'analyses
    }

    
    public void saveGroupClass(GroupClassEau grpclass)
    {
      em.persist(grpclass);
    }
    
    public List<GroupClassEau>getAllGroupClass()
    {
         return em.createQuery("select c from GroupClassEau c").getResultList();
    
    }
    
    
    public void updateEau(Eau e) {
        em.merge(e);
    }
        //ajouter les méthodes pour remplir la table d'analyses
    public void saveAnalyse(Analyse a){
      
        em.persist(a);
      }
    public void updateAnalyse(Analyse a) {
        em.merge(a);
    }

    public Analyse getAnalyseById(long id)
    {
    return em.find(Analyse.class, id);
    }
     

    public List<Eau> getAllEau()
    {
       return em.createQuery("select e from Eau e").getResultList();
    }
    public void deleteAnalyseEau(Analyse a)
{ em.remove(a);
}
    
     public List<Eau> getAllTypeauByRegion(String region, String typeeau)
     {
         
            String query = "select e from Eau e where e.zoneClimatique = param and e.typeEau = param2";
            Query req = em.createQuery(query);
            req.setParameter("param", region);
            req.setParameter("param2", typeeau);
            List ls=req.getResultList();
          if (ls!=null)
            {
              return ls;
            }
          else
            { System.out.println("PAS D\'EAU :getodeAllEauPeri");
                return null;
            }
     }

    
       public List<Eau> getAllEauPeriode( Date d1, Date d2)
         {
            String query = "select e from Eau e where e.dateLivraison > param and e.dateLivraison < param2";
            Query req = em.createQuery(query);
            req.setParameter("param", d1);
            req.setParameter("param2", d2);
            List ls=req.getResultList();
          if (ls!=null)
            {
              return ls;
            }
          else
            { System.out.println("PAS D\'EAU :getodeAllEauPeri");
                return null;
            }
         
         
         }
       
       public Vector<Eau> getAllEauPeriode2( Date d1, Date d2,String typeau)
       {
           Vector<Eau> ls = new  Vector<Eau>();
           List<Eau> lsp =getAllEau();
           
          if (lsp!=null)
            {           
                for(Eau e:lsp)
                    {
                        if((e.getTypeEau().equalsIgnoreCase(typeau))&&(e.getDateLivraison().compareTo(d1)>0)&&(e.getDateLivraison().compareTo(d2)<0))
                        ls.add(e);
                    }
            }else
              return null;
          if (ls!=null)
            {
              return ls;
            }
          else
            { System.out.println("PAS D\'EAU :getodeAllEauPeri");
                return null;
            }
       
       
       
       }
    
       
     /*  public List<Eau> getAllEauAnnuel(long idEau)
         {
           
         
         }
    */
    
    public Eau getEauByNumHydro( String numHydro)
      { String    query2= "select  e from Eau e where e.numEngLabo=:param";

      Query req = em.createQuery(query2);
      req.setParameter("param", numHydro);
      
      List<Eau> ls=req.getResultList();
       for(Eau e:ls)
      //Eau e = ls.get(0);
          return e;
          return null;
      }

public List<Analyse> getAllAnalysesEau(long idEau)
 {//group by a.analyseClasseParam
    String query = "select a from Analyse a where eau_id=:param  ";
    Query req = em.createQuery(query);
      req.setParameter("param", idEau);
       List<Analyse> ls=req.getResultList();
       if (ls!=null)
       {
         return ls;
       }
       else
       { System.out.println("PAS D\'ANALYSES");
         return null;
       }

 }

    public List<Analyse> getAllanalyseEauByClass(String classe, long idEau )
    {
   String query = "select a from Analyse a where a.analyseClasseParam=:param and eau_id=:param2   ";
    Query req = em.createQuery(query);
      req.setParameter("param2", idEau);
      req.setParameter("param", classe);
      List<Analyse> ls=req.getResultList();
       if (ls!=null)
       {
         return ls;
       }
       else
       { System.out.println("PAS D\'ANALYSES");
         return null;
       }

    
    
    }

    public List<Analyse> getAllanalyse()
    {String query = "select a from Analyse a ";
      Query req = em.createQuery(query);
      
      List<Analyse> ls=req.getResultList();
       if (ls!=null)
       {
         return ls;
       }
       else
       { System.out.println("PAS D\'ANALYSES");
         return null;
       }
    }
    
public List<Eau> getAllEauParamRegion(String nomParam)
{

    return null;
}
    
/*public void deleteAnalyseEau(Analyse a)
{ em.remove(a);
}*/

public void deleteAllAnalyseEau(long idEau)
 {String query = "select a from Analyse a where eau_id=:param";
    Query req = em.createQuery(query);
      req.setParameter("param", idEau);
       List<Analyse> ls=req.getResultList();
       if (ls!=null)
       { try{
           for(Analyse a :ls)
         deleteAnalyseEau(a);
         }
         catch(Exception e)
         {
              e.printStackTrace();
         }
       }
       else
       { System.out.println("PAS D\'ANALYSES");
         return ;
       }

 }



    public void saveEauCategorie(EauCategorie ec)
     {
       em.persist(ec);
     }


/**
     * les prametres
     * @param p
     */
   
public void saveParametre(Parametre p)
 {
   em.persist(p);
 }

public void updateParametre(Parametre p)
{
  em.merge(p);
}
public Parametre getParametre(long idParam)
 {
    return em.find(Parametre.class, idParam);
 }

public Parametre getParametre(String nomP)
 {
//   return em.find(Parametre.class, nomP);
       
String    query2= "select  p from Parametre p where p.nomParam=:param2";

      Query req = em.createQuery(query2);
      req.setParameter("param2", nomP);
           List<Parametre> ls=req.getResultList();
       for(Parametre p:ls)
         return p;
       
          return null;
     
     
 }





  public void saveNorme(Norme n)
  {
    em.persist(n);
  }

  public void updateNorme(Norme n)
  {
    em.merge(n);
  }
  
  public Norme getNorme(long idNorme)
  {
    return em.find(Norme.class, idNorme);
  }
  
  public Norme getNorme(Norme nomN)
  {
    return em.find(Norme.class, nomN);
  }

public Norme getNormeById(long idNorme)
{
       return em.find(Norme.class, idNorme);

}

public TypeEau getTypeEau(String nomT)
  {
    
String    query2= "select  t from TypeEau t where t.categorie=:param2";

      Query req = em.createQuery(query2);
      req.setParameter("param2", nomT);
           List<TypeEau> ls=req.getResultList();
       for(TypeEau t:ls)
         return t;
       
          return null;
     
  }

public TypeEau getTypeEau(long idTypeEau)
{
  return em.find(TypeEau.class, idTypeEau);
}

public void saveTypeEau(TypeEau t)
{
  em.persist(t);
}

public void updateTypeEau(TypeEau t)
{
 em.merge(t);
}  




public Eau getEauByID(long id)
{

  return em.find(Eau.class, id);
}






    public Eau getEauByID(long id, String typeEau) {

        //On récupère toutes les eaux et on recherche l'élément correspondante
       
String    query2= "select  e from Eau e where e.id=:param and e.typeEau=:param2";

      Query req = em.createQuery(query2);
      req.setParameter("param2", typeEau);
      req.setParameter("param", id);
      List<Eau> ls=req.getResultList();
       for(Eau e:ls)
      //Eau e = ls.get(0);
          return e;
          return null;
    }
///////////////////////
    public List<Eau> getEauByClient(Client c) {    //récupère la liste des analyses d'un client
        //on récupère les analyses de tous les types d'eau pour le même client

    String query = "select e from Eau e where client_id=:param";
    Query req = em.createQuery(query);
          req.setParameter("param", c.getId().longValue());
       List ls=req.getResultList();
       if (ls!=null)
       {
         return ls;
       }
       else
       { System.out.println("PAS D\'ANALYSES");
         return null;
       }
    }

    //Suppression d'une eau de la bd
    public void deleteEau(Eau e){
        //supprime toutes les analyses avant de supprimer l'eau
        deleteAllAnalyseEau(e.getId().longValue());
        em.remove(e);
    }
/**
 * les normes, zones climatiques, unités
 * @return
 */

    public List<TypeEau> getAllTypeEau() {
        return em.createQuery("select c from TypeEau c").getResultList();
    }

    public List<ZoneClimatique> getAllZoneClim() {
        return em.createQuery("select c from ZoneClimatique c").getResultList();
    }

     public List<EauCategorie> getAllEauCategorie( )
      {//
           return em.createQuery("select e from EauCategorie e ").getResultList();
      }

    public List<Norme> getAllNorme() {
        return em.createQuery("select c from Norme c").getResultList();
    }


    public List<Parametre> getAllParametre()
    {
return em.createQuery("select p from Parametre p").getResultList();
     }
/**
 * à partir de la norme on peut obtenir le parametre
  */

 public List<Norme> getParametreEau(long idTypeEau)
    {//List<Parametre> list = null ;
       //selectionner la liste des normes ayant pour idEau "idTypeEau" querry1
     // selectionner la liste des parametres parmi les normes de la requête précédente Querry2
     String 	query="select n from Norme n where typeeau_id=:param)";
    //  String    query2= "select p  from Parametre p where p.id in ("+query+")";

      Query req = em.createQuery(query);
      req.setParameter("param", idTypeEau);
       List ls=req.getResultList();
        if(ls!=null){
         return ls;
        }
           else
         System.out.println("liste vide------------------------------"+ls);

       return null;
   }
 public void saveLocalite(Localite l)
 {
    em.persist(l);
 }
	
 /*
  *RENVOI LA LISTE DES CLASESSES D'UN TYPE D4EAU
  **/
 
public List getClasseParamTypeEau(long idTypeEau)
{
   String    query="select n from Norme n  where typeeau_id=:param)";
  // String    query2= "select distinct p.classeParam  from Parametre p where p.id in ("+query+")";

      Query req = em.createQuery(query);
      req.setParameter("param", idTypeEau);
       List<Norme> ls=req.getResultList();
        if(/*(ls!=null)&&(ls.size()!=0)*/!ls.isEmpty()){
            System.out.println("\t\t**********TAILLE DE LA LISTE ="+ls.size());
            
            int i =0;
        List nomClass =req.getResultList();
        nomClass.clear();
        nomClass.add(ls.get(0).getParametre().getClasseParam());
       for( Norme n:ls)
       {
         if(!nomClass.contains(n.getParametre().getClasseParam()))
              nomClass.add(n.getParametre().getClasseParam());
               
       }
       

         return nomClass;
        }
           else
         System.out.println("liste vide------------------------------"+ls);

       return null;
}

public Parametre getParamFromNorme(long idParam)
{   //  String 	query="select n from Norme n where typeeau_id=:param)";
   String    query2= "select  p from Parametre p where p.id=:param";

      Query req = em.createQuery(query2);
      req.setParameter("param", idParam);
      Parametre  p = (Parametre)req.getSingleResult();

      return p;
}


}
