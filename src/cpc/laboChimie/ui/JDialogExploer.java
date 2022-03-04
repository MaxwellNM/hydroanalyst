/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogExploer.java
 *
 * Created on 15 déc. 2014, 12:05:20
 */
package cpc.laboChimie.ui;

import cpc.laboChimie.jiDBase.ManagerXML;
import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import java.awt.TextArea;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author hp
 */
public class JDialogExploer extends javax.swing.JDialog {
     
    IMetier metier =null;
    private Vector<Eau> ListEau= new Vector<Eau>();
    private Vector<Analyse> ListAnalyse= new Vector<Analyse>();
    private ManagerXML mngr;
    private int indexEauCourant;
    TextArea Conclusion;
    private JComboBox jComboBoxEauGroupe;
    private JComboBox jComboBoxAEtoile;
    private JComboBox jComboBoxNDEtoile;
    private Vector<Analyse> list_analyse;
    private long[] id_analyse_i;
    private ParametreGestion parametreGestion;
    //cardinalité de la Liste des normes
    private int n;
    private List<Norme> list_norme;
    private long idtypEau;
    /** Creates new form JDialogExploer */
    public JDialogExploer(java.awt.Frame parent, boolean modal, ManagerXML mg) {
        super(parent, modal);
        initComponents();
        mngr=mg;
        indexEauCourant=0;
        ListEau = mngr.getListEau();
        initSpring();
        Eau e;
        Conclusion = new TextArea(); 
     //jComboBox_Categrie_Eau.addItem("");
     jComboBoxNDEtoile = new JComboBox();
     jComboBoxAEtoile = new JComboBox();
     jComboBoxEauGroupe = new JComboBox();
     //jComboBoxNDEtoile.addItem("");
     //jComboBoxNDEtoile.addItem("ND*");
     //jComboBoxAEtoile.addItem("");
    
        
        if(ListEau.size()>0)
           
        {  e = ListEau.elementAt(indexEauCourant);
           idtypEau = metier.getTypeEau(e.getTypeEau()).getId();
                  
           setEauInterface(e);
        }
          
        else{
            JOptionPane.showMessageDialog(null, "LA LISTE DES EAUX N\'A PAS ETE IMPORTE");}
        
       list_norme = metier.getParametreEau(idtypEau);
       n = list_norme.size();
       //La conclusion du rapport
       
       // n= metier.getAllNorme().size();
    }

    private void initSpring() {
       parametreGestion = ParametreGestion.getInstance();
       metier = parametreGestion.getMetier();

      
 }    

public void reinitJTpane(){

    jTabbedPaneAnalyses.removeAll();
        
}
     
