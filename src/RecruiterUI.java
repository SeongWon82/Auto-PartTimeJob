import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class RecruiterUI extends RoundJPanel{
	
	private JTextArea displayArea;
	private JScrollPane scrollPane;

	private ArrayList<String> myJobList = new ArrayList<String>();
	
	private BufferedReader in;
	private PrintWriter out;
	private Client client;
	private responseThread thread;
	
	public RecruiterUI(Client client,BufferedReader in,PrintWriter out) throws IOException{
		
		this.client = client;
		this.in = in;
		this.out = out;
		
		setBackground(Color.WHITE);
		setBounds(127,95, 422, 290);
		setLayout(null);
		
		myJobList  = new ArrayList<String>();
		myJobList.add("1042"); //임시로 넣은값
		
		displayArea = new JTextArea();
		displayArea.setBackground(Color.WHITE);
		displayArea.setBounds(0, 0, 360, 250);
		displayArea.setEditable(false);
		scrollPane = new JScrollPane(displayArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(21, 10, 380, 270);
		add(scrollPane);
		
		displayArea.append("구인목록\n");
		displayArea.append("\n");
		
		thread = new RecruiterUI.responseThread();
	}
	public void startThread()
	{
		thread.start();
	}
	public void addText(String[] info) // 등록
	{	// 제목 , 모집인원, 지원자 수
		myJobList.add(info[0]);
		displayArea.append("구인 ID: "+info[0]+"\n");
		displayArea.append("제   목: "+info[1]+"\n");
		displayArea.append("지   역: "+info[2]+"\n");
		displayArea.append("급   여: "+info[3]+"\n");
		displayArea.append("종   류: "+info[4]+"\n");
		displayArea.append("근무기간: "+info[5]+"\n");
		displayArea.append("모집인원: "+info[6]+"\n");
		displayArea.append("지원자수: "+info[7]+"\n");
	}
	public void midifyText(String id,String[] info)
	{
		int LineNum = displayArea.getLineCount();
		int st,ed,len;
		int offset = 0;
		String[] tmp = displayArea.getText().split("\n");
		ArrayList<String> text = new ArrayList<String>(Arrays.asList(tmp));
		
		for(int i=0;i<LineNum;i++)
		{
			try {
				st = displayArea.getLineStartOffset(i);
				ed = displayArea.getLineEndOffset(i);
				len = ed - st -1;
				if( displayArea.getText(st, len).startsWith("구인 ID: "+id) )
				{
					offset=i+1;
				}
			} catch (BadLocationException e) {
			}
		}
		String s="";
		for(int i=offset;i<offset+6;i++)
		{
			if(i==offset)
				s = "제   목: "+info[0];
			else if(i == offset+1)
				s = "지   역: "+info[1];
			else if(i == offset+2)
				s = "급   여: "+info[2];
			else if(i == offset+3)
				s = "종   류: "+info[3];
			else if(i == offset+4)
				s = "근무기간: "+info[4];
			else if(i == offset+5)
				s = "모집인원: "+info[5];
			text.set(i,s);
		}
		tmp = text.toArray(new String[text.size()]);
		s = String.join("\n", tmp);
		displayArea.setText(s);
	}
	public void removeText(String id)
	{
		int LineNum = displayArea.getLineCount();
		int st,ed,len;
		int offset = 0;
		String[] tmp = displayArea.getText().split("\n");
		ArrayList<String> text = new ArrayList<String>(Arrays.asList(tmp));
		
		for(int i=0;i<LineNum;i++)
		{
			try {
				st = displayArea.getLineStartOffset(i);
				ed = displayArea.getLineEndOffset(i);
				len = ed - st -1;
				if( displayArea.getText(st, len).startsWith("구인 ID: "+id) )
				{
					offset=i;
				}
			} catch (BadLocationException e) {
			}
		}
		Iterator<String> it = text.iterator();
		for(int i=0;it.hasNext();i++)
		{
			String s =it.next();
			if(i>=offset && i<offset+8)
				it.remove();
			
		}
		tmp = text.toArray(new String[text.size()]);
		String s = String.join("\n", tmp);
		displayArea.setText(s);
	}
	class responseThread extends Thread 
	{ 
		ArrayList<String> list;
		String msg;
		String[] recv;
		@Override 
		public void run() 
		{
			try 
			{
				while(true) 
				{
					if(myJobList.size() > 0)
					{
						msg =String.join(" ", myJobList.toArray(new String[myJobList.size()]));
						out.println("ALARM "+msg);
						recv = in.readLine().split(" ");
						if(recv[0].equals("SUC"))
						{
							for(String s : recv)
							{
								if(!s.equals("SUC"))
									Client.showMsg("구직자 ID:"+s+" 지원", "base");
								
							}
						}
					}
					Thread.sleep(1000);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
