/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogAdmin.java
 *
 * Created on 14 juin 2011, 15:15:27
 */

package cpc.laboChimie.ui;

import cpc.laboChimie.AuthentificationView;
import cpc.laboChimie.HydroAboutBox;
import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import cpc.laboChimie.model.*;
import cpc.laboChimie.jiDBase.*;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import org.jdesktop.application.Action;
import org.jdesktop.swingx.JXTable;
import javax.swing.UIManager;
import javax.swing.plaf.metal.OceanTheme;
//import javax.swing.plaf.;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
//import com.lowagie.text.pdf.
import com.itextpdf.text.Image;
//import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.GifImage;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.DrawInterface;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import cpc.laboChimie.jpa.TypeEau;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 *
 * @author MAXWELL
 */
public class JDialogAdmin extends javax.swing.JFrame {
    IMetier metier = null;
    TableModelClient tableModelClient = null;
    TableModelEaux tableModelEaux = null;
    Vector<Client> clients = new Vector<Client>();
    Vector<Eau> eaus = new Vector<Eau>();
    
    private JDialogClient Jclient = null;
    private JDialogEau Jeau = null;
    private JDialogChoixTypeEau JChoixEau  = null;
    AuthentificationView aut;
    JDialogClient jDialogClient;
    private int Opnorme;
    private JDialogcreerTypeEau JTypEau;
    private JDialogTypEau JChoixEauModif;
    private JFrameParametrefinal jparamf;
    private long[] id_Client;
    private int opstat;
    
    
    
    
    //variables pour l'impression des analyses
        Chunk tab1 = new Chunk(new VerticalPositionMark(), 10, true);//nom
        
        Chunk tab2 = new Chunk(new VerticalPositionMark(), 200, true);//rsultat
        Chunk tab3 = new Chunk(new VerticalPositionMark(), 300, true);//unite
        Chunk tab4 = new Chunk(new VerticalPositionMark(), 400, true);//critere
    private int next;//pour sauvegarder l'état de la page précédente
   private statGlobal jfstat;
    private JDialogEauUsee Jeau2 = null;
    private JDialogEauBeton Jeau3 = null;
 
       
        
        
    
