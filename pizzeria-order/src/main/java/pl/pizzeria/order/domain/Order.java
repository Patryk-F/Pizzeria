package pl.pizzeria.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import pl.pizzeria.meal.domain.dinner.DinnerDto;
import pl.pizzeria.meal.domain.mapper.DinnerMapper;
import pl.pizzeria.meal.domain.mapper.MealMapper;
import pl.pizzeria.meal.domain.mapper.PizzaMapper;
import pl.pizzeria.meal.domain.pizza.PizzaDto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_history")
public class Order implements Serializable {
    private static final long serialVersionUID = -7322759631220603229L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_history_id")
    private Set<OrderItemEntity> orderItems;

    @Embedded
    private OrderDetails orderDetails;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationTime;

    private BigDecimal price;

    @Transient
    @JsonIgnore
    private Set<OrderItem> orderItemSet;

    public Order convertToEntity() {
        if(orderItems == null) {
            orderItems = new HashSet<>();
        }

        orderItemSet.forEach(orderItem -> {
            if (orderItem.getMeal() instanceof PizzaDto) {
                OrderItemEntity orderItemEntity = new OrderItemEntity(null,
                        PizzaMapper.INSTANCE.pizzaDtoToPizza((PizzaDto) orderItem.getMeal()),
                        orderItem.getQuantity());
                orderItems.add(orderItemEntity);
            } else if (orderItem.getMeal() instanceof DinnerDto) {
                OrderItemEntity orderItemEntity = new OrderItemEntity(null,
                        DinnerMapper.INSTANCE.dinnerDtoToDinner((DinnerDto) orderItem.getMeal()),
                        orderItem.getQuantity());
                orderItems.add(orderItemEntity);
            } else {
                OrderItemEntity orderItemEntity = new OrderItemEntity(null,
                        MealMapper.INSTANCE.mealDtoToMeal(orderItem.getMeal()),
                        orderItem.getQuantity());
                orderItems.add(orderItemEntity);
            }
        });

        return this;
    }
}
