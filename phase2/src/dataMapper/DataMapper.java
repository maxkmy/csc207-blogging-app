package dataMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMapper {
    /**
     * A data structure usable by presenters
     */
    private List<Map<String, String>> model = new ArrayList<>();

    /**
     * Return a string representing the value for the attribute of the object.
     *
     * @param object    a java object
     * @param attribute a string representing an attribute of the object
     * @return          a string representing the value for the attribute of the object
     */
    private <T> String getAttr(T object, String attribute) {
        try {
            Field field = object.getClass().getDeclaredField(attribute);
            field.setAccessible(true);
            return field.get(object).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Resets the model by clearing all existing items in the model.
     */
    public void reset() {
        model.clear();
    }

    /**
     * Extract attributes of an item and add them to the model
     *
     * @param item        a java object
     * @param attributes  a list of attributes to be extracted from item
     */
    public <T> void addItem(T item, String[] attributes) {
        Map<String, String> itemMap = getItemMap(item, attributes);
        model.add(itemMap);
    }

    /**
     * Remove an item from the model where the item has a specific attribute and value under the assumption that there
     * is exactly 1 item with that specific attribute and value.
     *
     * @param attribute   a string representing the attribute of the item to be removed
     * @param value       a string representing the value of the attribute of the item to be removed
     */
    public <T> void deleteItem(String attribute, String value) {
        int i = 0;
        for (Map<String, String> item : model) {
            if (item.get(attribute).equals(value)) {
                model.remove(i);
                break;
            }
            i++;
        }
    }

    /**
     * Extract attributes from items and add them to the model
     *
     * @param items       an iterable of objects to be added to the model
     * @param attributes  a list of attributes to be extracted from each item in the iterable of object
     */
    public <T> void addItems(Iterable<T> items, String[] attributes) {
        for (T item : items) {
            addItem(item, attributes);
        }
    }

    /**
     * Returns the model built from the data mapper
     *
     * @return the model built from the data mapper
     */
    public List<Map<String, String>> getModel() {
        return model;
    }

    /**
     * Returns a map created by extracting attributes from an object
     *
     * @param item        an objects to be converted into a map
     * @param attributes  a list of attributes to be extracted from each item in the iterable of object
     * @return a map created by extracting attributes from an object
     */
    public <T> Map<String, String> getItemMap(T item, String[] attributes) {
        Map<String, String> itemMap = new HashMap<>();
        for (String attribute : attributes) {
            itemMap.put(attribute, getAttr(item, attribute));
        }
        return itemMap;
    }
}
