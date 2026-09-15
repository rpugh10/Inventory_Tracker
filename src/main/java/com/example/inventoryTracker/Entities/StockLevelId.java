package com.example.inventoryTracker.Entities;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLevelId {
    private Long product_id;

    private Long location_id;

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StockLevelId)) return false;

        StockLevelId that = (StockLevelId) o;

        return Objects.equals(product_id, that.product_id)
                && Objects.equals(location_id, that.location_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, location_id);
    }
}
