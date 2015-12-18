import java.io.*;
import java.net.*;
import javax.swing.*;

class receiver implements Runnable
{
	String nodeB="",nodeD="";
	nodeC obj;
	int qsrc=0;
	
	receiver(nodeC obj)
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
			ServerSocket ss=new ServerSocket(3600);
			while(true)
			{
				Socket soc=ss.accept();
				DataInputStream din=new DataInputStream(soc.getInputStream());
				String route=din.readUTF();
				String dest=din.readUTF();
				String msg=din.readUTF();
				
				route+="-C";
				
				if (dest.equals("nodeC"))
				{
					nodeC.jtprocess.append("\nRoute: "+route+"\n");
					nodeC.jtprocess.append("Message: \n"+msg);
					decrypt(route,msg);
					
				}
				else
				if (dest.equals("nodeD"))
				{
					Socket s=new Socket(nodeD,4600);
					DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                        
                                        nodeC.jtprocess.append("Message: \n"+msg+"\n");
                                        nodeC.jtprocess.append("Destination: "+dest+"\n");
                                        nodeC.jtprocess.append("Message forwarded to node D");
                                        
					dout.writeUTF(route);
					dout.writeUTF(dest);
					dout.writeUTF(msg);
					dout.close();
					s.close();
				}
				else
				if (dest.equals("nodeB"))
				{
					Socket s=new Socket(nodeB,2600);
					DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                                        
                                        nodeC.jtprocess.append("Message: \n"+msg+"\n");
                                        nodeC.jtprocess.append("Destination: "+dest+"\n");
                                        nodeC.jtprocess.append("Message forwarded to node B");
                                        
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
				nodeC.jtprocess.append("\nDecrypted Msg:\n"+m);
                                JOptionPane.showMessageDialog(null,"Message Received: "+m);
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	boolean getkey(String src)
	{
		boolean reply=false;
		try
		{
			if (obj.k==0)
			JOptionPane.showMessageDialog(null,"U R Not Registered with RSU!");
			else
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
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return reply;
	}
	
	String findsk(int k,int q,String src)
	{
		String sk="";
		try
		{
			nodeC.jtprocess.append("\nPrivate Key of Receiver(K)= "+k);
			nodeC.jtprocess.append("\nPublic Key of Sender "+src+" (Q)= "+q);
				int s=k*q;
				sk=Integer.toString(s);
			nodeC.jtprocess.append("\nShared Key (S)= K*Q="+sk);	
				
				if (sk.length()>8)
				sk=sk.substring(0,8);
				else
				{
					for (int i=0;sk.length()<8;i++)
					sk+=i;
				}
				
				nodeC.jtprocess.append("\nFinal Shared Key (S)="+sk);	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return sk;
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
	
}