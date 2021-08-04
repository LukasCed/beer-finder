package com.whatever.beerfinder.dao.interfaces;

import java.util.List;

public interface DataDao<T> {

    List<T> list();

}
