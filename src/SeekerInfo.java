import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SeekerInfo extends JFrame implements ActionListener{
	
	private String[] header = {"이름","나이","지역","전화번호"};
	private JTable table;
	
	private JLabel jobID;
	private JLabel title;
	private JLabel NumApplicants;
	JScrollPane jp;
	
	BufferedReader in;
	PrintWriter out;
	int size = 0;
	public SeekerInfo(BufferedReader in,PrintWriter out) {
		
		this.in = in;
		this.out = out;
		
		getContentPane().setBackground(Color.WHITE);
		
		setTitle("구직자정보");
		setIconImage(Toolkit.getDefaultToolkit().getImage(signUp.class.getResource("/icon.PNG")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 473);
		
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		title = new JLabel();
		title.setBounds(25, 29, 320, 30);
		getContentPane().add(title);
		
		jobID = new JLabel();
		jobID.setBounds(25, 69, 320, 30);
		getContentPane().add(jobID);
		
		JButton exitBtn = new JButton("나가기");
		exitBtn.addActionListener(this);
		exitBtn.setBounds(280, 390, 95, 23);
		getContentPane().add(exitBtn);
		
		NumApplicants = new JLabel();
		NumApplicants.setBounds(25, 355, 95, 20);
		getContentPane().add(NumApplicants);

	}
	public void setInfo(String title,String id, String[][] contents)
	{
		this.title.setText("제   목:"+title);
		this.jobID.setText("구인 ID:"+id);
		size = contents.length;
		this.NumApplicants.setText("지원자 수: "+size+"명");
		
		DefaultTableModel model = new DefaultTableModel(contents,header) {
	        @Override public Class<?> getColumnClass(int column) {
	            return column==1?Integer.class:String.class;
	        }
	        @Override public boolean isCellEditable(int row, int column) {
	            return false;
	        }
		};
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		table.getColumnModel().getColumn(0).setPreferredWidth(55);
		table.getColumnModel().getColumn(1).setPreferredWidth(35);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(120);
		table.setBounds(0, 0, 400, 220);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setRowHeight(25);
		
		table.addMouseListener(new MyMouseListener());
		jp= new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jp.setBackground(Color.white);
		jp.setEnabled(false);
		jp.setBounds(25, 115, 360, 220);
		getContentPane().add(jp);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//table.isCellEditable(row, column);
		this.dispose();
	}
	private class MyMouseListener extends MouseAdapter 
	{
		 
	    @Override
	    public void mouseClicked(MouseEvent e) 
	    {
	    	if (e.getClickCount() == 2) // 더블클릭
	    	{
	    		int index = table.getSelectedRow();
	    		Object name = table.getValueAt(index,0);
	    		out.println("CONFIRM "+(String)name);
	    		try {
					if(in.readLine().equals("SUCSSESS")) {
						Client.showMsg((String)name+"님을 구인하였습니다.", "base");
						size--;
						NumApplicants.setText("지원자 수: "+size+"명");
						
					}
				} catch (IOException e1) {
					Client.showMsg("구인실패","error");
				}
	    		
	    	}
	    }
	}
}
