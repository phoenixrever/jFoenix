package demos.myConponent;

import com.jfoenix.controls.JFXChipView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;


public class Chip<T> extends Region {

    private final ObjectProperty<T> item = new SimpleObjectProperty<T>(this, "item");

    public Chip(T item) {
        getStyleClass().add("jfx-chip");
        setItem(item);
    }

    public final ObjectProperty<T> itemProperty() { return item; }

    public final void setItem(T value) { item.set(value); }

    public final T getItem() { return item.get(); }
}
