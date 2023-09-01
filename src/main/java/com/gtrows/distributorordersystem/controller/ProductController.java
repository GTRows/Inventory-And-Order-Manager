package com.gtrows.DistributorOrderSystem.controller;

import com.gtrows.DistributorOrderSystem.model.Product;
import com.gtrows.DistributorOrderSystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController extends GenericController<Product> {

    @Autowired
    public ProductController(ProductService productService) {
        super(productService);
    }

    // TODO: - Fix Put and Delete methods
    // TODO: - Automatic generation of id
}
