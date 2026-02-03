package com.poly.lab7.report;

import com.poly.lab7.entity.Category;

public interface Report {

    Category getGroup();

    Double getSum();

    Long getCount();
}
