/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogNormePa.java
 *
 * Created on 8 sept. 2011, 06:42:58
 */
package cpc.laboChimie.ui;


import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.jdesktop.application.Action;
import java.util.List;
/**
 *
 * @author HP
 */
public class JDialogNormePa extends javax.swing.JDialog {
//static JFrameParametres JFParam;
    ParametreGestion parametreGestion;
    private IMetier metier;
    private long idTypEau;
    private  JDialogFrameNormeParam jdf ;
    private  JFrameParametrefinal JFParamf;
    
    /** Creates new form JDialogNormePa */
  /*  public JDialogNormePa(java.awt.Frame parent, boolean modal,JFrameParametres jf, JDialogFrameNormeParam jDiagf) {
        super(parent, modal);
        this.JFParam = jf;
        this.jdf = jDiagf;
            if(this.JFParam !=null)
              this.idTypEau = this.JFParam.getIdEau();
       
        this.setTitle("NORME PARAMETRE");
        initSpring();
        initComponents();
        initInterface();  
     
        this.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                       // System.exit(0);
                        fermer();
                    }
                });
        
     }*/
    
    
       public JDialogNormePa(java.awt.Frame parent, boolean modal,JFrameParametrefinal jf, JDialogFrameNormeParam jDiagf) {
        super(parent, modal);
        this.JFParamf = jf;
        this.jdf = jDiagf;
            if(this.JFParamf !=null)
              this.idTypEau = this.JFParamf.getIdtypEau();
            else
                this.idTypEau = this.jdf.getidTypeEau();
       
        this.setTitle("MODIFICATION NORME");
        initSpring();
        initComponents();
        initInterface();  
     
        this.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                       // System.exit(0);
                        if(jdf!=null){
                           System.out.println("INTERFACE NORME FERMETURE................");
                           fermer2();
                    }
                        if(JFParamf!=null&&JFParamf.getCodeOperation()==-1)//ajout parametre
                        {
                            System.out.println("INTERFACE PARAMETRE FERMETURE................");
                            fermer();
                    }
                      
                    }
                });
        
     }
    
    private void initSpring() {
       parametreGestion = ParametreGestion.getInstance();
       metier = parametreGestion.getMetier();
    }
    
    private void fermer3()
      {
        this.setVisible(false);
     if(this.JFParamf!=null)
      { this.JFParamf.getTableModelNorme().removeAll();
      this.JFParamf.getTableModelNorme().addRows(metier.getParametreEau(idTypEau));
     // this.JFParam.getmasterTableParametre().set
      this.JFParamf.setJDialogNormeParametre(null);
      this.JFParamf.setVisible(true);
    }
   
      }
    private void fermer()
    {  System.out.println("INTERFACE PARAMETRE FERMETURE................");
                          
      this.setVisible(false);
      // vider la liste et la réemplir
      if(this.JFParamf!=null)
      { this.JFParamf.modelParametre.removeAll();
      this.JFParamf.modelParametre.addRows(metier.getAllParametre());
     // this.JFParam.getmasterTableParametre().set
      this.JFParamf.setJDialogNormeParametre(null);
       }
   //   System.out.println(" FENETRE INITIAL ETAT = "+this.JFParam.getJDialogNormeParametre());
    }
    
    private void fermer2()
    {     System.out.println("INTERFACE NORME FERMETURE................");
                          
         this.setVisible(false);
         this.jdf.TablemodelNoreme.removeAll();
         this.jdf.TablemodelNoreme.addRows(metier.getParametreEau(idTypEau));
         this.jdf.setJDialogNormeParametre(null);
    }
    
    
    JTextField getJtextfieldNomParam()
    {
      return jTextFieldNomParametre;
    }
    
    JTextField getjTextfieldClasseParam()
    {
     return jTextFieldClasseParametre;
    }

    JTextField getjTextfieldLOQ()
    {
     return jTextFieldLOQ;
    }
    
      JTextField getJTextField()
      {
        return jTextFieldTypeEau;
      
      }
      
      JTextField getjtextfieldUnite()
      {
        return jTextFieldUniteParametre;
      }
      
      JTextField getjtextfieldCritere()
      {
        return jTextFieldCritereParametre;
      }
      
      JTextField getjtextfieldMethode()
       {
         return jTextFieldMethodeNorme;
       }
      
      JTextField getjtextfieldReference()
      {
        return jTextFieldReferenceNorme;
      }
      
    JComboBox getjComboboxType()
    {
      return jComboBoxTypeRésultat;
    }
   
    JComboBox getjComboboxClasse()
    {
      return jComboBoxClasse;
    }
            
    JToggleButton getJToogleButtonAjouter() {
       // throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonAjouter;
    }

    JToggleButton getJtoogleButtonModifier() {
     //   throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonModifier;
    }
    
      JToggleButton getJtoogleButtonModifierFromNorme() {
     //   throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonmodifier;
    }
      
    
      
      
      
      
      JToggleButton getJtoogleButtonAjouterFromNorme() {
     //   throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonAjputerFromNorme;
    }
      

    @Action
    public void ModifierNorme() {//Modification d'une norme à partir de la liste des normes
        Norme n = this.jdf.getNormeTomasterTable();
        JOptionPane.showMessageDialog(null, "ID ="+n.getId()+" nom = "+n.getNormeNomParam());
        n.setNormeNomParam(jTextFieldNomParametre.getText());
            n.setNormeUniteParam(jTextFieldUniteParametre.getText());
            n.setNormeCritereParam(jTextFieldCritereParametre.getText());
            n.setNormeMethodeParam(jTextFieldMethodeNorme.getText());
            n.setNormeLoqParam(jTextFieldLOQ.getText());
            n.setNormeReferenceParam(jTextFieldReferenceNorme.getText());

            System.out.println(" MODIFICATION DE LA NORME...........");
               metier.updateNorme(n);
        fermer2();
    }
    
    void setParametreTointerface(Parametre p)
     {
         jTextFieldNomParametre.setText(p.getNomParam());
         jTextFieldClasseParametre.setText(p.getClasseParam());
         jComboBoxTypeRésultat.setSelectedItem(p.getTypeResult());
     }

    void setNormeTointerface(Norme n) {
      //  throw new UnsupportedOperationException("Not yet implemented");
         jTextFieldNomParametre.setText(n.getNormeNomParam());
      jTextFieldUniteParametre.setText(n.getNormeUniteParam());
      jTextFieldCritereParametre.setText(n.getNormeCritereParam());
      jTextFieldClasseParametre.setText(n.getParametre().getClasseParam());
      jTextFieldLOQ.setText(n.getNormeLoqParam());
      jTextFieldMethodeNorme.setText(n.getNormeMethodeParam());
      jTextFieldReferenceNorme.setText(n.getNormeReferenceParam());
      
        
    }

    @Action
    public void AjouterNormeFromParam() {
        
        //recupere le Parametre et associe le à la norme si le parametre existe déja et que la norme n'existe pas
        Parametre p = this.JFParamf.getParametreToMasterTable();
         if(p!=null)
         { Norme n = new Norme();
            //vérifie si la norme n'est pas dans ce type d'eau
              List<Norme> ls = metier.getParametreEau(idTypEau);
              
              if(ls==null)
               {
                 JOptionPane.showMessageDialog(null, "les normes de ce type d\'eau ne sont pas connu");
                 return;
               }
                 for(Norme np:ls)
                 {
                   if(np.getNormeNomParam().toLowerCase().equals(p.getNomParam().toLowerCase()))
                   {
                     JOptionPane.showMessageDialog(null, "Erreur cette norme existe déja");
                     return;
                   }
                 }
            n.setNormeNomParam(p.getNomParam());
            n.setNormeUniteParam(jTextFieldUniteParametre.getText());
            n.setNormeCritereParam(jTextFieldCritereParametre.getText());
            n.setNormeMethodeParam(jTextFieldMethodeNorme.getText());
            n.setNormeReferenceParam(jTextFieldReferenceNorme.getText());
            n.setNormeLoqParam(jTextFieldLOQ.getText());
            n.setTypeEau(metier.getTypeEau(idTypEau));
            n.setParametre(p);
            System.out.println(" JdialogNormePa: AJOUT DE LA NORME...........");
               metier.saveNorme(n);
               fermer();
         }
       /* else
         {
           AjouterNormeParametre();
         }*/
       // sinon appellé la méthode d'ajout normal
        
        System.out.println(".....................JdialogNormePa: FIN AJOUT DE LA NORME");
            
    }
    
    
    
    
    public JComboBox getJcomboboxClassParam()
     {
       return jComboBoxClasse;  
     }
    
    
   private void initInterface()
    {
      jTextFieldNomParametre.setText("");
      jTextFieldUniteParametre.setText("");
      jTextFieldCritereParametre.setText("");
      //jTextFieldClasseParametre.setText("");
      
      jTextFieldMethodeNorme.setText("");
      jTextFieldReferenceNorme.setText("");
      
      System.out.println("JFParam = "+this.JFParamf);
      if(this.JFParamf!=null )
         {jTextFieldTypeEau.setText(this.JFParamf.getNomTypeEau());
          this.idTypEau = this.JFParamf.getIdtypEau();
         }//jLabel1TypeEau.setVisible(true);
      else
      {
            jTextFieldTypeEau.setText(this.jdf.getNomTypEau());
            this.idTypEau = this.jdf.getidTypeEau();
      }
      
    }
    
    public Norme getParamNorme(Parametre p)
    {  Norme np;
        List<Norme> ls = metier.getParametreEau(idTypEau);    
       if(ls==null)
        {
          JOptionPane.showMessageDialog(null, "VEUILLEZ CREER LE PARAMETRE ET LUI ASSOCIER UNE NORME");
          return null;
        }
         else
        { 
           for(Norme n : ls)
            {
              if (n.getParametre()==p)
                 return n;
            }
          
        }  
        return null;
       
    }
    
    //méthode appellée par le père
    public int  setNormeParametreInterface(Parametre p)
    {             
        Norme np=getParamNorme(p);
      if(np==null)
        {
         JOptionPane.showMessageDialog(null, "VEUILLEZ CREER LA NORME DU PARAMETRE <html><i><b>"+p.getNomParam()+" </html></i></b>POUR LE TYPE D\'EAU"+this.JFParamf.getNomTypeEau());
            return -1 ; // probleme
        }
      jTextFieldNomParametre.setText(p.getNomParam());
      jTextFieldUniteParametre.setText(np.getNormeUniteParam());
      jTextFieldCritereParametre.setText(np.getNormeCritereParam());
      //jTextFieldClasseParametre.setText(p.getClasseParam());
      jTextFieldLOQ.setText(np.getNormeLoqParam());
      jTextFieldMethodeNorme.setText(np.getNormeMethodeParam());
      jTextFieldReferenceNorme.setText(np.getNormeReferenceParam());
      return 0;//tout ce bien passeé
    }
    
    
    /*
     *
     **/
    
    @Action
    public void AjouterNormeParametre() {
      //Enregistre d'abord le parametre
        //controle
           if(jTextFieldNomParametre.getText().equals("")&&(/*jTextFieldClasseParametre.getText().equals("")||*/jComboBoxClasse.getSelectedIndex()==-1)&&
              jTextFieldCritereParametre.getText().equals("")&&jTextFieldUniteParametre.getText().equals(""))
           {
             JOptionPane.showMessageDialog(null, " VEUILLEZ SAISIR LES CHAMPS OBLIGATOIRES");
             return;
           }
           else
           {
              Parametre p = new Parametre();
              //if(jTextFieldClasseParametre.getText().equals(""))
                 p.setClasseParam(jComboBoxClasse.getSelectedItem().toString());
              /*else
                 p.setClasseParam(jTextFieldClasseParametre.getText());
              */
              p.setNomParam(jTextFieldNomParametre.getText());
              p.setTypeResult(jComboBoxTypeRésultat.getSelectedItem().toString());
              
              Parametre pp =metier.getParametre(jTextFieldNomParametre.getText());
              if(pp!=null)
              {
                  if(/*pp.getClasseParam()==jTextFieldClasseParametre.getText()||*/pp.getClasseParam()==jComboBoxClasse.getSelectedItem())
                  {JOptionPane.showMessageDialog(null, "CE PARAMETRE EXISTE DEJA VEUILLEZ CREER UN AUTRE");
                   fermer();
                   return;
                  }
              }
              
              metier.saveParametre(p);
           }

               fermer();
    }
 
    @Action
    public void ModifierNormeParametre() { //  MMODIFICATION DE PARAMETRE
     // recupere le parametre 
        
        Parametre p = this.JFParamf.getParametreToMasterTable();
        String oldval = p.getNomParam();
        Parametre pp = p;
        if(jTextFieldNomParametre.getText().equals("")&&(/*jTextFieldClasseParametre.getText().equals("")||*/jComboBoxClasse.getSelectedIndex()==-1)&&
              jTextFieldCritereParametre.getText().equals("")&&jTextFieldUniteParametre.getText().equals(""))
           {
             JOptionPane.showMessageDialog(null, " VEUILLEZ SAISIR LES CHAMPS OBLIGATOIRES");
             return;
           }
           else
           {
            //  Parametre p = n.getParametre();
              //if(jTextFieldClasseParametre.getText().equals(""))
                 p.setClasseParam(jComboBoxClasse.getSelectedItem().toString());
              /*else
                 p.setClasseParam(jTextFieldClasseParametre.getText());
              */
              p.setNomParam(jTextFieldNomParametre.getText());
              p.setTypeResult(jComboBoxTypeRésultat.getSelectedItem().toString());
              
            
              metier.updateParametre(p);//
           }
     // Modifications de toutes les normes réferencée par le parametre p   
           
           List<Norme> ls = metier.getAllNorme();
           // Norme n = getParamNorme(p);
            Parametre pmodif = metier.getParametre(p.getId().longValue());
         //   System.out.println("Norme = "+n);
            if (ls==null)
            {
               JOptionPane.showMessageDialog(null, "LA LISTE EST VIDE");
               return;
            }
            
            for(Norme n:ls){
                //Norme n
                if(n.getParametre().equals(pp)                  ){
                     n.setNormeNomParam(jTextFieldNomParametre.getText());
                     n.setParametre(p);
                  //   n.
                    // n.getParametre().setClasseParam(jComboBoxClasse.getSelectedItem().toString());
                     //n.getParametre().setTypeResult(jComboBoxTypeRésultat.getSelectedItem().toString());
                     System.out.println(" MODIFICATION DE LA NORME...........");
                     metier.updateNorme(n);
               }
            } 
            
            //Modifier toutes les analyses ayant ce nom de parametre
            List<Analyse> lsa = metier.getAllanalyse();
            for(Analyse a:lsa)
            {
               if (a.getAnalyseNomParam().equals(oldval))
               {  a.setAnalyseNomParam(jTextFieldNomParametre.getText());
                  metier.updateAnalyse(a);
               }
            }
                    
               fermer();
    
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jToggleButtonAjputerFromNorme = new javax.swing.JToggleButton();
        jTextFieldMethodeNorme = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jToggleButtonModifier = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jToggleButtonAjouter = new javax.swing.JToggleButton();
        jComboBoxTypeRésultat = new javax.swing.JComboBox();
        jComboBoxClasse = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNomParametre = new javax.swing.JTextField();
        jTextFieldReferenceNorme = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldUniteParametre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jToggleButtonmodifier = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldTypeEau = new javax.swing.JTextField();
        jTextFieldCritereParametre = new javax.swing.JTextField();
        jTextFieldClasseParametre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldLOQ = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JDialogNormePa.class);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getActionMap(JDialogNormePa.class, this);
        jToggleButtonAjputerFromNorme.setAction(actionMap.get("AjouterNormeFromParam")); // NOI18N
        jToggleButtonAjputerFromNorme.setText(resourceMap.getString("jToggleButtonAjputerFromNorme.text")); // NOI18N
        jToggleButtonAjputerFromNorme.setName("jToggleButtonAjputerFromNorme"); // NOI18N

        jTextFieldMethodeNorme.setName("jTextFieldMethodeNorme"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jToggleButtonModifier.setAction(actionMap.get("ModifierNormeParametre")); // NOI18N
        jToggleButtonModifier.setText(resourceMap.getString("jToggleButtonModifier.text")); // NOI18N
        jToggleButtonModifier.setName("jToggleButtonModifier"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jToggleButtonAjouter.setAction(actionMap.get("AjouterNormeParametre")); // NOI18N
        jToggleButtonAjouter.setText(resourceMap.getString("jToggleButtonAjouter.text")); // NOI18N
        jToggleButtonAjouter.setName("jToggleButtonAjouter"); // NOI18N

        jComboBoxTypeRésultat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "reel", "entier", "reel >", "reel <", "chaine", "-" }));
        jComboBoxTypeRésultat.setFocusTraversalPolicyProvider(true);
        jComboBoxTypeRésultat.setName("jComboBoxTypeRésultat"); // NOI18N

        jComboBoxClasse.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Acidité-Alcalinité", "Agressivité antartrage", "Anions", "Cations", "Contrôle désinfection et décontamination microbienne", "Gaz de l'eau", "Micropolluants organiques", "Paramètres préalables", "Paramètres préliminaires", "Paramètres physico-chimiques", "Paramètres indicateurs de pollution organique", "Paramètres toxiques", "Paramètres indésirables", "Paramètres organoleptiques", "-" }));
        jComboBoxClasse.setName("jComboBoxClasse"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jTextFieldNomParametre.setName("jTextFieldNomParametre"); // NOI18N

        jTextFieldReferenceNorme.setName("jTextFieldReferenceNorme"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextFieldUniteParametre.setName("jTextFieldUniteParametre"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jToggleButtonmodifier.setAction(actionMap.get("ModifierNorme")); // NOI18N
        jToggleButtonmodifier.setText(resourceMap.getString("jToggleButtonmodifier.text")); // NOI18N
        jToggleButtonmodifier.setName("jToggleButtonmodifier"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jTextFieldTypeEau.setEnabled(false);
        jTextFieldTypeEau.setName("jTextFieldTypeEau"); // NOI18N

        jTextFieldCritereParametre.setName("jTextFieldCritereParametre"); // NOI18N
        jTextFieldCritereParametre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCritereParametreActionPerformed(evt);
            }
        });

        jTextFieldClasseParametre.setText(resourceMap.getString("jTextFieldClasseParametre.text")); // NOI18N
        jTextFieldClasseParametre.setName("jTextFieldClasseParametre"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextFieldLOQ.setText(resourceMap.getString("jTextFieldLOQ.text")); // NOI18N
        jTextFieldLOQ.setName("jTextFieldLOQ"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldLOQ, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldClasseParametre, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jToggleButtonAjputerFromNorme)
                                        .addGap(26, 26, 26)
                                        .addComponent(jToggleButtonAjouter)
                                        .addGap(18, 18, 18)
                                        .addComponent(jToggleButtonmodifier)
                                        .addGap(12, 12, 12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addGap(73, 73, 73)
                                        .addComponent(jTextFieldReferenceNorme, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                                        .addGap(55, 55, 55)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButtonModifier))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldNomParametre, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(jTextFieldUniteParametre)
                                    .addComponent(jTextFieldCritereParametre, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldTypeEau, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldMethodeNorme, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                                    .addComponent(jComboBoxTypeRésultat, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldTypeEau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNomParametre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldUniteParametre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldCritereParametre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldLOQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldClasseParametre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxTypeRésultat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldMethodeNorme, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldReferenceNorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonAjouter)
                    .addComponent(jToggleButtonModifier)
                    .addComponent(jToggleButtonmodifier)
                    .addComponent(jToggleButtonAjputerFromNorme))
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCritereParametreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCritereParametreActionPerformed
        
}//GEN-LAST:event_jTextFieldCritereParametreActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDialogNormePa dialog = new JDialogNormePa(new javax.swing.JFrame(), true,JFParamf,jdf);
                // JDialogNormeParam dialog = new JDialogNormeParam(JFParam,jdf);
               
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBoxClasse;
    private javax.swing.JComboBox jComboBoxTypeRésultat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextFieldClasseParametre;
    private javax.swing.JTextField jTextFieldCritereParametre;
    private javax.swing.JTextField jTextFieldLOQ;
    private javax.swing.JTextField jTextFieldMethodeNorme;
    private javax.swing.JTextField jTextFieldNomParametre;
    private javax.swing.JTextField jTextFieldReferenceNorme;
    private javax.swing.JTextField jTextFieldTypeEau;
    private javax.swing.JTextField jTextFieldUniteParametre;
    private javax.swing.JToggleButton jToggleButtonAjouter;
    private javax.swing.JToggleButton jToggleButtonAjputerFromNorme;
    private javax.swing.JToggleButton jToggleButtonModifier;
    private javax.swing.JToggleButton jToggleButtonmodifier;
    // End of variables declaration//GEN-END:variables
}
