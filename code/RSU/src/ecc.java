import java.lang.Math.*;

class ecc
{
	int G=0;
	int Ka=0,Kb=0;
	
	/*ecc()
	{
		
		G=generateG();
		Ka=generateK(G);
		Kb=generateK(G);
		int Qa=Ka*G;
		int Qb=Kb*G;
		
		int KaQb=Ka*Qb;
		int KbQa=Kb*Qa;
		int KaKbG=Ka*Kb*G;
		
		System.out.println("G="+G);
		System.out.println("Ka="+Ka);
		System.out.println("Kb="+Kb);
		System.out.println("Qa="+Qa);
		System.out.println("Qb="+Qb);
		System.out.println("KaQb="+KaQb);
		System.out.println("KbQa="+KbQa);
		System.out.println("KaKbG="+KaKbG);
		
	}
	*/
	int generateG()
	{
		int Gtemp=0;
		try
		{
			while(true)
			{
			
				Gtemp=(int)(Math.random()*10000);
				
				if (prime(Gtemp) && Gtemp>20)
				break;
			}
			
			
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return Gtemp;
	}
	
	boolean prime(int num)
	{
		boolean result=true;
		
		int i=0;
		for (i=2;i<num;i++)
		if ((num%i)==0)
		break;
		
		if (i==num)
		result=true;
		else
		result=false;
      		
		
		
		return result;
		
	}
	
	
	int generateK(int Gtemp)
	{
		int ktemp=0;
		try
		{
			while(true)
			{
				ktemp=(int)(Math.random()*Gtemp);
				if (ktemp>1)
				break;	
			}
				
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return ktemp;
	}
	
	
	public static void main(String args[])
	{
		ecc e=new ecc();
		
		
	}
}