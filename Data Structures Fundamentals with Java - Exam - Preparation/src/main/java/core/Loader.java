package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Loader implements Buffer {

    private final List<Entity> data;

    public Loader() {
        this.data = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.data.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity entity = null;
        for (Entity currentEntity : this.data) {
            if (currentEntity.getId() == id) {
                entity = currentEntity;
                break;
            }
        }
        this.data.remove(entity);
        return entity;
    }

    @Override
    public Entity find(Entity entity) {
        for (Entity e : this.data) {
            if (e.getId() == entity.getId()) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean contains(Entity entity) {
        return this.data.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.data.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        int i = 0;
        boolean found = false;
        for (; i < this.data.size(); i++) {
            if (this.data.get(i).getId() == oldEntity.getId()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalStateException("Entity not found");
        }
        this.data.set(i, newEntity);
    }

    private int findIndex(Entity entity) {
        for (int i = 0; i < this.data.size(); i++) {
            Entity currentEntity = this.data.get(i);
            if (currentEntity.getId() == entity.getId()) {
                return i;
            }
        }
        throw new IllegalStateException("Entities not found");
    }

    @Override
    public void swap(Entity first, Entity second) {
        int firstIndex = this.findIndex(first);
        int secondIndex = this.findIndex(second);

        Collections.swap(this.data, firstIndex, secondIndex);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] arr = new Entity[this.data.size()];
        return this.data.toArray(arr);
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        ArrayList<Entity> list = new ArrayList<>();

        for (Entity entity : data) {
            int currentEntityStatus = entity.getStatus().ordinal();
            if (currentEntityStatus >= lowerBound.ordinal() && currentEntityStatus <= upperBound.ordinal()) {
                list.add(entity);
            }
        }

        return list;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        for (Entity entity : this.data) {
            if (entity.getStatus().ordinal() == oldStatus.ordinal()) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        this.data.removeIf(e -> e.getStatus() == BaseEntity.Status.SOLD);
    }

    @Override
    public Iterator<Entity> iterator() {
        return this.data.iterator();
    }

}
