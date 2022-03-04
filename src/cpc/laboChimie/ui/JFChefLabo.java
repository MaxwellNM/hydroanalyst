/*
 * GrippeSMSView.java
 */
package cpc.laboChimie.ui;

/*import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
*/
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.mysql.jdbc.Connection;
import cpc.laboChimie.AuthentificationView;
import cpc.laboChimie.HydroAboutBox;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.jpa.EauCategorie;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import cpc.laboChimie.model.TableModelClient;
import cpc.laboChimie.model.TableModelEaux;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.TypeEau;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.StringTokenizer;



/**
 * The application's main frame.
 */
public class JFChefLabo extends FrameView {
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
    private statGlobal jfstat;
    
    
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon=null;;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private List<EauCategorie> list_categorie;
   
    
    
    
    //variables pour l'impression des analyses
        Chunk tab1 = new Chunk(new VerticalPositionMark(), 10, true);//nom
        Chunk tab2 = new Chunk(new VerticalPositionMark(), 200, true);//rsultat
        Chunk tab3 = new Chunk(new VerticalPositionMark(), 300, true);//unite
        Chunk tab4 = new Chunk(new VerticalPositionMark(), 400, true);//critere
        private int opstat;
    private int next;
 
    public JFChefLabo(SingleFrameApplication app,AuthentificationView aute) {
        super(app);
//code ajouté

//        metierSMS = ParametreGestion.getInstance().getMetierSMS();
        //fin ajout
        //met la liste des clients dans le jtable
        initSpring();
        initComponents();
        this.aut = aute;
        this.getFrame().setTitle("Chef de Laboratoire");
        this.getFrame().setResizable(true);
        this.getFrame().setSize(1360, 700);
        mastertableClient.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mastertableEaux.getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
       // mastertableClient.getColumn(0).setHeaderValue("");
        mastertableClient.getColumn(0).getHeaderRenderer();
        mastertableClient.setHorizontalScrollEnabled(true);
        this.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                        aut.quitter();
                    }
                });

           ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
       /* for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }*/
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
      //  idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        //statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });


    }
    
    public int getOpStat()
    {
      return opstat;
    }

        ////////////////////////////////
    public IMetier getMetier() {
        return metier;
    }

    
    public void setMetier(IMetier metierSMS) {
        this.metier = metierSMS;
    }
    ////Initialisation de l'application
    ParametreGestion parametreGestion;

    public int getOpNorme()
    {
       return Opnorme;
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

    
    /*
     *objet modifiables que l'on peut rendre invisible
      */
    
    public JMenu getMenuTypesEaux()
    {
       return jMenuTypeEau;
    }
    
    public JMenu getMenuNorme()
    {
      return jMenuNormes;
    }
    
    
    
    public JMenu getMenuParametre()
    {
      return jMenuParametres;
    }
    
    
    
    public JMenu getstatinferentielle()
    {
       return jMenuStatistiqueInferentielle;
    }
    
    public JToggleButton getBoutonsuppAnalyse()
    {
      return jToggleButtonSupAnalyse;
      
    }
    
    public JToggleButton getBoutonImprimer()
    {
      return jToggleButtonImprimerAnalyse;
        }
    
    public TableModelClient getTableModelClient() {
        return tableModelClient;
    }

    public void setTableModelClient(TableModelClient tableModelClient) {
        this.tableModelClient = tableModelClient;
    }

    public JDialogClient getjDialogClient() {
        return jDialogClient;
    }

    public void setjDialogClient(JDialogClient jDialogNouveauClient) {
        this.jDialogClient = jDialogNouveauClient;
    }

    
    public statGlobal getjFrameStatGlobal() {
        return jfstat;
    }

    public void setjFrameStatGlobal(statGlobal stat) {
        this.jfstat = stat;
    }

    
   
    public void fermer2()
    {
         this.setjFrameStatGlobal(null);
    
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainPanel1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        jXTaskPane1 = new org.jdesktop.swingx.JXTaskPane();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jXTaskPane2 = new org.jdesktop.swingx.JXTaskPane();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButtonSupAnalyse = new javax.swing.JToggleButton();
        jToggleButtonImprimerAnalyse = new javax.swing.JToggleButton();
        jXTaskPane3 = new org.jdesktop.swingx.JXTaskPane();
        jToggleButton5 = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jXLabel1 = new org.jdesktop.swingx.JXLabel();
        jPanel4 = new javax.swing.JPanel();
        masterTableNotification = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mastertableClient = new org.jdesktop.swingx.JXTable();
        jToggleButton4 = new javax.swing.JToggleButton();
        masterTableNotification1 = new org.jdesktop.swingx.JXTitledPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mastertableEaux = new org.jdesktop.swingx.JXTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
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
        jMenuStatistiqueInferentielle = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuContact = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        mainPanel1.setName("mainPanel1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jXTaskPaneContainer1.setName("jXTaskPaneContainer1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getResourceMap(JFChefLabo.class);
        jXTaskPane1.setTitle(resourceMap.getString("jXTaskPane1.title")); // NOI18N
        jXTaskPane1.setName("jXTaskPane1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(cpc.laboChimie.mainApp.class).getContext().getActionMap(JFChefLabo.class, this);
        jToggleButton1.setAction(actionMap.get("creerclient")); // NOI18N
        jToggleButton1.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton1.setText(resourceMap.getString("jToggleButton1.text")); // NOI18N
        jToggleButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton1.setName("jToggleButton1"); // NOI18N
        jToggleButton1.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane1.getContentPane().add(jToggleButton1);

        jToggleButton2.setAction(actionMap.get("modifierClient")); // NOI18N
        jToggleButton2.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton2.setText(resourceMap.getString("jToggleButton2.text")); // NOI18N
        jToggleButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton2.setName("jToggleButton2"); // NOI18N
        jToggleButton2.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane1.getContentPane().add(jToggleButton2);

        jXTaskPaneContainer1.add(jXTaskPane1);

        jXTaskPane2.setTitle(resourceMap.getString("jXTaskPane2.title")); // NOI18N
        jXTaskPane2.setName("jXTaskPane2"); // NOI18N

        jToggleButton8.setAction(actionMap.get("NouvelleEau")); // NOI18N
        jToggleButton8.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton8.setText(resourceMap.getString("jToggleButton8.text")); // NOI18N
        jToggleButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton8.setName("jToggleButton8"); // NOI18N
        jToggleButton8.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane2.getContentPane().add(jToggleButton8);

        jToggleButton9.setAction(actionMap.get("modifierEau")); // NOI18N
        jToggleButton9.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton9.setText(resourceMap.getString("jToggleButton9.text")); // NOI18N
        jToggleButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton9.setName("jToggleButton9"); // NOI18N
        jToggleButton9.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane2.getContentPane().add(jToggleButton9);

        jToggleButtonSupAnalyse.setAction(actionMap.get("supprimerEau")); // NOI18N
        jToggleButtonSupAnalyse.setText(resourceMap.getString("jToggleButtonSupAnalyse.text")); // NOI18N
        jToggleButtonSupAnalyse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonSupAnalyse.setName("jToggleButtonSupAnalyse"); // NOI18N
        jToggleButtonSupAnalyse.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane2.getContentPane().add(jToggleButtonSupAnalyse);

        jToggleButtonImprimerAnalyse.setAction(actionMap.get("ImprimerEau")); // NOI18N
        jToggleButtonImprimerAnalyse.setText(resourceMap.getString("jToggleButtonImprimerAnalyse.text")); // NOI18N
        jToggleButtonImprimerAnalyse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonImprimerAnalyse.setName("jToggleButtonImprimerAnalyse"); // NOI18N
        jToggleButtonImprimerAnalyse.setPreferredSize(new java.awt.Dimension(115, 30));
        jXTaskPane2.getContentPane().add(jToggleButtonImprimerAnalyse);

        jXTaskPaneContainer1.add(jXTaskPane2);

        jXTaskPane3.setName("jXTaskPane3"); // NOI18N

        jToggleButton5.setAction(actionMap.get("QuitterApp")); // NOI18N
        jToggleButton5.setFont(resourceMap.getFont("jToggleButton2.font")); // NOI18N
        jToggleButton5.setText(resourceMap.getString("jToggleButton5.text")); // NOI18N
        jToggleButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton5.setName("jToggleButton5"); // NOI18N
        jToggleButton5.setPreferredSize(new java.awt.Dimension(125, 30));
        jXTaskPane3.getContentPane().add(jToggleButton5);

        jXTaskPaneContainer1.add(jXTaskPane3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXTaskPaneContainer1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
        );

        jPanel2.setName("jPanel2"); // NOI18N

        jPanel3.setBackground(resourceMap.getColor("jPanel3.background")); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jXLabel1.setBackground(resourceMap.getColor("jXLabel1.background")); // NOI18N
        jXLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jXLabel1.setText(resourceMap.getString("jXLabel1.text")); // NOI18N
        jXLabel1.setFont(resourceMap.getFont("jXLabel1.font")); // NOI18N
        jXLabel1.setName("jXLabel1"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setName("jPanel4"); // NOI18N

        masterTableNotification.setTitle(resourceMap.getString("masterTableNotification.title")); // NOI18N
        masterTableNotification.setTitleFont(resourceMap.getFont("masterTableNotification.titleFont")); // NOI18N
        masterTableNotification.setTitleForeground(resourceMap.getColor("masterTableNotification.titleForeground")); // NOI18N
        masterTableNotification.setToolTipText(resourceMap.getString("masterTableNotification.toolTipText")); // NOI18N
        masterTableNotification.setName("masterTableNotification"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mastertableClient.setModel(tableModelClient);
        mastertableClient.setModel(tableModelClient);
        mastertableClient.setName("mastertableClient"); // NOI18N
        mastertableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mastertableClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(mastertableClient);

        jToggleButton4.setAction(actionMap.get("supprimerClient")); // NOI18N
        jToggleButton4.setText(resourceMap.getString("jToggleButton4.text")); // NOI18N
        jToggleButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButton4.setName("jToggleButton4"); // NOI18N
        jToggleButton4.setPreferredSize(new java.awt.Dimension(125, 30));

        javax.swing.GroupLayout masterTableNotificationLayout = new javax.swing.GroupLayout(masterTableNotification.getContentContainer());
        masterTableNotification.getContentContainer().setLayout(masterTableNotificationLayout);
        masterTableNotificationLayout.setHorizontalGroup(
            masterTableNotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );
        masterTableNotificationLayout.setVerticalGroup(
            masterTableNotificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );

        masterTableNotification1.setTitle(resourceMap.getString("masterTableNotification1.title")); // NOI18N
        masterTableNotification1.setTitleFont(resourceMap.getFont("masterTableNotification1.titleFont")); // NOI18N
        masterTableNotification1.setTitleForeground(resourceMap.getColor("masterTableNotification1.titleForeground")); // NOI18N
        masterTableNotification1.setToolTipText(resourceMap.getString("masterTableNotification1.toolTipText")); // NOI18N
        masterTableNotification1.setName("masterTableNotification1"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        mastertableEaux.setModel(tableModelEaux);
        mastertableEaux.setName("mastertableEaux"); // NOI18N
        mastertableEaux.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mastertableEauxMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mastertableEaux);

        javax.swing.GroupLayout masterTableNotification1Layout = new javax.swing.GroupLayout(masterTableNotification1.getContentContainer());
        masterTableNotification1.getContentContainer().setLayout(masterTableNotification1Layout);
        masterTableNotification1Layout.setHorizontalGroup(
            masterTableNotification1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );
        masterTableNotification1Layout.setVerticalGroup(
            masterTableNotification1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(masterTableNotification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(masterTableNotification1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(masterTableNotification, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masterTableNotification1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanel1Layout = new javax.swing.GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1204, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(52, Short.MAX_VALUE)))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenuTypeEau.setAction(actionMap.get("creeTypeEau")); // NOI18N
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

        menuBar.add(jMenuTypeEau);

        jMenuNormes.setAction(actionMap.get("ModifierNorme")); // NOI18N
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

        menuBar.add(jMenuNormes);

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

        menuBar.add(jMenuParametres);

        jMenuStatistiqueLaboratoire.setText(resourceMap.getString("jMenuStatistiqueLaboratoire.text")); // NOI18N
        jMenuStatistiqueLaboratoire.setName("jMenuStatistiqueLaboratoire"); // NOI18N

        jMenuItem9.setAction(actionMap.get("statMensuelle")); // NOI18N
        jMenuItem9.setText(resourceMap.getString("jMenuItem9.text")); // NOI18N
        jMenuItem9.setName("jMenuItem9"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jMenuItem9);

        jSeparator10.setName("jSeparator10"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jSeparator10);

        jMenuItem10.setAction(actionMap.get("StatAnnuelle")); // NOI18N
        jMenuItem10.setText(resourceMap.getString("jMenuItem10.text")); // NOI18N
        jMenuItem10.setName("jMenuItem10"); // NOI18N
        jMenuStatistiqueLaboratoire.add(jMenuItem10);

        menuBar.add(jMenuStatistiqueLaboratoire);

        jMenuStatistiqueInferentielle.setText(resourceMap.getString("jMenuStatistiqueInferentielle.text")); // NOI18N
        jMenuStatistiqueInferentielle.setName("jMenuStatistiqueInferentielle"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenuStatistiqueInferentielle.add(jSeparator1);

        jMenuItem14.setAction(actionMap.get("statistiqueParametre")); // NOI18N
        jMenuItem14.setText(resourceMap.getString("jMenuItem14.text")); // NOI18N
        jMenuItem14.setName("jMenuItem14"); // NOI18N
        jMenuStatistiqueInferentielle.add(jMenuItem14);

        jSeparator9.setName("jSeparator9"); // NOI18N
        jMenuStatistiqueInferentielle.add(jSeparator9);

        menuBar.add(jMenuStatistiqueInferentielle);

        jMenuContact.setText(resourceMap.getString("jMenuContact.text")); // NOI18N
        jMenuContact.setName("jMenuContact"); // NOI18N

        jMenuItem16.setAction(actionMap.get("AproposDe")); // NOI18N
        jMenuItem16.setText(resourceMap.getString("jMenuItem16.text")); // NOI18N
        jMenuItem16.setName("jMenuItem16"); // NOI18N
        jMenuContact.add(jMenuItem16);

        menuBar.add(jMenuContact);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1184, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(1011, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(statusMessageLabel)
                        .addComponent(statusAnimationLabel))
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void mastertableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mastertableClientMouseClicked

        // TODO add your handling code here:

        //on récupère le client sélectionné, on supprime
        //la liste des analyses présentes sur la table analyse
        //et on affiche la liste des analyses qu'il a déjà éffectué

        if (getClientToMasterTable() != null) {
            List<Eau> eau = metier.getEauByClient(getClientToMasterTable());
            tableModelEaux.removeAll();
            tableModelEaux.addRows(eau);
            System.out.println("Wanda");
        }

    }//GEN-LAST:event_mastertableClientMouseClicked

    private void mastertableEauxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mastertableEauxMouseClicked
        // TODO add your handling code here:

        System.out.println("///////////////En plein dans le master table eau ////////////" + getEauToMasterTable());
    }//GEN-LAST:event_mastertableEauxMouseClicked

    
    
    
    
    @Action
    public void QuitterApp() {

        aut.quitter();
    }

    @Action
    public void creeTypeEau() {
        JDialogcreerTypeEau tpEau = new JDialogcreerTypeEau(null,true,this,null);
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
        if(e==null)
          {
             JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE EAU");
             return;
          }
        
        if(Jeau==null)
        Jeau = new JDialogEau(null, true, this,null);
         System.out.println("NOUS MMODIFIONS EAU.........."+e.getId().longValue());
         Jeau = new JDialogEau(null,true,this,null);
        Jeau.getJlabelIdClientEau().setVisible(false);
        Jeau.getjtexFieldidClient().setVisible(false);
        Jeau.getJlabelidEau().setVisible(false);
        Jeau.getIdEau().setVisible(false);
        
        Jeau.getjToggleButtonEnregistrer().setEnabled(false);
        Jeau.setTitle("MODIFICATION DE L\'EAU "+e.getNumEngLabo());
//        jDialogNouveauClient.getjToggleButtonImprimer().setEnabled(true);
        //désactive et rend invisible les éléments suivant
        Jeau.getJlabelTypeEau().setVisible(false);
        Jeau.getJComboBoxTypeEau().setVisible(false);
        /*
         *Remplir la JCombox des catégories
         */
      /*  list_categorie = metier.getAllEauCategorie();
        
            for (EauCategorie ec : list_categorie)
            {
              if(ec!=null && (ec.getTypeEau().getId()==e.getId()))
                  Jeau.getJComboBoxCategorieEau().addItem(ec.getCategorie().toString());
              System.out.println("Catégorie Eau "+ec.getCategorie().toString());
            }*/
        
        Jeau.getjToggleButtonModifier().setEnabled(true);
        if (e!= null ) {
            
            /*
         *Remplir la JCombox des catégories
         */
        /*list_categorie = metier.getAllEauCategorie();
        
            for (EauCategorie ec : list_categorie)
            {
              if(ec!=null && (ec.getTypeEau().getId()==e.getId()))
                  Jeau.getJComboBoxCategorieEau().addItem(ec.getCategorie().toString());
              System.out.println("Catégorie Eau "+ec.getCategorie().toString()+"#");
            }
        */
            Jeau.setEauInterface(getEauToMasterTable());
            Jeau.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "VEUILLEZ SELECTIONNER UNE EAU");
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
        {  Jclient = new JDialogClient(null,true,null,this,null);
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
        jDialogClient = new JDialogClient(null,true,null,this,null);
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
          if(Jeau==null){
        Jeau = new JDialogEau(null,true,this,null);
        Jeau.setTitle("AJOUT D\'UNE EAU");
        Jeau.getJlabelIdClientEau().setVisible(false);
        Jeau.getjtexFieldidClient().setVisible(false);
        Jeau.getJlabelidEau().setVisible(false);
        Jeau.getIdEau().setVisible(false);
        System.out.println(" Bienvennue dans la fenetre eau");
         
           }
         Jeau.getjToggleButtonEnregistrer().setEnabled(true);
         //desactiv le bouton de modification

           Jeau.getjToggleButtonModifier().setEnabled(false);
           Jeau.setVisible(true);
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
            //  tableModelEaux.addRows(metier.getEauByClient(this.getClientToMasterTable()));

           }

      
    }

    @Action
    public void modifierTYpeEAu() {
       JDialogTypEau jt = new JDialogTypEau(null,true,this,null);
       jt.setVisible(true);
    }

    @Action
    public void CreerNorme() {
        //Ajout d'une norme //fonction du type d'eau
     Opnorme = -1;
    if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,this,null);
           JChoixEau.setVisible(true);
        }
    
    }

    @Action
    public void ModifierNorme() {
      
        //Modifier une norme fonction du type d'eau
        Opnorme = 0;
    if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,this,null);
           JChoixEau.setVisible(true);
        }
    }

    @Action
    public void consulterParametre() {
       //Liste des Parametres disponibles et ajout sii possible
        jparamf = new JFrameParametrefinal(null, true, null, null,null, this,null)  ;
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
        if(JChoixEau==null)
        {  JChoixEau = new JDialogChoixTypeEau(null,true,this,null);
           JChoixEau.setVisible(true);
        }
        
    }

    @Action
    public void AjouterParametre() {
        //i option = -1
        
        
        
        
        jparamf = new JFrameParametrefinal(null, true, null, null, null,this,null)  ;
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
         
        jparamf = new JFrameParametrefinal(null, true, null, null, null,this,null)  ;
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
          
          Phrase RepCamer = new Phrase(" REPUBLIQUE DU CAMEROUN ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, RepCamer, 10f, 805f, 0);
          
          Phrase PaixTravailPatrie = new Phrase(" PAIX-TRAVAIL-PATRIE ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, PaixTravailPatrie, 24f, 795f, 0);
          
          
          Phrase RepCameroon = new Phrase("REPUBLIC OF CAMEROON ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, RepCameroon, 425f, 805f, 0);
          
          Phrase PeaceWorkFatherland = new Phrase("PEACE-WORK-FATHERLAND ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.NORMAL, BaseColor.BLACK));
             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,PeaceWorkFatherland , 419f, 795f, 0);
          
             
             
          Phrase exelBio = new Phrase("    L'excellence en Biologie accessible à tous",new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, exelBio, 5f, 686f, 0);
            
         Phrase   cpc =new Phrase("CENTRE PASTEUR DU CAMEROUN :  Laboratoire National de Référence et de Santé Publique",new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.BOLD, BaseColor.BLACK));
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
           
         Phrase servLhe = new Phrase("Service d'Hygiène Environnement section physicochimie",new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK));
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
         
         
                  prelev.add(new Phrase("Effectué par "+e.getPrelevePar() +"              "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDatePrelevement())+"               à  "+e.getLieuPrelevement(), new Font(Font.FontFamily.TIMES_ROMAN))) ; 
                  ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, prelev, 80f, 585f, 0);
    
       Phrase recep = new Phrase("Reçu au laboratoire le :             ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK))         ;
                  recep.add(new Phrase("               "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateReceptionLabo())+"             Num Enregistrement Labo : "+e.getNumEngLabo(), new Font(Font.FontFamily.TIMES_ROMAN))) ; 
                  ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, recep, 80f, 570f, 0);
              
       Phrase etiquetage = new Phrase("Etiquetage du demandeur :    "+e.getEtiquetageDemandeur() , new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD, BaseColor.BLACK)) ;
                 ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, etiquetage, 80f, 555f, 0);
              
       Phrase numGlims = new Phrase("Num GLIMS : ", new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK)) ;
              numGlims.add(e.getNumEnrGlims());
            ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, numGlims, 20f, 540f, 0);
               if(e.getDateEffetPaiement()!=null)
               {Phrase paiement = new Phrase("Paiement :     "+e.getTypeDePayement()+"     Date d'effet :                        "+DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateEffetPaiement())+"           Num Commande :  "+e.getNumPaiement(),new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK) ) ;
              ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, paiement, 20f, 525f, 0);
                }
               else
               {Phrase paiement = new Phrase("Paiement:     "+e.getTypeDePayement()+"     Date d'effet:                                     Num Commande:  "+e.getNumPaiement(),new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLD, BaseColor.BLACK) ) ;
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
          paraResUniCrit.add(new Phrase("Normes", new Font(Font.FontFamily.TIMES_ROMAN, 12,	Font.BOLD, BaseColor.BLACK)));
          
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
              {   System.out.println(" Entrez dans la i"+page+"e page i"+i);
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
       for(Analyse a:list)
        {
          System.out.println("Analyse id ="+a.getId()+" nomParam"+a.getAnalyseNomParam()+" classe Parametre"+a.getAnalyseClasseParam()+" r ="+a.getAnalyseResult());
         // a.getEauAnalyse().getTypeEau();
        }      
    System.out.println("sortit de la boucle avec Y = "+lastPosFich)   ;
    
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
                if(  (a.getAnalyseTypeResultat().equals("reel"))|| (a.getAnalyseTypeResultat().equals("reel<"))||(a.getAnalyseTypeResultat().equals("reel>")))
                {  Float f = Float.parseFloat(a.getAnalyseResult());
                  
                   p.add(new Phrase(""+f,new Font(Font.FontFamily.TIMES_ROMAN, 9,	Font.NORMAL, BaseColor.BLACK)));
                }
                else
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
        
               Phrase posp = new Phrase("Page "+page,new Font(Font.FontFamily.TIMES_ROMAN, 8,	Font.BOLD, BaseColor.BLACK));
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, posp, 450f, 50f, 0);
         
  
    }

    private void last_Footer(float lastpos,int page,PdfContentByte cb,Eau e) {
        //throw new UnsupportedOperationException("Not yet implemented");
         /*
          type de l'eau
          */
         lastpos-=20f;
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
         
         if(e.getConclusion()!=null)
         {  String[] ch = e.getConclusion().split("\n");
        Phrase  conclu= new Phrase("Conclusion : "+ch[0],new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLDITALIC, BaseColor.BLACK));
          lastpos-=12.5f;      
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, conclu, 50f, (lastpos), 0);
      System.out.println("lastposfic groupe = "+lastpos)     ;
            
         for(int i=1;i<ch.length&&(i<7);i++)
          {
              Phrase  conclui= new Phrase("                     "+ch[i],new Font(Font.FontFamily.TIMES_ROMAN, 10,	Font.BOLDITALIC, BaseColor.BLACK));
              /*float- j*/lastpos =  (lastpos - 10f) ;
             // lastpos-=j;
               ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, conclui, 50f, lastpos, 0);
           
          }
         
            
         }
     }
         
         
        //Ce rapport ne concerne que l'échantillon soumis à l'analyse. 
        Phrase note1 =  new Phrase("Ce rapport ne concerne que l\'échantillon soumis à l\'analyse"+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
        Phrase note2 = new Phrase("La reproduction de ce document, si elle n'est pas certifiée conforme par le Directeur Général  du Centre Pasteur"+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));            
        Phrase note3 = new Phrase ("du Cameroun ou le Chef du laboratoire  LHE, n'a aucune valeur administrative ou scientifique."+Chunk.NEWLINE,new Font(Font.FontFamily.TIMES_ROMAN, 7,	Font.NORMAL, BaseColor.BLACK));
                 
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, note1, 50f, 120f, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, note2, 50f, 113, 0);
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
    public void statMensuelle() {
 opstat = -1;
     JDialogAnalyseMensuel stat = new JDialogAnalyseMensuel(null, true, this, null);
      stat.setVisible(true);
    
    
    }

    @Action
    public void StatAnnuelle() {
     opstat = 1;
     JDialogAnalyseMensuel stat = new JDialogAnalyseMensuel(null, true, this, null);
      stat.setVisible(true);
    }

    @Action
    public void CreeGroupeClasse() {
      JDialogGroupClass GroupClass = new JDialogGroupClass(null, true,null,this);
      GroupClass.setVisible(true);
     
    }

    @Action
    public void statistiqueParametre() {

      statGlobal stat = new statGlobal();
      stat.setVisible(true);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenuContact;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
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
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    private javax.swing.JToggleButton jToggleButtonImprimerAnalyse;
    private javax.swing.JToggleButton jToggleButtonSupAnalyse;
    private org.jdesktop.swingx.JXLabel jXLabel1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane1;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane2;
    private org.jdesktop.swingx.JXTaskPane jXTaskPane3;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel mainPanel1;
    private org.jdesktop.swingx.JXTitledPanel masterTableNotification;
    private org.jdesktop.swingx.JXTitledPanel masterTableNotification1;
    private org.jdesktop.swingx.JXTable mastertableClient;
    private org.jdesktop.swingx.JXTable mastertableEaux;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
   
    
}