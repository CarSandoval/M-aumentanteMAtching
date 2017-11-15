
package algoritmo.hungaro;

import java.util.Scanner;

public class AlgoritmoHungaro {

    
        static final boolean DEBUG_ON  = false; 

        //static M_aumentante M_aug;
        static boolean paso1_habilitado;
        static boolean error=false;
        static boolean fin;
        static boolean first;
        static int[][] g;
        static int[][] gl;
        static int[][] gm;
        static Vector S;
        static Vector T;
        static Vector N;
        static Vector lx;
        static Vector ly;
        static M_aumentante M;
        static Reetiquetado R;
        static int n;
        static int id;
        static Scanner input;
        static boolean llenado;
        static boolean process;
        
    
    public static void main(String[] args) {
        while(true)
        {
            setup();
            llenado();  //Usuario llena matriz
            redefinido();   //Redefinen matrices con tamaño definido por usuario
            gprint();       //Imprimir G
            etiquetado();   //Crea etiquetado lx y ly
            debug(true, "lx = "+lx.imprimir()); 
            debug(true, "ly = "+ly.imprimir());
            maximos();      //Encuentra los pesos máximos para Gl
            glprint();      //Imprime Gl
            matching();     //Encuentra un Matching arbitrario en Gl
            gmprint();      //Imprime Gm
            ciclo_algoritmo();  //Inicia ciclo del Algoritmo K-M
            
        }
    }
    
