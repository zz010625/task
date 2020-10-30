import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
//1.可以自定义对战怪物数量 既可一对多 当怪物全死亡时 玩家获胜
//2.引入速度 并能通过判断自定义个数怪物和玩家的速度大小 实现攻击顺序为从速度大到小 若玩家速度和某一怪物速度相当 则玩家先于该怪物攻击
public class Game {
    public static void main(String[] args) {
        Player player = new Player();
        Monster monster = new Monster();
        //输入玩家属性
        System.out.println("请输入玩家属性:");
        System.out.print("1.玩家名称:");
        player.setName(player.getScanner().nextLine());
        System.out.print("2.玩家生命值:");
        player.setHealth(player.getScanner().nextInt());
        System.out.print("3.玩家攻击力:");
        player.setAggressivity(player.getScanner().nextInt());
        System.out.print("4.玩家防御值:");
        player.setDefense(player.getScanner().nextInt());
        System.out.print("5.玩家速度:");
        player.setSpeed(player.getScanner().nextInt());
        //先将player的属性导入arrayList中
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, player.getName());
        arrayList.add(1, player.getHealth());
        arrayList.add(2, player.getAggressivity());
        arrayList.add(3, player.getDefense());
        arrayList.add(4, player.getSpeed());
        //自定义怪物个数并输入怪物属性
        System.out.print("请输入怪物数量:");
        int sc = monster.getScanner().nextInt();     //sc=怪物数量
        for (int i = 0; i < sc; i++) {
            System.out.println("请输入怪物" + (i + 1) + "属性:");
            System.out.print("1.怪物名称:");
            monster.getScanner().nextLine();//读取换行符
            monster.setName(monster.getScanner().nextLine());
            System.out.print("2.怪物生命值:");
            monster.setHealth(monster.getScanner().nextInt());
            System.out.print("3.怪物攻击力:");
            monster.setAggressivity(monster.getScanner().nextInt());
            System.out.print("4.怪物防御力:");
            monster.setDefense(monster.getScanner().nextInt());
            System.out.print("5.怪物速度:");
            monster.setSpeed(monster.getScanner().nextInt());
            //再将monsters的属性导入
            arrayList.add(5 * (i + 1), monster.getName());
            arrayList.add(1 + 5 * (i + 1), monster.getHealth());
            arrayList.add(2 + 5 * (i + 1), monster.getAggressivity());
            arrayList.add(3 + 5 * (i + 1), monster.getDefense());
            arrayList.add(4 + 5 * (i + 1), monster.getSpeed());
        }
        //根据速度大小判断攻击顺序，若速度相同Player先攻击
        int[][] speed = new int[sc + 1][2]; //用speed二维数组存放:speed,序号
        for (int i = 0; i < sc + 1; i++) {
            speed[i][1] = i;
            speed[i][0] = (Integer) arrayList.get(4 + 5 * i);
        }
        //对数组的第1列进行从大到小的排序
        Arrays.sort(speed, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });
        //确认Player的速度所在行 即为第a行 以a为怪物攻击的分割点可实现使分割点前的怪物先攻击 再进行玩家攻击 最后分割点后的怪物执行攻击
        //若a最终为0 既分割点前无怪物 所以分割点前的怪物攻击方法无法执行 之后执行玩家攻击方法 最后执行分割点后的怪物攻击
        int a =0;
        for (int i = 0; i <= sc; i++) {
            if (speed[i][0] == (Integer) arrayList.get(4)) {
                a = i;
                break;
            }

        }
        //下面为战斗环节
        System.out.println("战斗开始:");
        int z = 0;     //87行要用
        for (int i = 1; i >= 1; i++) {

            System.out.println("第" + i + "回合:");
            monster.attack(arrayList, sc, speed, a);
            if (monster.x <= 0) {     //判断玩家生命值是否大于0
                System.out.println(player.getName()+"失败 游戏结束");
                System.exit(0);
            }
            player.attack(arrayList, sc, speed, a);
            if (player.x <= 0) {   //判断指定攻击的这个怪物生命值是否大于0
                System.out.println("怪物" + player.z + ":" + arrayList.get(player.z * 5) + "死亡");
                z = z + 1;      //通过z的值来反映死亡怪物数量
                if (z == sc) {      //当死亡怪物数量等于怪物数量时
                    System.out.println(player.getName() + "获胜 游戏结束");
                    System.exit(0);
                }

            }
            monster.attack1(arrayList, sc, speed, a);
            if (monster.x <= 0) {     //判断玩家生命值是否大于0
                System.out.println(player.getName()+"失败 游戏结束");
                System.exit(0);
            }

        }
    }
}

