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

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.bson.BSONObject;
import org.bson.types.BasicBSONList;

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

public class Config_Cadastro_Ambientes extends JFrame {

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
	JScrollPane scrollPane = new JScrollPane();
	JScrollPane scrollPane_1 = new JScrollPane();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Config_Cadastro_Ambientes frame = new Config_Cadastro_Ambientes();
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
	public Config_Cadastro_Ambientes() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JComboBox comboBox = new JComboBox();
		final JComboBox comboBox_1 = new JComboBox();
		final JTextArea textArea = new JTextArea();
		final JTextArea textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea);
		scrollPane_1.setViewportView(textArea_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				documento.clear();
				String tamanhoTabela = String.valueOf(ambientes.getCount() + 1);
				documento.put("ambiente_ID", tamanhoTabela);
				documento.put("tipo de ambiente", comboBox.getSelectedItem());
				if (comboBox_1.getSelectedItem().equals("Térreo"))
				{
					documento.put("andar", "1");
				}else{
					documento.put("andar", comboBox_1.getSelectedItem().toString().substring(0, 1));
				}
				documento.put("nome do ambiente", textField.getText());
				documento.put("latitude", textField_1.getText());
				documento.put("longitude", textField_2.getText());
				documento.put("resumo", textArea.getText());
				documento.put("descricao", textArea_1.getText());
				
				ambientes.insert(documento);
						
				DBCursor cursor = ambientes.find();
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
				
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
			
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				comboBox.setSelectedItem("");
				comboBox_1.setSelectedItem("");	
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textArea.setText("");
				textArea_1.setText("");
			}
		});
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Cadastro_Ambientes.this.dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLimpar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnCadastrar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLimpar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancelar)
					.addContainerGap())
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
		);
		
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
		contentPane.setLayout(gl_contentPane);
	}
}
