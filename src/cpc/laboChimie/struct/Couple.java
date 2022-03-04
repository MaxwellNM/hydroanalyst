/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.struct;

import java.util.Date;

/**
 *
 * @author MAXWELL
 */
    
public    class Couple
    {
       public double valp;
        
       public  Date dt;
        public Couple(double a, Date d )
        {
          valp = a;
          dt = new Date();
          dt =d;
        }

        public Couple() {
           // throw new UnsupportedOperationException("Not yet implemented");
        }
    
    
    }
    