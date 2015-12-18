import java.util.*;
import java.io.*;
import java.net.*;

class agent implements Runnable
{
	Stack s=new Stack();
	String nodeB="",nodeD="";
   String self;
	
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
						next=(String)nodeC.df.getValueAt(0,0);
						else
						next=(String)nodeC.df.getValueAt(1,0);
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
				nodeC.list.setListData(s);
				
				Socket soc=null;
				
				if (next.equals("D"))
				soc=new Socket(nodeD,4500);
				else
				soc=new Socket(nodeB,2500);
				
				ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
				
				
				Stack dict=new Stack();
				Stack cost=new Stack();
				
				dict.push("C");
				cost.push(nodeC.jtmy.getText().trim());
				oos.writeObject("A");
				oos.writeObject(dict);
				oos.writeObject(cost);
				
				dict=(Stack)oin.readObject();
				cost=(Stack)oin.readObject();
				
				if (next.equals("B"))
				nodeC.df.setValueAt(cost.pop(),0,1);
				else
				if (next.equals("D"))
				nodeC.df.setValueAt(cost.pop(),1,1);
				
			
				
				Thread.sleep(5000);
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
	}
	
}