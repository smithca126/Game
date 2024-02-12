package observer_pattern;

import game.Entity;
import javafx.scene.control.TextArea;

public class EventBoxObserver implements Observer {
    private TextArea eventBox;
    public EventBoxObserver(TextArea eventBox) {
        this.eventBox = eventBox;
    }

    @Override
    public void update(Entity entity, String message) {
        if (message != null)
            eventBox.setText(message + eventBox.getText());
    }
}
