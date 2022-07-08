package week3;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  List of employees(id, name, age)
 *  1. write down all questions
 *     write a function which takes list of employees as input
 *     remove duplicate
 *  2. create a function
 *     use stream api, get employees whose age larger than 40
 *  3. create a function
 *     use one stream api to split list into 2 groups based on age
 *     List<List<Employee>> ,  [[employees' age < 40],  [employees' age >= 40]]
 *  4. create a class + function
 *     input 3 list of Integer, use multi-threading to sum all values from 3 list and return int result
 *     use CompletableFuture
 *  5. create a class + function
 *     create two threads and print even + odd number
 *     1   :  Thread1
 *     2   :  Thread2
 *     3   :  Thread1
 *     4   :  Thread2
 *     ....
 *
 *  deadline: tomorrow morning 10:00 am cdt
 *  upload to github
 */


public class Ass1 {

    static private class Employee {
        String name;
        int age;
        public Employee(String name, int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null || o.getClass() != this.getClass()) return false;
            Employee temp = (Employee) o;
            return temp.age == this.age && temp.name.equals(this.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return "(" + this.name + " " + this.age + ")";
        }
    }

    /**
     * 1. Remove Duplicates
     * @param employees
     * @return
     */
    private static List<Employee> removeDuplicates(List<Employee> employees) {
        if (employees == null || employees.size() == 0) {
            return null;
        }

        Set<Employee> set = new HashSet<>();
        for (int i = 0; i < employees.size(); i++) {
            if (set.contains(employees.get(i))) {
                employees.remove(i);
                i--;
                continue;
            }
            set.add(employees.get(i));
        }
        return employees;
    }
    /**
     * 2. get employees whose age larger than 40 by stream api
     */

    private static List<Employee> olderThan40(List<Employee> employees) {
        Stream<Employee> stream = employees.stream();
        List<Employee> olderList = stream.filter(i -> i.age >= 40)
                .collect(Collectors.toList());

        return olderList;
    }

    /**
     * 3. split employees by age
     */
    private static List<List<Employee>> splitByAge(List<Employee> employees) {
        Map<Boolean, List<Employee>> groups = employees.stream()
                .collect(Collectors.groupingBy(s -> s.age >= 40));
        return Arrays.asList(groups.get(true), groups.get(false));
    }

    /**
     * 4. Three thread sum
     */
    private static int sum(List<List<Integer>> lists)  throws ExecutionException, InterruptedException {
        List<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (List<Integer> list : lists) {
            CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> list.stream().reduce(0, (x, y) -> x + y));
            futures.add(cf);
        }
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(Void -> {
                    int res = 0;
                    for (CompletableFuture<Integer> f : futures) {
                        res += f.join();
                    }
                    return res;
                }).join();
    }
//    private static ExecutorService esPool = Executors.newCachedThreadPool();
//    private static int sum(List<List<Integer>> lists) throws ExecutionException, InterruptedException {
//        List<Future<Integer>> futures = new ArrayList<>();
//        for (List<Integer> list : lists) {
//            Future<Integer> f = esPool.submit(() ->
//                list.stream().reduce(0, (x,y) -> x + y));
//            futures.add(f);
//        }
//        int res = 0;
//        for (Future<Integer> f: futures) {
//            res += f.get();
//        }
//        return res;
//    }

    /**
     * 5. print odd & even number with two thread
     */
    volatile int counter = 1;
    static int N;
    public void printOddNumber() {
        synchronized (this)
        {
            while (counter < N) {
                while (counter % 2 == 0) {
                    try {
                        wait();
                    }
                    catch (
                            InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(Thread.currentThread().getName() + " " + counter + " | ");
                counter++;
                notify();
            }
        }
    }
    public void printEvenNumber() {
        synchronized (this)
        {
            while (counter < N) {
                while (counter % 2 == 1) {
                    try {
                        wait();
                    }
                    catch (
                            InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(Thread.currentThread().getName() + " " + counter + " | ");
                counter++;
                notify();
            }
        }
    }



    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Employee> emps = new ArrayList<>();
        emps.add(new Employee("Alex", 31));
        emps.add(new Employee("Michael", 40));
        emps.add(new Employee("Shawn", 21));
        emps.add(new Employee("Tom", 55));
        emps.add(new Employee("Judy", 30));

        emps.add(new Employee("Shawn", 21));
        emps.add(new Employee("Shawn", 21));
        emps.add(new Employee("Tom", 55));
        System.out.println("employee list initial:");
        System.out.println(emps);

        System.out.println("===========================");

        /**
         *  Test 1:
         */
        List<Employee> res = removeDuplicates(emps);
        System.out.println("test 1: remove duplicates");
        System.out.println(res);
        System.out.println("===========================");
        /**
         * Test 2:
         */
        System.out.println("test 2: get older than 40");
        System.out.println(olderThan40(res));
        System.out.println("===========================");

        /**
         * Test 3:
         */
        System.out.println("test 3: split by age");
        System.out.println(splitByAge(res));
        System.out.println("===========================");

        /**
         * Test 4: three threads sum
         */
        System.out.println("test 4: three threads sum");
        List<List<Integer>> input = Arrays.asList(
                                    Arrays.asList(1,2,3),
                                    Arrays.asList(4,5,6),
                                    Arrays.asList(7,8,9));
        System.out.println(sum(input));
        System.out.println("===========================");
        /**
         *  Test 5: two thread print even, odd numbers
         */
        System.out.println("test 5: print odd, even numbers");
        N = 1000;
        Ass1 test5 = new Ass1();
        Thread t1 = new Thread(test5::printOddNumber, "T1");
        Thread t2 = new Thread(test5::printEvenNumber, "T2");

        t1.start();
        t2.start();
    }


}
