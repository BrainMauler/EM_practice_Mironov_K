package task_3;

public class Robot {

    private int runLimit;
    private int jumpLimit;
    private String name;

    public int getRunLimit() {
        return runLimit;
    }

    public int getJumpLimit() {
        return jumpLimit;
    }

    public String getName() {
        return this.name;
    }

    public Robot(String name, int runLimit, int jumpLimit) {
        this.name = name;
        this.runLimit = runLimit;
        this.jumpLimit = jumpLimit;
    }

    public void run(int distance) {
        if (distance <= runLimit) {
            System.out.println("Робот " + name + " пробежал " + distance + " м");
        }
    }

    public void jump(int high) {
        if (high <= jumpLimit) {
            System.out.println("Робот " + name + " перепрыгнул " + high + " м");
        }
    }
}