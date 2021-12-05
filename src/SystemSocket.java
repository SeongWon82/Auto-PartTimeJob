import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SystemSocket extends Thread  {

		// sql 연관 
		private Connection conn=null;
		public Statement stmt=null;
		public PreparedStatement prst=null;
		public String prstString=null;
		private ResultSet rs;
		private ResultSetMetaData rsmd;
		
		// socket 연관
		private Socket socket;
		private int ClientID;
		// data 연관
		private BufferedReader input;
		private PrintWriter output;
		
		//WebDriver 연관
	    private WebDriver driver;
	    private WebElement webElement;
	    private Actions action;
	    private ArrayList<WebElement> jobList;;
	    
	    //Properties 상수
	    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	    public static final String WEB_DRIVER_PATH = "C:\\Users\\SEONGWON\\chromedriver.exe";
	    
	    //크롤링 할 URL
	    private String base_url;
		
	    private String[] area;
	    private String type;
	    private String[] check;
	    private String time;
	    
	    private String userID;
	    private boolean isApp = true;
	    private String massage;
	    
		SystemSocket(Socket socket,int ClientID) throws Exception
		{
			// jdbc 설정
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn =DriverManager.getConnection("jdbc:mysql://localhost/sampledb?serverTimezone=UTC","root","speed7");
			// socket 설정
			this.socket=socket;
			this.ClientID = ClientID;
			System.out.println("#"+ClientID+" 클라이언트 IP : "+this.socket.getInetAddress() +"  연결포트 : "+this.socket.getPort());
		}
		
		@Override
		public void run() 
		{
			String msg=null;
			String[] order =null;
			try 
			{	
				 input=new BufferedReader(new InputStreamReader(socket.getInputStream(),"EUC-KR"));
				 output=new PrintWriter(new OutputStreamWriter( socket.getOutputStream(),"EUC-KR") ,true);
				while(true)
				{
					msg = input.readLine();
					order = msg.split(" ");
					
					if(order[0].equals("SIGNUP"))
					{
						prstString = "insert into member(id, pwd, name, age, phone_num, utype) values(?, ?, ?, ?, ?, ?)";
						prst=conn.prepareStatement(prstString);
						
						prst.setString(1, order[1]);
						prst.setString(2, order[2]);
						prst.setString(3, order[3]);
						prst.setInt(4, Integer.parseInt(order[4]) );
						prst.setString(5, order[5]);
						prst.setInt(6, Integer.parseInt(order[6]) );
						
						if(prst.executeUpdate() > 0)
							output.println("SUCSSESS");
						else
							output.println("FAIL");
					}
					if(order[0].equals("RESUME"))
					{
						prstString = "insert into resume(id, title, text) values(?, ?, ?)";
						prst=conn.prepareStatement(prstString);
						
						prst.setString(1, userID);
						prst.setString(2, order[1]);
						prst.setString(3, order[2]);
						prst.executeUpdate();	
					}
					if(order[0].equals("CONFIRM"))
					{
						prstString = "select id from member where name=?";
						prst=conn.prepareStatement(prstString);
						prst.setString(1, order[1]);
						rs = prst.executeQuery();
						if(rs.next()) {
							SystemServer.user.put(rs.getString(1),true);
							System.out.println(userID+"->"+rs.getString(1)+" 메시지 전송");
							output.println("SUCSSESS");
						}
						else
							output.println("FAIL");
					}
					if(order[0].equals("ALARM"))
					{
						int key;
						String[] send={""};
						ArrayList<String> list = new ArrayList(Arrays.asList(order));
						list.remove(0);
						for ( String s : list ) {
							key=Integer.parseInt(s) ;
					          if( isApp && SystemServer.recruiter.containsKey( key ) ) {
					        	  send = SystemServer.recruiter.get(key).split(" ");
					        	  isApp=false;
					          }
						}
						if(send[0].equals(""))
							output.println("FAIL");
						else
							output.println("SUC "+send[send.length-1]);
					}
					if(order[0].equals("LOGIN")) 
					{
						prstString ="select count(id),name,uType from member where id=? and pwd=?";
						prst=conn.prepareStatement(prstString);
						userID = order[1];
						prst.setString(1,order[1]);
						prst.setString(2,order[2]);
						SystemServer.user.put(userID,false);
						try 
						{
							rs =prst.executeQuery();
							rs.next();
							if( rs.getInt(1)== 1 ) { 
								output.println("SUCSSESS,"+rs.getString(2)+","+rs.getInt(3));
							}
							else
								output.println("FAIL,");
						}
						catch(Exception e) 
						{
							output.println("FAIL");
						}	
					}
					if(order[0].equals("MATT"))
					{
						int cnt = 0;
						while(true)
			        	{
			        		String res =input.readLine();
			        		
			        		if( res.equals("RUN") )
			        		{
			        			prstString ="select job_id,wage,area,jtype,RecruitNum,curNum from job";
								prst=conn.prepareStatement(prstString);
			        			rs =prst.executeQuery();
			        			while(rs.next())
			        			{
			        				int job_id = rs.getInt(1);
			        				String tmp = rs.getString(2).split(",")[0]+" "+rs.getString(2).split(",")[1]+" "
			        							+rs.getString(3)+" "+rs.getString(4);
			        				int mNum = rs.getInt(5);
			        				int curNum = rs.getInt(6);
	
			        				if ( curNum < mNum ) 
			        				{
			        					prstString ="insert into applicant(id,job_id,area) values(?,?,?)";
										prst=conn.prepareStatement(prstString);
										prst.setString(1, userID);
										prst.setInt(2, job_id);
										String t_area = String.join(" ", area);
										prst.setString(3, t_area);
										if( SystemServer.recruiter.containsKey(job_id) )  // 이미 키가 존재하고
										{ 
											String[] s1 = SystemServer.recruiter.get(job_id).split(" ");
											String s2 = SystemServer.recruiter.get(job_id);
											if( ! Arrays.asList(s1).contains(userID)) //  현재 ID가  Job_id에 포함되있지 않다면
											{
												prst.executeUpdate();
												cnt++;
												SystemServer.recruiter.put(job_id, s2+" "+userID);
											}
										}
										else
										{
											prst.executeUpdate();
											cnt++;
											SystemServer.recruiter.put(job_id, userID);	
										}
			        				}
			        			}
			        			if(SystemServer.user.get(userID))
			        			{
			        				prstString ="insert into applicant(id,job_id,area) values(?,?,?)";
									prst=conn.prepareStatement(prstString);
									
									
			        				output.println("FIND");
			        				SystemServer.user.put(userID, false);
			        			}
			        			else {
			        				output.println("RUN "+cnt);
			        			}
			        		}
			        		else if( res.equals("EXIT") )
			        			break;
			        	}
					}
					if(order[0].equals("CRAWL"))
					{
						crawl();
					}
					if(order[0].equals("CONDITION"))
					{
							// 지역 , 직종, 근무기간, 근무시간
							area = input.readLine().split(" ");
							type = input.readLine();
							check = input.readLine().split(" ");
							time = input.readLine();
							output.println("ENABLE");
					}
					if(order[0].equals("REGIST"))
					{
						prstString ="insert into job(id,title,wage,area,jtype,workWeek,workPeriod,RecruitNum,addText) "+
									    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
						prst=conn.prepareStatement(prstString);
						prst.setString(1,userID);
						prst.setString(2,order[1]);
						prst.setString(3,order[2]);
						prst.setString(4,order[3]);
						prst.setString(5,order[4]);
						prst.setString(6,order[5]);
						prst.setString(7,order[6]);
						int tmp = Integer.parseInt(order[7]);
						prst.setInt(8,tmp);
						prst.setString(9,order[8]);
						
						if(prst.executeUpdate() != 0) {
							stmt = conn.createStatement();
							rs=stmt.executeQuery("SELECT LAST_INSERT_ID()");
							rs.next();
							output.println("SUCSSESS "+rs.getInt(1));
						}
						else
							output.println("FAIL");
					}
					if(order[0].equals("SEARCH"))
					{
						prstString = "select title,wage,area,jtype,workWeek,workPeriod,RecruitNum,addText from job where job_id=?";
						prst=conn.prepareStatement(prstString);
						int find_id = Integer.parseInt(order[1]);
						prst.setInt(1,find_id);
						rs = prst.executeQuery();
						if(rs.next()) 
						{
							String tmp = rs.getString(1)+" "+
										       rs.getString(2).split(",")[0]+" "+
										       rs.getString(2).split(",")[1]+" "+
										       rs.getString(3)+" "+
										       rs.getString(4)+" "+
										       rs.getString(5)+" "+
										       rs.getString(6)+" "+
										       rs.getInt(7)+" "+
										       rs.getString(8);
							output.println("SUCSSESS "+tmp);
						}
						else
							output.println("FAIL");
					}
					if(order[0].equals("MODIFY"))
					{
						prstString ="update job set title=?,wage=?,area=?,jtype=?,workWeek=?,workPeriod=?,RecruitNum=?,addText=? where job_id=?";
						prst=conn.prepareStatement(prstString);
						prst.setString(1,order[1]);
						prst.setString(2,order[2]);
						prst.setString(3,order[3]);
						prst.setString(4,order[4]);
						prst.setString(5,order[5]);
						prst.setString(6,order[6]);
						int tmp = Integer.parseInt(order[7]);
						prst.setInt(7,tmp);
						prst.setString(8,order[8]);
						prst.setString(9,order[9]);
				
						if(prst.executeUpdate() != 0) 
							output.println("SUCSSESS");
						else
							output.println("FAIL");
					}
					if(order[0].equals("MORE"))
					{
						String title;
						String item="";
						int job_id = Integer.parseInt(order[1]);
						// 제목 구하기
						prstString="select title from job where job_id=?";
						prst=conn.prepareStatement(prstString);
						prst.setInt(1,job_id);
						rs = prst.executeQuery();
						rs.next();
						title = rs.getString(1);
						// 지원자 정보 구하기
						prstString ="SELECT name,age,area,phone_num FROM member INNER JOIN applicant ON member.id = applicant.id where job_id=?";
						prst=conn.prepareStatement(prstString);
						prst.setInt(1,job_id);
						rs = prst.executeQuery();
						while(rs.next())
						{
							item+=rs.getString(1)+","+rs.getInt(2)+","+rs.getString(3)+","+rs.getString(4)+"&";
						}
						if(item.equals(""))
							output.println("FAIL");
						else
							output.println("SUCSSESS&"+title+"&"+item);
					}
					if(order[0].equals("DELETE"))
					{
						int del_id = Integer.parseInt(order[1]);
						prstString = "DELETE FROM applicant WHERE job_id=?";
						prst=conn.prepareStatement(prstString);
						prst.setInt(1,del_id);
						prst.executeUpdate();
						prstString = "DELETE FROM job WHERE job_id=?";
						prst=conn.prepareStatement(prstString);
						prst.setInt(1,del_id);
						if(prst.executeUpdate() != 0)
							output.println("SUCSSESS");
						else
							output.println("FAIL");
					}
				}
			}
			catch(Exception e1) 
			{
				output.println("FAIL");
			}
			finally 
			{
				try {
					driver.quit();
					socket.close();
				}catch(IOException e2) {
				}
			}
		}
		
		public void setCondition()
		{
			//Driver SetUp
			 System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
	         ChromeOptions options = new ChromeOptions();
	         options.setCapability("ignoreProtectedModeSettings", true);
	         jobList = new ArrayList<WebElement>();
	         driver = new ChromeDriver(options);
	        
	        base_url = "http://www.alba.co.kr";
	        
			driver.get(base_url);
			
			action = new Actions(driver);
			
            // 도 클릭
			webElement = driver.findElement(By.linkText(area[0]));
			action.moveToElement(webElement).click().perform();
            //군, 구 클릭
			if(area.length==3)
			{
				driver.findElement(By.linkText(area[1])).click();
				new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'"+area[2]+"')]") ) ).click();
			}
			if(area.length==4)
			{
				String tmp = area[1]+" "+area[2];
				driver.findElement(By.linkText(tmp)).click();
				new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'"+area[3]+"')]") ) ).click();
			}
            
            //근무기간 클릭		
			for(int i=0;i<check.length;i++) 
				driver.findElement(By.xpath("//*[@id=\"schWorkPeriod\"]/dd/ul/li["+ check[i]+"]/span/label")).click();
            //근무요일 클릭   
			int worktime=Integer.parseInt(time);
			if(  worktime> 4 ) 
				worktime+=2;
            driver.findElement(By.xpath("//*[@id=\"schWorkWeek\"]/dd/ul/li["+ worktime +"]/span/label")).click();
		}
		
	    public void crawl() 
	    {	
	        try 
	        {
	        	WebElement table;
	        	ArrayList<WebElement> tableList=null;
	        	
	        	setCondition();
	        	WebDriverWait wait = new WebDriverWait(driver, 3);
	        	while(true)
	        	{
	        		String res =input.readLine();
	        		if(res.equals("RUN"))
	        		{
	        			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"JobSearch\"]/fieldset/div[4]/div[2]/a[1]"))).click();	        			
	        			table= driver.findElement(By.xpath("//*[@id=\"NormalInfo\"]/table/tbody"));
	    	            
	    				tableList=(ArrayList<WebElement> ) table.findElements(By.tagName("tr"));
	        			output.println("RUN "+tableList.size());
	        		}
	        		else if( res.equals("EXIT") )
	        			break;
	        	}
				sendResult(tableList);  
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            driver.close();
	        }
	    }
	    
	    public void sendResult(ArrayList<WebElement> tableList)
	    {
	    	if(tableList == null) 
	    		return;
	    	String[] element1;
	    	String[] element2;
	    	
	    	String total="";
	    	String joburl="";
	    	for( int i=0; i< tableList.size() ;i++)
            {
            	if( !tableList.get(i).getAttribute("class").equals("summaryView")  ) {
            		jobList.add( tableList.get(i) );
            		/*
            		element1 =   jobList.get( jobList.size()-1 ).getText().split("\n");
            		total+="지   역 : "+element1[0]+";";
            		total+="기업 이름 : "+element1[1]+";";
            		total+="제   목 : "+element1[2]+";";
            		element2 =element1[3].split(" ");
            		total+="근무 시간 : "+element2[element2.length-2]+";";
            		total+="급여 종류 : "+element2[element2.length-1]+";";
            		element2 =element1[4].split(" ");
            		total+="급   여 : "+element2[0]+";";
            		total+="올린 시간 : "+element2[1];
            		*/
            		joburl=	jobList.get(jobList.size()-1).findElement(By.tagName("a")).getAttribute("href");
            		output.println(joburl);
            	}
            }
	    	output.println("FIN");
	    }
}
