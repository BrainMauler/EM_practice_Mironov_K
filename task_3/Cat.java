package task_3;

public class Cat {

    private String name;
    private int runLimit;
    private int jumpLimit;

    public int getRunLimit() {
        return runLimit;
    }

    public int getJumpLimit() {
        return jumpLimit;
    }

    public String getName() {
        return this.name;
    }

    public Cat(String name, int runLimit, int jumpLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.jumpLimit = jumpLimit;
    }

    public void run(int distance) {
        if (distance <= runLimit) {
            System.out.println("Кот " + name + " пробежал " + distance + " м");
        }
    }

    public void jump(int high) {
        if (high <= jumpLimit) {
            System.out.println("Кот " + name + " перепрыгнул " + high + " м");
        }
    }
}