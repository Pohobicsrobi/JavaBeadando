import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Program extends JFrame {
	
	private JPanel contentPane;
	private DbMethods dbm = new DbMethods();
	private EmpTM etm;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Program() {  //kezdõablak
		dbm.Reg();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnLista = new JButton("Lista");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dbm.Connect();
				etm = dbm.ReadData();
				dbm.DisConnect();
				EmpList el = new EmpList(Program.this, etm);
				el.setVisible(true);			
			}
		});
		btnLista.setBounds(10, 11, 146, 23);
		contentPane.add(btnLista);
		
		JButton btnUjAdatsor = new JButton("\u00DAj adatsor");
		btnUjAdatsor.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			NewEmp ne = new NewEmp();
			ne.setVisible(true);
			}	
		});
		btnUjAdatsor.setBounds(10, 45, 146, 23);
		contentPane.add(btnUjAdatsor);
		
		JButton btnTrls = new JButton("T\u00F6rl\u00E9s");
		btnTrls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.Connect();
				etm = dbm.ReadData();
				dbm.DisConnect();
				EmpDel ed = new EmpDel(Program.this, etm);
				ed.setVisible(true);	
			}
		});
		btnTrls.setBounds(10, 79, 146, 23);
		contentPane.add(btnTrls);

	JButton btnModositas = new JButton("Teniszez\u0151 m\u00F3dos\u00EDt\u00E1sa");
		btnModositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.Connect();
				etm= dbm.ReadData();
				dbm.DisConnect();
				EmpMod em = new EmpMod(Program.this, etm);
				em.setVisible(true);
			}
		});
		btnModositas.setBounds(10, 113, 146, 23);
		contentPane.add(btnModositas);	
		
		Object emptmn[] = {"jel","azonosito", "nev", "szulido", "nemzetiseg", "ranglistapont"};
		etm = new EmpTM(emptmn, 0);
		
	}
	
}
