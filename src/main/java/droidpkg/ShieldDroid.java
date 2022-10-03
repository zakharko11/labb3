package droidpkg;

public class ShieldDroid extends droidpkg.Droid
{
    private double shield = 0;

    public ShieldDroid(String name, double health, double damage, double shield)
    {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.shield = shield;
    }

    public double getShield() { return shield; }

    @Override
    public String takeDamageFrom(droidpkg.Droid d)
    {
        double currDamage = d.getDamage() * (0.5 + dmgRange.nextDouble(0.5));

        if(shield > 0) shield -= currDamage;
        if(shield < 0)
        {
            health -= -1 * shield;
            shield = 0;
        }
        else if(shield == 0)
        {
            health -= currDamage;
            if(health < 0) health = 0;
        }

        return String.format("%s takes %.1f damage from %s -- shd left: %.1f, hp left: %.1f\n",
                name, currDamage, d.getName(), shield, health);
    }

    @Override
    public String toString()
    {
        return String.format("%s HP: %.1f DMG: %.1f SHD: %.1f", name, health, damage, shield);
    }
}