import java.io.*;
import java.net.*;

class sendmsg
{
	String dest="",msg="",m1="";
	
	sendmsg(String d,String msg,int k,int q)
	{
		dest=d;
		this.msg=msg;
		
		String skey=findsk(k,q); 
		DES des=new DES();
		m1=des.encrypt(msg,skey);
		nodeD.jtprocess.append("\nEncrypted Msg: \n"+m1);
		
		int cost1=Integer.parseInt((String)nodeD.jtable.getValueAt(0,1));
		int cost2=Integer.parseInt((String)nodeD.jtable.getValueAt(1,1));
		
		if (dest.equals("nodeB"))		
		{
			if (cost1<cost2)
			send("nodeA");
			else
			send("nodeC");
		}
		else
		send(dest)	;
	}
	
	String findsk(int k,int q)
	{
		String sk="";
		try
		{
			nodeD.jtprocess.append("\nPrivate Key of Sender(K)= "+k);
			nodeD.jtprocess.append("\nPublic Key of Receiver "+dest+" (Q)= "+q);
				int s=k*q;
				sk=Integer.toString(s);
			nodeD.jtprocess.append("\nShared Key (S)= K*Q="+sk);	
				
				if (sk.length()>8)
				sk=sk.substring(0,8);
				else
				{
					for (int i=0;sk.length()<8;i++)
					sk+=i;
				}
				
				nodeD.jtprocess.append("\nFinal Shared Key (S)="+sk);	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return sk;
	}
	
	void send(String next)
	{
		int port=0;
		try
		{
			String ip=readaddr(next);
			if (next.equals("nodeA"))
			port=1600;
			else
			port=3600;
			
			Socket soc=new Socket(ip,port)	;
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
			dout.writeUTF("D");
			dout.writeUTF(dest);
			dout.writeUTF(m1);
			dout.close();
			soc.close();
			nodeD.jtprocess.append("\nMessage Sent...\n");
		}	
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	String readaddr(String next)
	{
		String addr="";
		try
		{
			int ch=0;
			
			
			if (next.equals("nodeA"))
			{
				FileInputStream fin=new FileInputStream("nodeA.txt");
				while((ch=fin.read())!=-1)
				addr+=(char)ch;
				addr.trim();
			}
			else
			{
				FileInputStream fin=new FileInputStream("nodeC.txt");
				while((ch=fin.read())!=-1)
				addr+=(char)ch;
				addr.trim();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return addr;
	}
	
}