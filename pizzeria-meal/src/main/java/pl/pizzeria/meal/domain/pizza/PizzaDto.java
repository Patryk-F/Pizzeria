package pl.pizzeria.meal.domain.pizza;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pizzeria.meal.domain.MealDto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PizzaDto extends MealDto implements Serializable {
    private static final long serialVersionUID = 6596146530547941213L;

    private List<ToppingDto> toppings = new LinkedList<>();
}
