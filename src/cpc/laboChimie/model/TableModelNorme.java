package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cpc.laboChimie.jpa.Norme;
import cpc.laboChimie.jpa.Norme;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MAXWELL
 */
public class TableModelNorme extends AbstractTableModel {
    private String[]columNames = {"","Nom Param","Unite", "Critere"};

    private Vector<Norme> Normes;

    public TableModelNorme(Vector<Norme> n){
        this.Normes = n;
        //Normes.
    }

    public int getRowCount(){
        return Normes.size();
    }


    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Norme n = this.Normes.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
            case 0:
                 o = n.getId();
                break;
            case 1:
                o = n.getNormeNomParam();
                break;
            case 2:
                o = n.getNormeUniteParam();
                break;
            case 3:
                o = n.getNormeCritereParam();

        }

        return o;
    }

    public void addRow(Norme n) {
        this.Normes.add(n);
        System.out.println("Ajout dans le master table");
        this.fireTableDataChanged();
    }

    public void addRows(Vector<Norme> Normes) {
        for (Norme n : Normes) {
            this.Normes.add(n);

        }
        this.fireTableDataChanged();
    }

    public void addRows(List<Norme> nor) {
        for (Norme n : nor) {
            this.Normes.add(n);

        }
        this.fireTableDataChanged();
    }

    public void removeRow(Norme n) {
        this.Normes.remove(n);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.Normes.removeAllElements();
        this.fireTableDataChanged();
    }

}
