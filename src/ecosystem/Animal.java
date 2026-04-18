package ecosystem;

public abstract class Animal extends Organism {
    protected int energy;
    protected int maxEnergy;
    protected int energyCostPerStep;

    public Animal(int x, int y, int maxAge, int initialEnergy, int maxEnergy, int energyCostPerStep) {
        super(x, y, maxAge);
        this.energy = initialEnergy;
        this.maxEnergy = maxEnergy;
        this.energyCostPerStep = energyCostPerStep;
    }

    protected void updateBasicState() {
        incrementAge();
        energy -= energyCostPerStep;
    }

    protected void addEnergy(int amount) {
        this.energy = Math.min(this.energy + amount, maxEnergy);
    }

    protected void tryMoving(Grid grid) {
        int[] move = grid.getRandomFreeAdjacentPosition(x, y);
        if (move != null) {
            setPosition(move[0], move[1]);
        }
    }

    public boolean isDeadByEnergy() {
        return energy <= 0;
    }

    public int getEnergy() {
        return energy;
    }
}