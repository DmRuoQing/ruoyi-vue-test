package com.ruoyi.test;

import com.ruoyi.test.domain.Fruit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestData {

    @Test
    void test01() {
        Fruit<Double> fruit = new Fruit<>();

        fruit.setPeaches(1.00);
        fruit.setPlums(2.00);
        fruit.setApricots(3.00);
        fruit.setCherries(4.00);

        double[] value = new double[]{0.9, 1.0, 0.3, 0.1};
        for (int i = 0; i < 4; i++) {
            double v = fruit.getValueAt(i) * value[i];
            fruit.setValueAt(i, v);
        }

        System.out.println(fruit);
    }
}
