import java.util.ArrayList;

public class Monster extends Attribute implements Attack {
    int x;
    int x1;

    //Monster的战斗过程分为2阶段 如下所示
    //1
    public void attack(ArrayList arrayList, int sc, int[][] speed, int a) {

        for (int i = 0; i < a; i++) {    //通过a可实现使在[][]speed中a行之前的的每一行代表的单位先执行攻击方法
            //判断攻击力是否大于防御力
            if ((Integer) arrayList.get(2 + (i + 1) * 5) > (Integer) arrayList.get(3)) {
                //arrayList.get(5 * speed[i][1]）为速度最快怪物的name
                System.out.println(arrayList.get(5 * speed[i][1]) + "对" + arrayList.get(0) + "造成了" + ((Integer) arrayList.get(2 + speed[i][1] * 5) - (Integer) arrayList.get(3)) + "点伤害");
                //x为攻击后 玩家剩余血量
                x = (Integer) arrayList.get(1) - ((Integer) arrayList.get(2 + speed[i][1] * 5) - (Integer) arrayList.get(3));
                arrayList.remove(1); //先arrayList中玩家血量删除
                arrayList.add(1, x); //再添加x为玩家血量 这样使得玩家血量改变后的结果能被保存下来
                System.out.println("此时" + arrayList.get(0) + "的血量为" + x);
            } else {
                System.out.println(arrayList.get(5 * speed[i][1]) + "攻击力不足 " + arrayList.get(5 * speed[i][1]) + "只能对" + arrayList.get(0) + "造成1点伤害");
                x = (Integer) arrayList.get(1) - 1;
                arrayList.remove(1); //先arrayList中玩家血量删除
                arrayList.add(1, x); //再添加x为玩家血量 这样使得玩家血量改变后的结果能被保存下来
                System.out.println("此时" + arrayList.get(0) + "的血量为" + x);

            }
        }
    }

    //2
    public void attack1(ArrayList arrayList, int sc, int[][] speed, int a) {
        for (int i = a; i < sc; i++) {   //通过a可实现使在[][]speed中a行之前的的每一行代表的单位先执行攻击方法
            //判断攻击力是否大于防御力
            if ((Integer) arrayList.get(2 + (i + 1) * 5) > (Integer) arrayList.get(3)) {
                System.out.println(arrayList.get(5 * speed[i + 1][1]) + "对" + arrayList.get(0) + "造成了" + ((Integer) arrayList.get(2 + speed[i + 1][1] * 5) - (Integer) arrayList.get(3)) + "点伤害");
                x1 = x - ((Integer) arrayList.get(2 + speed[i + 1][1] * 5) - (Integer) arrayList.get(3));
                arrayList.remove(1);  //先arrayList中玩家血量删除
                arrayList.add(1, x1); //再添加x为玩家血量 这样使得玩家血量改变后的结果能被保存下来
                System.out.println("此时" + arrayList.get(0) + "的血量为" + x1);
            } else {
                System.out.println(arrayList.get(5 * speed[i + 1][1]) + "攻击力不足 " + arrayList.get(5 * speed[i + 1][1]) + "只能对" + arrayList.get(0) + "造成1点伤害");
                x1 = x - 1;
                arrayList.remove(1); //先arrayList中玩家血量删除
                arrayList.add(1, x1); //再添加x1为玩家血量 这样使得玩家血量改变后的结果能被保存下来
                System.out.println("此时" + arrayList.get(0) + "的血量为" + x1);
            }
        }
    }

}
