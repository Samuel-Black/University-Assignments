package Classes;

import ObjectCreation.AddObject;

// Child class for managing RT panel functionality
public class RT extends TestAlgorithms {
	private AddObject objDescription;
	
    public RT(double failureRate, int panelSize, int coordDiameter) {
        super(failureRate, panelSize, coordDiameter);
    }
    
    public RT() {
    	super(0,0,0);
    }
    
    public Object generateObject() {
    	return Mutations.createObject(objDescription);
    }
    
    public void setObjDescription(AddObject objDescription) {
    	this.objDescription = objDescription;
    }
}
