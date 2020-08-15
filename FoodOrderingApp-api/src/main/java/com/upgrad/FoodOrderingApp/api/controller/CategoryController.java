package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemList.ItemTypeEnum;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CategoryNotFoundException {

        List<CategoryEntity> categoryEntities = categoryService.getAllCategoriesOrderByName();
        List<CategoryListResponse> categoriesList = new LinkedList<>();

        categoryEntities.forEach(catEnt ->
                categoriesList.add(new CategoryListResponse()
                        .categoryName(catEnt.getCategoryName())
                        .id(UUID.fromString(catEnt.getUuid())))
        );

        return new ResponseEntity<>(new CategoriesListResponse().categories(categoriesList), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getAllItemsForCategory(@RequestHeader("authorization") final String authorization, @PathVariable("category_id") String categoryId) throws AuthorizationFailedException, CategoryNotFoundException {

        String authToken = authorization.split("Bearer ")[1];
        customerService.getAuthCustomer(authToken);

        List<CategoryItemEntity> CategoryEntity=categoryService.getCategoryById(categoryId);

        List<ItemList> itemLists = new ArrayList<>();
        CategoryEntity.forEach(ele -> {
            ItemList itemList = new ItemList()
                    .itemName(ele.getItem().getItemName())
                    .itemType(ItemTypeEnum.values()[Integer.parseInt(ele.getItem().getType())])
                    .price(ele.getItem().getPrice())
                    .id(UUID.fromString(ele.getItem().getUuid()));
            itemLists.add(itemList);
        });
        CategoryDetailsResponse catDetRes = new CategoryDetailsResponse()
                .itemList(itemLists)
                .categoryName(CategoryEntity.get(0).getCategory().getCategoryName())
                .id(UUID.fromString(CategoryEntity.get(0).getCategory().getUuid()));

        return new ResponseEntity<>(catDetRes, HttpStatus.OK);

    }

}
