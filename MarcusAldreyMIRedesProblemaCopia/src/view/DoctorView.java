package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.ControllerMedico;
import exceptions.MensagemInvalidaException;
import exceptions.PacienteNaoEncontradoException;
import model.Paciente;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

public class DoctorView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPorta;
	private JTextField textPorta;
	private JLabel lblIp;
	private JTextField textIP;
	private String[] pacientesCriticos;
	protected JList<?> list;
	private JLabel textFreq;
	private JLabel textPress;
	private JLabel textEstado;
	private String pacienteSelecionado;
	private Paciente pacienteMonitorado;
	private Timer timer;
	boolean monitoramentoAtivo;
	private JButton btnMonitoramento;
	private JButton btnAtualizar;
	private JComboBox<String> comboBoxOutros;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorView frame = new DoctorView();
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
	public DoctorView() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();		
		setBounds((screen.width-600)/2, (screen.height-500)/2, 600, 550);
		this.setTitle("Doctor View");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* Instancia da lista */
		DefaultListModel<String> model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setBounds(10, 144, 249, 180);
		list.setVisibleRowCount(10);
		list.addListSelectionListener(new SelecaoDePaciente());
		contentPane.add(list);

		timer = new Timer();

		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(169, 11, 89, 49);
		btnConectar.addActionListener(new ActionListener() {

			/* Método de conectar ao servidor */
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					ControllerMedico.getInstance().criarConexao(textIP.getText(), Integer.parseInt(textPorta.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Não foi possível criar conexão");
					e1.printStackTrace();
					return;
				}
				btnConectar.setText("Conectado");
			}
		});
		contentPane.add(btnConectar);

		lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblPorta.setBounds(10, 14, 61, 14);
		contentPane.add(lblPorta);

		textPorta = new JTextField();
		textPorta.setText("12345");
		textPorta.setColumns(10);
		textPorta.setBounds(59, 11, 100, 20);
		contentPane.add(textPorta);

		lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblIp.setBounds(10, 43, 61, 14);
		contentPane.add(lblIp);

		textIP = new JTextField();
		textIP.setText("localhost");
		textIP.setColumns(10);
		textIP.setBounds(59, 40, 100, 20);
		contentPane.add(textIP);

		comboBoxOutros = new JComboBox<String>();
		comboBoxOutros.setBounds(10, 360, 249, 20);
		contentPane.add(comboBoxOutros);

		JLabel lblPacientesCrticos = new JLabel("Pacientes cr\u00EDticos:");
		lblPacientesCrticos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPacientesCrticos.setBounds(10, 95, 150, 14);
		contentPane.add(lblPacientesCrticos);

		JLabel lblOutrosPacientes = new JLabel("Outros pacientes:");
		lblOutrosPacientes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOutrosPacientes.setBounds(10, 335, 150, 14);
		contentPane.add(lblOutrosPacientes);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(279, 4, 2, 495);
		contentPane.add(separator);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(DoctorView.class.getResource("/view/if_3_2104702.png")));
		label.setBounds(291, 59, 150, 128);
		contentPane.add(label);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(DoctorView.class.getResource("/view/if_100_Pressure_Reading_183415.png")));
		label_1.setBounds(291, 209, 150, 128);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(DoctorView.class.getResource("/view/if_running_172541.png")));
		label_2.setBounds(291, 372, 150, 128);
		contentPane.add(label_2);

		textFreq = new JLabel("0");
		textFreq.setFont(new Font("Roboto", Font.PLAIN, 24));
		textFreq.setHorizontalAlignment(SwingConstants.CENTER);
		textFreq.setBounds(430, 76, 112, 111);
		contentPane.add(textFreq);

		textPress = new JLabel("0/0");
		textPress.setFont(new Font("Roboto", Font.PLAIN, 24));
		textPress.setHorizontalAlignment(SwingConstants.CENTER);
		textPress.setBounds(430, 209, 112, 128);
		contentPane.add(textPress);

		textEstado = new JLabel("...");
		textEstado.setFont(new Font("Roboto", Font.PLAIN, 16));
		textEstado.setHorizontalAlignment(SwingConstants.CENTER);
		textEstado.setBounds(430, 372, 112, 127);
		contentPane.add(textEstado);

		JLabel lblInformaesDoPaciente = new JLabel("Monitoramento em tempo real");
		lblInformaesDoPaciente.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInformaesDoPaciente.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformaesDoPaciente.setBounds(291, 14, 283, 14);
		contentPane.add(lblInformaesDoPaciente);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 76, 249, 8);
		contentPane.add(separator_1);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(169, 91, 89, 23);
		btnAtualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* Atualiza o nome de todos os pacientes que estão no servidor e separa os primeiros 5, como críticos */
				
				// TODO Auto-generated method stub
				try {
					pacientesCriticos = ControllerMedico.getInstance().getPacientes();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Não foi possível receber a lista de clientes");
					e1.printStackTrace();
					return;
				}
				System.out.println(pacientesCriticos.length);
				model.clear();
				comboBoxOutros.removeAllItems();
				if(pacientesCriticos.length > 5) {
					for(int i=5;i<pacientesCriticos.length;i++)
						comboBoxOutros.addItem(pacientesCriticos[i]);
				}
				for(String nome : pacientesCriticos)
					model.addElement(nome);
			}
		});
		contentPane.add(btnAtualizar);				

		btnMonitoramento = new JButton("Ativar");
		btnMonitoramento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(monitoramentoAtivo) 
					pararTransmissao();
				else 
					iniciarTransmissao();	
			}
		});
		btnMonitoramento.setBounds(387, 37, 89, 23);
		contentPane.add(btnMonitoramento);
	}

	private void pararTransmissao(){
		monitoramentoAtivo = false;
		btnMonitoramento.setText("Ativar");
		btnMonitoramento.setForeground(Color.BLACK);
		btnAtualizar.setEnabled(true);
		list.setEnabled(true);
		timer.cancel();
		textEstado.setText("...");
		textFreq.setText("0");
		textPress.setText("0");
		System.out.println("Monitoramento cancelado");
	}



	private void iniciarTransmissao() {

		atualizarPacienteMonitorado();
		monitoramentoAtivo = true;
		btnMonitoramento.setText("Desativar");
		btnMonitoramento.setForeground(Color.DARK_GRAY);
		btnAtualizar.setEnabled(false);
		list.setEnabled(false);
		timer = new Timer();
		timer.schedule(new Monitoramento(),2000,2000);
		System.out.println("Monitoramento iniciado");

	}

	private class SelecaoDePaciente implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(e.getValueIsAdjusting()) {
				pacienteSelecionado = (String) list.getSelectedValue();
				System.out.println(pacienteSelecionado);
			}
		}

	}
	
	private void atualizarPacienteMonitorado() {
			try {
				
				pacienteMonitorado = ControllerMedico.getInstance().receberPacientedoServidor((String) list.getSelectedValue());
			
			} catch (ClassNotFoundException | IOException | PacienteNaoEncontradoException
					| MensagemInvalidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private class Monitoramento extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/* A cada dois segundos, caso o monitoramento esteja ativo, será enviado uma solicitação ao servidor para atualizar
			 * os valores do paciente monitorado e exibi-los na tela. */
			atualizarPacienteMonitorado();
			textFreq.setText(Integer.toString(pacienteMonitorado.getFrequencia()));
			textPress.setText(pacienteMonitorado.getSistole()+"/"+pacienteMonitorado.getDiastole());
			if(pacienteMonitorado.isEmMovimento().equals("true"))
				textEstado.setText("Em movimento");
			else
				textEstado.setText("Parado");

		}

	}
}
