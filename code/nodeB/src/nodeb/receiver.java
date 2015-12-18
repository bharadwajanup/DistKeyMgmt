import java.io.*;
import java.net.*;
import javax.swing.*;

class receiver implements Runnable
{
	String nodeA="",nodeC="";
	nodeB obj;
	int qsrc=0;
	
	receiver(nodeB obj)
	{
		this.obj=obj;
		Thread t=new Thread(this);
		t.start();
		readaddr();
	}
	
	public void run()
	{
		try
		{
			ServerSocket ss=new ServerSocket(2600);
			while(true)
			{
				Socket soc=ss.accept();
				DataInputStream din=new DataInputStream(soc.getInputStream());
				String route=din.readUTF();
				String dest=din.readUTF();
				String msg=din.readUTF();
				
				route+="-B";
				
				if (dest.equals("nodeB"))
				{
					nodeB.jtprocess.append("\nRoute: "+route+"\n");
					nodeB.jtprocess.append("Message: \n"+msg);
					decrypt(route,msg);
					
				}
				else
				if (dest.equals("nodeC"))
				{
					Socket s=new Socket(nodeC,3600);
					DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                        
                                        nodeB.jtprocess.append("Message: \n"+msg+"\n");
                                        nodeB.jtprocess.append("Destination: "+dest+"\n");
                                        nodeB.jtprocess.append("Message forwarded to node C");
                                        
					dout.writeUTF(route);
					dout.writeUTF(dest);
					dout.writeUTF(msg);
					dout.close();
					s.close();
				}
				else
				if (dest.equals("nodeA"))
				{
					Socket s=new Socket(nodeA,1600);
					DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                        
                                        nodeB.jtprocess.append("Message: \n"+msg+"\n");
                                        nodeB.jtprocess.append("Destination: "+dest+"\n");
                                        nodeB.jtprocess.append("Message forwarded to node A");
                                        
					dout.writeUTF(route);
					dout.writeUTF(dest);
					dout.writeUTF(msg);
					dout.close();
					s.close();
					
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	void decrypt(String route,String msg)
	{
		try
		{
			char s=route.charAt(0);
			String source="node"+s;
			boolean res=getkey(source);
			if (res)
			{
				String sk=findsk(obj.k,qsrc,source);
				DES des=new DES();
				String m=des.decrypt(msg,sk);
				nodeB.jtprocess.append("\nDecrypted Msg:\n"+m);
                                JOptionPane.showMessageDialog(null,"Message Received: "+m);
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	boolean getkey(String src)
	{
		boolean reply=false;
		try
		{
			
				Socket soc=new Socket(obj.rsuaddr,10000);
				DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
				DataInputStream din=new DataInputStream(soc.getInputStream());
				
				dout.writeUTF("GETKEY");
				dout.writeUTF(src);
				
				reply=(Boolean)din.readBoolean();
				if (reply)
				{
					qsrc=din.readInt();
					JOptionPane.showMessageDialog(null,"Public key of "+src+" is: "+qsrc);
				}
				else
				JOptionPane.showMessageDialog(null,src+" is Not Registered in RSU ");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		
		return reply;
	}
	
	String findsk(int k,int q,String src)
	{
		String sk="";
		try
		{
			nodeB.jtprocess.append("\nPrivate Key of Receiver(K)= "+k);
			nodeB.jtprocess.append("\nPublic Key of Sender "+src+" (Q)= "+q);
				int s=k*q;
				sk=Integer.toString(s);
			nodeB.jtprocess.append("\nShared Key (S)= K*Q="+sk);	
				
				if (sk.length()>8)
				sk=sk.substring(0,8);
				else
				{
					for (int i=0;sk.length()<8;i++)
					sk+=i;
				}
				
				nodeB.jtprocess.append("\nFinal Shared Key (S)="+sk);	
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		
		return sk;
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
	
}