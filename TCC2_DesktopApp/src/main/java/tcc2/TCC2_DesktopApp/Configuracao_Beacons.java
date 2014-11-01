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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Configuracao_Beacons extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection beacons = db.getCollection("Beacons");
	DBCollection ambientes = db.getCollection("Ambientes");
	BasicDBObject documento = new BasicDBObject();
	final DefaultListModel listModel = new DefaultListModel();
	BasicDBObject search_atualizacao = new BasicDBObject();
	Pattern pattern = Pattern.compile("\"(.*?)\"");
	JScrollPane scrollpane = new JScrollPane();

	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	JList list = new JList(listModel);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuracao_Beacons frame = new Configuracao_Beacons();
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
	public Configuracao_Beacons() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final JComboBox comboBox = new JComboBox();
				
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 410, 127);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		atualizaList(list);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(10, 148, 92, 23);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documento.clear();
				String tamanhoTabela = String.valueOf(beacons.getCount() + 1);
				documento.put("beacon_ID", tamanhoTabela);
				documento.put("identificacao do ambiente", comboBox.getSelectedItem());				
				documento.put("identificacao do beacon", textField.getText());
				documento.put("posicao em X", textField_1.getText());
				documento.put("posicao em Y", textField_2.getText());
				documento.put("major", textField_3.getText());
				documento.put("minor", textField_4.getText());
				beacons.insert(documento);				
				atualizaList(list);
				clear();				
			}
		});
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(112, 148, 92, 23);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBCursor cursor = beacons.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("beacon_ID", data.get("beacon_ID").toString());
				newDocument.put("identificacao do ambiente", comboBox.getSelectedItem());				
				newDocument.put("identificacao do beacon", textField.getText());
				newDocument.put("posicao em X", textField_1.getText());
				newDocument.put("posicao em Y", textField_2.getText());
				newDocument.put("major", textField_3.getText());
				newDocument.put("minor", textField_4.getText());
				BasicDBObject updateObj = new BasicDBObject();				
				updateObj.put("$set", newDocument);								
				beacons.update(search_atualizacao, newDocument);
				atualizaList(list);	
				clear();				
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(214, 148, 92, 23);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				beacons.remove(search_atualizacao);				
				atualizaList(list);	
				clear();				
			}
		});
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBounds(316, 148, 104, 23);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracao_Beacons.this.dispose();
			}
		});
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("predio_ID", "1");
		DBCursor result = ambientes.find(searchQuery);
		while (result.hasNext()) {  
	        comboBox.addItem(result.next().get("identificacao do ambiente").toString());
		}
		
		JLabel lblA = new JLabel("Ambiente:");
		
		JLabel lblNomeDoAmbiente = new JLabel("Identificação:");
		
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
		
		JLabel lblDescrio = new JLabel("Major:");
		
		JLabel lblMinor = new JLabel("Minor:");
		
		textField_3 = new JTextField();
		textField_3.setBorder(border);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBorder(border);
		textField_4.setColumns(10);
		
		JScrollPane listScrollPane = new JScrollPane();
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("66px"),
				ColumnSpec.decode("25px"),
				ColumnSpec.decode("82px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("66px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("110px"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("16px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("16px"),}));
		panel.add(lblPosioEmX, "2, 6, 2, 1, left, center");
		panel.add(textField_1, "4, 6, left, top");
		panel.add(lblPosioEmY, "6, 6, 2, 1, left, center");
		panel.add(textField_2, "8, 6, left, top");
		panel.add(lblDescrio, "2, 8, left, center");
		panel.add(textField_3, "4, 8, left, top");
		panel.add(lblMinor, "6, 8, 2, 1, left, center");
		panel.add(textField_4, "8, 8, left, top");
		panel.add(lblA, "2, 2, left, center");
		panel.add(lblNomeDoAmbiente, "2, 4, 2, 1, left, center");
		panel.add(textField, "4, 4, 5, 1, fill, top");
		panel.add(comboBox, "4, 2, 5, 1, fill, top");
		
		JLabel lblBeacons = new JLabel("Beacons:");
		lblBeacons.setBounds(430, 10, 159, 14);
		contentPane.setLayout(null);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("identificacao do beacon", list.getSelectedValue().toString());
				DBCursor cursor = beacons.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				comboBox.setSelectedItem(data.get("identificacao do ambiente").toString());				
				textField.setText(data.get("identificacao do beacon").toString());
				textField_1.setText(data.get("posicao em X").toString());
				textField_2.setText(data.get("posicao em Y").toString());
				textField_3.setText(data.get("major").toString());
				textField_4.setText(data.get("minor").toString());
				System.out.println(data);
			}
		});
				
		list.setBorder(border);
		list.setBounds(430, 33, 164, 138);
		contentPane.add(list);
		contentPane.add(panel);
		contentPane.add(lblBeacons);
		contentPane.add(btnCadastrar);
		contentPane.add(btnAtualizar);
		contentPane.add(btnExcluir);
		contentPane.add(btnFechar);
	}
	
	public void atualizaList(JList list)
	{
		DBCursor cursor = beacons.find();
		String beacon_updated;
		listModel.clear();
		while (cursor.hasNext()) {
			beacon_updated = cursor.next().get("identificacao do beacon").toString();
			listModel.addElement(beacon_updated);	
		}
	}
	
	public void clear()
	{
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");		
		textField_3.setText("");
		textField_4.setText("");
	}

}
