package domo.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Marco Versari
 *
 */
public final class SchedulerImpl implements Scheduler {
	private static final int DAY_TIME = 24;
	private final Map<Integer, List<Boolean>> listSensor;
	private static SchedulerImpl instance;
	
	private SchedulerImpl() {
		listSensor = new HashMap<>();
	}
	
	/**
	 * Get class instance.
	 * @return the class instance.
	 */
	public static SchedulerImpl getInstance() {
		synchronized (SchedulerImpl.class) {
			if (instance == null) {
				instance = new SchedulerImpl();
			}
		}
		return instance;
	}
	
	private void addToMap(final int id, final List<Boolean> tmp) {
		if (listSensor.containsKey(id)) {
			listSensor.replace(id, tmp);
		} else {
			listSensor.put(id, tmp);
		}
	}

	@Override
	public void addStartStopScheduling(final int id, final int start, final int stop, final boolean status) {
	
		final List<Boolean> tmp = new ArrayList<>(DAY_TIME);
		
		for (int i = 0; i < tmp.size(); i++) {		
			if (i == start) {
				while (i != stop) {
					tmp.add(status);
					i++;
				}
			}				
			tmp.add(!status);
		}			
		
		addToMap(id, tmp);
	}

	@Override
	public void addDurationScheduling(final int id, final int start, final int duration, final boolean status) {
		final List<Boolean> tmp = new ArrayList<>(DAY_TIME);
		
		for (int i = 0; i < tmp.size(); i++) {		
			if (i == start) {
				for (int a = 0; a < duration && a < tmp.size(); a++) {
					tmp.add(status);
					i++;
				}
			}				
			tmp.add(!status);
		}	
		
		addToMap(id, tmp);
	}

	@Override
	public void addHourScheduling(final int id, final int hour, final boolean status) {
		final List<Boolean> tmp = new ArrayList<>(DAY_TIME);
		
		for (int i = 0; i < tmp.size(); i++) {		
			if (i == hour) {
				tmp.add(status);
			}				
			tmp.add(!status);
		}	
		
		addToMap(id, tmp);
	}

	@Override
	public void removeScheduling(final int id) {
		listSensor.remove(id);
	}

}
