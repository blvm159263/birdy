package com.newbies.birdy.services.impl;

import com.newbies.birdy.dto.OrdersChartDTO;
import com.newbies.birdy.dto.ProductsChartDTO;
import com.newbies.birdy.dto.ShipmentDTO;
import com.newbies.birdy.dto.ShopDTO;
import com.newbies.birdy.entities.Account;
import com.newbies.birdy.entities.OrderState;
import com.newbies.birdy.entities.Shipment;
import com.newbies.birdy.entities.Shop;
import com.newbies.birdy.exceptions.entity.EntityNotFoundException;
import com.newbies.birdy.mapper.ShipmentMapper;
import com.newbies.birdy.mapper.ShopMapper;
import com.newbies.birdy.repositories.*;
import com.newbies.birdy.services.ShopService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<ShopDTO> listByShopName(String name, Boolean status) {
        List<Shop> shopList = shopRepository.findByShopNameContainingAndStatus(name, status);
        return shopList.stream().map(ShopMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public ShopDTO getShopById(Integer id) {
        return ShopMapper.INSTANCE.toDTO(shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID shop not found")));
    }

    @Override
    public List<ShopDTO> listAllShop(Boolean status) {
        List<Shop> shopList = shopRepository.findByStatus(status);
        return shopList.stream().map(ShopMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public Map<List<ShopDTO>, Integer> listByNameAndStatusWithPaging(String name, Boolean status, Pageable pageable) {
        Map<List<ShopDTO>, Integer> pair = new HashMap<>();
        Page<Shop> shops = shopRepository.findByStatusAndShopNameContaining(status, name, pageable);
        pair.put(shops.stream().map(ShopMapper.INSTANCE::toDTO).toList(), shops.getTotalPages());
        return pair;
    }

    @Override
    public String getShopAddress(Integer shopId) {
        return shopRepository.findByIdAndStatus(shopId, true).getAddress();
    }

    @Override
    public List<ShipmentDTO> listShipmentByShopId(Integer shopId, Boolean status) {
        Shop shop = new Shop();
        shop.setId(shopId);
        List<Shipment> list = shipmentRepository.
                findByShopShipmentAndStatus(shop, status);
        return list.stream().map(ShipmentMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<Integer> getAllYearsForProductsChart(Integer shopId) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        return productRepository.findDistinctYear(shop);
    }

    @Override
    public ProductsChartDTO getDataForProductsChart(Integer shopId, Integer year) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);

        LocalDate localDate;
        LocalDate endLocalDate;

        List<Integer> counts = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            localDate = LocalDate.of(year, month, 1);
            endLocalDate = localDate.withDayOfMonth(localDate.lengthOfMonth());

            Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            long count = productRepository.countByShopProductAndStateAndStatusAndCreateDateBetween(shop, 1, true, startDate, endDate);
            counts.add((int) count);
        }

        localDate = LocalDate.of(year, 1, 1);
        endLocalDate = localDate.withDayOfYear(localDate.lengthOfYear());

        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        long allInYear = productRepository.countByShopProductAndStateAndStatusAndCreateDateBetween(shop, 1, true, startDate, endDate);
        long allBirds = productRepository.countByShopProductAndStateAndStatusAndCreateDateBetweenAndCategory(shop, 1, true, startDate, endDate, categoryRepository.findByIdAndStatus(1, true));
        long allAccessories = productRepository.countByShopProductAndStateAndStatusAndCreateDateBetweenAndCategory(shop, 1, true, startDate, endDate, categoryRepository.findByIdAndStatus(2, true));
        long allFood = productRepository.countByShopProductAndStateAndStatusAndCreateDateBetweenAndCategory(shop, 1, true, startDate, endDate, categoryRepository.findByIdAndStatus(3, true));

        ProductsChartDTO productsChartDTO = new ProductsChartDTO();
        productsChartDTO.setChartData(counts);
        productsChartDTO.setAllInYear(allInYear);
        productsChartDTO.setAllBirds(allBirds);
        productsChartDTO.setAllAccessories(allAccessories);
        productsChartDTO.setAllFood(allFood);

        return productsChartDTO;
    }

    @Override
    public List<Integer> getAllYearsForOrdersChart(Integer shopId) {
        List<OrderState> states = Arrays.asList(OrderState.DONE, OrderState.CANCELED);
        return orderRepository.getAllYearsForOrdersChart(shopId, states);
    }

    @Override
    public OrdersChartDTO getDataForOrdersChart(Integer shopId, Integer year) {
        Shop shop = shopRepository.findByIdAndStatus(shopId, true);
        List<Shipment> shipments = shipmentRepository.findByShopShipmentAndStatus(shop, true);

        OrdersChartDTO ordersChartDTO = new OrdersChartDTO();

        List<Integer> dones = new ArrayList<>();
        List<Integer> cancels = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDate localDate = LocalDate.of(year, month, 1);
            LocalDate endLocalDate = localDate.withDayOfMonth(localDate.lengthOfMonth());

            Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            long countDone = orderRepository.countByShipmentOrderInAndStateAndCreateDateBetweenAndStatus(shipments, OrderState.DONE, startDate, endDate, true);
            dones.add((int) countDone);

            long countCanceled = orderRepository.countByShipmentOrderInAndStateAndCreateDateBetweenAndStatus(shipments, OrderState.CANCELED, startDate, endDate, true);
            cancels.add((int) countCanceled);
        }

        ordersChartDTO.setDone(dones);
        ordersChartDTO.setCanceled(cancels);
        return ordersChartDTO;
    }

    @Override
    public Integer updateShopProfile(Integer id, String shopName, String ava) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ID shop not found"));
        shop.setShopName(shopName);
        shop.setAvatarUrl(ava);
        return shopRepository.save(shop).getId();
    }

    @Override
    public ShopDTO getShopByPhoneNumber(String phoneNumber, Boolean status) {
        Account account = accountRepository
                .findByPhoneNumberAndStatus(phoneNumber, status)
                .orElseThrow(() -> new EntityNotFoundException("Account not found!"));
        return ShopMapper.INSTANCE.toDTO(account.getShop());
    }
}
