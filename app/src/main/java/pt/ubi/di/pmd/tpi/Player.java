package pt.ubi.di.pmd.tpi;

public class Player {
    // Create a class Player that has a name a role and a number
    private String name;
    private String role;

    // Make a static variable that counts the number of players
    private static int number = 0;

    // Create a constructor for the class that increments the number of players
    public Player(String name, String role) {
        this.name = name;
        this.role = role;
        number++;
    }

    // Create a constructor for the class without the role
    public Player(String name) {
        this.name = name;
        number++;
    }

    // Create getters and setters for the class
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
