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

/**
 *
 * @author HP
 */
@Entity
public class Norme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String nomParam;
    String uniteParam;
  //String critereParam;
    String normeCritere ;
    String normeReference;
    String normeMethode;
    //pour les analyses non quantifiables
    String loq; 

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeEau typeeau;

    public TypeEau getTypeEau() {
        return typeeau;
    }

    public void setTypeEau(TypeEau c)
    {
      this.typeeau = c;
    }



    @ManyToOne(fetch = FetchType.EAGER)
    private Parametre param;

    public Parametre getParametre() {
        return this.param;
    }

    public void setParametre(Parametre p)
    {
      this.param = p;
    }


public String getNormeLoqParam ()
 {
   return this.loq;
 }

public void setNormeLoqParam(String normeloq)
 {
   this.loq = normeloq;
 }


public String getNormeNomParam ()
 {
   return this.nomParam;
 }

public void setNormeNomParam(String normeNom)
 {
   this.nomParam = normeNom;
 }

public String getNormeUniteParam ()
 {
   return this.uniteParam;
 }

public void setNormeUniteParam(String normeUnite)
 {
   this.uniteParam = normeUnite;
 }


public String getNormeCritereParam ()
 {
   return this.normeCritere;
 }

public void setNormeCritereParam(String normeCritere)
 {
   this.normeCritere = normeCritere ;
 }


public String getNormeReferenceParam ()
 {
   return this.normeReference;
 }

public void setNormeReferenceParam(String normeRef)
 {
   this.normeReference = normeRef;
 }

public String getNormeMethodeParam ()
 {
   return this.normeMethode;
 }

public void setNormeMethodeParam(String methode)
 {
   this.normeMethode = methode;
 }



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
        if (!(object instanceof Norme)) {
            return false;
        }
        Norme other = (Norme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.Norme[id=" + id + "]";
    }

}
