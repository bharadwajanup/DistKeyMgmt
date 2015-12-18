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
                        //nodeD.jtprocess.append("Mobile Agent started!\n");
			ServerSocket ss=new ServerSocket(4500);
			while(true)
			{
				Socket s=ss.accept();
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
	String self="";
	String nodeA="",nodeC="";
	
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
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(soc.getOutputStream());
			ObjectInputStream oin=new ObjectInputStream(soc.getInputStream());
			
			
			String dest=(String)oin.readObject();
			Stack dict=(Stack)oin.readObject();
			Stack cost=(Stack)oin.readObject();
			
			if (dest.trim().equals(self))
			{
				dict.push("D");
				cost.push(nodeD.jtmy.getText().trim());
				oos.writeObject(dict);
				oos.writeObject(cost);
			}
			else
			if (dest.equals("C"))
			{
				Socket clisoc=new Socket(nodeC,3500);
				ObjectOutputStream clioos=new ObjectOutputStream(clisoc.getOutputStream());
				ObjectInputStream clioin=new ObjectInputStream(clisoc.getInputStream());
				
				dict.push("D");
				cost.push(nodeD.jtmy.getText().trim());
				clioos.writeObject(dest);
				clioos.writeObject(dict);
				clioos.writeObject(cost);
				
				dict=(Stack)clioin.readObject();
				cost=(Stack)clioin.readObject();
				
				String node=(String)dict.pop();
				String wt=(String)cost.pop();
				
				nodeD.jtable.setValueAt(wt,1,1);
				
				oos.writeObject(dict);
				oos.writeObject(cost);
				
				clioos.close();
				clioin.close();
				clisoc.close();
				
			}
			else
			if (dest.equals("A"))
			{
				Socket clisoc=new Socket(nodeA,1500);
				ObjectOutputStream clioos=new ObjectOutputStream(clisoc.getOutputStream());
				ObjectInputStream clioin=new ObjectInputStream(clisoc.getInputStream());
				
				dict.push("D");
				cost.push(nodeD.jtmy.getText().trim());
				clioos.writeObject(dest);
				clioos.writeObject(dict);
				clioos.writeObject(cost);
				
				dict=(Stack)clioin.readObject();
				cost=(Stack)clioin.readObject();
				
				String node=(String)dict.pop();
				String wt=(String)cost.pop();
				
				nodeD.jtable.setValueAt(wt,0,1);
				
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