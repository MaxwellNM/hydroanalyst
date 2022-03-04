/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

import cpc.laboChimie.jpa.Analyse;
import cpc.laboChimie.jpa.Client;
import cpc.laboChimie.jpa.Eau;
import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JFileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.JXDatePicker;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author MAXWELL
 */
public class ManagerXML {
    
    //Properties
    private  String nomFichier;
    
    private  Date dateArchivage;
    
    private  Date dateImport;
    
    private Element root;
    
    private Document document;
    
    private Date dateDeb = null;
    
    private Date dateFin = null;

    private String choixTypEau = null;
    
    
    private IMetier metier = null;
    
    private Vector<Eau> listEau = null;
    
    ////Initialisation de l'application
    private ParametreGestion parametreGestion;

    //Constructor
    public ManagerXML( String choxTypEau){
        
        saveMap();
        
        nomFichier =chooser.getSelectedFile().toString();//".\\BdEau.xml";
        root= new Element("EAUX");
        document = new Document(root);
        createXMLFile();
        choixTypEau = choxTypEau;
        //recupere la couche metier
        initSpring();
    
    }
 JFileChooser chooser;   
 public void saveMap() {
    String sb = "TEST CONTENT";
    chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("C:\\Users"));
    int retrival = chooser.showSaveDialog(null);
    if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
            //FileWriter fw = new FileWriter(chooser.getSelectedFile());
            System.out.println("Nom du fichier :"+chooser.getSelectedFile());
           // fw.write(sb.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
    
public void openFile(){

 chooser = new JFileChooser();
chooser.setCurrentDirectory(new File("C:\\Users"));
int result = chooser.showOpenDialog(null);
if (result == JFileChooser.APPROVE_OPTION) {
    File selectedFile = chooser.getSelectedFile();
    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
    nomFichier=""+selectedFile.getAbsolutePath();
}    
}
    public ManagerXML(Date deb, Date fin, String st) {
        //throw new UnsupportedOperationException("Not yet implemented");
        //JFileChooser file = new JFileChooser(nomFichier);
        
        //recupere le chemin absolu du fichier
        
        root= new Element("EAUX");
        listEau = new Vector<Eau>();
        document = new Document(root);
        dateDeb = deb;
        dateFin = fin;
        choixTypEau = st;
        //recupere la couche metier
        initSpring();
    }
    
    //Getters and Setters

    private String getChoixTypEau()
    {
        return choixTypEau;
    }
            
    public Date getPeriodDebutArchivage()
    {
        return dateDeb;
    }
    public Date getPeriodFinArchivage()
    {
        return dateFin;
    }

    
    public Date getDateArchivage()
    {
        return dateArchivage;
    }

    public void setDateArchivage(Date d)
    {
       dateArchivage=d;
    }
    
    public String getNomFichier(){
        return nomFichier;
    }
    
    public void setNomFichier(String nom){
        nomFichier=nom;
    }
    public Element getRootXML(){
        return root;
    }
    
    public void setRootXML(Element rt){
        root=rt;
    }    
    
    public Document getDomDocument(){
        return document;
    }
    
    public void setDomDocument(Document nom){
        document=nom;
    }
    
    public Vector getListEau(){
        
        return listEau;
    }
    
    public void setListEau(Vector ls){
        
         listEau=ls;
    }
    
    //methods
    
    private void initSpring()
    {
        parametreGestion = ParametreGestion.getInstance();
        metier = parametreGestion.getMetier();

    }
    
