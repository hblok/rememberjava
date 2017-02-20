/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi.roland.tr8;

import java.util.HashMap;
import java.util.Map;

import com.rememberjava.midi.roland.tr8.Tr8Message.Tr8Controllers;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

@SuppressWarnings("restriction")
class InstrumentControl extends Control {

  private final Instrument instrument;

  private Slider slider;

  private Map<InstrumentKnob, Label> knobs = new HashMap<>();
  
  private Map<Tr8Controllers, Control> midiUiMap = new HashMap<>();

  InstrumentControl(Instrument instrument) {
    this.instrument = instrument;

    init();
  }

  private void init() {
    VBox root = new VBox();
    root.setAlignment(Pos.CENTER);
    getChildren().add(root);
    ObservableList<Node> children = root.getChildren();

    Label name = new Label(instrument.getFormattedName());
    name.setFont(Font.font("FreeMono", FontWeight.BOLD, 14));
    children.add(name);

    GridPane knobBox = new GridPane();
    knobBox.setAlignment(Pos.CENTER);
    children.add(knobBox);
    for (int i = 0; i < instrument.getKnobs().size(); i++) {
      Label label = new Label("000");
      label.setFont(Font.font("FreeMono", FontWeight.LIGHT, 12));
      label.setMinWidth(20);
      InstrumentKnob knob = instrument.getKnobs().get(i);
      knobs.put(knob, label);
      knobBox.add(label, (i + 2) / 2, (i % 2) + 1);
      
      Tr8Controllers tr8Controller = Tr8Controllers.valueOf(instrument.getMidiName(knob));
      midiUiMap.put(tr8Controller, label);
    }

    slider = new Slider();
    slider.setOrientation(Orientation.VERTICAL);
    children.add(slider);
    Tr8Controllers tr8Controller = Tr8Controllers.valueOf(instrument.getMidiName(InstrumentKnob.VOLUME));
    midiUiMap.put(tr8Controller, slider);
  }

  @Override
  protected Skin<?> createDefaultSkin() {
    return new InstrumentSkin(this);
  }

  private class InstrumentSkin extends SkinBase<InstrumentControl> {

    protected InstrumentSkin(InstrumentControl control) {
      super(control);
    }

    @Override
    public void dispose() {

    }
  }

  public Map<? extends Tr8Controllers, ? extends Control> getMidiUiMap() {
    return midiUiMap;
  }
}
