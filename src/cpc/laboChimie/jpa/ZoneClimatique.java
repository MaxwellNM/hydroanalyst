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
public class ZoneClimatique implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String  zoneClimatique;

   @OneToMany(mappedBy="zone")
    private List<Localite>  listLocalite;

    public List getListLocalite()
    {
       return this.listLocalite;
     }
    public void setListLocalite(List<Localite> ls)
    {
        this.listLocalite = ls;
    }


    public String getZoneClimatique()
      {
        return this.zoneClimatique;
      }

    public void setZoneClimatique(String zone)
    {
      this.zoneClimatique=zone;
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
        if (!(object instanceof ZoneClimatique)) {
            return false;
        }
        ZoneClimatique other = (ZoneClimatique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cpc.laboChimie.jpa.ZoneClimatique[id=" + id + "]";
    }

}
