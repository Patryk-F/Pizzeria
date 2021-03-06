package pl.pizzeria.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {
    private static final long serialVersionUID = 221525872329117812L;

    public static OrderDetails from(String name, String comment) {
        return new OrderDetails(name, comment);
    }

    private String name;
    private String comment;
}
