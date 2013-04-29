package org.dao.util;

import org.hibernate.Session;

public interface HibernateDaoHandler {

    Session getSession();
}
