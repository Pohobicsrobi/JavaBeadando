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


public class EmpList extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private EmpTM etm;
	
	public EmpList(JFrame f, EmpTM betm) {
		super(f, "Teniszezõk listája", true);
		etm = betm;
		
		setBounds(100, 100, 679, 518); //100, 100, 450, 300
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//{		
		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnBezar.setBounds(300, 22, 89, 23);
		contentPanel.add(btnBezar);	
	
		//}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 643, 211);
		getContentPane().add(scrollPane, BorderLayout.NORTH);
		
		table = new JTable(etm);
		scrollPane.setViewportView(table);

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
}
