package domain;

import java.util.ArrayList;

public class HeroCharacter extends Character {
    public ArrayList<String> skillList;
    public HeroCharacter() {
        super();
        this.skillList = new ArrayList<>();
    }

    public HeroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
        this.skillList = new ArrayList<>();
    }
    public String showskills(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            if(i==skillList.size()-1){
                sb.append(skillList.get(i));
                break;
            }
            sb.append(skillList.get(i));
            sb.append(",");
        }
        return sb.toString();
    }
}
