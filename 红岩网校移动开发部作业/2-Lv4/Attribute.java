import java.util.Scanner;

public class Attribute {
    private String name;
    private int health;
    private int aggressivity;
    private int defense;
    private int speed;
    private Scanner scanner = new Scanner(System.in);
    //用于当输入值不在给定区间内时输出提示
    public void wrong() {

        System.out.print("输入不满足格式要求 请重新输入:");
    }

    public String getName() {
        return name;
    }
    //以下setXXX能够判断输入内容是否在给定区间内 若不在给定区间内则会给出提示 wrong 并能够再次输入
    public void setName(String name) {
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

    public void setHealth(int health) {
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


    public int getAggressivity() {
        return aggressivity;
    }

    public void setAggressivity(int aggressivity) {
        while (true) {
            if (aggressivity > 1 && aggressivity < 999) {
                this.aggressivity = aggressivity;
                break;
            } else {
                wrong();
                aggressivity = scanner.nextInt();
            }
        }
    }


    public int getDefense() {
        return defense;
    }

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

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