    private void createXMLFile(){
        File f = new File(getNomFichier());
		
	if(f.exists())
	{
	     System.out.println("------------- File Already exist -----------");
            try {
                readFile(getNomFichier());
            } catch (Exception ex) {
                Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
	else { 
	     System.out.println("-------------- File is created -------------");
		try
	            {
		        java.io.File myXMLfile = new java.io.File(getNomFichier());
	                myXMLfile.createNewFile(); 
                   try {
                    WaterTreeStructXML();
                   } catch (Exception ex){
                    Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
                       JOptionPane.showMessageDialog(null, "Erreur archivage\n "+ex);
                }
		//CreateXMLFile (enterpriseName,  serviceName, serviceLevel, serviceParent, serviceCode, serviceActivity, serviceTarget,  variability,variantServiceCode);
                        
	            }catch(java.io.IOException ex){
                        JOptionPane.showMessageDialog(null, "Impossible de creer le fichier\n "+ex);
                    }
             }
                          storeXMLFile(getNomFichier()); // creates the file

	
      }
    
    public void WaterTreeStructXML(){
 
                  Element DateCreate = new  Element("DateCreation");
                  getRootXML().addContent(DateCreate);
                  if(getPeriodDebutArchivage()!=null&&getPeriodFinArchivage()!=null)
                  {
                      Element Datedeb = new  Element("DebutPeriode");
                      Datedeb.setText(""+getPeriodDebutArchivage().toString());
                      getRootXML().addContent(Datedeb);

                      Element DateFin = new  Element("FinPeriode");
                      DateFin.setText(""+getPeriodFinArchivage().toString());
                      getRootXML().addContent(DateFin);
                  }
                
                  Element Echantillon = new Element("Echantillon");
                  getRootXML().addContent(Echantillon);
                 
//                  Element Eau = new Element("Eau");
//                  Echantillon.addContent(Eau);
//
//                 }catch (Exception ex) {
//                    //Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);*
//                     System.out.println("Exception "+ex);
//                   }
//                 
                  //--------------------------EAU_INFOS------------------------
 
//		  Element IdEau = new Element("IdEau");
//                  Eau.addContent(IdEau);
//
//                  Element Demandeur = new Element("Demandeur");
//                  Eau.addContent(Demandeur);
//                  
//                  
//                  Element DatePreleve = new Element("DatePrelevement");
//                  Eau.addContent(DatePreleve);
//                  
//                  Element DateRecpetion = new Element("DateRecpetion");
//                  Eau.addContent(DateRecpetion);
//                  
//                  Element DatePaiement = new Element("DatePaiement");
//                  Eau.addContent(DatePaiement);
//                  
//                  Element DateLivraison = new Element("DateLivraison");
//                  Eau.addContent(DateLivraison);
//                  
//                  Element TypeEau = new Element("TypeEau");
//                  Eau.addContent(TypeEau);
//
//                  Element CategorieEau = new Element("CategorieEau");
//                  Eau.addContent(CategorieEau);
//                  
//                  
//                  Element NumLaboEau = new Element("NumLabo");
//                  Eau.addContent(NumLaboEau);
//                                    
//                  Element NumGlimsEau = new Element("NumGlims");
//                  Eau.addContent(NumGlimsEau);
//                  
//                  Element lieuPrelevementEau = new Element("lieuPrelevement");
//                  Eau.addContent(lieuPrelevementEau);
//
//                  Element condConservEau = new Element("condConserv");
//                  Eau.addContent(condConservEau);
//
//                  Element zoneClimatiqueEau = new Element("zoneClimatique");
//                  Eau.addContent(zoneClimatiqueEau);
//                 
//                  Element etiquetageDemandeurEau = new Element("etiquetageDemandeur");
//                  Eau.addContent(etiquetageDemandeurEau);
//                  
//                  Element EauNbreEchantillon = new Element("nbreEchantillon");
//                  Eau.addContent(EauNbreEchantillon);
//                  
//                  Element responssable = new Element("responssable");
//                  Eau.addContent(responssable);
//
//                  Element typeDePayement = new Element("typeDePayement");
//                  Eau.addContent(typeDePayement);
//                  
//                  Element numPaiement = new Element("numPaiement");
//                  Eau.addContent(numPaiement);
//                  
//                  Element EauprelevePar = new Element("prelevePar");
//                  Eau.addContent(EauprelevePar);
//                 
//                  Element EauClassification = new Element("EauClassification");
//                  Eau.addContent(EauClassification);
//                  
//                  Element EauConclusion = new Element("EauConclusion");
//                  Eau.addContent(EauConclusion);
//                  
//                  Element EauLocalite = new Element("EauLocalite");
//                  Eau.addContent(EauLocalite);
//                  
//                  Element EauAEtoile = new Element("EauAEtoile");
//                  Eau.addContent(EauAEtoile);
//                  
//                  Element EauNDEtoile = new Element("EauNDEtoile");
//                  Eau.addContent(EauNDEtoile);
                  
                  // -----------------------ANALYSE_INFO----------------------------
//                  Element Analyses = new Element("Analyses");
//                  Eau.addContent(Analyses);
//
//                  Element NomParametreAnalyse = new Element("NomParametre");
//                  Analyses.addContent(NomParametreAnalyse);
//
//                  Element ValeurResultatAnalyse = new Element("ValeurResultat");
//                  Analyses.addContent(ValeurResultatAnalyse);
//
//                  Element CritereParametreAnalyse = new Element("CritereParametre");
//                  Analyses.addContent(CritereParametreAnalyse);
//                  
//                  Element UniteParametreAnalyse = new Element("UniteParametre");
//                  Analyses.addContent(UniteParametreAnalyse);
//
//                  Element ClasseParametreAnalyse = new Element("ClasseParametre");
//                  Analyses.addContent(ClasseParametreAnalyse);
//                  
//
//                  Element TypeResultatAnalyse = new Element("TypeResultat");
//                  Analyses.addContent(TypeResultatAnalyse);
//
//                  Element MethodeatAnalyse = new Element("MethodeatAnalyse");
//                  Analyses.addContent(MethodeatAnalyse);
                  
    }
    
  public void readFile(String fichier) throws Exception
	{
	SAXBuilder sxb = new SAXBuilder();
	setDomDocument(sxb.build(new File(fichier)));
	setRootXML(getDomDocument().getRootElement());
	}

//   public void saveFile(String fichier1) throws Exception
//	{
//	XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
//	sortie.output(getDomDocument(), new FileOutputStream(fichier1));
//	}
//    
    
    private void storeXMLFile(String fichier)
     {
	try
	{
	//On utilise ici un affichage classique avec getPrettyFormat()
	  XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	  FileOutputStream sortie1 = new FileOutputStream(fichier);
	  sortie.output(getDomDocument(), new FileOutputStream(fichier));
	  sortie1.flush();
	  sortie1.close();
	  }
	catch (java.io.IOException e){
          System.out.println(e);
        }
    }       
    
/**
 *@return liste des eaux de l'arbre XML
 */
  private Vector<Eau> getListEauXML()
  {  
      Vector<Eau> list = new Vector<Eau>();
        try {
            readFile(getNomFichier());
            Element lesEaux =(Element) getRootXML().getChild("Echantillon");
            List<Element> listeEau =(List<Element>)lesEaux.getChildren("Eau");
            Iterator<Element> ieau = listeEau.iterator();

             while(ieau.hasNext())
             {
                Element leEau  = ieau.next();
                Eau e = new Eau();
                
                Element iEau = (Element)leEau.getChild("idEau");
                e.setId(new Long(iEau.getText()));

                Element idClient = (Element)leEau.getChild("IdClient");
                Client c = metier.getClientByID(new Long(idClient.getText()));
                
                if(c!=null)
                    e.setClient(c);
                
                
                Element dossierEau = (Element)leEau.getChild("NumLabo");
                e.setNumEngLabo(dossierEau.getText());
                
//                Element demandr= (Element)leEau.getChild("Demandeur");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Element datePrelev = (Element)leEau.getChild("DatePrelevement");
                //JXDatePicker d = new JXDatePicker();
                if(!datePrelev.getText().equalsIgnoreCase("null")){
                Date d = formatter.parse(datePrelev.getText());
                e.setDatePrelevement(d);
                }
                Element dateRecept = (Element)leEau.getChild("DateRecpetion");
                if(!dateRecept.getText().equalsIgnoreCase("null")){
                Date d = formatter.parse(dateRecept.getText());
                e.setDateReceptionLabo(d);
                }
                Element datePaie = (Element)leEau.getChild("DatePaiement");
                if(!datePaie.getText().equalsIgnoreCase("null")){
                Date d = formatter.parse(datePaie.getText());
                e.setDateEffetPaiement(d);
                }
                Element dateLivr = (Element)leEau.getChild("DateLivraison");
                if(!dateLivr.getText().equalsIgnoreCase("null")){
                Date d = formatter.parse(dateLivr.getText());
                e.setDateLivraison(d);
                }
                Element typEau = (Element)leEau.getChild("TypeEau");
                e.setTypeEau(typEau.getText());
  
                Element categorie = (Element)leEau.getChild("CategorieEau");
                e.seteauCategorie(categorie.getText());
                
                Element NumLabo = (Element)leEau.getChild("NumLabo");
                e.setNumEngLabo(NumLabo.getText());
                
                Element NumGlims = (Element)leEau.getChild("NumGlims");
                e.setNumEnrGlims(NumGlims.getText());
                
                Element LieuPrelev = (Element)leEau.getChild("lieuPrelevement");
                e.setLieuPrelevement(LieuPrelev.getText());
                
                Element Condcons = (Element)leEau.getChild("condConserv");
                e.setCondConserv(Condcons.getText());
                
                Element zoneClim = (Element)leEau.getChild("zoneClimatique");
                e.setZoneClimatique(zoneClim.getText());
                
                Element etiqDem = (Element)leEau.getChild("etiquetageDemandeur");
                e.setEtiquetageDemandeur(etiqDem.getText());
                
                Element NbEcht = (Element)leEau.getChild("nbreEchantillon");
                e.setNombreEchantillon(new Integer(NbEcht.getText()));
                
                Element Resp = (Element)leEau.getChild("responssable");
                e.setResponssable(Resp.getText());

                Element typPaie = (Element)leEau.getChild("typeDePayement");
                e.setTypeDePayement(typPaie.getText());
                
                Element numPaie = (Element)leEau.getChild("numPaiement");
                e.setNumPaiement(numPaie.getText());
                
                Element PrelevPar = (Element)leEau.getChild("prelevePar");
                e.setPrelevePar(PrelevPar.getText());
                
                Element conclu = (Element)leEau.getChild("EauConclusion");
                e.setConclusion(conclu.getText());
                
                Element Eauclassi = (Element)leEau.getChild("EauClassification");
                e.setEauGroupe(Eauclassi.getText());
                
                Element EaEtoile = (Element)leEau.getChild("EauAEtoile");
                e.setaEtoile(EaEtoile.getText());
                
                Element NdEtoile = (Element)leEau.getChild("EauNDEtoile");
                e.setNDEtoile(NdEtoile.getText());

                
                e.setListAnalyse(null);
                
                //DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateReceptionLabo());
//                DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH).format(datePrelev.getText());
//                //d.set                e.setDatePrelevement("");
//                
////                Element datePaie = (Element)leEau.getChild("DatePaiement");
////                Date d = new java.sql.Date(date)
////                d.s
////                e.setDateEffetPaiement(new Date);
//                metier.saveEau(e);
                
                list.addElement(e);
             }

        } catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
        }
      return list;
  }
  
  /**
   *@param Eau e : un echantillon d'eau de la base de données
   *@return Element le noeud de l'arbre XML ajouté
   */
    
    private Element saveEauXML(Eau e){
        Element eau =null;
        try {
            
                        readFile(getNomFichier());
                        
 			Element lesEaux =(Element) getRootXML().getChild("Echantillon");
                        List<Element> listeEau =(List<Element>)lesEaux.getChildren("Eau");
			Iterator <Element> ieau = listeEau.iterator();
                        //iproj.next();
                        
                         while(ieau.hasNext())
                         {
                            Element leEau  = ieau.next();
                            Element dossierEau = (Element)leEau.getChild("NumLabo");
                            Element iEau = (Element)leEau.getChild("idEau");
                          if(iEau.getText().trim().equals(e.getId().toString().trim())|| dossierEau.getText().trim().equals(e.getNumEngLabo().trim()))
                            {
                                System.out.println("A water with this name already exist...");
                                //JOptionPane.showMessageDialog(null, "A eau with this name already exist...");
                                storeXMLFile(getNomFichier()); // creates the file
                                return null;
                            }
                         }
                          //if(!(dossierEau.getText().trim().equalsIgnoreCase(e.getNumEngLabo().trim())))
                             {                                                                                                     
                         Element listEau =(Element)getRootXML().getChild("Echantillon");
                         
                          eau = new Element("Eau");
                         listEau.addContent(eau);
                        
                         Element idEau = new Element("idEau");
                         idEau.setText(e.getId().toString());
                         eau.addContent(idEau);
                         
                         Element IdClient = new Element("IdClient");
                         IdClient.setText(e.getClient().getId().toString());
                         eau.addContent(IdClient);

                         Element Demandeur = new Element("Demandeur");
                         Demandeur.setText(e.getClient().getNomDemandeur());
                         eau.addContent(Demandeur);
                  
                  
                  Element DatePreleve = new Element("DatePrelevement");
                  DatePreleve.setText(""+e.getDatePrelevement());
                  eau.addContent(DatePreleve);
                  
                  Element DateRecpetion = new Element("DateRecpetion");
                  DateRecpetion.setText(""+e.getDateReceptionLabo());
                  eau.addContent(DateRecpetion);
                  
                  Element DatePaiement = new Element("DatePaiement");
                  DatePaiement.setText(""+e.getDateEffetPaiement());
                  eau.addContent(DatePaiement);
                  
                  Element DateLivraison = new Element("DateLivraison");
                  DateLivraison.setText(""+e.getDateLivraison());
                  eau.addContent(DateLivraison);
                  
                  Element Typeeau = new Element("TypeEau");
                  Typeeau.setText(e.getTypeEau());
                  eau.addContent(Typeeau);

                  Element Categorieeau = new Element("CategorieEau");
                  Categorieeau.setText(e.geteauCategorie());
                  eau.addContent(Categorieeau);
                  
                  
                  Element NumLaboeau = new Element("NumLabo");
                  NumLaboeau.setText(e.getNumEngLabo());
                  eau.addContent(NumLaboeau);
                                    
                  Element NumGlimseau = new Element("NumGlims");
                  NumGlimseau.setText(e.getNumEnrGlims());
                  eau.addContent(NumGlimseau);
                  
                  Element lieuPrelevementeau = new Element("lieuPrelevement");
                  lieuPrelevementeau.setText(e.getLieuPrelevement());
                  eau.addContent(lieuPrelevementeau);

                  Element condConserveau = new Element("condConserv");
                  condConserveau.setText(e.getCondConserv());
                  eau.addContent(condConserveau);

                  Element zoneClimatiqueeau = new Element("zoneClimatique");
                  zoneClimatiqueeau.setText(e.getZoneClimatique());
                  eau.addContent(zoneClimatiqueeau);
                 
                  Element etiquetageDemandeureau = new Element("etiquetageDemandeur");
                  etiquetageDemandeureau.setText(e.getEtiquetageDemandeur());
                  eau.addContent(etiquetageDemandeureau);
                  
                  Element eauNbreEchantillon = new Element("nbreEchantillon");
                  eauNbreEchantillon.setText(""+e.getNombreEchantillon());
                  eau.addContent(eauNbreEchantillon);
                  
                  Element responssable = new Element("responssable");
                  responssable.setText(e.getResponssable());
                  eau.addContent(responssable);

                  Element typeDePayement = new Element("typeDePayement");
                  typeDePayement.setText(e.getTypeDePayement());
                  eau.addContent(typeDePayement);
                  
                  Element numPaiement = new Element("numPaiement");
                  numPaiement.setText(""+e.getNumPaiement());
                  eau.addContent(numPaiement);
                  
                  Element eauprelevePar = new Element("prelevePar");
                  eauprelevePar.setText(e.getPrelevePar());
                  eau.addContent(eauprelevePar);
                 
                  Element eauClassification = new Element("EauClassification");
                  eauClassification.setText(e.getEauGroupe());
                  eau.addContent(eauClassification);
                  
                  Element eauConclusion = new Element("EauConclusion");
                  eauConclusion.setText(e.getConclusion());
                  eau.addContent(eauConclusion);
                  
                  Element Analyses = new Element("Analyses");
                  eau.addContent(Analyses);

//                  Element eauLocalite = new Element("EauLocalite");
//                  eauLocalite.setText(e.getEauLocalite().getNomLocalite());
//                  eau.addContent(eauLocalite);
                  
                  Element eauAEtoile = new Element("EauAEtoile");
                  eauAEtoile.setText(e.getaEtoile());
                  eau.addContent(eauAEtoile);
                  
                  Element eauNDEtoile = new Element("EauNDEtoile");
                  eauNDEtoile.setText(e.getNDEtoile());
                  eau.addContent(eauNDEtoile);
                                          
                        
                         
             }   
            
        } catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, ex);
        }
        storeXMLFile(getNomFichier());
        return eau;
        
    }
  /**
   *@param Eau e : un echantillon d'eau de la base de données
   *@param Analyse a: UN paramètre d'analyse
   *sauvegarde une eau dans l'arborescence 
   */

    public void saveAnalysisXML(Eau e, Analyse a){

        try {
            
                        readFile(getNomFichier());
 			
 			Element lesEaux =(Element) getRootXML().getChild("Echantillon");
                        List<Element> listeEau =(List<Element>)lesEaux.getChildren("Eau");
			Iterator <Element> ieau = listeEau.iterator();
                        
                        while(ieau.hasNext())
                         {
                        
                             Element eau = ieau.next();
                             Element idEau = (Element)eau.getChild("idEau");
                            // Si l'analyse pas encore été enregistré exécute                                            
                            if(idEau.getText().trim().equalsIgnoreCase(a.getEauAnalyse().getId().toString().trim()))
                              {
                                  System.out.println("SAVE ANALYSIS "+a.getAnalyseNomParam());
                                  Element Analyses = (Element)eau.getChild("Analyses");
                                
                                  Element Analyse = new Element("Analyse");
                                  Analyses.addContent(Analyse);
                                
                                  Element NomParametreAnalyse = new Element("NomParametre");
                                  NomParametreAnalyse.setText(a.getAnalyseNomParam());
                                  Analyse.addContent(NomParametreAnalyse);

                                  Element ValeurResultatAnalyse = new Element("ValeurResultat");
                                  ValeurResultatAnalyse.setText(a.getAnalyseResult());
                                  Analyse.addContent(ValeurResultatAnalyse);

                                  Element CritereParametreAnalyse = new Element("CritereParametre");
                                  CritereParametreAnalyse.setText(a.getAnalyseCritereParam());
                                  Analyse.addContent(CritereParametreAnalyse);

                                  Element UniteParametreAnalyse = new Element("UniteParametre");
                                  UniteParametreAnalyse.setText(a.getAnalyseUniteParam());
                                  Analyse.addContent(UniteParametreAnalyse);

                                  Element ClasseParametreAnalyse = new Element("ClasseParametre");
                                  ClasseParametreAnalyse.setText(a.getAnalyseClasseParam());
                                  Analyse.addContent(ClasseParametreAnalyse);


                                  Element TypeResultatAnalyse = new Element("TypeResultat");
                                  TypeResultatAnalyse.setText(a.getAnalyseTypeResultat());
                                  Analyse.addContent(TypeResultatAnalyse);

                                  Element MethodeatAnalyse = new Element("MethodeatAnalyse");
                                  MethodeatAnalyse.setText(a.getEauAnalyseMethod());
                                  Analyse.addContent(MethodeatAnalyse);
                              }     
                                
                        }
         } catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, ex);
        }
        storeXMLFile(getNomFichier());
        
    }

    public void saveAnalysisXML(Element eauParent, Analyse a){

        try {
            
                                  readFile(getNomFichier());
 			
                                  System.out.println("SAVE ANALYSIS "+a.getAnalyseNomParam());
                                  Element Analyses = eauParent.getChild("Analyses");
                                
                                  Element Analyse = new Element("Analyse");
                                  Analyses.addContent(Analyse);
                                
                                  Element NomParametreAnalyse = new Element("NomParametre");
                                  Analyse.addContent(NomParametreAnalyse);

                                  Element ValeurResultatAnalyse = new Element("ValeurResultat");
                                  Analyse.addContent(ValeurResultatAnalyse);

                                  Element CritereParametreAnalyse = new Element("CritereParametre");
                                  Analyse.addContent(CritereParametreAnalyse);

                                  Element UniteParametreAnalyse = new Element("UniteParametre");
                                  Analyse.addContent(UniteParametreAnalyse);

                                  Element ClasseParametreAnalyse = new Element("ClasseParametre");
                                  Analyse.addContent(ClasseParametreAnalyse);


                                  Element TypeResultatAnalyse = new Element("TypeResultat");
                                  Analyse.addContent(TypeResultatAnalyse);

                                  Element MethodeatAnalyse = new Element("MethodeatAnalyse");
                                  Analyse.addContent(MethodeatAnalyse);
                                
                                
                             }
                         
         catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, ex);
        }
        storeXMLFile(getNomFichier());
        
    }
    
