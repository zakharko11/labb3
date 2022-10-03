package droidpkg;

public class EnergyDroid extends droidpkg.Droid
{
    private double energyLeft = 0;

    public EnergyDroid(String name, double health, double damage, double energy)
    {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.energyLeft = energy;
    }

    public double getEnergy() { return energyLeft; }

    @Override
    public double getDamage()
    {
        energyLeft -= 20;
        if(energyLeft < 0)
        {
            energyLeft = 0;
            damage = 0;
        }

        return damage;
    }

    @Override
    public String toString() {
        return String.format("%s HP: %.1f DMG: %.1f ERG: %.1f", name, health, damage, energyLeft);
    }
}

