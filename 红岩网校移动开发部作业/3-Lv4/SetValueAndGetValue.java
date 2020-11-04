import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SetValueAndGetValue {
    public void setValueAndGetValue(Object object) throws ClassNotFoundException, IllegalAccessException {
        Class z = object.getClass();
        Field[] field = z.getDeclaredFields();
        String a = field[0].getType().toString();
        String string = "123";
        if (a.endsWith("int")) {
            field[0].set(object, Integer.parseInt(string));
            System.out.println("这是Integer类型的:" + field[0].get(object));

        } else {
            if (a.endsWith("String")) {
                field[0].set(object, string);
                System.out.println("这是String类型的:" + field[0].get(object));
            }
        }
    }


}

