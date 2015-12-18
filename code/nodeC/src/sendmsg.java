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
		nodeC.jtprocess.append("\nEncrypted Msg: \n"+m1);
		
		int cost1=Integer.parseInt((String)nodeC.jtable.getValueAt(0,1));
		int cost2=Integer.parseInt((String)nodeC.jtable.getValueAt(1,1));
		
		if (dest.equals("nodeA"))		
		{
			if (cost1<cost2)
			send("nodeB");
			else
			send("nodeD");
		}
		else
		send(dest)	;
	}
	
	String findsk(int k,int q)
	{
		String sk="";
		try
		{
			nodeC.jtprocess.append("\nPrivate Key of Sender(K)= "+k);
			nodeC.jtprocess.append("\nPublic Key of Receiver "+dest+" (Q)= "+q);
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
	
	void send(String next)
	{
		int port=0;
		try
		{
			String ip=readaddr(next);
			if (next.equals("nodeB"))
			port=2600;
			else
			port=4600;
			
			Socket soc=new Socket(ip,port)	;
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
			dout.writeUTF("C");
			dout.writeUTF(dest);
			dout.writeUTF(m1);
			dout.close();
			soc.close();
			nodeC.jtprocess.append("\nMessage Sent...\n");
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
			
			
			if (next.equals("nodeB"))
			{
				FileInputStream fin=new FileInputStream("nodeB.txt");
				while((ch=fin.read())!=-1)
				addr+=(char)ch;
				addr.trim();
			}
			else
			{
				FileInputStream fin=new FileInputStream("nodeD.txt");
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