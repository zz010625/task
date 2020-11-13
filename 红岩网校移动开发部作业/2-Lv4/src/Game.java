public class Game {
    public static void main(String[] args) {
        Player player = new Player();
        Monster monster = new Monster();
        AttackProcess attackProcess=new AttackProcess();
        player.playerAttribute();//运行Player中的方法1：输入玩家属性
        monster.monsterAttribute();//运行Monster中的方法1：输入怪物属性
        System.out.println("战斗开始:");
        attackProcess.insertData(monster.getList());
        attackProcess.compareSpeed(monster.a,player.getSpeed());
        attackProcess.attackProcess(attackProcess.speed1,monster.getList(),monster.a,player.getSpeed(),player.getName(),monster.getName(),player.getDefense(),monster.getDefense(), player.getAttack());


    }public void xx(){

    }
}
