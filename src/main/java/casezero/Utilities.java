package casezero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Generic helper class that works with different data types
class CollectionHelper<T extends Comparable<T>> {

    // Returns a sorted copy of the given list
    public List<T> sortList(List<T> items) {

        List<T> sortedItems = new ArrayList<>(items);

        Collections.sort(sortedItems);

        return sortedItems;
    }

    // Displays all items in a list
    public void displayList(List<T> items) {

        for (T item : items) {
            System.out.println(item);
        }
    }

    // Returns the number of items in the list
    public int countItems(List<T> items) {

        return items.size();
    }
}
