package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import cpc.laboChimie.jpa.Eau;



import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fidélus
 */
public class TableModelEaux extends AbstractTableModel {
    private String[] columnNames = {"", "Numlabo","type", "Date prelv labo","Date livrai"};
    private Vector<Eau> eaus;

    public TableModelEaux(Vector<Eau> ea) {
        this.eaus = ea;
    }

    public int getRowCount() {
        return eaus.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Eau e = this.eaus.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
            case 0:
                o = e.getId();
                break;
            case 1:
                o = e.getNumEngLabo();
                break;
            case 2:
                o = e.getTypeEau();
                break;
            case 3:
                o = (e.getDatePrelevement() == null) ? "" : DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDatePrelevement());
                break;
            case 4:
                o = (e.getDateLivraison() == null)? "": DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE).format(e.getDateLivraison());
                break;
        }

        return o;
    }

    public void addRow(Eau eau) {
        this.eaus.add(eau);
        this.fireTableDataChanged();
    }

    public void addRows(Vector<Eau> es) {
        for (Eau e : es) {
            this.eaus.add(e);

        }
        this.fireTableDataChanged();
    }

    public void addRows(List<Eau> e) {
        for (Eau eau : e) {
            this.eaus.add(eau);

        }
        this.fireTableDataChanged();
    }

    public void removeRow(Eau e) {
        this.eaus.remove(e);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.eaus.removeAllElements();
        this.fireTableDataChanged();
    }
}
