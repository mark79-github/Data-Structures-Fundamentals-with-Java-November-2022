package barbershopjava;

public class Client {

    public String name;
    public int age;
    public Gender gender;
    public Barber barber;

    public Client(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
