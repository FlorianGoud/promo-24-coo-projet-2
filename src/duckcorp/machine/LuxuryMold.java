package duckcorp.machine;

import duckcorp.duck.Duck;
import duckcorp.duck.LuxuryDuck;
import duckcorp.duck.DuckType;

/**
 * Moule produisant des Canards de Luxe.
 */
public class LuxuryMold extends Machine {

    public static final int PURCHASE_COST    = 800;
    public static final int CAPACITY         = 2;
    public static final int MAINTENANCE_COST = 80;

    public LuxuryMold() {
        super(DuckType.LUXURY, CAPACITY, MAINTENANCE_COST);
    }

    @Override
    public Duck produceDuck() {
        int quality = computeQuality();
        return new LuxuryDuck(quality);
    }

    @Override
    public int getPurchaseCost() {
        return PURCHASE_COST;
    }

    @Override
    public String getName() {
        return "Moule de Luxe";
    }
}
