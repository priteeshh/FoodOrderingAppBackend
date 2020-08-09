package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;

@Entity
@Table(name = "category_item")
public class CategoryItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    //TODO: Needs more brainstorming
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public CategoryItemEntity(ItemEntity item, CategoryEntity category) {
        this.item = item;
        this.category = category;
    }

    public ItemEntity getItem() {
        return item;
    }

    public Integer getId() {
        return id;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

}
