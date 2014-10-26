package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Config_Gerencia_Predio extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	MongoClient mongoClient = new MongoClient();
	DB db = mongoClient.getDB("TCC2_Data");
	DBCollection predio = db.getCollection("Macroambiente");
	BasicDBObject documento = new BasicDBObject();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Config_Gerencia_Predio frame = new Config_Gerencia_Predio();
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
	public Config_Gerencia_Predio() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 445, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JTextArea textArea = new JTextArea();
		
		final JTextArea textArea_1 = new JTextArea();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		textField = new JTextField();
		textField.setColumns(10);
				
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("predio_ID", "1");
		DBCursor result = predio.find(searchQuery);
		DBObject oneDetails = result.next();
	    String andar =oneDetails.get("andares").toString();
        textField.setText(andar.toString());
	    	
	    Pattern pattern = Pattern.compile("\"(.*?)\"");
	    String tiposAmbientes = oneDetails.get("tipos de ambiente").toString();
	    Matcher matcher = pattern.matcher(tiposAmbientes);
	    String mensagem = "";
		while (matcher.find()) {  
	        String text = matcher.group(1);
	        mensagem = mensagem + ";" + text;
		}
		textArea.setText(mensagem.substring(1,mensagem.length()));
		
	    String tiposObjeto = oneDetails.get("tipos de objeto").toString();
	    Matcher matcher_1 = pattern.matcher(tiposObjeto);
	    String mensagem_1 = "";
		while (matcher_1.find()) {  
	        String text = matcher_1.group(1);
	        mensagem_1 = mensagem_1 + ";" + text;
		}
		textArea_1.setText(mensagem_1.substring(1,mensagem_1.length()));
		
		
		JButton btnCadastrar = new JButton("Atualizar");
		btnCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put("predio_ID", "1");
				predio.remove(searchQuery);
				documento.put("predio_ID", "1");
				documento.put("andares", textField.getText());
				String[] tipos_ambientes = textArea.getText().split(";");
				BasicDBList tipos_ambiente = new BasicDBList();
				for (int i = 0; i < tipos_ambientes.length; i++)
				{
					tipos_ambiente.add(tipos_ambientes[i]);
				}
				documento.put("tipos de ambiente", tipos_ambiente);

				String[] tipos_objetos = textArea_1.getText().split(";");
				BasicDBList tipos_objeto = new BasicDBList();
				for (int i = 0; i < tipos_objetos.length; i++)
				{
					tipos_objeto.add(tipos_objetos[i]);
				}
				documento.put("tipos de objeto", tipos_objeto);
				
				predio.insert(documento);
						
				DBCursor cursor = predio.find();
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Gerencia_Predio.this.dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 274, GroupLayout.DEFAULT_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCadastrar, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnCadastrar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancelar)
					.addGap(199))
		);
		
		JLabel lblNmeroDeAndares = new JLabel("Número de Andares:");
		

		JLabel lblTiposDeAmbiente = new JLabel("Tipos de Ambiente:");
		

		
		JLabel lblTiposDeObjeto = new JLabel("Tipos de Objeto:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNmeroDeAndares)
						.addComponent(lblTiposDeAmbiente)
						.addComponent(lblTiposDeObjeto))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
						.addComponent(textArea_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNmeroDeAndares)
						.addComponent(textField))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTiposDeAmbiente)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 94, GroupLayout.DEFAULT_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textArea_1, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
						.addComponent(lblTiposDeObjeto))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
