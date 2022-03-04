/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

/**
 *
 * @author MAXWELL
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class chDBase extends JFrame {
	private JTabbedPane chDBase;
	private JPanel msAcces;
	private JPanel MySQL;
	private JButton btnTest;
	private JPasswordField txtPW;
	private JButton btnDBPath;
	private JLabel lblpwd;
	private JTextField txtID;
	private JLabel lblCompt;
	private JCheckBox cbProtect;
	private JFileChooser dbPath;
	private JTextField txtDBPath;
	private JLabel lblDBPath;
	private JLabel lblTitre;
	private final BDConnect con;
	private JTextField txtPort;
	private JLabel jLabel5;
	private JButton btnRep;
	private JTextField txtAdd;
	private JLabel jLabel4;
	private JPasswordField txtPw;
	private JLabel jLabel3;
	private JTextField txtId;
	private JLabel jLabel2;
	private JTextField txtBase;
	private JLabel jLabel1;
	private JButton btnAInfo;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new chDBase();
			}
		});
	}
    private JButton btnImport;

	public chDBase() {
		super();
		con = new BDConnect();
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
		this.setBounds(250, 130, 580, 500);
		setLayout(null);
		setVisible(true);
		this.setTitle("Connexion à une base de données  ::  HydroDBase");
		{
			chDBase = new JTabbedPane();
			getContentPane().add(chDBase);
			chDBase.setBounds(45, 87, 475, 306);
			chDBase.setFont(new java.awt.Font("Verdana", 0, 16));
			{
				msAcces = new JPanel();
				chDBase.addTab("Microsoft Acces", null, msAcces, null);
				msAcces.setLayout(null);
				msAcces.setFont(new java.awt.Font("Verdana", 0, 14));
				{
					lblDBPath = new JLabel();
					msAcces.add(lblDBPath);
					lblDBPath.setText("Choisir une base de données  :");
					lblDBPath.setBounds(21, 26, 209, 19);
					lblDBPath.setFont(new java.awt.Font("Verdana", 0, 12));
				}
				{
					txtDBPath = new JTextField();
					msAcces.add(txtDBPath);
					txtDBPath.setBounds(40, 52, 260, 23);
					txtDBPath.setFont(new java.awt.Font("Verdana", 0, 14));
					txtDBPath.setEditable(false);
				}
				{
					btnDBPath = new JButton();
					msAcces.add(btnDBPath);
					btnDBPath.setText("Chemin");
					btnDBPath.setBounds(314, 45, 104, 36);
					btnDBPath.setFont(new java.awt.Font("Verdana", 1, 14));
					btnDBPath.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							int result = dbPath.showOpenDialog(null);
							if (result == JFileChooser.APPROVE_OPTION)
								txtDBPath.setText(dbPath.getSelectedFile()
										.getPath());
						}
					});
				}
				{
					cbProtect = new JCheckBox();
					msAcces.add(cbProtect);
					cbProtect.setText("Protégé");
					cbProtect.setBounds(36, 96, 119, 23);
					cbProtect.setFont(new java.awt.Font("Verdana", 0, 12));
					cbProtect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (cbProtect.isSelected()) {
								txtID.setEditable(true);
								txtPW.setEditable(true);
								txtID.grabFocus();
							} else {
								txtID.setEditable(false);
								txtPW.setEditable(false);
							}

						}
					});
				}
				{
					lblCompt = new JLabel();
					msAcces.add(lblCompt);
					lblCompt.setText("Compte          :");
					lblCompt.setBounds(62, 126, 102, 16);
					lblCompt.setFont(new java.awt.Font("Verdana", 0, 12));
				}
				{
					txtID = new JTextField();
					msAcces.add(txtID);
					txtID.setBounds(167, 124, 157, 24);
					txtID.setEditable(false);
					txtID.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					lblpwd = new JLabel();
					msAcces.add(lblpwd);
					lblpwd.setText("Mot de passe :");
					lblpwd.setFont(new java.awt.Font("Verdana", 0, 12));
					lblpwd.setBounds(62, 161, 95, 16);
				}

				{
					txtPW = new JPasswordField();
					msAcces.add(txtPW);
					txtPW.setEditable(false);
					txtPW.setBounds(167, 161, 157, 22);
					txtPW.setFont(new java.awt.Font("Verdana", 0, 24));
				}
				{
					btnTest = new JButton();
					msAcces.add(btnTest);
					btnTest.setText("Tester");
					btnTest.setBounds(324, 192, 86, 34);
					btnTest.setFont(new java.awt.Font("Verdana", 0, 12));
					btnTest.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (txtDBPath.getText().equals(""))
								JOptionPane.showMessageDialog(null,
										"Veuillez choisir une base de donée",
										"Connexion", JOptionPane.ERROR_MESSAGE);
							else {
								ConMsAcc();

								if (con.Connect()) {
									JOptionPane.showMessageDialog(null,
											"Connexion ok", "Connexion",
											JOptionPane.INFORMATION_MESSAGE);
									con.Close();
								}
							}
						}
					});
				}

				{
					dbPath = new JFileChooser("c:");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"Access DataBase", "mdb","accdr");

					dbPath.setFileFilter(filter);
				}
			}
			{
				MySQL = new JPanel();
				chDBase.addTab("MySQL", null, MySQL, null);
				MySQL.setLayout(null);
				{
					btnRep = new JButton();
					MySQL.add(btnRep);
					btnRep.setText("Tester");
					btnRep.setBounds(330, 226, 91, 34);
					btnRep.setFont(new java.awt.Font("Verdana", 0, 14));
					btnRep.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (txtBase.getText().equals(""))
								JOptionPane.showMessageDialog(null,
										"Veuillez Entrer une base de donée",
										"Connexion", JOptionPane.ERROR_MESSAGE);
							else {
								ConMySql();

								if (con.Connect()) {
									JOptionPane.showMessageDialog(null,
											"Connexion ok", "Connexion",
											JOptionPane.INFORMATION_MESSAGE);
									con.Close();
								}
							}
						}
					});
				}
				{
					jLabel1 = new JLabel();
					MySQL.add(jLabel1);
					jLabel1.setText("Base de donnée");
					jLabel1.setBounds(29, 27, 114, 19);
					jLabel1.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					txtBase = new JTextField();
					MySQL.add(txtBase);
					txtBase.setBounds(54, 52, 167, 22);
					txtBase.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					jLabel2 = new JLabel();
					MySQL.add(jLabel2);
					jLabel2.setText("Compte");
					jLabel2.setBounds(39, 115, 71, 18);
					jLabel2.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					txtId = new JTextField();
					MySQL.add(txtId);
					txtId.setText("root");
					txtId.setBounds(69, 139, 152, 25);
					txtId.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					jLabel3 = new JLabel();
					MySQL.add(jLabel3);
					jLabel3.setText("Mot de passe");
					jLabel3.setBounds(39, 174, 113, 17);
					jLabel3.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					txtPw = new JPasswordField();
					MySQL.add(txtPw);
					txtPw.setBounds(69, 197, 152, 27);
					txtPw.setFont(new java.awt.Font("Verdana", 0, 24));
				}
				{
					jLabel4 = new JLabel();
					MySQL.add(jLabel4);
					jLabel4.setText("Adresse");
					jLabel4.setBounds(251, 74, 70, 17);
					jLabel4.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					txtAdd = new JTextField();
					MySQL.add(txtAdd);
					txtAdd.setText("Localhost");
					txtAdd.setBounds(277, 97, 128, 23);
					txtAdd.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					jLabel5 = new JLabel();
					MySQL.add(jLabel5);
					jLabel5.setText("Port");
					jLabel5.setBounds(251, 126, 70, 20);
					jLabel5.setFont(new java.awt.Font("Verdana", 0, 14));
				}
				{
					txtPort = new JTextField();
					MySQL.add(txtPort);
					txtPort.setBounds(277, 152, 59, 22);
					txtPort.setFont(new java.awt.Font("Verdana", 0, 14));
				}
			}
		}
		{
			lblTitre = new JLabel();
			getContentPane().add(lblTitre);
			lblTitre.setText("Choisir un SGBD");
			lblTitre.setBounds(186, 19, 221, 57);
			lblTitre.setFont(new java.awt.Font("Verdana", 1, 24));
		}
		{       btnImport = new JButton();
			btnAInfo = new JButton();
			getContentPane().add(btnAInfo);
                        getContentPane().add(btnImport);
			btnAInfo.setText("Afficher Info");
                        btnImport.setText("Importer les données ");
			btnAInfo.setBounds(372, 411, 148, 39);
                        btnImport.setBounds(150, 411, 200, 39);
			btnAInfo.setFont(new java.awt.Font("Verdana", 0, 14));
                        btnImport.setFont(new java.awt.Font("Verdana", 0, 14));
                        btnImport.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                   // throw new UnsupportedOperationException("Not supported yet.");
                	if (chDBase.getSelectedIndex() == 0)
						ConMsAcc();

					if (chDBase.getSelectedIndex() == 1)
						ConMySql();

					if (con.Connect()&&txtBase.getText()!="") {
						// dispose();
						//importe les données ;
                                            JOptionPane.showMessageDialog(null, "IMPORTATION DES DONNEES");
                                            //if()
                                            new Importerbd(con);
					}
                                        else
                                            JOptionPane.showMessageDialog(null, "ENTREZLE NOM DE LA BASE DE DONNEE");
                                            
                
                }
            });
			btnAInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (chDBase.getSelectedIndex() == 0)
						ConMsAcc();

					if (chDBase.getSelectedIndex() == 1)
						ConMySql();

					if (con.Connect()) {
						// dispose();
						new dbInfo(con);
					}
				}
			});
		}

	}

	private void ConMsAcc() {
		con.setSGBD("Microsoft ACCESS");
		con.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");

		con.setUrl("jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)}; DBQ="
				+ txtDBPath.getText());

		if (cbProtect.isSelected()) {
			con.setId(txtID.getText());
			con.setPw(txtPW.getText());
		}

		int tb = txtDBPath.getText().lastIndexOf("\\");
		String tbs = txtDBPath.getText().substring(tb + 1);
		con.setDBName(tbs.substring(0, tbs.length() - 4));
	}

	private void ConMySql() {
		con.setSGBD("MySQL");
		con.setDBName(txtBase.getText());
		con.setDriver("org.gjt.mm.mysql.Driver");

		con.setId(txtId.getText());
		con.setPw(txtPw.getText());

		String str = txtAdd.getText();
		if (!txtPort.getText().equals(""))
			str += ":" + txtPort.getText();
		str += "/" + txtBase.getText();
		con.setUrl("jdbc:mysql://" + str);
	}
}

