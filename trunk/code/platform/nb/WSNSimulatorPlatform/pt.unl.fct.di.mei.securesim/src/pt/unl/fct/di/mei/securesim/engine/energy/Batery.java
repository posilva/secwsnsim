package pt.unl.fct.di.mei.securesim.engine.energy;

public class Batery {
	public static final double INFINIT = -1;
	private double currentPower = INFINIT;

	/**
	 * @param currentPower
	 *            the currentPower to set
	 */
	public void setCurrentPower(double currentPower) {
		this.currentPower = currentPower;
	}

	/**
	 * @return the currentPower
	 */
	public double getCurrentPower() {
		return currentPower;
	}

	public boolean off() {
		return currentPower == 0;
	}
	
	 public void consume(double value){
		 synchronized (this) {
			 currentPower-=value;	
		} 
		fireOnEnergyConsume(new EnergyEvent(currentPower)); 
	}

	private void fireOnEnergyConsume(EnergyEvent energyEvent) {
		
	} 
}
