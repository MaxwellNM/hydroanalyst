/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author MAXWELL
 */
@Entity
public class GroupClassEau implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String typeGroupeClasse; // 2 choix possibles groupe ou classe
    
    private String nouvelleclasseGroupEau;
    
    public String getTypeGroupClass()
      {
        return typeGroupeClasse;
      }
    
    public void setTypeGroupClass(String type)
      {
        this.typeGroupeClasse = type;
      }
    
    public String getnouvelleclasseGroupEau()
      {
        return nouvelleclasseGroupEau;
      }
    
    public void setnouvelleclasseGroupEau(String type)
      {
        this.nouvelleclasseGroupEau= type;
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
        if (!(object instanceof GroupClassEau)) {
            return false;
        }
        GroupClassEau other = (GroupClassEau) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.GroupClassEau[ id=" + id + " ]";
    }
    
}
