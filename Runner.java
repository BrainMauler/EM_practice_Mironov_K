import task_1.Worker;
import task_2.Animal;
import task_2.Cat;
import task_2.Dog;
import task_3.Human;
import task_3.JumpingWall;
import task_3.Robot;
import task_3.Treadmill;
import user_exceptions.MyArrayDataException;
import user_exceptions.MyArraySizeException;

import java.util.*;

public class Runner {

    public static void main(String[] args) throws MyArrayDataException, MyArraySizeException {
        //Задача 1_1
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
        //Задача 1_2
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
        //Задача 1_3
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
                obstacleNum++;
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
        String testTextRus = "Это тестовая,        тестовая:., строка строка строка ";
        String testTextEng = "This is test,   test,./' text text...";
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList("Первый", "Второй", "Третий"));
        ArrayList<Integer> integerArrayList = new ArrayList<>(Arrays.asList(1, 2, 3));
        Integer[] integers = {1, 2, 3, 4, 5};
        String[][] correctArray = {{"1", "0", "15", "300"}, {"8", "14", "250", "777"},
                {"-30", "500", "850", "-0"}, {"12", "15", "3000", "-250"}};
        String[][] incorrectDimArray = {{"1", "0", "15", "300"}, {"8", "14", "250", "777"},
                {"-30", "500", "850", "-0"}, {"12", "15", "3000"}};
        String[][] incorrectDimsArray = {{"1", "0", "15"}, {"8", "14", "250"},
                {"-30", "500", "850.0", "-0"}};
        String[][] incorrectDataArray = {{"1", "0", "15", "300"}, {"8", "", "/", "Seven"},
                {"-30", "500", "850.0", "-0"}, {"12", "15", "3000", "-250"}};
        wordsCounter(testTextRus, "rus");
        wordsCounter(testTextEng, "eng");
        wordsCounter(testTextEng, "chn");
        arrayListRounder(stringArrayList);
        arrayListRounder(integerArrayList);
        System.out.println(arrayToArayList(integers));
        intsOpsStreams(0, 30, 10);
        intsOpsStreams(0, 1, 10);
        System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(correctArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDimsArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDimArray));
        //System.out.println("Сумма элементов двумерного массива равна " + twoDimArraySum(incorrectDataArray));
    }

    //Задача 2_1
    public static void wordsCounter(String sentence, String language) {
        String kirRegex = "[^а-яА-ЯёЁ]+";
        String latRegex = "[^a-zA-Z]+";
        List<String> words = new ArrayList<>();
        switch (language) {
            case "rus":
                words = Arrays.asList(sentence.toLowerCase().split(kirRegex));
                break;
            case "eng":
                words = Arrays.asList(sentence.toLowerCase().split(latRegex));
                break;
            default:
                System.out.println("Метод не распознает строки на языке " + language);
                break;
        }
        HashMap<String, Integer> counter = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                counter.put(word, counter.getOrDefault(word, 0) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println("Слово \"" + entry.getKey() + "\" встречается в строке раз: " +
                    entry.getValue());
        }
    }

    //Задача 2_2
    public static <T> void arrayListRounder(ArrayList<T> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println((i + 1) + " элемент списка: " + arrayList.get(i));
            System.out.println("Это через цикл for.");
            int index = 0;
            while (index < arrayList.size()) {
                System.out.println((index + 1) + " элемент списка: " + arrayList.get(index));
                index++;
            }
            System.out.println("Это через цикл while.");
            for (T element : arrayList) {
                System.out.println((arrayList.indexOf(element) + 1) + " элемент списка: " + element);
            }
            System.out.println("Это через цикл for-each.");
        }
    }

    //Задача 2_3
    public static <T> ArrayList<T> arrayToArayList(T[] array) {
        return new ArrayList<>(Arrays.stream(array).toList());
    }

    //Задача 2_4
    public static void intsOpsStreams(int min, int max, int size) {
        Random random = new Random();
        List<Integer> integersList;
        integersList = random.ints(size, min, max + 1).boxed().toList();
        System.out.println("Сгенерирован список: " + integersList);
        List<Integer> evensList = integersList.stream().filter((x) -> x % 2 == 0).toList();
        System.out.println("Убраны нечетные: " + evensList);
        List<Integer> doubledList = evensList.stream().map((x) -> x * 2).toList();
        System.out.println("Удвоены оставшиеся: " + doubledList);
        int sum = doubledList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Сумма оставшихся чисел: " + sum);
    }

    //Задача 2_5
    public static int twoDimArraySum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4) {
            throw new MyArraySizeException("Количество сток массива должен быть 4, а не "
                    + array.length);
        }
        for (int i = 0; i < 4; i++) {
            if (array[i].length != 4) {
                throw new MyArraySizeException("Размер строки " + (i + 1) + " массива должен быть 4, а не "
                        + array[i].length);
            }
        }
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException exception) {
                    throw new MyArrayDataException("Формат содержимого ячейки " + (i + 1) + " колонки и "
                            + (j + 1) + " строки, а именно " + array[i][j] +
                            " не соответствует целочисленному преобразованию");
                }
            }
        }
        return sum;
    }
}