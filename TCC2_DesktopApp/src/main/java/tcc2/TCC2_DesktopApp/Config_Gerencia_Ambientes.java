package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.bson.BSONObject;
import org.bson.types.BasicBSONList;
import org.omg.CosNaming.IstringHelper;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import java.awt.Panel;
import java.awt.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Config_Gerencia_Ambientes extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	DBCollection ambientes = db.getCollection("Ambientes");
	BasicDBObject documento = new BasicDBObject();
	BasicDBObject search_atualizacao = new BasicDBObject();

	final DefaultListModel listModel = new DefaultListModel();
	JScrollPane scrollPane = new JScrollPane();
	final JList list;
	
	JScrollPane scrollPane_1 = new JScrollPane();
	JScrollPane scrollPane_2 = new JScrollPane();

	
	JComboBox comboBox;
	JComboBox comboBox_1;
	JTextArea textArea;
	JTextArea textArea_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Config_Gerencia_Ambientes frame = new Config_Gerencia_Ambientes();
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
	public Config_Gerencia_Ambientes() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		comboBox = new JComboBox();
		comboBox_1 = new JComboBox();
		textArea = new JTextArea();
		textArea_1 = new JTextArea();
		
		scrollPane_1.setViewportView(textArea);
		scrollPane_2.setViewportView(textArea_1);
		
		DBCursor cursor = ambientes.find();
		String ambiente;
		while (cursor.hasNext()) {
			ambiente = cursor.next().get("nome do ambiente").toString();
			listModel.addElement(ambiente);
		}
		list = new JList(listModel);
		scrollPane.setViewportView(list);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnCadastrar = new JButton("Atualizar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DBCursor cursor = ambientes.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("ambiente_ID", data.get("ambiente_ID").toString());
				newDocument.put("tipo de ambiente", comboBox.getSelectedItem());
				if (comboBox_1.getSelectedItem().equals("Térreo"))
				{
					newDocument.put("andar", "1");
				}else{
					newDocument.put("andar", comboBox_1.getSelectedItem().toString().substring(0, 1));
				}
				newDocument.put("nome do ambiente", textField.getText());	
				newDocument.put("latitude", textField_1.getText());
				newDocument.put("longitude", textField_2.getText());
				newDocument.put("resumo",  textArea.getText());
				newDocument.put("descricao", textArea_1.getText());
				
				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", newDocument);
								
				ambientes.update(search_atualizacao, newDocument);
				limpaCampos();
				atualizaList();
								
			}
		});
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("predio_ID", "1");
		DBCursor result = predio.find(searchQuery);
	    Pattern pattern = Pattern.compile("\"(.*?)\"");
		DBObject oneDetails = result.next();
	    String data=oneDetails.get("tipos de ambiente").toString();
	    Matcher matcher = pattern.matcher(data);
	    
		while (matcher.find()) {  
	        String text = matcher.group(1);
	        comboBox.addItem(text);
		}		
		
		Integer andares = Integer.parseInt(oneDetails.get("andares").toString());
		int count = 1;
		while (count <= andares) {  
			if (count == 1)
			{
				comboBox_1.addItem("Térreo");
			}else{
				comboBox_1.addItem(count + "º Andar");
			}	        
			count++;
		}
				
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("nome do ambiente", list.getSelectedValue().toString());
				DBCursor cursor = ambientes.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				comboBox.setSelectedItem(data.get("tipo de ambiente").toString());
				comboBox_1.setSelectedItem(data.get("andar").toString());
				textField.setText(data.get("nome do ambiente").toString());
				textField_1.setText(data.get("latitude").toString());
				textField_2.setText(data.get("longitude").toString());
				textArea.setText(data.get("resumo").toString());
				textArea_1.setText(data.get("descricao").toString());
				System.out.println(data);
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
		});
			
		
		JButton btnLimpar = new JButton("Excluir");
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DBCursor cursor = ambientes.find(search_atualizacao);
				ambientes.remove(search_atualizacao);		
				limpaCampos();
				atualizaList();
				
			}
		});
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Gerencia_Ambientes.this.dispose();
			}
		});
		
		JLabel lblTipoDeAmbiente = new JLabel("Tipo de Ambiente:");
		
	
		JLabel lblAndar = new JLabel("Andar:");
		
		
		JLabel lblNomeDoAmbiente = new JLabel("Nome do Ambiente:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblLatitude = new JLabel("Latitude:");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude:");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Resumo:");
		
	
		JLabel lblDescrio = new JLabel("Descrição:");
		

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblTipoDeAmbiente)
							.addGap(26)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblAndar)
							.addGap(18)
							.addComponent(comboBox_1, 0, 93, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNomeDoAmbiente)
								.addComponent(lblLatitude)
								.addComponent(lblNewLabel)
								.addComponent(lblDescrio))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
								.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textField_1, 84, 84, 84)
									.addGap(41)
									.addComponent(lblLongitude)
									.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
									.addComponent(textField_2, 83, 83, 83))
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTipoDeAmbiente)
						.addComponent(comboBox)
						.addComponent(lblAndar)
						.addComponent(comboBox_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNomeDoAmbiente)
						.addComponent(textField))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLatitude)
						.addComponent(textField_1)
						.addComponent(lblLongitude)
						.addComponent(textField_2))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 49, GroupLayout.DEFAULT_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(13)
							.addComponent(lblDescrio))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		//JList list = new JList();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(list, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(list, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblAmbientesCadastrados = new JLabel("Ambientes cadastrados:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnCadastrar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnCancelar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnLimpar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(lblAmbientesCadastrados))
					.addGap(9))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCadastrar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnLimpar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancelar)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblAmbientesCadastrados)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void atualizaList()
	{
		DBCursor cursor_1 = ambientes.find();
		String ambiente_updated;
		listModel.clear();
		while (cursor_1.hasNext()) {
			ambiente_updated = cursor_1.next().get("nome do ambiente").toString();
			listModel.addElement(ambiente_updated);
		}
		list.validate();
	}
	
	public void limpaCampos()
	{
		comboBox.setSelectedItem("");
		comboBox_1.setSelectedItem("");	
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textArea.setText("");
		textArea_1.setText("");
	}
	
}
