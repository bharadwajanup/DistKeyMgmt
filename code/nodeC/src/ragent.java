import java.net.*;
import java.io.*;
import java.util.*;

class ragent implements Runnable
{
	String self="";
	
	ragent(String node)
	{
		self=node;
		Thread t=new Thread(this);
		t.start();
	}
	

	
	
	
	public void run()
	{
		try
		{
			System.out.println("ragent started...");
                        //nodeC.jtprocess.append("Mobile Agent started!\n");
			ServerSocket ss=new ServerSocket(3500);
			while(true)
			{
				Socket s=ss.accept();
				System.out.println("connected..");
				new readmsg(s,self);
			}
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
}

class readmsg implements Runnable
{
	Socket soc;
		String nodeB="",nodeD="";
	String self="";
	
	readmsg(Socket s,String node)
	{
		soc=s;
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
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
			ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
			
			
			String dest=(String)oin.readObject();
			Stack dict=(Stack)oin.readObject();
			Stack cost=(Stack)oin.readObject();
			
			System.out.println("request received..");
			
			if (dest.trim().equals(self))
			{
				dict.push("C");
				cost.push(nodeC.jtmy.getText().trim());
				oos.writeObject(dict);
				oos.writeObject(cost);
				System.out.println("reply sent");
			}
			else
			if (dest.equals("D"))
			{
				Socket clisoc=new Socket(nodeD,4500);
				ObjectOutputStream clioos=new ObjectOutputStream(clisoc.getOutputStream());
				ObjectInputStream clioin=new ObjectInputStream(clisoc.getInputStream());
				
				dict.push("C");
				cost.push(nodeC.jtmy.getText().trim());
				clioos.writeObject(dest);
				clioos.writeObject(dict);
				clioos.writeObject(cost);
				
				dict=(Stack)clioin.readObject();
				cost=(Stack)clioin.readObject();
				
				String node=(String)dict.pop();
				String wt=(String)cost.pop();
				
				nodeC.jtable.setValueAt(wt,1,1);
				
				oos.writeObject(dict);
				oos.writeObject(cost);
				
				clioos.close();
				clioin.close();
				clisoc.close();
				
			}
			else
			if (dest.equals("B"))
			{
				Socket clisoc=new Socket(nodeB,2500);
				ObjectOutputStream clioos=new ObjectOutputStream(clisoc.getOutputStream());
				ObjectInputStream clioin=new ObjectInputStream(clisoc.getInputStream());
				
				dict.push("C");
				cost.push(nodeC.jtmy.getText().trim());
				clioos.writeObject(dest);
				clioos.writeObject(dict);
				clioos.writeObject(cost);
				
				dict=(Stack)clioin.readObject();
				cost=(Stack)clioin.readObject();
				
				String node=(String)dict.pop();
				String wt=(String)cost.pop();
				
				nodeC.jtable.setValueAt(wt,0,1);
				
				oos.writeObject(dict);
				oos.writeObject(cost);
				
				clioos.close();
				clioin.close();
				clisoc.close();
				
				
			}
			
			oin.close();
			oos.close();
			soc.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}