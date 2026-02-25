package pl.morawski.lego_store.domain;

public enum AvailabilityType {
    IN_STORE_ONLY,
    SHIPPING_ONLY,
    IN_STORE_AND_SHIPPING,
    NOT_AVAILABLE;

    public static AvailabilityType from(int store, int warehouse) {
        boolean inStore = store > 0;
        boolean inWarehouse = warehouse > 0;

        if (inStore && inWarehouse) return IN_STORE_AND_SHIPPING;
        if (inStore) return IN_STORE_ONLY;
        if (inWarehouse) return SHIPPING_ONLY;
        return NOT_AVAILABLE;
    }
}
