package observer_pattern;

import game.Entity;

public interface Observer {
    void update(Entity entity, String message);
}
