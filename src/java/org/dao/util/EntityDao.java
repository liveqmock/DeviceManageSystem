package org.dao.util;

import java.io.Serializable;
import java.util.List;

public interface EntityDao<T> {

    T getById(Serializable id);

    List<T> getAll();

    void save(T newInstance);

    void remove(T transientObject);

    void removeById(Serializable id);

    String getIdName(Class clazz);
}
