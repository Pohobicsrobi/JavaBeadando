import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;


public class EmpMod extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private EmpTM etm;
	private Checker c = new Checker();
	private DbMethods dbm = new DbMethods();
	private JTextField azonosito;
	private JTextField nev;
	private JTextField szulido;
	private JTextField nemzetiseg;
	private JTextField ranglistapont;
	private JButton btnmodositas;
	
	public EmpMod(JFrame f, EmpTM betm) {
		super(f, "Teniszezõk módosítása", true);
		etm = betm;
		
		setBounds(100, 100, 679, 405); 
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		//{		
		JButton btnBezar2 = new JButton("Bez\u00E1r");
		btnBezar2.setBounds(390, 302, 89, 23);
		btnBezar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPanel.setLayout(null);
		contentPanel.setLayout(null);
		contentPanel.add(btnBezar2);	
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 643, 158);
		contentPanel.add(scrollPane);
		//getContentPane().add(scrollPane, BorderLayout.NORTH);
		
		table = new JTable(etm);
		scrollPane.setViewportView(table);
		
		btnmodositas = new JButton("M\u00F3dos\u00EDt\u00E1s");
		//btnmodositas.setBounds(153, 302, 116, 23);
		btnmodositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db=0, Jel=0, x=0;
				for(x = 0; x<etm.getRowCount();x++)
					if ((boolean)etm.getValueAt(x,0)) {db++; Jel=x;}
					if (db==0) c.SM("Nincs kijelölve módosítandó teniszezõ!", 0);
					if (db>1) c.SM("Több teniszezõ van kijelölve! (Egyszerre csak egy módosíthat)", 0); 
					if (db==1) {
						if (modDataPc() >0) {
							boolean ok= true;
							if (c.filled(azonosito)) ok= c.goodInt(azonosito,"Azonosító");
							if (ok && c.filled(ranglistapont)) ok = c.goodInt(ranglistapont, "Közösköltség");
							if(ok) {
								String mkod = etm.getValueAt(Jel, 1).toString();
								dbm.Connect();
								if(c.filled(nev)) dbm.UpdateData(mkod, "nev", c.RTF(nev));
								if(c.filled(szulido)) dbm.UpdateData(mkod, "szulido", c.RTF(szulido));
								if(c.filled(nemzetiseg)) dbm.UpdateData(mkod, "nemzetiseg", c.RTF(nemzetiseg));
								if(c.filled(ranglistapont)) dbm.UpdateData(mkod, "ranglistapont", c.RTF(ranglistapont));
								if(c.filled(azonosito)) dbm.UpdateData(mkod, "azonosito", c.RTF(azonosito));
								dbm.DisConnect();
								
								if (c.filled(azonosito)) etm.setValueAt(c.stringToInt(c.RTF(azonosito)), Jel, 1);
								if (c.filled(nev)) etm.setValueAt(c.RTF(nev), Jel, 2);
								if (c.filled(szulido)) etm.setValueAt(c.RTF(szulido), Jel, 3);
								if (c.filled(nemzetiseg)) etm.setValueAt(c.RTF(nemzetiseg), Jel, 4);
								if (c.filled(ranglistapont)) etm.setValueAt(c.RTF(ranglistapont), Jel, 5);
								c.SM("A teniszezõ Módosítva",1);
							reset(Jel);
							}
							else {
								c.SM("Nincs kitöltve egy módosító mezezõ sem!",0);
							}
						}
					}
			}
		});
		contentPanel.add(btnmodositas);
		btnmodositas.setBounds(153, 302, 116, 23);
		
		azonosito = new JTextField();
		azonosito.setBounds(78, 225, 68, 20);
		contentPanel.add(azonosito);
		azonosito.setColumns(10);
		
		nev = new JTextField();
		nev.setBounds(153, 225, 124, 20);
		contentPanel.add(nev);
		nev.setColumns(10);
		
		szulido = new JTextField();
		szulido.setBounds(287, 225, 139, 20);
		contentPanel.add(szulido);
		szulido.setColumns(10);
		
		nemzetiseg = new JTextField();
		nemzetiseg.setBounds(436, 225, 128, 20);
		contentPanel.add(nemzetiseg);
		nemzetiseg.setColumns(10);
		
		ranglistapont = new JTextField();
		ranglistapont.setBounds(574, 225, 68, 20);
		contentPanel.add(ranglistapont);
		ranglistapont.setColumns(10);

			TableColumn tc = null;
			for (int i=0; i<6; i++){
				tc = table.getColumnModel().getColumn(i);
				if (i==0 || i==1 || i==5)tc.setPreferredWidth(30);
				else {tc.setPreferredWidth(100);
			}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<EmpTM> trs =	(TableRowSorter<EmpTM>)table.getRowSorter();
		trs.setSortable(0, false);
}
			
}
	public int modDataPc() {
		int pc=0;
		if (c.filled(azonosito)) pc++;
		if (c.filled(nev)) pc++;
		if (c.filled(szulido)) pc++;
		if (c.filled(nemzetiseg)) pc++;
		if (c.filled(ranglistapont)) pc++;
		return pc;
	}	
	
	public void reset (int i){
		azonosito.setText("");
		nev.setText("");
		szulido.setText("");
		nemzetiseg.setText("");
		ranglistapont.setText("");
		etm.setValueAt(false, i, 0);
	}
}
