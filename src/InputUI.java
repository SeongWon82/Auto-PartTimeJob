import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class InputUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private RecruiterUI recruit;
	private int type; // 실행종류 1: 수정 2: 삭제 3 : 상세보기
	private BufferedReader in;
	private PrintWriter out;
	private Announce announce;
	private SeekerInfo info;
	private JLabel inputLabel;
	
	public InputUI(RecruiterUI recruit,int type,BufferedReader in,PrintWriter out) {
		this.recruit =recruit;
		this.type = type;
		this.in = in;
		this.out = out;

		setIconImage(Toolkit.getDefaultToolkit().getImage(signUp.class.getResource("/icon.PNG")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 300, 250, 150);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 49, 218, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		inputLabel = new JLabel();
		inputLabel.setBounds(12, 23, 218, 15);
		contentPane.add(inputLabel);
		
		JButton btnNewButton = new JButton("제출");
		btnNewButton.setBounds(153, 85, 71, 23);
		btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);
		
		switch(type)
		{
		case 1:
			setTitle("수정");
			inputLabel.setText("수정할 구인 ID를 입력하세요.");
			break;
		case 2:
			setTitle("삭제");
			inputLabel.setText("삭제할 구인 ID를 입력하세요.");
			break;
		case 3:
			setTitle("상세보기");
			inputLabel.setText("구인 ID를 입력하세요.");
			break;
		}
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(type==1){
			out.println("SEARCH "+textField.getText());
			try {
				String[] tmp = in.readLine().split(" ");
				if(tmp[0].equals("SUCSSESS")) {
					String items="";
					for(int i =1; i<tmp.length;i++)
					{
						items+=tmp[i]+" ";
					}
					String[] info= items.split(" ");
					info[3] =String.join(" ", info[3].split(","));
					announce= new Announce(recruit,in,out);
					announce.setInfo(info);
					announce.setType(2);
					announce.setID(textField.getText());
					announce.setVisible(true);
				}
				else 
					Client.showMsg("수정실패.","base");
			} catch (IOException e) {
				Client.showMsg("에러발생!","error");
			}
		}
		if(type==2){
			out.println("DELETE "+textField.getText());
			try {
				String tmp = in.readLine();
				if(tmp.equals("SUCSSESS")) {
					Client.showMsg("삭제성공","base");
					recruit.removeText(textField.getText());
				}
				else
					Client.showMsg("삭제실패.","base");
			} catch (IOException e) {
				Client.showMsg("에러발생!","error");
			}
		}
		if(type==3){
			out.println("MORE "+textField.getText());
			try {
				String[] tmp = in.readLine().split("&");
				if(tmp[0].equals("SUCSSESS")) {
					Client.showMsg("찾기성공","base");
					String[][] item=new String[tmp.length-2][];
					for(int i=2;i<tmp.length;i++) {
						item[i-2]=tmp[i].split(",");
					}
					info = new SeekerInfo(in,out);
					info.setInfo(tmp[1], textField.getText(), item);
					info.setVisible(true);
				}
				else
					Client.showMsg("찾기실패.","base");
			} catch (IOException e) {
				Client.showMsg("에러발생!","error");
			}
		}
		this.dispose();
	}
}
