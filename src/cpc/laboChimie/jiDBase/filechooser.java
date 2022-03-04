/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;

/**
 *
 * @author hp
 */


public class filechooser {

    /**
     * @param args the command line arguments
     */
    static void saveMap() {
    String sb = "TEST CONTENT";
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("C:\\Users"));
    int retrival = chooser.showSaveDialog(null);
    if (retrival == JFileChooser.APPROVE_OPTION) {
        try {
            FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt");
            System.out.println("Nom du fichier :"+chooser.getSelectedFile().toString());
            fw.write(sb.toString());
           } catch (Exception ex) {
            ex.printStackTrace();
           }
    }
}

    
    
    public static void main(String[] args) {
        // TODO code application logic here
      saveMap();    
    }
}
