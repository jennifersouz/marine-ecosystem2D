package ecosystem;

public abstract class Organism {
    protected int x, y;
    protected int age;
    protected int maxAge;

    public Organism(int x, int y, int maxAge) {
        this.x = x;
        this.y = y;
        this.age = 0;
        this.maxAge = maxAge;
    }

    public void incrementAge() { age++; }
    public boolean isDeadByAge() { return age > maxAge; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAge() { return age; }

    public abstract void step(Grid grid);
    public abstract char getSymbol();
    public abstract int getMoveRange();
}