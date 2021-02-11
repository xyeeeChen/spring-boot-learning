package com.springboot.management.managementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductDTO {
    private Long id;        // 產品的ID
    private String name;    // 產品的名稱
    private Integer remain; // 產品的剩餘數量
}
