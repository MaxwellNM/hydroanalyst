/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import java.util.regex.*;
/**
 *
 * @author MAXWELL
 */
public class Importerbd {
    
 private  Connection con = null;
 private BDConnect bd;
 private Statement st;
    /*
     *Importer un objet de type connectiopn pour etablir les connexions aavec la bd
     * 
     * 
     */
    private IMetier metier;
    private ParametreGestion parametreGestion;
    private ResultSet RS;
    private Statement st2;
    private Statement st3;
    private Statement st1;
    private Pattern pattern;
    private Matcher matcher;
    
    
    public Importerbd(BDConnect conb)
      {
      //Enter les éléments du champ de connection
          bd = conb;
          con = conb.getConnect();
          if(con!=null)
          {
             JOptionPane.showMessageDialog(null, "CONNECTER");
          }
          
           
                //appel de la couche métier
                initSpring();
                importer();
      }


        //  Ici ajoute les méthodes 

    public IMetier getMetier() {
        return metier;
    }

            private void initSpring() {

        parametreGestion = ParametreGestion.getInstance();
        metier = parametreGestion.getMetier();
    
            } 

public void importer()
{  int nbreCols;
   String req; 
   String[] tab = bd.getTables();
   String[] req1 = {"select *  from eauxpropres","select *  from eauxusees","select *  from eauxbeton"};
   Vector<Integer>  tid = new Vector();// list des id des clients
   int n;//nbre de client
   int j;//nbre de lignes par eau     
  Hashtable[] tableau ;//= new Hashtable[nbLign];
  
  List<Norme> list_eau_Propre = null;
  List<Norme> list_eau_Usee   = null;
  List<Norme> list_eau_Beton  = null;
  
  List<TypeEau> typeau;
  
  typeau = metier.getAllTypeEau();
  
  for(TypeEau t: typeau)
  { 
    if(t.getCategorie().equals("eaux propres"))
     {  
          long idtypEau = t.getId().longValue();
          list_eau_Propre = metier.getParametreEau(idtypEau);
     }
    
    if(t.getCategorie().equals("eaux bétons"))
     {  
          long idtypEau = t.getId().longValue();
          list_eau_Beton = metier.getParametreEau(idtypEau);
     }
    
     if(t.getCategorie().equals("eaux usées"))
     {  
          long idtypEau = t.getId().longValue();
          list_eau_Usee = metier.getParametreEau(idtypEau);
     }
    
  }       
          try  {
            st = con.createStatement();
            
        } catch (SQLException ex) {
   //         Logger.getLogger(Importerbd.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erreur Statement :\n"+ex);
        }
   
   req = "select count(*) as nblign from client";
        try {
             st.execute(req);
            if(st.getResultSet().next())
            { n = st.getResultSet().getInt("nblign");
              tableau = new Hashtable[n];
              System.out.println(" n = "+n);
              req = "select * from client";
              st.execute(req);
              ResultSet r = st.getResultSet();
            j=0;
            while(r.next())
            { 
                tid.add(j,new Integer(r.getInt("id")) );
                tableau[j] = new Hashtable();
                tableau[j].put("clientID", r.getInt("id"));
                tableau[j].put("nomDemandeur", r.getString("nomDemandeur"));
                tableau[j].put("telDemandeur", (r.getString("telDemandeur") == null) ? "" : r.getString("telDemandeur"));
                tableau[j].put("emailDemandeur", r.getString("emailDemandeur"));
                tableau[j].put("faxDemandeur", r.getString("faxDemandeur"));
                tableau[j].put("bp", r.getString("bp"));
                tableau[j].put("ville", r.getString("ville"));               
                tableau[j].put("nomPersContact", r.getString("nomPersContact"));
                tableau[j].put("telPersContact", r.getString("telPersContact")); // à revoir son fonctionnement
            /*    tableau[j].put("descriptionC", (r.getString("c.description") == null) ? "" : r.getString("c.description"));
                tableau[j].put("nbLignes", r.getInt("nbLignes"));*/
                System.out.println("j = "+tid.get(j).intValue());
                        j++;
            }
                 
     for(int i =0;i<tid.size();i++)
     {   //Enregistre le client dans la nouvelle bd
         Client bdmCli = new Client();
         
         
         bdmCli.setNomDemandeur(tableau[i].get("nomDemandeur").toString());
         bdmCli.setTelDemandeur(tableau[i].get("telDemandeur").toString());
         bdmCli.setEmailDemandeur(tableau[i].get("emailDemandeur").toString());
         bdmCli.setFaxDemandeur(tableau[i].get("faxDemandeur").toString());
         bdmCli.setBp(tableau[i].get("bp").toString());
         bdmCli.setVille(tableau[i].get("ville").toString());
         bdmCli.setNomPersContact(tableau[i].get("nomPersContact").toString());
         bdmCli.setNomPersContact(tableau[i].get("telPersContact").toString());
         
         //save dans la new base 
         metier.saveClient(bdmCli);
        /* synchronized({
         });*/
          try  {
            st = con.createStatement();
            st1 = con.createStatement();       
            st2 = con.createStatement();
            st3 = con.createStatement();
            
            
        } catch (SQLException ex) {
   //         Logger.getLogger(Importerbd.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erreur Statement :\n"+ex);
        }

       //gestion des eaux du client  i;
         Integer id = tid.get(i);
          
       for(int l=0;l<3;l++)
        {
           String cond = req1[l]+ " WHERE client_id ="+id.intValue();
       
          System.out.println("requete == "+cond);
       try {
                if(st.execute(cond))
                { //scond="";
                  //Récupère le Resultset
                    RS = st.getResultSet();
                    nbreCols = RS.getMetaData().getColumnCount();
                    //System.out.println(" nbre colones ="+nbreCols);
                    //qu'est ce qu'il faut retirer dans la bd
                    //recupère l'id du client
                    //string cli = Rs.
                    j = 0;
                    while(RS.next())
                     {   System.out.println("CLIENT "+tableau[i].get("nomDemandeur").toString()+" eau ="+RS.getString("condConserv")+"  "+RS.getString("lieuPrelevement"));
                         //pour chaque ligne d'eau
                        //crée une nouvelle eau dans ma base de donnée 
                          Eau bdmEau = new Eau();
                          
                         //1st step recupere les informations sur l'eau
                         
                          if(RS.getString("typeEau").equals("EauPropre"))
                           {  //TypeEau t = metier.getTypeEau("eauxpropres");
                              bdmEau.setTypeEau("eauxpropres");
                              
                           }
                          
                          if(RS.getString("typeEau").equals("EauUsee"))
                           {  //TypeEau t = metier.getTypeEau("eaux propres");
                              bdmEau.setTypeEau("eaux usées");
                              
                           }
                          if(RS.getString("typeEau").equals("EauBeton"))
                           {  //TypeEau t = metier.getTypeEau("eauxpropres");
                              bdmEau.setTypeEau("eauxpropres");
                              
                           }
                          
                          //référence l'id du client que tu viens d'ajoute dans la nouvelle bd 
                             Client c = metier.getClientByNom(tableau[i].get("nomDemandeur").toString()) ;
                             //
                             bdmEau.setClient(c);
                          
                             bdmEau.setDatePrelevement(RS.getDate("datePrelevement"));
                             bdmEau.setDateReceptionLabo(RS.getDate("dateReceptionLabo"));
                             bdmEau.setDateEffetPaiement(RS.getDate("dateEffetPaiement"));
                             bdmEau.setDateLivraison(RS.getDate("dateLivraison"));
                             bdmEau.setCondConserv(RS.getString("condConserv"));
                             bdmEau.setLieuPrelevement(RS.getString("lieuPrelevement"));
                             bdmEau.seteauCategorie(RS.getString("typeNatureEau"));
                             bdmEau.setNumEnrGlims(RS.getString("numEnrGlims"));
                             bdmEau.setNumEngLabo(RS.getString("numEngLabo"));
                             bdmEau.setZoneClimatique(RS.getString("zoneClimatique"));
                             bdmEau.setEtiquetageDemandeur(RS.getString("etiquetageDemandeur"));
                             bdmEau.setResponssable(RS.getString("responssable"));
                             bdmEau.setConclusion(RS.getString("conclusion"));
                             bdmEau.setTypeDePayement(RS.getString("typeDePayement"));
                             bdmEau.setNumPaiement(RS.getString("numPaiement"));
                             
                             
                          
                         //2nd step persister l'eau
                             metier.saveEau(bdmEau);
                             {};
                          
                     /*   System.out.println("INFO EAU :"+  bdmEau.getDatePrelevement()+"=>"+
                             bdmEau.getDateReceptionLabo()+"=>"+
                             bdmEau.getDateEffetPaiement()+"=>"+
                             bdmEau.getDateLivraison()+"=>"+
                             bdmEau.getCondConserv()+"=>"+
                             bdmEau.getLieuPrelevement()+"=>"+
                             bdmEau.geteauCategorie()+"=>"+
                             bdmEau.getNumEnrGlims()+"=>"+
                             bdmEau.getNumEngLabo()+"=>"
                             );
                        */
                             //recupere l'eau précédemment crée
                                Eau e = metier.getEauByNumHydro(bdmEau.getNumEngLabo()) ;
                        
                             //3rd step persister les analyses
                        
                                
                       if(e!=null)         
                             // recherche de tous les motifs des parametres de la nouvellebase dans la base précédente   
                        {    if(e.getTypeEau().equals("eaux propres") )
                           {  //TypeEau t = metier.getTypeEau("eauxpropres");
                              if(list_eau_Propre!=null)
                               {     int pos =0; //positione de l'occurence cherchée
                                     int d=0;
                                 for(Norme nor: list_eau_Propre )
                                   { 
                                       String motif = nor.getNormeNomParam().toLowerCase();
                                   for(int col=18;col<nbreCols;col++)
                                        {  //recherche de la colone qui contient le motif
                                            
                                            String input = RS.getString(col);
                                            if(input!=null)
                                             { pattern = Pattern.compile(motif);
                                               String st = input.toLowerCase();
                                               matcher = pattern.matcher(st) ;
                                               
                                               while(matcher.find()&& d!=1)
                                                { d++;
                                                  pos = col;                                                
                                                  System.out.println(" trouvé "+nor.getNormeNomParam()+" position = "+pos+" eaux propres");
                                                }
                                             }
                                            if(d!=0)
                                               break;
                                        }
                                   
                                   if(d!=0)
                                   {       //on a trouvé l'occurence de l'analyse
                                        Analyse a = new Analyse();
                                        
                                          a.setEauAnalyse(e);
                                          a.setAnalyseNomParam(nor.getNormeNomParam());
                                          a.setAnalyseClasseParam(nor.getParametre().getClasseParam());
                                          a.setAnalyseUniteParam(nor.getNormeUniteParam());
                                          a.setAnalyseResult(""+RS.getFloat(pos+1));
                                          a.setAnalyseCritereParam(RS.getString(pos+3));
                                          
                                          //sauvegarder l'analyse 
                                           metier.saveAnalyse(a);
                                   }
                                             
                                    }
                              
                               }
                           }
                          
                          if(e.getTypeEau().equals("eaux usées"))
                           {  //TypeEau t = metier.getTypeEau("eaux propres");
                                   if(list_eau_Usee!=null)
                               {     int pos =0; //positione de l'occurence cherchée
                                     int d=0;
                                 for(Norme nor: list_eau_Usee )
                                   {
                                       String motif = nor.getNormeNomParam().toLowerCase();
                                   for(int col=16;col<nbreCols;col++)
                                        {
                                           //recherche de la colone qui contient le motif
                                            
                                            String input = RS.getString(col);
                                            if(input!=null)
                                             { pattern = Pattern.compile(motif);
                                               String st = input.toLowerCase();
                                               matcher = pattern.matcher(st) ;
                                               
                                               while(matcher.find()&& d!=1)
                                                { d++;
                                                  pos = col;                                                
                                                    System.out.println(" trouvé "+nor.getNormeNomParam()+" position = "+pos+" eaux usées");
                                                
                                                }
                                             }
                                            if(d!=0)
                                               break;
                                        } 
                                   
                                        if(d!=0)
                                         { 
                                          //on a trouvé l'occurence de l'analyse
                                    
                                          Analyse a = new Analyse();
                                        
                                          a.setEauAnalyse(e);
                                          a.setAnalyseNomParam(nor.getNormeNomParam());
                                          a.setAnalyseClasseParam(nor.getParametre().getClasseParam());
                                          a.setAnalyseUniteParam(nor.getNormeUniteParam());
                                          a.setAnalyseResult(""+RS.getFloat(pos+1));
                                          a.setAnalyseCritereParam(RS.getString(pos+3));
                                          
                                          //sauvegarder l'analyse 
                                           metier.saveAnalyse(a);
                                   }
                                    }
                              
                               }
                           }
                          if(e.getTypeEau().equals("eaux bétons"))
                           {  //TypeEau t = metier.getTypeEau("eauxpropres");
                                    if(list_eau_Beton!=null)
                               {         int pos =0; //positione de l'occurence cherchée
                                         int d=0;
                                     
                                 for(Norme nor: list_eau_Beton )
                                   { 
                                           String motif = nor.getNormeNomParam().toLowerCase();
                                   
                                   for(int col=16;col<nbreCols;col++)
                                        {
                                          //recherche de la colone qui contient le motif
                                            
                                            String input = RS.getString(col);
                                            if(input!=null)
                                             { pattern = Pattern.compile(motif);
                                               String st = input.toLowerCase();
                                               matcher = pattern.matcher(st) ;
                                               
                                               while(matcher.find()&& d!=1)
                                                { d++;
                                                  pos = col;                                                
                                                    System.out.println(" trouvé "+nor.getNormeNomParam()+" position = "+pos+" eaux béétons");
                                                
                                                }
                                             }
                                            if(d!=0)
                                               break;
                                        }  
                                   
                                         
                                          //on a trouvé l'occurence de l'analyse
                                   
                                     if(d!=0)
                                   {   Analyse a = new Analyse();
                                        
                                          a.setEauAnalyse(e);
                                          a.setAnalyseNomParam(nor.getNormeNomParam());
                                          a.setAnalyseClasseParam(nor.getParametre().getClasseParam());
                                          a.setAnalyseUniteParam(nor.getNormeUniteParam());
                                          a.setAnalyseResult(""+RS.getFloat(pos+1));
                                          a.setAnalyseCritereParam(RS.getString(pos+3));
                                          
                                          //sauvegarder l'analyse 
                                           metier.saveAnalyse(a);
                                   }
                                    }
                              
                               }
                          
                           }
                          
                     }    
                           
                         
                       j++;
                     }
                    //RS.close();
                }
                
                
          
            } catch (SQLException ex) {
                //Logger.getLogger(Importerbd.class.getName()).log(Level.SEVERE, null, ex);
                   System.out.println("Erreur select eau "+l+":\n"+ex);
                   ex.printStackTrace();
         
            }
            //passe au prochain type d'eau :eau usee ou beton ....
        
        }// fin parcours d'eau du client
       
       
         //passe au cliennt suivant   
     }//fin parcours client
     
     //Actualiser la fenetre d'administration 
      
     
     
       st.close();//  fermeture de la connexion pour la sélection des clients de l'ancienne base.
         con.close(); 
            }
        } catch (SQLException ex) {
           // Logger.getLogger(Importerbd.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Erreur client :"+ex);
            ex.printStackTrace();
        }

   
   
   
}

}