    public static void main2() {
        
        int nErr = n;
        int[][] gErr = new int[nErr][nErr];
        for(int i=0; i<nErr; i++)
        {
            for(int j=0; j<nErr; j++)
            {
                gErr[i][j]=g[j][i];
            }
        }
        setup();
        //llenado();  //Usuario llena matriz
        n = nErr;
        g = new int[n][n];
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                g[i][j]=gErr[i][j];
            }
        }
        redefinido();   //Redefinen matrices con tamaño definido por usuario
        gprint();       //Imprimir G
        etiquetado();   //Crea etiquetado lx y ly
        debug(true, "lx = "+lx.imprimir()); 
        debug(true, "ly = "+ly.imprimir());
        maximos();      //Encuentra los pesos máximos para Gl
        glprint();      //Imprime Gl
        matching();     //Encuentra un Matching arbitrario en Gl
        gmprint();      //Imprime Gm
        ciclo_algoritmo();  //Inicia ciclo del Algoritmo K-M
        System.out.println("Trans:");
        gmtprint();
            
        
    }
    
    static void ciclo_algoritmo()
    {
        while(!fin) //Mientras no haya terminado el Algoritmo
            {
                if(paso1_habilitado)    //Si el Paso 1 está habulitado
                {
                    paso1();    //Hacer Paso 1
                    debug(true,"Paso 1 Completado");
                    paso1_habilitado = false;   //Deshabilitaar Paso 1
                }
                if(!fin)        //Si no ha terminado, hacer los siguientes Pasos
                {
                    paso2();    //Hacer Paso 2
                    debug(true,"Paso 2 Completado");
                    paso3();    //Hacer Paso 3
                    debug(true,"Paso 3 Completado");
                    glprint();  //Imprimir Gl
                    gmprint();  //Imprimir Gm
                }
            }
    }
    
    static void final_algoritmo()
    {
        
            fin=true;   //Algoritmo Terminado
            debug(matching_perfecto(),"fin(): Fin Completado");
            System.out.println("w_max = "+weight());
            first=true; //Primera iteración de Algoritmo habilitada
        
    }
    
    static void transpuesta_g()
    {
        int[][] glTemp = new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                glTemp[i][j] = gl[j][i];
            }
        }
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                gl[i][j] = glTemp[i][j];
            }
        }
    }
    
    static int weight()
    {
        int i=0;
        int j=0;
        int sum=0;
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                if(gm[i][j]==1)
                {
                    sum+=g[i][j];
                }
            }
        }
        return sum;
    }
    
    static void paso1()
    {
        int u;
        gmprint();
        if(matching_perfecto()) //Si hay  Matching Perfecto
        {
            final_algoritmo();  //Iniciar proceso de Finalizacion
        }
        if(first && !fin)   //Si Primera Iterqacion haabilitada y no ha terminado
        {
            u=find_non_matching();  //Encuentra un 'u' sin M-Saturacion
            if(u>=0)    //Si 'u' no es -1
            {
                S.add(find_non_matching()); //Añadir 'u' a 'S'
                System.out.println("S = "+S.imprimir());
                System.out.println("T = "+T.imprimir());
            }
            else    //Si  'u' es -1
            {
                debug(true&&u==-1,"paso1(): 'u' No existe");    //ERROR
                
            }
            first = false;  //Deshabilitar Primera Iteracion
        }
    }
    
    static void paso2()
    {
        vecinos();  //Calcular Vecinos N de S
        System.out.println("N = "+N.imprimir());
        if(Vector.igual(T,N))   //Si T = N
        {
            debug(true,"paso2(): T=N");
            R.reetiquetado();   //Hacer Reetiquetado
            System.out.println("lx = "+lx.imprimir());
            System.out.println("ly = "+ly.imprimir());
            debug(true,"paso2(): reetiquetado() Completado");
            glprint();  //Imprimir Gl
            vecinos();  //Calcular Vecinos N de S
        }
        if(Vector.igual(T,N))
        {
            System.out.println("N = T: Transponiendo . . .");
            main2();
        }
    }
    
    static void paso3()
    {
        Vector V0 = new Vector(n);
        int y=-1;
        int z=-1;
        V0=Vector.resta(N, T);  //Calcular N\T
        debug(true,"paso3(): N-T = "+V0.imprimir()+" Completado");
        y=find_y(V0);   //Encuentra una 'y' en N\T
        debug(true&&y!=-1,"paso3(): y = "+y+" Encontrado");
        if(y!=-1)
        {
            if(!saturado(y))    //Si 'y' no es M-Saturado
            {
                debug(true, "paso3(): 'y' no M-Saturado");
                M.setGL(n, gl);
                System.out.println("Y = "+y);
                if(!M.mAumentante(y))
                {
                    System.out.println("Sin M-Aumentante: Transponiendo . . .");
                    main2();
                }
                glprint();
               // while(!M.mAumentante(y))   //Calcular camino M-Aumentnate
                //{
                    //R.reetiquetado();
                    //M.setGL(n,gl);
                //}
                debug(true,"paso3(): Camino M-Aumentante Creado");
                paso1_habilitado = true;    //Habilitar Paso 1
                debug(true&&paso1_habilitado,"paso3(): paso1_habilitado");
            }
            else    //Si 'y' es M-Saturado
            {
                debug(true, "paso3(): 'y' M-Saturado");
                z=find_z(y);    //Encontrar una 'z' que hace Matching con y en Gm
                debug(true&&z!=-1,"paso3(): z = "+z+" Encontrado");
                S.add(z);   //Añadir 'z' a S
                System.out.println("z = "+z);
                debug(true&&S.at(z)==1,"paso3(): S U {z} = "+S.imprimir());
                T.add(y);   //Añadir 'y' a T
                System.out.println("y = "+y);
                debug(true&&T.at(y)==1,"paso3(): T U {y} = "+T.imprimir());
            }
        }
        else 
        {
            System.out.println("y = -1:  Resuelto ");
            final_algoritmo();
        }
        
    }
    
    static void etiquetado()
    {
        int j=0;
        int i=0;
        int max=0;
        for(j=0;j<n;j++)
        {
            for(i=0;i<n;i++)
            {
                if(g[i][j]>max)
                {
                    max = g[i][j];
                }
            }
            lx.at(j,max);
            max=0;
        }
        for(i=0;i<n;i++)
        {
            ly.at(i,0);
        }
    }
    
    static int find_y(Vector V0)
    {
        int i=0;
        int y=-1;
        for(i=0;i<n;i++)
        {
            if(V0.at(i)==1)
            {
                y=i;
                return y;
            }
        }
        return y;
    }
    
    static int find_z(int y0)
    {
        int j=0;
        for(j=0;j<n;j++)
        {
            if(gm[y0][j]==1)
            {
                return j;
            }
        }
        return -1;
    }
    
    static boolean saturado(int i0)
    {
        int j=0;
        int sum=0;
        for(j=0;j<n;j++)
        {
            sum+=gm[i0][j];
            //debug(true&&j>=0,"saturado(): 'j' Correcto");
            //debug(true&&i0>=0,"saturado(): 'i0' Correcto");
        }
        if(sum>0)
            return true;
        else
            return false;
    }
    
    static void vecinos()
    {
        int i=0;
        int j=0;
        for(j=0;j<n;j++)
        {
            if(S.at(j)==1)
            {
                for(i=0;i<n;i++)
                {
                    if(gl[i][j]==1)
                    {
                        N.add(i);
                        debug(true, "gl["+j+"]["+i+"] = "+gl[i][j]);
                    }
                    //System.out.println("N["+i+"] = "+N.imprimir());
                }
            }
        }
    }
    
    static int find_non_matching()
    {
        int i=0;
        int j=0;
        int sum=0;
        for(j=0;j<n;j++)
        {
            for(i=0;i<n;i++)
            {
                sum+=gm[i][j];
                
            }
            if(sum<=0)
            {
                return j;
            }
            sum=0;
        }
        return -1;
    }
    
    static boolean matching_perfecto()
    {
        int i=0;
        int j=0;
        int sum=0;
        for(j=0;j<n;j++)
        {
            for(i=0;i<n;i++)
            {
                sum+=gm[j][i];
            }
            if(sum<=0)
            {
                return false;
            }
            sum=0;
        }
        return true;
    }
    
    static void redefinido()
    {
        //g = new int[n][n];
        gl = new int[n][n];
        gm = new int[n][n];
        T = new Vector(n);
        S = new Vector(n);
        N = new Vector(n);
        lx = new Vector(n);
        ly = new Vector(n);
        M = new M_aumentante(n,gl,gm);
    }
    
    static void setup()
    {
      //size(300,400);
      n = 1;
      fin = false;
      paso1_habilitado = true;
      input = new Scanner(System.in);
      id=0;
      process = false;
      first = true;
      g = new int[n][n];
      gl = new int[n][n];
      gm = new int[n][n];
      T = new Vector(n);
      S = new Vector(n);
      N = new Vector(n);
      lx = new Vector(n);
      ly = new Vector(n);
      M = new M_aumentante(n,gl,gm);
      R = new Reetiquetado();
      debug(true, "Declaraciones Iniciales");
    }

    static void debug(boolean arg, String msg)
    {
      if (DEBUG_ON)
      {
        if (arg)
          System.out.println("DEBUG("+id+"): "+msg);
        else
          System.out.println("DEBUG("+id+"):ERROR: "+msg);
        id++;
      }
    }

    static void matching()
    {
      int i;
      int j;
      int jj=0;
      int sum = 0;
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          sum=0;
          while (jj<n && sum<=0)
          {
            sum+=gm[jj][i];
            jj++;
          }
          jj=0;
          if (gl[j][i] == 1 && sum <= 0)
          {
            gm[j][i]=1;
            break;
          }
        }
      }
    }

    static void maximos()
    {
      int i;
      int j;
      int[] j0;
      int max;
      int ind;

      i = 0;
      j = 0;
      j0 = new int[n];
      max = 0;
      ind = 1;
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          if (g[i][j]==max)
          {
            j0[i]=ind;
          }
          if (g[i][j]>max)
          {
            max=g[i][j];
            ind++;
            j0[i]=ind;
          }
        }
        for (i=0; i<n; i++)
        {
          if (j0[i]==ind)
          {
            gl[i][j]=1;
          }
          j0[i]=0;
        }
        ind=1;
        max=0;
      }
    }

    static void zero()
    {
      int i=0;
      int j=0;
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          g[j][i]=0;
          gl[j][i]=0;
          gm[j][i]=0;
        }
      }
    }
    
    static void zero_no_g()
    {
      int i=0;
      int j=0;
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          //g[j][i]=0;
          gl[j][i]=0;
          gm[j][i]=0;
        }
      }
    }

    static void gprint()
    {
      int i=0;
      int j=0;
      System.out.println("\nG:\t");
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          System.out.print(g[j][i]);
          System.out.print(", ");
        }
        System.out.println(";");
      }
    }

    static void glprint()
    {
      int i=0;
      int j=0;
      System.out.println("\nGl:\t");
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          System.out.print(gl[j][i]);
          System.out.print(", ");
        }
        System.out.println(";");
      }
    }

    static void gmprint()
    {
      int i=0;
      int j=0;
      System.out.println("\nGm:\t");
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          System.out.print(gm[j][i]);
          System.out.print(", ");
        }
        System.out.println(";");
      }
    }
    
    static void gmtprint()
    {
      int i=0;
      int j=0;
      System.out.println("\nGm:\t");
      for (j=0; j<n; j++)
      {
        for (i=0; i<n; i++)
        {
          System.out.print(gm[i][j]);
          System.out.print(", ");
        }
        System.out.println(";");
      }
    }

    static void llenado()
    {
        String keys;
        int key=0;
        int i=0;
        int j=0;
        char c=0;
        debug(true, "llenado(): En Proceso");
        System.out.print("Cantidad de Elementos:  ");
        n = input.nextInt();
        debug(true&&n>0,"llenado(): Redefinida Cantidad de Elementos");
        g = new int[n][n];
        System.out.println("Elementos:  ");
        debug(true, "llenado(): Input->Elementos...");
        /*keys = input.next();
        System.out.println(keys);
        debug(true&&keys!="", "llenado(): Input->Elementos... Completado");
        input = new Scanner(keys);
        debug(true,"llenado(): Scanner<-keys");
        */
        for(j=0;j<n;j++)
        {
            for(i=0;i<n;i++)
            {
                key = input.nextInt();
                g[j][i]=key;
            }
        }
        /*while (j<n && input.hasNext())
        {
            
            /*
            debug(true,"llenado(): while()");
            c=input.next().charAt(0);
            if(c<='0' && c>='9')
                key=c-'0';
            if (i<=n-1)
            {
              g[j][i] = key;
              System.out.print(g[j][i]+", ");
              i++;
            }
            else
            {
              System.out.println(";");
              j++;
              i=0;
            }
        }*/
        debug(true&& g!=null,"llenado(): Completado");
        gprint();
        input = new Scanner(System.in);
    }
        // TODO code application logic here
}