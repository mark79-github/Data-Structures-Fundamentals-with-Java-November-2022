package org.softuni.exam.structures;


import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {

    private final Map<String, Deliverer> deliverers;
    private final Map<String, Package> packages;
    private final Map<String, Package> unassignedPackages;
    private final Map<Deliverer, List<Package>> deliverersWithPackages;

    public DeliveriesManagerImpl() {
        this.deliverers = new HashMap<>();
        this.packages = new HashMap<>();
        this.unassignedPackages = new HashMap<>();
        this.deliverersWithPackages = new HashMap<>();
    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        this.deliverers.put(deliverer.getId(), deliverer);
        this.deliverersWithPackages.put(deliverer, new ArrayList<>());
    }

    @Override
    public void addPackage(Package aPackage) {
        this.packages.put(aPackage.getId(), aPackage);
        this.unassignedPackages.put(aPackage.getId(), aPackage);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return this.deliverers.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package aPackage) {
        return this.packages.containsKey(aPackage.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return this.deliverers.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return this.packages.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package aPackage) throws IllegalArgumentException {
        if (!this.contains(deliverer)) {
            throw new IllegalArgumentException();
        }
        if (!this.contains(aPackage)) {
            throw new IllegalArgumentException();
        }
        this.deliverersWithPackages.get(deliverer).add(aPackage);
        this.unassignedPackages.remove(aPackage.getId());
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return this.unassignedPackages.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return this.packages.values()
                .stream()
                .sorted(Comparator.comparing(Package::getWeight).reversed().thenComparing(Package::getReceiver))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return this.deliverersWithPackages.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.getValue().size() - o1.getValue().size() == 0) {
                        return o1.getKey().getName().compareTo(o2.getKey().getName());
                    }
                    return o2.getValue().size() - o1.getValue().size();
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
