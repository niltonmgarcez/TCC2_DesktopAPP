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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Configuracao_Predio extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	BasicDBObject documento = new BasicDBObject();
	JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
	Border border = BorderFactory.createLineBorder(Color.BLACK);
	final DefaultListModel listModel = new DefaultListModel();
	BasicDBObject search_atualizacao = new BasicDBObject();
	Pattern pattern = Pattern.compile("\"(.*?)\"");

	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configuracao_Predio frame = new Configuracao_Predio();
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
	public Configuracao_Predio() throws UnknownHostException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		final JTextArea textArea  = new JTextArea();;
		final JTextArea textArea_1  = new JTextArea();;

		DBCursor cursor = predio.find();
		if (cursor.size()>0)
		{
			String ambiente;
			while (cursor.hasNext()) {
				ambiente = cursor.next().get("nome").toString();
				listModel.addElement(ambiente);
			}
		}

		final JList list = new JList(listModel);
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//BasicDBObject searchQuery = new BasicDBObject();
				//searchQuery.put("predio_ID", "1");
				//predio.remove(searchQuery);
				documento.put("predio_ID", "1");
				documento.put("nome", textField.getText());
				documento.put("descricao", textArea.getText());
				String[] tipos_ambientes = textArea_1.getText().split(";");
				BasicDBList tipos_ambiente = new BasicDBList();
				for (int i = 0; i < tipos_ambientes.length; i++)
				{
					tipos_ambiente.add(tipos_ambientes[i]);
				}
				documento.put("tipos de ambiente", tipos_ambiente);
				documento.put("andares", textField_1.getText());
							
				predio.insert(documento);
						
				atualizaList(list);
				
				textField.setText("");
				textField_1.setText("");
				textArea.setText("");
				textArea_1.setText("");
				
				DBCursor cursor = predio.find();
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			}
		});
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBCursor cursor = predio.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();				
				BasicDBObject newDocument = new BasicDBObject();
				newDocument.put("predio_ID", data.get("predio_ID").toString());
				newDocument.put("nome", textField.getText());
				newDocument.put("descricao", textArea.getText());	
				String[] tipos_ambientes = textArea_1.getText().split(";");
				BasicDBList tipos_ambiente = new BasicDBList();
				for (int i = 0; i < tipos_ambientes.length; i++)
				{
					tipos_ambiente.add(tipos_ambientes[i]);
				}
				newDocument.put("tipos de ambiente", tipos_ambiente);
				newDocument.put("andares", textField_1.getText());

				BasicDBObject updateObj = new BasicDBObject();
				updateObj.put("$set", newDocument);
								
				predio.update(search_atualizacao, newDocument);
				
				textField.setText("");
				textField_1.setText("");
				textArea.setText("");
				textArea_1.setText("");
				atualizaList(list);
			}
		});
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				predio.remove(search_atualizacao);
				textField.setText("");
				textField_1.setText("");
				textArea.setText("");
				textArea_1.setText("");
				atualizaList(list);
			}
		});
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Configuracao_Predio.this.dispose();
			}
		});
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblPrdios = new JLabel("Prédios:");
		
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search_atualizacao = new BasicDBObject();
				search_atualizacao.put("nome", list.getSelectedValue().toString());
				DBCursor cursor = predio.find(search_atualizacao);
				BasicDBObject data = (BasicDBObject) cursor.next();
				textField.setText(data.get("nome").toString());
				textArea.setText(data.get("descricao").toString());			    
			    String ambientes = data.get("tipos de ambiente").toString();
			    System.out.println(ambientes);
			    Matcher matcher = pattern.matcher(ambientes);
			    String mensagem = "";
				while (matcher.find()) {  
			        String text = matcher.group(1);
			        mensagem = mensagem + ";" + text;
				}
				textArea_1.setText(mensagem.substring(1,mensagem.length()));
				
				textField_1.setText(data.get("andares").toString());
			}
		});
		list.setBorder(border);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 401, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addComponent(lblPrdios)
						.addComponent(btnFechar, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCadastrar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAtualizar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExcluir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnFechar)
							.addGap(18)
							.addComponent(lblPrdios)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(list, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JLabel lblIdentificaoDoPrdio = new JLabel("Identificação do Prédio:");
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBorder(border);
		
		JLabel lblDescrio = new JLabel("Descrição:");
		
		textArea.setLineWrap(true);
		textArea.setBorder(border);
		
		JLabel lblNewLabel = new JLabel("Ambientes:");
		
		
		textArea_1.setBorder(border);
		
		JLabel lblNmeroDeAndares = new JLabel("Número de Andares:");
		
		textField_1 = new JTextField();
		textField_1.setBorder(border);
		textField_1.setColumns(10);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(36, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIdentificaoDoPrdio)
						.addComponent(lblDescrio)
						.addComponent(lblNewLabel)
						.addComponent(lblNmeroDeAndares))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(textField_1)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
						.addComponent(textArea_1)
						.addComponent(textArea))
					.addGap(18))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIdentificaoDoPrdio)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblDescrio)
							.addGap(87)
							.addComponent(lblNewLabel))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textArea_1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNmeroDeAndares))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void atualizaList(JList list)
	{
		DBCursor cursor = predio.find();
		String predio_updated;
		listModel.clear();
		while (cursor.hasNext()) {
			predio_updated = cursor.next().get("nome").toString();
			listModel.addElement(predio_updated);
		}
		list.validate();
	}
}
