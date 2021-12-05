import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class signUp extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField PWD_Field;
	private JTextField ID_Field;
	private JTextField NameField;
	private JTextField AgeField;
	private JTextField PhoneNumField;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton signUpBtn;
	private JButton cancelBtn;
	private JLabel lblAsdsa;
	private JLabel label_1;
	private JLabel typeLabel1; // 구인자
	private JLabel typeLabel2; // 구직자
	private JRadioButton radio1; 
	private JRadioButton radio2;
	private BufferedReader in;
	private PrintWriter out;
	
	public signUp(BufferedReader in,PrintWriter out) {
		
		this.in = in;
		this.out = out;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(signUp.class.getResource("/icon.PNG")));
		setTitle("회원가입");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 416, 513);
		contentPane.add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(Color.WHITE);
		panel_1.setFont(new Font("CookieRun", Font.BOLD, 12));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(38, 21, 337, 193);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		ID_Field = new RoundJTextField(10);
		ID_Field.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	ID_Field.setText("");
            }
        });
		ID_Field.setBounds(35, 36, 254, 29);
		ID_Field.setColumns(10);
		ID_Field.setText("User ID");
		panel_1.add(ID_Field);
		
		PWD_Field = new RoundJTextField(10);
		PWD_Field.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	PWD_Field.setText("");
            }
        });
		PWD_Field.setBounds(35, 90, 254, 29);
		PWD_Field.setColumns(10);
		PWD_Field.setText("User Password");
		panel_1.add(PWD_Field);
		
		lblAsdsa = new JLabel("필수");
		lblAsdsa.setBounds(35, 10, 76, 15);
		panel_1.add(lblAsdsa);
		
		//
		typeLabel1 = new JLabel("구직자");
		typeLabel1.setBounds(50, 140, 40, 30);
		typeLabel2 = new JLabel("모집자");
		typeLabel2.setBounds(190, 140, 40, 30);
		
		radio1 = new JRadioButton();
		radio1.setBounds(100, 145, 18, 20);
		radio1.setBackground(Color.WHITE);
		radio2 = new JRadioButton();
		radio2.setBounds(240, 145, 18, 20);
		radio2.setBackground(Color.WHITE);
		
		ButtonGroup  group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		panel_1.add(typeLabel1);
		panel_1.add(typeLabel2);
		panel_1.add(radio1);
		panel_1.add(radio2);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(38, 224, 337, 216);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		NameField = new RoundJTextField(10);
		NameField.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	NameField.setText("");
            }
        });
		NameField.setBounds(37, 39, 252, 29);
		NameField.setColumns(10);
		NameField.setText("User Name");
		NameField.setBorder(new LineBorder(Color.BLACK, 1, true));
		panel_2.add(NameField);
		
		AgeField = new RoundJTextField(10);
		AgeField.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	AgeField.setText("");
            }
        });
		AgeField.setBounds(37, 91, 252, 29);
		AgeField.setColumns(10);
		AgeField.setText("User Age");
		AgeField.setBorder(new LineBorder(Color.BLACK, 1, true));
		panel_2.add(AgeField);
		
		PhoneNumField = new RoundJTextField(10);
		PhoneNumField.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	PhoneNumField.setText("");
            }
        });
		PhoneNumField.setBounds(37, 148, 252, 29);
		PhoneNumField.setColumns(10);
		PhoneNumField.setText("Phone Number..");
		PhoneNumField.setBorder(new LineBorder(Color.BLACK, 1, true));
		panel_2.add(PhoneNumField);
		
		label_1 = new JLabel("선택");
		label_1.setBounds(38, 10, 76, 15);
		panel_2.add(label_1);
		
		signUpBtn = new RoundButton("sign up");
		signUpBtn.addActionListener(this);
		signUpBtn.setBounds(218, 450, 141, 35);
		signUpBtn.setForeground(new Color(255, 255, 255));
		signUpBtn.setBorder(new LineBorder(SystemColor.inactiveCaption));
		signUpBtn.setBackground(new Color(49, 181, 255));
		panel.add(signUpBtn);
		
		cancelBtn = new RoundButton("cancel");
		cancelBtn.addActionListener(this);
		cancelBtn.setBounds(53, 450, 141, 35);
		panel.add(cancelBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		
		if(btn.equals(signUpBtn))
		{
			int type = radio1.isSelected() ? 1: 2;
			String s = ID_Field.getText()+" "+PWD_Field.getText()+" "+NameField.getText()+" "+
					AgeField.getText()+" "+PhoneNumField.getText()+" "+1;
			out.println("SIGNUP "+s);
			try {
				if( in.readLine().equals("SUCSSESS"))
				{
					Client.showMsg("회원가입성공!", "base");
				}
				else
					Client.showMsg("회원가입실패!", "base");
			} catch (IOException e1) {
				Client.showMsg("에러발생", "error");
			}
			this.dispose();
		}
		else if(btn.equals(cancelBtn))
			this.dispose();
	}

}


