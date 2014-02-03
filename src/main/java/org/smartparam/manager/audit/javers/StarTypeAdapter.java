package org.smartparam.manager.audit.javers;

import org.javers.core.json.BasicStringTypeAdapter;
import org.smartparam.engine.core.index.Star;

/**
 * @author bartosz walacik
 */
public class StarTypeAdapter extends BasicStringTypeAdapter<Star> {

    @Override
    public String serialize(Star star) {
        return Star.SYMBOL;
    }

    @Override
    public Star deserialize(String s) {
        if (Star.SYMBOL.equals(s)) {
            return Star.star();
        }
        return null;
    }

    @Override
    public Class getValueType() {
        return Star.class;
    }
}
