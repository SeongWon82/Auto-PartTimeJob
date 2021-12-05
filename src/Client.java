import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JButton;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import java.awt.CardLayout;

public class Client extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanelWithBackground loginPanel = null;
	private JPanelWithBackground titlePanel = null;
	private JPanelWithBackground titlePanel_2 = null;
	private JTextField IDField;
	private JPasswordField PWDField;
	private JPanel menuPanel;
	private JLabel msgLabel;
	private JButton loginButton;
	private JButton signupButton;
	
	// 구직자 버튼 
	private JButton[]  seekerBtn;
	private String[] seekerlist = { "조건입력", "찾기","공고보기","결과"};
	// 모집자 버튼
	private JButton[]  RecruiterBtn;
	private String[] Recruitlist = { "등록", "수정","상세보기","삭제"};
	private Announce announce;
	private InputUI input;
	
	private CardLayout card=new CardLayout();
	
	private SeekerUI user1;
	private RecruiterUI user2;
	private int type=0; //사용자 종류
	private signUp frame;
	
	private Socket socket;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public static void main(String[] args) throws IOException{
			Client frame = new Client();
			frame.setVisible(true);
	}

	public Client() throws IOException{
		
		socket=new Socket("localhost",9100);
		 in=new BufferedReader(new InputStreamReader(socket.getInputStream(),"EUC-KR"));
		 out=new PrintWriter(new OutputStreamWriter( socket.getOutputStream(),"EUC-KR") ,true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Client.class.getResource("/icon.PNG")));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 490);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(card);
		
		loginPanel = new JPanelWithBackground("src//backImg.png");
		loginPanel.setBounds(0, 0, 710, 450);
		contentPane.add("loginPanel",loginPanel);
		loginPanel.setLayout(null);

		titlePanel = new JPanelWithBackground("src//titleImg.png");
		titlePanel.setBounds(15, 15,254, 70);
		loginPanel.add(titlePanel);
		titlePanel.setLayout(null);
		
		JPanel textPanel = new JPanel();
		textPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Login Menu", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, new Color(64, 64, 64)));
		textPanel.setBackground(SystemColor.window);
		textPanel.setBounds(127, 95, 422, 289);
		loginPanel.add(textPanel);
		textPanel.setLayout(null);
		
		IDField = new JTextField("User ID");
		IDField.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	IDField.setText("");
            }
        });
		IDField.setDisabledTextColor(SystemColor.menuText);
		IDField.setBorder(new LineBorder(new Color(171, 173, 179)));
		IDField.setToolTipText("아이디입력");
		IDField.setHorizontalAlignment(SwingConstants.LEFT);
		IDField.setBounds(51, 71, 288, 38);
		IDField.setColumns(10);
		IDField.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		textPanel.add(IDField);
		
		PWDField = new JPasswordField("Password");
		PWDField.addMouseListener((MouseListener) new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	PWDField.setText("");
            }
        });
		PWDField.setDisabledTextColor(SystemColor.menuText);
		PWDField.setBorder(new LineBorder(new Color(171, 173, 179)));
		PWDField.setToolTipText("비밀번호입력");
		PWDField.setHorizontalAlignment(SwingConstants.LEFT);
		PWDField.setColumns(10);
		PWDField.setEchoChar('*');
		PWDField.setBounds(51, 157, 288, 38);
		PWDField.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		textPanel.add(PWDField);
		
		loginButton = new RoundButton("login");
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.addActionListener(this);
		loginButton.setBorder(new LineBorder(SystemColor.inactiveCaption));
		loginButton.setBackground(new Color(49,181,255));
		loginButton.setBounds(258, 228, 81, 38);
		textPanel.add(loginButton);
		
		msgLabel = new JLabel("");
		msgLabel.setBackground(new Color(255, 255, 255));
		msgLabel.setForeground(new Color(255, 0, 0));
		msgLabel.setVisible(false);
		msgLabel.setBounds(55, 232, 176, 31);
		textPanel.add(msgLabel);
		
		signupButton = new RoundButton("회원가입");
		signupButton.setBorder(new LineBorder(SystemColor.activeCaption));
		signupButton.setBounds(584, 308, 95, 76);
		signupButton.addActionListener(this);
		loginPanel.add(signupButton);
		
		menuPanel = new JPanelWithBackground("src//backImg.png");
		menuPanel.setBounds(0, 0, 710, 450);
		menuPanel.setBackground(Color.WHITE);
		menuPanel.setVisible(false);
		menuPanel.setLayout(null);
		contentPane.add("menuPanel",menuPanel);

		titlePanel_2 = new JPanelWithBackground("src//titleImg.png");
		titlePanel_2.setBounds(15, 15,254, 70);
		titlePanel_2.setLayout(null);
		menuPanel.add(titlePanel_2);
		
	}
	public void setUseBtn(int type)
	{
		if(type==1) { // 구직자
			seekerBtn = new RoundButton[4];
			for(int i=0;i<4;i++)
			{
				seekerBtn[i] = new RoundButton(seekerlist[i]);
				if(i==1)
				{
					seekerBtn[i].setBackground(Color.GRAY);
					seekerBtn[i].setEnabled(false);
				}
				else
					seekerBtn[i].setBackground(new Color(49,181,255));
				seekerBtn[i].setForeground(new Color(255, 255, 255));
				seekerBtn[i].addActionListener(this);
				seekerBtn[i].setBorder(new LineBorder(SystemColor.inactiveCaption));
				menuPanel.add(seekerBtn[i]);
			}
			seekerBtn[0].setBounds(45+ 39, 400, 81, 38);
			seekerBtn[1].setBounds(205+39, 400, 81, 38);
			seekerBtn[2].setBounds(365 +39, 400, 81, 38);
			seekerBtn[3].setBounds(525 +39, 400, 81, 38);
		}
		else {
			RecruiterBtn = new RoundButton[4];
			for(int i=0;i<4;i++)
			{
				RecruiterBtn[i] = new RoundButton(Recruitlist[i]);
				RecruiterBtn[i].setForeground(new Color(255, 255, 255));
				RecruiterBtn[i].addActionListener(this);
				RecruiterBtn[i].setBorder(new LineBorder(SystemColor.inactiveCaption));
				RecruiterBtn[i].setBackground(new Color(49,181,255));
				menuPanel.add(RecruiterBtn[i]);
			}
			RecruiterBtn[0].setBounds(45+ 39, 400, 81, 38);
			RecruiterBtn[1].setBounds(205+39, 400, 81, 38);
			RecruiterBtn[2].setBounds(365 +39, 400, 81, 38);
			RecruiterBtn[3].setBounds(525 +39, 400, 81, 38);
		}
	}
	public void enabledSearchBtn()
	{
		seekerBtn[1].setEnabled(true);
		seekerBtn[1].setBackground(new Color(49,181,255));
	}
	public void setEnabledBtn(boolean active)
	{
		seekerBtn[0].setEnabled(active);
		seekerBtn[1].setEnabled(active);
		seekerBtn[2].setEnabled(active);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		String[] tmp=null;
		if(btn.equals(loginButton))
		{
			login(btn,tmp);
			if(type==2) 
				user2.startThread();
		}
		else if(btn.equals(signupButton))
		{
			frame = new signUp(in,out);
			frame.setVisible(true);
		}
		if(type==1) 
		{
			if(btn.getText().equals(seekerlist[0]))
				user1.showPanel("condition");
			else if(btn.getText().equals(seekerlist[1]))
				user1.showPanel("search");
			else if(btn.getText().equals(seekerlist[2])) {}
			else if(btn.getText().equals(seekerlist[3]))
				user1.showPanel("result");
		}
		if(type==2) 
		{
			if(btn.getText().equals(Recruitlist[0])) {
				announce = new Announce(user2,in,out);
				announce.setType(1);
				announce.setVisible(true);
			}
			else if(btn.getText().equals(Recruitlist[1])) {
				input = new InputUI(user2,1,in,out);
				input.setVisible(true);
			}
			else if(btn.getText().equals(Recruitlist[2])) {
				input = new InputUI(user2,3,in,out);
				input.setVisible(true);
			}
			else if(btn.getText().equals(Recruitlist[3])) {
				input = new InputUI(user2,2,in,out);
				input.setVisible(true);
			}
		}
	}
	static void showMsg(String Message,String type) {
		if(type.equals("base"))
			JOptionPane.showMessageDialog(null, Message);
		else if(type.equals("error"))
			JOptionPane.showMessageDialog(null,Message,"Error",JOptionPane.ERROR_MESSAGE);
	}
	private void login(JButton btn,String[] tmp)
	{
		String id = IDField.getText();
		String pwd = new String(PWDField.getPassword());
		
		out.println("LOGIN "+id+" "+pwd);
		
		try {
			tmp = in.readLine().split(",");
			if(tmp[0].equals("SUCSSESS")) {
				showMsg( tmp[1]+"님 환영합니다","base");
				type = Integer.parseInt(tmp[2]);
				setUseBtn(type);
				if( type== 1 )
				{ 
					user1=new SeekerUI(this,in,out);
					menuPanel.add(user1);
				}
				else if( type == 2)
				{
					user2=new RecruiterUI(this,in,out);
					menuPanel.add(user2);
				}
				card.show(contentPane, "menuPanel");
			}
			else if(tmp[0].equals("FAIL")) {
				msgLabel.setVisible(true);
				showMsg("로그인에 실패하였습니다.","error");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

