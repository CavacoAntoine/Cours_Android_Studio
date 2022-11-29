package com.example.tp2.pile;

import java.util.Stack;

public class Pile2<T> implements PileI<T>{
  /** par d�l�gation : utilisation de la class Stack */
  private Stack<T> stk;
  /** la capcit� de la pile */
  private int   capacité;
  
  /** Cr�ation d'une pile.
   * @param taille la taille de la pile, la taille doit �tre > 0
   */
  public Pile2(int taille){
    this.stk = new Stack<T>();
    this.capacité = taille;
  }

  public Pile2(){
    this(PileI.CAPACITE_PAR_DEFAUT);
  }
  

  public void empiler(T o) throws PilePleineException{
    if(estPleine()) throw new PilePleineException(o.toString());
    stk.push(o);
  }
  

  public T depiler() throws PileVideException{
    if(estVide())  throw new PileVideException();
    return stk.pop();
  }


  public T sommet() throws PileVideException{
    if(estVide())  throw new PileVideException();
     return stk.peek();
  }
  
  /** Effectue un test de l'�tat de la pile.
   * @return vrai si la pile est vide, faux autrement
   */
  public boolean estVide(){
    return stk.empty();
  }

  /** Effectue un test de l'�tat de la pile.
   * @return vrai si la pile est pleine, faux autrement
   */   
  public boolean estPleine(){
    return capacité == stk.size();
  }
  
  /** Retourne une repr�sentation en String d'une pile, 
   * contenant la repr�sentation en String de chaque �l�ment.
   * @return une repr�sentation en String d'une pile
   */ 
  public String toString(){
    return stk.toString();
  }
  
  /** Retourne la taille de la pile
   * @return cette taille
   */ 
  public int taille(){
    return this.stk.size();
  }
  
  /** Retourne la taille de la pile
   * @return cette taille
   */ 
  public int capacite(){
    return this.capacité;
  }
}
