package com.ruoyi.test.domain;

import com.ruoyi.common.inteface.FieldIndex;
import lombok.Data;

@Data
public class Fruit<T> implements FieldIndex<T> {

    // 桃
    private T peaches;

    // 李
    private T plums;

    // 杏
    private T apricots;

    // 樱桃
    private T cherries;

    @Override
    public T getValueAt(int index) {
        switch (index) {
            case 0: return getPeaches();
            case 1: return getPlums();
            case 2: return getApricots();
            case 3: return getCherries();
            // 其他情况
            default: throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    @Override
    public void setValueAt(int index, T value) {
        switch (index) {
            case 0: setPeaches(value); break;
            case 1: setPlums(value); break;
            case 2: setApricots(value); break;
            case 3: setCherries(value); break;
            // 其他情况
            default: throw new IllegalArgumentException("Invalid index: " + index);
        }
    }
}
