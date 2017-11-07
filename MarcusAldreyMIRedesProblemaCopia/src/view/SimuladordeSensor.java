package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.ControllerSensor;
import control.ControllerServerDeBorda;
import exceptions.NenhumServerDeBordaEncontraException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JSeparator;

public class SimuladordeSensor extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFreq;
	private JTextField textPress;
	private int di = 12, si = 8, freq = 80;
	private JButton btnAddDi;
	private JButton btnLessDi;
	private JButton btnLessSi;
	private JButton btnLessFreq;
	private JButton btnAddFreq;
	private JButton btnAddSi;
	private JTextField textPorta;
	private JTextField textIP;
	private boolean estaTransmitindo;
	private boolean emMovimento;
	private JButton btnStartStop;
	private Timer timer;
	private JTextField txtNome;
	private JComboBox<String> comboBoxEstado;
	private ControllerSensor controller;
	private int tentativas;
	private JTextField textField_X;
	private JTextField textField_Y;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimuladordeSensor frame = new SimuladordeSensor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public SimuladordeSensor() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();		
		setBounds((screen.width-600)/2, (screen.height-500)/2, 600, 500);
		contentPane = new JPanel();
		this.setTitle("Data generator");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		controller = ControllerSensor.getInstance();

		textFreq = new JTextField();
		textFreq.setBackground(Color.WHITE);
		textFreq.setHorizontalAlignment(SwingConstants.CENTER);
		textFreq.setFont(new Font("Bernard MT Condensed", Font.PLAIN, 25));
		textFreq.setText("80");
		textFreq.setBounds(220, 32, 144, 60);
		contentPane.add(textFreq);
		textFreq.setColumns(10);

		JLabel lblNewLabel = new JLabel("Frequ\u00EAncia card\u00EDaca");
		lblNewLabel.setBounds(10, 0, 564, 21);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);

		btnLessFreq = new JButton("");
		btnLessFreq.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/minus-symbol-inside-a-circle.png")));
		btnLessFreq.setBounds(374, 62, 30, 30);
		btnLessFreq.addActionListener(this);
		contentPane.add(btnLessFreq);

		btnAddFreq = new JButton("");
		btnAddFreq.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/plus.png")));
		btnAddFreq.setBounds(374, 32, 30, 30);
		btnAddFreq.addActionListener(this);
		contentPane.add(btnAddFreq);

		JLabel label = new JLabel("");
		label.setBounds(583, 235, 0, 0);
		contentPane.add(label);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(583, 235, 0, 0);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setBounds(583, 235, 0, 0);
		contentPane.add(label_2);

		textPress = new JTextField();
		textPress.setHorizontalAlignment(SwingConstants.CENTER);
		textPress.setText("12/8");
		textPress.setFont(new Font("Bernard MT Condensed", Font.PLAIN, 25));
		textPress.setBounds(220, 148, 144, 60);
		contentPane.add(textPress);
		textPress.setColumns(10);

		btnAddSi = new JButton("");
		btnAddSi.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/plus.png")));
		btnAddSi.setBounds(374, 148, 30, 30);
		btnAddSi.addActionListener(this);
		contentPane.add(btnAddSi);

		btnLessSi = new JButton("");
		btnLessSi.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/minus-symbol-inside-a-circle.png")));
		btnLessSi.setBounds(374, 178, 30, 30);
		btnLessSi.addActionListener(this);
		contentPane.add(btnLessSi);

		JLabel lblPressoArterial = new JLabel("Press\u00E3o Arterial");
		lblPressoArterial.setBounds(10, 116, 564, 21);
		contentPane.add(lblPressoArterial);
		lblPressoArterial.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressoArterial.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(10, 238, 564, 21);
		contentPane.add(lblEstado);
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));

		comboBoxEstado = new JComboBox<String>();
		comboBoxEstado.setFont(new Font("Roboto", Font.PLAIN, 18));
		comboBoxEstado.setBackground(Color.WHITE);
		comboBoxEstado.addItem("Em repouso");
		comboBoxEstado.addItem("Em movimento");
		comboBoxEstado.setBounds(212, 270, 160, 60);
		comboBoxEstado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				atualizarTextos();
			}
		});
		contentPane.add(comboBoxEstado);

		btnAddDi = new JButton("");
		btnAddDi.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/plus.png")));
		btnAddDi.setBounds(180, 148, 30, 30);
		btnAddDi.addActionListener(this);
		contentPane.add(btnAddDi);

		btnLessDi = new JButton("");
		btnLessDi.setIcon(new ImageIcon(SimuladordeSensor.class.getResource("/view/minus-symbol-inside-a-circle.png")));
		btnLessDi.setBounds(180, 178, 30, 30);
		btnLessDi.addActionListener(this);
		contentPane.add(btnLessDi);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 116, 564, 8);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 235, 564, 8);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 341, 564, 8);
		contentPane.add(separator_2);

		JLabel lblInformaesDeConexo = new JLabel("Informa\u00E7\u00F5es de conex\u00E3o");
		lblInformaesDeConexo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInformaesDeConexo.setBounds(10, 360, 159, 14);
		contentPane.add(lblInformaesDeConexo);

		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 385, 46, 14);
		contentPane.add(lblPorta);

		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 410, 46, 14);
		contentPane.add(lblIp);

		textPorta = new JTextField();
		textPorta.setText("12345");
		textPorta.setBounds(42, 382, 86, 20);
		contentPane.add(textPorta);
		textPorta.setColumns(10);

		textIP = new JTextField();
		textIP.setText("localhost");
		textIP.setColumns(10);
		textIP.setBounds(42, 407, 86, 20);
		contentPane.add(textIP);

		btnStartStop = new JButton("Iniciar transmiss\u00E3o");
		btnStartStop.setBounds(220, 381, 144, 43);
		btnStartStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(estaTransmitindo) 
					pararTransmissao();
				else 
					iniciarTransmissao();					
			}
		});
		contentPane.add(btnStartStop);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 435, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setText("Jo\u00E3o");
		txtNome.setColumns(10);
		txtNome.setBounds(42, 432, 86, 20);
		contentPane.add(txtNome);

		JLabel lblLocalizao = new JLabel("Localiza\u00E7\u00E3o");
		lblLocalizao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLocalizao.setBounds(415, 360, 159, 14);
		contentPane.add(lblLocalizao);

		JLabel lblX = new JLabel("X:");
		lblX.setBounds(415, 385, 10, 14);
		contentPane.add(lblX);

		textField_X = new JTextField();
		textField_X.setText("12345");
		textField_X.setColumns(10);
		textField_X.setBounds(425, 382, 36, 20);
		contentPane.add(textField_X);

		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(415, 410, 10, 14);
		contentPane.add(lblY);

		textField_Y = new JTextField();
		textField_Y.setText("localhost");
		textField_Y.setColumns(10);
		textField_Y.setBounds(425, 407, 36, 20);
		contentPane.add(textField_Y);
	}

	private void atualizarTextos() {
		textPress.setText(di + "/" + si);	
		textFreq.setText(Integer.toString(freq));
		if(comboBoxEstado.getSelectedIndex() == 0)
			emMovimento = false;
		else
			emMovimento = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnAddFreq)) {
			freq+=5;
		}
		else if(e.getSource().equals(btnLessFreq)) {
			freq-=5;
		}
		else if(e.getSource().equals(btnAddSi)) {
			si+=1;
		}
		else if(e.getSource().equals(btnLessSi)) {
			si-=1;
		}
		else if(e.getSource().equals(btnAddDi)) {
			di+=1;
		}
		else if(e.getSource().equals(btnLessDi)) {
			di-=1;
		}
		atualizarTextos();
	}

	/**
	 * 
	 */
	private void iniciarTransmissao() {
		String endereco = null;
		try {
			try {
				endereco = controller.infoServerMaisProximo(textIP.getText(), Integer.parseInt(textPorta.getText()), Integer.parseInt(textField_X.getText()), Integer.parseInt(textField_Y.getText()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (NenhumServerDeBordaEncontraException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Nenhum servidor de borda na lista da nuvem");
				e.printStackTrace();
				return;
			}
		} catch (NumberFormatException | IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Não foi possível criar conexão");
			e1.printStackTrace();
			return;
		}
		if(endereco == null)
			return;
		else {
			String[] ipEPorta = endereco.split(",");
			String ip = ipEPorta[0];
			int porta = Integer.parseInt(ipEPorta[1]);
			try {
				controller.criarConexao(ip, porta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		textIP.setEditable(false);
		textPorta.setEditable(false);
		textIP.setEnabled(false);
		textPorta.setEnabled(false);
		txtNome.setEnabled(false);
		estaTransmitindo = true;
		btnStartStop.setText("Transmitindo");
		btnStartStop.setForeground(Color.DARK_GRAY);
		timer = new Timer();
		timer.schedule(new Transmissao(),2000,2000);
		System.out.println("Tramissão iniciada");
	}


	private void pararTransmissao(){
		estaTransmitindo = false;
		btnStartStop.setText("Iniciar Transmissão");
		btnStartStop.setForeground(Color.BLACK);
		textIP.setEditable(true);
		textPorta.setEditable(true);
		textIP.setEnabled(true);
		textPorta.setEnabled(true);
		txtNome.setEnabled(true);
		timer.cancel();
		System.out.println("Tramissão cancelada");
		tentativas = 0;
		try {
			controller.enviarMensagem("disconnect sensor," + txtNome.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private class Transmissao extends TimerTask {
		public void run() {
			/*Método chamado a cada 2 segundos. Envia as informações do sensor para o servidor. Caso tente 5 vezes e não consiga, para a transmissão*/
			if(tentativas == 5) {
				pararTransmissao();
				JOptionPane.showMessageDialog(null, "Conexão perdida");
				return;
			}
			try {
				String mensagem = "connect sensor," + txtNome.getText() + "," + di + "," + si + "," + freq + "," + emMovimento;
				controller.enviarMensagem(mensagem);
				System.out.println(mensagem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				tentativas++;
				e.printStackTrace();
			}
		}
	}
}