  /** Creates new form JDialogAdmin */
    public JDialogAdmin(java.awt.Frame parent, boolean modal,AuthentificationView aute) {
        super("Administration");
        
        
   
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
        
        initSpring();
        initComponents();
        this.aut = aute;
        this.setTitle("Administrateur");
        mastertableClient.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mastertableEaux.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
       // mastertableClient.getColumn(0).setHeaderValue("");
        mastertableClient.getColumn(0).getHeaderRenderer();
        mastertableClient.setHorizontalScrollEnabled(true);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                        aut.quitter();
                    }
                });

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */



        ////////////////////////////////
    public IMetier getMetier() {
        return metier;
    }

        public statGlobal getjFrameStatGlobal() {
        return jfstat;
    }

    public void setjFrameStatGlobal(statGlobal stat) {
        this.jfstat = stat;
    }


    public void fermer1()
    {
      this.setjFrameStatGlobal(null);
     
    }
  
    ////Initialisation de l'application
    ParametreGestion parametreGestion;

    public int getOpNorme()
    {
       return Opnorme;
    }
    
    
    public int getopStat()  //-1 statistique mensuelle
    {                      // 1  statistique annuelle
    
       return opstat;
    }
    
    private void initSpring() {

     //   List<Client> ls = ;
        
        tableModelClient = new TableModelClient(clients);
        tableModelEaux = new TableModelEaux(eaus);
        parametreGestion = ParametreGestion.getInstance();
        metier = parametreGestion.getMetier();
        tableModelClient.addRows(metier.getAllClient());
    /*  id_Client = new long[ls.size()];
      /*Recupère les ids*/
      /*int i =0;
      for(Client c:ls)
       {
          id_Client[i] = c.getId().longValue();
          i++;
      }*/
      
      
    }

    public TableModelClient getTableModelClient() {
        return tableModelClient;
    }

    public void setTableModelClient(TableModelClient tableModelClient) {
      //  tableModelClient.
        this.tableModelClient = tableModelClient;
    }

    public JDialogClient getjDialogClient() {
        return jDialogClient;
    }

    public void setjDialogClient(JDialogClient jDialogNouveauClient) {
        this.jDialogClient = jDialogNouveauClient;
    }

    
    public JFrameParametrefinal getjDialogListParam() {
        return jparamf;
    }

    public void setjDialogListParam(JFrameParametrefinal jD ){
        this.jparamf = jD;
    }

    
    //JFrameParametrefinal
    
    public JDialogcreerTypeEau getjDialogCreerTypeEau() {
        return JTypEau;
    }

    public void setJDialogCreerTypeEau(JDialogcreerTypeEau typ) {
        this.JTypEau = typ;
    }
    
    
    public JDialogChoixTypeEau getjDialogTypeEau() {
        return JChoixEau;
    }

    public void setJDialoTypeEau(JDialogChoixTypeEau typ) {
        this.JChoixEau = typ;
    }
    
     public JDialogTypEau getjDialogChoixmodifTypeEau() {
        return JChoixEauModif;
    }

    public void setJDialogChoixmodifTypeEau(JDialogTypEau typ) {
        this.JChoixEauModif = typ;
    }
    
    public JDialogEau getjDialogEau() {
        return Jeau;
    }

    public void setjDialogEau(JDialogEau jDialogeau) {
        this.Jeau = jDialogeau;
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jPanel6 = new javax.swing.JPanel();
        jToggleButton_New_Eau = new javax.swing.JToggleButton();
        jToggleButton_Modifier_Eau = new javax.swing.JToggleButton();
        jToggleButton_Delete_Eau = new javax.swing.JToggleButton();
        jToggleButton_Print_Eau = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        jToggleButton8 = new javax.swing.JToggleButton();
        jPanelArchivage = new javax.swing.JPanel();
        jToggleButtonPurger = new javax.swing.JToggleButton();
        jToggleButtonImporter = new javax.swing.JToggleButton();
        jToggleButtonExplorer = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mastertableClient = new org.jdesktop.swingx.JXTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mastertableEaux = new org.jdesktop.swingx.JXTable();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuTypeEau = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuNormes = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemModifierNorme = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAjouterNorme = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItemListNorme = new javax.swing.JMenuItem();
        jMenuParametres = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemAjouterparam = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItemModifierParam = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItemListeParam = new javax.swing.JMenuItem();
        jMenuStatistiqueLaboratoire = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuStatistiqueInferentielle = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuContact = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JDialogAdmin.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jPanel5.setBackground(resourceMap.getColor("jPanel5.background")); // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getActionMap(JDialogAdmin.class, this);
        jToggleButton2.setAction(actionMap.get("creerclient")); // NOI18N
        jToggleButton2.setText(resourceMap.getString("jToggleButton2.text")); // NOI18N
        jToggleButton2.setName("jToggleButton2"); // NOI18N

        jToggleButton1.setAction(actionMap.get("modifierClient")); // NOI18N
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setName("jToggleButton1"); // NOI18N

        jToggleButton3.setAction(actionMap.get("supprimerClient")); // NOI18N
        jToggleButton3.setText(resourceMap.getString("jToggleButton3.text")); // NOI18N
        jToggleButton3.setName("jToggleButton3"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jToggleButton3))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(resourceMap.getColor("jPanel6.background")); // NOI18N
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel6.border.title"))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        jToggleButton_New_Eau.setAction(actionMap.get("NouvelleEau")); // NOI18N
        jToggleButton_New_Eau.setText(resourceMap.getString("jToggleButton_New_Eau.text")); // NOI18N
        jToggleButton_New_Eau.setName("jToggleButton_New_Eau"); // NOI18N

        jToggleButton_Modifier_Eau.setAction(actionMap.get("modifierEau")); // NOI18N
        jToggleButton_Modifier_Eau.setText(resourceMap.getString("jToggleButton_Modifier_Eau.text")); // NOI18N
        jToggleButton_Modifier_Eau.setName("jToggleButton_Modifier_Eau"); // NOI18N

        jToggleButton_Delete_Eau.setAction(actionMap.get("supprimerEau")); // NOI18N
        jToggleButton_Delete_Eau.setText(resourceMap.getString("jToggleButton_Delete_Eau.text")); // NOI18N
        jToggleButton_Delete_Eau.setName("jToggleButton_Delete_Eau"); // NOI18N

        jToggleButton_Print_Eau.setAction(actionMap.get("ImprimerEau")); // NOI18N
        jToggleButton_Print_Eau.setText(resourceMap.getString("jToggleButton_Print_Eau.text")); // NOI18N
        jToggleButton_Print_Eau.setName("jToggleButton_Print_Eau"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToggleButton_New_Eau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_Modifier_Eau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_Delete_Eau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_Print_Eau, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton_New_Eau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton_Modifier_Eau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton_Delete_Eau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton_Print_Eau)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(resourceMap.getColor("jPanel8.background")); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N

        jToggleButton8.setAction(actionMap.get("QuitterApp")); // NOI18N
        jToggleButton8.setText(resourceMap.getString("jToggleButton8.text")); // NOI18N
        jToggleButton8.setName("jToggleButton8"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jToggleButton8)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToggleButton8)
        );

        jPanelArchivage.setBackground(resourceMap.getColor("jPanelArchivage.background")); // NOI18N
        jPanelArchivage.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanelArchivage.border.title"))); // NOI18N
        jPanelArchivage.setName("jPanelArchivage"); // NOI18N

        jToggleButtonPurger.setText(resourceMap.getString("jToggleButtonPurger.text")); // NOI18N
        jToggleButtonPurger.setName("jToggleButtonPurger"); // NOI18N
        jToggleButtonPurger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonPurgerActionPerformed(evt);
            }
        });

        jToggleButtonImporter.setText(resourceMap.getString("jToggleButtonImporter.text")); // NOI18N
        jToggleButtonImporter.setName("jToggleButtonImporter"); // NOI18N
        jToggleButtonImporter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonImporterActionPerformed(evt);
            }
        });

        jToggleButtonExplorer.setText(resourceMap.getString("jToggleButtonExplorer.text")); // NOI18N
        jToggleButtonExplorer.setEnabled(false);
        jToggleButtonExplorer.setName("jToggleButtonExplorer"); // NOI18N
        jToggleButtonExplorer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonExplorerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelArchivageLayout = new javax.swing.GroupLayout(jPanelArchivage);
        jPanelArchivage.setLayout(jPanelArchivageLayout);
        jPanelArchivageLayout.setHorizontalGroup(
            jPanelArchivageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelArchivageLayout.createSequentialGroup()
                .addGroup(jPanelArchivageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButtonPurger, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jToggleButtonImporter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButtonExplorer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanelArchivageLayout.setVerticalGroup(
            jPanelArchivageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelArchivageLayout.createSequentialGroup()
                .addComponent(jToggleButtonPurger)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonImporter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButtonExplorer))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelArchivage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelArchivage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        jPanel7.setBackground(resourceMap.getColor("jPanel7.background")); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane1.setBackground(resourceMap.getColor("jScrollPane1.background")); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        mastertableClient.setModel(tableModelClient);
        mastertableClient.setName("mastertableClient"); // NOI18N
        mastertableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mastertableClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(mastertableClient);

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(resourceMap.getColor("jPanel9.background")); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        mastertableEaux.setModel(tableModelEaux);
        mastertableEaux.setName("mastertableEaux"); // NOI18N
        mastertableEaux.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mastertableEauxMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mastertableEaux);

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(resourceMap.getColor("jPanel4.background")); // NOI18N
        jPanel4.setForeground(resourceMap.getColor("jPanel4.foreground")); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(533, 533, 533)
                .addComponent(jLabel1)
                .addContainerGap(446, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenuTypeEau.setText(resourceMap.getString("jMenuTypeEau.text")); // NOI18N
        jMenuTypeEau.setName("jMenuTypeEau"); // NOI18N

        jMenuItem1.setAction(actionMap.get("creeTypeEau")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuTypeEau.add(jMenuItem1);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jMenuTypeEau.add(jSeparator3);

        jMenuItem2.setAction(actionMap.get("modifierTYpeEAu")); // NOI18N
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuTypeEau.add(jMenuItem2);

        jSeparator11.setName("jSeparator11"); // NOI18N
        jMenuTypeEau.add(jSeparator11);

        jMenuItem3.setAction(actionMap.get("CreeGroupeClasse")); // NOI18N
        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuTypeEau.add(jMenuItem3);

        jMenuBar1.add(jMenuTypeEau);

        jMenuNormes.setText(resourceMap.getString("jMenuNormes.text")); // NOI18N
        jMenuNormes.setName("jMenuNormes"); // NOI18N

        jSeparator4.setName("jSeparator4"); // NOI18N
        jMenuNormes.add(jSeparator4);

        jMenuItemModifierNorme.setAction(actionMap.get("ModifierNorme")); // NOI18N
        jMenuItemModifierNorme.setText(resourceMap.getString("jMenuItemModifierNorme.text")); // NOI18N
        jMenuItemModifierNorme.setName("jMenuItemModifierNorme"); // NOI18N
        jMenuNormes.add(jMenuItemModifierNorme);

        jSeparator5.setName("jSeparator5"); // NOI18N
        jMenuNormes.add(jSeparator5);

        jMenuItemAjouterNorme.setAction(actionMap.get("CreerNorme")); // NOI18N
        jMenuItemAjouterNorme.setText(resourceMap.getString("jMenuItemAjouterNorme.text")); // NOI18N
        jMenuItemAjouterNorme.setName("jMenuItemAjouterNorme"); // NOI18N
        jMenuNormes.add(jMenuItemAjouterNorme);

        jSeparator6.setName("jSeparator6"); // NOI18N
        jMenuNormes.add(jSeparator6);

        jMenuItemListNorme.setAction(actionMap.get("ConsulterNormeTypeEau")); // NOI18N
        jMenuItemListNorme.setText(resourceMap.getString("jMenuItemListNorme.text")); // NOI18N
        jMenuItemListNorme.setName("jMenuItemListNorme"); // NOI18N
        jMenuNormes.add(jMenuItemListNorme);

        jMenuBar1.add(jMenuNormes);

        jMenuParametres.setText(resourceMap.getString("jMenuParametres.text")); // NOI18N
        jMenuParametres.setName("jMenuParametres"); // NOI18N

        jSeparator2.setName("jSeparator2"); // NOI18N
        jMenuParametres.add(jSeparator2);

        jMenuItemAjouterparam.setAction(actionMap.get("AjouterParametre")); // NOI18N
        jMenuItemAjouterparam.setText(resourceMap.getString("jMenuItemAjouterparam.text")); // NOI18N
        jMenuItemAjouterparam.setName("jMenuItemAjouterparam"); // NOI18N
        jMenuParametres.add(jMenuItemAjouterparam);

        jSeparator8.setName("jSeparator8"); // NOI18N
        jMenuParametres.add(jSeparator8);

        jMenuItemModifierParam.setAction(actionMap.get("ModifierParametre")); // NOI18N
        jMenuItemModifierParam.setText(resourceMap.getString("jMenuItemModifierParam.text")); // NOI18N
        jMenuItemModifierParam.setName("jMenuItemModifierParam"); // NOI18N
        jMenuParametres.add(jMenuItemModifierParam);

        jSeparator7.setName("jSeparator7"); // NOI18N
        jMenuParametres.add(jSeparator7);

        jMenuItemListeParam.setAction(actionMap.get("consulterParametre")); // NOI18N
        jMenuItemListeParam.setText(resourceMap.getString("jMenuItemListeParam.text")); // NOI18N
        jMenuItemListeParam.setName("jMenuItemListeParam"); // NOI18N
        jMenuParametres.add(jMenuItemListeParam);

        jMenuBar1.add(jMenuParametres);

        jMenuStatistiqueLaboratoire.setText(resourceMap.getString("jMenuStatistiqueLaboratoire.text")); // NOI18N
        jMenuStatistiqueLaboratoire.setName("jMenuStatistiqueLaboratoire"); // NOI18N

        jMenuItem9.setAction(actionMap.get("statistiqueMensuelle")); // NOI18N
        jMenuItem9.setText(resourceMap.getString("jMenuItem9.text")); // NOI18N
        jMenuItem9.setName("jMenuItem9"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jMenuItem9);

        jSeparator10.setName("jSeparator10"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jSeparator10);

        jMenuItem10.setAction(actionMap.get("Statistiqueannuelle")); // NOI18N
        jMenuItem10.setText(resourceMap.getString("jMenuItem10.text")); // NOI18N
        jMenuItem10.setName("jMenuItem10"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jMenuItem10);

        jMenuBar1.add(jMenuStatistiqueLaboratoire);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem4.setAction(actionMap.get("importerbd")); // NOI18N
        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAction(actionMap.get("connexionJiDBase")); // NOI18N
        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu1.add(jMenuItem5);

        jSeparator12.setName("jSeparator12"); // NOI18N
        jMenu1.add(jSeparator12);

        jMenuBar1.add(jMenu1);

        jMenuStatistiqueInferentielle.setText(resourceMap.getString("jMenuStatistiqueInferentielle.text")); // NOI18N
        jMenuStatistiqueInferentielle.setName("jMenuStatistiqueInferentielle"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenuStatistiqueInferentielle.add(jSeparator1);

        jMenuItem14.setAction(actionMap.get("statistiqueGlobale")); // NOI18N
        jMenuItem14.setText(resourceMap.getString("jMenuItem14.text")); // NOI18N
        jMenuItem14.setName("jMenuItem14"); // NOI18N
        jMenuStatistiqueInferentielle.add(jMenuItem14);

        jSeparator9.setName("jSeparator9"); // NOI18N
        jMenuStatistiqueInferentielle.add(jSeparator9);

        jMenuBar1.add(jMenuStatistiqueInferentielle);

        jMenuContact.setText(resourceMap.getString("jMenuContact.text")); // NOI18N
        jMenuContact.setName("jMenuContact"); // NOI18N

        jMenuItem16.setAction(actionMap.get("AproposDe")); // NOI18N
        jMenuItem16.setText(resourceMap.getString("jMenuItem16.text")); // NOI18N
        jMenuItem16.setName("jMenuItem16"); // NOI18N
        jMenuContact.add(jMenuItem16);

        jMenuBar1.add(jMenuContact);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void mastertableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mastertableClientMouseClicked
        // TODO add your handling code here:
        //dès qu'on clique sur le Master Table on recupere toutes les eaux du client et on les affiche


        if (getClientToMasterTable() != null) {
            List<Eau> eau = metier.getEauByClient(getClientToMasterTable());
            tableModelEaux.removeAll();
            tableModelEaux.addRows(eau);
            System.out.println("MAXWELL table des clients !!!!!!!!!!!!!!!!!!!!!!!!!!");
        }






    }//GEN-LAST:event_mastertableClientMouseClicked

    private void mastertableEauxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mastertableEauxMouseClicked
        // TODO add your handling code here:
        //on est dan lla table des eaux
    }//GEN-LAST:event_mastertableEauxMouseClicked

private void jToggleButtonPurgerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonPurgerActionPerformed
// TODO add your handling code here:
    JDialogArchiver arch = new JDialogArchiver();
    
    arch.getBoutonExplorer().setEnabled(false);
    arch.getBoutonImporter().setEnabled(false);
    arch.setVisible(true);
    
}//GEN-LAST:event_jToggleButtonPurgerActionPerformed

