/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.metierConfig;

import cpc.laboChimie.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Fidélus
 */
public class ParametreGestion {
    ////////////////

    IMetier metier = null;
////////////
    private static ParametreGestion parametreGestion = null;
    ///////////
    private ApplicationContext ctx = null;

    public void initSpring() {

        //initialisation de l'application
        //Fichier de description de l'Enterprise Java Bean (EJB)
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    // le constructeur du paramètre de gistion. on initialise l'application
    private ParametreGestion() {
        initSpring();
    }

    // retourne une instance de paramètre gestion qui va servir dans la couche metier
    public static ParametreGestion getInstance() {

        if (parametreGestion == null) {
            parametreGestion = new ParametreGestion();
        }
        return parametreGestion;
    }

    //////L'objet qui va permettre de manipuler les données
    public IMetier getMetier() {
        setMetier((IMetier) ctx.getBean("metier"));
        return metier;
    }

    public void setMetier(IMetier iMetier) {
        this.metier = iMetier;
    }
}
