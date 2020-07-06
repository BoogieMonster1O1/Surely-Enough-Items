package io.github.boogiemonster1o1.sei.util;

public interface SEIInventory {
    default void updatePage(){}

    default void updateButtonEnabled(){}

    default void setPageNum(int pageNum){}

    default int getPageNum(){
        return 0;
    }
}
