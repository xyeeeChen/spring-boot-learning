package com.springboot.management.managementsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@ApiModel(description = "物品資料")
public class ProductDTO {
    @ApiModelProperty(value = "序號", required = true)
    private Long id;        // 產品的ID
    @ApiModelProperty(value = "名稱", required = true)
    private String name;    // 產品的名稱
    @ApiModelProperty(value = "剩餘數量", required = true)
    private Integer remain; // 產品的剩餘數量
}
