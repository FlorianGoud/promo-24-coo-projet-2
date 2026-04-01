package duckcorp.duck;

import java.util.Objects;

/**
 * Classe abstraite représentant un canard en plastique.
 *
 * TODO (Ex1) :
 *   - Faites implémenter l'interface Qualifiable à cette classe
 *   - Implémentez equals() et hashCode() basés uniquement sur l'id
 *   - Implémentez les méthodes abstraites dans les sous-classes
 * @author GOUDY Florian <goudyf@3il.fr>
 */
public abstract class Duck implements Qualifiable {

    private static int counter = 0;

    private final String   id;
    private final DuckType type;
    private final int      qualityScore;

    protected Duck(DuckType type, int qualityScore) {
        this.id           = type.name().charAt(0) + String.format("%04d", ++counter);
        this.type         = type;
        this.qualityScore = Math.max(0, Math.min(100, qualityScore));
    }

    public String   getId()          { return id; }
    public DuckType getType()        { return type; }
    public int      getQualityScore(){ return qualityScore; }

    public abstract double getBasePrice();
    public abstract String describe();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duck)) return false;
        Duck duck = (Duck) o;
        return id.equals(duck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — qualité : %d/100", id, describe(), qualityScore);
    }
}