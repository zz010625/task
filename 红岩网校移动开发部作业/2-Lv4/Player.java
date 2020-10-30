import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player extends Attribute implements Attack {
    int x;int z;
    Scanner scanner = new Scanner(System.in);


    //Player战斗的过程
    public void attack(ArrayList arrayList, int sc, int[][] speed, int a) {

        System.out.println("轮到你攻击了 请选择你要攻击的怪物序号:");
        int sc1 = scanner.nextInt(); z=sc1;

        if ((Integer) arrayList.get(2) > (Integer) arrayList.get(3 + sc1 * 5)) {

            System.out.println(arrayList.get(0) + "对" + arrayList.get(sc1 * 5) + "造成了" + ((Integer) arrayList.get(2) - (Integer) arrayList.get(3 + sc1 * 5)) + "点伤害");
            x = (Integer) arrayList.get(1 + sc1 * 5) - ((Integer) arrayList.get(2) - (Integer) arrayList.get(3 + sc1 * 5));
            arrayList.remove(1 + sc1 * 5); //先arrayList中该指定怪物sc1的血量删除
            arrayList.add(1 + sc1 * 5, x); //再添加x为该指定怪物sc1的血量 这样使得怪物血量改变后的结果能被保存下来
            System.out.println("此时"+arrayList.get(sc1 * 5) + "的血量为" + x );
        } else {
            System.out.println(arrayList.get(0) + "攻击力不足 " + arrayList.get(0) + "只能对" + arrayList.get(sc1 * 5) + "造成1点伤害");
            x = (Integer) arrayList.get(1 + sc1 * 5) - 1;
            arrayList.remove(1 + sc1 * 5); ////先arrayList中该指定怪物sc1的血量删除
            arrayList.add(1 + sc1 * 5, x); //再添加x为该指定怪物sc1的血量 这样使得怪物血量改变后的结果能被保存下来
            System.out.println("此时"+arrayList.get(sc1 * 5) + "的血量为"+ x );
        }
    }

}
