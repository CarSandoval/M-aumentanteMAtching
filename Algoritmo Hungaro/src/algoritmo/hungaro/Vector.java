package algoritmo.hungaro;

public class Vector
{
	private int vector[];

	private int tam;

	Vector(int n)
	{
		vector = new int[n];
		tam = n;
	}

	public int tamano()
	{
		return tam;
	}

	public void add(int i)
	{
		vector[i] = 1;
	}
        
	public void remove(int i)
	{
		vector[i] = 0;
	}

	public int at(int i)
	{
		return vector[i];
	}
        
        public void at(int i, int val)
        {
            vector[i]=val;
        }
	
	public static Vector resta(Vector a, Vector b)
	{
		
		Vector aux = new Vector(a.tamano());

		for(int i=0;i<a.tamano();i++)
		{
			if( a.at(i)==1 && b.at(i)==1)
			{
				aux.remove(i);
			}
                        else if(a.at(i)==1 && b.at(i)==0)
                        {
                            aux.add(i);
                        }
			
		}
		return aux;
	}

	public static boolean igual(Vector a, Vector b)
	{
		for(int i=0;i<a.tamano();i++)
		{
			if(a.at(i) != b.at(i))
			{
				return false;
			}
		}
		return true;
	}

	public String imprimir()
	{
		String aux = "";
		for(int i=0;i<tam;i++)
		{
			aux+=vector[i]+" ";
		}
		return aux;
	}
}
