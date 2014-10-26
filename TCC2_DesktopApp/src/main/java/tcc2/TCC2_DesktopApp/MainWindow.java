package tcc2.TCC2_DesktopApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;

public class MainWindow {

	private JFrame frame;
	MongoClient mongoClient = new MongoClient();
	DB db = mongoClient.getDB("TCC2_Data");
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
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() throws UnknownHostException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnConfiguraes = new JMenu("Configurações");
		menuBar.add(mnConfiguraes);
		
		JMenu mnCadastros = new JMenu("Cadastros");
		mnConfiguraes.add(mnCadastros);
		
		JMenuItem mntmPrdio = new JMenuItem("Prédio");
		mntmPrdio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Config_Cadastro_Predio CCP;
				try {
					CCP = new Config_Cadastro_Predio();
					CCP.setVisible(true);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		mnCadastros.add(mntmPrdio);
		
		JMenuItem mntmAmniente = new JMenuItem("Ambientes");
		mntmAmniente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Cadastro_Ambientes CCA;
				try {
					CCA = new Config_Cadastro_Ambientes();
					CCA.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnCadastros.add(mntmAmniente);
		
		JMenuItem mntmObjetos = new JMenuItem("Objetos");
		mntmObjetos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Cadastro_Objeto CCO;
				try {
					CCO = new Config_Cadastro_Objeto();
					CCO.setVisible(true);
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		mnCadastros.add(mntmObjetos);
		
		JMenu mnGerenciamento = new JMenu("Gerenciamento");
		mnConfiguraes.add(mnGerenciamento);
		
		JMenuItem mntmPrdio_1 = new JMenuItem("Prédio");
		mntmPrdio_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Gerencia_Predio CGP;
				try {
					CGP = new Config_Gerencia_Predio();
					CGP.setVisible(true);
				} catch (UnknownHostException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			}
		});
		mnGerenciamento.add(mntmPrdio_1);
		
		JMenuItem mntmAmbientes = new JMenuItem("Ambientes");
		mntmAmbientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Gerencia_Ambientes CGA;
				try {
					CGA = new Config_Gerencia_Ambientes();
					CGA.setVisible(true);
				} catch (UnknownHostException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			}
		});
		mnGerenciamento.add(mntmAmbientes);
		
		JMenuItem mntmObjetos_1 = new JMenuItem("Objetos");
		mntmObjetos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config_Gerencia_Objetos CGO;
				try {
					CGO = new Config_Gerencia_Objetos();
					CGO.setVisible(true);
				} catch (UnknownHostException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
			}
		});
		mnGerenciamento.add(mntmObjetos_1);
		
		JMenu mnExportar = new JMenu("Exportar");
		menuBar.add(mnExportar);
		
		JMenuItem mntmArquivoJson = new JMenuItem("Arquivo JSON");
		mntmArquivoJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Seleciona_Path SP;
				try {
					SP = new Seleciona_Path();
					SP.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mnExportar.add(mntmArquivoJson);
	}

}
