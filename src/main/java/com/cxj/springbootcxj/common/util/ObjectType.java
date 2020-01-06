package com.cxj.springbootcxj.common.util;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author : ChuXianJie
 * @date : 2019-12-30 14:26
 */
public class ObjectType {

    private static final Log logger = LogFactory.getLog(ObjectType.class);

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String) value).length() == 0;
        } else if (value instanceof Collection) {
            return ((Collection) value).size() == 0;
        } else if (value instanceof Map) {
            return ((Map) value).size() == 0;
        } else if (value instanceof CharSequence) {
            return ((CharSequence) value).length() == 0;
        } else if (value instanceof IsEmpty) {
            return ((IsEmpty) value).isEmpty();
        } else if (value instanceof Boolean) {
            return false;
        } else if (value instanceof Number) {
            return false;
        } else if (value instanceof Character) {
            return false;
        } else if (value instanceof java.util.Date) {
            return false;
        } else {
            logger.info("In ObjectType.isEmpty(Object value) returning false for " + value.getClass() + " Object.");
        }

        return false;
    }
}
