import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DbMethods {
	private Statement s = null;
	private Connection conn = null;
	private ResultSet rs = null;
	
	public void Reg(){
		try {
			Class.forName("org.sqlite.JDBC");
			SM("Sikeres driver regisztráció!", 1);
		}catch (ClassNotFoundException e){
			SM("Hibás driver regisztráció!"+e.getMessage(), 0);
		}
	}

	public void SM(String msg, int tipus){
		JOptionPane.showMessageDialog(null, msg, "Program üzenet", tipus);;
	}
	
	public void Connect(){
		try{
			String url = "jdbc:sqlite:C:/Users/Robi/Downloads/JDBC/sqlite-tools-win32-x86-3310100/teniszezok";
			conn = DriverManager.getConnection(url);
			SM("Connection OK!", 1);
		}catch (SQLException e){
			SM("JDBC Connect: "+e.getMessage(), 0);
		}
	}
	
	public void DisConnect(){
		try{
			conn.close();
			SM("DisConnection OK!", 1);
		}catch (SQLException e) {SM(e.getMessage(), 0);}
	}
	
	public EmpTM ReadData(){
		Object emptmn[] = {"jel","azonosito", "nev", "szulido", "nemzetiseg", "ranglistapont"};
		EmpTM etm = new EmpTM(emptmn, 0);
		String nev="", szulido="", nemzetiseg="", x="\t";
		int azonosito=0, ranglistapont=0;
		String sqlp="select azonosito, nev, szulido, nemzetiseg, ranglistapont from teniszezok";
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sqlp);
			while (rs.next()){
				azonosito = rs.getInt("azonosito");
				nev = rs.getString("nev");
				szulido = rs.getString("szulido");
				nemzetiseg = rs.getString("nemzetiseg");
				ranglistapont = rs.getInt("ranglistapont");
				etm.addRow(new Object[]{false, azonosito, nev, szulido, nemzetiseg, ranglistapont});
				}
		
		rs.close();		
	}catch (SQLException e) {SM(e.getMessage(), 0);}
	return etm;
	}
	
public void InsertData(String azonosito, String nev, String szulido, String nemzetiseg, String ranglistapont ) {
		String sqlp ="insert into teniszezok values("+azonosito+",\""+nev+"\", \""+szulido+"\", \""+nemzetiseg+"\","+ranglistapont+")";
		try {
			s= conn.createStatement();
			s.execute(sqlp);
			SM("Új teniszezõ sikeresen bekerült a listába!",1);
		}	catch(SQLException e) {
			SM("JDBC insert: "+e.getMessage(),0 );
		
		}
	}
	
public void DelData(String azonosito) {
	String sqlp = "delete from teniszezok where azonosito ="+azonosito;
	try {
		s = conn.createStatement();
		s.execute(sqlp);}
	catch (SQLException e) {
		SM("JDBC Delete: "+e.getMessage(),0);
		
	}
}


public void UpdateData(String azonosito, String mnev, String madat) {
	String sqlp = "update teniszezok set "+mnev+"='"+madat+"' where azonosito="+azonosito;
	try {
		s = conn.createStatement();
		s.execute(sqlp);}
	catch (SQLException e) {
		SM("JDBC Update: "+e.getMessage(),0);
		
	}

}
}



