package com.example.tp2.pile;

/**
 * D�crivez votre classe PilePleineException ici.
 * 
 * @author (votre nom) 
 * @version (un num�ro de version ou une date)
 */
public class PilePleineException extends Exception{

  public PilePleineException(){
    super("Pile pleine exception");
  }
  public PilePleineException(String message){
    super(message);
  }
}