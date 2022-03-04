/*
 * Cette classe permet de créer des connections à une base de données MYSQL
 */

package JDBC;

import java.net.UnknownHostException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author franklin
 */
public class ConnectionBD {
    String Driver="org.gjt.mm.mysql.Driver";
    String path;
    String user;
    String password;
    Connection connection=null;
    Statement statement=null;
    String dbname;
    boolean bool=false;
  //  Parametres parametres;
    public ConnectionBD(String pwd,String user,String chemin){
        password=pwd;
        this.user=user;
        path=chemin;
        try {
                Class.forName(Driver);  //chargement du pilote
        } catch (java.lang.ClassNotFoundException e) {
                System.err.print("ClassNotFoundException:   ");
                System.err.println(e.getMessage());
        }
        
            try {
                connection = DriverManager.getConnection(path,this.user, password);
                statement=connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
/*
    public ConnectionBD(){
        try {
            Class.forName(Driver);
            //System.out.println("pilote chargé");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException:   ");
            System.err.println(e.getMessage());
        }
        user="root";
        password="dlbhnadia";
        path="jdbc:mysql://localhost:3307/bdpeage";
        parametres=new Parametres(null,true,path,user,password);
        while(bool==false){
                try {
                    connection = DriverManager.getConnection(path, user, password);
                    //System.out.println("connection créé");
                    statement=connection.createStatement();
                    //System.out.println("statement créé");
                    bool=true;
                    //System.out.println("connection réussie");
                } catch (Exception ex) {
                    parametres.setVisible(true);
                    user=parametres.getUser();
                    path=parametres.getPath();
                    password=parametres.getPass();
                }
        }
    }*/


    public void closeConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *
     * @return Retourne l'objet de la classe java.sql.Connection que la classe aurait pu créer
     */
    public Connection getConnection(){
        return connection;
    }
    /**
     * 
     * @return Retourne l'instance de la classe java.sql.Statement que l'objet aurait créé
     */
    public Statement getStatement(){
        return statement;
    }
    
    public void setPath(String str){
        path=str;
    }
    public void setUser(String user){
        this.user=user;
    }
    public void setPassword(String pwd){
        password=pwd;
    }

}
