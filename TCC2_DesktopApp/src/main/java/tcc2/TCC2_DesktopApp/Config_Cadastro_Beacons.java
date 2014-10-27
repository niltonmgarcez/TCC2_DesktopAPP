package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Config_Cadastro_Beacons extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	MongoClient mongoClient = new MongoClient();
	DB db = mongoClient.getDB("TCC2_Data");
	DBCollection predio = db.getCollection("Macroambiente");
	DBCollection ambientes = db.getCollection("Ambientes");
	DBCollection beacons = db.getCollection("Beacons");
	BasicDBObject documento = new BasicDBObject();


	BasicDBObject search_atualizacao = new BasicDBObject();

	final DefaultListModel listModel = new DefaultListModel();
	JScrollPane scrollPane = new JScrollPane();
	final JList list;
	final JComboBox comboBox = new JComboBox();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Config_Cadastro_Beacons frame = new Config_Cadastro_Beacons();
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
	public Config_Cadastro_Beacons() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("predio_ID", "1");
		DBCursor result = predio.find(searchQuery);
	    Pattern pattern = Pattern.compile("\"(.*?)\"");
		DBObject oneDetails = result.next();
	    
		DBCursor cursor = beacons.find();
		String beacon;
		while (cursor.hasNext()) {
			beacon = cursor.next().get("nome do beacon").toString();
			listModel.addElement(beacon);
		}
		list = new JList(listModel);
		scrollPane.setViewportView(list);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("nome do beacon", list.getSelectedValue().toString());
				DBCursor cursor = beacons.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				comboBox.setSelectedItem(data.get("andar").toString());
				textField.setText(data.get("nome do beacon").toString());
				textField_1.setText(data.get("posicao x").toString());
				textField_2.setText(data.get("posicao y").toString());
				System.out.println(data);
			}
		});
			
		Integer andares = Integer.parseInt(oneDetails.get("andares").toString());
		int count = 1;
		while (count <= andares) {  
			if (count == 1)
			{
				comboBox.addItem("Térreo");
			}else{
				comboBox.addItem(count + "º Andar");
			}	        
			count++;
		}
		
		textField = new JTextField();
		textField.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				documento.clear();
				String tamanhoTabela = String.valueOf(ambientes.getCount());
				documento.put("beacon_ID", tamanhoTabela);
				documento.put("nome do beacon", textField.getText());
				if (comboBox.getSelectedItem().equals("Térreo"))
				{
					documento.put("andar", "1");
				}else{
					documento.put("andar", comboBox.getSelectedItem().toString().substring(0, 1));
				}
				documento.put("posicao x", textField_1.getText());
				documento.put("posicao y", textField_2.getText());
				
				beacons.insert(documento);

				atualizaList();
				limpaCampos();
						
				DBCursor cursor = beacons.find();
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}			
			}
			
			
		});
		
		
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DBCursor cursor = beacons.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("beacon_ID", data.get("beacon_ID").toString());
				newDocument.put("nome do beacon", textField.getText());
				if (comboBox.getSelectedItem().equals("Térreo"))
				{
					newDocument.put("andar", "1");
				}else{
					newDocument.put("andar", comboBox.getSelectedItem().toString().substring(0, 1));
				}
				newDocument.put("posicao x", textField_1.getText());
				newDocument.put("posicao y", textField_2.getText());
				
				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", newDocument);
								
				beacons.update(search_atualizacao, newDocument);
				
				atualizaList();
				limpaCampos();
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DBCursor cursor = beacons.find(search_atualizacao);
				beacons.remove(search_atualizacao);		
				limpaCampos();
				atualizaList();
			}
		});
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config_Cadastro_Beacons.this.dispose();
			}
		});
		
		JLabel lblLocalizaoNoPrdio = new JLabel("Localização no Prédio:");
		
		
		JLabel lblIdentificadorDoBeacon = new JLabel("Identificador do Beacon:");
		
		
		
		JLabel lblPosioEmX = new JLabel("Posição em X:");

		
		
		JLabel lblPosioEmY = new JLabel("Posição em Y:");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentificadorDoBeacon)
						.addComponent(lblLocalizaoNoPrdio)
						.addComponent(lblPosioEmX)
						.addComponent(lblPosioEmY))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLocalizaoNoPrdio)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIdentificadorDoBeacon)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPosioEmX)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPosioEmY)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));

		
		JLabel lblBeconsCadastrados = new JLabel("Becons Cadastrados:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBeconsCadastrados)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
						.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblBeconsCadastrados)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnCadastrar)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAtualizar)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExcluir)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnCancelar))
								.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))))
					.addContainerGap(144, Short.MAX_VALUE))
		);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(list, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void atualizaList()
	{
		DBCursor cursor_1 = beacons.find();
		String beacon_updated;
		listModel.clear();
		while (cursor_1.hasNext()) {
			beacon_updated = cursor_1.next().get("nome do beacon").toString();
			listModel.addElement(beacon_updated);
		}
		list.validate();
	}
	
	public void limpaCampos()
	{
		comboBox.setSelectedItem("");
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
	}

}
