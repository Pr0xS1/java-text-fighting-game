package domain;

public class EnemyCharacter extends Character {
   public String skill;
   public boolean defending;

   public EnemyCharacter(){
       super();
       defending = false;
   }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
        defending = false;
    }
    @Override
    public void takeDamage(int damage){
       //防御状态，伤害减半
        if(defending){
            damage=damage/2 > 1 ? damage/2 : 1;
            defending = false;
        }
        super.takeDamage(damage);
    }
}
