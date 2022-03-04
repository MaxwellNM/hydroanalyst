package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.jpa.TypeEau;
import cpc.laboChimie.jpa.TypeEau;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author MAXWELL
 */
public class TableModelTypeEau extends AbstractTableModel {
    private String[]columNames = {"","Nom Type d'Eau"};

    private Vector<TypeEau> TypeEaus;

    public TableModelTypeEau(Vector<TypeEau> n){
        this.TypeEaus = n;
    }

    public int getRowCount(){
        return TypeEaus.size();
    }


    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        TypeEau n = this.TypeEaus.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
            case 0:
                o = n.getId();
                break;
            case 1:
                o = n.getCategorie();
                break;
           /* case 2:
                o = n.getClasseParam();
                break;
            case 3:
                o = n.getTypeResult();*/

        }

        return o;
    }

    public void addRow(TypeEau n) {
        this.TypeEaus.add(n);
        System.out.println("Ajout dans le master table");
        this.fireTableDataChanged();
    }

    public void addRows(Vector<TypeEau> TypeEaus) {
        for (TypeEau n : TypeEaus) {
            this.TypeEaus.add(n);

        }
        this.fireTableDataChanged();
    }

    public void addRows(List<TypeEau> nor) {
       
        for (TypeEau n : nor) {
            
            this.TypeEaus.add(n);

        }
        this.fireTableDataChanged();
    }

    public void removeRow(TypeEau n) {
        this.TypeEaus.remove(n);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.TypeEaus.removeAllElements();
        this.fireTableDataChanged();
    }

}
