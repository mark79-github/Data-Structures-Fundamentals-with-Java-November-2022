package core;

import model.Task;
import shared.Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessScheduler implements Scheduler {

    private final List<Task> data = new ArrayList<>();

    @Override
    public void add(Task task) {
        this.data.add(task);
    }

    @Override
    public Task process() {
        if (this.data.isEmpty()) {
            return null;
        }
        return this.data.remove(0);
    }

    @Override
    public Task peek() {
        if (this.data.isEmpty()) {
            return null;
        }
        return this.data.get(0);
    }

    @Override
    public Boolean contains(Task task) {
        return this.data.contains(task);
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public Boolean remove(Task task) {
        boolean removeIf = this.data.removeIf(t -> t.equals(task));
        if (!removeIf) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    @Override
    public Boolean remove(int id) {
        int index = -1;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        this.data.remove(index);
        return true;
    }

    @Override
    public void insertBefore(int id, Task task) {
        int index = -1;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        }

        this.data.add(index, task);
    }

    @Override
    public void insertAfter(int id, Task task) {
        int index = -1;
        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IllegalArgumentException();
        }
        if (index + 1 < this.data.size()) {
            this.data.add(index + 1, task);
        } else {
            this.data.add(task);
        }
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public Task[] toArray() {
        Task[] tasks = new Task[this.data.size()];
        return this.data.toArray(tasks);
    }

    @Override
    public void reschedule(Task first, Task second) {
        int firstIndex = this.data.indexOf(first);
        int secondIndex = this.data.indexOf(second);

        if (firstIndex == -1 || secondIndex == -1) {
            throw new IllegalArgumentException();
        }
        Collections.swap(this.data, firstIndex, secondIndex);
    }

    @Override
    public List<Task> toList() {
        return new ArrayList<>(this.data);
    }

    @Override
    public void reverse() {
        Collections.reverse(this.data);
    }

    @Override
    public Task find(int id) {
        for (Task task : this.data) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Task find(Task task) {
        for (Task t : this.data) {
            if (t.equals(task)) {
                return t;
            }
        }
        throw new IllegalArgumentException();
    }
}
