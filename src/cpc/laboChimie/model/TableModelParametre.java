package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cpc.laboChimie.jpa.Parametre;
import cpc.laboChimie.jpa.Parametre;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MAXWELL
 */
public class TableModelParametre extends AbstractTableModel {
    private String[]columNames = {"","Nom Parametre","Classe Parametre", "type Resultat"};

    private Vector<Parametre> Parametres;

    public TableModelParametre(Vector<Parametre> n){
        this.Parametres = n;
    }

    public int getRowCount(){
        return Parametres.size();
    }


    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Parametre n = this.Parametres.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
            case 0:
                o = n.getId();
                break;
            case 1:
                o = n.getNomParam();
                break;
            case 2:
                o = n.getClasseParam();
                break;
            case 3:
                o = n.getTypeResult();

        }

        return o;
    }

    public void addRow(Parametre n) {
        this.Parametres.add(n);
        System.out.println("Ajout dans le master table");
        this.fireTableDataChanged();
    }

    public void addRows(Vector<Parametre> Parametres) {
        for (Parametre n : Parametres) {
            this.Parametres.add(n);

        }
        this.fireTableDataChanged();
    }

    public void addRows(List<Parametre> nor) {
       
        for (Parametre n : nor) {
            
            this.Parametres.add(n);

        }
        this.fireTableDataChanged();
    }

    public void removeRow(Parametre n) {
        this.Parametres.remove(n);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.Parametres.removeAllElements();
        this.fireTableDataChanged();
    }

}
