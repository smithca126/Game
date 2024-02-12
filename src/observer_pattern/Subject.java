package observer_pattern;

import game.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observers = new ArrayList<>();
    public void attach(Observer o) {
        observers.add(o);
    }
    public void notifyObservers(Entity entity, String message) {
        for (Observer o : observers) {
            o.update(entity, message);
        }
    }
}
