package couponsopsjava;

public class Website {

    public final String domain;
    public final int usersCount;

    public Website(String domain, int usersCount) {
        this.domain = domain;
        this.usersCount = usersCount;
    }

    public String getDomain() {
        return domain;
    }

    public int getUsersCount() {
        return usersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Website)) return false;

        Website website = (Website) o;

        return getDomain().equals(website.getDomain());
    }

    @Override
    public int hashCode() {
        return getDomain().hashCode();
    }
}
