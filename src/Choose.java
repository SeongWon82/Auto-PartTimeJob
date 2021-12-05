import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

public class Choose extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Choose frame = new Choose();
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
	public Choose() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton radio1 = new JRadioButton("일반구직");
		radio1.setBounds(100, 145, 73, 20);
		radio1.setBackground(Color.WHITE);
		JRadioButton radio2 = new JRadioButton("웹 구직");
		radio2.setBounds(240, 145, 61, 20);
		radio2.setBackground(Color.WHITE);
		
		ButtonGroup  group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		
		contentPane.add(radio1);
		contentPane.add(radio2);
	}

}
