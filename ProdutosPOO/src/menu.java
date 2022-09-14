import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;

public class menu extends JFrame {

	private JTextField txtID;
	private JTextField txtDtCadastro;
	private JTextField txtDescricao;
	private JTable tabProduto;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public menu() {
		setTitle("PRODUTO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 11, 23, 14);
		getContentPane().add(lblNewLabel);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(30, 8, 55, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data Cadastro");
		lblNewLabel_1.setBounds(89, 11, 86, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtDtCadastro = new JTextField();
		txtDtCadastro.setEditable(false);
		txtDtCadastro.setBounds(173, 8, 150, 20);
		getContentPane().add(txtDtCadastro);
		txtDtCadastro.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Descricao");
		lblNewLabel_2.setBounds(10, 50, 71, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtDescricao = new JTextField();
		txtDescricao.setEditable(false);
		txtDescricao.setBounds(80, 47, 207, 20);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);
		
		
		JButton btnNewButton = new JButton("Novo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDescricao.setEditable(true);
				txtDescricao.setText(null);
				txtID.setText(null);
				txtDtCadastro.setText(null);
				txtDescricao.requestFocus();
				
				
			}
		});
		btnNewButton.setBounds(297, 46, 76, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gravar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					try {
						gravar();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
		});
		btnNewButton_1.setBounds(383, 46, 80, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Excluir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					excluir();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(476, 46, 80, 23);
		getContentPane().add(btnNewButton_2);
		
		tabProduto = new JTable();
		
		
		tabProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				linhaSelecionada();
				
			}
		});
		tabProduto.setBounds(10, 99, 546, 130);
		getContentPane().add(tabProduto);
		
		JButton btnNewButton_3 = new JButton("Atualizar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					atualizar();
					txtDescricao.setEditable(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(383, 7, 89, 23);
		getContentPane().add(btnNewButton_3);
		try
		{
		    listarProduto();
		}
		catch(Exception ex)
		{
			double x=0;
	     }
		}
	
	private void linhaSelecionada()
	{	desabilitarText();
		DefaultTableModel tableModel = (DefaultTableModel) tabProduto.getModel();
		int row = tabProduto.getSelectedRow();
		if (tableModel.getValueAt(row, 0).toString()!="ID")
		{  txtID.setText(tableModel.getValueAt(row, 0).toString());
		   txtDescricao.setText(tableModel.getValueAt(row, 1).toString());
		   txtDtCadastro.setText(tableModel.getValueAt(row, 2).toString());
		}
	}
	
	private void desabilitarText()
	{
		txtDescricao.setEditable(false);
		txtID.setEditable(false);
		txtDtCadastro.setEditable(false);
		
	}
	
	private void listarProduto() throws SQLException 
    {  	Connection con=null;
		ConexaoBanco objconexao=new ConexaoBanco();
		//try
		//{   
			con=objconexao.conectar();
		//}
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
	
	private void gravar() throws SQLException 
	{
		Connection con=null;
		ConexaoBanco objconexao=new ConexaoBanco();
		try
		{
			con=objconexao.conectar();
			if(con ==null)
			{  	JOptionPane.showMessageDialog(null,"conexao nao realizada");
		    }
		    else
		    {   Statement stmt = con.createStatement();
		        String query="insert into produto(descricao) values('"+txtDescricao.getText()+"')";
		        stmt.executeUpdate(query);
		        listarProduto();
		        txtDescricao.setText(null);
		        desabilitarText();
		    	
		    }
        }
		catch(Exception ex)
		{
			con.close();
			JOptionPane.showMessageDialog(null,"Nao foi possivel gravar. "+ex.getMessage());
			
			
		}
		
	}
	
	private void excluir() throws SQLException
	{
		Connection con=null;
		ConexaoBanco objconexao = new ConexaoBanco();
		try {
			con=objconexao.conectar();
			if(con==null) {
				JOptionPane.showMessageDialog(null, "Conexao nao Realizada");
			}else 
			{  
				 Statement stmt = con.createStatement();
				 String query="delete from produto where id = '"+txtID.getText()+"' "; 
				 stmt.executeUpdate(query);
				 listarProduto();
				
			}
		}
		catch(Exception ex) 
		{
			con.close();
			JOptionPane.showMessageDialog(null,"Nao foi possivel excluir. "+ex.getMessage());
		}
	}
	
	private void atualizar() throws SQLException
	{
		Connection con=null;
		ConexaoBanco objconexao = new ConexaoBanco();
		try {
			con=objconexao.conectar();
			if(con==null) {
				JOptionPane.showMessageDialog(null, "Conexao nao Realizada");
			}else 
			{  
				Statement stmt = con.createStatement();
				String query="update produto set descricao = '"+txtDescricao.getText()+ "'where id = '"+txtID.getText()+"' "; 
				stmt.executeUpdate(query);
				listarProduto();
				
			}
		}
		catch(Exception ex) 
		{
			con.close();
			JOptionPane.showMessageDialog(null,"Nao foi possivel excluir. "+ex.getMessage());
		}
		
	}
	
}
