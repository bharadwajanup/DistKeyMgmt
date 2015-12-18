import java.util.*;
import java.io.*;
import java.net.*;

class agent implements Runnable
{
	Stack s=new Stack();
	String nodeA="",nodeC="";
	static String self;
	
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
			
			FileInputStream fin=new FileInputStream("nodeA.txt");
			while((line=fin.read())!=-1)
			nodeA+=(char)line;
			nodeA.trim();
			fin.close();
			System.out.println("Address of node A is "+nodeA);
			
			FileInputStream fin1=new FileInputStream("nodeC.txt");
			while((line=fin1.read())!=-1)
			nodeC+=(char)line;
			nodeC.trim();
			fin1.close();
			System.out.println("Address of node C is "+nodeC);
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
						next=(String)nodeB.df.getValueAt(0,0);
						else
						next=(String)nodeB.df.getValueAt(1,0);
				}		
				else
				{
					String last=(String)s.peek();
					String node=""+last.charAt(0);
					node.trim();
					if (node.equals("A"))
					next="C";
					else
					next="A";
				}
			
				s.push(next+"-->"+new Date());
				nodeB.list.setListData(s);
				
				Socket soc=null;
				
				if (next.equals("A"))
				soc=new Socket(nodeA,1500);
				else
				soc=new Socket(nodeC,3500);
				
				ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
				
				
				Stack dict=new Stack();
				Stack cost=new Stack();
				
				dict.push("B");
				cost.push(nodeB.jtmy.getText().trim());
				oos.writeObject("D");
				oos.writeObject(dict);
				oos.writeObject(cost);
				
				dict=(Stack)oin.readObject();
				cost=(Stack)oin.readObject();
				
				if (next.equals("A"))
				nodeB.df.setValueAt(cost.pop(),0,1);
				else
				if (next.equals("C"))
				nodeB.df.setValueAt(cost.pop(),1,1);
				
			
				
				Thread.sleep(5000);
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
	}
	
}