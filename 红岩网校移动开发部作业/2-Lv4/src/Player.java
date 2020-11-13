import java.util.ArrayList;
import java.util.Random;

//玩家类 包含有关玩家的一切内容
//继承Monster类
//接口AttackPattern
public class Player extends Monster implements AttackPattern {
    Monster monster=new Monster();
    ArrayList list=new ArrayList();
    Game game=new Game();
    //方法1 用于输入玩家的属性
    public void playerAttribute() {
        System.out.println("请输入玩家属性:");
        System.out.print("1.玩家名称:");
        this.setName(monster.getScanner().nextLine());
        System.out.print("2.玩家生命值:");
        this.setHealth(monster.getScanner().nextInt());
        System.out.print("3.玩家攻击力:");
        this.setAttack(monster.getScanner().nextInt());
        System.out.print("4.玩家防御值:");
        this.setDefense(monster.getScanner().nextInt());
        System.out.print("5.玩家速度:");
        this.setSpeed(monster.getScanner().nextInt());

    }

    //方法2 完善接口中的AttackPattern方法 其中包含作业中举出的三种玩家攻击模式
    public void attackPattern(int [][]speed1,ArrayList arrayList1,int monsterNum, int Speed,String playername,String monstername,int playerdefense,int monsterdefense,int playerattack) {
        Random random = new Random();
        int rd=random.nextInt(monsterNum);
        if (playerattack>(Integer) arrayList1.get(3+rd*5)){

            System.out.println(playername + "对" + arrayList1.get(rd* 5) + "造成了" + (playerattack - (Integer) arrayList1.get(3 + rd* 5)) + "点伤害");
        } else {
            System.out.println(playername + "攻击力不足 " + playername + "只能对" + (Integer) arrayList1.get(rd*5) + "造成1点伤害");
        }
    }
}
