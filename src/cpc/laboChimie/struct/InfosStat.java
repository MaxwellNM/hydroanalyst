/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.struct;

import java.util.Vector;

/**
 *
 * @author MAXWELL
 */
public class InfosStat {
 
    
    public String nomParam;
    public double moyenne;
    public double variance;;
    public double Ecart;
    public double min;
    public double Quart1;    
    public double med;
    public double Quart3;
    public double max;
    public double InterQuart;
    
    public Vector<Couple> serie;
    public int taille;
    public int nb_null; // nbre de valeurs nulles     
    
    public InfosStat(String nom,/* double moy, double var,double ec,double mini,double q1,double me, double q3, double maxi,double interq,*/ Vector<Couple> a)
    {   
        serie = new Vector<Couple>();
        nomParam = nom;
        
      /*  moyenne = moy;
        variance = var;
        Ecart = ec;
        min =mini;
        Quart1 = q1;
        med =me;
        Quart3 = q3;
        max = maxi;
        InterQuart = interq;*/
        taille = a.size();
        serie.addAll(a);
    }
    
    
    
    
    
    
    
    
    
}
