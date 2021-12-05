import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class Resume extends JFrame implements ActionListener{
	private JTextField titleField;
	private JTextArea textArea;
	private BufferedReader in;
	private PrintWriter out;
	/**
	 * Create the panel.
	 */
	public Resume(BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
		setIconImage(Toolkit.getDefaultToolkit().getImage(signUp.class.getResource("/icon.PNG")));
		setTitle("이력서");
		setBackground(Color.WHITE);
		setBounds(127, 95, 422, 340);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("제목:");
		titleLabel.setBounds(47, 26, 39, 15);
		getContentPane().add(titleLabel);
		
		titleField = new JTextField();
		titleField.setBounds(98, 23, 252, 21);
		getContentPane().add(titleField);
		titleField.setColumns(10);
		
		JLabel textLabel = new JLabel("자유양식");
		textLabel.setBounds(47, 60, 52, 15);
		getContentPane().add(textLabel);
		
		textArea = new JTextArea();
		getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(47, 85, 305, 177);
		getContentPane().add(scrollPane);
		
		JButton submitBtn = new JButton("제출");
		submitBtn.addActionListener(this);
		submitBtn.setBounds(255, 270, 95, 23);
		getContentPane().add(submitBtn);
		
	}
	public String getTitle() {
		return titleField.getText();
	}
	public String getTextArea() {
		return textArea.getText();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		out.println("RESUME "+titleField.getText()+" "+textArea.getText());
		this.dispose();
	}
}
