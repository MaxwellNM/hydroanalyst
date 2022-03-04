/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogEauBeton.java
 *
 * Created on 20 nov. 2014, 15:58:17
 */
package cpc.laboChimie.ui;

import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.EauCategorie;
import cpc.laboChimie.jpa.GroupClassEau;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.jpa.ZoneClimatique;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import java.awt.TextArea;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.jdesktop.application.Action;

/**
 *
 * @author hp
 */
public class JDialogEauBeton extends javax.swing.JDialog {
    private Iterable<TypeEau> list_typeEau;
    private long idEau;
    IMetier metier =null;
    private List<EauCategorie> list_categorie;
    private int j;
    private List<Norme> list_norme;
    private int n;
    private JLabel[] list_nom_Param;
    private String[] list_class_Param;
    private JLabel[] list_nom_parametre;
    private JComboBox[] list_typeRes_Param;
    private JTextField[] list_resultat_Param;
    private JComboBox[] list_unite_Param;
    private JComboBox[] list_norme_Param;
    private JPanel[] panel_parametre;
    private JScrollPane[] jpanel_parametre;
    private JComboBox jComboBoxAEtoile;
    private JComboBox jComboBoxNDEtoile;
    private JComboBox jComboBoxEauGroupe;
    private TextArea Conclusion;
    private ParametreGestion parametreGestion;
    private  JDialogAdmin jadmin;
    public   Client c;
    //private final Object jchef;
    private List<ZoneClimatique> list_zone;
    private  int nbreEchantillon;
    private  int nbreAnalyse;
    private List<GroupClassEau> lsgroup;
    private long  idTypEau;
    private long[] id_analyse_i;
    private List<Analyse> list_analyse;
    /** Creates new form JDialogEauBeton */
    public JDialogEauBeton(java.awt.Frame parent, boolean modal,JDialogAdmin admin) {
        super(parent, modal);
        initComponents();
        jadmin = admin;
              if(jadmin!=null){
      this.jTextField_IDclient.setText(""+jadmin.getClientToMasterTable().getId().longValue());
      this.c  =  jadmin.getClientToMasterTable();
              }else{
                  JOptionPane.showMessageDialog(null, "FENETRE ADMIN EST NULLE");
              }
//              }if(jchef!=null)
//          this.jTextField_IDclient.setText(""+jchef.getClientToMasterTable().getId().longValue());
//      
      reinit();
      
      jComboBox_Categrie_Eau.addItem("");
     jComboBoxNDEtoile = new JComboBox();
     jComboBoxAEtoile = new JComboBox();
     jComboBoxEauGroupe = new JComboBox();
     jComboBoxNDEtoile.addItem("");
     jComboBoxNDEtoile.addItem("ND*");
     jComboBoxAEtoile.addItem("");
     jComboBoxAEtoile.addItem("A*");
     jTextFieldNomClient.setText(c.getNomDemandeur());
    
     initSpring();
      
     
      
      Conclusion = new TextArea();
      Conclusion.setText("");
      nbreEchantillon = 0;
      nbreAnalyse = 0;

      InitGenreEau()  ;
    }
private void InitGenreEau(){
    
            for(TypeEau e : list_typeEau)
          {
              if(e.getCategorie().equalsIgnoreCase("eaux bétons"))
                 idEau = e.getId().longValue();//id du type d'eau
          }
          if(idEau!=0)
          {try{
            list_categorie = metier.getAllEauCategorie();
           }
           catch(Exception e)
           {
            e.printStackTrace();
           }
          //initialisation  à la chaîne vide
      jComboBox_Categrie_Eau.addItem("");
            
            for (EauCategorie ec : list_categorie)
            {
              if(ec!=null && (ec.getTypeEau().getId()==idEau))
                  jComboBox_Categrie_Eau.addItem(ec.getCategorie().toString());
            }
          }

       //if (j==1)
          { // Remplissage du JTabbedpane
               initJtabbedPane(idEau);
               //j++;
          }
          // else
        //désactive la Jcombox
        this.jComboBox_Type_Eau.setEnabled(false);
    return;

    
}

