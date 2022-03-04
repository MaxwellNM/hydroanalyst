/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

/**
 *
 * @author MAXWELL
 */

import cpc.laboChimie.metier.IMetier;
import cpc.laboChimie.metierConfig.ParametreGestion;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BDConnect {
	private String driver;
	private String url;
	private String id;
	private String pw;
	private Connection con;
	private String sgbd;
	private String dbname;
	public BDConnect() {
		driver = "";
		url = "";
		id = "";
		pw = "";
               
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setSGBD(String sgbd) {
		this.sgbd = sgbd;
	}

	public String getSGBD() {
		return this.sgbd;
	}

	public void setDBName(String dbname) {
		this.dbname = dbname;
	}

	public String getDBName() {
		return this.dbname;
	}
        
        
        public Connection  getConnect()
        { Connection con =null;
        
          
		if (driver == "" && url == "")
			JOptionPane.showMessageDialog(null,
					"Connexion impossible, car driver ou url non renseigner",
					"Connexion", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, id, pw);
				
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"Problème avec le driver ODBC", "Connexion",
						JOptionPane.ERROR_MESSAGE);

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Impossible de se connecter à la base", "Connexion",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
           return con;
        }
        
        
        

	public boolean Connect() {
		boolean isCon = false;

		if (driver == "" && url == "")
			JOptionPane.showMessageDialog(null,
					"Connexion impossible, car driver ou url non renseigner",
					"Connexion", JOptionPane.ERROR_MESSAGE);
		else {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, id, pw);
				isCon = true;
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"Problème avec le driver ODBC", "Connexion",
						JOptionPane.ERROR_MESSAGE);

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,
						"Impossible de se connecter à la base", "Connexion",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return isCon;
	}

	public String[] getTables() {
		DatabaseMetaData metaData;
		ResultSet bd;
		String[] tb;
		List<String> tbf = new ArrayList<String>();

		int i = 0;

		try {
			metaData = con.getMetaData();
			bd = metaData.getTables(con.getCatalog(), null, "%", null);
			while (bd.next()) {
				if (bd.getString(4).equals("TABLE"))
					tbf.add(bd.getString(3));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Connexion",
					JOptionPane.ERROR_MESSAGE);
		}

		tb = new String[tbf.size()];
		for (i = 0; i < tb.length; i++)
			tb[i] = tbf.get(i).toString();

		return tb;
	}
        
        
        
        
        
        

	public String[] getChamp(String tab) {
		DatabaseMetaData metaData;
		ResultSet tbl;
		String[] tb;
		List<String> tbf = new ArrayList<String>();

		int i = 0;

		try {
			metaData = con.getMetaData();

			tbl = metaData.getColumns(con.getCatalog(), null, tab, "%");

			while (tbl.next())
				tbf.add(tbl.getString(4));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Connexion",
					JOptionPane.ERROR_MESSAGE);
		}

		tb = new String[tbf.size()];
		for (i = 0; i < tb.length; i++)
			tb[i] = tbf.get(i).toString();

		return tb;

	}

	public String[] getChpInfo(String tab, String chp) {
		DatabaseMetaData metaData;
		ResultSet tbl;
		String res[] = new String[2];
		;

		try {
			metaData = con.getMetaData();
			tbl = metaData.getColumns(con.getCatalog(), null, tab, "%");

			while (tbl.next())
				if (tbl.getString(4).equals(chp)) {
					res[0] = tbl.getString(6);
					res[1] = (tbl.getInt(11) == 1) ? "Oui" : "Non";
				}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Connexion",
					JOptionPane.ERROR_MESSAGE);
		}

		return res;
	}
        
        
        
        
        
	public void Close() {
		try {
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Connexion",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

