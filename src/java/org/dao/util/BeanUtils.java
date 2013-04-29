package org.dao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtils {

    protected static final Log logger = LogFactory.getLog(BeanUtils.class);

    private BeanUtils() {
    }

    public static Field getDeclaredField(Object object, String propertyName)
            throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);
        return getDeclaredField(object.getClass(), propertyName);
    }

    public static Field getDeclaredField(Class clazz, String propertyName)
            throws NoSuchFieldException {
        Assert.notNull(clazz);
        Assert.hasText(propertyName);
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass
                        .getSuperclass()) {
            try {
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
            }
        }
        throw new NoSuchFieldException("No such field: " + clazz.getName()
                + '.' + propertyName);
    }

    public static Object forceGetProperty(Object object, String propertyName)
            throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = getDeclaredField(object, propertyName);

        boolean accessible = field.isAccessible();
        field.setAccessible(true);

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.info("error wont' happen");
        }
        field.setAccessible(accessible);
        return result;
    }

    public static void forceSetProperty(Object object, String propertyName,
            Object newValue) throws NoSuchFieldException {
        Assert.notNull(object);
        Assert.hasText(propertyName);

        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(object, newValue);
        } catch (IllegalAccessException e) {
        }
        field.setAccessible(accessible);
    }

    public static List<Field> getFieldsByType(Object object, Class type) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isAssignableFrom(type)) {
                list.add(field);
            }
        }
        return list;
    }

    public static Class getPropertyType(Class type, String name)
            throws NoSuchFieldException {
        return getDeclaredField(type, name).getType();
    }
}
