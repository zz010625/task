import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
// 作业4-Lv6:整体为Lv3 的简易计算器 但用到了Lv3-Lv6的知识
// 其中包含了:
// 1.使用链表储存用户输入的计算式
// 2.使用两种方法打印链表内容
// 第一种:在链表中实现了Iterable接口 重写了接口方法能使链表内容通过foreach依次打印
// 第二种:通过反射获取Node中的内容并对其进行修改 再通过反射调用Link中的打印方法实现链表内容的打印
// 3.使用反射实例化对象并调用对象方法
// 4.使用泛型 多线程 异常的处理 try - catch及自定义异常
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<String> list = new ArrayList<>();
        list.add("计算器说明：");
        list.add("1.输入格式为:依次输入 number 运算符 number");
        list.add("2.仅支持+-*/（加减乘除）运算");
        list.add("3.该计算器一次仅支持两个数进行加减乘除");
        list.add("4.负数运算不能带括号如1 - （-1） 可直接输入负数如1 - -1");
        list.add("请输入计算式:");

        //多线程
        new Thread(() -> {
            for (String s : list
            ) {
                System.out.println(s);
            }
        }).start();
        //Lv4 用反射去实例化一个对象并调用对象方法
        Class calculator = Class.forName("Calculator");
        Object object = calculator.getDeclaredConstructor().newInstance();
        Calculator calculator1 = (Calculator) object;
        calculator1.calculator();
    }


}