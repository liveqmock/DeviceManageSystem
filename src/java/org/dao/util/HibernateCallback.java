package org.dao.util;

import org.hibernate.Session;

public interface HibernateCallback {

    Object doInHibernate(Session session);
}
