 package com.rocky.controller;
import com.rocky.model.Product;
import com.rocky.model.ProductQuery;
import com.rocky.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('boss2','admin')")
    public List<Product> select(ProductQuery query) {
        return productService.select(query);
    }

    @PostMapping
    public Integer insert(@RequestBody Product product) {
        return productService.insert(product);
    }

    @PutMapping
    public Integer update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping
    public Integer delete(@RequestParam Integer id) {
        return productService.delete(id);
    }
}
