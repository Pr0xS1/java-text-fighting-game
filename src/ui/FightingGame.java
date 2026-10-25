package ui;

import domain.Character;
import domain.EnemyCharacter;
import domain.HeroCharacter;

import java.lang.classfile.Attribute;
import java.sql.SQLOutput;
import java.util.*;

public class FightingGame {
    public void gameStart(String username) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("    \uD83C\uDFAE "+username+"欢迎来到文字格斗游戏 \uD83C\uDFAE   ");
        System.out.println("╚════════════════════════════════════════╝");
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功！");
        System.out.println("\uD83C\uDF1F 初始属性:"+player.show());
        System.out.println("\uD83C\uDF1F 拥有技能:"+player.showskills());

        ArrayList<EnemyCharacter> enemylist=new ArrayList<>();
        enemylist.add(new EnemyCharacter("初级战士",80,15,10,"猛击"));
        enemylist.add(new EnemyCharacter("敏捷刺客",60,20,5,"快速攻击"));
        enemylist.add(new EnemyCharacter("重装坦克",120,10,20,"防御姿态"));
        enemylist.add(new EnemyCharacter("神秘法师",70,25,8,"火球术"));

        int count=1;//记录敌人
        int wins=0;
        while(player.isAlive()){
            //开始战斗
            if(wins!=0){
                //获取每个敌人
                for (int i = 0; i < enemylist.size(); i++) {
                    EnemyCharacter ec=enemylist.get(i);
                    ec.maxHP+=10;
                    ec.HP=ec.maxHP;
                    ec.attack+=3;
                    ec.defense+=2;
                    ec.defending=false;
                }
            }
            Random rand=new Random();
            int index = rand.nextInt(enemylist.size());
            EnemyCharacter enemy=enemylist.get(index);
            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔\uFE0F 第 "+count+" 场战斗开始！对手: "+enemy.name);
            int round=1;
            while(player.isAlive()){
                System.out.println("---------------------------------------");
                System.out.println("⚔\uFE0F 第 "+round+" 回合开始！ ");
                System.out.println(getHealthBar(player));
                System.out.println(getHealthBar(enemy));

                playerTurn(player,enemy);

                if(!enemy.isAlive()){
                    System.out.println("\uD83C\uDF89 你击败了 "+enemy.name+"！");
                    wins++;
                    break;
                }

                enemyTrun(enemy,player);

                if(!player.isAlive()){
                    System.out.println("你被"+enemy.name+"击败了...");
                    break;
                }

                round++;
            }

            if(player.isAlive()){
                int healHP=new Random().nextInt(21)+20;
                System.out.println("\uD83D\uDC9A 战斗结束！你恢复了 "+healHP+" 点生命值");
                player.heal(healHP);
                System.out.println("\uD83C\uDFC6 当前胜场: "+wins);
                System.out.println("═══════════════════════════════════════");

            }

            if(player.isAlive()&&wins%3==0){
                System.out.println("恭喜你获得了属性提升!");
                player.maxHP +=30;
                player.attack+=5;
                player.defense+=3;
                System.out.println("最大生命值+30 攻击力+5 防御力+3");
                System.out.println("当前属性:"+player.show());
            }

            if(player.isAlive()){
                System.out.println("继续下一场战斗？(y/n): ");
                Scanner sc=new Scanner(System.in);
                String s=sc.next();
                if("y".equalsIgnoreCase(s)){
                    count++;
                    continue;
                }else if("n".equalsIgnoreCase(s)){
                    break;
                }else{
                    System.out.println("没有这个选项 默认继续游戏");
                }
            }



        }

