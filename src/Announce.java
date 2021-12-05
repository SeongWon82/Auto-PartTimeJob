import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Announce extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField wageField;
	private JTextField areaField;
	private JTextField typeField;

	private JButton submitButton;
	private JButton cancelButton;

	private JComboBox wageBox;
	private JComboBox weekBox;
	private JComboBox periodBox;
	
	private JTextArea textArea;
	private JTextField RecruitNumField;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private RecruiterUI recruit;
	
	private int type;
	private String id;
	
	public Announce(RecruiterUI recruit,BufferedReader in,PrintWriter out) {
		this.in = in;
		this.out = out;
		this.recruit = recruit;
		setTitle("구인공고");
		setIconImage(Toolkit.getDefaultToolkit().getImage(signUp.class.getResource("/icon.PNG")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 466, 601);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("제     목:");
		nameLabel.setBounds(33, 25, 67, 15);
		getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(126, 21, 247, 23);
		contentPane.add(nameField);
		nameField.setColumns(15);
		
		wageBox = new JComboBox();
		wageBox.setBackground(Color.WHITE);
		wageBox.setModel(new DefaultComboBoxModel(new String[] {"시급", "주급", "월급", "연봉"}));
		wageBox.setBounds(131, 65, 52, 23);
		getContentPane().add(wageBox);
		
		JLabel wageLabel = new JLabel("급     여:");
		wageLabel.setBounds(33, 69, 67, 15);
		getContentPane().add(wageLabel);
		
		wageField = new JTextField();
		wageField.setBounds(195, 65, 166, 23);
		contentPane.add(wageField);
		wageField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("원");
		lblNewLabel_2.setBounds(365, 65, 19, 23);
		contentPane.add(lblNewLabel_2);
		
		JLabel areaLabel = new JLabel("지     역:");
		areaLabel.setBounds(33, 120, 67, 15);
		contentPane.add(areaLabel);
		
		areaField = new JTextField();
		areaField.setColumns(10);
		areaField.setBounds(128, 117, 247, 21);
		contentPane.add(areaField);
		
		JLabel typeLabel = new JLabel("직     종:");
		typeLabel.setBounds(33, 170, 67, 15);
		contentPane.add(typeLabel);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(126, 167, 249, 21);
		contentPane.add(typeField);
		
		JLabel weekLabel = new JLabel("근무시간:");
		weekLabel.setBounds(33, 215, 67, 15);
		contentPane.add(weekLabel);
		
		JLabel periodLabel = new JLabel("근무기간:");
		periodLabel.setBounds(212, 215, 82, 15);
		contentPane.add(periodLabel);
		
		weekBox = new JComboBox();
		weekBox.setModel(new DefaultComboBoxModel(new String[] {"월-금", "월-토", "월-일", "월-토(격주)", "주말", "요일협의"}));
		weekBox.setBounds(126, 211, 67, 23);
		contentPane.add(weekBox);
		
		periodBox = new JComboBox();
		periodBox.setModel(new DefaultComboBoxModel(new String[] {"1일", "1주일이내", "1주일-1개월", "1개월-3개월", "3개월-6개월", "6개월-1년", "1년이상"}));
		periodBox.setBounds(278, 211, 95, 23);
		contentPane.add(periodBox);
		
		JLabel addTextLabel = new JLabel("추가설명");
		addTextLabel.setBounds(36, 306, 52, 15);
		contentPane.add(addTextLabel);
		
		textArea = new JTextArea();
		textArea.setBounds(33, 331, 379, 173);
		contentPane.add(textArea);
		
		submitButton = new JButton("제출");
		submitButton.setBounds(289, 514, 95, 23);
		submitButton.addActionListener(this);
		contentPane.add(submitButton);
		
		cancelButton = new JButton("취소");
		cancelButton.addActionListener(this);
		cancelButton.setBounds(182, 514, 95, 23);
		contentPane.add(cancelButton);
		
		JLabel RecruitNumLabel = new JLabel("모집인원:");
		RecruitNumLabel.setBounds(33, 261, 67, 15);
		contentPane.add(RecruitNumLabel);
		
		RecruitNumField = new JTextField();
		RecruitNumField.setColumns(10);
		RecruitNumField.setBounds(126, 257, 77, 23);
		contentPane.add(RecruitNumField);
		
		JLabel lblNewLabel_2_1 = new JLabel("명");
		lblNewLabel_2_1.setBounds(215, 257, 19, 23);
		contentPane.add(lblNewLabel_2_1);
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public void setID(String id)
	{
		this.id= id;
	}
	public void setInfo(String[] info)
	{
		nameField.setText(info[0]);
		wageBox.setSelectedItem(info[1]);
		wageField.setText(info[2]);
		areaField.setText(info[3]);
		typeField.setText(info[4]);
		weekBox.setSelectedItem(info[5]);
		periodBox.setSelectedItem(info[6]);
		RecruitNumField.setText(info[7]);
		textArea.setText(info[8]);
	}
	public String getInfo()
	{
		String[] area = areaField.getText().split(" ");
		String tmp = nameField.getText()+" "+
				     wageBox.getSelectedItem().toString()+","+wageField.getText()+" "+
					 String.join(",",area)+" "+
					 typeField.getText()+" "+
					 weekBox.getSelectedItem().toString()+" "+
					 periodBox.getSelectedItem().toString()+" "+
					 RecruitNumField.getText()+" "+
					 textArea.getText();
		return tmp;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton)arg0.getSource();
		
		if(btn.equals(submitButton))
		{
			if(type==1)
			{
				out.println("REGIST "+getInfo());
				try {
					String[] tmp = in.readLine().split(" ");
					if(tmp[0].equals("SUCSSESS")) {
						Client.showMsg("등록성공","base");
						recruit.addText(new String[] {
								tmp[1],
								nameField.getText(),
								areaField.getText(),
								wageBox.getSelectedItem().toString()+wageField.getText(),
								typeField.getText(),
								weekBox.getSelectedItem().toString()+", "+periodBox.getSelectedItem().toString(),
								RecruitNumField.getText(),
								"0"});
					}
					else
						Client.showMsg("등록실패.","base");
				} catch (IOException e) {
					Client.showMsg("에러발생!","error");
				}
			}
			if(type==2)
			{
				out.println("MODIFY "+getInfo()+" "+id);
				try {
					String[] tmp = in.readLine().split(" ");
					if(tmp[0].equals("SUCSSESS")) {
						Client.showMsg("수정성공","base");
						String[] test = new String[] {
								nameField.getText(),
								areaField.getText(),
								wageBox.getSelectedItem().toString()+" "+wageField.getText(),
								typeField.getText(),
								weekBox.getSelectedItem().toString()+", "+periodBox.getSelectedItem().toString(),
								RecruitNumField.getText(),
								};
						recruit.midifyText(id,test);
					}
					else
						Client.showMsg("수정실패.","base");
				} catch (IOException e) {
					Client.showMsg("에러발생!","error");
				}
			}
		}
		this.dispose();
	}
}
