import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class SeekerUI extends RoundJPanel implements ActionListener{
	
	private JPanel AreaPanel;
	private JPanel conditionPanel;
	private JPanel searchJobPanel;
	private JPanel resultPanel;
	private JScrollPane scrollPane;
	private JButton editButton;
	private JButton removeButton;
	
	// conditionPanel Component
	private JTextField areaField;
	private JTextField typeField;
	private JCheckBox[] checkbox = new JCheckBox[7];
	private JComboBox comboBox;
	private JButton applyButton;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private Client client;
	private JButton seekButton;
	
	private JLabel conditionMsgLabel;
	private JLabel iconLabel;
	private responseThread thread;
	
	private JComboBox wageBox;
	private JTextField wageField;
	
	private String[] webUrl=null;
	private Resume r=null;
	
	private JRadioButton radio1;
	private JRadioButton radio2;
	
	private int type = 0;
	
	public SeekerUI(Client client,BufferedReader in,PrintWriter out) throws IOException{
		setBackground(Color.WHITE);
		setBounds(127, 95, 422, 290);
		setLayout(null);
		
		this.client=client;
		this.in = in;
		this.out = out;
		
		conditionPanel = new JPanel();
		conditionPanel.setLocation(21, 20);
		conditionPanel.setSize(380, 250);
		conditionPanel.setBackground(Color.WHITE);
		conditionPanel.setLayout(null);
		conditionPanel.setVisible(false);
		
		searchJobPanel = new JPanel();
		searchJobPanel.setLocation(21, 20);
		searchJobPanel.setSize(380, 250);
		searchJobPanel.setVisible(false);
		searchJobPanel.setLayout(null);
		
		add(searchJobPanel);
		
		add(conditionPanel);
		
		resultPanel = new JPanel();
		resultPanel.setLocation(21, 20);
		resultPanel.setSize(380, 250);
		resultPanel.setBackground(Color.WHITE);
		resultPanel.setVisible(false);
		resultPanel.setLayout(new GridLayout(0,2));
		add(resultPanel);
		
		JLabel areaLabel = new JLabel("*지    역");
		areaLabel.setBounds(12, 12, 50, 23);
		conditionPanel.add(areaLabel);
		
		JLabel typeLabel = new JLabel(" 직    종");
		typeLabel.setBounds(12, 50, 50, 23);
		conditionPanel.add(typeLabel);
		
		wageBox = new JComboBox();
		wageBox.setModel(new DefaultComboBoxModel(new String[] {"시급", "주급", "월급", "연봉"}));
		wageBox.setBackground(Color.WHITE);
		wageBox.setBounds(212, 48, 50, 21);
		conditionPanel.add(wageBox);
		
		JLabel dateLabel = new JLabel("*근무기간");
		dateLabel.setBounds(12, 106, 76, 23);
		conditionPanel.add(dateLabel);
		
		JLabel partLabel = new JLabel("*근무시간");
		partLabel.setBounds(12, 217, 76, 23);
		conditionPanel.add(partLabel);
		
		areaField = new JTextField();
		areaField.setBounds(100, 10, 231, 23);
		conditionPanel.add(areaField);
		areaField.setColumns(10);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(100, 48, 105, 23);
		conditionPanel.add(typeField);
		
		wageField = new JTextField();
		wageField.setColumns(10);
		wageField.setBounds(270, 48, 105, 23);
		conditionPanel.add(wageField);
		
		radio1 = new JRadioButton("웹 구직");
		radio1.setBounds(100, 75, 73, 20);
		radio1.setBackground(Color.WHITE);
		radio1.addActionListener(this);
		radio2 = new JRadioButton("일반구직");
		radio2.setBounds(224, 75, 73, 20);
		radio2.addActionListener(this);
		radio2.setBackground(Color.WHITE);
		
		ButtonGroup  group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		
		conditionPanel.add(radio1);
		conditionPanel.add(radio2);
		
		//conditionPanel Component insert
		
		checkbox[0] = new JCheckBox("1일");
		checkbox[0].setBackground(Color.WHITE);
		checkbox[0].setBounds(100, 106, 95, 23);
		conditionPanel.add(checkbox[0]);
		
		checkbox[1] = new JCheckBox("1주일이내");
		checkbox[1].setBackground(Color.WHITE);
		checkbox[1].setBounds(224, 106, 95, 23);
		conditionPanel.add(checkbox[1]);
		
		checkbox[2] = new JCheckBox("1주일-1개월");
		checkbox[2].setBackground(Color.WHITE);
		checkbox[2].setBounds(100, 131, 95, 23);
		conditionPanel.add(checkbox[2]);
		
		checkbox[3] = new JCheckBox("1개월-3개월");
		checkbox[3].setBackground(Color.WHITE);
		checkbox[3].setBounds(224, 131, 95, 23);
		conditionPanel.add(checkbox[3]);
		
		checkbox[4] = new JCheckBox("3개월-6개월");
		checkbox[4].setBackground(Color.WHITE);
		checkbox[4].setBounds(100, 156, 95, 23);
		conditionPanel.add(checkbox[4]);
		
		checkbox[5] = new JCheckBox("6개월-1년");
		checkbox[5].setBackground(Color.WHITE);
		checkbox[5].setBounds(224, 156, 95, 23);
		conditionPanel.add(checkbox[5]);
		
		checkbox[6] = new JCheckBox("1년이상");
		checkbox[6].setBackground(Color.WHITE);
		checkbox[6].setBounds(100, 180, 95, 23);
		conditionPanel.add(checkbox[6]);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"월~금", "월~토", "월~일", "월~토(격주)", "주말", "요일협의"}));
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(101, 217, 107, 23);
		conditionPanel.add(comboBox);
		
		JLabel msgLabel = new JLabel("* 는 필수입력");
		msgLabel.setForeground(Color.RED);
		msgLabel.setBounds(224, 185, 101, 19);
		conditionPanel.add(msgLabel);
		
		applyButton = new RoundButton("적용");
		applyButton.setForeground(new Color(255, 255, 255));
		applyButton.addActionListener(this);
		applyButton.setBorder(new LineBorder(SystemColor.inactiveCaption));
		applyButton.setBackground(Color.GRAY);
		applyButton.setBounds(292,217,76,23);
		conditionPanel.add(applyButton);
		
		
		//searchJobPanel component

		conditionMsgLabel = new JLabel("버튼을 누르세요..");
		conditionMsgLabel.setBounds(41, 220, 215, 25);
		searchJobPanel.add(conditionMsgLabel);
		
		URL url = this.getClass().getResource("/loadImg.gif");
		Icon icon = new ImageIcon(url);
		iconLabel = new JLabel();
		iconLabel.setBounds(41, 10, 300, 200);
		iconLabel.setIcon(icon);
		iconLabel.setVisible(false);
		searchJobPanel.add(iconLabel);
		
		seekButton = new JButton("시작");
		seekButton.addActionListener(this);
		seekButton.setBounds(258, 221, 83, 23);
		searchJobPanel.add(seekButton);
		
		//resultPanel component
		AreaPanel = new JPanel();
		AreaPanel.setLayout(new GridLayout(0,1));
		AreaPanel.setBackground(Color.WHITE);
		scrollPane = new JScrollPane(AreaPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(200,180));
		scrollPane.setBorder(new LineBorder(Color.BLACK));
		resultPanel.add(scrollPane);
		
		JPanel btnPan=new JPanel();
		btnPan.setLayout(null);
		btnPan.setPreferredSize(new Dimension(200,180));
		editButton = new RoundButton("수정");
		editButton.setForeground(new Color(255, 255, 255));
		editButton.addActionListener(this);
		editButton.setBorder(new LineBorder(SystemColor.inactiveCaption));
		editButton.setBackground(Color.GRAY);
		editButton.setBounds(50, 51, 95, 22);
		btnPan.add(editButton);
		//resultPanel.add(editButton);
		
		removeButton = new RoundButton("삭제");
		removeButton.setForeground(new Color(255, 255, 255));
		removeButton.addActionListener(this);
		removeButton.setBorder(new LineBorder(SystemColor.inactiveCaption));
		removeButton.setBackground(Color.GRAY);
		removeButton.setBounds(50, 100, 95, 22);
		//resultPanel.add(removeButton);
		btnPan.add(removeButton);
		resultPanel.add(btnPan);
	}
	
	public void showPanel(String name)
	{
		if(name.equals("condition")) {
			searchJobPanel.setVisible(false);
			resultPanel.setVisible(false);
			conditionPanel.setVisible(true);
		}
		else if(name.equals("search")) {
			conditionPanel.setVisible(false);
			resultPanel.setVisible(false);
			searchJobPanel.setVisible(true);
		}
		else if(name.equals("result")){
			conditionPanel.setVisible(false);
			searchJobPanel.setVisible(false);
			resultPanel.setVisible(true);
		}
		else{
			conditionPanel.setVisible(false);
			searchJobPanel.setVisible(false);
			resultPanel.setVisible(false);
		}
	}
	class responseThread extends Thread 
	{ 
		@Override 
		public void run() 
		{
			try 
			{
				if(type==1)
				{
					while(true) 
					{
						out.println("RUN");
						String res=in.readLine();
					
						if(res.contains(" "))
							conditionMsgLabel.setText("게시물을 찾았습니다!.");
						if(res.startsWith("RUN"))
							Thread.sleep(3000);
					}
				}
				else if(type==2)
				{
					while(true) 
					{
						out.println("RUN");
						String res=in.readLine();
					
						if(res.contains(" "))
							conditionMsgLabel.setText("찾은 개수: "+res.split(" ")[1]);
						if(res.startsWith("RUN"))
							Thread.sleep(3000);
						if(res.startsWith("FIND"))
						{
							//결과를 가져와서 결과판넬에 쓰는 코드 
							out.println("EXIT");
							Client.showMsg("구인되었습니다.", "base");
							iconLabel.setVisible(false);
							break;
						}
					}
				}
			}
			catch (Exception e) {
			} 
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		 Object obj = e.getSource();
		
		if(obj instanceof JRadioButton) 
		{
			if(obj.equals(radio1))
				type =1;
			else if(obj.equals(radio2))
				type =2;
		}
		
		else if(obj instanceof JButton) 
		{ 
			if(obj.equals(editButton))
				return;
			else if(obj.equals(removeButton))
				return;
			else if(obj.equals(applyButton))
			{
				//if(type==2) {
				//	r= new Resume(in,out);
				//	r.setVisible(true);
				//}
				out.println("CONDITION ");
				out.println(areaField.getText());
				out.println(typeField.getText());
				String tmp ="";
				for(int i=0;i<7;i++)
				{
					if(checkbox[i].isSelected())
						tmp+=(i+1)+" ";
			
				}
				out.println(tmp);
				out.println(comboBox.getSelectedIndex()+1);
				try 
				{
					if(in.readLine().equals("ENABLE"))
						client.enabledSearchBtn();
				}catch(Exception e1) {
				}
			}
			else if(obj.equals(seekButton))
			{
				String text = seekButton.getText();
				
				if(type==1) // 웹 구직 방식
				{
					if(text.equals("시작"))
					{
						out.println("CRAWL");
						thread = new responseThread();
						thread.start();
						client.setEnabledBtn(false); // 버튼 비활성화
						conditionMsgLabel.setText("찾는 중입니다..");
						iconLabel.setVisible(true);
						seekButton.setText("중지");
					}
					else if(text.equals("중지"))
						Crawling();
				}
				else if(type==2) //시스템 구직 방식
				{
					if(text.equals("시작"))
					{
						out.println("MATT");
						thread = new responseThread();
						thread.start();
						client.setEnabledBtn(false); // 버튼 비활성화
						conditionMsgLabel.setText("찾는 중입니다..");
						iconLabel.setVisible(true);
						seekButton.setText("중지");
					}
					else if(text.equals("중지"))
					{
						matching();
					}
				}
			}
		}
	}

	private void Crawling() 
	{
		String element;
		thread.interrupt();
		out.println("EXIT");
		int cnt=1;
		try 
		{
			while(!((element=in.readLine()).equals("FIN")) ) 
			{
				JLabel item = new JLabel();
				item.setText(String.format("<html> %d : <a href=\"\">%s</a></html>",cnt++,element));
				item.setCursor(new Cursor(Cursor.HAND_CURSOR));
				item.addMouseListener(new MouseAdapter() {
		           @Override
		           public void mouseClicked(MouseEvent e) {
		        	   JLabel tp = (JLabel)e.getSource();
		        	   int begin = tp.getText().indexOf("http");
		        	   int last = tp.getText().indexOf("LOCAL");
		               try {
		                   try {
		                       Desktop.getDesktop().browse(new URI(tp.getText().substring(begin, last+5)));
		                   } catch (IOException ex) {
		                   }
		               }
		               catch (URISyntaxException ex) {
		               }
		            }
				});
				AreaPanel.add(item);
			}
		}catch(Exception e1) {
		}

		client.setEnabledBtn(true); // 버튼 활성화
		conditionMsgLabel.setText("버튼을 누르세요..");
		iconLabel.setVisible(false);
		seekButton.setText("시작");
	}
	private void matching()
	{
		String element;
		thread.interrupt();
		out.println("EXIT");
		
		client.setEnabledBtn(true); // 버튼 활성화
		conditionMsgLabel.setText("버튼을 누르세요..");
		iconLabel.setVisible(false);
		seekButton.setText("시작");
	}
}