public Vector <Analyse> getAllAnalysesEauXML (Eau e){
    
    Vector <Analyse> ls = new Vector<Analyse>();
        try {
            readFile(getNomFichier());
 			Element lesEaux =(Element) getRootXML().getChild("Echantillon");
                        List<Element> listeEau =(List<Element>)lesEaux.getChildren("Eau");
			Iterator <Element> ieau = listeEau.iterator();
                        Element eau = null;
                        while(ieau.hasNext())
                         {
                        
                              eau = ieau.next();
                             Element idEau = (Element)eau.getChild("idEau");
                            // Si l'eau encore été enregistré exécute                                            
                            if(idEau.getText().trim().equalsIgnoreCase(e.getId().toString().trim()))
                              {     
                                    
                                   break; 
                              }
                         }
                            if (eau!=null)
                            {
                                 Element PtrAnalyse =(Element)eau.getChild("Analyses");
                                 List<Element> listeAnalyse =(List<Element>)PtrAnalyse.getChildren("Analyse");
                                 Iterator <Element> ianalys = listeAnalyse.iterator();
                                 Element analys = null;
                                 while(ianalys.hasNext())
                                 {
                                      analys = ianalys.next();
                                      Analyse lyse = new  Analyse();
                                      Element nomParam  = analys.getChild("NomParametre");
                                      lyse.setAnalyseNomParam(nomParam.getText());
                                      Element ParamRes  = analys.getChild("ValeurResultat");
                                      lyse.setAnalyseResult(ParamRes.getText());
                                      Element CriterParam  = analys.getChild("CritereParametre");
                                      lyse.setAnalyseCritereParam(CriterParam.getText());
                                      Element ParamUnite  = analys.getChild("UniteParametre");
                                      lyse.setAnalyseUniteParam(ParamUnite.getText());
                                      Element ParamClass  = analys.getChild("ClasseParametre");
                                      lyse.setAnalyseClasseParam(ParamClass.getText());
                                      Element ParamTyperes  = analys.getChild("TypeResultat");
                                      lyse.setAnalyseTypeResultat(ParamTyperes.getText());
                                      
//                                      Element ParamMethod  = analys.getChild("TypeResultat");
//                                      lyse.setAnalyse(ParamMethod.getText());
                                      ls.add(lyse);

                                 }//next analyse
                            
                         }
        
        } catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return ls;
}   

    /**
     *take all data in the xml file and  put it on the mysql Data base
     */
    public void importXML(){
    
        openFile();
        Vector<Eau> ls = getListEauXML();
        
        if(ls==null){
            JOptionPane.showMessageDialog(null, "LA LISTE DES EAUX EST VIDE");
            return;
        }
        setListEau(ls);
//        int i =0;
//        int t = ls.size();
//        for(Eau e:ls){
//            
//            e.setId(null);
//            metier.saveEau(e);
//            JOptionPane.showMessageDialog(null, "EAU DE"+e.getClient().getNomDemandeur());
//            
//            System.out.println("Is it persisted");
//            break;
//            
//        }
//        for(Eau e:ls){
//            //metier.saveEau(e);
////            if (e==null)
////            {
////                          JOptionPane.showMessageDialog(null, "L\'EAU EST VIDE");
////            return;
////            }
//            
//            List<Analyse> ls_Analyse = getAllAnalysesEauXML(e);
//            if(ls_Analyse == null){
//            JOptionPane.showMessageDialog(null, "LA LISTE DES analyses de"+e.getClient().getNomDemandeur()+"  EST VIDE");
//            continue ;
//            }            
//            for(Analyse a :ls_Analyse ){
//                metier.saveAnalyse(a);
//            }
////            i++;
////            if (i==5)
////                break;
//        }
            JOptionPane.showMessageDialog(null, "Importation terminée");

    
    }
    /**
     *take all data in the mysql Data base and  put it on the xml file
     */    
    public void exportXML(){
        JOptionPane.showMessageDialog(null, "ARCHIVAGE DES DONNEES");
        saveMap();
        
        nomFichier =chooser.getSelectedFile().getAbsolutePath().toString();//".\\BdEau.xml";
        createXMLFile();

        try {
        //JOptionPane.showMessageDialog(null, "DANS LE TRY");
            
            //readFile(getNomFichier());
            List<Eau> list = metier.getAllEauPeriode2(dateDeb, dateFin, choixTypEau);
            System.out.println("Date de début "+dateDeb+" Date de Fin "+dateFin+" Choix du type d'eau "+choixTypEau);
            //List<Eau> list = metier.getAllEauPeriode2(getPeriodDebutArchivage(), getPeriodFinArchivage(),getChoixTypEau());
            //Element listEau =(Element)getRootXML().getChildren("Echantillon");
            if(list==null){
                JOptionPane.showMessageDialog(null, "LA LISTE DES "+choixTypEau.toUpperCase()+" EST VIDE");
                return;
            }
            int i = 0;
             for(Eau e:list)
             {
                 //Element e_hist =  
                 saveEauXML(e);
                 List<Analyse> lst = metier.getAllAnalysesEau(e.getId());
                 int j = 0;
                 for(Analyse a:lst){
                 
                     saveAnalysisXML(e, a);
                     metier.deleteAnalyseEau(a);
//                     j++;
//                     if(j==6)
//                       break;
                 }
//                 i++;
//                 if(i==10)
//                     break;
                 metier.deleteEau(e);
             }
            
        } catch (Exception ex) {
            Logger.getLogger(ManagerXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //storeXMLFile(getNomFichier());
        JOptionPane.showMessageDialog(null, "FIN D\'ARCHIVAGE DES DONNEES");
        return;
                        

        
   }

    public void cryptXML(){
        
    }
    
    public void DecryptXML(){
        
    }
}
