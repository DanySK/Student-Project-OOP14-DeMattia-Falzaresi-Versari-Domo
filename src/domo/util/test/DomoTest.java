package domo.util.test;

import domo.general.Flat;

public interface DomoTest {

	public void setObserver(AbstractTestObserver testObserver);
	
	public void refresh(Flat flat);
}