 public void initJtabbedPane(Eau e)
{   
    reinitJTpane();
    //appel la couche métier : Recupere les éléments de la table des normes

     JLabel[] list_nom_Param;
     String[] list_class_Param;
     
     JTextField[] list_resultat_Param;
     JComboBox[] list_typeRes_Param;
     JComboBox[] list_unite_Param;
     JComboBox[] list_norme_Param;
     JPanel[] panel_parametre;
     JLabel[] list_nom_parametre;
     JScrollPane[] jpanel_parametre;
    
    
   System.out.println("id de l\'eau "+idtypEau+" taille de la liste "+n);
            if(list_norme==null){
              System.out.println("AUCUN PARAMETRE DISPONIBLE");


                return;
       }
System.out.println("NOMBRE DE PARAMETRE = "+n);
   // list_parametre = list_parametre.iterator();

           for (Norme p : list_norme)
           {//  Object op = list.next();
             // if (list_parametre.get(j).getClass().equals("Parametre"))
              System.out.println("Parametre p = "+p.getNormeNomParam());
             // System.out.println("good");

          }

          //  remplir le JtabbedPane
          /*
           *Déclaration tableaux d'objets :
           * tab JLabel pour les nom des param
           * tab result pour les resultats
           * tab unite pour les unités
           * tab citere pour les criteres du parametre
           */
          //jTabbedPaneAnalyses

           list_nom_Param= new JLabel[n];
           list_class_Param = new String[n];
           
           list_nom_parametre = new JLabel[n];

           list_resultat_Param = new JTextField[n];

           list_typeRes_Param = new JComboBox[n];
           list_unite_Param = new JComboBox[n];

           list_norme_Param = new JComboBox[n];

           
           
           int l = 0;
          // int espace = 50;
           for(Norme n : list_norme)
           {  JLabel lab = new JLabel();
              lab.setText( n.getNormeNomParam());
             // lab.setBounds((10),( 30), 300, 20);
              list_nom_Param[l]= lab;
             // list_resultat_Param[j] = n.
               JTextField textAnalyse = new JTextField("");
             //  textAnalyse.setBounds((10+ 200) , ( 30+20), 100, 20);
              list_resultat_Param[l]= textAnalyse;
              JComboBox    combo = new JComboBox();
                       combo.addItem(n.getNormeUniteParam())      ;
                        combo.addItem("-");
                      
                       // combo.setBounds((10 + 2*200), ( 30+2*20), 100, 20);
            list_unite_Param[l]= combo;
 
             list_class_Param[l]=n.getParametre().getClasseParam();            
            JComboBox    combo2 = new JComboBox();
            // Spliter les classes pour insérer les critères
                       String[] st = n.getNormeCritereParam().split(";");
                       
                       for(int i=0;i<st.length;i++)
                        {
                           combo2.addItem(st[i]);
                        
                        }
                       combo2.addItem("-");
                       
                      // combo2.setBounds((10 + 3*200),( 30+3*20), 100, 20);
             list_norme_Param[l] = combo2;
             //list_norme_Param[l].setBounds(10, 80, 35, 20);


              l++;

           }
           /**
            *Recherche la liste des classes des parametres ci-dessus
            */
           
          // String[] classeParam;
           
           
         //La liste des normes de ce type d'eau  
         List list_classe = metier.getClasseParamTypeEau(idtypEau);
//         

         if(/*list_classe!=null&&tail!=0*/list_classe!=null)
         {
            for(int y=0;y<list_classe.size();y++ )
            {  System.out.println("classe "+y+" = "+list_classe.get(y).toString());
            }
         }
         else{
             System.out.println("La ligne est vide");
             JOptionPane.showMessageDialog(null," AUCUN PARAMETRE N\'A ENCORE ETE ENREGISTRE");
             return;
             }
         int tail = list_classe.size();
         System.out.println("La taille est "+tail+" Initialisation des composants du Jtabbedpane........");

              panel_parametre = new JPanel[tail];
              jpanel_parametre = new JScrollPane[tail];
                  int cpt=0;
              do
              {  JScrollPane jpan = new JScrollPane();
                  JPanel pan = new JPanel();
                 //ajoute le nom de la classedu parametre
                 JLabel nomParam = new JLabel("Nom du parametre");
                 //nomParam.setFont(Font);
                 JLabel resultatParam = new JLabel("Resultat");
                 JLabel uniteParam = new JLabel("Unite");
                 JLabel critParam = new JLabel("Norme");
                 JLabel typeresparam = new JLabel("Type");
                 JLabel nomclassParam = new JLabel(list_classe.get(cpt).toString());
                 pan.add(nomclassParam);

                   nomclassParam.setBounds(350,5, 200, 20);
                    nomParam.setBounds((10),20, 200, 20);
                    resultatParam.setBounds(215 , 20, 100, 20);
                    uniteParam.setBounds( 350, 20, 100, 20);
                     critParam.setBounds(455, 20, 100, 20);
                     typeresparam.setBounds(555, 20, 100, 20);
                     pan.add(nomParam);
                    pan.add(resultatParam);
                   pan.add(uniteParam);
                   pan.add(critParam);
                   //pan .add(typeresparam); 
                 //definir un gestionnaire de positionnement
                // pan.setLayout(new GridLayout(5,5));
                 jpan.add(pan);
                 panel_parametre[cpt] = pan;
                // jpanel_parametre[cpt].add(panel_parametre[cpt]);
                  cpt++;
              }
              while(cpt<tail);
System.out.println("Fin Initialisation des composants du Jtabbedpane................");

      //remplir tous les panels maintenant
 cpt = 0;

 int[] dep = new int[tail];//deplacement vertical dans les panels

          int jpos = 0;
             do
             {//recherche du Panel adequat ou mettre le champ_du_parametre
                 int pos = 0;
                 do
                 {if(panel_parametre[pos].getComponent(0).getClass().equals("JLabel"));
                  { JLabel text = (JLabel)panel_parametre[pos].getComponent(0);
                    String classe = text.getText();
                     //System.out.println("classe du parametre "+text.getText());
                   //verifier si la classe du parametre est comptatible avec le jpanel
                    //recupérer la classe du parametre à partir de la norme
                  if(list_norme.get(jpos).getParametre().getClasseParam().equals(classe))
                  {  dep[pos]++;
                      //ajoute les infos du parametre
                     panel_parametre[pos].setLayout(null);
                    list_nom_Param[jpos].setBounds((10),( 30+25*dep[pos]), 200, 25);
                     panel_parametre[pos].add(list_nom_Param[jpos]);
                    list_resultat_Param[jpos].setBounds(215 , ( 30+25*dep[pos]), 100, 25);
                     panel_parametre[pos].add(list_resultat_Param[jpos]);
                     list_unite_Param[jpos].setBounds( 350, ( 30+25*dep[pos]), 100, 25);
                     panel_parametre[pos].add(list_unite_Param[jpos]);
                     list_norme_Param[jpos].setBounds(455, ( 30+25*dep[pos]), 100, 25);
                     panel_parametre[pos].add(list_norme_Param[jpos]);
           
            JComboBox    combo2 = new JComboBox();
                     
           
           combo2.addItem("reel");
           combo2.addItem("reel<");
           combo2.addItem("reel>");
           combo2.addItem("entier");
           combo2.addItem("Chaine de caractere");
           list_typeRes_Param[jpos] = combo2;
                     list_typeRes_Param[jpos].setBounds(565, ( 30+25*dep[pos]), 100, 20);
      //               panel_parametre[pos].add(list_typeRes_Param[jpos]);

                  }
               }
                  //mettre dans le panel par defaut
                  pos++;
                 }
                while(pos<tail);
                 jpos++;
             }
             while(jpos<n);

//Component jtest = new JPanel();
for(int p=0;p<tail;p++)
 {  int t = p;
    jTabbedPaneAnalyses.addTab("<html><i>saisi n°"+ ++t +" </i></html>", null, panel_parametre[p], list_classe.get(p).toString());
}
//ajout du dernier Panel contenant la classe de l'eau et la conclusion

      JPanel lastPanel = new JPanel();
      JLabel classeEau = new JLabel("Classe/Groupe :");
      JLabel conclusNom = new JLabel("Conclusion :");
      String[] itemcombo = {"Eaux de \"classe 1A\"", "Eaux de \"classe 1B\"","Eaux de \"classe 2\"","Eaux de \"classe 3\"","Eaux \"Hors classe\""};
      JComboBox comboClass_Group = new JComboBox(itemcombo);
    //jComboBoxEauGroupeA2 = new JComboBox();
      //        jComboBoxEauGroupeA3 = new JComboBox()

      classeEau.setBounds(10,20,100, 20);
        

      
      
          
      // gestion des eaux propres
     this.jComboBoxAEtoile.setBounds(200,20 ,200 ,20 );
     this.jComboBoxNDEtoile.setBounds(450,20 ,200 ,20 );
     this.jComboBoxEauGroupe.setBounds(450,50 ,400 ,20 );
    // jComboBoxEauGroupeA2.setBounds(350,50 ,200 ,20 );
     //jComboBoxEauGroupeA3.setBounds(600,50 ,200 ,20 );
     
      conclusNom.setBounds(10,100, 100, 20);
      Conclusion.setBounds(10, 120, 700, 150);
      lastPanel.setSize(800, 500);
      lastPanel.setLayout(null);
      lastPanel.add(classeEau);
      //lastPanel.add(comboClass_Group);
      lastPanel.add(this.jComboBoxAEtoile);
      lastPanel.add(this.jComboBoxNDEtoile);
      lastPanel.add(this.jComboBoxEauGroupe);
    //  lastPanel.add(jComboBoxEauGroupeA2);
      //lastPanel.add(jComboBoxEauGroupeA3);
      
      
      
      
      lastPanel.add(conclusNom);
      lastPanel.add(Conclusion);

      list_analyse = mngr.getAllAnalysesEauXML(e); //metier.getAllAnalysesEau(e.getId().longValue());
     //On replace les analyses dans le Jtabbedpane
      // n le nombre de paramètres
         if(list_analyse==null)
      {
      JOptionPane.showMessageDialog(null, "PAS D\'ANALYSES POUR CETTE EAU");
         return;
      }
       
        int j;
        int   na=list_analyse.size();
        id_analyse_i = new long[n];
//        System.out.println("n = "+n+" \n na ="+na);
//        
//        if(na>n)
//        {
//          JOptionPane.showMessageDialog(null, "ERREUR LE NOMBRE D\'ANALYSES NE PEUT ETRE SUPERIEUR AU NOMBRE DE PARAMETRE DISPONIBLE POUR CETTE EAU");
//          return;
//        }
//        
     for(int i=0;i<n;i++){// parcours  la liste de paramètres de ce type d'eau
      
         for(j=0;j<na;j++){//parcours la liste des analyses de ce type d'eau
         if(list_nom_Param[i].getText().equals(list_analyse.get(j).getAnalyseNomParam())&&(list_analyse.get(j).getAnalyseResult()!=""))
        {
          list_resultat_Param[i].setText(list_analyse.get(j).getAnalyseResult());
          list_unite_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseUniteParam());
          list_norme_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseCritereParam());
          list_typeRes_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseTypeResultat());
           //Mémorise les id de chaque analyses
          //id_analyse_i[j] = list_analyse.get(j).getId().longValue();
        System.out.println("METHODE SET_EAUTOINTERFACE IDANALYSE"+id_analyse_i[j]+" nom analyse "+list_analyse.get(j).getAnalyseNomParam());
        }
         }
     }

      
      
    jTabbedPaneAnalyses.addTab("<html><i>saisi fin </i></html>", null,lastPanel, "info bulle");

}   
    void setEauInterface(Eau e) {
        //throw new UnsupportedOperationException("Not yet implemented");
     
           
        System.out.println("Eau e"+e);
     //on remplit d'abord les entetes :
     this.jTextFieldLieuPrelevement.setText(e.getLieuPrelevement());
     this.jTextField_Condition_Conservement.setText(e.getCondConserv());
     this.jTextField_Etiquette_Demandeur.setText(e.getEtiquetageDemandeur());
     this.jTextField_Num_Commande.setText(e.getNumPaiement());
     this.jTextField_Num_Glims.setText(e.getNumEnrGlims());
     this.jTextField_Num_Labo.setText(e.getNumEngLabo());
     jTextFieldNomClient.setText(e.getClient().getNomDemandeur());
     this.jComboBox_Type_Eau.setSelectedItem(e.getTypeEau());

     this.jComboBox_ZoneClimatique.setSelectedItem(e.getZoneClimatique());
     this.jComboBox_Paiement.setSelectedItem(e.getTypeDePayement());
     this.jComboBox_Responsable.setSelectedItem(e.getResponssable());
     this.jComboBoxRealisePar.setSelectedItem(e.getPrelevePar());
     this.jComboBox_Categrie_Eau.setSelectedItem(e.geteauCategorie());
     this.jComboBox_Type_Eau.setSelectedItem(e.getTypeEau());
     this.jComboBox_ZoneClimatique.setSelectedItem(e.getZoneClimatique());
     this.jXDatePicker_Effet_Labo.setDate(e.getDateEffetPaiement());
     this.jXDatePicker_Livraison.setDate(e.getDateLivraison());
     this.jXDatePicker_Recep_Labo.setDate(e.getDateReceptionLabo());
     this.jXDatePicker_Prelevement.setDate(e.getDatePrelevement());

     if (e.getConclusion()!=null)
     this.Conclusion.setText(e.getConclusion());
     jComboBoxEauGroupe.setSelectedItem(e.getEauGroupe());
   
//  for(TypeEau ep : list_typeEau)
//          {
//              if(ep.getCategorie().equals(e.getTypeEau()))
//              {  idEau = ep.getId().longValue();//id du type d'eau
//                 System.out.println("Methode setEauToInterfaceid de l\'eau = "+idEau);
//          }
//          }
//         if(idEau!=0)
//          {try{
//            list_categorie = metier.getAllEauCategorie();
//           }
//           catch(Exception er)
//           {
//            er.printStackTrace();
//           }
//
//            for (EauCategorie ec : list_categorie)
//            {
//              if(ec!=null && (ec.getTypeEau().getId()==idEau))
//                  jComboBox_Categrie_Eau.addItem(ec.getCategorie().toString());
//            }
//          }
//    
      this.jComboBox_Categrie_Eau.setSelectedItem(e.geteauCategorie());
    
     
     // Les Id
     this.jTextField_IDclient.setText(""+e.getClient().getId().longValue());
     this.jTextField_IDeau.setText(""+e.getId().longValue());
     /**
      *on remplit ici toutes les composants du tableau de Panel
      */
      //recoitt l'id du type d'eau
      
//               for(TypeEau ep : list_typeEau)
//          {    System.out.println("Méthode setEautoInterface type eau = "+ep.getCategorie());
//                
//              if(ep.getCategorie().equals(e.getTypeEau()))
//                 
//              { System.out.println("Méthode setEautoInterface type eau = "+e.getTypeEau());
//                idTypEau = ep.getId().longValue();//id du type d'eau
//                break;
//              }
//          }

           //initJtabbedPane(  idTypEau);
            this.jComboBoxAEtoile.setSelectedItem (e.getaEtoile());
            this.jComboBoxNDEtoile.setSelectedItem (e.getNDEtoile());
   
           
   /* this.jComboBoxEauGroupe.setSelectedItem(e.getEauGroupe());
    this.jComboBoxAEtoile.setSelectedItem (e.getaEtoile());
    this.jComboBoxNDEtoile.setSelectedItem (e.getNDEtoile());*/
           
     
           
           
           
           initJtabbedPane(e); 
           
           
           //On recupere toutes les analyses de l'eau

    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButtonREchercher = new javax.swing.JButton();
        jTextFieldNomClient = new javax.swing.JTextField();
        jPanelInfoEau = new javax.swing.JPanel();
        jLabelIdClient = new javax.swing.JLabel();
        jTextField_IDclient = new javax.swing.JTextField();
        jLabelIdEau = new javax.swing.JLabel();
        jTextField_IDeau = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Num_Glims = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Num_Labo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_Etiquette_Demandeur = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldLieuPrelevement = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jXDatePicker_Prelevement = new org.jdesktop.swingx.JXDatePicker();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxRealisePar = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jComboBox_Categrie_Eau = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jXDatePicker_Livraison = new org.jdesktop.swingx.JXDatePicker();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jXDatePicker_Recep_Labo = new org.jdesktop.swingx.JXDatePicker();
        jLabel12 = new javax.swing.JLabel();
        jXDatePicker_Effet_Labo = new org.jdesktop.swingx.JXDatePicker();
        jComboBox_ZoneClimatique = new javax.swing.JComboBox();
        jComboBox_Type_Eau = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBox_Paiement = new javax.swing.JComboBox();
        jTextField_Num_Commande = new javax.swing.JTextField();
        jTextField_Condition_Conservement = new javax.swing.JTextField();
        jComboBox_Responsable = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jPanelAnalyses = new javax.swing.JPanel();
        jTabbedPaneAnalyses = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToggleButtonPrecedent = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JDialogExploer.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jButtonREchercher.setText(resourceMap.getString("jButtonREchercher.text")); // NOI18N
        jButtonREchercher.setName("jButtonREchercher"); // NOI18N

        jTextFieldNomClient.setEditable(false);
        jTextFieldNomClient.setFont(resourceMap.getFont("jTextFieldNomClient.font")); // NOI18N
        jTextFieldNomClient.setText(resourceMap.getString("jTextFieldNomClient.text")); // NOI18N
        jTextFieldNomClient.setName("jTextFieldNomClient"); // NOI18N

        jPanelInfoEau.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), resourceMap.getString("jPanelInfoEau.border.title"))); // NOI18N
        jPanelInfoEau.setName("jPanelInfoEau"); // NOI18N

        jLabelIdClient.setText(resourceMap.getString("jLabelIdClient.text")); // NOI18N
        jLabelIdClient.setName("jLabelIdClient"); // NOI18N

        jTextField_IDclient.setEditable(false);
        jTextField_IDclient.setName("jTextField_IDclient"); // NOI18N
        jTextField_IDclient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IDclientActionPerformed(evt);
            }
        });

        jLabelIdEau.setText(resourceMap.getString("jLabelIdEau.text")); // NOI18N
        jLabelIdEau.setName("jLabelIdEau"); // NOI18N

        jTextField_IDeau.setEditable(false);
        jTextField_IDeau.setName("jTextField_IDeau"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextField_Num_Glims.setName("jTextField_Num_Glims"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextField_Num_Labo.setName("jTextField_Num_Labo"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextField_Etiquette_Demandeur.setName("jTextField_Etiquette_Demandeur"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextFieldLieuPrelevement.setName("jTextFieldLieuPrelevement"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jXDatePicker_Prelevement.setFocusable(false);
        jXDatePicker_Prelevement.setName("jXDatePicker_Prelevement"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jComboBoxRealisePar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "le demandeur le :", "le CPC le :" }));
        jComboBoxRealisePar.setName("jComboBoxRealisePar"); // NOI18N

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jComboBox_Categrie_Eau.setName("jComboBox_Categrie_Eau"); // NOI18N
        jComboBox_Categrie_Eau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_Categrie_EauActionPerformed(evt);
            }
        });

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jXDatePicker_Livraison.setFocusable(false);
        jXDatePicker_Livraison.setName("jXDatePicker_Livraison"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jXDatePicker_Recep_Labo.setFocusable(false);
        jXDatePicker_Recep_Labo.setName("jXDatePicker_Recep_Labo"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jXDatePicker_Effet_Labo.setFocusable(false);
        jXDatePicker_Effet_Labo.setName("jXDatePicker_Effet_Labo"); // NOI18N

        jComboBox_ZoneClimatique.setName("jComboBox_ZoneClimatique"); // NOI18N

        jComboBox_Type_Eau.setName("jComboBox_Type_Eau"); // NOI18N
        jComboBox_Type_Eau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_Type_EauMouseClicked(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jComboBox_Paiement.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Comptant", "Chèque", "Bon de Commande", "Dévis", "Convention avec le cpc" }));
        jComboBox_Paiement.setName("jComboBox_Paiement"); // NOI18N

        jTextField_Num_Commande.setName("jTextField_Num_Commande"); // NOI18N

        jTextField_Condition_Conservement.setName("jTextField_Condition_Conservement"); // NOI18N

        jComboBox_Responsable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dr Marie Madeleine Gimou" }));
        jComboBox_Responsable.setName("jComboBox_Responsable"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        javax.swing.GroupLayout jPanelInfoEauLayout = new javax.swing.GroupLayout(jPanelInfoEau);
        jPanelInfoEau.setLayout(jPanelInfoEauLayout);
        jPanelInfoEauLayout.setHorizontalGroup(
            jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_Etiquette_Demandeur)
                                    .addComponent(jTextField_Condition_Conservement, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                                .addGap(89, 89, 89))
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldLieuPrelevement, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(369, 369, 369)))
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker_Livraison, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_Responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoEauLayout.createSequentialGroup()
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jXDatePicker_Recep_Labo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jComboBox_ZoneClimatique, 0, 175, Short.MAX_VALUE))
                                    .addComponent(jComboBox_Type_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox_Paiement, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField_Num_Commande, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(18, 18, 18)
                                            .addComponent(jXDatePicker_Effet_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(52, 52, 52))))
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXDatePicker_Prelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxRealisePar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_Categrie_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(475, Short.MAX_VALUE))))
            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelIdClient)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jTextField_IDclient, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabelIdEau)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField_IDeau, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField_Num_Glims, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField_Num_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanelInfoEauLayout.setVerticalGroup(
            jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField_Etiquette_Demandeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel21)
                                .addComponent(jComboBox_ZoneClimatique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jComboBox_Paiement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox_Type_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addComponent(jTextField_Num_Commande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)))
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_Condition_Conservement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jXDatePicker_Recep_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXDatePicker_Effet_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGap(15, 15, 15)
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jTextFieldLieuPrelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(jComboBox_Responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jXDatePicker_Livraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addGap(18, 18, 18)
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRealisePar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker_Prelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jComboBox_Categrie_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelIdClient)
                            .addComponent(jLabelIdEau)
                            .addComponent(jTextField_IDeau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_Num_Glims, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_Num_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTextField_IDclient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(192, Short.MAX_VALUE)))
        );

        jPanelAnalyses.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), resourceMap.getString("jPanelAnalyses.border.title"))); // NOI18N
        jPanelAnalyses.setName("jPanelAnalyses"); // NOI18N

        jTabbedPaneAnalyses.setName("jTabbedPaneAnalyses"); // NOI18N

        javax.swing.GroupLayout jPanelAnalysesLayout = new javax.swing.GroupLayout(jPanelAnalyses);
        jPanelAnalyses.setLayout(jPanelAnalysesLayout);
        jPanelAnalysesLayout.setHorizontalGroup(
            jPanelAnalysesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAnalysesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneAnalyses, javax.swing.GroupLayout.DEFAULT_SIZE, 1198, Short.MAX_VALUE))
        );
        jPanelAnalysesLayout.setVerticalGroup(
            jPanelAnalysesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAnalysesLayout.createSequentialGroup()
                .addComponent(jTabbedPaneAnalyses, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jToggleButtonPrecedent.setFont(resourceMap.getFont("jToggleButtonPrecedent.font")); // NOI18N
        jToggleButtonPrecedent.setText(resourceMap.getString("jToggleButtonPrecedent.text")); // NOI18N
        jToggleButtonPrecedent.setName("jToggleButtonPrecedent"); // NOI18N
        jToggleButtonPrecedent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPrecedentActionPerformed(evt);
            }
        });

        jToggleButton1.setFont(resourceMap.getFont("jToggleButton1.font")); // NOI18N
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setName("jToggleButton1"); // NOI18N
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jToggleButtonPrecedent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 595, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addGap(207, 207, 207))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButtonPrecedent))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(192, 192, 192)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonREchercher))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3)
                        .addGap(50, 50, 50)
                        .addComponent(jTextFieldNomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelAnalyses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelInfoEau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonREchercher))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelInfoEau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelAnalyses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jTextField_IDclientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDclientActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField_IDclientActionPerformed

