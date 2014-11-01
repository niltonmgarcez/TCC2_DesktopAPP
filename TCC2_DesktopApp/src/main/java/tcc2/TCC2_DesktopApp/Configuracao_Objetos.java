/**
 * 
 */
package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.naming.ldap.Rdn;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JRadioButton;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Configuracao_Objetos extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection objetos = db.getCollection("Objetos");
	DBCollection ambientes = db.getCollection("Ambientes");
	BasicDBObject documento = new BasicDBObject();
	final DefaultListModel listModel = new DefaultListModel();
	BasicDBObject search_atualizacao = new BasicDBObject();
	Pattern pattern = Pattern.compile("\"(.*?)\"");
	JScrollPane scrollpane = new JScrollPane();
	final JTextArea textArea_1 = new JTextArea();

	private JTextField textField_1;
	private JTextField textField_2;
	
	JRadioButton rdbtnAlta = new JRadioButton("Alta");
	
	JRadioButton rdbtnMdia = new JRadioButton("Média");
	
	JRadioButton rdbtnBaixa = new JRadioButton("Baixa");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuracao_Objetos frame = new Configuracao_Objetos();
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
	public Configuracao_Objetos() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final JComboBox comboBox = new JComboBox();
	
		final JList list_1 = new JList(listModel);
		
		atualizaList(list_1);
		
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("identificacao do objeto", list_1.getSelectedValue().toString());
				DBCursor cursor = objetos.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				comboBox.setSelectedItem(data.get("identificacao do ambiente").toString());
				
				textField.setText(data.get("identificacao do objeto").toString());
				textField_1.setText(data.get("posicao em X").toString());
				textField_2.setText(data.get("posicao em Y").toString());
				textArea_1.setText(data.get("descricao").toString());
				switch(Integer.parseInt(data.get("criticidade").toString()))
				{
					case 1 : 
						rdbtnAlta.setSelected(true);
						break;
					case 2 :
						rdbtnMdia.setSelected(true);
						break;
					case 3 :
						rdbtnBaixa.setSelected(true);
						break;						
				}				
				System.out.println(data);
			}
		});
				
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documento.clear();
				String tamanhoTabela = String.valueOf(objetos.getCount() + 1);
				documento.put("objeto_ID", tamanhoTabela);
				documento.put("identificacao do ambiente", comboBox.getSelectedItem());				
				documento.put("identificacao do objeto", textField.getText());
				documento.put("posicao em X", textField_1.getText());
				documento.put("posicao em Y", textField_2.getText());
				documento.put("descricao", textArea_1.getText());
				if (rdbtnAlta.isSelected()){documento.put("criticidade", "1");}	
				else if (rdbtnMdia.isSelected()){documento.put("criticidade", "2");}	
				else if (rdbtnBaixa.isSelected()){documento.put("criticidade", "3");}	
				objetos.insert(documento);				
				atualizaList(list_1);
				clear();				
			}
		});
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBCursor cursor = objetos.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("objeto_ID", data.get("objeto_ID").toString());
				newDocument.put("identificacao do ambiente", comboBox.getSelectedItem());
				newDocument.put("identificacao do objeto", textField.getText());	
				newDocument.put("posicao em X", textField_1.getText());
				newDocument.put("posicao em Y", textField_2.getText());
				newDocument.put("descricao", textArea_1.getText());
				if (rdbtnAlta.isSelected()){newDocument.put("criticidade", "1");}	
				else if (rdbtnMdia.isSelected()){newDocument.put("criticidade", "2");}	
				else if (rdbtnBaixa.isSelected()){newDocument.put("criticidade", "3");}
				BasicDBObject updateObj = new BasicDBObject();				
				updateObj.put("$set", newDocument);								
				objetos.update(search_atualizacao, newDocument);
				atualizaList(list_1);	
				clear();				
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				DBCursor cursor = objetos.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject updateObj = new BasicDBObject();
				objetos.remove(search_atualizacao);				
				atualizaList(list_1);	
				clear();				
			}
		});
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracao_Objetos.this.dispose();
			}
		});
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("predio_ID", "1");
		DBCursor result = ambientes.find(searchQuery);
		while (result.hasNext()) {  
	        comboBox.addItem(result.next().get("identificacao do ambiente").toString());
		}
		
				
		JLabel lblAmbientes_1 = new JLabel("Objetos:");
		
		JLabel lblA = new JLabel("Ambiente:");
		
		JLabel lblNomeDoAmbiente = new JLabel("Identificação");
		
		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				
			}
		});
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				
			}
		});
		textField.setBorder(border);
		textField.setColumns(10);
		
		JLabel lblPosioEmX = new JLabel("Posição em X:");
		
		textField_1 = new JTextField();
		textField_1.setBorder(border);
		textField_1.setColumns(10);
		
		JLabel lblPosioEmY = new JLabel("Posição em Y:");
		
		textField_2 = new JTextField();
		textField_2.setBorder(border);
		textField_2.setColumns(10);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		

		textArea_1.setBorder(border);
		
		JLabel lblCriticidade = new JLabel("Criticidade:");
		
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblA)
								.addGap(42)
								.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNomeDoAmbiente)
									.addComponent(lblPosioEmX))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblPosioEmY)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
									.addComponent(textField)
									.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE))))
						.addComponent(lblDescrio)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCriticidade)
							.addGap(75)
							.addComponent(rdbtnAlta)
							.addGap(18)
							.addComponent(rdbtnMdia)
							.addGap(18)
							.addComponent(rdbtnBaixa)))
					.addContainerGap(51, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblA)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNomeDoAmbiente)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPosioEmY)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPosioEmX))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescrio)
						.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnMdia)
						.addComponent(rdbtnBaixa)
						.addComponent(rdbtnAlta)
						.addComponent(lblCriticidade))
					.addGap(26))
		);
		rdbtnAlta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rdbtnMdia.setSelected(false);
				rdbtnBaixa.setSelected(false);
			}
		});
		rdbtnMdia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnAlta.setSelected(false);
				rdbtnBaixa.setSelected(false);
			}
		});
		rdbtnBaixa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnAlta.setSelected(false);
				rdbtnMdia.setSelected(false);
			}
		});
		panel.setLayout(gl_panel);
		
		JScrollPane listScrollPane = new JScrollPane();
		list_1.setBorder(border);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCadastrar, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFechar, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(lblAmbientes_1))
						.addComponent(list_1, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btnCadastrar)
						.addGap(6)
						.addComponent(btnAtualizar)
						.addGap(6)
						.addComponent(btnExcluir)
						.addGap(6)
						.addComponent(btnFechar)
						.addGap(6)
						.addComponent(lblAmbientes_1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(list_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void atualizaList(JList list)
		{
		DBCursor cursor = objetos.find();
		String objetos_updated;
		listModel.clear();
		while (cursor.hasNext()) {
			objetos_updated = cursor.next().get("identificacao do objeto").toString();
			listModel.addElement(objetos_updated);
		}
		list.validate();
	}
	
	public void clear()
	{
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");		
		textArea_1.setText("");
		rdbtnAlta.setSelected(false);
		rdbtnMdia.setSelected(false);
		rdbtnBaixa.setSelected(false);
	}

}
