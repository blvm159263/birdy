package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ProductDTO;
import com.newbies.birdy.entities.Category;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "shopName", source = "shopProduct.shopName")
    @Mapping(target = "shopId", source = "shopProduct.id")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "categoryId", source = "category.id")
    ProductDTO toDTO(Product product);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderDetailList", ignore = true)
    @Mapping(target = "productImageList", ignore = true)
    @Mapping(target = "shopProduct", source = "shopId", qualifiedByName = "mapShop")
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    Product toEntity(ProductDTO dto);

    @Named("mapShop")
    default Shop mapShop(Integer id) {
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }

    @Named("mapCategory")
    default Category mapCategory(Integer id) {
        Category c = new Category();
        c.setId(id);
        return c;
    }
}
