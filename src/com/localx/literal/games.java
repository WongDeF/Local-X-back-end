package com.localx.literal;

public class games {
    //    工厂模式 生成我 和对手实体 通过传入数据的方式生成
    public static class GameEntity {
        private final String name;
        private int attack;
        private int defense;
        private double hp;
        private double skill;
        public GameEntity(int attack, int defense, double hp, double skill, String name) {
            this.attack = attack;
            this.defense = defense;
            this.hp = hp;
            this.skill = skill;
            this.name = name;
        }
        public int getAttack() {
            return attack;
        }
        public void setSkill(int rate) {
            this.skill += this.skill * rate;
        }
        public void setAttack(int attack) {
            this.attack = attack;
        }
        public int getDefense() {
            return defense;
        }
        public void setDefense(int defense) {
            this.defense += defense;
        }
        public double getHp() {
            return hp;
        }
        public void setHp(double hp) {
            this.hp = hp;
        }
    }
    // 普通攻击方法
    public static void attack(GameEntity me, GameEntity enemy) {
        int myAttack = me.getAttack();
        int enemyDefense = enemy.getDefense();
        int damage = myAttack - enemyDefense;
        if (damage > 0) {
            enemy.setHp(enemy.getHp() - damage);
            System.out.println( me.name + "攻击了"+ enemy.name +"，攻击力：" + myAttack + "，防御力：" + enemyDefense + "，伤害：" + damage);
            System.out.println(enemy.name + "剩余血量：" + enemy.getHp());
        } else {
            System.out.println(me.name + "攻击了" + enemy.name + "，攻击力：" + myAttack + "，防御力：" + enemyDefense + "，伤害：0");
        }
    }
    static void main(){
    /*                  我                对手
    *    攻击力 整数      288              250
    *    防御 整数        99               77
    *    血量 浮点        1024.99          1000.11
    *    技能加成 浮点     1.1              1.0
    */
        GameEntity me = new GameEntity(288, 99, 1024.99, 1.1, "我");
        GameEntity enemy = new GameEntity(250, 77, 1000.11, 1.0, "对手");
        attack(me, enemy);
        attack(enemy, me);
    }
}
