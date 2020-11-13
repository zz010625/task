import java.util.ArrayList;
import java.util.Scanner;

//怪物类 包含有关怪物的一切内容
//接口AttackPattern
public  class Monster {
    private String name;
    private int health;
    private int attack;
    private int defense;
    private int speed;
    int a;
    private Scanner scanner = new Scanner(System.in);//用于 当输入数据不在限定范围内时 可实现再次输入
    public int [][]Speed1=new int[99999][2];

    private ArrayList list = new ArrayList();     //用list来装入多个monster的各项属性

    //方法1 用于将多个monster属性存入list里 使得第1到5项，第6到10项......依次为各个monster的name health attack speed
    public void arraylist(int num) {

        list.add(num * 5, name);
        list.add(1 + num * 5, health);
        list.add(2 + num * 5, attack);
        list.add(3 + num * 5, defense);
        list.add(4 + num * 5, speed);
    }

    //方法2 用于输入用户自定义数量的怪物的属性并通过方法1使得怪物参数填入list中
    public void monsterAttribute() {
        System.out.print("请输入怪物数量:");
        int sc = scanner.nextInt();     //sc=怪物数量
        a=sc;
        for (int i = 0; i < sc; i++) {
            System.out.println("请输入怪物" + (i + 1) + "属性:");
            System.out.print("1.怪物名称:");
            scanner.nextLine();//读取换行符
            setName(scanner.nextLine());
            System.out.print("2.怪物生命值:");
            setHealth(scanner.nextInt());
            System.out.print("3.怪物攻击力:");
            setAttack(scanner.nextInt());
            System.out.print("4.怪物防御力:");
            setDefense(scanner.nextInt());
            System.out.print("5.怪物速度:");
            setSpeed(scanner.nextInt());
            arraylist(i);     //运行方法1
        }
    }

    //方法3 完善接口中的AttackPattern方法 此方法为怪物攻击模式
    public void attackPattern(int [][]speed1,ArrayList arrayList1,int a, int Speed,String playername,String monstername,int playerdefense,int monsterdefense){
        Speed1=speed1;
        for (int i = 0; i <a; i++) {

        if ((Integer)arrayList1.get(2+i*5) >playerdefense){

            System.out.println(arrayList1.get(5*Speed1[i][1]) + "对" + playername + "造成了" + ((Integer)arrayList1.get(2+Speed1[i][1]*5) -playerdefense)+"点伤害");
        }else{
            System.out.println(arrayList1.get(5*Speed1[i][1]) + "攻击力不足 " + arrayList1.get(5*Speed1[i][1]) + "只能对" + playername+ "造成1点伤害");
        }
    }}

    public void attackPattern1(int [][]speed1,ArrayList arrayList1,int a,int monsterNum, int Speed,String playername,String monstername,int playerdefense,int monsterdefense){
        Speed1=speed1;
        for (int i = a; i <monsterNum; i++) {

            if ((Integer)arrayList1.get(2+i*5) >playerdefense){

                System.out.println(arrayList1.get(5*Speed1[i][1]) + "对" + playername + "造成了" + ((Integer)arrayList1.get(2+Speed1[i][1]*5) -playerdefense)+"点伤害");
            }else{
                System.out.println(arrayList1.get(5*Speed1[i][1]) + "攻击力不足 " + arrayList1.get(5*Speed1[i][1]) + "只能对" + playername+ "造成1点伤害");
            }
        }}
    //方法3 用于当输入值不在给定区间内时输出提示
    public void wrong() {

        System.out.print("输入不满足格式要求 请重新输入:");
    }

    public String getName() {
        return name;
    }

    //方法4 用于set玩家名称并能够判断输入名称长度是否在给定区间内 若不在给定区间内则会给出提示（提示为方法3）并能够再次输入
    public void setName(String name) {
        //判断输入的name是否满足范围要求
        while (true) {
            if (name.length() > 1 && name.length() < 50) {
                this.name = name;
                break;
            } else {
                wrong();
                name = scanner.nextLine();
            }
        }
    }

    public int getHealth() {
        return health;
    }

    //方法5 大致同方法4一样 name变为health
    public void setHealth(int health) {
        //判断输入的health是否满足范围要求
        while (true) {
            if (health > 1 && health < 999) {
                this.health = health;
                break;
            } else {
                wrong();
                health = scanner.nextInt();
            }
        }
    }

    public int getAttack() {
        return attack;
    }

    //方法6 大致同方法4一样 name变为attack
    public void setAttack(int attack) {
        //判断输入的attack是否满足范围要求
        while (true) {
            if (attack > 1 && attack < 999) {
                this.attack = attack;
                break;
            } else {
                wrong();
                attack = scanner.nextInt();
            }
        }
    }

    public int getDefense() {
        return defense;
    }

    //方法7 大致同方法4一样 name变为defense
    public void setDefense(int defense) {
        while (true) {
            if (defense > 1 && defense < 999) {
                this.defense = defense;
                break;
            } else {
                wrong();
                defense = scanner.nextInt();
            }
        }
    }

    public int getSpeed() {
        return speed;
    }

    //方法8 大致同方法4用于 name变为speed
    public void setSpeed(int speed) {
        while (true) {
            if (speed > 1 && speed < 999) {
                this.speed = speed;
                break;
            } else {
                wrong();
                speed = scanner.nextInt();
            }
        }
    }

    //方法9 set get list 使AttackPattern可以用到Monsters的属性
    public ArrayList getList() {
        return list;
    }

    public void setList(ArrayList list) {
        this.list = list;
    }

    //方法10 用于让子类Player也能够用到Monster的scanner
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }
}
