package com.rocky.controller;


import com.rocky.model.Category;
import com.rocky.model.CategoryQuery;
import com.rocky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public List<Category> select(CategoryQuery query){

        return  categoryService.select(query);
    }

    @PostMapping()
    public Integer insert(@RequestBody Category category){
        return categoryService.insert(category);
    }

    @PutMapping
    public Integer update(@RequestBody Category category){
        return categoryService.update(category);
    }

    @DeleteMapping
    public Integer delete(@RequestParam Integer id){
        return categoryService.delete(id);
    }
}

