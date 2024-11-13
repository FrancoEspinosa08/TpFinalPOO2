package observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	
	List<IObserver> observers = new ArrayList<>();

    public List<IObserver> getObservers() {
        return observers;
    }

    public void setObservers(List<IObserver> observers) {
        this.observers = observers;
    }

    public void attach(IObserver observer) {
        observers.add(observer);
    }

    public void detach(IObserver observer) {
        observers.remove(observer);
    }
}
