package shared;

import model.Task;

import java.util.List;

public interface Scheduler {
    // 1, 3, 4, 5, 7, 8, 9, 10, 12, 13, 14, 17, 18, 19, 21, 23, 24, 26, 27, 28, 29, 30, 31
    void add(Task task);

    // 3, 4, 5, 7, 10, 12, 14, 21, 28, 31
    Task process();

    // 1, 3, 4, 7, 23, 24, 26, 27, 28, 29, 30
    Task peek();

    // 19, 20
    Boolean contains(Task task);

    // 3, 4, 5, 7, 8, 9, 10, 14, 17, 18, 21, 28, 29, 30, 31
    int size();

    // 22, 23
    Boolean remove(Task task);

    // 24, 25
    Boolean remove(int id);

    // 2, 3, 7
    void insertBefore(int id, Task task);

    // 4, 5, 6
    void insertAfter(int id, Task task);

    // 8
    void clear();

    // 9
    Task[] toArray();

    // 10, 11
    void reschedule(Task first, Task second);

    // 10, 11, 13, 14
    List<Task> toList();

    // 14
    void reverse();

    // 15, 17
    Task find(int id);

    // 16, 18
    Task find(Task task);
}
