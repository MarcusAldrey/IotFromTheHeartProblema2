package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.ControllerMedico;
import exceptions.MedicoNaoEncontradoException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class TelaLoginMedico extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JLabel lblSenha;
	private JTextField textSenha;
	private JLabel lblPorta;
	private JTextField textPorta;
	private JLabel lblIp;
	private JTextField textIP;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLoginMedico frame = new TelaLoginMedico();
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
	public TelaLoginMedico() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(125, 40, 100, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblNewLabel.setBounds(75, 43, 61, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ControllerMedico.getInstance().criarConexao(textIP.getText(), Integer.parseInt(textPorta.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor");
					e1.printStackTrace();
				}
				try {
					ControllerMedico.getInstance().logar(textUsuario.getText(), textSenha.getText());
				} catch (IOException | MedicoNaoEncontradoException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Não foi possível logar");
					e.printStackTrace();
					return;
				}
				DoctorView frame = new DoctorView();
				frame.setVisible(true);
			}
		});
		btnLogar.setFont(new Font("Roboto", Font.PLAIN, 11));
		btnLogar.setBounds(75, 178, 147, 23);
		contentPane.add(btnLogar);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblSenha.setBounds(75, 74, 61, 14);
		contentPane.add(lblSenha);
		
		textSenha = new JTextField();
		textSenha.setColumns(10);
		textSenha.setBounds(125, 71, 100, 20);
		contentPane.add(textSenha);
		
		lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblPorta.setBounds(75, 102, 61, 14);
		contentPane.add(lblPorta);
		
		textPorta = new JTextField();
		textPorta.setText("12345");
		textPorta.setColumns(10);
		textPorta.setBounds(125, 99, 100, 20);
		contentPane.add(textPorta);
		
		lblIp = new JLabel("IP");
		lblIp.setFont(new Font("Roboto", Font.PLAIN, 11));
		lblIp.setBounds(75, 133, 61, 14);
		contentPane.add(lblIp);
		
		textIP = new JTextField();
		textIP.setText("localhost");
		textIP.setColumns(10);
		textIP.setBounds(125, 130, 100, 20);
		contentPane.add(textIP);
	}
}
