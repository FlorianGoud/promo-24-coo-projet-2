package duckcorp.stock;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.EnumMap;
import java.util.Iterator;

/**
 * Stock générique de canards.
 *
 * TODO (Ex3) :
 *   - Implémentez remove(), count(), countDefective(), countByType()
 *
 * Les méthodes add(), getAll() et total() sont fournies.
 *
 * @param <T> type de canard stocké (doit étendre Duck)
 * @author GOUDY Florian <goudyf@3il.fr>
 */
public class Stock<T extends Duck> {

    private final List<T> items = new ArrayList<>();

    /** Ajoute un canard au stock. */
    public void add(T duck) {
        items.add(duck);
    }

    /** Retourne une vue non modifiable de tous les canards en stock. */
    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    /** Retourne le nombre total de canards en stock. */
    public int total() {
        return items.size();
    }

    // --- TODO ---

    public List<T> remove(DuckType type, int count) {
        List<T> removed = new ArrayList<>(count);
        Iterator<T> it = items.iterator();

        while (it.hasNext() && removed.size() < count) {
            T duck = it.next();
            if (duck.getType() == type) {
                removed.add(duck);
                it.remove();
            }
        }

        if (removed.size() != count) {
            throw new IllegalStateException(
                    "Stock insuffisant : demandé " + count + " " + type +
                            ", disponible : " + removed.size()
            );
        }

        return removed;
    }

    public int count(DuckType type) {
        int c = 0;
        for (T duck : items) {
            if (duck.getType() == type) c++;
        }
        return c;
    }

    public int countDefective() {
        int c = 0;
        for (T duck : items) {
            if (duck.isDefective()) c++;
        }
        return c;
    }

    public Map<DuckType, Integer> countByType() {
        Map<DuckType, Integer> map = new EnumMap<>(DuckType.class);

        for (DuckType type : DuckType.values()) {
            map.put(type, 0);
        }

        for (T duck : items) {
            DuckType type = duck.getType();
            map.put(type, map.get(type) + 1);
        }

        return map;
    }
}
