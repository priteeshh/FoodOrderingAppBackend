package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;

/**
 * The type CategoryItem entity for category_item table.
 */
@Entity
@Table(name = "category_item")
@NamedQueries({
        @NamedQuery(name = "itemBycategoryUUID", query = "select ci from CategoryItemEntity ci where ci.category.uuid =:categoryUUID")
})
public class CategoryItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public CategoryItemEntity() {
    }

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

    public void setId(Integer id) {
        this.id = id;
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
