package ecosystem;

public class Algae extends Organism {

    private double reproductionProbability;

    public Algae(int x, int y) {
        super(x, y, 15);
        this.reproductionProbability = 0.10;
    }

    @Override
    public void step(Grid grid) {
        attState();
        tryReproduct(grid);
    }

    private void attState() {
        incrementAge();
    }

    private void tryReproduct(Grid grid) {
        if (Math.random() < reproductionProbability) {
            int[] pos = grid.getRandomFreeAdjacentPosition(x, y);

            if (pos != null) {
                grid.addOrganism(new Algae(pos[0], pos[1]));
            }
        }
    }

    @Override
    public int getMoveRange() {
        return 0;
    }

    @Override
    public char getSymbol() {
        return 'A';
    }
}