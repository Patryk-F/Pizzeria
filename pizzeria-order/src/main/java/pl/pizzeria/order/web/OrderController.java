package pl.pizzeria.order.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pizzeria.order.domain.Order;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders/history")
public class OrderController {

    private final OrderServiceImpl orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrdersHistory() {
        return new ResponseEntity<>(orderService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("{id}") final Long id) {
        return orderService.findById(id)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}