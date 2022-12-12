package org.softuni.exam.entities;

public class Deliverer {
    private String id;

    private String name;

    public Deliverer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deliverer)) return false;

        Deliverer deliverer = (Deliverer) o;

        return getId().equals(deliverer.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
