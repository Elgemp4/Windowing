package be.groupe18.windowing.presentation.components;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class NumericTextField extends TextField {

  private final DoubleProperty value = new SimpleDoubleProperty(0.0);

  public DoubleProperty valueProperty() {
    return value;
  }

  public double getValue() {
    return value.get();
  }

  public void setValue(double v) {
    value.set(v);
  }

  public NumericTextField() {
    super();
    StringConverter<Double> customConverter = new StringConverter<>() {
      @Override
      public String toString(Double object) {
        if (object == null) return "";
        if (object == Double.POSITIVE_INFINITY) return "+inf";
        if (object == Double.NEGATIVE_INFINITY) return "-inf";
        if (object == object.intValue()) return String.valueOf(
          object.intValue()
        );
        return object.toString();
      }

      @Override
      public Double fromString(String string) {
        if (string == null || string.isEmpty()) return 0.0;

        String lower = string.toLowerCase();
        if (
          lower.equals("inf") || lower.equals("+inf")
        ) return Double.POSITIVE_INFINITY;
        if (lower.equals("-inf")) return Double.NEGATIVE_INFINITY;

        if (string.matches("[+-]?(i(n)?)?|\\.|[+-]\\.")) return 0.0;

        try {
          return Double.valueOf(string);
        } catch (NumberFormatException e) {
          return 0.0;
        }
      }
    };

    TextFormatter<Double> formatter = new TextFormatter<>(
      customConverter,
      0.0,
      change -> {
        String newText = change.getControlNewText();
        if (newText.matches("(?i)^[+-]?([0-9]*\\.?[0-9]*|i(n(f)?)?)$")) {
          return change;
        }
        return null;
      }
    );

    this.setTextFormatter(formatter);
    formatter.valueProperty().bindBidirectional(this.value.asObject());
  }
}
