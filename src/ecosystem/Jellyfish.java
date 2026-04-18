package ecosystem;

public class Jellyfish extends Animal {

    public Jellyfish(int x, int y) {
        // super(x, y, maxAge, initialEnergy, maxEnergy, energyCostPerStep)
        super(x, y, 35, 12, 20, 1);
    }

    @Override
    public void step(Grid grid) {
        // Use the inherited method from Animal to age and lose energy
        updateBasicState();

        boolean ate = false;
        // Only try to eat if not at max energy
        if (energy < maxEnergy) {
            ate = tryEating(grid);
        }

        // Only move if it didn't eat (predators usually stay where they find food)
        if (!ate) {
            tryMoving(grid); // Inherited from Animal
        }

        tryReproduct(grid);
    }

    private boolean tryEating(Grid grid) {
        for (int[] pos : grid.getAdjacentPositions(x, y)) {
            Organism o = grid.getOrganismAtPosition(pos[0], pos[1]);

            // Jellyfish eats Zooplankton
            if (o instanceof Zooplankton) {
                addEnergy(7); // Helper from Animal to handle maxEnergy cap
                grid.removeOrganism(o);
                setPosition(pos[0], pos[1]);
                return true;
            }
        }
        return false;
    }

    private void tryReproduct(Grid grid) {
        // Minimal energy requirement for reproduction
        if (energy < 10) return;

        for (int[] pos : grid.getAdjacentPositions(x, y)) {
            Organism o = grid.getOrganismAtPosition(pos[0], pos[1]);

            // Sexual reproduction: requires another Jellyfish nearby
            if (o instanceof Jellyfish) {
                int[] newPos = grid.getRandomFreeAdjacentPosition(x, y);
                if (newPos != null) {
                    grid.addOrganism(new Jellyfish(newPos[0], newPos[1]));
                    energy -= 5;
                    return; // Reproduce only once per step
                }
            }
        }
    }

    @Override
    public int getMoveRange() {
        return 1;
    }

    @Override
    public char getSymbol() {
        return 'J';
    }
}