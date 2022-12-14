package com.example.tp4;

public abstract class HumiditySensorAbstract{
  /** valeur du capteur, précision de 0.1 */
	public abstract float value() throws Exception;
	
	/* période minimale entre deux lectures */
	public abstract long minimalPeriod();

	public abstract void setUrlSensor(String urlSensor);
}
