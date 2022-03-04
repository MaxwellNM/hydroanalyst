/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFrameParametrefinal.java
 *
 * Created on 8 sept. 2011, 15:43:24
 */
package cpc.laboChimie.ui;

import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import cpc.laboChimie.model.TableModelNorme;
import cpc.laboChimie.model.TableModelParametre;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.jdesktop.application.Action;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author MAXWELL
 */
public class JFrameParametrefinal extends javax.swing.JDialog {
    TableModelParametre modelParametre;
    TableModelNorme     modelNorme;
    Vector<Parametre> params = new Vector<Parametre>();
    ParametreGestion parametreGestion;
    private IMetier metier;
    List<Parametre> ls;
    private long idTyEau;
    private int op;
    
    //classe appelleé
    private JDialogNormePa JNormParam;
    
    //classe appellante
    private JDialogChoixTypeEau  JChoix;
    private JDialogcreerTypeEau jCreertyp;
    private JDialogFrameNormeParam Jdfnorm;
    
    /** Creates new form JFrameParametrefinal */
    public JFrameParametrefinal(java.awt.Frame parent, boolean modal,JDialogcreerTypeEau j ,JDialogChoixTypeEau jfnorm,JDialogFrameNormeParam jdnor,JFChefLabo jf, JDialogAdmin jadm) {
        super(parent, modal);
        this.jCreertyp = j;
        this.JChoix  = jfnorm;
        this.Jdfnorm = jdnor;
        this.setTitle("LISTE DES PARAMETRES DU LABORATOIRE");
        initSpring();
        initComponents();
        if(this.jCreertyp!=null)
        {
            jTextFieldTypeEau.setText(this.jCreertyp.getNomTypEau());
            setItypdEau(this.jCreertyp.getidTypeEau());
        }
        if(JChoix!=null)
        {  jTextFieldTypeEau.setText(this.JChoix.getNomTypeEau());
           setItypdEau(this.JChoix.getIdEau());//id du Type d'eau
        }
        
        if(Jdfnorm!=null)
        {
          jTextFieldTypeEau.setText(this.getNomTypeEau());
          setItypdEau(this.Jdfnorm.getidTypeEau());
        }
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                       // System.exit(0);
                        fermer();
                    }
                });
        masterTableParametre.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        op = -1;//opération par défaut
    }
 private void fermer()
    {
      this.setVisible(false);
      // vider la liste et la réemplir
      if(this.Jdfnorm!=null){
          this.Jdfnorm.TablemodelNoreme.removeAll();
         this.Jdfnorm.TablemodelNoreme.addRows(metier.getParametreEau(idTyEau));
      // this.Jdfnorm.setjDialogListParam(null);
    }
    }
    
    private void initSpring() {
        modelParametre = new TableModelParametre(params);
        parametreGestion = ParametreGestion.getInstance();
        metier = parametreGestion.getMetier();
        //list_typeEau =  metier.getAllTypeEau();
        ls= metier.getAllParametre();
        if(ls!=null)
        {
            for(Parametre n: ls)
            {
              System.out.println("nom n = "+n.getNomParam());
              //System.out.println("Table Model = "+modelParametre);
              
             // modelParametre.addRow(n); 
            }
            
            modelParametre.addRows(ls); 
    }
        else
      System.out.println("La liste est vide");
              
    }
    
    
     public TableModelNorme getTableModelNorme()
    {
       return modelNorme;
    }
    public void setTableModelNorme(TableModelNorme tab)
    {
     this.modelNorme = tab;
    }
    
     public IMetier getMetier() {
        return metier;
    }
  
     public int getCodeOperation()
      {
         return op;       
             }
     
     public void setCodeOperation(int op)
     {
       this.op = op;
     }
     public String getNomTypeEau()
     {
       return jTextFieldTypeEau.getText();
     }
     
     public void setNomTypeEau(String st)
     {
        jTextFieldTypeEau.setText(st);
     }
     /*Identifiant de l'eau en cours*/
     public long getIdtypEau()
     {
       return idTyEau;
    
     }
     
     public void setItypdEau(long id)
     {
        this.idTyEau = id;
    
     }
     
    public Parametre getParametreToMasterTable()
    { if (masterTableParametre.getSelectedRow() != -1) {
            long i = 0;
            int ligne = masterTableParametre.getSelectedRow();
            i = (Long) masterTableParametre.getValueAt(ligne, 0);
            return metier.getParametre(i);
        } else {
            return null;
        }
    
    }
    public JXTable getmasterTableParametre()
      {
        return masterTableParametre;
      }
    
    
    
    public JDialogNormePa getJDialogNormeParametre()
    {
       return JNormParam;
    }
    
    public void setJDialogNormeParametre(JDialogNormePa jd)
    {
       this.JNormParam = jd;
    }

  JToggleButton getjToggleButtonModifier() {
       // throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonModifier;
    }

    JToggleButton getjToggleButtonEnregistrer() {
        //throw new UnsupportedOperationException("Not yet implemented");
        return jToggleButtonAjouter;
    }
    
    JToggleButton getjToggleButtonListerNormes() {
       return jToggleButtonListNorme;
    }
    
    JTextField getJtextFieldTypeEau()
    {
        return jTextFieldTypeEau;
    }
    
    JLabel    getJLabelAjouter()
     {
          return jLabelAjouter;
      
      }
    
    JLabel    getJLabelModifier()
     {
          return jLabelModifier;
      
      }
     JLabel    getJLabelTypeEau()
     {
          return jLabelTYpeEau;
      
      }
    
    JLabel    getJLabelListerNorme()
     {
          return jLabelListernorme;
      
      }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTYpeEau = new javax.swing.JLabel();
        jTextFieldTypeEau = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabelAjouter = new javax.swing.JLabel();
        jToggleButtonAjouter = new javax.swing.JToggleButton();
        jLabelModifier = new javax.swing.JLabel();
        jToggleButtonModifier = new javax.swing.JToggleButton();
        jLabelListernorme = new javax.swing.JLabel();
        jToggleButtonListNorme = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        masterTableParametre = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JFrameParametrefinal.class);
        jLabelTYpeEau.setText(resourceMap.getString("jLabelTYpeEau.text")); // NOI18N
        jLabelTYpeEau.setName("jLabelTYpeEau"); // NOI18N

        jTextFieldTypeEau.setEditable(false);
        jTextFieldTypeEau.setText(resourceMap.getString("jTextFieldTypeEau.text")); // NOI18N
        jTextFieldTypeEau.setName("jTextFieldTypeEau"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabelAjouter.setText(resourceMap.getString("jLabelAjouter.text")); // NOI18N
        jLabelAjouter.setName("jLabelAjouter"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getActionMap(JFrameParametrefinal.class, this);
        jToggleButtonAjouter.setAction(actionMap.get("CreerParametre")); // NOI18N
        jToggleButtonAjouter.setText(resourceMap.getString("jToggleButtonAjouter.text")); // NOI18N
        jToggleButtonAjouter.setName("jToggleButtonAjouter"); // NOI18N

        jLabelModifier.setText(resourceMap.getString("jLabelModifier.text")); // NOI18N
        jLabelModifier.setName("jLabelModifier"); // NOI18N

        jToggleButtonModifier.setAction(actionMap.get("ModifierParametre")); // NOI18N
        jToggleButtonModifier.setText(resourceMap.getString("jToggleButtonModifier.text")); // NOI18N
        jToggleButtonModifier.setName("jToggleButtonModifier"); // NOI18N

        jLabelListernorme.setText(resourceMap.getString("jLabelListernorme.text")); // NOI18N
        jLabelListernorme.setName("jLabelListernorme"); // NOI18N

        jToggleButtonListNorme.setAction(actionMap.get("ListerNormes")); // NOI18N
        jToggleButtonListNorme.setText(resourceMap.getString("jToggleButtonListNorme.text")); // NOI18N
        jToggleButtonListNorme.setName("jToggleButtonListNorme"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        masterTableParametre.setModel(modelParametre);
        masterTableParametre.setName("masterTableParametre"); // NOI18N
        jScrollPane1.setViewportView(masterTableParametre);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelTYpeEau)
                        .addGap(33, 33, 33)
                        .addComponent(jTextFieldTypeEau, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(53, 53, 53)
                                    .addComponent(jToggleButtonAjouter)
                                    .addGap(169, 169, 169)
                                    .addComponent(jLabelModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                    .addComponent(jToggleButtonModifier))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabelListernorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(38, 38, 38)
                                    .addComponent(jToggleButtonListNorme))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(276, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(275, 275, 275))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTYpeEau)
                    .addComponent(jTextFieldTypeEau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonAjouter)
                    .addComponent(jLabelModifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonModifier))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelListernorme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonListNorme))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
