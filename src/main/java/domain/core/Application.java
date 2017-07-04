package domain.core;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Vasili_Spirydzionak on 7/4/2017.
 */
public abstract class Application {
    private static final Set<Object> emptySet = Collections.emptySet();

    public abstract Set<Class<?>> getCalsses();

    public Set<Object> getSingletons() {
        return emptySet;
    }
}
