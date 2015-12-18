import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

class rsu extends JFrame implements ActionListener
{
	DefaultTableModel dft;
	JTable table;
	JTextArea jta;
	JButton jbexit;
	ecc e=new ecc();
	int g=e.generateG();
	int row=0;
	
	rsu()
	{
		super("RSU");
		createwin();
		new readreq(this);
	}
	
	void createwin()
	{
		Container cp=this.getContentPane();
		cp.setLayout(null);
		//cp.setBackground(Color.green);
		
		JLabel jl=new JLabel("ROAD SIDE UNIT",JLabel.CENTER);
		jl.setFont(new Font("Dialog",Font.BOLD,20));
		jl.setForeground(Color.red);
		
		JSeparator js=new JSeparator();
		
		JLabel jl1=new JLabel("Registered Node Details:");
		
		dft=new DefaultTableModel(4,0);
		table=new JTable(dft);
		dft.addColumn("NODE NAME");
		dft.addColumn("PUBLIC KEY");
		dft.addColumn("PRIVATE KEY");
		
		JScrollPane jsp=new JScrollPane(table);
		
		JSeparator js1=new JSeparator();
		
		JLabel jl2=new JLabel("PROCESS DETAILS: ");
		jta=new JTextArea();
                jta.setEditable(false);
		JScrollPane jsp1=new JScrollPane(jta);
		
		jbexit=new JButton("EXIT");
		
		JLabel jlimg1=new JLabel(new ImageIcon("index.jpg"),JLabel.CENTER);
		JLabel jlimg2=new JLabel(new ImageIcon("images.jpg"),JLabel.CENTER);
		
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		addcomponent(cp,jl,0,0,d.width,30);
		addcomponent(cp,js,0,30,d.width,30);
		addcomponent(cp,jl1,10,50,300,20);
		addcomponent(cp,jsp,10,80,600,100);
		addcomponent(cp,js1,0,190,d.width,180);
		addcomponent(cp,jl2,10,200,300,20);
		addcomponent(cp,jsp1,10,230,600,400);
		addcomponent(cp,jbexit,150,650,100,30);
		addcomponent(cp,jlimg1,650,40,400,150);
		addcomponent(cp,jlimg2,650,300,300,200);
		
		
		this.setSize(d.width,d.width-30);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jbexit.addActionListener(this);
		
	}
	
	void addcomponent(Container cp,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		cp.add(c);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource()==jbexit)
		{
			System.exit(0);
		}
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
            java.util.logging.Logger.getLogger(rsu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		new rsu();
	}
}