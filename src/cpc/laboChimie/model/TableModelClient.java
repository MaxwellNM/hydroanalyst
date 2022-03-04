package cpc.laboChimie.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import cpc.laboChimie.jpa.Client;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fidélus
 */
public class TableModelClient extends AbstractTableModel {
    private String[]columNames = {"","Nom demandeur","Téléphone demandeur"};

    private Vector<Client> clients;

    public TableModelClient(Vector<Client> client){
        this.clients = client;
    }

    public int getRowCount(){
        return clients.size();
    }


    public int getColumnCount() {
        return columNames.length;
    }
    
    public Client getClient()
    {
      return  null;
    }

    @Override
    public String getColumnName(int col) {
        return columNames[col];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Client c = this.clients.get(rowIndex);

        Object o = null;

        switch (columnIndex) {
           case 0:
                o = c.getId();
                break;
            case 1:
                o = c.getNomDemandeur();
                break;
            case 2:
                o = c.getTelDemandeur();
                break;

        }

        return o;
    }
    
    public Client getClient(int rowindex)
    {
      return this.clients.get(rowindex);
    }

    public void addRow(Client client) {
        this.clients.add(client);
        System.out.println("Ajout dans le master table");
        this.fireTableDataChanged();
    }

    public void addRows(Vector<Client> clients) {
        for (Client client : clients) {
            this.clients.add(client);

        }
        this.fireTableDataChanged();
    }

    public void addRows(List<Client> clients) {
        for (Client client : clients) {
                this.clients.add(client);

        }
        this.fireTableDataChanged();
    }

    public void removeRow(Client client) {
        this.clients.remove(client);
        this.fireTableDataChanged();
    }

    public void removeAll() {
        this.clients.removeAllElements();
        this.fireTableDataChanged();
    }
}
