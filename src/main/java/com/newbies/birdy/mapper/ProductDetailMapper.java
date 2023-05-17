package com.newbies.birdy.mapper;

import com.newbies.birdy.dto.ProductDetailDTO;
import com.newbies.birdy.entities.Product;
import com.newbies.birdy.entities.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDetailMapper {

    ProductDetailMapper INSTANCE = Mappers.getMapper(ProductDetailMapper.class);

    @Mapping(target = "productName", source = "productDetail.productName")
    @Mapping(target = "productId", source = "productDetail.id")
    ProductDetailDTO toDTO(ProductDetail productDetail);

    @Mapping(target = "productDetail", source = "productId", qualifiedByName = "mapProduct")
    ProductDetail toEntity(ProductDetailDTO dto);

    @Named("mapProduct")
    default Product mapProduct(Integer id) {
        Product p = new Product();
        p.setId(id);
        return p;
    }
}
