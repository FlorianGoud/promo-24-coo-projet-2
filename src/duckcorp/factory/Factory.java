package duckcorp.factory;

import duckcorp.duck.Duck;
import duckcorp.machine.Machine;
import duckcorp.order.Order;
import duckcorp.stats.ProductionStats;
import duckcorp.stock.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * L'usine du joueur. Gère le budget, les machines, le stock et la réputation.
 *
 * TODO (Ex5) :
 *   - Implémentez buyMachine(), maintainMachine(), runProduction(), fulfillOrder()
 *
 * TODO (Bonus 1) :
 *   - Implémentez endTurn()
 *
 * Le constructeur, les getters et notifyExpiredOrder() sont fournis.
 * @author GOUDY Florian <goudyf@3il.fr>
 */
public class Factory {

    private double budget;
    private double reputation;
    private final Stock<Duck> stock;
    private final List<Machine> machines;
    private final ProductionStats stats;

    public Factory(double initialBudget) {
        this.budget     = initialBudget;
        this.reputation = 100.0;
        this.stock      = new Stock<>();
        this.machines   = new ArrayList<>();
        this.stats      = new ProductionStats();
    }

    // --- Getters fournis ---

    public double         getBudget()     { return budget; }
    public double         getReputation() { return reputation; }
    public Stock<Duck>    getStock()      { return stock; }
    public List<Machine>  getMachines()   { return Collections.unmodifiableList(machines); }
    public ProductionStats getStats()     { return stats; }

    // --- Méthodes fournies ---

    public void notifyExpiredOrder() {
        reputation = Math.max(0, reputation - 5);
        stats.recordExpiredOrder();
    }

    public int computeScore() {
        return (int) (budget
                + reputation * 80
                + stats.getTotalOrders() * 200
                - stats.getOrdersExpired() * 100);
    }

    // --- TODO (Ex5) ---

    public boolean buyMachine(Machine machine) {
        if (budget < machine.getPurchaseCost()) {
            return false;
        }
        budget -= machine.getPurchaseCost();
        machines.add(machine);
        return true;
    }

    public boolean maintainMachine(Machine machine) {
        if (budget < machine.getMaintenanceCost()) {
            return false;
        }
        budget -= machine.getMaintenanceCost();
        machine.maintain();
        return true;
    }

    public List<Duck> runProduction() {
        List<Duck> produced = new ArrayList<>();

        for (Machine machine : machines) {
            for (int i = 0; i < machine.getCapacity(); i++) {
                Duck d = machine.produceDuck();
                produced.add(d);
                stock.add(d);
                stats.recordProduction(List.of(d));
            }
        }

        return produced;
    }

    public boolean fulfillOrder(Order order) {
        if (!order.canBeFulfilled(stock)) {
            return false;
        }

        List<Duck> ducks = stock.remove(order.getDuckType(), order.getQuantity());

        budget += order.getTotalValue();

        double avg = ducks.stream()
                .mapToInt(Duck::getQualityScore)
                .average()
                .orElse(0);

        if (avg >= 70) {
            reputation = Math.min(100, reputation + 3);
        } else if (avg >= 50) {
            reputation = Math.min(100, reputation + 1);
        }

        order.fulfill();
        stats.recordSale(order);

        return true;
    }

    // --- TODO (Bonus 1) ---

    public void endTurn() {
        for (Machine machine : machines) {
            machine.degrade();
            if (machine.needsMaintenance()) {
                reputation = Math.max(0, reputation - 5);
            }
        }
    }
}
