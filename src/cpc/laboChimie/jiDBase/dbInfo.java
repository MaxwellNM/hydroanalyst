/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cpc.laboChimie.jiDBase;

/**
 *
 * @author HP
 */
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class dbInfo extends JFrame {
	private JLabel lblTitre;
	private JPanel jPanel1;
	private JList lstTab;

	private final BDConnect con;
	private JLabel lblChp;
	private JLabel lblNull;
	private JLabel lblTyp;
	private JLabel jLabel5;
	private JLabel lblTotChp;
	private JLabel lblTotTab;
	private JLabel lblPath;
	private JLabel lblBase;
	private JLabel lblSgbd;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JScrollPane jScrollPane2;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JList lstChamp;
	private JLabel jLabel1;
	private ListModel lstChampModel;
	private ListModel lstTabModel;

	public dbInfo(BDConnect con) {
		super();
		this.con = con;
		con.Connect();
		initGUI();
		con.Close();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(170, 80, 600, 600);
		setVisible(true);
		setTitle("Schemas de  " + con.getDBName() + "  ::  HydroDBase");
		{
			lblTitre = new JLabel();
			getContentPane().add(lblTitre);
			lblTitre.setText("Afficher les infos de " + con.getDBName());
			lblTitre.setFont(new java.awt.Font("Verdana", 1, 24));
			lblTitre.setBounds(35, 6, 531, 37);
		}
		{
			jPanel1 = new JPanel();
			getContentPane().add(jPanel1);
			jPanel1.setLayout(null);
			jPanel1.setBounds(25, 219, 521, 259);
			jPanel1.setBorder(BorderFactory
					.createTitledBorder(null, "Schema de " + con.getDBName(),
							TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
							new java.awt.Font("Verdana", 1, 12)));
			{
				jScrollPane2 = new JScrollPane();
				jPanel1.add(jScrollPane2);
				jScrollPane2.setBounds(31, 48, 155, 195);
				jScrollPane2.getVerticalScrollBar().setAutoscrolls(true);
				jScrollPane2.getHorizontalScrollBar().setAutoscrolls(true);
				jScrollPane2.setBorder(new LineBorder(new java.awt.Color(0, 0,
						0), 1, false));
				{
					lstTabModel = new DefaultComboBoxModel(con.getTables());

					lstTab = new JList();
					jScrollPane2.setViewportView(lstTab);
					lstTab.setModel(lstTabModel);
					lstTab.setBounds(26, 59, 147, 195);
					lstTab.setPreferredSize(new java.awt.Dimension(136, 309));
					lstTab
							.addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent evt) {
									String str;
									str = lstTabModel.getElementAt(
											lstTab.getSelectedIndex())
											.toString();
									con.Connect();
									lstChampModel = new DefaultComboBoxModel(
											con.getChamp(str));

									lstChamp.setModel(lstChampModel);
									con.Close();
								}
							});

				}
			}
			{
				jLabel1 = new JLabel();
				jPanel1.add(jLabel1);
				jLabel1.setText("Tables");
				jLabel1.setBounds(54, 26, 51, 16);
				jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
			}
			{
				jLabel2 = new JLabel();
				jPanel1.add(jLabel2);
				jLabel2.setText("Champs");
				jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
				jLabel2.setBounds(333, 26, 61, 16);
			}
			{
				jScrollPane1 = new JScrollPane();
				jPanel1.add(jScrollPane1);
				jScrollPane1.setBounds(306, 48, 155, 199);
				jScrollPane1.getVerticalScrollBar().setAutoscrolls(true);
				jScrollPane1.getHorizontalScrollBar().setAutoscrolls(true);
				jScrollPane1.setBorder(new LineBorder(new java.awt.Color(0, 0,
						0), 1, false));
				jScrollPane1.setAutoscrolls(true);
				{
					lstChamp = new JList();
					jScrollPane1.setViewportView(lstChamp);
					lstChamp.setBounds(349, 57, 135, 202);
					lstChamp.setPreferredSize(new java.awt.Dimension(136, 310));
					lstChamp.setAutoscrolls(false);
					lstChamp
							.addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent evt) {
									lblChp.setText(lstChampModel.getElementAt(
											lstChamp.getSelectedIndex())
											.toString());
									String str = lstTabModel.getElementAt(
											lstTab.getSelectedIndex())
											.toString();
									con.Connect();
									String tstr[] = con.getChpInfo(str, lblChp
											.getText());
									con.Close();
									lblTyp.setText(tstr[0]);
									lblNull.setText(tstr[1]);

								}
							});
				}
			}
		}
		{
			jPanel2 = new JPanel();
			getContentPane().add(jPanel2);
			jPanel2.setBounds(32, 484, 521, 71);
			jPanel2.setBorder(BorderFactory.createTitledBorder(null,
					"Info sur ", TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new java.awt.Font("Verdana", 1, 12)));
			jPanel2.setLayout(null);
			{
				jLabel8 = new JLabel();
				jPanel2.add(jLabel8);
				jLabel8.setText("Type");
				jLabel8.setBounds(49, 26, 59, 19);
				jLabel8.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				jLabel9 = new JLabel();
				jPanel2.add(jLabel9);
				jLabel9.setText("Null Autorisé");
				jLabel9.setBounds(345, 26, 112, 19);
				jLabel9.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				lblTyp = new JLabel();
				jPanel2.add(lblTyp);
				lblTyp.setBounds(80, 45, 265, 20);
				lblTyp.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblNull = new JLabel();
				jPanel2.add(lblNull);
				lblNull.setBounds(380, 46, 99, 18);
				lblNull.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblChp = new JLabel();
				jPanel2.add(lblChp);
				lblChp.setBounds(74, 1, 190, 19);
				lblChp.setFont(new java.awt.Font("Verdana", 1, 11));
				lblChp.setForeground(new java.awt.Color(0, 70, 213));
			}
		}
		{
			jPanel3 = new JPanel();
			getContentPane().add(jPanel3);
			jPanel3.setLayout(null);
			jPanel3.setBounds(15, 43, 538, 158);
			jPanel3.setBorder(BorderFactory
					.createTitledBorder(null, "Info Générale",
							TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
							new java.awt.Font("Verdana", 1, 12)));
			{
				jLabel3 = new JLabel();
				jPanel3.add(jLabel3);
				jLabel3.setText("SGBD                   :");
				jLabel3.setBounds(26, 28, 152, 16);
				jLabel3.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				jLabel4 = new JLabel();
				jPanel3.add(jLabel4);
				jLabel4.setText("Base de donnée     :");
				jLabel4.setBounds(26, 49, 152, 20);
				jLabel4.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				jLabel5 = new JLabel();
				jPanel3.add(jLabel5);
				jLabel5.setText("Localisation           :");
				jLabel5.setBounds(26, 75, 145, 14);
				jLabel5.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				jLabel6 = new JLabel();
				jPanel3.add(jLabel6);
				jLabel6.setText("Total des Tables    :");
				jLabel6.setBounds(26, 98, 145, 17);
				jLabel6.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				jLabel7 = new JLabel();
				jPanel3.add(jLabel7);
				jLabel7.setText("Total des Champs  :");
				jLabel7.setBounds(26, 121, 145, 20);
				jLabel7.setFont(new java.awt.Font("Verdana", 0, 14));
			}
			{
				lblSgbd = new JLabel();
				jPanel3.add(lblSgbd);
				lblSgbd.setBounds(178, 27, 200, 20);
				lblSgbd.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblBase = new JLabel();
				jPanel3.add(lblBase);
				lblBase.setBounds(178, 51, 237, 18);
				lblBase.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblPath = new JLabel();
				jPanel3.add(lblPath);
				lblPath.setBounds(177, 73, 355, 21);
				lblPath.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblTotTab = new JLabel();
				jPanel3.add(lblTotTab);
				lblTotTab.setBounds(177, 97, 73, 20);
				lblTotTab.setFont(new java.awt.Font("Verdana", 1, 14));
			}
			{
				lblTotChp = new JLabel();
				jPanel3.add(lblTotChp);
				lblTotChp.setBounds(175, 123, 75, 18);
				lblTotChp.setFont(new java.awt.Font("Verdana", 1, 14));
			}

			{
				lblSgbd.setText(con.getSGBD());
				lblBase.setText(con.getDBName());
				String str = "";
				String tb[], chp[];
				int i, j = 0;
				if (con.getSGBD().endsWith("ESS")) {
					i = con.getUrl().lastIndexOf("=");
					str = con.getUrl().substring(i + 1);

				}
				if (con.getSGBD().equals("MySQL")) {
					i = con.getUrl().indexOf("/");
                                       	str = con.getUrl().substring(i + 2);
				}

				lblPath.setText(str);
				lblPath.setToolTipText(str);
				tb = con.getTables();
				lblTotTab.setText(Integer.toString(tb.length));
				for (i = 0; i < tb.length; i++)
					j += con.getChamp(tb[i]).length;
				lblTotChp.setText(Integer.toString(j));
			}

		}
	}
}
