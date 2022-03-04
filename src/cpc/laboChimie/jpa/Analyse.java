/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author HP
 */
@Entity
public class Analyse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String  analyseParamResultat;
  //  Boolean analyseParamQuantifie;
    String  analyseParamNom;
    String  analyseParamCritere;
    String  analyseParamUnite;
    String  analyseParamMethode;
    String  analyseParamReference;
    String  analyseTypeResultat;
    String  analyseClasseParam;
  //  int     analyseModif;

/**
 *
 * Une eau à plusieurs analyses
 */
    @ManyToOne(fetch = FetchType.EAGER)
    private Eau eau;

   public String getEauAnalyseMethod()
    {
        return this.analyseParamMethode;
    }
   public void setEauAnalyseMethod(String e)
   {
     this.analyseParamMethode = e;
    }


   public Eau getEauAnalyse()
    {
        return this.eau;
    }
   public void setEauAnalyse(Eau e)
   {
     this.eau = e;
    }

public String getAnalyseNomParam()
{
    return this.analyseParamNom;
}
public void setAnalyseNomParam(String nomP)
{
    this.analyseParamNom =nomP;
}

public String getAnalyseUniteParam()
{
  return this.analyseParamUnite;
}

public void setAnalyseUniteParam(String s)
{
   this.analyseParamUnite = s;
}

public String getAnalyseCritereParam()
{
  return this.analyseParamCritere;
}

public void setAnalyseCritereParam(String s)
{
   this.analyseParamCritere = s;
}

public String getAnalyseResult()
 {
    return this.analyseParamResultat;
 }

public void setAnalyseResult(String res)
  {
    this.analyseParamResultat = res;
  }



public String getAnalyseTypeResultat()
 {
    return this.analyseTypeResultat;
 }

public void setAnalyseTypeResultat(String res)
  {
    this.analyseTypeResultat = res;
  }

public String getAnalyseClasseParam()
  {
        return  analyseClasseParam;
}

public void setAnalyseClasseParam(String classe)
  {
      analyseClasseParam  = classe;
  
  }
/*public Boolean getAnalyseQuantif()
 {
    return this.analyseParamQuantifie;
 }

public void setAnalyseQuantif(Boolean q)
  {
    this.analyseParamQuantifie = q;
  }
*/

    public Long getId() {
        return id;
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
        if (!(object instanceof Analyse)) {
            return false;
        }
        Analyse other = (Analyse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.NewEntity[id=" + id + "]";
    }

}
