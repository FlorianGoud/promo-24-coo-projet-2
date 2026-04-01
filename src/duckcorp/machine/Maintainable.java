package duckcorp.machine;

/**
 * Interface représentant une machine pouvant être entretenue.
 *
 * TODO (Ex2) :
 *   - Implémentez needsMaintenance() et getConditionLabel() (méthodes default)
 * @author GOUDY Florian <goudyf@3il.fr>
 */
public interface Maintainable {

    /** Retourne l'état de la machine (entre 0 et 100). */
    int getCondition();

    /** Effectue une opération de maintenance sur la machine. */
    void maintain();

    /**
     * Retourne true si la machine nécessite une maintenance (condition < 30).
     * TODO : implémentez cette méthode default.
     */
    default boolean needsMaintenance() {
        return getCondition() < 30;
    }

    /**
     * Retourne un libellé décrivant l'état de la machine :
     *   condition >= 80 -> "Parfait" / >= 50 -> "Correct" / >= 30 -> "Usé" / < 30 -> "Critique"
     * TODO : implémentez cette méthode default.
     */
    default String getConditionLabel() {
        int c = getCondition();

        if (c >= 80) return "Parfait";
        if (c >= 50) return "Correct";
        if (c >= 30) return "Usé";
        return "Critique";
    }
}
