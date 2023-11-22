package com.ruoyi.common.inteface;

public interface FieldIndex<T> {

    /**
     * 获取某个Index的Field的值
     *
     * @param index index-对应关系自定义(建议使用switch case)
     * @return 值
     */
    T getValueAt(int index);

    /**
     * 给某个index的属性赋值
     *
     * @param index index
     * @param value 值
     */
    void setValueAt(int index, T value);
}
