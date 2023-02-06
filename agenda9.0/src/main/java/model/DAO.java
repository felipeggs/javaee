package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de conexão *. */
	// Parametro de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "felipejoao";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Inserir contatos.
	 *
	 * @param contato the contato
	 */
	// CRUD CREATE
	public void inserirContatos(JavaBens contato) {
		String create = "insert into contato (nome,fone, email) values (?,?,?) ";
		try {
			// abrir conexao com banco de dados
			Connection con = conectar();
			// Preparar a query para a execução no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir as interrogações pelos dados da javaBens
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Ecerrar a conexão com o banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// CRUD Read (listagem dos contatos do banco de dados) usando um vetor
	/**
	 * Listar contato.
	 *
	 * @return the array list
	 */
	// dinamico(array)
	public ArrayList<JavaBens> listarContato() {
		// Criando um objeto para acessar a classe JavaBens
		ArrayList<JavaBens> contato = new ArrayList<>();
		String read = "select * from contato order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// O laço abaixo será executa enquanto tiver contatos
			// Next é usado para recuperar os contatos do banco de dados
			while (rs.next()) {
				// Variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// Populando o ArrayList
				contato.add(new JavaBens(idcon, nome, fone, email));
			}
			con.close();
			return contato;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	/**
	 *  CRUD UPDATE*.
	 *
	 * @param contato the contato
	 */
	//Selecionar Contato
	public void selecionarContato(JavaBens contato)
	{
		String read2 = "select * from contato where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				//Setar as variaveis JavaBens
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	// Editar Contato
	public void alterarContato(JavaBens contato) {
		String create = "update contato set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 *  CRUD DELETE *.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(JavaBens contato) {
		String delete = "delete from contato where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}


//teste de conexão
/**
 * public void testeConexao() { try { Connection con = conectar();
 * System.out.println(con); con.close(); } catch (Exception e) {
 * System.out.println(e);
 * 
 * } }
 **/