/*    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JFrameParametrefinal dialog = new JFrameParametrefinal(new javax.swing.JFrame(), true,,);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/

    @Action
    public void CreerParametre() {
    //   System.out.println("AJOUT D\'UNE NOUVELLE NORME..............");
           //qui Crée JChoix : Ajoute un nouveu parametre
       //             JFNorm : Ajoute Nouvelle Norme
       
       //tester le code de l'opération
        
        if(this.getCodeOperation()==-1) //Gestion des paramètres
        { //ajouter un parametre
            if(this.jCreertyp!=null)
            System.out.println("CLASSE APPELANTE 1 "+this.jCreertyp);
              if(JChoix!=null)
                  System.out.println("CLASSE APPELANTE 2 "+this.JChoix);
              
            JNormParam = new  JDialogNormePa(null,true,this,null);
            JNormParam.setTitle("AJOUT DUN PARAMETRE");
            //désactiver les éléments inécessaires
            JNormParam.getjtextfieldUnite().setEnabled(false);
            JNormParam.getjtextfieldCritere().setEnabled(false);
            JNormParam.getjtextfieldMethode().setEnabled(false);
            JNormParam.getjtextfieldReference().setEnabled(false);
            JNormParam.getjTextfieldClasseParam().setEnabled(false);
            JNormParam.getJtoogleButtonAjouterFromNorme().setVisible(false);
            JNormParam.getJtoogleButtonModifierFromNorme().setVisible(false);
            JNormParam.getJtoogleButtonModifier().setEnabled(false);
            JNormParam.setVisible(true);
            
            
        }
        
        if(this.getCodeOperation()==1) //Gestion des normes
          {
            Parametre p = getParametreToMasterTable();
            
            if(p==null)   
            {
              JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN PARAMETRE POUR CREER LA NORME");
              return;
             }
              if(Jdfnorm!=null)
              System.out.println("CLASSE APPELANTE 1 "+this.Jdfnorm);
              if(JChoix!=null)
                  System.out.println("CLASSE APPELANTE 2 "+this.JChoix);
              
              //Cree la fenetre JDnormpa et initialiser
              JNormParam = new  JDialogNormePa(null,true,this,Jdfnorm);
              JNormParam.setTitle("AJOUT D\'UNE NORME");
              JNormParam.getJToogleButtonAjouter().setVisible(false);
              JNormParam.getJtoogleButtonModifier().setVisible(false);
              JNormParam.getJtoogleButtonModifierFromNorme().setEnabled(false);
              JNormParam.getJtextfieldNomParam().setEnabled(false);
              JNormParam.getjTextfieldClasseParam().setEnabled(false);
              JNormParam.getjComboboxClasse().setVisible(false);
              JNormParam.getjComboboxType().setEnabled(false);
              JNormParam.setParametreTointerface(p);
              JNormParam.setVisible(true);
              this.setVisible(false);
          }
        
        
    }

    @Action
    public void ModifierParametre() {
         if(this.getCodeOperation()==-1) //Gestion des parametres
          {
            Parametre p = getParametreToMasterTable();
            
            if(p==null)   
            {
              JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN PARAMETRE POUR MODIFICATION");
              return;
             }
            if(this.jCreertyp!=null)
            System.out.println("CLASSE APPELANTE 1 "+this.jCreertyp);
              if(JChoix!=null)
                  System.out.println("CLASSE APPELANTE 2 "+this.JChoix);
              
            JNormParam = new  JDialogNormePa(null,true,this,null);
            //désactiver les éléments inécessaires
            JNormParam.getjtextfieldUnite().setEnabled(false);
            JNormParam.getjtextfieldCritere().setEnabled(false);
            JNormParam.getjtextfieldMethode().setEnabled(false);
            JNormParam.getjtextfieldReference().setEnabled(false);
            JNormParam.getjTextfieldClasseParam().setEnabled(false);
            
            JNormParam.getJtoogleButtonAjouterFromNorme().setVisible(false);
            JNormParam.getJtoogleButtonModifierFromNorme().setVisible(false);
            JNormParam.getJToogleButtonAjouter().setEnabled(false);
            JNormParam.setParametreTointerface(p);
            JNormParam.setVisible(true);
         }
    }

    @Action
    public void ListerNormes() {
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelAjouter;
    private javax.swing.JLabel jLabelListernorme;
    private javax.swing.JLabel jLabelModifier;
    private javax.swing.JLabel jLabelTYpeEau;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldTypeEau;
    private javax.swing.JToggleButton jToggleButtonAjouter;
    private javax.swing.JToggleButton jToggleButtonListNorme;
    private javax.swing.JToggleButton jToggleButtonModifier;
    private org.jdesktop.swingx.JXTable masterTableParametre;
    // End of variables declaration//GEN-END:variables
}
