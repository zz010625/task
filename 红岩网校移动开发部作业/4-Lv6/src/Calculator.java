import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

//Lv3 简易计算器
public class Calculator {
    //计算过程（保留三位小数）
    public String calculation(String[] string) {
        double x = Double.parseDouble(string[0]);
        char[] operate = string[1].toCharArray();
        double y = Double.parseDouble(string[2]);
        return switch (operate[0]) {
            case '+' -> String.format("%.3f", x + y);
            case '-' -> String.format("%.3f", x - y);
            case '*' -> String.format("%.3f", x * y);
            case '/' -> String.format("%.3f", x / y);
            default -> " 错误:未检测到运算符 请按照说明重新输入计算式:";
        };
    }
    //计算器主体
    public void calculator() {
        //Lv3 异常的处理以及try - catch语句的使用
        //当输入格式不正确导致无法正确计算时 给出错误提示并重新进行输入和计算
        try {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            String[] str1 = str.split(" ");
            if (str1.length > 3) {
                System.out.println(" 错误:该计算器一次仅支持录入两个数以及指定运算符进行加减乘除 请重新输入计算式:");
                calculator();
            }
            Link<String> link = new Link();
            link.addNode(str1[0]);
            link.addNode(str1[1]);
            link.addNode(str1[2]);
            Link.Node root=link.getRoot(); //将链表的私有属性根节点拿出 作为之后通过反射修改Node中的data时所需的Node类对象

            //Lv5 用重写的iterator.next() 将链表内容依次打印

//            Iterator<String> iterator = n1.ite;
//            while (iterator.hasNext()) {
//                System.out.print(iterator.next());
//            }

            //用foreach将链表内容依次打印
            for (Object a : link
            ) {
                System.out.print(a);
            }
            System.out.print("=");
            try {
                System.out.println(calculation(str1));
            } catch (Exception e) {
                // Lv3 自定义异常
                System.out.println(" 错误 请按照说明重新输入计算式:");
                calculator();
            }

            //下面实现计算器的重复计算（用到Lv4反射知识）

            while (true) {
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("请输入(输入1继续计算 输入其他退出计算):");
                int z = scanner1.nextInt();
                if (z == 1) {
                    System.out.println("请输入计算式:");
                    String str2 = scanner.nextLine();
                    String[] str3 = str2.split(" ");
                    if (str3.length > 3) {
                        System.out.println(" 错误:该计算器一次仅支持录入两个数以及一个指定运算符进行加减乘除 请重新输入计算式:");
                        calculator();
                    }

                    Class classNode = Class.forName("Link$Node");
                    //Lv4 用反射修改“私有/公有”的属性值
                    Field field = classNode.getDeclaredField("data");
                    Field field1 = classNode.getDeclaredField("next");
                    field.setAccessible(true);
                    field1.setAccessible(true);
                    field.set(root, str3[0]);
                    field.set(field1.get(root), str3[1]);
                    field.set(field1.get(field1.get(root)), str3[2]);

                    Class classLink = Class.forName("Link");
                    //Lv4 反射调用Link中的打印方法将链表内容依次打印
                    Method method = classLink.getMethod("printData");
                    method.invoke(link);
                    System.out.print("=");
                    try {
                        System.out.println(calculation(str3));
                    } catch (Exception e) {
                        System.out.println(" 错误 请按照说明重新输入计算式:");
                        calculator();
                    }
                } else {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(" 错误:输入内容未用空格隔开或未按照说明1依次输入 请按照说明重新输入计算式:");
            calculator();
        }
    }
}