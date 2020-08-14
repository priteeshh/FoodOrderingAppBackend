package com.upgrad.FoodOrderingApp.service.common;

public class ItemType {
    public enum ItemTypeEnum {
        VEG("VEG"),

        NON_VEG("NON_VEG");

        private String value;

        ItemTypeEnum(String value) {
            this.value = value;
        }

    }
}
