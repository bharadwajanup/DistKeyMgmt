import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class agent implements Runnable
{
	Stack s=new Stack();
	String nodeB="",nodeD="";
	static String self;
	Socket soc;
	
	agent(String node)
	{
		self=node;
		readaddr();
		Thread t=new Thread(this);
		t.start();
	}
	
	void readaddr()
	{
		try
		{
			int line=0;
			
			FileInputStream fin=new FileInputStream("nodeB.txt");
			while((line=fin.read())!=-1)
			nodeB+=(char)line;
			nodeB.trim();
			fin.close();
			System.out.println("Address of node B is "+nodeB);
			
			FileInputStream fin1=new FileInputStream("nodeD.txt");
			while((line=fin1.read())!=-1)
			nodeD+=(char)line;
			nodeD.trim();
			fin1.close();
			System.out.println("Address of node D is "+nodeD);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
			
				String next="";
				if (s.empty())
				{
						int r=(int)Math.random()*2;
						if (r==0)
						next=(String)nodeA.df.getValueAt(0,0);
						else
						next=(String)nodeA.df.getValueAt(1,0);
				}		
				else
				{
					String last=(String)s.peek();
					String node=""+last.charAt(0);
					node.trim();
					if (node.equals("B"))
					next="D";
                                        else 
					next="B";
				}
			
				s.push(next+"-->"+new Date());
				nodeA.list.setListData(s);
				
				
				
				System.out.println("Connecting to "+next);
				if (next.equals("D"))
				soc=new Socket(nodeD,4500);
				else
				soc=new Socket(nodeB,2500);
				
				if (soc!=null)
				{
				
				System.out.println("creating input and output stream..");
				
				
				ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
				System.out.println("output stream created..");
				ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
				System.out.println("input stream created..");
				
								
				System.out.println("creating stack..");
				Stack dict=new Stack();
				Stack cost=new Stack();
				
				dict.push("A");
				cost.push(nodeA.jtmy.getText().trim());
				
				System.out.println("sending data..");
				oos.writeObject("C");
				System.out.println("dest name sent..");
				oos.writeObject(dict);
				System.out.println("dict sent..");
				oos.writeObject(cost);
				System.out.println("dest name sent..");
				
				System.out.println("cost sent to neighbour...");
				
				dict=(Stack)oin.readObject();
				cost=(Stack)oin.readObject();
				
				System.out.println("reply received..");
				
				if (next.equals("B"))
				nodeA.df.setValueAt(cost.pop(),0,1);
				else
				if (next.equals("D"))
				nodeA.df.setValueAt(cost.pop(),1,1);
				
			oin.close();
			oos.close();
			soc.close();
				
				Thread.sleep(5000);
			}
                        
                                    
                                   
                                    
                                    
                                    
                                
			}
			catch(Exception e)
			{
				System.out.println(e);
                                
                                
                          
                                
			}
		}
		
	}
	
}