        System.out.println("═══════════════════════════════════════");
        System.out.println("游戏结束!");
        System.out.println("总胜场"+wins);
        System.exit(0);




    }

    private void enemyTrun(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("===== "+enemy.name+"的回合 =====");
        String action="普通攻击";
        boolean flag=new Random().nextBoolean();
        if(flag){
            action=enemy.skill;
        }
        switch(action){
            case "普通攻击":
                int damage1 = calculateDamage(enemy.attack, player.defense);
                System.out.println("⚔\uFE0F "+enemy.name+" 对你使用了普通攻击，造成 "+damage1+" 点伤害！");
                player.takeDamage(damage1);
                break;
            case "猛击":
                int damage2 = calculateDamage((int)(enemy.attack*1.5), player.defense);
                System.out.println("⚔\uFE0F "+enemy.name+" 对你使用了猛击，造成 "+damage2+" 点伤害！");
                player.takeDamage(damage2);
                break;
            case "快速攻击":
                int damage3=0;
                for (int i = 0; i < 2; i++) {
                    int tmp = calculateDamage((int)(enemy.attack/2), player.defense);
                    damage3+=tmp;
                }
                System.out.println("⚔\uFE0F "+enemy.name+" 对你使用了快速攻击，造成 "+damage3+" 点伤害！");
                player.takeDamage(damage3);
                break;
            case "防御姿态":
                enemy.defending=true;
                System.out.println("⚔\uFE0F "+enemy.name+" 使用了防御姿态");
                break;
            case "火球术":
                int damage4 = calculateDamage((int)(enemy.attack*1.8), player.defense);
                System.out.println("⚔\uFE0F "+enemy.name+" 对你使用了火球术，造成 "+damage4+" 点伤害！");
                player.takeDamage(damage4);
                break;
        }
    }

    public HeroCharacter createPlayerCharacter(String username){
        System.out.println("创建你的角色:");
        System.out.println("您的角色名为: "+username);
        int point = 20;
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");

        Scanner sc = new Scanner(System.in);
        String[] attributes ={"生命值","攻击力","防御力"};
        int[]values =new int [3];
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("分配点数到"+attributes[i]+"(剩余点数"+point+"):");
            int input = sc.nextInt();
            if(input<0){
                System.out.println("输入无效 分配0点");
                input=0;
            }
            if(input>point){
                System.out.println("属性点不足 默认剩余全部属性点分配到"+ attributes[i]);
                input=point;
            }
            point-=input;
            values[i]=input;
        }
        HeroCharacter player = new HeroCharacter(
                username,
                100+values[0]*10,
                10+values[1]*2,
                0+values[2]);
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");
        return player;
    }

    public String getHealthBar(Character c){
        int barlength=20;
        int filled =(int)( (c.HP * 1.0 / c.maxHP)*barlength);
        StringBuilder sb=new StringBuilder();
        sb.append(c.name).append(":[");
        for (int i = 0; i < barlength; i++) {
            if(i<filled){
                sb.append("█");
            }else{
                sb.append(" ");
            }
        }
        sb.append("]").append(c.HP).append("/").append(c.maxHP).append("HP");
        return sb.toString();
    }

    public void playerTurn(HeroCharacter player,EnemyCharacter enemy){
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("选择行动 (1-3): ");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        switch (input) {
            default:
                System.out.println("没有这个操作，默认普通攻击");
            case 1:
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔你对 "+enemy.name+" 使用了普通攻击，造成 "+damage1+" 点伤害！");
                enemy.takeDamage(damage1);
                break;
            case 2:
                if(player.HP>10){
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("\uD83D\uDCA5 消耗10HP，你对 "+enemy.name+" 使用了强力一击，造成 "+damage2+" 点伤害！");
                    enemy.takeDamage(damage2);
                }else{
                    System.out.println("体力不足，使用失败");
                }
                break;
            case 3:
                if(player.HP>10){
                    player.takeDamage(10);
                    Random rand=new Random();
                    int healHP = rand.nextInt(21);
                    player.heal(healHP);
                    System.out.println("\uD83D\uDCA5 消耗10HP，你对使用了生命汲取，恢复了"+healHP+"点生命值！");
                }else{
                    System.out.println("体力不足，使用失败");
                }
                break;
        }

    }

    public int calculateDamage(int attack,int defense){
        int damage = attack-defense;
        if(damage<1){
            damage=1;
        }
        return damage;
    }
}
