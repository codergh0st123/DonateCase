package com.jodexindustries.donatecase.spigot.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class OptionalPluginApi {

    private OptionalPluginApi() {
    }

    public static Object create(String className, Object... arguments) {
        try {
            Class<?> type = Class.forName(className);

            for (Constructor<?> constructor : type.getConstructors()) {
                if (constructor.getParameterCount() == arguments.length
                        && matches(constructor.getParameterTypes(), arguments)) {
                    return constructor.newInstance(arguments);
                }
            }
        } catch (ReflectiveOperationException exception) {
            return null;
        }

        return null;
    }

    public static Object invokeStatic(String className, String methodName, Object... arguments) {
        try {
            Class<?> type = Class.forName(className);
            Method method = findMethod(type, methodName, true, arguments);
            return method == null ? null : method.invoke(null, arguments);
        } catch (ReflectiveOperationException exception) {
            return null;
        }
    }

    public static Object invoke(Object target, String methodName, Object... arguments) {
        if (target == null) {
            return null;
        }

        try {
            Method method = findMethod(target.getClass(), methodName, false, arguments);
            return method == null ? null : method.invoke(target, arguments);
        } catch (ReflectiveOperationException exception) {
            return null;
        }
    }

    private static Method findMethod(Class<?> type, String methodName, boolean requireStatic, Object[] arguments) {
        for (Method method : type.getMethods()) {
            if (!method.getName().equals(methodName)
                    || method.getParameterCount() != arguments.length
                    || Modifier.isStatic(method.getModifiers()) != requireStatic
                    || !matches(method.getParameterTypes(), arguments)) {
                continue;
            }

            return method;
        }

        return null;
    }

    private static boolean matches(Class<?>[] parameterTypes, Object[] arguments) {
        for (int index = 0; index < parameterTypes.length; index++) {
            Object argument = arguments[index];

            if (argument == null) {
                continue;
            }

            Class<?> parameterType = wrap(parameterTypes[index]);

            if (!parameterType.isInstance(argument)) {
                return false;
            }
        }

        return true;
    }

    private static Class<?> wrap(Class<?> type) {
        if (!type.isPrimitive()) {
            return type;
        }

        if (type == boolean.class) {
            return Boolean.class;
        }

        if (type == byte.class) {
            return Byte.class;
        }

        if (type == short.class) {
            return Short.class;
        }

        if (type == int.class) {
            return Integer.class;
        }

        if (type == long.class) {
            return Long.class;
        }

        if (type == float.class) {
            return Float.class;
        }

        if (type == double.class) {
            return Double.class;
        }

        if (type == char.class) {
            return Character.class;
        }

        return type;
    }
}
