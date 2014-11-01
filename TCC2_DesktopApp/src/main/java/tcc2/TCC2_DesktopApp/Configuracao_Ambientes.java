/**
 * 
 */
package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

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

/**
 * @author Nilton Mena Garcez
 *
 */
public class Configuracao_Ambientes extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	DBCollection ambientes = db.getCollection("Ambientes");
	BasicDBObject documento = new BasicDBObject();
	final DefaultListModel listModel = new DefaultListModel();
	BasicDBObject search_atualizacao = new BasicDBObject();
	Pattern pattern = Pattern.compile("\"(.*?)\"");
	JScrollPane scrollpane = new JScrollPane();
	final JTextArea textArea = new JTextArea();
	final JTextArea textArea_1 = new JTextArea();

	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuracao_Ambientes frame = new Configuracao_Ambientes();
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
	public Configuracao_Ambientes() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final JComboBox comboBox = new JComboBox();
		final JComboBox comboBox_1 = new JComboBox();
	
		final JList list_1 = new JList(listModel);
		
		atualizaList(list_1);
		
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("identificacao do ambiente", list_1.getSelectedValue().toString());
				DBCursor cursor = ambientes.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				comboBox.setSelectedItem(data.get("tipo de ambiente").toString());
				if (data.get("andar").equals("1"))
				{
					comboBox_1.setSelectedItem("Térreo");
				}else{
					comboBox_1.setSelectedItem(data.get("andar").toString()+"º Andar");
				}
				
				textField.setText(data.get("identificacao do ambiente").toString());
				textField_1.setText(data.get("posicao em X").toString());
				textField_2.setText(data.get("posicao em Y").toString());
				textArea.setText(data.get("resumo").toString());
				textArea_1.setText(data.get("descricao").toString());

				String ambientes_inter = data.get("ambientes interligados").toString();
			    if (!ambientes_inter.toString().isEmpty())
			    {
					Matcher matcher = pattern.matcher(ambientes_inter);
				    String mensagem = "";
					while (matcher.find()) {  
				        String text = matcher.group(1);
				        mensagem = mensagem + ";" + text;
					}
					textField_3.setText(mensagem.substring(1,mensagem.length()));
			    }else{textField_3.setText("");}
				System.out.println(data);
			}
		});
				
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documento.clear();
				String tamanhoTabela = String.valueOf(ambientes.getCount() + 1);
				documento.put("predio_ID","1");
				documento.put("ambiente_ID", tamanhoTabela);
				documento.put("tipo de ambiente", comboBox.getSelectedItem());
				if (comboBox_1.getSelectedItem().equals("Térreo"))
				{
					documento.put("andar", "1");
				}else{
					documento.put("andar", comboBox_1.getSelectedItem().toString().substring(0, 1));
				}
				documento.put("identificacao do ambiente", textField.getText());
				documento.put("posicao em X", textField_1.getText());
				documento.put("posicao em Y", textField_2.getText());
				documento.put("resumo", textArea.getText());
				documento.put("descricao", textArea_1.getText());
				if (!textField_3.getText().isEmpty())
				{
					String[] amb_interligados = textField_3.getText().split(";");
					BasicDBList tipos_ambiente = new BasicDBList();
					for (int i = 0; i < amb_interligados.length; i++)
					{
						tipos_ambiente.add(amb_interligados[i]);
						atualizaDependencias(textField.getText(), amb_interligados[i]);
					}
					documento.put("ambientes interligados", tipos_ambiente);
				}else{documento.put("ambientes interligados", "");}
				ambientes.insert(documento);				
						
				atualizaList(list_1);
				clear();				
			}
		});
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBCursor cursor = ambientes.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("predio_ID", data.get("predio_ID").toString());
				newDocument.put("ambiente_ID", data.get("ambiente_ID").toString());
				newDocument.put("tipo de ambiente", comboBox.getSelectedItem());
				if (comboBox_1.getSelectedItem().equals("Térreo"))
				{
					newDocument.put("andar", "1");
				}else{
					newDocument.put("andar", comboBox_1.getSelectedItem().toString().substring(0, 1));
				}
				newDocument.put("identificacao do ambiente", textField.getText());	
				newDocument.put("posicao em X", textField_1.getText());
				newDocument.put("posicao em Y", textField_2.getText());
				newDocument.put("resumo",  textArea.getText());
				newDocument.put("descricao", textArea_1.getText());
				if (!textField_3.getText().isEmpty())
				{
				String[] amb_interligados = textField_3.getText().split(";");
				BasicDBList tipos_ambiente = new BasicDBList();
				for (int i = 0; i < amb_interligados.length; i++)
				{
					tipos_ambiente.add(amb_interligados[i]);
					atualizaDependencias(textField.getText(), amb_interligados[i]);
				}
				newDocument.put("ambientes interligados", tipos_ambiente);
				}else{newDocument.put("ambientes interligados", "");}
				BasicDBObject updateObj = new BasicDBObject();
				
				updateObj.put("$set", newDocument);								
				ambientes.update(search_atualizacao, newDocument);
				atualizaList(list_1);	
				clear();				
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				DBCursor cursor = ambientes.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				
				if (!textField_3.getText().isEmpty())
				{
					String[] amb_interligados = textField_3.getText().split(";");
					BasicDBList tipos_ambiente = new BasicDBList();
					for (int i = 0; i < amb_interligados.length; i++)
					{
						tipos_ambiente.add(amb_interligados[i]);
						removeDependencias(textField.getText(), amb_interligados[i]);
					}
				}
				BasicDBObject updateObj = new BasicDBObject();
				ambientes.remove(search_atualizacao);				
				atualizaList(list_1);	
				clear();				
			}
		});
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Configuracao_Ambientes.this.dispose();
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
		
		JLabel lblAmbientes_1 = new JLabel("Ambientes:");
		
		JLabel lblA = new JLabel("Tipo de Ambiente:");
		

		
		JLabel lblNewLabel = new JLabel("Andar:");
		

		
		JLabel lblNomeDoAmbiente = new JLabel("Nome do Ambiente:");
		
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
		
		JLabel lblResumo = new JLabel("Resumo:");
		

		textArea.setBorder(border);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		

		textArea_1.setBorder(border);
		
		JLabel lblAmbientes = new JLabel("Interligações:");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblA)
							.addGap(27)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNomeDoAmbiente)
								.addComponent(lblPosioEmX)
								.addComponent(lblResumo)
								.addComponent(lblDescrio)
								.addComponent(lblAmbientes))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
								.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
								.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblPosioEmY)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
								.addComponent(textField))))
					.addContainerGap(49, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblA)
						.addComponent(lblNewLabel)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblResumo)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDescrio))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmbientes)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(70))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane listScrollPane = new JScrollPane();
		list_1.setBorder(border);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 462, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(list_1, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(btnFechar, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(lblAmbientes_1))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCadastrar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAtualizar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFechar)
							.addGap(18)
							.addComponent(lblAmbientes_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void atualizaList(JList list)
		{
		DBCursor cursor = ambientes.find();
		String ambientes_updated;
		listModel.clear();
		while (cursor.hasNext()) {
			ambientes_updated = cursor.next().get("identificacao do ambiente").toString();
			listModel.addElement(ambientes_updated);
		}
		list.validate();
	}
	
	public void clear()
	{
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textArea.setText("");
		textArea_1.setText("");
	}
	
	public void atualizaDependencias(String from, String ambiente)
	{
		BasicDBObject search_atualizacao = new BasicDBObject();
		search_atualizacao.put("identificacao do ambiente", ambiente);
		DBCursor cursor = ambientes.find(search_atualizacao);
		BasicDBObject data = (BasicDBObject) cursor.next();	
		String ambientes_inter = data.get("ambientes interligados").toString();
		boolean needToAdd = true;
	    //System.out.println(ambientes_inter);
	    Matcher matcher = pattern.matcher(ambientes_inter);
	    String mensagem = "";
		while (matcher.find()) {  
	        String text = matcher.group(1);
	        if (text.equals(from)){needToAdd = false;}
	        mensagem = mensagem + ";" + text;
		}
		mensagem = mensagem + ";" + textField.getText();

		if (needToAdd)
		{
			BasicDBObject newDocument = new BasicDBObject();
			
			String[] tipos_ambientes = mensagem.substring(1,mensagem.length()).split(";");
			BasicDBList tipos_ambiente = new BasicDBList();
			for (int i = 0; i < tipos_ambientes.length; i++)
			{
				tipos_ambiente.add(tipos_ambientes[i]);
			}
			newDocument.put("predio_ID", data.get("predio_ID").toString());
			newDocument.put("ambiente_ID", data.get("ambiente_ID").toString());
			newDocument.put("tipo de ambiente", data.get("tipo de ambiente").toString());
			newDocument.put("identificacao do ambiente", data.get("identificacao do ambiente").toString());
			newDocument.put("andar", data.get("andar").toString());
			newDocument.put("posicao em X", data.get("posicao em X").toString());
			newDocument.put("posicao em Y", data.get("posicao em Y").toString());
			newDocument.put("resumo", data.get("resumo").toString());
			newDocument.put("descricao", data.get("descricao").toString());		
			newDocument.put("ambientes interligados", tipos_ambiente);
			
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
							
			ambientes.update(search_atualizacao, newDocument);
		}
	}
		
	public void removeDependencias(String from, String ambiente)
	{
		BasicDBObject search_atualizacao = new BasicDBObject();
		search_atualizacao.put("identificacao do ambiente", ambiente);
		DBCursor cursor = ambientes.find(search_atualizacao);
		BasicDBObject data = (BasicDBObject) cursor.next();	
		String ambientes_inter = data.get("ambientes interligados").toString();
		boolean needToRemove = false;
	    Matcher matcher = pattern.matcher(ambientes_inter);
	    String mensagem = "";
		while (matcher.find()) {  
	        String text = matcher.group(1);
	        if (text.equals(from)){needToRemove = true;}
	        else{mensagem = mensagem + ";" + text;}
		}
		if (needToRemove)
		{
			BasicDBObject newDocument = new BasicDBObject();
			
			String[] tipos_ambientes = mensagem.substring(1,mensagem.length()).split(";");
			BasicDBList tipos_ambiente = new BasicDBList();
			for (int i = 0; i < tipos_ambientes.length; i++)
			{
				tipos_ambiente.add(tipos_ambientes[i]);
			}
			newDocument.put("predio_ID", data.get("predio_ID").toString());
			newDocument.put("ambiente_ID", data.get("ambiente_ID").toString());
			newDocument.put("tipo de ambiente", data.get("tipo de ambiente").toString());
			newDocument.put("identificacao do ambiente", data.get("identificacao do ambiente").toString());
			newDocument.put("andar", data.get("andar").toString());
			newDocument.put("posicao em X", data.get("posicao em X").toString());
			newDocument.put("posicao em Y", data.get("posicao em Y").toString());
			newDocument.put("resumo", data.get("resumo").toString());
			newDocument.put("descricao", data.get("descricao").toString());		
			newDocument.put("ambientes interligados", tipos_ambiente);
			
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
							
			ambientes.update(search_atualizacao, newDocument);
		}
	}	
}
