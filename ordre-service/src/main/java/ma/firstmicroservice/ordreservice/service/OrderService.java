package ma.firstmicroservice.ordreservice.service;

import lombok.AllArgsConstructor;
import ma.firstmicroservice.ordreservice.dto.OrderRequest;
import ma.firstmicroservice.ordreservice.entities.Order;
import ma.firstmicroservice.ordreservice.entities.OrderItems;
import ma.firstmicroservice.ordreservice.mappers.Mapper;
import ma.firstmicroservice.ordreservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private Mapper mapper;
    private OrderRepository orderRepository;
    public String saveOrder(OrderRequest orderRequest){
        // 1. Convert OrderRequest to Order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        // 2. Convert OrderItemDTO to OrderItem
        List<OrderItems> orderItemsList = orderRequest.getOrderItemsdto()
                .stream().map(mapper::toOrderItems).toList();
        order.setOrderItems(orderItemsList);
        List<String> skuCodes = order.getOrderItems().stream()
                .map(OrderItems::getSkuCode).toList();
        orderRepository.save(order);
        return "Done!";
    }
}
