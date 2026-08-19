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
    private Long productId;

    private Long locationId;

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StockLevelId)) return false;

        StockLevelId that = (StockLevelId) o;

        return Objects.equals(productId, that.productId)
                && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, locationId);
    }
}
