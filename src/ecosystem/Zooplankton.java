package ecosystem;

public class Zooplankton extends Animal {

    private double reproductionProbability;
    private int minReproductionAge;
    private int minReproductionEnergy;

    public Zooplankton(int x, int y) {
        // super(x, y, maxAge, initialEnergy, maxEnergy, energyCostPerStep)
        super(x, y, 25, 10, 15, 1);
        this.reproductionProbability = 0.12;
        this.minReproductionAge = 4;
        this.minReproductionEnergy = 10;
    }

    @Override
    public void step(Grid grid) {
        // Inherited from Animal
        updateBasicState();

        boolean ate = tryEating(grid);

        if (!ate) {
            tryMoving(grid); // Inherited from Animal
        }

        tryReproduct(grid);
    }

    private boolean tryEating(Grid grid) {
        for (int[] pos : grid.getAdjacentPositions(x, y)) {
            Organism o = grid.getOrganismAtPosition(pos[0], pos[1]);

            // Zooplankton eats Algae
            if (o instanceof Algae) {
                addEnergy(5); // Inherited helper from Animal
                grid.removeOrganism(o);
                setPosition(pos[0], pos[1]);
                return true;
            }
        }
        return false;
    }

    private void tryReproduct(Grid grid) {
        if (canReproduct()) {
            int[] pos = grid.getRandomFreeAdjacentPosition(x, y);
            if (pos != null) {
                grid.addOrganism(new Zooplankton(pos[0], pos[1]));
                this.energy -= 3;
            }
        }
    }

    private boolean canReproduct() {
        return age >= minReproductionAge &&
                energy >= minReproductionEnergy &&
                Math.random() < reproductionProbability;
    }

    @Override
    public int getMoveRange() {
        return 1;
    }

    @Override
    public char getSymbol() {
        return 'Z';
    }
}