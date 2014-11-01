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
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {

	private JFrame frame;
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	DBCollection ambientes = db.getCollection("Ambientes");
	DBCollection objetos = db.getCollection("Objetos");
	DBCollection beacons = db.getCollection("Beacons");
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
		
		JMenu mnConfiguraes = new JMenu("Gerenciamento");
		menuBar.add(mnConfiguraes);
		
		JMenuItem mntmPrdio = new JMenuItem("Prédio");
		mntmPrdio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Configuracao_Predio CP = new Configuracao_Predio();
					CP.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
			}
		});
		mnConfiguraes.add(mntmPrdio);
		
		JMenuItem mntmAmbientes = new JMenuItem("Ambientes");
		mntmAmbientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Configuracao_Ambientes CA = new Configuracao_Ambientes();
					CA.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnConfiguraes.add(mntmAmbientes);
		
		JMenuItem mntmObjetos = new JMenuItem("Objetos");
		mntmObjetos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Configuracao_Objetos CO = new Configuracao_Objetos();
					CO.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnConfiguraes.add(mntmObjetos);
		
		JMenuItem mntmBeacons = new JMenuItem("Beacons");
		mntmBeacons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Configuracao_Beacons CB = new Configuracao_Beacons();
					CB.setVisible(true);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnConfiguraes.add(mntmBeacons);
	}

}
