package gamepkg;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import droidpkg.Droid;
import droidpkg.ShieldDroid;
import droidpkg.EnergyDroid;

public class Game
{
    private final ArrayList<Droid> droidList = new ArrayList<Droid>();
    private Scanner usrInput = new Scanner(System.in);
    private Random rand = new Random();

    private void addDroid(int droidType)
    {
        switch(droidType)
        {
            case 1: System.out.print("Enter name, health, damage: "); break;
            case 2: System.out.print("Enter name, health, damage, shield: "); break;
            case 3: System.out.print("Enter name, health, damage, energy: "); break;
        }

        String name = usrInput.next();
        double health = usrInput.nextDouble();
        double damage = usrInput.nextDouble();

        switch(droidType)
        {
            case 1:
                droidList.add(new Droid(name, health, damage));
                break;
            case 2:
                double shield = usrInput.nextDouble();
                droidList.add(new ShieldDroid(name, health, damage, shield));
                break;
            case 3:
                double energy = usrInput.nextDouble();
                droidList.add(new EnergyDroid(name, health, damage, energy));
                break;
        }
    }

    private String droidsInfo(Droid[] droids)
    {
        StringBuilder outStr = new StringBuilder(new String());
        for(int i = 0; i < droids.length; i++)
        {
            outStr.append(String.format("%d# - ", i + 1)).append(droids[i]).append("\n");
        }

        return outStr + "\n";
    }

    public void matchCreation()
    {
        System.out.println("Match type: 1-(1v1) or 2-(2v2)");
        usrInput.nextLine();
        usrInput.nextLine();
        int mType = usrInput.nextInt();
        if(mType == 1) startBattle(2);
        else if(mType == 2) startBattle(4);
    }

    public void droidCreation()
    {
        System.out.println("Droid types: 1-(BaseDroid) 2-(ShieldDroid) 3-(EnergyDroid)");
        while(usrInput.hasNextInt())
        {
            int dType = usrInput.nextInt();
            addDroid(dType);
        }
    }

    private void startBattle(int playerCount)
    {
        if(droidList.size() == 0) return;
        Droid[] selectedDroids = new Droid[playerCount];
        System.out.printf("Choose %d droids: ", playerCount);
        int[] index = new int[playerCount];
        for(int i = 0 ; i < index.length; i++)
        {
            index[i] = usrInput.nextInt();
            selectedDroids[i] = droidList.get(index[i] - 1);
        }

        try
        {
            String fileOut = "battle.txt";
            FileWriter writer = new FileWriter(fileOut, false);
            writer.write(droidsInfo(selectedDroids));

            while(droidsNotAlive(selectedDroids) < 0)
            {
                writer.write(randomAttack(selectedDroids));
            }

            writer.write("\n" + droidsInfo(selectedDroids));
            writer.write(checkWin(selectedDroids));
            writer.close();

        } catch(IOException e)
        {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public void readBattleFromFile(String filename)
    {
        try
        {
            FileReader reader = new FileReader(filename);
            int symbol;
            while((symbol = reader.read()) != -1)
            {
                System.out.print((char)symbol);
            }

            reader.close();

        } catch(IOException e)
        {
            System.err.println("IOException: " + e.getMessage());
        }

        System.out.printf("Read from: %s\n", filename);
    }

    private String randomAttack(Droid[] droids)
    {
        int turn = rand.nextInt(droids.length);
        return droids[turn].takeDamageFrom(droids[droids.length - turn - 1]);
    }

    private int droidsNotAlive(Droid[] droids)
    {
        int teamSize = droids.length / 2;
        int dead1 = 1, dead2 = 1;
        for(int i = 0; i < droids.length; i++)
        {
            if(droids[i].getHealth() > 0)
            {
                if(i < teamSize) dead1 = 0;
                else dead2 = 0;
            }
        }

        if(dead1 > 0) return 1;
        else if(dead2 > 0) return 0;
        return -1;
    }

    private String checkWin(Droid[] droids)
    {
        int notAliveId = droidsNotAlive(droids);
        if(notAliveId > -1) return String.format("--- %s WIN ---\n", notAliveId + 1);
        return "Unknown";
    }
}
