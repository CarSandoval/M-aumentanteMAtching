/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo.hungaro;

/**
 *
 * @author axeladn
 */
public class Reetiquetado {
    
//VARIABLE GLOBAL
static int S_to=1; //si el valor es 1 indica que S guarda valores del conjunto "X" si el valor es 0 entonces S guarda valores del conjunto "Y"



static void reetiquetado(){
  int min=0;
  
  for (int i=0;i<AlgoritmoHungaro.n;i++){
    if(AlgoritmoHungaro.S.at(i)==1){ //Busca un x que se encuentre en S
      for (int j=0;j<AlgoritmoHungaro.n;j++)
        if(AlgoritmoHungaro.T.at(j)==0){//Busca un y que no se encuentre en T
          if(S_to==1){
              min=AlgoritmoHungaro.lx.at(i) + AlgoritmoHungaro.ly.at(j) - AlgoritmoHungaro.g[j][i];
              break;
          } else{        //DUDA
              min=AlgoritmoHungaro.ly.at(i) + AlgoritmoHungaro.lx.at(j) - AlgoritmoHungaro.g[i][j];
              break;
          }
        }
    }
    break;
  }
  
  for (int i=0;i<AlgoritmoHungaro.n;i++){
    if(AlgoritmoHungaro.S.at(i)==1){ //Busca un x que se encuentre en S
      for (int j=0;j<AlgoritmoHungaro.n;j++)
        if(AlgoritmoHungaro.T.at(j)==0){//Busca un y que no se encuentre en T
          if(S_to==1){
            if((AlgoritmoHungaro.lx.at(i) + AlgoritmoHungaro.ly.at(j) - AlgoritmoHungaro.g[j][i])<min)
              min=AlgoritmoHungaro.lx.at(i) + AlgoritmoHungaro.ly.at(j) - AlgoritmoHungaro.g[j][i];
          } else{        //DUDA
            if((AlgoritmoHungaro.ly.at(i) + AlgoritmoHungaro.lx.at(j) - AlgoritmoHungaro.g[i][j])<min)
              min=AlgoritmoHungaro.ly.at(i) + AlgoritmoHungaro.lx.at(j) - AlgoritmoHungaro.g[i][j];
          }
        }
    }
  }
  
  System.out.println("minimo: "+min+"\n");
  for (int i=0;i<AlgoritmoHungaro.n;i++){
    //Modifica el etiquetado de los elementos que esten en S
    if(AlgoritmoHungaro.S.at(i)==1){
      if(S_to==1)
        AlgoritmoHungaro.lx.at(i,AlgoritmoHungaro.lx.at(i)-min);
       else
         AlgoritmoHungaro.ly.at(i,AlgoritmoHungaro.ly.at(i)-min);
    }
    //Modifica el etiquetado de los elementos que esten en T
    if(AlgoritmoHungaro.T.at(i)==1)
      if(S_to==1)
        AlgoritmoHungaro.ly.at(i,AlgoritmoHungaro.ly.at(i)+min);
       else
         AlgoritmoHungaro.lx.at(i,AlgoritmoHungaro.lx.at(i)+min);
  }
  
  
  
  //Modifica Gl contemplando el nuevo etiquetado
  for (int i=0;i<AlgoritmoHungaro.n;i++)
    for (int j=0;j<AlgoritmoHungaro.n;j++){
      if(AlgoritmoHungaro.g[j][i]==AlgoritmoHungaro.lx.at(i))
        AlgoritmoHungaro.gl[j][i]=1;
    }
   
  
  
}
    
}
