package ch.framedev.simplejavautils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * / This Plugin was Created by FrameDev
 * / Package: de.framedev.javautils
 * / ClassName ReflectionUtils
 * / Date: 26.09.21
 * / Project: JavaUtils
 * / Copyrighted by FrameDev
 * <p>
 * This Class can be used for Reflection in Java
 * Please mention the use of this Class {@link ReflectionUtils}
 */

@SuppressWarnings({"SpellCheckingInspection","unused"})
public class ReflectionUtils {

    private final Logger logger = Logger.getLogger("ReflectionUtils");

    private boolean accessible = false;

    /**
     * This Class can be used for Java Reflection
     * Please mention the use of this Class {@link ReflectionUtils}
     */
    public ReflectionUtils() {
    }

    /**
     * This Class can be used for Java Reflection
     * Please mention the use of this Class {@link ReflectionUtils}
     */
    public ReflectionUtils(boolean accessible) {
        this.accessible = accessible;
    }

    /**
     * Return the ClassName
     * @param class_ the Class
     * @return return the ClassName (Package and Name as example [de.framedev.javaproject.main.Main])
     */
    public String getClassName(Class<?> class_) {
        return class_.getPackage().getName() + "." + class_.getSimpleName();
    }

    /**
     * Return the ClassName
     * @param of the Object
     * @return return the ClassName (Package and Name as example [de.framedev.javaproject.main.Main])
     */
    public Object getEnumValue(String className, String of) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        assert cls != null;
        for (Object obj : cls.getEnumConstants()) {
            try {
                Method m = cls.getMethod("valueOf", String.class);
                m.setAccessible(accessible);
                return m.invoke(obj, of);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                logger.log(Level.SEVERE,"Error", ex);
            }
        }
        return null;
    }

    /**
     * Return the Enume Values
     * @param className the Class Name
     * @return return the Enum Values
     */
    public Object[] getEnumValues(String className) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        assert cls != null;
        if (cls.isEnum()) {
            return cls.getEnumConstants();
        }
        return null;
    }

    /**
     * Retrieves you the selected Field
     * @param className the Class Name
     * @param fieldName the Field Name
     * @return return the Field
     */
    public Field getField(String className, String fieldName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field method : cls.getDeclaredFields()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(fieldName)) {
                    return method;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves you the selected Field from the Upper Class
     * @param className the Class Name
     * @param fieldName the Field Name
     * @return return the Field
     */
    public Field getFieldSuperClass(String className, String fieldName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field method : cls.getSuperclass().getDeclaredFields()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(fieldName)) {
                    return method;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves if the Constructor has the Annotation
     * @param constructor the Constructor
     * @param class__ the Annotation
     * @return return true if the Constructor has the Annotation
     */
    public boolean hasConstructorAnnotation(Constructor<?> constructor, Class<? extends Annotation> class__) {
        constructor.setAccessible(accessible);
        return constructor.getAnnotation(class__) != null;
    }

    /**
     * Retrieves if the Class has the Annotation
     * @param className the ClassName
     * @param class__ the Annotation
     * @return return true if the Class has the Annotation
     */
    public boolean hasClassAnnotation(String className, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }

        try {
            assert cls != null;
            return cls.getDeclaredAnnotation(class__) != null;
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves if the Class has the Annotation from the Upper Class
     * @param className the ClassName
     * @param class__ the Annotation
     * @return return true if the Class has the Annotation
     */
    public boolean hasClassAnnotationSuperClass(String className, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }

        try {
            assert cls != null;
            return cls.getSuperclass().getDeclaredAnnotation(class__) != null;
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves if the Field has the Annotation
     * @param className the ClassName
     * @param fieldName the Field Name
     * @param class__ the Annotation
     * @return return true if the Field has the Annotation
     */
    public boolean hasFieldAnnotation(String className, String fieldName, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    return field.getAnnotation(class__) != null;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves if the Field has the Annotation from the Upper Class
     * @param className the ClassName
     * @param fieldName the Field Name
     * @param class__ the Annotation
     * @return return true if the Field has the Annotation
     */
    public boolean hasFieldAnnotationSuperClass(String className, String fieldName, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field field : cls.getSuperclass().getDeclaredFields()) {
                field.setAccessible(accessible);
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    return field.getAnnotation(class__) != null;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves the Annotations from the Field
     * @param className the ClassName
     * @param fieldName the Field Name
     * @return return the Annotations from the Field
     */
    public Annotation[] getFieldAnnotations(String className, String fieldName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(accessible);
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    return field.getAnnotations();
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Annotations from the Field from the Upper Class
     * @param className the ClassName
     * @param fieldName the Field Name
     * @return return the Annotations from the Field
     */
    public Annotation[] getFieldAnnotationsSuperClass(String className, String fieldName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Field field : cls.getSuperclass().getDeclaredFields()) {
                field.setAccessible(accessible);
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    return field.getAnnotations();
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method Annotations
     * @param className the ClassName
     * @param methodName the Method Name
     * @return return the Method Annotations
     */
    public Annotation[] getMethodAnnotations(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.getAnnotations();
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method Annotations from the Upper Class
     * @param className the ClassName
     * @param methodName the Method Name
     * @return return the Method Annotations
     */
    public Annotation[] getMethodAnnotationsSuperClass(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.getAnnotations();
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method Annotations
     * @param className the ClassName
     * @param methodName the Method Name
     * @param class__ the Annotation
     * @return return the Method Annotations
     */
    public boolean hasAnnotation(String className, String methodName, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    if (method.getAnnotation(class__) != null) return true;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves the Method Annotations from the Upper Class
     * @param className the ClassName
     * @param methodName the Method Name
     * @param class__ the Annotation
     * @return return the Method Annotations
     */
    public boolean hasAnnotationSuperClass(String className, String methodName, Class<? extends Annotation> class__) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    if (method.getAnnotation(class__) != null) return true;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Retrieves the Method
     * @param className the ClassName
     * @param methodName the Field Name
     * @return return the Method
     */
    public Method getMethod(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method from the Upper Class
     * @param className the ClassName
     * @param methodName the Field Name
     * @return return the Method
     */
    public Method getMethodSuperClass(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method;
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method Parameter Types
     * @param className the ClassName
     * @param methodName the Field Name
     * @return return the Method Parameter Types
     */
    public List<Type> getParameterTypes(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return Arrays.asList(method.getParameterTypes());
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Method Parameter Types from the Upper Class
     * @param className the ClassName
     * @param methodName the Field Name
     * @return return the Method Parameter Types
     */
    public List<Type> getParameterTypesSuperClass(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return Arrays.asList(method.getParameterTypes());
                }
            }
        } catch (SecurityException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Invokes the Method with arguments
     *
     * @param className the fully qualified name of the class containing the method
     * @param object the instance of the class on which to invoke the method
     * @param methodName the name of the method to invoke
     * @param args the arguments to pass to the method
     * @return the result of the method invocation, or null if an error occurs
     */
    public Object runMethod(String className, Object object, String methodName, Object... args) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object, args);
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Invokes the Method with arguments
     *
     * @param className the fully qualified name of the class containing the method
     * @param object the instance of the class on which to invoke the method
     * @param methodName the name of the method to invoke
     * @param args the arguments to pass to the method
     * @return the result of the method invocation, or null if an error occurs
     */
    public Object runMethodSupClass(String className, Object object, String methodName, Object... args) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object, args);
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Invokes the Method
     *
     * @param className the fully qualified name of the class containing the method
     * @param object the instance of the class on which to invoke the method
     * @param methodName the name of the method to invoke
     * @return the result of the method invocation, or null if an error occurs
     */
    public Object runMethod(String className, Object object, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object);
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Invokes the Method
     *
     * @param className the fully qualified name of the class containing the method
     * @param object the instance of the class on which to invoke the method
     * @param methodName the name of the method to invoke
     * @return the result of the method invocation, or null if an error occurs
     */
    public Object runMethodSuperClass(String className, Object object, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object);
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Checks if the Method is a Void Method
     *
     * @param className
     * @param object
     * @param methodName
     * @return
     */
    public boolean isVoidMethod(String className, Object object, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object) == null;
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return true;
    }

    public boolean isVoidMethodSuperClass(String className, Object object, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object) == null;
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return true;
    }

    public boolean isVoidMethod(String className, Object object, String methodName, Object... args) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object, args) == null;
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return true;
    }

    public boolean isVoidMethodSuperClass(String className, Object object, String methodName, Object... args) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.invoke(object, args) == null;
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return true;
    }

    /**
     * Checks if the Method requires Arguments
     * @param className the ClassName as example [de.framedev.javaproject.main.Main]
     * @param methodName the Method name to check
     * @return return true if the Method requires Arguments
     */
    public boolean hasMethodArguments(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.getParameterCount() > 0;
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Checks if the Method requires Arguments from the Upper Class
     * @param className the ClassName as example [de.framedev.javaproject.main.Main]
     * @param methodName the Method name to check
     * @return return true if the Method requires Arguments
     */
    public boolean hasMethodArgumentsSuperClass(String className, String methodName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        try {
            assert cls != null;
            for (Method method : cls.getSuperclass().getDeclaredMethods()) {
                method.setAccessible(accessible);
                if (method.getName().equalsIgnoreCase(methodName)) {
                    return method.getParameterCount() > 0;
                }
            }
        } catch (SecurityException | IllegalArgumentException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return false;
    }

    /**
     * Change the Value of a Field in a Class
     * @param className the selected Class to change a Field
     * @param object the Class Object for the Field
     * @param fieldName the FieldName to change the value
     * @param data the new Value for the Field
     */
    public void setField(String className, Object object, String fieldName, Object data) {
        Field field = getField(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return;
        }
        try {
            field.setAccessible(true);
            field.set(object, data);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
    }

    /**
     * Change the Value of a Field in a Class from the Upper Class
     * @param className the selected Class to change a Field
     * @param object the Class Object for the Field
     * @param fieldName the FieldName to change the value
     * @param data the new Value for the Field
     */
    public void setFieldSuperClass(String className, Object object, String fieldName, Object data) {
        Field field = getFieldSuperClass(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return;
        }
        try {
            field.setAccessible(true);
            field.set(object, data);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
    }

    /**
     * Retrieves the Value of a Field in a Class
     * @param className the selected Class to get a Field as example [de.framedev.javaproject.main.Main]
     * @param object the Class Object for the Field
     * @param fieldName the FieldName to get the value
     * @return return the Value of the Field
     */
    public Object getFieldValue(String className, Object object, String fieldName) {
        Field field = getField(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Value of a Field in a Class from the Upper Class
     * @param className the selected Class to get a Field as example [de.framedev.javaproject.main.Main]
     * @param object the Class Object for the Field
     * @param fieldName the FieldName to get the value
     * @return return the Value of the Field
     */
    public Object getFieldValueSuperClass(String className, Object object, String fieldName) {
        Field field = getFieldSuperClass(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieves the Type of the Field
     * @param className the selected Class as example [de.framedev.javaproject.main.Main]
     * @param fieldName the selected Field name to get the Type from it
     * @return return the Type of the Field
     */
    public Type getFieldType(String className, String fieldName) {
        Field field = getField(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return null;
        }
        field.setAccessible(true);
        return field.getType();
    }

    /**
     * Retrieves the Type of the Field from the Upper Class
     * @param className the selected Class as example [de.framedev.javaproject.main.Main]
     * @param fieldName the selected Field name to get the Type from it
     * @return return the Type of the Field
     */
    public Type getFieldTypeSuperClass(String className, String fieldName) {
        Field field = getFieldSuperClass(className, fieldName);
        if (field == null) {
            logger.log(Level.SEVERE,"Error");
            return null;
        }
        field.setAccessible(true);
        return field.getType();
    }

    /**
     * Create a new Instance from a Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @param paramsObjects the Parameters for the Constructor
     * @param accessible the Accessible for the Constructor
     * @param params the Parameters for the Constructor
     * @return return the new Instance from the Class
     */
    public Object newInstance(String className, List<Object> paramsObjects, boolean accessible, Class<?>... params) {
        Object object = null;
        try {
            Object[] objs = paramsObjects.toArray();
            Class<?> cls = Class.forName(className);
            Constructor<?> constructor = cls.getDeclaredConstructor(params);
            constructor.setAccessible(accessible);
            object = constructor.newInstance(objs);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Create a new Instance from a Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @param paramsObjects the Parameters for the Constructor
     * @param params the Parameters for the Constructor
     * @return return the new Instance from the Class
     */
    public Object newInstance(String className, List<Object> paramsObjects, Class<?>... params) {
        Object object = null;
        try {
            Object[] objs = paramsObjects.toArray();
            Class<?> cls = Class.forName(className);
            Constructor<?> constructor = cls.getDeclaredConstructor(params);
            object = constructor.newInstance(objs);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Create a new Instance from a Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @return return the new Instance from the Class
     */
    public Object newInstance(String className) {
        Object object = null;
        try {
            Class<?> cls = Class.forName(className);
            Constructor<?> constructor = cls.getDeclaredConstructor();
            object = constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Retrieve the Constructorrs from the Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @return return the Constructors from the Class
     */
    public Constructor<?>[] getConstructors(String className) {
        Constructor<?>[] object = null;
        try {
            Class<?> cls = Class.forName(className);
            object = cls.getDeclaredConstructors();
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Retrieve the Constructor from the Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @param params the Parameters for the Constructor
     * @return return the Constructor from the Class
     */
    public Constructor<?> getConstructor(String className, Class<?>... params) {
        Constructor<?> object = null;
        try {
            Class<?> cls = Class.forName(className);
            object = cls.getDeclaredConstructor(params);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Retrieve the Constructor from the Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @return return the Constructor from the Class
     */
    public Constructor<?> getConstructor(String className) {
        Constructor<?> object = null;
        try {
            Class<?> cls = Class.forName(className);
            object = cls.getDeclaredConstructor();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return object;
    }

    /**
     * Retrieve the Fields from the Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @return return the Fields from the Class
     */
    public Field[] getFields(String className) {
        try {
            Class<?> cls = Class.forName(className);
            return cls.getFields();
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }

    /**
     * Retrieve the Fields from the Upper Class
     * @param className the selected Class Name as example [de.framedev.javaproject.main.Main]
     * @return return the Fields from the Class
     */
    public Field[] getFieldsSuperClass(String className) {
        try {
            Class<?> cls = Class.forName(className);
            return cls.getSuperclass().getFields();
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"Error", e);
        }
        return null;
    }
}
