/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDBC;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franklin
 *
 */
public class Requettes {
    ResultSet result=null;  // le résultat de la requette
    String queryString;     // la requette qu'on veut executer
    ConnectionBD con;
    private ResultSetMetaData metaresult;
    boolean isSelect;
    public Requettes(ConnectionBD con,String query,boolean isSelect){
        this.con=con;
        queryString =query;
        this.isSelect=isSelect;
//        executer();
//        if(result!=null){
//            try {
//                metaresult = result.getMetaData();
//            } catch (SQLException ex) {
//                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        else metaresult=null;
    }

    public void closeRequette(){
        try {
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeRequette(Requettes requette){
        try {
            requette.getResult().close();
        } catch (SQLException ex) {
            Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ResultSet getResult(){
        return result;
    }
    public ResultSetMetaData getMetaData(){
        return metaresult;
    }


    public void executer(){
        ResultSet resultat = null;
        if(isSelect){
            try {
                // si c'est une requette de sélection, alors on doit faire appel à la methode executeQuery
                resultat = con.getStatement().executeQuery(queryString);
                result =resultat;
            } catch (SQLException ex) {
                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
            }
            result=resultat;
        }
        else{
            // si ce n'est pas une selection, alors ça pourait etre une insertion, mise à jour, suppression
            // ou une requette de definition de données
            try {
                // si c'est une requette de sélection, alors on doit faire appel à la methode executeQuery
                con.getStatement().executeUpdate(queryString);
                
            } catch (SQLException ex) {
                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
	
	public ResultSet ResultatExecuter(String query){
        ResultSet resultat = null;
        
            try {
                // si c'est une requette de sélection, alors on doit faire appel à la methode executeQuery
                resultat = con.getStatement().executeQuery(query);
                return resultat;
            } catch (SQLException ex) {
                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
            }
            return resultat;
    }
	
	
    public void executer(String query,boolean isSelect){
        //queryString=query;
        //this.isSelect=isSelect;
        //executer();
		ResultSet resultat=null;
		if(isSelect){
            try {
                // si c'est une requette de sélection, alors on doit faire appel à la methode executeQuery
                resultat = con.getStatement().executeQuery(query);
                result =resultat;
            } catch (SQLException ex) {
                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            // si ce n'est pas une selection, alors ça pourait etre une insertion, mise à jour, suppression
            // ou une requette de definition de données
            try {
                // si c'est une requette de sélection, alors on doit faire appel à la methode executeQuery
                con.getStatement().executeUpdate(query);
                
            } catch (SQLException ex) {
                Logger.getLogger(Requettes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
		
    }

}
