/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cpc.laboChimie.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author HP
 */
@Entity
public class Parametre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String nomParam;
    String classeParam;
    String typeResultat;
   // String uniteParam;
   // String normeParam;

   //@OneToMany()
    //private List<Norme> listNormeParam;

   /*  public List getListNorme()
    {
       return listNormeParam;
     }

    public void setListNorme(List<Norme> ls)
    {
      this.listNormeParam = ls;
    }*/


    public String getNomParam()
    {
      return this.nomParam;
    }

    public void setNomParam(String nom)
    {
     this.nomParam=nom;
    }


    public String getClasseParam()
    {
      return this.classeParam;
    }

    public void setClasseParam(String classe)
    {
     this.classeParam=classe;
    }


    public String getTypeResult()
    {
      return this.typeResultat;
    }

    public void setTypeResult(String type)
    {
     this.typeResultat= type;
    }
/*

    public String getUniteParam()
    {
      return this.uniteParam;
    }

    public void setUniteParam(String unite)
    {
     this.uniteParam=unite;
    }
*/
/*
    public String getNormeParam()
    {
      return this.normeParam;
    }

    public void setNormeParam(String norme)
    {
     this.normeParam = norme;
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
        if (!(object instanceof Parametre)) {
            return false;
        }
        Parametre other = (Parametre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.Parametre[id=" + id + "]";
    }

}
