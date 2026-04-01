package duckcorp.order;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.stock.Stock;

/**
 * Commande client : demande un certain nombre de canards d'un type donné.
 *
 * TODO (Ex4) :
 *   - Implémentez getTotalValue(), canBeFulfilled(), fulfill()
 *   - Implémentez equals() et hashCode() basés sur l'id
 *
 * Les getters et tick() sont fournis.
 * @author GOUDY Florian <goudyf@3il.fr>
 */
public class Order {

    private static int counter = 0;

    private final String id;
    private final DuckType duckType;
    private final int quantity;
    private final double pricePerUnit;
    private int turnsRemaining;
    private OrderStatus status;

    /**
     * Constructeur fourni.
     */
    public Order(DuckType duckType, int quantity, double pricePerUnit, int turnsRemaining) {
        this.id             = String.format("CMD-%03d", ++counter);
        this.duckType       = duckType;
        this.quantity       = quantity;
        this.pricePerUnit   = pricePerUnit;
        this.turnsRemaining = turnsRemaining;
        this.status         = OrderStatus.PENDING;
    }

    // --- Getters fournis ---

    public String      getId()              { return id; }
    public DuckType    getDuckType()        { return duckType; }
    public int         getQuantity()        { return quantity; }
    public double      getPricePerUnit()    { return pricePerUnit; }
    public int         getTurnsRemaining()  { return turnsRemaining; }
    public OrderStatus getStatus()          { return status; }

    // --- Méthode fournie ---

    /**
     * Décrémente le délai restant. Si le délai atteint 0, la commande expire.
     * Appelée automatiquement par Game à chaque tour. Ne pas modifier.
     */
    public void tick() {
        if (status == OrderStatus.PENDING) {
            turnsRemaining--;
            if (turnsRemaining <= 0) {
                status = OrderStatus.EXPIRED;
            }
        }
    }

    // --- TODO ---

    public double getTotalValue() {
        return pricePerUnit * quantity;
    }

    public boolean canBeFulfilled(Stock<? extends Duck> stock) {
        return stock.count(duckType) >= quantity;
    }

    public void fulfill() {
        status = OrderStatus.FULFILLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order other = (Order) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // --- toString fourni ---

    @Override
    public String toString() {
        return String.format("[%s] %dx %s → %.0f€/u (%.0f€) — %d tour(s) — %s",
                id, quantity, duckType.getLabel(), pricePerUnit,
                pricePerUnit * quantity, turnsRemaining, status);
    }
}
