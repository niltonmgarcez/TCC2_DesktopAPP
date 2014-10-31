package tcc2.TCC2_DesktopApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreeModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTree;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Seleciona_Path extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	DBCollection ambientes = db.getCollection("Ambientes");
	DBCollection objetos = db.getCollection("Objetos");
	BasicDBObject documento = new BasicDBObject();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Seleciona_Path frame = new Seleciona_Path();
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
	public Seleciona_Path() throws UnknownHostException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblPath = new JLabel("Path:");
		
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPath)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPath)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Seleciona_Path.this.dispose();
			}
		});
		
		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//What ever the file path is.
					File predio_file = new File("C:/Users/Nilton Garcez/Desktop/Arquivos JSON/Macroambientes.json");
			        FileOutputStream is = new FileOutputStream(predio_file);
			        OutputStreamWriter osw = new OutputStreamWriter(is);    
			        Writer w = new BufferedWriter(osw);					        
			        DBCursor cursor = predio.find();
					while (cursor.hasNext()) {
						w.write(cursor.next().toString());
					}
					w.close();
					
					File ambientes_file = new File("C:/Users/Nilton Garcez/Desktop/Arquivos JSON/Ambientes.json");
			        FileOutputStream FOS = new FileOutputStream(ambientes_file);
			        OutputStreamWriter OSW_1 = new OutputStreamWriter(FOS);    
			        Writer writer_1 = new BufferedWriter(OSW_1);					        
			        
			        cursor = ambientes.find();
					while (cursor.hasNext()) {
						writer_1.write(cursor.next().toString());
					}
					writer_1.close();

					
					File objetos_file = new File("C:/Users/Nilton Garcez/Desktop/Arquivos JSON/Objetos.json");
			        FileOutputStream FOS_1 = new FileOutputStream(objetos_file);
			        OutputStreamWriter OSW_2 = new OutputStreamWriter(FOS_1);    
			        Writer writer_2 = new BufferedWriter(OSW_2);		
			        
			        cursor = objetos.find();
					while (cursor.hasNext()) {
						writer_2.write(cursor.next().toString());
					}
					writer_2.close();

			       
			    } catch (IOException e3) {
			        System.err.println("Problem writing to the file statsTest.txt");
			    }
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 486, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(330)
					.addComponent(btnExportar)
					.addGap(6)
					.addComponent(btnCancelar))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnExportar)
						.addComponent(btnCancelar)))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