private void jComboBox_Categrie_EauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Categrie_EauActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBox_Categrie_EauActionPerformed

private void jComboBox_Type_EauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_Type_EauMouseClicked
        // TODO add your handling code here:
        //Actuve les éléments de l'interface : les paramètres
   
}//GEN-LAST:event_jComboBox_Type_EauMouseClicked

private void jToggleButtonPrecedentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonPrecedentActionPerformed
// TODO add your handling code here:
        if(indexEauCourant<0){
        indexEauCourant=ListEau.size()-1;
    }else
        indexEauCourant--;
    //reinitJTpane();
    Eau e = ListEau.elementAt(indexEauCourant);
    setEauInterface(e);

}//GEN-LAST:event_jToggleButtonPrecedentActionPerformed

private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
// TODO add your handling code here:
    if(indexEauCourant>=ListEau.size()){
        indexEauCourant=0;
    }else
        indexEauCourant++;
    //SreinitJTpane();
    Eau e = ListEau.elementAt(indexEauCourant);
    setEauInterface(e);
}//GEN-LAST:event_jToggleButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogExploer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogExploer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogExploer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogExploer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogExploer dialog = new JDialogExploer(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonREchercher;
    private javax.swing.JComboBox jComboBoxRealisePar;
    private javax.swing.JComboBox jComboBox_Categrie_Eau;
    private javax.swing.JComboBox jComboBox_Paiement;
    private javax.swing.JComboBox jComboBox_Responsable;
    private javax.swing.JComboBox jComboBox_Type_Eau;
    private javax.swing.JComboBox jComboBox_ZoneClimatique;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelIdClient;
    private javax.swing.JLabel jLabelIdEau;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAnalyses;
    private javax.swing.JPanel jPanelInfoEau;
    private javax.swing.JTabbedPane jTabbedPaneAnalyses;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldLieuPrelevement;
    private javax.swing.JTextField jTextFieldNomClient;
    private javax.swing.JTextField jTextField_Condition_Conservement;
    private javax.swing.JTextField jTextField_Etiquette_Demandeur;
    private javax.swing.JTextField jTextField_IDclient;
    private javax.swing.JTextField jTextField_IDeau;
    private javax.swing.JTextField jTextField_Num_Commande;
    private javax.swing.JTextField jTextField_Num_Glims;
    private javax.swing.JTextField jTextField_Num_Labo;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButtonPrecedent;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Effet_Labo;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Livraison;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Prelevement;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Recep_Labo;
    // End of variables declaration//GEN-END:variables
}
