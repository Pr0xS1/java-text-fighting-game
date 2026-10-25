package domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;

    public Character() {
    }
    //刚创建人物 HP满
    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
    //是否存活
    public boolean isAlive() {
        return HP > 0;
    }
    //恢复血量
    public void heal(int amount){
        HP=HP+amount;
        if(HP>maxHP){
            HP=maxHP;
        }
    }
    //受到伤害
    public void takeDamage(int amount){
        HP=HP-amount;
        if(HP<0){
            HP=0;
        }
    }

    //展示人物属性
    public String show(){
        return(name+"[当前生命:"+HP+"/"+maxHP+".攻击:"+attack+".防御:"+defense+"]");
    }

}
