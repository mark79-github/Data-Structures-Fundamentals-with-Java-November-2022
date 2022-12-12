package org.softuni.exam.structures;


import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

public interface DeliveriesManager {
    void addDeliverer(Deliverer deliverer);

    void addPackage(Package aPackage);

    boolean contains(Deliverer deliverer);

    boolean contains(Package aPackage);

    Iterable<Deliverer> getDeliverers();

    Iterable<Package> getPackages();

    void assignPackage(Deliverer deliverer, Package aPackage) throws IllegalArgumentException;

    Iterable<Package> getUnassignedPackages();

    Iterable<Package> getPackagesOrderedByWeightThenByReceiver();

    Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName();
}
