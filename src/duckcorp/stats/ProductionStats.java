package duckcorp.stats;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;
import duckcorp.order.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Suivi des statistiques de production et de vente de l'usine.
 *
 * TODO (Bonus 1) :
 *   - Implémentez recordProduction(), recordSale(),
 *     getTotalProduced(), getMostProduced()
 *
 * Les getters et le constructeur sont fournis.
 * La Map produced est protected pour permettre la sous-classe ConcurrentProductionStats.
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class ProductionStats {

    protected Map<DuckType, Integer> produced;
    protected Map<DuckType, Integer> sold;
    private double totalRevenue;
    private int totalOrders;
    private int ordersExpired;

    public ProductionStats() {
        produced = new HashMap<>();
        sold     = new HashMap<>();
        for (DuckType t : DuckType.values()) {
            produced.put(t, 0);
            sold.put(t, 0);
        }
    }

    // --- Getters fournis ---

    public double getTotalRevenue()          { return totalRevenue; }
    public int    getTotalOrders()           { return totalOrders; }
    public int    getOrdersExpired()         { return ordersExpired; }
    public Map<DuckType, Integer> getProduced() { return produced; }
    public Map<DuckType, Integer> getSold()     { return sold; }

    // --- Méthode fournie ---

    /** Incrémente le compteur de commandes expirées. Appelée par Factory. */
    public void recordExpiredOrder() {
        ordersExpired++;
    }

    // --- TODO (Bonus 1) ---

    @SuppressWarnings("unused")
    public void recordProduction(List<Duck> ducks) {
        for (Duck d : ducks) {
            DuckType type = d.getType();
            produced.put(type, produced.get(type) + 1);
        }
    }

    public void recordSale(Order order) {
        DuckType type = order.getDuckType();
        int qty = order.getQuantity();

        sold.put(type, sold.get(type) + qty);

        totalRevenue += order.getTotalValue();
        totalOrders++;
    }

    public int getTotalProduced() {
        int total = 0;
        for (int n : produced.values()) {
            total += n;
        }
        return total;
    }

    public DuckType getMostProduced() {
        DuckType best = null;
        int max = 0;

        for (Map.Entry<DuckType, Integer> e : produced.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                best = e.getKey();
            }
        }

        return best;
    }
}
