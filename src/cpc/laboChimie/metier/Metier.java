/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.metier;

import cpc.laboChimie.dao.IDao;
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
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author  MAXWELL
 */
@Service
public class Metier  implements IMetier{

    @Resource
    private IDao iDao = null;

    //on récupère la liste
    public List<Utilisateur> getAllUtilisateur()
    {
      return iDao.getAllUtilisateur();
    }

    /**
     * Opérations sur le client
     */
     @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveClient(Client c)
    {
      iDao.saveClient(c);
     }
      @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateClient(Client c)
    {
      iDao.updateClient(c);
      }
       @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteClient(Client c)
    {
       iDao.deleteClient(c);
       }
    public Client getClientByID(long id)
    {
      return iDao.getClientByID(id);
    }
    public List<Client> getAllClient()
    {
     return iDao.getAllClient();
    }

    /**
     * Opérations sur les eaux
     */
 @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveEau(Eau e)
    {
     iDao.saveEau(e);
 }
  
 
 
 @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateEau(Eau e)
    {
  iDao.updateEau(e);
     }
   @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Eau getEauByID(long id, String typeEau)
    {
      return iDao.getEauByID(id, typeEau);
    }
 @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Eau getEauByNumHydro( String numHydro)
    {
        return iDao.getEauByNumHydro(numHydro);
    }
 
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveGroupClass(GroupClassEau grpclass)
    {
       iDao.saveGroupClass(grpclass);
    }     
    
  public List<GroupClassEau>getAllGroupClass()
  {
     return iDao.getAllGroupClass();
  
  } 
 public Eau getEauByID(long id)
 {
 
   return iDao.getEauByID(id);
 }
 
 public List<Eau> getAllEau()
 {
   return iDao.getAllEau();
 }

     public List<Eau> getAllTypeauByRegion(String region, String typeeau)
     {
       return iDao.getAllTypeauByRegion(region, typeeau);
     }

 
  public List<Eau> getAllEauPeriode( Date d1, Date d2)
   {
      return iDao.getAllEauPeriode( d1, d2);
   }
   
 public Vector<Eau> getAllEauPeriode2( Date d1, Date d2,String typeau)
 {
   return iDao.getAllEauPeriode2( d1, d2,typeau);
 }
    public List<Eau> getEauByClient(Client c)
    {
      return iDao.getEauByClient(c);
    }
     @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteEau(Eau e)
    {
       iDao.deleteEau(e);
     }
 @Transactional(propagation = Propagation.REQUIRES_NEW)
     public void saveAnalyse(Analyse a)
 {
     iDao.saveAnalyse(a);
 }

 @Transactional(propagation = Propagation.REQUIRES_NEW)
 public void updateAnalyse(Analyse a)
 {
     iDao.updateAnalyse(a);
 }
     public Analyse getAnalyseById(long id)
     {
       return iDao.getAnalyseById(id);
     }
    public List<Analyse> getAllAnalysesEau(long idEau)
    {
      return iDao.getAllAnalysesEau(idEau);
    }

    
    public List<Analyse> getAllanalyse()
    {
      return iDao.getAllanalyse();
    }
    
        public List<Analyse> getAllanalyseEauByClass(String classe, long idEau )
        { 
           return iDao.getAllanalyseEauByClass(classe, idEau);
        }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
     public void deleteAnalyseEau(Analyse a)
    {
      iDao.deleteAnalyseEau(a);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
     public void deleteAllAnalyseEau(long idEau)
     {
      iDao.deleteAllAnalyseEau(idEau);
     }
@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveParametre(Parametre p)
      {
         iDao.saveParametre(p);
      }
@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateParametre(Parametre p)
     {
        iDao.updateParametre(p);
     }
   public Parametre getParametre(long idParam)
     {
         return iDao.getParametre(idParam);
     }

   public Parametre getParametre(String nomP)
   {
     return iDao.getParametre(nomP);    
    }
@Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveNorme(Norme n)
  {
    iDao.saveNorme(n);
   }

@Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateNorme(Norme n)
  {
    iDao.updateNorme(n);
  }
 public Norme getNorme(long idNorme)
  {
    return iDao.getNorme(idNorme);
  }
  public Norme getNorme(Norme nomN)
  {
    return iDao.getNorme(nomN);
  }

  public List<Norme> getAllNorme()
  {
    return iDao.getAllNorme();
  }
   
   public Norme getNormeById(long idNorme)
{
   return iDao.getNormeById(idNorme);
}


   
   public Client getClientByNom(String nomCli)
   {
     return iDao.getClientByNom( nomCli);
   }
   
   
@Transactional(propagation = Propagation.REQUIRES_NEW)
      public void saveTypeEau(TypeEau t)
      { 
        iDao.saveTypeEau(t);
       }
@Transactional(propagation = Propagation.REQUIRES_NEW)
      public void updateTypeEau(TypeEau t)
       {
        iDao.updateTypeEau(t);
       }          
    public TypeEau getTypeEau(long idTypeEau)
      {
        return iDao.getTypeEau(idTypeEau);
      }

 public TypeEau getTypeEau(String TypeEau)
      {
        return iDao.getTypeEau(TypeEau);
      }

     public List<TypeEau> getAllTypeEau()
     {
       return iDao.getAllTypeEau();
     }

@Transactional(propagation = Propagation.REQUIRES_NEW)
 public void saveEauCategorie(EauCategorie ec)
 {
    iDao.saveEauCategorie(ec);
 }

       public List<ZoneClimatique> getAllZoneClim()
     {
       return iDao.getAllZoneClim();
     }

       public List<EauCategorie> getAllEauCategorie()
       {
         return iDao.getAllEauCategorie();
       }

       /*****************************************************************/
    public List<Parametre> getAllParametre()
    {
        return iDao.getAllParametre();
    }
    public List<Norme> getParametreEau(long idTypeEau)
    {
        return iDao.getParametreEau(idTypeEau);
    }


public List getClasseParamTypeEau(long idTypeEau)
{
  return iDao.getClasseParamTypeEau(idTypeEau);
}
public Parametre getParamFromNorme(long idParam)
{
   return iDao.getParamFromNorme(idParam);
}


}