private void jToggleButtonImporterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonImporterActionPerformed
// TODO add your handling code here:
    JDialogArchiver arch = new JDialogArchiver();
    
    arch.getBoutonExplorer().setEnabled(false);
    arch.getDateDebut().setEnabled(false);
    arch.getDateFin().setEnabled(false);
    arch.getBoutonPurger().setEnabled(false);
    arch.setVisible(true);
    
}//GEN-LAST:event_jToggleButtonImporterActionPerformed

private void jToggleButtonExplorerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonExplorerActionPerformed
// TODO add your handling code here:
        JDialogArchiver arch = new JDialogArchiver();
    
    arch.getBoutonPurger().setEnabled(false);
    arch.getBoutonImporter().setEnabled(false);
    arch.setVisible(true);

}//GEN-LAST:event_jToggleButtonExplorerActionPerformed

    /**
    * @param args the command line arguments
    */
  /*  public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogAdmin dialog = new JDialogAdmin(new javax.swing.JFrame(), true, aut);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                        aut.quitter();
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/

    @Action
    public void QuitterApp() {

        aut.quitter();
    }

    @Action
    public void creeTypeEau() {
        JDialogcreerTypeEau tpEau = new JDialogcreerTypeEau(null,true,null,this);
        //JDialogcreerTypeEau.sett
        tpEau.setVisible(true);
    }

    @Action
    public void modifierEau() {
       Eau e = getEauToMasterTable();
        

        if(getClientToMasterTable()==null)
        { 
         JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UN CLIENT");
         return;
        }
        
        if(getEauToMasterTable()==null)
        { 
         JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE EAU");
         return;
        }
        String st =e.getTypeEau();
        
        if(st.equalsIgnoreCase("eaux propres")){
        Jeau = new JDialogEau(null, true, null,this);
         System.out.println("NOUS MMODIFIONS EAU.........."+e.getId().longValue());
        //Jeau = new JDialogEau(null,true,null,this);
        Jeau.getJlabelIdClientEau().setVisible(false);
        Jeau.getjtexFieldidClient().setVisible(false);
        Jeau.getJlabelidEau().setVisible(false);
        Jeau.getIdEau().setVisible(false);
        
        Jeau.getjToggleButtonEnregistrer().setEnabled(false);
        Jeau.setTitle("MODIFICATION DE L\'EAU PROPRE: "+e.getNumEngLabo());
//        jDialogNouveauClient.getjToggleButtonImprimer().setEnabled(true);
        //désactive et rend invisible les éléments suivant
        Jeau.getJlabelTypeEau().setVisible(false);
        Jeau.getJComboBoxTypeEau().setVisible(false);
        
        Jeau.getjToggleButtonModifier().setEnabled(true);
        Jeau.setEauInterface(e);
        Jeau.setVisible(true);

      }
   
      if(st.equalsIgnoreCase("eaux usées")){
        Jeau2 = new JDialogEauUsee(null,true,this);
        Jeau2.setTitle("MODIFICATION DE L\'EAU USEE:  "+e.getNumEngLabo());
        Jeau2.getJlabelIdClientEau().setVisible(false);
        Jeau2.getjtexFieldidClient().setVisible(false);
        Jeau2.getJlabelidEau().setVisible(false);
        Jeau2.getIdEau().setVisible(false);
        System.out.println(" Bienvennue dans la fenetre eaux usees");
        Jeau2.getjToggleButtonEnregistrer().setEnabled(false);
        Jeau2.getjToggleButtonModifier().setEnabled(true);
        Jeau2.setEauInterface(e);
        Jeau2.setVisible(true);
       }

        if(st.equalsIgnoreCase("eaux bétons")){
        Jeau3 = new JDialogEauBeton(null,true,this);
        Jeau3.setTitle("MODIFICATION DE L\'EAU BETON:  "+e.getNumEngLabo());
        Jeau3.getJlabelIdClientEau().setVisible(false);
        Jeau3.getjtexFieldidClient().setVisible(false);
        Jeau3.getJlabelidEau().setVisible(false);
        Jeau3.getIdEau().setVisible(false);
        System.out.println(" Bienvennue dans la fenetre eaux usees");
        Jeau3.getjToggleButtonEnregistrer().setEnabled(false);
        Jeau3.getjToggleButtonModifier().setEnabled(true);
        Jeau3.setEauInterface(getEauToMasterTable());
        Jeau3.setVisible(true);
        
        }
   
        
        System.out.println("FIN MODIFICATION");
    }

    @Action
    public void AproposDe() {
        JDialog j = new HydroAboutBox(null);
         j.setVisible(true);
    }

    @Action
    public void creerclient() {
       if(Jclient==null)
        {  Jclient = new JDialogClient(null,true,null,null,this);
           //active le boutton d' enregistrement
         }
       Jclient.setTitle("AJOUT D\'UN CLIENT");
         Jclient.getjToggleButtonEnregistrer().setEnabled(true);
         //desactiv le bouton de modification
         Jclient.getJLabelIdClient().setVisible(false);
         Jclient.getJtextfieldIdClient().setVisible(false);
           Jclient.getjToggleButtonModifier().setEnabled(false);
           Jclient.setVisible(true);
    
    }

      public Eau getEauToMasterTable() {
        
          String typeEau = "";
          if (mastertableEaux.getSelectedRow() != -1) {
            long i = 0;
            
            int ligne = mastertableEaux.getSelectedRow();
            i = (Long) mastertableEaux.getValueAt(ligne, 0);
            typeEau = (String) mastertableEaux.getValueAt(ligne, 2);
         
             return metier.getEauByID(i, typeEau);
        } else {
            return null;
        }
      }


      
    public Client getClientToMasterTable() {
        int ligne;
        if (mastertableClient.getSelectedRow() != -1) {
            long i ;
             ligne = mastertableClient.getSelectedRow();
            i = (Long) mastertableClient.getValueAt(ligne, 0);
            //System.out.println("CLIENT ="+c.getNomDemandeur());
           /* Client c = metier.getClientByNom(i);
            
            for(Client cp:clients)
            {
               if(cp.equals(c))
                   return cp;
            }
            return null;*/
            return metier.getClientByID(i);
        } else {
            return null;
        }

    }

    @Action
    public void modifierClient() {
 if (jDialogClient == null) {
            jDialogClient = new JDialogClient(null, true, null,null,this);
        }
        
        jDialogClient.getjToggleButtonEnregistrer().setEnabled(false);
//        jDialogNouveauClient.getjToggleButtonImprimer().setEnabled(true);
        jDialogClient.getjToggleButtonModifier().setEnabled(true);
        jDialogClient.getJLabelIdClient().setVisible(false);
        jDialogClient.getJtextfieldIdClient().setVisible(false);
          Client c = getClientToMasterTable();
        if (c != null) {
            jDialogClient.setTitle("MODIFICATION DU CLIENT "+c.getNomDemandeur().toUpperCase());
            jDialogClient.setClientInterface(getClientToMasterTable());
            jDialogClient.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vous devez sélectionner de prime abord un client");
        }
    }

    @Action
    public void supprimerClient() {
           Client c =getClientToMasterTable() ;
        if (c!= null) {
            //supprime toutes les analyses du client ensuite supprime les eaux du clients et enfin le client
           String ok =""  ;
           ok = JOptionPane.showInputDialog("VOULEZ VOUS VRAIMENT SUPPRIMER LE CLIENT "+c.getNomDemandeur()+"\n yes/no");
           System.out.println("valeur ok ="+ok);

           if(ok!=null||ok=="yes"){
              System.out.println("suppression du client......");
              metier.deleteClient(c);
              //raffraichir la liste
             JOptionPane.showMessageDialog(null, "LE CLIENT "+c.getNomDemandeur()+"A ETE SUPPRIME  ");
              tableModelClient.removeAll();
              tableModelClient.addRows(metier.getAllClient());

           }

        } else {
            JOptionPane.showMessageDialog(null, "Vous devez sélectionner de prime abord un client");
        }
    }

    @Action
    public void NouvelleEau() {



         if((getClientToMasterTable()== null))
        {  
           //active le boutton d' enregistrement
               JOptionPane.showMessageDialog(null, "Vous devez sélectionner de prime abord un client");
       return;
         }
//          if(Jeau==null){
//        Jeau = new JDialogEau(null,true,null,this);
//        Jeau.setTitle("AJOUT D\'UNE EAU");
//        Jeau.getJlabelIdClientEau().setVisible(false);
//        Jeau.getjtexFieldidClient().setVisible(false);
//        Jeau.getJlabelidEau().setVisible(false);
//        Jeau.getIdEau().setVisible(false);
//        System.out.println(" Bienvennue dans la fenetre eau");
//         
//           }
//         Jeau.getjToggleButtonEnregistrer().setEnabled(true);
//         //desactiv le bouton de modification
//
//           Jeau.getjToggleButtonModifier().setEnabled(false);
//           Jeau.setVisible(true);
//           
           
          JDialogChoixTypeEau TypWater = new JDialogChoixTypeEau(null, true, null, this);
          TypWater.setVisible(true);
           
    }

    @Action
    public void supprimerEau() {

        Eau e = getEauToMasterTable();
        if(e==null)
        {JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE EAU");
          return;
         }
        String ok =""  ;
           ok = JOptionPane.showInputDialog("VOULEZ VOUS VRAIMENT SUPPRIMER L\'EAU \n DU CLIENT "+this.getClientToMasterTable().getNomDemandeur()+"\n yes/no");
           System.out.println("valeur ok ="+ok);

           if(ok!=null && ok!=""){
              System.out.println("suppression de l'eau......");
                //on supprime d'abord les analyses de l'eau
             long idEau = e.getId().longValue();
             metier.deleteAllAnalyseEau(idEau);
             //on supprime l'eau
             metier.deleteEau(e);
              //raffraichir la liste
             JOptionPane.showMessageDialog(null, "L\'EAU A ETE SUPPRIME  ");
              tableModelEaux.removeAll();
              tableModelEaux.addRows(metier.getEauByClient(this.getClientToMasterTable()));

           }

      
    }

    @Action
    public void modifierTYpeEAu() {
       JDialogTypEau jt = new JDialogTypEau(null,true,null,this);
       jt.setVisible(true);
    }

    @Action
    public void CreerNorme() {
        //Ajout d'une norme //fonction du type d'eau
     Opnorme = -1;
    //if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,null,this);
           JChoixEau.getBoutAjouterEau().setEnabled(false);
           JChoixEau.getBoutModifierEau().setEnabled(false);
           JChoixEau.setVisible(true);
        }
    
    }

    @Action
    public void ModifierNorme() {
      
        //Modifier une norme fonction du type d'eau
        Opnorme = 0;
    //if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,null,this);
           JChoixEau.getBoutAjouterEau().setEnabled(false);
           JChoixEau.getBoutModifierEau().setEnabled(false);
        
           JChoixEau.setVisible(true);
        }
    }

    @Action
    public void consulterParametre() {
       //Liste des Parametres disponibles et ajout sii possible
        jparamf = new JFrameParametrefinal(null, true, null, null, null,null,this)  ;
       // jparamf.getJLabelAjouter().setVisible(false);
      //  jparamf.getjToggleButtonEnregistrer().setVisible(false);
       // jparamf.getJLabelModifier().setVisible(false);
       // jparamf.getjToggleButtonModifier().setVisible(false);
        jparamf.getJtextFieldTypeEau().setVisible(false);
        jparamf.getJLabelListerNorme().setVisible(false);
        jparamf.getJLabelTypeEau().setVisible(false);
        jparamf.getjToggleButtonListerNormes().setVisible(false);
        jparamf.setVisible(true);
        
        
        
     /*  Opnorme =1;
        if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,this);
           JChoixEau.setVisible(true);
        }*/
    }

    @Action
    public void ConsulterNormeTypeEau() {
         Opnorme =10;
        //if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,null,this);
           JChoixEau.getBoutAjouterEau().setEnabled(false);
           JChoixEau.getBoutModifierEau().setEnabled(false);
        
           JChoixEau.setVisible(true);
        }
        
    }

    @Action
    public void AjouterParametre() {
        //i option = -1
        
        
        
        
        jparamf = new JFrameParametrefinal(null, true, null, null, null,null,this)  ;
        //jparamf.getJLabelAjouter().setVisible(false);
        //.getjToggleButtonEnregistrer().setVisible(false);
      //  jparamf.getJLabelModifier().setEnabled(false);
        jparamf.setCodeOperation(-1);
        jparamf.getjToggleButtonModifier().setEnabled(false);
        jparamf.getJtextFieldTypeEau().setVisible(false);
        jparamf.getJLabelListerNorme().setVisible(false);
        jparamf.getJLabelTypeEau().setVisible(false);
        jparamf.getjToggleButtonListerNormes().setVisible(false);
        jparamf.setVisible(true);
        
    }

    @Action
    public void ModifierParametre() {
        // code 3
         
        jparamf = new JFrameParametrefinal(null, true, null, null, null,null,this)  ;
        //jparamf.getJLabelAjouter().setVisible(false);
        jparamf.getjToggleButtonEnregistrer().setEnabled(false);
      //  jparamf.getJLabelModifier().setEnabled(false);
        jparamf.setCodeOperation(-1);
       // jparamf.getjToggleButtonModifier().setEnabled(false);
        jparamf.getJtextFieldTypeEau().setVisible(false);
        jparamf.getJLabelListerNorme().setVisible(false);
        jparamf.getJLabelTypeEau().setVisible(false);
        jparamf.getjToggleButtonListerNormes().setVisible(false);
        jparamf.setVisible(true);
    }
    
    
    public void enteteItext(Eau e , Document doc, PdfContentByte cb)throws Exception
    {
       Image image = Image.getInstance("C://Impression_ITEXT//minste.jpg");
		image.setAbsolutePosition(259f,775f);
                image.scaleAbsolute(41,46);
                image.setAlignment(Image.TEXTWRAP);
          Image img = Image.getInstance("C://Impression_ITEXT//cpcBB.jpg")      ;
                img.setAbsolutePosition(22f,700f);
                img.scaleAbsolute(69f,73f);
               // img.setAlignment(Image.TEXTWRAP);
         
         Phrase phrase = new Phrase(new StringTokenizer("MINSANTE", " ").nextToken(), new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK));
           ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, phrase, 259f, 770f, 0);
      /*  
         Phrase labRef = new Phrase("Laboratoire national de référence et de santé publique",new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
           ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, labRef, 1f, 694f, 0);*/
          
          Phrase RepCamer = new Phrase("  REPUBLIQUE DU CAMEROUN ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, RepCamer, 10f, 805f, 0);
          
          Phrase PaixTravailPatrie = new Phrase("  PAIX-TRAVAIL-PATRIE ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, PaixTravailPatrie, 24f, 795f, 0);
          
          
          Phrase RepCameroon = new Phrase("REPUBLIC OF CAMEROON ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, RepCameroon, 425f, 805f, 0);
          
          Phrase PeaceWorkFatherland = new Phrase("PEACE-WORK-FATHERLAND ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,PeaceWorkFatherland , 419f, 795f, 0);
          
             
             
          Phrase exelBio = new Phrase("   L'excellence en Biologie accessible à tous",new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, exelBio, 5f, 686f, 0);
            
         Phrase   cpc =new Phrase("CENTRE PASTEUR DU CAMEROUN : Laboratoire National de Référence et de Santé Publique",new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.BOLD, BaseColor.BLACK));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, cpc, 121f, 735f, 0);
         
         Phrase   cpc1 =new Phrase("Membre du Réseau International des Instituts Pasteur ",new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.BOLD, BaseColor.BLACK));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, cpc1, 180f, 725f, 0);
            
            
            
         Phrase Bpcpc = new Phrase("B.P : 1274 - YAOUNDE  CAMEROUN Internet :  http://www.pasteur-yaounde.org ",new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK));
            /*Anchor anchor = new Anchor("http://www.pasteur-yaounde.org");
                anchor.setReference("http://www.pasteur-yaounde.org");
                anchor.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 2, Font.NORMAL, BaseColor.WHITE));
          
           Bpcpc.add(anchor);*/
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, Bpcpc, 160f, 715f, 0);
            
         Phrase faxDema = new Phrase("Tel : (237) 22 23 10 15 / 22 23 18 03 - Télécopie : (237) 22 23 15 64 Couriel : cpc@pasteur-yaounde.org",new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK) );
           // faxDema.add();
            
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, faxDema, 125f, 705f, 0);
           
         Phrase servLhe = new Phrase("Service d'Hygiène Environnement section physico-chimie",new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, servLhe, 151f, 690f, 0);
         
         Phrase Bulletin = new Phrase("Bulletin d'analyse physicochimique",new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, Bulletin, 185f, 680f, 0);
         
         Phrase typEau = new Phrase("des "+e.getTypeEau()+" : "+e.geteauCategorie(),new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                     //METTRE UNE LIGNE
         LineSeparator line = new LineSeparator(100 ,100f,BaseColor.BLACK, 1, 671f);        
             //    Chunk ckline= new Chunk(line);
                 typEau.add(line);
          ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, typEau, 185f, 670f, 0);
            doc.add(img);
                doc.add(image);
            Phrase ligne = new Phrase(new Chunk(new LineSeparator(1f, 75, BaseColor.BLACK, Element.ALIGN_CENTER, 0.5f)));           
              ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, ligne, 50f, 660f, 0);
                     
    }
   
    
    @Action
    public void ImprimerEau() {
                    /*Variables de */
         //compteur des y
                
        Vector<Analyse> list_save = new Vector<Analyse>();
        float lastPosFich =500f;
        Eau e = getEauToMasterTable();
        if(e==null)
        {
          JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE EAU ");
          return;
        }
        
         
       /**
        *Remplir les infos du report
        * 
        * 
        */
       
       
       
        String path = "C://Impression_ITEXT//"+e.getNumEngLabo()+"_Analyse.pdf";
         Document doc= new Document(PageSize.A4);
         try{
        PdfWriter w = PdfWriter.getInstance(doc, new FileOutputStream(path));
     /*   PdfReader r = new PdfReader(path);
        PdfStamper stp = new PdfStamper(r, new FileOutputStream(path));*/
 
        PdfContentByte cb = new PdfContentByte(w) ;
 
       System.out.println("Dimension du document \n Longueur L= "+doc.getPageSize().getHeight()+" Largeur l = "+doc.getPageSize().getWidth());
         doc.open();
         cb =w.getDirectContent();
         enteteItext(e, doc, cb);                        
            Phrase ligne = new Phrase(new Chunk(new LineSeparator(1f, 75, BaseColor.BLACK, Element.ALIGN_CENTER, 0.5f)));           
                          
            Phrase nomDemandeur = new Phrase("Demandeur :  ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                nomDemandeur.add(e.getClient().getNomDemandeur());
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, nomDemandeur, 20f, 645f, 0);
         
        Phrase Bp = new Phrase("BP : ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK)); 
                 Bp.add(e.getClient().getBp()+"      ");
                 Bp.add(e.getClient().getVille()+"                                      ");
                 
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, Bp, 80f, 630f, 0);
         Phrase tel = new Phrase("                Tel :  ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                tel.add(" "+e.getClient().getTelDemandeur()) ;
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, tel, 150f, 615f, 0);
                 
                  Phrase fax = new Phrase("                        Fax :  ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                fax.add(" "+e.getClient().getFaxDemandeur()) ;
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, fax, 250f, 615f, 0);
                   
         Phrase mail = new Phrase("mail :  ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
                mail.add(" "+e.getClient().getEmailDemandeur()) ;
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, mail, 80f, 600f, 0);
                 
         Phrase prelev1 = new Phrase("Prelevement :", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK))         ;
         ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, prelev1, 20f, 585f, 0);
    
          Phrase prelev = new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK))         ;
         
         
                  prelev.add(new Phrase("Effectué par "+e.getPrelevePar()+"               "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDatePrelevement())+"               à  "+e.getLieuPrelevement(), new Font(Font.FontFamily.TIMES_ROMAN))) ; 
                  ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, prelev, 80f, 585f, 0);
    
       Phrase recep = new Phrase("Reçu au laboratoire le :              ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK))         ;
                  recep.add(new Phrase("               "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateReceptionLabo())+"             Num Enregistrement Labo : "+e.getNumEngLabo(), new Font(Font.FontFamily.TIMES_ROMAN))) ; 
                  ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, recep, 80f, 570f, 0);
              
       Phrase etiquetage = new Phrase("Etiquetage du demandeur :    "+e.getEtiquetageDemandeur() , new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD, BaseColor.BLACK)) ;
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, etiquetage, 80f, 555f, 0);
              
       Phrase numGlims = new Phrase("Num GLIMS : ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK)) ;
              numGlims.add(e.getNumEnrGlims());
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, numGlims, 20f, 540f, 0);
               if(e.getDateEffetPaiement()!=null)
               {Phrase paiement = new Phrase("Paiement :     "+e.getTypeDePayement()+"     Date d'effet :                         "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateEffetPaiement())+"           Num Commande :  "+e.getNumPaiement(),new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK) ) ;
              ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, paiement, 20f, 525f, 0);
                }
               else
               {Phrase paiement = new Phrase("Paiement:     "+e.getTypeDePayement()+"     Date d'effet :                                   Num Commande :  "+e.getNumPaiement(),new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK) ) ;
              ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, paiement, 20f, 525f, 0);
         }
              //METTRE UNE LIGNE 
               ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, ligne, 50f, 520f, 0);
         
              
       Phrase paraResUniCrit = new Phrase();//"Parametre                                        Resultat                                     Unite                                        Norme ",new Font(Font.FontFamily.TIMES_ROMAN, 11,	Font.BOLD, BaseColor.BLACK) ); 
              
          paraResUniCrit.add(new Chunk(tab1));
          paraResUniCrit.add(new Phrase("Paramètres", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
          
          paraResUniCrit.add(new Chunk(tab2));
          paraResUniCrit.add(new Phrase("Résultats", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
          
          paraResUniCrit.add(new Chunk(tab3));
          paraResUniCrit.add(new Phrase("Unités", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
          
          paraResUniCrit.add(new Chunk(tab4));
          if (!e.getTypeEau().equalsIgnoreCase("eaux usées"))
            paraResUniCrit.add(new Phrase("Normes", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
          else
            paraResUniCrit.add(new Phrase("Critères d'appréciation", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
              
          
          ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, paraResUniCrit, 50f, 500f, 0);
          
          
          
          
              //AFFICHAGE DES ANALYSES
            List<Analyse> list = metier.getAllAnalysesEau(e.getId().longValue());
       
       if(list==null||list.size()==0)
         {
              JOptionPane.showMessageDialog(null, "IL  N\' Y\'A PAS D\'ANALYSES DE PARAMETRES A IMPRIMER ");
               return;
       
         }
       /*PdfReader r = new PdfReader(path);
        
         PdfStamper stamper = new PdfStamper(r, new FileOutputStream(path));
          stamper.insertPage(2,r.getPageSize(1) );*/
       /*doc.newPage();
       enteteItext(e, doc, cb);
       //doc.add(paiement); 
       ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, paraResUniCrit, 50f, 560f, 0);
          */
       
       TypeEau typ = metier.getTypeEau(list.get(0).getEauAnalyse().getTypeEau());
       
       if(typ==null)
        {
                JOptionPane.showMessageDialog(null, "IL  N\' Y\'A PAS DE CLASSES D\'EAU  ");
               return;
       
        }
            int l =1;
            int page =1;
            int time = 1;
            float posprec = 0;     
            String  classParam = "";
           
       List classesAnalyses = metier.getClasseParamTypeEau(typ.getId());
       
       if(classesAnalyses!=null)
        {
          //imprime les analyses par classe
            int p = classesAnalyses.size();
            for(int i=0;i<p;i++)
            {  time = 1;
               classParam = classesAnalyses.get(i).toString();
                Phrase clp2 = new Phrase();
                      clp2.add(new Chunk(tab1));
                      Chunk cl = new Chunk(classParam,new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK));
                        cl.setUnderline(0.1f,-2f );
                      clp2.add(cl);
                      
                       /*AFFICHER LES ANALYSES PAR CLASSE*/
       List<Analyse> list2 = metier.getAllanalyseEauByClass(classParam, e.getId().longValue());
           if (!list2.isEmpty()&&lastPosFich>60f){
                           lastPosFich-=15f;
                           ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, clp2, 50f, lastPosFich, 0);
                                  for(Analyse a:list2)
        {
          System.out.println("AVANT INSERTION Analyse id ="+a.getId()+" nomParam"+a.getAnalyseNomParam()+" classe Parametre"+a.getAnalyseClasseParam()+" r ="+a.getAnalyseResult());
         // a.getEauAnalyse().getTypeEau();
        }       
       
                for(Analyse a:list2)
                 { System.out.println("  Analyse a = "+a.getAnalyseNomParam()+" classe = "+classParam+" Position P = "+lastPosFich);
                     
                    if(page==1)
                    {                
                           // posprec = (lastPosFich-10f*l);
                        if((lastPosFich-10f*l)>120f||(lastPosFich>80))
                          {   System.out.println(" Entrez dans la 1ere page i ="+i);
                            if((a.getAnalyseClasseParam().equals(classParam)))    //Ecrire la nouvelle classe
                                  {  System.out.println("  Analyse a = "+a.getAnalyseNomParam()+" classe = "+classParam+" Position P = "+lastPosFich+" l =="+l);
                                     lastPosFich=lastPosFich-10f;
                      //classParam = list.get(i).getAnalyseClasseParam();
                                     rempli_ligne(a,lastPosFich,cb);
                
                                  }
                   
                               l++;
                             //   posprec = (lastPosFich-10f*l);
                          }
                    else//créé une new page dans le document
                       {
                         new_Footer(doc,page,cb);
                
                l=1;
                /**
                 *Cè ici le pb qui est résolu dans print
                 */
                if(lastPosFich<90)//si plus d'espace pour écrire  sauvegarde les analyses non écrites sur cette page
                {   
                    if(!a.equals(null))
                      {
                         list_save.add(a);  
                         next = 1;
                      }
                    doc.newPage();
                    page++;
                    enteteItext(e, doc, cb);
                    lastPosFich = 660f;
                   
                }  
                /*rempli_ligne(a,posprec,cb);
                 lastPosFich-=10f;*/           
                
               System.out.println("  Analyse a = "+a.getAnalyseNomParam()+" classe = "+classParam+" Position P = "+lastPosFich);
                                     lastPosFich=lastPosFich-10f;
                       
             }
                             
            }
           else //page 2 et +++
                    {
              //s'ily'a des analyses précédentes qui n'ont pas été prises en compte les ajoutées
              int j =0;
              
            
                       
                        
             if((lastPosFich-10f*l)>120f)
              {   System.out.println(" Entrez dans la i "+page+" page i"+i);
                  if(classParam.equals(a.getAnalyseClasseParam()))    //Ecrire la nouvelle classe
                      {
                      lastPosFich=lastPosFich-10f;
                      //classParam = list.get(i).getAnalyseClasseParam();
                      rempli_ligne(a,lastPosFich,cb);
                
                       }
                   
                 l++;
              }
               
             else//ajoute une page
             {
             new_Footer(doc,page,cb);
                page++;
                l=1;
                doc.newPage();
                enteteItext(e, doc, cb);
                lastPosFich = 670f;
                   lastPosFich=lastPosFich-10f;
                      //classParam = list.get(i).getAnalyseClasseParam();
                      rempli_ligne(a,lastPosFich,cb);
                  
             }
                    }
                    
                     
                 }//fin parcours analyses de la keme classe
                if(!list_save.isEmpty())
                {   for(Analyse b:list_save)
                      {
                            System.out.println("  Analyse a = "+b.getAnalyseNomParam()+" classe = "+classParam+" Position P = "+lastPosFich+" l =="+l);
                            if(next==1){
                                    lastPosFich=lastPosFich-10f;
                      //classParam = list.get(i).getAnalyseClasseParam();
                                     rempli_ligne(b,lastPosFich,cb);
                                 }
                       }
                   next =0;
                }
            }
                
            }//fin parcours classes
        
        }
    /*   for(Analyse a:list)
        {
          System.out.println("Analyse id ="+a.getId()+" nomParam"+a.getAnalyseNomParam()+" classe Parametre"+a.getAnalyseClasseParam()+" r ="+a.getAnalyseResult());
         // a.getEauAnalyse().getTypeEau();
        }    */  
    //System.out.println("sortit de la boucle avec Y = "+lastPosFich)   ;
    
    // Gestion du pied de page
      if(lastPosFich>200f)
        {
          last_Footer(lastPosFich, page, cb,e);
        }
      else
      {    new_Footer(doc,page,cb);
                page++;
                //j=1;
                doc.newPage();
                enteteItext(e, doc, cb);
                lastPosFich=660f;
               last_Footer(lastPosFich, page, cb,e);
       
      
      }
       
       
       
          doc.close();
       
         }
         catch(Exception ex)
         {
            ex.printStackTrace();
         }
         
    JOptionPane.showMessageDialog(null, "FIN IMPRESSION");
    //affichage à l'écran par appel de jdesktopp
    
// On vérifie que la classe Desktop soit bien supportée :
if ( Desktop.isDesktopSupported() ) {
	// On récupère l'instance du desktop :
	Desktop desktop = Desktop.getDesktop();
	
	// On vérifie que la fonction open est bien supportée :
	if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    // Et on lance l'application associé au fichier pour l'ouvrir :
                    desktop.open(new File(path));
                } catch (IOException ex) {
                   // Logger.getLogger(JDialogAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
	}
}
    
    
    
    }

    @Action
    public void statistiqueMensuelle() {
       opstat = -1;
       //instancie l'objet
    JDialogAnalyseMensuel stat = new JDialogAnalyseMensuel(null, true, null, this);
      stat.setVisible(true);
    }

    @Action
    public void Statistiqueannuelle() {
      opstat = 1;
     JDialogAnalyseMensuel stat = new JDialogAnalyseMensuel(null, true, null, this);
      stat.setVisible(true);
   
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuContact;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemAjouterNorme;
    private javax.swing.JMenuItem jMenuItemAjouterparam;
    private javax.swing.JMenuItem jMenuItemListNorme;
    private javax.swing.JMenuItem jMenuItemListeParam;
    private javax.swing.JMenuItem jMenuItemModifierNorme;
    private javax.swing.JMenuItem jMenuItemModifierParam;
    private javax.swing.JMenu jMenuNormes;
    private javax.swing.JMenu jMenuParametres;
    private javax.swing.JMenu jMenuStatistiqueInferentielle;
    private javax.swing.JMenu jMenuStatistiqueLaboratoire;
    private javax.swing.JMenu jMenuTypeEau;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelArchivage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButtonExplorer;
    private javax.swing.JToggleButton jToggleButtonImporter;
    private javax.swing.JToggleButton jToggleButtonPurger;
    private javax.swing.JToggleButton jToggleButton_Delete_Eau;
    private javax.swing.JToggleButton jToggleButton_Modifier_Eau;
    private javax.swing.JToggleButton jToggleButton_New_Eau;
    private javax.swing.JToggleButton jToggleButton_Print_Eau;
    private org.jdesktop.swingx.JXTable mastertableClient;
    private org.jdesktop.swingx.JXTable mastertableEaux;
    // End of variables declaration//GEN-END:variables

    private void rempli_ligne( Analyse a, Float  lastPosFich,PdfContentByte cb) {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        Phrase p = new Phrase();
        
      // Phrase p  = new Phrase();
                // insert a tab
                p.add(new Chunk(tab1));
                // add the origina title
                if (a.getAnalyseResult() != null && a.getAnalyseResult() !="")
                {
                 // insert a tab
                p.add(new Chunk(tab1));
                 //ajoute le nom   
                p.add(new Phrase(a.getAnalyseNomParam(), new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK)));
                // inserseFloatt a tab
                p.add(new Chunk(tab2));
                // ajoute 
                /**/ 
                /*if(  (a.getAnalyseTypeResultat().equals("reel"))|| (a.getAnalyseTypeResultat().equals("reel<"))||(a.getAnalyseTypeResultat().equals("reel>")))
                {  Float f = Float.parseFloat(a.getAnalyseResult());
                  
                   p.add(new Phrase(""+f,new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK)));
                }
                else*/
                   p.add(new Phrase(a.getAnalyseResult(),new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK))); 
                
                // insert a tab
                p.add(new Chunk(tab3));
                // 
                if(a.getAnalyseUniteParam()!=null)
                {   String punit = a.getAnalyseUniteParam();
                 //if(punit.charAt(0)=='<'||punit.charAt(0)=='>')
                   p.add(new Phrase(punit,new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK)));
                 /*else
                        p.add(new Phrase("≤"+punit,new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK)));*/
                }
                // insert a tab
                p.add(new Chunk(tab4));
                // add the production year of the movie
                if(a.getAnalyseCritereParam()!=null)
                {   String punit = a.getAnalyseCritereParam();
                // if(punit.charAt(0)=='<'||punit.charAt(0)=='>')
                   p.add(new Phrase(punit,new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK)));
                /* else
                        p.add(new Phrase("≤"+punit,new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK)));*/
                }//add the paragraph to the document
              //  doc.add(p);
                p.add(Chunk.NEWLINE);
                //lastPosFich-=2f;
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, p, 50f, lastPosFich, 0);
               // lastPosFich-=2f;
                       
                }
            
        
    }

    private void new_Footer(Document doc,int page,PdfContentByte cb) {
        //throw new UnsupportedOperationException("Not yet implemented");
        //Ce rapport ne concerne que l'échantillon soumis à l'analyse. 
 
               Phrase posp = new Phrase("Page "+page,new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLD, BaseColor.BLACK));
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, posp, 450f, 50f, 0);
         
    }

     private void last_Footer(float lastpos,int page,PdfContentByte cb,Eau e) {
        //throw new UnsupportedOperationException("Not yet implemented");
        
         /*
          type de l'eau
          */
         //if (e.getTypeEau().equalsIgnoreCase("eaux propres"))
         //lastpos-=20f;
         
         if(lastpos>120f)
         {   if(e.getaEtoile()!=null)
         {  //affiche le groupe A*
         Phrase  aetoile= new Phrase(e.getaEtoile(),new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK));
              
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, aetoile, 50f, (lastpos), 0);
                        
         }
         
         if(e.getNDEtoile()!= null)
         {
           //affiche ND*
         Phrase  ndtoile= new Phrase(e.getNDEtoile(),new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.NORMAL, BaseColor.BLACK));
                lastpos-=10f;
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, ndtoile, 50f, (lastpos), 0);               
         }
         
         if(e.getEauGroupe()!= null)
         
           {
           //affiche ND*
        // Phrase  groupe= new Phrase(e.getEauGroupe(),new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLD, BaseColor.BLACK));
                  
               //4 lignes maximum pour le groupe/classe            
         
         String[] ch = e.getEauGroupe().split(";");
        /* if(!ch[0].equals("")){
         Phrase  groupe= new Phrase(""+ch[0],new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLDITALIC, BaseColor.BLACK));
         lastpos-=10f;
          ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, groupe, 50f, (lastpos), 0);
      
           }
          if(!ch[1].equals(""))
      {
             Phrase  group1= new Phrase(""+ch[1],new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLDITALIC, BaseColor.BLACK));
         lastpos-=10f;
          ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, group1, 50f, (lastpos), 0);
      
      }
          */
          
          //System.out.println("lastposfic groupe = "+lastpos)     ;
         lastpos-=5f;
         for(int i=0;i<ch.length&&(i<4);i++)
          {
              Phrase  groupi= new Phrase("                     "+ch[i],new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLDITALIC, BaseColor.BLACK));
              /*float j*/lastpos =  (lastpos -10f) ;
             // lastpos-=j;
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, groupi, 20f, lastpos, 0);
           
          }             
         }
         
         
         /**
          *Conclusion
          * Traiter cas d'une conclusion multi lignes
          */
         lastpos -=25f;
         if(e.getConclusion()!=null)
         {  String[] ch = e.getConclusion().split("\n");
        Phrase  conclu= new Phrase("Conclusion : "+ch[0],new Font(Font.FontFamily.TIMES_ROMAN, 11,	Font.BOLDITALIC, BaseColor.BLACK));
          lastpos-=12.5f;      
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, conclu, 50f, (lastpos), 0);
      System.out.println("lastposfic groupe = "+lastpos)     ;
            
         for(int i=1;i<ch.length&&(i<7);i++)
          {
              Phrase  conclui= new Phrase("                     "+ch[i],new Font(Font.FontFamily.TIMES_ROMAN, 11,	Font.BOLDITALIC, BaseColor.BLACK));
              /*float- j*/lastpos =  (lastpos - 10f) ;
             // lastpos-=j;
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, conclui, 50f, lastpos, 0);
           
          }
         
            
         }
     }
         
         
        //Ce rapport ne concerne que l'échantillon soumis à l'analyse. 
        Phrase note1 =  new Phrase("Ce rapport ne concerne que l\'échantillon soumis à l\'analyse. La reproduction de ce document, si elle n'est pas certifiée conforme par"+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
        //Phrase note2 = new Phrase("La reproduction de ce document, si elle n'est pas certifiée conforme par le Directeur Général  du Centre Pasteur"+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));            
        Phrase note3 = new Phrase ("Le Directeur Général  du Centre Pasteur du Cameroun ou le Chef de service  SHE pc, n'a aucune valeur administrative ou scientifique."+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
                 
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, note1, 50f, 120f, 0);
        //ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, note2, 50f, 113, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, note3, 50f, 106f, 0);
        
         
     /*   Phrase noteService =new Phrase()   ;
        noteService.add(note1);
        noteService.add(note2);
        noteService.add(note3);
        
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, noteService, 50f, 80f, 0);*/
         
               Phrase  date= new Phrase("Yaoundé : le "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateLivraison()),new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK));
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, date, 50f, 80f, 0);
         
               Phrase  responsable= new Phrase(e.getResponssable(),new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK));
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, responsable, 50f, 60f, 0);
               
               Phrase  chefservice= new Phrase("Chef de service",new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK));
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, chefservice, 50f, 50f, 0);
               
         
               
               Phrase posp = new Phrase("Page "+page,new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLD, BaseColor.BLACK));
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, posp, 500f, 50f, 0);
         
    }
    
    @Action
    public void CreeGroupeClasse() {
   JDialogGroupClass GroupClass = new JDialogGroupClass(null, true,this,null);
      GroupClass.setVisible(true);
      
    }

    @Action
    public void connexionJiDBase() {
        System.out.println("JADMIN  connexion aux bds");
        chDBase cpcBD = new chDBase();
       // cpcBD.setVisible(true);
    }

    @Action
    public void importerbd() {
        System.out.println("JADMIN importation des données");
    }

    @Action
    public void statistiqueGlobale() {

     statGlobal st = new statGlobal();
     st.setVisible(true);
    
    }

    @Action
    public void ArchiveData() {
    }

}
