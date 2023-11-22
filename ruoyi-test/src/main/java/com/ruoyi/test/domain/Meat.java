package com.ruoyi.test.domain;

import com.ruoyi.common.inteface.FieldIndex;
import lombok.Data;

@Data
public class Meat implements FieldIndex<Integer> {

    @Override
    public Integer getValueAt(int index) {
        return null;
    }

    @Override
    public void setValueAt(int index, Integer value) {

    }
}
