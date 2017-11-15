package algoritmo.hungaro;

public class M_aumentante
{
	int n;

	int[][]g;
	int[][]gl;
	int[][]gm;
        int[][]gmPrim;

	//para construir M_aumentante
	M_aumentante(int nAux, int[][]glAux, int[][]gmAux)
	{
		n = nAux;
		gl = new int[n][n];
		gm = gmAux;
                gmPrim = new int[n][n];

		//hago una copia de gl
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{			
				gl[i][j] = glAux[i][j];
                                gmPrim[j][i] = 0;
                                
			}
		}
	}

	public void setGL(int aux, int [][]glAux)
	{
		//hago una copia de gl
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{			
				gl[i][j] = glAux[i][j];
                                gmPrim[i][j] = 0;
			}
		}
	}

	//para imprimir las matrices
	public static void imprimirMat(int[][]m,int n)
	{
            System.out.println("Camino M-Aumentante");
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(m[i][j]+" ");
			}
			System.out.println("");
		}
	}

	//desarrollo el camino M aumentante
	public boolean mAumentante(int index)
	{
		for(int i=0;i<n;i++)
		{
			//System.out.println(i);
			//si no hay camino
			if(gl[index][i]==0)
			{
				System.out.println("entre caso 1");
				continue;
			}
                        gl[index][i]=0;
			//veo si esta M-saturado
			int sum=0;
			for(int j=0;j<n;j++)
			{
				sum+=gm[j][i];
			}
			//si no esta M-saturado
			if(sum==0)
			{
				System.out.println("entre caso 2");
				gm[index][i]=1;
                                //imprimirMat(gm,n);
				return true;
			}
			//si esta M-saturado veo su m-camino
			System.out.println("entre caso 3");
			for(int j=0;j<n;j++)
			{
				if(gm[j][i]==1 && gmPrim[j][i]==0)
				{
					gl[j][i]=0;
                                        gmPrim[j][i]=1;
                                        System.out.println("Gl escofico: "+ j+", "+i);
					if(mAumentante(j)==true)
					{
						gm[j][i]=0;
						gm[index][i]=1;
                                                imprimirMat(gm,n);
						return true;
					}
                                        //gl[j][i]=1;
                                        //gmPrim[j][i]=0;
				}
			}
                        //gl[index][i]=1;
			
		}
               
		return false;
		
	}

	/*
	public static void main(String[] args)
	{
		gl[0][0]=1;
		gl[0][1]=0;
		gl[0][2]=0;
		gl[1][0]=1;
		gl[1][1]=1;
		gl[1][2]=0;
		gl[2][0]=0;
		gl[2][1]=1;
		gl[2][2]=1;

		gm[0][0]=0;
		gm[0][1]=0;
		gm[0][2]=0;
		gm[1][0]=1;
		gm[1][1]=0;
		gm[1][2]=0;
		gm[2][0]=0;
		gm[2][1]=1;
		gm[2][2]=0;
		
		System.out.println("matriz GL");
		imprimirMat(gl,3);
		System.out.println("matriz GM");		
		imprimirMat(gm,3);
		System.out.println("::::::::::::::::");
		mAumentante(0);
		System.out.println("matriz GL");
		imprimirMat(gl,3);
		System.out.println("matriz GM");
		imprimirMat(gm,3);		
	}
	*/
		
}	
		

