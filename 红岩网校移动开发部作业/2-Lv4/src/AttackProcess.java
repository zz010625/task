import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AttackProcess {
    Player player = new Player();
    Monster monster = new Monster();
    ArrayList list = new ArrayList();

    public void insertData(ArrayList arrayList) {
        list = arrayList;
    }

    int[][] speed = new int[999999][2];
    int [][]speed1=new int[999999][2];

    public void compareSpeed(int monsterNum, int Speed) {

        for (int i = 0; i < monsterNum; i++) {
            speed[i][1] = i;
            speed[i][0] = (Integer) list.get(4 + i * 5);
        }Arrays.sort(speed1, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });

        speed[monsterNum][1] = monsterNum;
        speed[monsterNum][0] = Speed;
        Arrays.sort(speed, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[0] - o1[0];
            }
        });//对二维数组按列排序

    }

    public void attackProcess(int [][]speed1,ArrayList arrayList1,int monsterNum, int Speed,String playername,String monstername,int playerdefense,int monsterdefense,int playerattack) {
        int a = 0;
        for (int i = 0; i <= monsterNum; i++) {
            if (speed[i][0] == Speed) {
                a = i;
            }

        }

        monster.attackPattern(speed1,arrayList1,a,Speed,playername,monstername,playerdefense,monsterdefense);


        player.attackPattern(speed1,arrayList1,monsterNum,Speed,playername,monstername,playerdefense,monsterdefense,playerattack);

            monster.attackPattern1(speed1,arrayList1,a,monsterNum,Speed,playername,monstername,playerdefense,monsterdefense);



    }
}



