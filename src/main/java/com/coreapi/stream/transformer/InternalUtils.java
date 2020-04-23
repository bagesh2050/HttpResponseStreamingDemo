package com.coreapi.stream.transformer;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Version;


public final class InternalUtils {

    private InternalUtils() {

    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static void closeQuietly(InputStream stream) {
        try {
            stream.close();
        } catch (Exception ex) {
        	throw InternalUtils.toRuntimeException(ex);
        }
    }

    public static RuntimeException toRuntimeException(Throwable throwable) {
        return throwable instanceof RuntimeException ? (RuntimeException) throwable
                : new RuntimeException(throwable);
    }

    public static RuntimeException toRuntimeException(String message, Throwable throwable) {
        return message == null ? toRuntimeException(throwable)
                : new RuntimeException(message, throwable);
    }

    public static final class StringUtils {

        public static final String EMPTY = "";

        public static final String[] EMPTY_ARRAY = new String[0];

        private StringUtils() {

        }

        public static String correctEmpty(final String value) {
            return correctEmpty(value, EMPTY);
        }

        public static String correctEmpty(final String value, final String valueForEmpty) {
            return isEmpty(value) ? valueForEmpty : value;
        }

        public static boolean isEmpty(final String value) {
            if (value == null) {
                return true;
            }

            int len = value.length();
            if (len == 0) {
                return true;
            }

            if (value.charAt(0) > ' ') {
                return false;
            }

            for (int i = 1; i < len; i++) {
                if (value.charAt(i) > ' ') {
                    return false;
                }
            }

            return true;
        }

        public static int length(String value) {
            return value == null ? 0 : value.length();
        }

        public static String joinWithSpace(String... strings) {
            return join(strings, " ");
        }

        public static String join(String[] strings, String separator) {
            return join(Arrays.asList(strings), separator);
        }

        public static String join(List<String> strings, String separator) {
            int size = CollectionUtils.size(strings);

            if (size == 0) {
                return EMPTY;
            }

            StringBuilder result = new StringBuilder(
                    size * (strings.get(0).length() + separator.length()));

            if (!isEmpty(strings.get(0))) {
                result.append(strings.get(0));
            }

            for (int i = 1; i < size; i++) {
                if (!isEmpty(strings.get(i))) {
                    result.append(separator).append(strings.get(i));
                }
            }
            return result.toString();
        }

        public static final class Joiner {

            private final String separator;

            private Joiner(String separator) {
                this.separator = separator;
            }

            public static Joiner on(String separator) {
                return new Joiner(separator);
            }

            public String join(String... strings) {
                return InternalUtils.StringUtils.join(strings, separator);
            }

        }

        public static String[] splitByDot(String value) {
            return split(value, "\\.");
        }

        public static String[] splitBySpace(String value) {
            return split(value, "\\s+");
        }

        public static String[] split(String value, String regExpression) {
            String result = value == null ? EMPTY : value.trim();
            return result.length() == 0 ? EMPTY_ARRAY : result.split(regExpression);
        }

        public static String addSuffixIfNot(String value, char suffix) {
            if (isEmpty(value)) {
                return value;
            }

            return value.charAt(value.length() - 1) == suffix ? value : value + suffix;
        }

        public static int toInt(final String str, final int defaultValue) {
            return toInteger(str, defaultValue);
        }

        public static Integer toInteger(final String str, final Integer defaultValue) {
            if (str == null) {
                return defaultValue;
            }
            try {
                return Integer.valueOf(str);
            } catch (NumberFormatException nfe) {
                return defaultValue;
            }
        }

    }

    public static final class CollectionUtils {

        private CollectionUtils() {

        }

        public static <T> T first(List<T> items) {
            return isEmpty(items) ? null : items.get(0);
        }

        public static boolean isEmpty(Collection<?> collection) {
            return collection == null || collection.isEmpty();
        }

        public static boolean isEmpty(Object[] array) {
            return array == null || array.length == 0;
        }

        public static boolean isEmptyEllipsis(Object[] array) {
            return isEmpty(array) || isOneNull(array);
        }

        public static <T> List<T> correctToEmpty(List<T> list) {
            return list == null ? Collections.<T> emptyList() : list;
        }

        public static int size(Collection<?> collection) {
            return collection == null ? 0 : collection.size();
        }

        public static <T> int size(T[] array) {
            return array == null ? 0 : array.length;
        }

        public static <E> List<E> newArrayList() {
            return new ArrayList<>();
        }

        public static <E> List<E> newArrayListWithCapacity(int size) {
            return new ArrayList<>(size);
        }

        public static <K, V> Map<K, V> newHashMap() {
            return new HashMap<>();
        }

        public static <K> Set<K> newHashSet() {
            return new HashSet<>();
        }

        public static <T> T[] correctOneNullToEmpty(T[] array) {
            return isOneNull(array) ? newArray(array, 0) : array;
        }

        public static boolean isOneNull(Object[] array) {
            return size(array) == 1 && array[0] == null;
        }

        public static <T> T[] newArray(T[] reference, int length) {
            Class<?> type = reference.getClass().getComponentType();
            @SuppressWarnings("unchecked")
            T[] result = (T[]) Array.newInstance(type, length);
            return result;
        }

    }

    public static final class ClassUtils {

        private ClassUtils() {

        }

        /**
         * Create a new object of a class.
         *
         * @param classToInstantiate
         *            a class of an object
         */
        public static Object newInstance(Class<?> classToInstantiate) {
            try {
                return classToInstantiate.newInstance();
            } catch (Exception ex) {
            	throw InternalUtils.toRuntimeException(ex); 
            }
        }

        public static Class<?> classForNameFromContext(String className) {
            return classForName(className, contextClassLoader());
        }

        public static Class<?> classForName(String className, ClassLoader loader) {
            try {
                return Class.forName(className, true, loader);
            } catch (ClassNotFoundException ex) {
                throw InternalUtils.toRuntimeException(ex);
            }
        }

        public static ClassLoader contextClassLoader() {
            return Thread.currentThread().getContextClassLoader();
        }

        public static ClassLoader staticClassLoader() {
            return ClassUtils.class.getClassLoader();
        }

    }

    public static final class Asserts {

        private Asserts() {

        }

        public static void isTrue(boolean expression, String message) {
            if (!expression) {
                throw new IllegalArgumentException(message);
            }
        }

        public static void fail(String message) {
            isTrue(false, message);
        }

    }

    public static final class HibernateUtils {

        private static final int HIBERNATE_5_MAJOR_VERSION = 5;

        private HibernateUtils() {

        }

        public static boolean isHibernate4Used() {
            return HibernateUtils
                    .getMajorVersion(HIBERNATE_5_MAJOR_VERSION) < HIBERNATE_5_MAJOR_VERSION;
        }

        public static int getMajorVersion(int returnOnError) {
            String[] version = StringUtils.splitByDot(Version.getVersionString());

            return CollectionUtils.isEmpty(version) ? returnOnError
                    : StringUtils.toInt(version[0], returnOnError);
        }

    }

}
