import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.SortedSet;

/**
 * Класс с функциями обработки и исполнения пользовательских команд.
 * @author я
 */
abstract public class Treatment {
    public static void help() {
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции.");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении.");
        System.out.println("add {element} : добавить новый элемент в коллекцию.");
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному.");
        System.out.println("remove_by_id id : удалить элемент из коллекции по его id");
        System.out.println("clear : очистить коллекцию.");
        System.out.println("save : сохранить коллекцию в файл.");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла.");
        System.out.println("exit : завершить программу (без сохранения в файл).");
        System.out.println("head : вывести первый элемент коллекции.");
        System.out.println("add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.");
        System.out.println("remove_greater {element} : удалить из коллекции все элементы, превышающие заданный.");
        System.out.println("sum_of_health : вывести сумму значений поля health для всех элементов коллекции.");
        System.out.println("count_by_health health : вывести количество элементов, значение поля health которых равно заданному.");
        System.out.println("filter_by_health health : вывести элементы, значение поля health которых равно заданному.");
    }

    public static void head(PriorityQueue<SpaceMarine> pQ){
        if (!pQ.isEmpty()) {
            objpols(pQ.peek());
        }
        else {
            System.out.println("Колекция пуста.");
        }
    }

    public static void show(PriorityQueue<SpaceMarine> pQ){
        Iterator value = pQ.iterator();
        if (pQ.size()!=0) {
            while (value.hasNext()) {
                objpols((SpaceMarine) value.next());
                //System.out.println(((SpaceMarine) value.next()).hashCode());
            }
        }
        else {
            System.out.println("Колекция пуста.");
        }
    }

    public static void sum_of_health(PriorityQueue<SpaceMarine> pQ){
        Iterator value = pQ.iterator();
        int hp = 0;
        while (value.hasNext()) {
            hp += ((SpaceMarine) value.next()).getHealth();
        }
        System.out.println(hp);
    }

    public static void count_by_health(PriorityQueue<SpaceMarine> pQ, int health){
        Iterator value = pQ.iterator();
        int i = 0;
        while (value.hasNext()) {
            if (((SpaceMarine) value.next()).getHealth()==health){
                i++;
            }
        }
        System.out.println(i);
    }

    public static void filter_by_health(PriorityQueue<SpaceMarine> pQ, int health){
        Iterator value = pQ.iterator();
        while (value.hasNext()) {
            SpaceMarine chek = (SpaceMarine) value.next();
            if (chek.getHealth()==health){
                objpols(chek);
            }
        }
    }

    public static SpaceMarine remove_by_id(PriorityQueue<SpaceMarine> pQ, int id){
        Iterator value = pQ.iterator();
        SpaceMarine a = null;
        while (value.hasNext()) {
            SpaceMarine chek = (SpaceMarine) value.next();
            if (chek.getId()==id){
                a = chek;
                break;
            }
        }
        return a;
    }

    public static SpaceMarine make_element(int id){
        ZonedDateTime creationDate = ZonedDateTime.now();
        Scanner in = new Scanner(System.in);

        String name;
        while (true) {
            System.out.print("name: ");
            name = in.nextLine();
            if (name.length() == 0){
                System.out.println("Имя не может быть пустым.");
            }
            else {
                break;
            }
        }

        int x;
        while (true) {
            System.out.print("X: ");
            try {
                x = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }

        double y;
        while (true) {
            System.out.print("Y: ");
            try {
                y = Double.parseDouble(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }

        long health;
        while (true) {
            System.out.print("health: ");
            try {
                health = Long.parseLong(in.nextLine());
                if (health>0) {
                    break;
                } else {
                    System.out.println("Число должно быть положительным.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }

        System.out.print("achievements: ");
        String achievements = in.nextLine();
        if (achievements.length()==0) {
            achievements = null;
        }

        AstartesCategory category;
        while (true) {
            System.out.print("category (ASSAULT, INCEPTOR, HELIX, APOTHECARY): ");
            try {
                category = AstartesCategory.valueOf(in.nextLine());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Введите значение из списка.");
            }
        }

        Weapon weaponType;
        String chek_null;
        while (true) {
            System.out.print("weaponType (MELTAGUN, COMBI_PLASMA_GUN, GRAV_GUN, null - пустая строка): ");
            chek_null = in.nextLine();
            if (chek_null.length()==0) {
                weaponType = null;
                break;
            } else {
                try {
                    weaponType = Weapon.valueOf(chek_null);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Введите значение из списка.");
                }
            }
        }

        System.out.print("chapter name: ");
        String chname = in.nextLine();
        Chapter chapter = null;
        if (chname.length() > 0) {
            Integer marinesCount;
            while (true) {
                System.out.print("chapter marinesCount: ");
                chek_null = in.nextLine();
                if (chek_null.length() == 0) {
                    marinesCount = null;
                    chapter = new Chapter(chname, marinesCount);
                    break;
                } else {
                    try {
                        marinesCount = Integer.parseInt(chek_null);
                        if (marinesCount <= 1000 && marinesCount > 0) {
                            chapter = new Chapter(chname, marinesCount);
                            break;
                        } else {
                            System.out.println("Число должно быть от 1 до 1000.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введите целое число.");
                    }
                }
            }
        }
        return new SpaceMarine(id, name, new Coordinates(x, y), creationDate, health, achievements, category, weaponType, chapter);
    }

    public static void remove_greater(PriorityQueue<SpaceMarine> pQ, SpaceMarine o) {
        Iterator value = pQ.iterator();
        SpaceMarine a;
        boolean ch = false;
        while (value.hasNext()) {
            a = (SpaceMarine) value.next();
            if (!ch && a.hashCode() > o.hashCode()) {
                ch = true;
            }
            if (ch) {
                pQ.remove(a);
            }
        }
    }

    private static void objpols(SpaceMarine o) {
        System.out.println("id: " + o.getId().toString());
        System.out.println("name: " + o.getName());
        System.out.println("coordinates: X: " + o.getCoordinates().getX() + " Y: " + o.getCoordinates().getY());
        System.out.println("creationDate: " + o.getCreationDate().toString());
        System.out.println("health: " + o.getHealth());
        System.out.println("achievements: " + o.getAchievements());
        System.out.println("category: " + o.getCategory());
        System.out.println("weaponType: " + o.getWeaponType());
        if (o.getChapter() != null) {
            System.out.println("chapter: name: " + o.getChapter().getName() + " marinesCount: " + o.getChapter().getMarinesCount());
        } else {
            System.out.println("chapter: " + o.getChapter());
        }
    }

    public static void treatment (PriorityQueue<SpaceMarine> pQueue, SortedSet<Integer> all_id, Gson g, LocalDateTime time_create) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        boolean script = false;
        Scanner ex_scan = null;
        String[] comand;
        SpaceMarine obj;
        boolean work = true;
        while (work) {
            if (script) {
                if (ex_scan.hasNextLine()) {
                    comand = ex_scan.nextLine().split("\\s");
                } else {
                    script = false;
                    comand = in.nextLine().split("\\s");
                }
            } else {
                comand = in.nextLine().split("\\s");
            }

            switch (comand[0]) {
                case ("exit"):
                    work = false;
                    in.close();
                    break;
                case ("help"):
                    Treatment.help();
                    break;
                case ("head"):
                    Treatment.head(pQueue);
                    break;
                case ("show"):
                    Treatment.show(pQueue);
                    break;
                case ("clear"):
                    pQueue.clear();
                    break;
                case ("sum_of_health"):
                    Treatment.sum_of_health(pQueue);
                    break;
                case ("count_by_health"):
                    if (comand.length == 2) {
                        try {
                            if (Integer.parseInt(comand[1]) > 0) {
                                Treatment.count_by_health(pQueue, Integer.parseInt(comand[1]));
                            } else {
                                System.out.println("Число должно быть положительным.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("В качестве аргумента введите целое положительное число.");
                        }
                    } else {
                        System.out.println("В качестве аргумента введите целое положительное число.");
                    }
                    break;
                case ("filter_by_health"):
                    if (comand.length == 2) {
                        try {
                            if (Integer.parseInt(comand[1]) > 0) {
                                Treatment.filter_by_health(pQueue, Integer.parseInt(comand[1]));
                            } else {
                                System.out.println("Число должно быть положительным.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("В качестве аргумента введите целое положительное число.");
                        }
                    } else {
                        System.out.println("В качестве аргумента введите целое положительное число.");
                    }
                    break;
                case ("remove_by_id"):
                    if (comand.length == 2) {
                        try {
                            if (Integer.parseInt(comand[1]) > 0) {
                                obj = Treatment.remove_by_id(pQueue, Integer.parseInt(comand[1]));
                                if (obj != null) {
                                    pQueue.remove(obj);
                                    all_id.remove(Integer.parseInt(comand[1]));
                                } else {
                                    System.out.println("Элемента с таким id не найдено.");
                                }
                            } else {
                                System.out.println("Число должно быть положительным.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("В качестве аргумента введите целое положительное число.");
                        }
                    } else {
                        System.out.println("В качестве аргумента введите целое положительное число.");
                    }
                    break;
                case ("add"):
                    if (pQueue.size() != 0) {
                        pQueue.add(Treatment.make_element(all_id.last() + 1));
                        all_id.add(all_id.last() + 1);
                    } else {
                        pQueue.add(Treatment.make_element(1));
                        all_id.add(1);
                    }
                    break;
                case ("update"):
                    if (comand.length == 2) {
                        try {
                            if (Integer.parseInt(comand[1]) > 0) {
                                obj = Treatment.remove_by_id(pQueue, Integer.parseInt(comand[1]));
                                if (obj != null) {
                                    pQueue.remove(obj);
                                    pQueue.add(Treatment.make_element(Integer.parseInt(comand[1])));
                                } else {
                                    System.out.println("Элемента с таким id не найдено.");
                                }
                            } else {
                                System.out.println("Число должно быть положительным.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("В качестве аргумента введите целое положительное число.");
                        }
                    } else {
                        System.out.println("В качестве аргумента введите целое положительное число.");
                    }
                    break;
                case ("add_if_min"):
                    if (pQueue.size() != 0) {
                        obj = Treatment.make_element(all_id.last() + 1);
                        if (obj.hashCode() < pQueue.peek().hashCode()) {
                            pQueue.add(obj);
                            all_id.add(all_id.last() + 1);
                        }
                    } else {
                        pQueue.add(Treatment.make_element(1));
                        all_id.add(1);
                    }
                    break;
                case ("remove_greater"):
                    if (pQueue.size() > 0) {
                        obj = Treatment.make_element(all_id.last() + 1);
                        Treatment.remove_greater(pQueue, obj);
                    }
                    break;
                case ("info"):
                    System.out.println("Тип: " + pQueue.getClass().getName());
                    System.out.println("Время инициализации: " + time_create);
                    System.out.println("Количество элементов: " + pQueue.size());
                    break;
                case ("save"):
                    try {
                        PrintWriter pw = new PrintWriter("E:/JP/M5/src/main/java/kavo.json");
                        pw.write(g.toJson(pQueue));
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден");
                    }
                    break;
                case ("execute_script"):
                    if (!script) {
                        if (comand.length == 2) {
                            try {
                                ex_scan = new Scanner(new File(comand[1]));
                                script = true;
                            } catch (FileNotFoundException e) {
                                System.out.println("Не удается найти указанный файл.");
                            }
                        } else {
                            System.out.println("В качестве аргумента введите путь до файла.");
                        }
                    } else {
                        System.out.println("Из файла не может быть вызван другой файл");
                        script = false;
                    }
                    break;
                default:
                    System.out.println("Неизвестная команда, для получения списка команд используйте help.");
                    break;
            }
        }
    }
}