import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

class AnnotatedClass {
    public AnnotatedClass() {}

    @Repeat(3)
    protected void protectedMethod(String message) {
        System.out.println("Protected Method: " + message);
    }

    @Repeat(4)
    private void privateMethod(int number) {
        System.out.println("Private Method: " + number);
    }

    public void publicMethod() {
        System.out.println("Public Method: No annotation here.");
    }

    protected void anotherProtectedMethod() {
        System.out.println("Another Protected Method: No annotation here.");
    }
}

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        AnnotatedClass annotatedClass = new AnnotatedClass();
        Class<?> clazz = annotatedClass.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat repeat = method.getAnnotation(Repeat.class);
                method.setAccessible(true); // Чтобы получить доступ к приватным методам

                for (int i = 0; i < repeat.value(); i++) {
                    if (method.getParameterCount() == 1) {
                        Parameter param = method.getParameters()[0];
                        if (param.getType() == String.class) {
                            method.invoke(annotatedClass, "Hello world iou");
                        } else if (param.getType() == int.class) {
                            method.invoke(annotatedClass, 100);
                        }
                    }
                }
            }
        }
    }
}
