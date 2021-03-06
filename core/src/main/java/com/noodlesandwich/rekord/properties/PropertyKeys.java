package com.noodlesandwich.rekord.properties;

import java.util.HashSet;
import java.util.Set;
import com.noodlesandwich.rekord.implementation.KeySet;
import com.noodlesandwich.rekord.keys.Key;
import com.noodlesandwich.rekord.keys.Keys;

public final class PropertyKeys {
    private PropertyKeys() { }

    public static <T> Keys<T> keysFrom(Properties<T> properties) {
        Set<Keys<T>> keys = new HashSet<>();
        for (Property<T, ?> property : properties) {
            Key<T, ?> key = property.key();
            if (key.test(properties)) {
                keys.add(key);
            }
        }
        return KeySet.from(keys);
    }

    public static <T> void checkAcceptabilityOf(Properties<T> properties, Keys<T> acceptedKeys) {
        for (Key<T, ?> key : keysFrom(properties)) {
            if (!acceptedKeys.contains(key)) {
                throw new UnacceptableKeyException(key);
            }
        }
    }
}
