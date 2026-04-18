package ecosystem;

public class SeaTurtle extends Animal {

    private int minReproductionEnergy;
    private int minReproductionAge;
    private int reproductionEnergyCost;
    private double reproductionProbability;

    public SeaTurtle(int x, int y) {
        // super(x, y, maxAge, initialEnergy, maxEnergy, energyCostPerStep)
        super(x, y, 40, 20, 30, 1);
        this.minReproductionEnergy = 25;
        this.minReproductionAge = 8;
        this.reproductionEnergyCost = 12;
        this.reproductionProbability = 0.15;
    }

    @Override
    public void step(Grid grid) {
        // Use the method inherited from Animal
        updateBasicState();

        // 1. Feeding (Higher priority)
        boolean ate = tryEating(grid);

        // 2. Movement (Only if it didn't eat)
        if (!ate) {
            tryMoving(grid); // Inherited from Animal
        }

        // 3. Reproduction
        tryReproduct(grid);
    }

    private boolean tryEating(Grid grid) {
        // Prefers Jellyfish (10 energy) over Zooplankton (5 energy)
        if (findAndEat(grid, Jellyfish.class, 10)) return true;
        if (findAndEat(grid, Zooplankton.class, 5)) return true;
        return false;
    }

    private boolean findAndEat(Grid grid, Class<?> targetType, int gain) {
        for (int[] pos : grid.getAdjacentPositions(x, y)) {
            Organism o = grid.getOrganismAtPosition(pos[0], pos[1]);
            if (targetType.isInstance(o)) {
                addEnergy(gain); // Helper method from Animal
                grid.removeOrganism(o);
                setPosition(pos[0], pos[1]);
                return true;
            }
        }
        return false;
    }

    private void tryReproduct(Grid grid) {
        if (!canStartReproduction()) return;

        for (int[] pos : grid.getAdjacentPositions(x, y)) {
            Organism o = grid.getOrganismAtPosition(pos[0], pos[1]);

            // Casting to SeaTurtle to check its energy
            if (o instanceof SeaTurtle partner) {
                if (partner.canBePartner()) {
                    int[] babyPos = grid.getRandomFreeAdjacentPosition(x, y);
                    if (babyPos != null) {
                        grid.addOrganism(new SeaTurtle(babyPos[0], babyPos[1]));
                        this.energy -= reproductionEnergyCost;
                        partner.energy -= reproductionEnergyCost;
                        return; // Successfully reproduced
                    }
                }
            }
        }
    }

    private boolean canStartReproduction() {
        return age >= minReproductionAge &&
                energy >= minReproductionEnergy &&
                Math.random() < reproductionProbability;
    }

    public boolean canBePartner() {
        return age >= minReproductionAge && energy >= minReproductionEnergy;
    }

    @Override
    public int getMoveRange() {
        return 1;
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}