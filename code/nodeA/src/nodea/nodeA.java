import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.io.*;
import java.net.*;
import javax.swing.border.TitledBorder;


class nodeA extends JFrame implements ActionListener
{
	JComboBox jcdest;
	static JTextArea jta;
	static JTable jtable;
	static JTextField jtmy;
	static DefaultTableModel df;
	JButton jbsend,jbclear,jbexit,jbstart,jbbrowse,jbregister;
        
	static JList list;
	String rsuaddr="";
	static JTextArea jtprocess;
	int k=0;
	int q=0;
	int qdest=0;
	
	nodeA()
	{
		super("Node A");
		readaddr();
		createwin();
		new ragent("A");
		new receiver(this);
	}
	
	void readaddr()
	{
		try
		{
			int ch=0;
			FileInputStream fin=new FileInputStream("rsu.txt");
			while((ch=fin.read())!=-1)
			rsuaddr+=(char)ch;
			rsuaddr.trim();
			fin.close();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void createwin()
	{
		Container cp=this.getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(159,182,205));
		
		JLabel jl=new JLabel("NODE A",JLabel.CENTER);
		jl.setFont(new Font("Dialog",Font.BOLD,20));
		jl.setForeground(new Color(23,45,123));
		
		JPanel jp1=new JPanel();
		jp1.setLayout(null);
		
		JPanel jp2=new JPanel();
		jp2.setLayout(null);
		
		JLabel jldest=new JLabel("Select Destination");
		jcdest=new JComboBox();
		jcdest.addItem("SELECT");
		jcdest.addItem("nodeB");
		jcdest.addItem("nodeC");
		jcdest.addItem("nodeD");
		
		JLabel jlmsg=new JLabel("Enter the Message:");
		jta=new JTextArea();
                jta.setBackground(new Color(248,248,255));
                TitledBorder title;
                title = BorderFactory.createTitledBorder("Message");
                jta.setBorder(title);
		JScrollPane jsp=new JScrollPane(jta);
		
		jbbrowse=new JButton("Browse");
		
		jbsend=new JButton("Send");
		jbclear=new JButton("Clear");
		
		JLabel jltable=new JLabel("Routing Table");
		
		df=new DefaultTableModel(2,0);
		jtable=new JTable(df);
		df.addColumn("NEIGHBOUR");
		df.addColumn("Cost");
		jtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane jsp1=new JScrollPane(jtable);
		
		df.setValueAt("B",0,0);
		df.setValueAt("5",0,1);
		df.setValueAt("D",1,0);
		df.setValueAt("5",1,1);
		
		JLabel jlmy=new JLabel("MY COST");
		jtmy=new JTextField("5");
                
		
		JLabel jlstack=new JLabel("STACK");
		list=new JList();
                list.setBackground(new Color(248,248,255));
		JScrollPane jsplist=new JScrollPane(list);
		
		jbstart=new JButton("START AGENT");
		jbexit=new JButton("EXIT");
                
		jbregister=new JButton("Register With RSU");
                
		
		addcomponent(cp,jl,0,0,400,30);
		addcomponent(jp1,jldest,10,10,150,20);
		addcomponent(jp1,jcdest,160,10,100,20);
		addcomponent(jp1,jbstart,280,10,120,25);
		addcomponent(jp1,jlmsg,10,40,120,20);
		addcomponent(jp1,jbbrowse,130,40,80,25);
		addcomponent(jp1,jsp,10,70,200,100);
		addcomponent(jp1,jbsend,10,180,100,30);
		addcomponent(jp1,jbclear,110,180,100,30);
		addcomponent(jp1,jltable,10,220,150,20);
		addcomponent(jp1,jsp1,10,250,200,50);
		addcomponent(jp1,jlmy,260,40,70,20);
		addcomponent(jp1,jtmy,330,40,30,25);
		addcomponent(jp1,jlstack,230,80,100,20);
		addcomponent(jp1,jsplist,230,110,150,250);
		addcomponent(jp1,jbexit,50,320,100,30);
		addcomponent(jp1,jbregister,10,380,300,30);
                
		
		jtprocess=new JTextArea();
                jtprocess.setBackground(new Color(248,248,255));
                jtprocess.setEditable(false);
		JScrollPane jspprocess=new JScrollPane(jtprocess);
		addcomponent(jp2,jspprocess,0,0,350,400);
		
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Messaging",jp1);
		jtp.addTab("Process",jp2);
		
		addcomponent(cp,jtp,0,30,400,450);
		
		this.setSize(406,510);
		this.setVisible(true);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setResizable(false);
		
		jbstart.addActionListener(this);
		jbexit.addActionListener(this);
		jbsend.addActionListener(this);
		jbclear.addActionListener(this);
		jbbrowse.addActionListener(this);
		jbregister.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource()==jbregister)
		{
			register();
		}
		else
		if (ae.getSource()==jbstart)
		{
			new agent("A");
		}
		else
		if (ae.getSource()==jbexit)
		{
			System.exit(0);
		}
		else
		if (ae.getSource()==jbbrowse)
		{
			JFileChooser jfc=new JFileChooser();
			int m=jfc.showOpenDialog(this);
			if (m==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					String msg="";
					File f=jfc.getSelectedFile();
					FileInputStream fin=new FileInputStream(f);
					int ch=0;
					while((ch=fin.read())!=-1)
					msg+=(char)ch;
					msg.trim();
					fin.close();
					jta.setText(msg);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else
		if (ae.getSource()==jbsend)
		{
			String dest=jcdest.getSelectedItem().toString().trim();
		
			String msg=jta.getText().trim();
			
			if (!dest.equals("SELECT") && !msg.equals(""))
			{
				try
				{
					boolean result=getkey(dest);
					if (result)
					new sendmsg(dest,msg,k,qdest);
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				
			}
			else
			JOptionPane.showMessageDialog(this,"Select Dest and Message first! ");
			
			
		}
		else
		if (ae.getSource()==jbclear)
		{
			jcdest.setSelectedIndex(0);
			jta.setText("");
		}
	}
	
	void addcomponent(Container cp,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		cp.add(c);
	}
	
	void register()
	{
		try
		{
			Socket soc=new Socket(rsuaddr,10000);
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
			DataInputStream din=new DataInputStream(soc.getInputStream());
                       
			
                        
			dout.writeUTF("REGISTER");
			dout.writeUTF("nodeA");
                        
			String reply=din.readUTF();
			
			if (reply.equals("SUCCESS"))
			{
				k=din.readInt();
				q=din.readInt();
				JOptionPane.showMessageDialog(this,"Successfully Registered\nPublic Key(Q)="+q+"\nPrivate Key(K)="+k);
                                jtprocess.append("Successfully Registered...\n");
                                jbregister.setText("REGISTERED!");
                                jbregister.setForeground(Color.red);
                                
                               jbregister.setEnabled(false);
                                
                                
                                
			}
                        else if(reply.equals("EXISTS"))
                        {
                            JOptionPane.showMessageDialog(this, "Node already registered with RSU!", "Already Registered!",JOptionPane.ERROR_MESSAGE);
                            
                            
                        }
                                
			else
			JOptionPane.showMessageDialog(this,"Registration Failed");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	boolean getkey(String dest)
	{
		boolean reply=false;
		try
		{
			if (k==0)
			JOptionPane.showMessageDialog(this,"U R Not Registered with RSU!");
			else
			{
				Socket soc=new Socket(rsuaddr,10000);
				DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
				DataInputStream din=new DataInputStream(soc.getInputStream());
				
				dout.writeUTF("GETKEY");
                                
				dout.writeUTF(dest);
                                
				
				reply=(Boolean)din.readBoolean();
				if (reply)
				{
					qdest=(int)din.readInt();
					JOptionPane.showMessageDialog(this,"Public key of "+dest+" is: "+qdest);
				}
				else
				JOptionPane.showMessageDialog(this,dest+" is Not Registered in RSU ");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return reply;
	}
	
	

	
	public static void main(String args[])
	{
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nodeA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		new nodeA();
	}
}
