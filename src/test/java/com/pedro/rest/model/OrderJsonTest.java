package com.pedro.rest.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JsonTest
public class OrderJsonTest {
    @Autowired
    private JacksonTester<Order> json;

    @Autowired
    private JacksonTester<List<Order>> jsonList;

    private final Order order1 = new Order("iphone", Status.IN_PROGRESS);

    private final Order order2 = new Order("notebook", Status.CANCELLED);

    private final List<Order> orders = List.of(order1, order2);

    @Test
    void shouldSerializeToOrder() throws IOException {
        assertThat(json.write(order1)).isEqualToJson("order.json");
    }

    @Test
    void shouldSerializeToOrderList() throws IOException {
        assertThat(jsonList.write(orders)).isEqualToJson("order-list.json");
    }

    @Test
    void shouldParseToOrder() throws IOException {
        var jsonValue = """
                  {
                  "id": null,
                  "description": "iphone",
                  "status": "IN_PROGRESS"
                  }
                """;
        assertThat(json.parseObject(jsonValue)).isEqualTo(order1);
    }

    @Test
    void shouldParseToOrderList() throws IOException {
        var jsonValue = """
                  [
                    {
                      "id": null,
                      "description": "iphone",
                      "status": "IN_PROGRESS"
                    },
                    {
                      "id": null,
                      "description": "notebook",
                      "status": "CANCELLED"
                    }
                  ]
                """;
        assertThat(jsonList.parseObject(jsonValue)).isEqualTo(orders);
    }
}
