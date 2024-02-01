package carsharing;

import java.util.List;

public interface CarSharingDao<T> {
    void manageDatabase();
    void add(String name, int id);
    void displayList(List<T> items);
    void displayMenu();
}
