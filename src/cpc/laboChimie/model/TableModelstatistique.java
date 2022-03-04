package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.struct.Couple;
import cpc.laboChimie.struct.InfosStat;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MAXWELL
 */
public class TableModelstatistique extends AbstractTableModel {
    private String[]columNames ={"Parametre","Moyenne", "Variance","Ecart type","Min","Quart1","Mediane","Quart3","Max","Inter Quartile","serie","Nbre observation"};

    private Vector<InfosStat> stat; //valeur de calcul.
    //private Vector<Couple> serie; //liste de  valeurs de la série

    public TableModelstatistique(Vector<InfosStat> a){
        stat = new Vector();
       // serie = new Vector<Couple>();
        stat.addAll(a);
       // serie.addAll(cp);
        //this.stat.addAll(n);
    }

    public int getRowCount(){
        return stat.size();
    }


    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
     InfosStat n = this.stat.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
            case 0:
                 o = n.nomParam;
                break;
            case 1:
                o = n.moyenne;
                break;
            case 2:
                o = n.variance;
                break;
            case 3:
                o = n.Ecart;
                break;
            case 4:
                o = n.min;
                break;
            case 5:
                o = n.Quart1;
                break;
            case 6:
                o = n.med;
            case 7:
                o = n.Quart3;
                break;
            case  8:
                 o = n.max;
                break;
            case 9:
                o = n.InterQuart;
                break;                        
            case 10:
                o = n.serie;
                break;
            case 11:
                o = n.taille;
                break;
        }

        return o;
    }

    public Object getValueAt(int rowIndex) {
       return (stat.elementAt(rowIndex));

    }
    public void addRow(InfosStat  n) {
        this.stat.add(n);
        System.out.println("Ajout dans le master table");
        this.fireTableDataChanged();
    }

  /*  public void addRows(Vector<Parametre> Parametres) {
        for (Parametre n : Parametres) {
            this.Parametres.add(n);

        }
        this.fireTableDataChanged();
    }*/

   /* public void addRows(List<Parametre> nor) {
       
        for (Parametre n : nor) {
            
            this.Parametres.add(n);

        }
        this.fireTableDataChanged();
    }*/

    public void removeRow(Vector n) {
        this.stat.remove(n);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.stat.removeAllElements();
        this.fireTableDataChanged();
    }

}
