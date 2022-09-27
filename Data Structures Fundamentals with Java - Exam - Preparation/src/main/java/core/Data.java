package core;

import interfaces.Entity;
import interfaces.Repository;
import model.Invoice;
import model.StoreClient;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Data implements Repository {

    private final Queue<Entity> elements;

    public Data() {
        this.elements = new PriorityQueue<>();
    }

    private Data(Queue<Entity> elements) {
        this.elements = elements;
    }

    @Override
    public void add(Entity entity) {
        this.elements.offer(entity);
    }

    @Override
    public Entity getById(int id) {
        for (Entity entity : this.elements) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        return this.elements.stream()
                .filter(e -> e.getParentId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.elements);
    }

    @Override
    public Repository copy() {
        PriorityQueue<Entity> queue = new PriorityQueue<>(this.elements);
        return new Data(queue);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        String invoiceType = Invoice.class.getSimpleName();
        String storeClientType = StoreClient.class.getSimpleName();
        String userType = User.class.getSimpleName();

        if (!type.equals(invoiceType) && !type.equals(storeClientType) && !type.equals(userType)) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }

        return this.elements
                .stream()
                .filter(entity -> {
                    String entityType = entity.getClass().getSimpleName();
                    return entityType.equals(type);
                }).collect(Collectors.toList());
    }

    @Override
    public Entity peekMostRecent() {
        if (this.elements.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.elements.peek();
    }

    @Override
    public Entity pollMostRecent() {
        if (this.elements.isEmpty()) {
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.elements.poll();
    }

    @Override
    public int size() {
        return this.elements.size();
    }
}
