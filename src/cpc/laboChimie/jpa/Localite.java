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
public class Localite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String nomLocalite;
    String ville;
    String Region;


       @ManyToOne(fetch = FetchType.EAGER)
       private ZoneClimatique zone;


   public ZoneClimatique getZoneClimatique()
     {
       return this.zone;
     }

    public void setZoneClimatique(ZoneClimatique zone)
    {
      this.zone = zone;
     }

    public String getNomLocalite()
     {
       return this.nomLocalite;
     }

    public void setNomLocalite(String nom)
    {
      this.nomLocalite = nom;
     }

    public String getVilleLocalite()
     {
       return this.ville;
     }

    public void setVilleLocalite(String ville)
    {
      this.ville = ville;
     }

    public String getRegionLocalite()
     {
       return this.Region;
     }

    public void setRegionLocalite(String nom)
    {
      this.Region = nom;
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
        if (!(object instanceof Localite)) {
            return false;
        }
        Localite other = (Localite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.Localite[id=" + id + "]";
    }

}
