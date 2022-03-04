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
public class EauCategorie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String categorie ;

    public String getCategorie()
      {
         return categorie;
      }

    public void setCategorie(String cat)
      {
         this.categorie = cat;
      }

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeEau typeeau;

    public TypeEau getTypeEau()
    {
        return  typeeau;
    }

    public void setTypeEau(TypeEau t)
    {
        this.typeeau = t;
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
        if (!(object instanceof EauCategorie)) {
            return false;
        }
        EauCategorie other = (EauCategorie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.EauCategorie[id=" + id + "]";
    }

}
