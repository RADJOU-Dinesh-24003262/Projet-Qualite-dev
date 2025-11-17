package Character;

import Food.AbstractFood;

import java.util.Objects;

public abstract class AbstractCharacter{

    protected enum Sex{
        MALE,FEMALE
    }

    protected String name;

    protected Sex sex;

    protected float height;

    protected int age;

    protected int strength;

    protected int stamina;

    protected int health;

    protected int hunger;

    protected int belligerence;

    protected int levelMagicPotion;

    protected boolean isAlive;

    public AbstractCharacter(){

    }

    public AbstractCharacter(String name){
        this.name = name;
    }

    public void fight(AbstractCharacter other){
        int damage = Math.max(this.strength - other.stamina, 0);
        other.setHealth(other.getHealth() - damage);
    }

    public void fightBoth(AbstractCharacter other){
        int damageToOther = Math.max(0, this.strength - other.stamina);
        int damageToThis  = Math.max(0, other.strength - this.stamina);

        other.setHealth(other.getHealth() - damageToOther);
        this.setHealth(this.getHealth() - damageToThis);
    }

    public void heal(AbstractCharacter other){
        // TO DO : continue really
        other.setHealth(other.getHealth() + this.strength);
    }

    public void heal() {
        this.setHealth(this.getHealth() + this.strength);
    }

    public void eat(AbstractFood food){
        // TO DO : en fonction de l'alimentation
        this.setHealth(getHealth() + 1);

    }

    public void drink (MagicPotion potion){
        this.setLevelMagicPotion(potion);
    }

    public void dead(){
        this.setHealth(0);
        this.setStrength(0);
        this.setStamina(0);
        this.setHealth(0);
        this.isAlive = false;
    }



















    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }


    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getBelligerence() {
        return belligerence;
    }

    public void setBelligerence(int belligerence) {
        this.belligerence = belligerence;
    }

    public int getLevelMagicPotion() {
        return levelMagicPotion;
    }

    public void setLevelMagicPotion(int levelMagicPotion) {
        this.levelMagicPotion = levelMagicPotion;
    }

    @Override
    public String toString() {
        return "AbstractCharacter{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", height=" + height +
                ", age=" + age +
                ", strength=" + strength +
                ", stamina=" + stamina +
                ", health=" + health +
                ", hunger=" + hunger +
                ", belligerence=" + belligerence +
                ", levelMagicPotion=" + levelMagicPotion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractCharacter that)) return false;
        return Float.compare(height, that.height) == 0 && age == that.age && strength == that.strength && stamina == that.stamina && health == that.health && hunger == that.hunger && belligerence == that.belligerence && levelMagicPotion == that.levelMagicPotion && Objects.equals(name, that.name) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex, height, age, strength, stamina, health, hunger, belligerence, levelMagicPotion);
    }
}
