import java.util.*;

public class restaurant {
    public static void main(String[] args) {
        menu z = new menu();
        System.out.println("欢迎来到国民饭店，这是今天的菜单：");
        for (int i = 0; i < 5; i++) {
            System.out.println(z.c[i] + z.a[i] + " " + z.b[i] + "元");
        }
        int t = 0;
        ArrayList y = new ArrayList();
        ArrayList l = new ArrayList();
        Scanner x = new Scanner(System.in);
        for (int o = 0; o >= 0; o++) {
            System.out.println("请输入想要点的菜的序号(退出点菜系统请输入0 若完成点菜请输入6进行支付)：");
            int x1 = x.nextInt();
            if (x1 == 0) {
                System.exit(0);
            }
            if (x1 == 6) {
                break;
            }
            y.add(o, z.a[x1 - 1]);
            l.add(o, z.b[x1 - 1]);
            System.out.println("你一共选择了：");
            for (int i = 0; i <= o; i++) {
                System.out.println(y.get(i) + " " + l.get(i) + "元");
            }
            t += z.b[x1 - 1];
            System.out.println("共计" + t + "元");
            System.out.println("还有需要点的菜么？");
            for (int i = 0; i < 5; i++) {
                System.out.print(z.c[i]);
                for (int j = 0; j < 1; j++) {
                    System.out.println(z.a[i] + " " + z.b[i] + "元");
                }
            }
        }
        System.out.println("请选择支付方式");
        String[] pay = {"支付宝", "微信", "人脸识别"};
        for (int i = 1; i < 4; i++) {
            System.out.print(i);
            for (int j = 0; j < 1; j++) {
                System.out.println(pay[i - 1]);
            }
        }
        int x2 = x.nextInt();
        String[] m = {"请出示付款码", "请将面部靠近摄像头"};
        String q;
        q = (x2 < 3) ? m[0] : m[1];
        System.out.println(q);
        System.out.println("正在支付请稍后......");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("支付成功");
                System.exit(0);
                this.cancel();
            }
        }, 2000);
    }
}