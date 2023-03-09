package appUsers;

public abstract class User {
    private final String firstName, lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User user) {
            return this.firstName.equals(user.firstName) && this.lastName.equals(user.lastName);
        } else {
            return false;
        }
    }
}
