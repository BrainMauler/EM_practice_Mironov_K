import task_1.Worker;
import task_2.Animal;
import task_2.Cat;
import task_2.Dog;
import task_3.Human;
import task_3.JumpingWall;
import task_3.Robot;
import task_3.Treadmill;

public class Runner {

    public static void main(String[] args) {
        //Задача 1
        Worker[] workers = new Worker[5];
        workers[0] = new Worker("Ivanov I.I.", "iii@mail.ru",
                "+71111111111", 30000, 30);
        workers[1] = new Worker("Petrov P.P.", "ppp@mail.ru",
                "+72222222222", 35000, 35);
        workers[2] = new Worker("Sidorov S.S.", "sss@mail.ru",
                "+73333333333", 40000, 40);
        workers[3] = new Worker("Antonov A.A.", "aaa@mail.ru",
                "+74444444444", 45000, 45);
        workers[4] = new Worker("Pirogov P.P.", "ppp@mail.ru",
                "+75555555555", 50000, 50);
        for (Worker worker : workers) {
            if (worker.getAge() > 40) {
                worker.getWorkerInfo(worker);
            }
        }
        //Задача 2
        Cat Barsik = new Cat("Barsik");
        Dog Bobik = new Dog("Bobik");
        Barsik.run(0);
        Barsik.run(100);
        Barsik.run(300);
        Barsik.swim(30);
        Bobik.run(0);
        Bobik.run(200);
        Bobik.run(700);
        Bobik.swim(0);
        Bobik.swim(10);
        Bobik.swim(25);
        System.out.println("Котов " + Cat.getCatsCount() + "\nСобак " + Dog.getDogsCount() +
                "\nЖивотных в целом " + Animal.getAnimalCount());
        //Задача 3
        Human Sasha = new Human("Sasha", 300, 5);
        Human Lesha = new Human("Lesha", 250, 10);
        Human Dobrina = new Human("Dobrina", 7000, 7000);
        task_3.Cat Jorik = new task_3.Cat("Barsik", 50, 1);
        task_3.Cat Vaska = new task_3.Cat("Vaska", 30, 2);
        Robot Vintik = new Robot("Vintik", 1000, 1000);
        Robot Shpuntik = new Robot("Shpuntik", 2000, 2000);
        Treadmill mill1 = new Treadmill(100);
        Treadmill mill2 = new Treadmill(300);
        Treadmill mill3 = new Treadmill(1500);
        JumpingWall wall1 = new JumpingWall(1);
        JumpingWall wall2 = new JumpingWall(10);
        JumpingWall wall3 = new JumpingWall(1500);
        Object[] competitors = {Sasha, Lesha, Dobrina, Jorik, Vaska, Vintik, Shpuntik};
        Object[] obstacles = {mill1, wall1, mill2, wall2, mill3, wall3};
        for (Object competitor : competitors) {
            int obstacleNum = -1;
            for (Object obstacle : obstacles) {
                obstacleNum ++;
                if (obstacle instanceof Treadmill treadmill) {
                    int length = treadmill.getLength();
                    if (competitor instanceof Human human) {
                        if (human.getRunLimit() >= length) {
                            human.run(length);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Человек " + human.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Человек " + human.getName() +
                                    " не может пробежать дистанцию и выбывает");
                            break;
                        }
                    } else if (competitor instanceof task_3.Cat cat) {
                        if (cat.getRunLimit() >= length) {
                            cat.run(length);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Кот " + cat.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Кот " + cat.getName() +
                                    " не может пробежать дистанцию и выбывает");
                            break;
                        }
                    } else if (competitor instanceof Robot robot) {
                        if (robot.getRunLimit() >= length) {
                            robot.run(length);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Робот " + robot.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Робот " + robot.getName() +
                                    " разрядился и выбывает");
                            break;
                        }
                    }
                } else if (obstacle instanceof JumpingWall jumpingWall) {
                    int high = jumpingWall.getHigh();
                    if (competitor instanceof Human human) {
                        if (human.getJumpLimit() >= high) {
                            human.jump(high);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Человек " + human.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Человек " + human.getName() +
                                    " не осилил прыжок и выбывает");
                            break;
                        }
                    } else if (competitor instanceof task_3.Cat cat) {
                        if (cat.getJumpLimit() >= high) {
                            cat.jump(high);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Кот " + cat.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Кот " + cat.getName() +
                                    " не доскочил и выбывает");
                            break;
                        }
                    } else if (competitor instanceof Robot robot) {
                        if (robot.getJumpLimit() >= high) {
                            robot.jump(high);
                            if (obstacleNum == (obstacles.length - 1)) {
                                System.out.println("Робот " + robot.getName() + " успешно прошел полосу!");
                            }
                        } else {
                            System.out.println("Робот " + robot.getName() +
                                    " не обладает ТТХ под такой прыжок и выбывает");
                            break;
                        }
                    }
                }
            }
        }
    }
}