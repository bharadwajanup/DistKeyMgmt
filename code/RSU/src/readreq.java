import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;


class readreq extends Thread
{
	rsu obj;
	
	readreq(rsu obj)
	{
		super();
		this.obj=obj;
		start();
	}
	
	public void run()
	{
		try
		{
			ServerSocket ss=new ServerSocket(10000);
			
			while (true)
			{
				Socket soc=ss.accept();
				DataInputStream din=new DataInputStream(soc.getInputStream());
				DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
				
				String req=din.readUTF();
                                
				
				if (req.equals("REGISTER"))
				{
					String node=din.readUTF();
                                       
                                            
					obj.jta.append(req+" request from "+node+"\n");
                                        boolean entry=false;
                                        for (int i=0;i<obj.row;i++)
					{
						String n=obj.dft.getValueAt(i,0).toString().trim();
						if (n.equals(node))
						{
							
                                                        
                                                        obj.jta.append("Node already registered!\n ");
                                                        entry=true;
                                                        
							break;
						}
					}
                                        if(!entry)
                                        {
					int k=obj.e.generateK(obj.g);
					int q=k*obj.g;
                                        System.out.println(k);
                                        System.out.println(q);
					addinfo(node,q,k);
					dout.writeUTF("SUCCESS");
					dout.writeInt(k);
					dout.writeInt(q);
					obj.jta.append("Reply Sent to "+node+"\n");
                                        }
                                        else
                                        {
                                            dout.writeUTF("EXISTS");
                                        }
                                           
				}
				else
				if (req.equals("GETKEY"))
				{
					String node=(String) din.readUTF();
                                        //obj.jta.append("Public key request by"+node+"\n");
					int  key=0;
					boolean exist=false;
					for (int i=0;i<obj.row;i++)
					{
						String n=obj.dft.getValueAt(i,0).toString().trim();
						if (n.equals(node))
						{
							key=Integer.parseInt(obj.dft.getValueAt(i,1).toString().trim());
							exist=true;
                                                        
                                                        obj.jta.append("Public key of "+node+" sent \n");
                                                        
                                                        
							break;
						}
					}
					
					if (exist)
					{
						dout.writeBoolean(true);
						dout.writeInt(key);
					}
					else
					dout.writeBoolean(false);
				}
				
				
				din.close();
				dout.close();
				soc.close();
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void addinfo(String node,int q,int k)
	{
		obj.dft.setValueAt(node,obj.row,0);
		obj.dft.setValueAt(q,obj.row,1);
		obj.dft.setValueAt(k,obj.row++,2);
	}
}