    public Eau getEauToInterface()
     { Eau e = new Eau();
        Client c = metier.getClientByID(Long.parseLong(jTextField_IDclient.getText()));
         System.out.println("client c = "+c);
         e.setClient(c);

         if(!jTextField_IDeau.getText().equals(""))
         {
            long id = Long.parseLong(jTextField_IDeau.getText());
            e.setId(id);
         }         
         if(!jTextField_Num_Glims.toString().equals(""))
            e.setNumEnrGlims(jTextField_Num_Glims.getText());

        if(!jTextField_Num_Labo.toString().equals(""))
          e.setNumEngLabo(jTextField_Num_Labo.getText());

        if((jComboBox_Type_Eau.getSelectedItem())!=null)
             e.setTypeEau(jComboBox_Type_Eau.getSelectedItem().toString());

        if(jComboBox_Paiement.getSelectedItem()!=null)
             e.setTypeDePayement(jComboBox_Paiement.getSelectedItem().toString());

        if(!jTextFieldLieuPrelevement.toString().equals(""))
             e.setLieuPrelevement(jTextFieldLieuPrelevement.getText());

        if(!jTextField_Condition_Conservement.toString().equals(""))
            e.setCondConserv(jTextField_Condition_Conservement.getText());

        if(!jTextField_Etiquette_Demandeur.toString().equals(""))
            e.setEtiquetageDemandeur(jTextField_Etiquette_Demandeur.getText());

        if(!jTextField_Num_Commande.toString().equals(""))
            e.setNumPaiement(jTextField_Num_Commande.getText());

        if(jComboBox_ZoneClimatique.getSelectedItem()!=null)
            e.setZoneClimatique(jComboBox_ZoneClimatique.getSelectedItem().toString());

        if(jComboBox_Categrie_Eau.getSelectedItem()!=null)
            e.seteauCategorie(jComboBox_Categrie_Eau.getSelectedItem().toString());

        if(jComboBox_Responsable.getSelectedItem()!=null)
            e.setResponssable(jComboBox_Responsable.getSelectedItem().toString());

        if(jComboBoxRealisePar.getSelectedItem()!=null)
           e.setPrelevePar(jComboBoxRealisePar.getSelectedItem().toString());
        //enregistrement des dates
         if(jXDatePicker_Effet_Labo.getDate()!=null)
           e.setDateEffetPaiement(jXDatePicker_Effet_Labo.getDate());
         
          if(jXDatePicker_Livraison.getDate()!=null)
           e.setDateLivraison(jXDatePicker_Livraison.getDate());
         
          if(jXDatePicker_Prelevement.getDate()!=null)
           e.setDatePrelevement(jXDatePicker_Prelevement.getDate());
           
          if(jXDatePicker_Recep_Labo.getDate()!=null)
            e.setDateReceptionLabo(jXDatePicker_Recep_Labo.getDate());

         /*on met le nombre de parametre et d'échantillon à 0
         *car les parametres ne sont pas encore enregistré
          */
          
            if(jComboBoxEauGroupe.getSelectedItem()!=null){
               
               //if( jComboBoxEauGroupeA1.getSelectedItem().toString().trim()=="EauxdugroupeA1")
                if(jComboBoxEauGroupe.getSelectedItem().toString()!="")
                e.setEauGroupe(jComboBoxEauGroupe.getSelectedItem().toString());
            
              /*  if( jComboBoxEauGroupeA1.getSelectedItem().toString().trim()=="EauxdugroupeA2")
                     e.setEauGroupeA2(jComboBoxEauGroupeA2.getSelectedItem().toString()+": Utilisation subordonnée à un traitement normal physique, chimique et à une désinfection");
            
                if(jComboBoxEauGroupeA1.getSelectedItem().toString().trim()=="EauxdugroupeA3")
                 e.setEauGroupeA3(jComboBoxEauGroupeA3.getSelectedItem().toString()+": Utilisation subordonnée à un traitement physique et chimique poussé, à des opérations d'affinage et de désinfection ");
           */
            }

            System.out.println("VALEURS DES JCOMBOX "+jComboBoxNDEtoile.getSelectedItem()+"   "+jComboBoxAEtoile.getSelectedItem());
            
            
            if(jComboBoxNDEtoile.getSelectedItem()!=null){
            if(jComboBoxNDEtoile.getSelectedItem().toString()!="")
                e.setNDEtoile(jComboBoxNDEtoile.getSelectedItem().toString()+": non détectable organoleptiquement");
            }
            if(jComboBoxAEtoile.getSelectedItem()!=null){
            
            if(jComboBoxAEtoile.getSelectedItem().toString()!="")
                e.setaEtoile(jComboBoxAEtoile.getSelectedItem().toString()+": l'eau ne devrait pas être agressive");
            }

          
            e.setNombreEchantillon(nbreEchantillon);
           
            e.setConclusion(Conclusion.getText());
      //      e.setEauGroupeA1();

         return e;
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

     this.jComboBox_Type_Eau.setSelectedItem(e.getTypeEau());

     this.jComboBox_ZoneClimatique.setSelectedItem(e.getZoneClimatique());
     this.jComboBox_Paiement.setSelectedItem(e.getTypeDePayement());
     this.jComboBox_Responsable.setSelectedItem(e.getResponssable());
     this.jComboBoxRealisePar.setSelectedItem(e.getPrelevePar());

     this.jXDatePicker_Effet_Labo.setDate(e.getDateEffetPaiement());
     this.jXDatePicker_Livraison.setDate(e.getDateLivraison());
     this.jXDatePicker_Recep_Labo.setDate(e.getDateReceptionLabo());
     this.jXDatePicker_Prelevement.setDate(e.getDatePrelevement());

     
     this.Conclusion.setText(e.getConclusion());
     this.jComboBoxEauGroupe.setSelectedItem(e.getEauGroupe());
   
  for(TypeEau ep : list_typeEau)
          {
              if(ep.getCategorie().equals(e.getTypeEau()))
              {  idEau = ep.getId().longValue();//id du type d'eau
                 System.out.println("Methode setEauToInterfaceid de l\'eau = "+idEau);
          }
          }
         if(idEau!=0)
          {try{
            list_categorie = metier.getAllEauCategorie();
           }
           catch(Exception er)
           {
            er.printStackTrace();
           }

            for (EauCategorie ec : list_categorie)
            {
              if(ec!=null && (ec.getTypeEau().getId()==idEau))
                  jComboBox_Categrie_Eau.addItem(ec.getCategorie().toString());
            }
          }
    
      this.jComboBox_Categrie_Eau.setSelectedItem(e.geteauCategorie());
    
     
     // Les Id
     this.jTextField_IDclient.setText(""+e.getClient().getId().longValue());
     this.jTextField_IDeau.setText(""+e.getId().longValue());
     /**
      *on remplit ici toutes les composants du tableau de Panel
      */
      //recoitt l'id du type d'eau
      
               for(TypeEau ep : list_typeEau)
          {    System.out.println("Méthode setEautoInterface type eau = "+ep.getCategorie());
                
              if(ep.getCategorie().equals(e.getTypeEau()))
                 
              { System.out.println("Méthode setEautoInterface type eau = "+e.getTypeEau());
                idTypEau = ep.getId().longValue();//id du type d'eau
                break;
              }
          }

           //initJtabbedPane(  idTypEau);
            this.jComboBoxAEtoile.setSelectedItem (e.getaEtoile());
            this.jComboBoxNDEtoile.setSelectedItem (e.getNDEtoile());
   
           
   /* this.jComboBoxEauGroupe.setSelectedItem(e.getEauGroupe());
    this.jComboBoxAEtoile.setSelectedItem (e.getaEtoile());
    this.jComboBoxNDEtoile.setSelectedItem (e.getNDEtoile());*/
           
     
           
           
           
           
           
           
           //On recupere toutes les analyses de l'eau

      list_analyse = metier.getAllAnalysesEau(e.getId().longValue());
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
        System.out.println("n = "+n+" \n na ="+na);
        
        if(na>n)
        {
          JOptionPane.showMessageDialog(null, "ERREUR LE NOMBRE D\'ANALYSES NE PEUT ETRE SUPERIEUR AU NOMBRE DE PARAMETRE DISPONIBLE POUR CETTE EAU");
          return;
        }
        
     for(int i=0;i<n;i++){// parcours  la liste de paramètres de ce type d'eau
      
         for(j=0;j<na;j++){//parcours la liste des analyses de ce type d'eau
         if(list_nom_Param[i].getText().equals(list_analyse.get(j).getAnalyseNomParam())&&(list_analyse.get(j).getAnalyseResult()!=""))
        {
          list_resultat_Param[i].setText(list_analyse.get(j).getAnalyseResult());
          list_unite_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseUniteParam());
          list_norme_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseCritereParam());
          list_typeRes_Param[i].setSelectedItem(list_analyse.get(j).getAnalyseTypeResultat());
           //Mémorise les id de chaque analyses
          id_analyse_i[j] = list_analyse.get(j).getId().longValue();
        System.out.println("METHODE SET_EAUTOINTERFACE IDANALYSE"+id_analyse_i[j]+" nom analyse "+list_analyse.get(j).getAnalyseNomParam());
        }
         }
     }


    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInfoEau = new javax.swing.JPanel();
        jLabelIdClient = new javax.swing.JLabel();
        jTextField_IDclient = new javax.swing.JTextField();
        jLabelIdEau = new javax.swing.JLabel();
        jTextField_IDeau = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Num_Glims = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
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
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton_Enregistrer = new javax.swing.JToggleButton();
        jToggleButton_Modifier = new javax.swing.JToggleButton();
        jToggleButton_Fermer = new javax.swing.JToggleButton();
        jLabel20 = new javax.swing.JLabel();
        jPanelAnalyses = new javax.swing.JPanel();
        jTabbedPaneAnalyses = new javax.swing.JTabbedPane();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNomClient = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JDialogEauBeton.class);
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

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextField_Num_Glims.setName("jTextField_Num_Glims"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

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

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jXDatePicker_Recep_Labo.setFocusable(false);
        jXDatePicker_Recep_Labo.setName("jXDatePicker_Recep_Labo"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jXDatePicker_Effet_Labo.setFocusable(false);
        jXDatePicker_Effet_Labo.setName("jXDatePicker_Effet_Labo"); // NOI18N

        jComboBox_ZoneClimatique.setName("jComboBox_ZoneClimatique"); // NOI18N

        jComboBox_Type_Eau.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "eaux bétons" }));
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

        jComboBox_Responsable.setEditable(true);
        jComboBox_Responsable.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Dr Marie Madeleine Gimou" }));
        jComboBox_Responsable.setName("jComboBox_Responsable"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        javax.swing.GroupLayout jPanelInfoEauLayout = new javax.swing.GroupLayout(jPanelInfoEau);
        jPanelInfoEau.setLayout(jPanelInfoEauLayout);
        jPanelInfoEauLayout.setHorizontalGroup(
            jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInfoEauLayout.createSequentialGroup()
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jTextField_IDclient, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelIdEau, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_IDeau, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_Num_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(jTextField_Num_Glims, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldLieuPrelevement, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelInfoEauLayout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_Etiquette_Demandeur))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelInfoEauLayout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField_Condition_Conservement, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jXDatePicker_Recep_Labo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBox_ZoneClimatique, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_Num_Commande, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jXDatePicker_Effet_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                        .addComponent(jXDatePicker_Livraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox_Responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jComboBox_Type_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_Paiement, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jXDatePicker_Prelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxRealisePar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_Categrie_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(105, Short.MAX_VALUE))
            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelIdClient)))
        );
        jPanelInfoEauLayout.setVerticalGroup(
            jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelIdEau, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_Num_Glims, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_IDeau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_Num_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_IDclient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_Paiement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox_Type_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox_ZoneClimatique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Num_Commande, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Etiquette_Demandeur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker_Recep_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Condition_Conservement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker_Effet_Labo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jXDatePicker_Livraison, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Responsable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldLieuPrelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker_Prelevement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxRealisePar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jComboBox_Categrie_Eau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
            .addGroup(jPanelInfoEauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelInfoEauLayout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jLabelIdClient, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(171, Short.MAX_VALUE)))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Action  Eau"));
        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getActionMap(JDialogEauBeton.class, this);
        jToggleButton_Enregistrer.setAction(actionMap.get("enregistrerEau")); // NOI18N
        jToggleButton_Enregistrer.setText(resourceMap.getString("jToggleButton_Enregistrer.text")); // NOI18N
        jToggleButton_Enregistrer.setName("jToggleButton_Enregistrer"); // NOI18N

        jToggleButton_Modifier.setAction(actionMap.get("modifierEau")); // NOI18N
        jToggleButton_Modifier.setText(resourceMap.getString("jToggleButton_Modifier.text")); // NOI18N
        jToggleButton_Modifier.setName("jToggleButton_Modifier"); // NOI18N

        jToggleButton_Fermer.setAction(actionMap.get("fermerEau")); // NOI18N
        jToggleButton_Fermer.setText(resourceMap.getString("jToggleButton_Fermer.text")); // NOI18N
        jToggleButton_Fermer.setName("jToggleButton_Fermer"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jToggleButton_Enregistrer)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton_Modifier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton_Fermer)
                .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButton_Enregistrer)
                        .addComponent(jToggleButton_Modifier)
                        .addComponent(jToggleButton_Fermer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelAnalyses.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Infos Analyses"));
        jPanelAnalyses.setName("jPanelAnalyses"); // NOI18N

        jTabbedPaneAnalyses.setName("jTabbedPaneAnalyses"); // NOI18N

        javax.swing.GroupLayout jPanelAnalysesLayout = new javax.swing.GroupLayout(jPanelAnalyses);
        jPanelAnalyses.setLayout(jPanelAnalysesLayout);
        jPanelAnalysesLayout.setHorizontalGroup(
            jPanelAnalysesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneAnalyses, javax.swing.GroupLayout.PREFERRED_SIZE, 1114, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanelAnalysesLayout.setVerticalGroup(
            jPanelAnalysesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneAnalyses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldNomClient.setEditable(false);
        jTextFieldNomClient.setFont(resourceMap.getFont("jTextFieldNomClient.font")); // NOI18N
        jTextFieldNomClient.setText(resourceMap.getString("jTextFieldNomClient.text")); // NOI18N
        jTextFieldNomClient.setName("jTextFieldNomClient"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNomClient, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelInfoEau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelAnalyses, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1132, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNomClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelInfoEau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanelAnalyses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
  
    private void reinit() {
       // throw new UnsupportedOperationException("Not yet implemented");
    this.jTextFieldLieuPrelevement.setText("");
     this.jTextField_Condition_Conservement.setText("");
     this.jTextField_Etiquette_Demandeur.setText("");
     this.jTextField_Num_Commande.setText("");
     this.jTextField_Num_Glims.setText("");
     this.jTextField_Num_Labo.setText("");

    }
    
private void jTextField_IDclientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDclientActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jTextField_IDclientActionPerformed

private void jComboBox_Categrie_EauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Categrie_EauActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jComboBox_Categrie_EauActionPerformed

   public JTextField getIdEau()
    {
       return jTextField_IDeau;
     }
    
    public JTextField getjtexFieldidClient()
    {
       return jTextField_IDclient;
     }

    
    public JLabel getJlabelIdClientEau()
    {
       return jLabelIdClient;
    }
    
    public JLabel getJlabelidEau()
    {
       return jLabelIdEau;
    }
    
    private void initSpring() {
       parametreGestion = ParametreGestion.getInstance();
       metier = parametreGestion.getMetier();
      list_typeEau =  metier.getAllTypeEau();
      list_zone = metier.getAllZoneClim();
      lsgroup  = metier.getAllGroupClass();
      
      //list_categorie = metier.getAllEauCategorie(WIDTH)
       //Remplissage des Jcombobox
      if(list_typeEau==null)
          return;
      else
         System.out.println("Remlissage de la JComboBox");

   for( TypeEau t  : list_typeEau)
      { 
          if (t!=null)
      jComboBox_Type_Eau.addItem(t.getCategorie());
              //System.out.println(" categorie elem "+t.getCategorie());
          else
              System.out.println("Erreur pas de TypeEau");
      }
   
    jComboBox_ZoneClimatique.addItem("");
     
         for( ZoneClimatique z  : list_zone)
      {
          if (z!=null)
      jComboBox_ZoneClimatique.addItem(z.getZoneClimatique());
              //System.out.println(" categorie elem "+t.getCategorie());
          else
              System.out.println("Erreur pas de Zone Climatique");
      }

      if(lsgroup!=null)
       for(GroupClassEau g:lsgroup )
       {
       jComboBoxEauGroupe.addItem(g.getnouvelleclasseGroupEau());
     
       
       }
       
      
     
    }


    
   public JComboBox getJComboBoxTypeEau()
    {
      return jComboBox_Type_Eau;
    }

    public JToggleButton getjToggleButtonEnregistrer() {
        return jToggleButton_Enregistrer;
    }

    public void setjToggleButtonEnregistrer(JToggleButton jToggleButtonEnregistrer) {
        this.jToggleButton_Enregistrer = jToggleButtonEnregistrer;
    }

    public JToggleButton getjToggleButtonFermer() {
        return jToggleButton_Fermer;
    }

    public void setjToggleButtonFermer(JToggleButton jToggleButtonFermer) {
        this.jToggleButton_Fermer = jToggleButtonFermer;
    }

    public JToggleButton getjToggleButtonModifier() {
        return jToggleButton_Modifier;
    }

    public void setjToggleButtonModifier(JToggleButton jToggleButtonModifier) {
        this.jToggleButton_Modifier = jToggleButtonModifier;
    }


private void jComboBox_Type_EauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_Type_EauMouseClicked
        // TODO add your handling code here:
        //Actuve les éléments de l'interface : les paramètres

//System.out.println("un element a ete selectionné");
      
        Object o = evt.getSource();

        if(o instanceof JComboBox)
        { JComboBox jb = (JComboBox) evt.getSource();
          System.out.println("un element a ete selectionné");
          JOptionPane.showMessageDialog(null,jb.getSelectedItem().toString());


         // jComboBox_Categrie_Eau.removeAllItems();
          for(TypeEau e : list_typeEau)
          {
              if(e.getCategorie()==jb.getSelectedItem().toString())
                 idEau = e.getId().longValue();//id du type d'eau
          }
          if(idEau!=0)
          {try{
            list_categorie = metier.getAllEauCategorie();
           }
           catch(Exception e)
           {
            e.printStackTrace();
           }
          //initialisation  à la chaîne vide
      jComboBox_Categrie_Eau.addItem("");
            
            for (EauCategorie ec : list_categorie)
            {
              if(ec!=null && (ec.getTypeEau().getId()==idEau))
                  jComboBox_Categrie_Eau.addItem(ec.getCategorie().toString());
            }
          }

       if (j==1)
          { // Remplissage du JTabbedpane
               initJtabbedPane(idEau);
               j++;
          }
           else
         return;

        }
        //désactive la Jcombox
        this.jComboBox_Type_Eau.setEnabled(false);
       // this.jComboBox_Type_Eau.setVisible(false);    
}//GEN-LAST:event_jComboBox_Type_EauMouseClicked
public void initJtabbedPane(long idtypEau)
{   //appel la couche métier : Recupere les éléments de la table des normes
          list_norme = metier.getParametreEau(idtypEau);
            n = list_norme.size();
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
                     //panel_parametre[pos].add(list_typeRes_Param[jpos]);

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
        

      
      
          
      // gestion des eaux bétons
     this.jComboBoxAEtoile.setBounds(200,20 ,200 ,20 );
     this.jComboBoxNDEtoile.setBounds(450,20 ,200 ,20 );
     this.jComboBoxEauGroupe.setBounds(550,50 ,400 ,20 );
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
    jTabbedPaneAnalyses.addTab("<html><i>saisi fin </i></html>", null,lastPanel, "info bulle");

}

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
            java.util.logging.Logger.getLogger(JDialogEauBeton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogEauBeton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogEauBeton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogEauBeton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogEauBeton dialog = new JDialogEauBeton(new javax.swing.JFrame(), true,null);
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

    @Action
 public void enregistrerEau() {
       //enregistrement des entêtes de l'eau
         
         if(getEauToInterface()==null)
         { JOptionPane.showMessageDialog(null,"VEUILLLEZ REMPLIR LES CHAMPS S\'IL VOUS PLAIT");
           return;
         }
      Eau e = getEauToInterface();
      System.out.println("\n\nENREGISTREMENT DANS LA BASE DE DONNEE.....");
      /*phase de controle*/
         Date dPrelev, dRecepLab, dLivraison,dEffetLab;
         dPrelev = jXDatePicker_Prelevement.getDate();
         dRecepLab = jXDatePicker_Recep_Labo.getDate();
         dLivraison = jXDatePicker_Livraison.getDate();
         dEffetLab = jXDatePicker_Effet_Labo.getDate();
         if((!jTextFieldLieuPrelevement.getText().equals("")) && (!jTextField_Num_Labo.getText().equals("")))
         {
             if(/*(dPrelev.compareTo(dEffetLab)>0)||(dEffetLab.compareTo(dRecepLab)>0)||*/(dRecepLab.compareTo(dLivraison)>0) )
             {
                JOptionPane.showMessageDialog(null,"VEUILLLEZ REMPLIR CORRECTEMENT LES DATES  S\'IL VOUS PLAIT");
                return;
             }
             //Vérifier si l'eau  n'a pas ce numéro d'hydrologie
             
             if(metier.getEauByNumHydro(jTextField_Num_Labo.getText())!=null)
              {
                    JOptionPane.showMessageDialog(null,"VEUILLLEZ SAISIR UN NUMERO DE lABORATOIRE DIFFERENT S\'IL VOUS PLAIT CETTE ANALYSE EXISTE DEJA");
                    return;
             
              }
             metier.saveEau(e); //pas encore 2 conclusion enregistrée
         //voyons le pb temporel: on récupère l'eau que l'on vient d'enregistrer
           Eau ep = metier.getEauByNumHydro(jTextField_Num_Labo.getText());
           //  System.out.println("eau enregistrée e ="+ep.getNumEngLabo());
      //enregistrement des analyses a partir dde l'id de  l'eau et du numéro hydrologie

         // n prend la taille de la liste dans seteauinterface
             int k=0;
     for(int i=0;i<n;i++){
           
        if(!list_resultat_Param[i].getText().equals(""))
        {
           /* if(!getValueToJtext(list_resultat_Param[i]).isNaN())
                    {
                      JOptionPane.showMessageDialog(null, "VEUILEZ SAISIR UNE VALEUR REELLE");
                      return;
                    }
           */
            
            Analyse ani = new Analyse();
            ani.setEauAnalyse(ep);
            ani.setAnalyseNomParam(list_nom_Param[i].getText());
            
            // contraintes sur les types
            
            ani.setAnalyseResult(list_resultat_Param[i].getText());
            ani.setAnalyseTypeResultat(list_typeRes_Param[i].getSelectedItem().toString());
            
            ani.setAnalyseUniteParam(list_unite_Param[i].getSelectedItem().toString());
            ani.setAnalyseCritereParam(list_norme_Param[i].getSelectedItem().toString());
            
           // System.out.println("METHODE ENREGISTRER classe = "+metier.getParametre(list_nom_Param[i].getText()).getClasseParam());metier.getParametre(list_nom_Param[i].getText()).getClasseParam()
            ani.setAnalyseClasseParam(list_class_Param[i]);
            //ani.setAnalyseClasseParam(metier.getParametre(list_nom_Param[i].getText()).getClasseParam());
            
            //Vérifier le type de résultat
            
            k++;
            
            ep.setNombreEchantillon(e.getNombreEchantillon()+1);
            ani.setEauAnalyse(e);// une eau à plusieurs analyses
            
            //enregistrement 
            metier.saveAnalyse(ani);
           
            
        }
         
     }
            if(Conclusion!=null)
                ep.setConclusion(Conclusion.getText());
     
            // mettre  à jour les eaux
     JOptionPane.showMessageDialog(null, "Nombre d'analyses ="+k);
        metier.updateEau(ep);
             
        System.out.println("NOMBRE DE PARAMETRES DE L\'EAU ="+n);
     
      //something here

    System.out.println("\n\n..... FIN ENREGISTREMENT DANS LA BASE DE DONNEE");
    //raffraichir la mémoire
    if(jadmin!=null)
    {jadmin.tableModelEaux.removeAll();
            jadmin.tableModelEaux.addRows(metier.getEauByClient(jadmin.getClientToMasterTable()));
    }
    fermerEau();
         }
         else
         {
             JOptionPane.showMessageDialog(null,"VEUILLLEZ REMPLIR LES CHAMPS OBLIGATOIRES \n S\'IL VOUS PLAIT");
         }

    
    }

    @Action
    public void modifierEau() {
      //recupere l'eau de l'interface et enregistre
        Eau e = getEauToInterface();
        //modifications de l'eau
       // System.out.println("ID EAU = "+e.getId().longValue());
        
        //Eau ep =metier.getEauByID(e.getId().longValue());
        
      /*
        if(metier.getEauByNumHydro(jTextField_Num_Labo.getText())!=null)
              {
               JOptionPane.showMessageDialog(null,"VEUILLLEZ SAISIR UN NUMERO DE lABORATOIRE DIFFERENT S\'IL VOUS PLAIT");
             return;
             
              }
        */     
        if(e==null)
          {
            System.out.println("Eau "+e.getTypeEau()+"Pas modifie");
            JOptionPane.showMessageDialog(null, "PAS D\'EAU REFERENCE");
            return;
          }
        else
            
        //Modifications des analyses
        e.setNombreEchantillon(0);
        int k=0;
        int creeanalyse =1;
        for(int i=0;i<n;i++)
          System.out.print(" "+id_analyse_i[i]);
            System.out.println("");
                    
        
          for(int i=0;i<n;i++){
           
         if(list_resultat_Param[i].getText().toString().trim().equals(""))     
         {  System.out.println("Pas d\'analyses modifiée" );
                    
         }
         else //Compte le nombre d'échantillon
        {  System.out.println("resultat R = "+list_resultat_Param[i].getText());
          /*    
            //Analyse ani = new Analyse();
            if(id_analyse_i[i]!=0)
            {
                 Analyse ani = metier.getAnalyseById(id_analyse_i[i]);
                            System.out.println("ID de l\'analyse "+id_analyse_i[i]);
               
            ani.setEauAnalyse(e);
            ani.setAnalyseNomParam(list_nom_Param[i].getText());
            ani.setAnalyseResult(list_resultat_Param[i].getText());
            ani.setAnalyseUniteParam(list_unite_Param[i].getSelectedItem().toString());
            ani.setAnalyseCritereParam(list_norme_Param[i].getSelectedItem().toString());
            ani.setAnalyseClasseParam(metier.getParametre(list_nom_Param[i].getText()).getClasseParam());
           // ani.setId(id_analyse_i[i]);
            //Vérifier le type de résultat
            e.setNombreEchantillon(e.getNombreEchantillon()+1);
            k++;
             //enregistrement 
            metier.updateAnalyse(ani);
            
            }
            else//cree une nouvelle analyse
            {
              Analyse ani1 = new Analyse();
               ani1.setEauAnalyse(e);
            ani1.setAnalyseNomParam(list_nom_Param[i].getText());
            ani1.setAnalyseResult(list_resultat_Param[i].getText());
            ani1.setAnalyseUniteParam(list_unite_Param[i].getSelectedItem().toString());
            ani1.setAnalyseCritereParam(list_norme_Param[i].getSelectedItem().toString());
            ani1.setAnalyseClasseParam(metier.getParametre(list_nom_Param[i].getText()).getClasseParam());
           // ani.setId(id_analyse_i[i]);
            //Vérifier le type de résultat
            e.setNombreEchantillon(e.getNombreEchantillon()+1);
            k++;
            
            metier.saveAnalyse(ani1);
              
            }
        
            */
            
                //parcours de la liste d'analyses de l'eau à la recherche de la référence de l'analyse
        
            for(Analyse a: list_analyse)
            {  if(list_nom_Param[i].getText().equals(a.getAnalyseNomParam()))
               {
                    //modification de l'analyse
                    
            a.setEauAnalyse(e);
            a.setAnalyseNomParam(list_nom_Param[i].getText());
            a.setAnalyseResult(list_resultat_Param[i].getText());
            a.setAnalyseUniteParam(list_unite_Param[i].getSelectedItem().toString());
            a.setAnalyseCritereParam(list_norme_Param[i].getSelectedItem().toString());
            a.setAnalyseTypeResultat(list_typeRes_Param[i].getSelectedItem().toString());
//metier.getParametre(list_nom_Param[i].getText()).getClasseParam()
            
            if(a.getAnalyseNomParam().equals("Nitrites"))
                System.out.println("classe de Nitrites ="+list_class_Param[i]);
            a.setAnalyseClasseParam(list_class_Param[i]);
           // ani.setId(id_analyse_i[i]);
            //Vérifier le type de résultat
            e.setNombreEchantillon(e.getNombreEchantillon()+1);
            k++;
             //enregistrement 
            metier.updateAnalyse(a);
            creeanalyse=0;
             break;
               }
             else
               creeanalyse=1;
                 
            }
           //Nouvelle analyse la crée
            System.out.println("Cree ANALYSE = "+creeanalyse);
            if(creeanalyse!=0)
            {   //continue;
            
            System.out.println(" valeur de i = "+i);
            Analyse ani1 = new Analyse();
            ani1.setEauAnalyse(e);
            ani1.setAnalyseNomParam(list_nom_Param[i].getText());
            ani1.setAnalyseResult(list_resultat_Param[i].getText());
            ani1.setAnalyseUniteParam(list_unite_Param[i].getSelectedItem().toString());
            ani1.setAnalyseCritereParam(list_norme_Param[i].getSelectedItem().toString());
            ani1.setAnalyseTypeResultat(list_typeRes_Param[i].getSelectedItem().toString());
           
            ani1.setAnalyseClasseParam(metier.getParametre(list_nom_Param[i].getText()).getClasseParam());
           // ani.setId(id_analyse_i[i]);
            //Vérifier le type de résultat
            e.setNombreEchantillon(e.getNombreEchantillon()+1);
            k++;
            
            metier.saveAnalyse(ani1);
         }
        
        }
         
     }
           JOptionPane.showMessageDialog(null, "Nombre d'analyses ="+k);
        metier.updateEau(e);
        if (jComboBox_Responsable.getSelectedItem()!=null)
            System.out.println("MODIFICATIONS");

         fermerEau();
         System.out.println("FIN MODIFICATION..................");
    }

    @Action
    public void fermerEau() {

        reinit();
        this.setVisible(false);
         if(jadmin!=null)
          jadmin.setjDialogEau(null);
//         if(jchef!=null)
//             jchef.setjDialogEau(null);
//        System.out.println(" Au revoir Fenetre Eau");
          
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelIdClient;
    private javax.swing.JLabel jLabelIdEau;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAnalyses;
    private javax.swing.JPanel jPanelInfoEau;
    private javax.swing.JTabbedPane jTabbedPaneAnalyses;
    private javax.swing.JTextField jTextFieldLieuPrelevement;
    private javax.swing.JTextField jTextFieldNomClient;
    private javax.swing.JTextField jTextField_Condition_Conservement;
    private javax.swing.JTextField jTextField_Etiquette_Demandeur;
    private javax.swing.JTextField jTextField_IDclient;
    private javax.swing.JTextField jTextField_IDeau;
    private javax.swing.JTextField jTextField_Num_Commande;
    private javax.swing.JTextField jTextField_Num_Glims;
    private javax.swing.JTextField jTextField_Num_Labo;
    private javax.swing.JToggleButton jToggleButton_Enregistrer;
    private javax.swing.JToggleButton jToggleButton_Fermer;
    private javax.swing.JToggleButton jToggleButton_Modifier;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Effet_Labo;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Livraison;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Prelevement;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker_Recep_Labo;
    // End of variables declaration//GEN-END:variables
}
