package domo.util.test;

import domo.general.Flat;

public interface DomoTest {

	public void setObserver(AbstracTestInterface testObserver);
	
	public void refresh(Flat flat);
}
