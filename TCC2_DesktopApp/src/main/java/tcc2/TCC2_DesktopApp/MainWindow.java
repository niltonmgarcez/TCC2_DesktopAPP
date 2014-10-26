package tcc2.TCC2_DesktopApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;

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
	public MainWindow() {
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
				Config_Cadastro_Predio CCP = new Config_Cadastro_Predio();
				CCP.setVisible(true);
			}
			
		});
		mnCadastros.add(mntmPrdio);
		
		JMenuItem mntmAmniente = new JMenuItem("Ambientes");
		mnCadastros.add(mntmAmniente);
		
		JMenuItem mntmObjetos = new JMenuItem("Objetos");
		mnCadastros.add(mntmObjetos);
		
		JMenu mnGerenciamento = new JMenu("Gerenciamento");
		mnConfiguraes.add(mnGerenciamento);
		
		JMenuItem mntmPrdio_1 = new JMenuItem("Prédio");
		mnGerenciamento.add(mntmPrdio_1);
		
		JMenuItem mntmAmbientes = new JMenuItem("Ambientes");
		mnGerenciamento.add(mntmAmbientes);
		
		JMenuItem mntmObjetos_1 = new JMenuItem("Objetos");
		mnGerenciamento.add(mntmObjetos_1);
	}

}
