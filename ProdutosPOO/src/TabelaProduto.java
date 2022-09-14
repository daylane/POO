import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;

public class TabelaProduto extends JFrame {

	private JPanel contentPane;
	private JTable tabProduto;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TabelaProduto frame = new TabelaProduto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabelaProduto() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		tabProduto = new JTable();
		tabProduto.setBounds(138, 218, 269, -126);
		contentPane.add(tabProduto);
		getContentPane().add(tabProduto);
		listarProduto();
		
	}
	
	private void listarProduto() throws SQLException 
    {  	Connection con=null;
		ConexaoBanco objconexao=new ConexaoBanco();
		{   
			try {
			con=objconexao.conectar();
			if(con ==null)
			{  	JOptionPane.showMessageDialog(null,"conexao nao realizada");
		    }
		    else
		    {   Statement stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery("SELECT * FROM javapoo.produto");
				    	       
		    	String[] colunasTabela= new String[]{ "ID", "Descricao", "Pontuacao" };  
		    	DefaultTableModel modeloTabela = new DefaultTableModel(null,colunasTabela);
		    	modeloTabela.addRow(new String[] {"ID", "DESCRICAO", "CADASTRO"}); 
		    	if(rs != null) {
		    	    while(rs.next()) {
		    	        modeloTabela.addRow(new String[] {  
		    	                String.valueOf(rs.getInt("ID")),  
		    	                rs.getString("descricao"),  
		    	                rs.getString("data_cadastro")
		    	            }); 
		    	    }
		    	}
		    	tabProduto.setModel(modeloTabela);
		    	
		    
		    }
        }
		catch(Exception ex)
		{
			con.close();
			
		}
	}
}
}