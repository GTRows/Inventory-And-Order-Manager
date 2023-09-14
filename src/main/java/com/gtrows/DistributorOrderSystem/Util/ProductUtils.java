package com.gtrows.DistributorOrderSystem.Util;

import com.gtrows.DistributorOrderSystem.model.StoredProduct;

import java.util.List;
import java.util.Optional;

public class ProductUtils {

    public static List<StoredProduct> updateProductList(List<StoredProduct> products, String productId, int quantity) {
        Optional<StoredProduct> existingProductOpt = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (existingProductOpt.isPresent()) {
            StoredProduct existingProduct = existingProductOpt.get();
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {
            products.add(new StoredProduct(productId, quantity));
        }
        return products;
    }
}

