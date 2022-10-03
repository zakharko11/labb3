package droidpkg;

import java.util.Random;

public class Droid
{
    protected String name;
    protected double health;
    protected double damage;
    protected Random dmgRange = new Random();

    public Droid()
    {
        name = "noname";
        health = 0;
        damage = 0;
    }

    public Droid(String name, double health, double damage)
    {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName() { return name; }
    public double getHealth() { return health; }
    public double getDamage() { return damage; }

    public String takeDamageFrom(Droid d)
    {
        double currDamage = d.getDamage() * (0.5 + dmgRange.nextDouble(0.5));
        health -= currDamage;
        if(health < 0) health = 0;
        return String.format("%s takes %.1f damage from %s -- hp left: %.1f\n", name, currDamage, d.getName(), health);
    }

    @Override
    public String toString()
    {
        return String.format("%s HP: %.1f DMG: %.1f", name, health, damage);
    }
}