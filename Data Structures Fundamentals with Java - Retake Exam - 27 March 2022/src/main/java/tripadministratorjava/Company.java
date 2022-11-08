package tripadministratorjava;

public class Company {

    public String name;
    public int tripOrganizationLimit;

    public Company(String name, int tripOrganizationLimit) {
        this.name = name;
        this.tripOrganizationLimit = tripOrganizationLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
