/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.midi.roland.tr8;

import java.util.HashMap;
import java.util.Map;

import com.rememberjava.midi.roland.tr8.Tr8Message.Tr8Controllers;

import javafx.scene.layout.*;
import javafx.scene.control.*;

@SuppressWarnings("restriction")
public class Tr8Ui extends Pane {

  private Map<Instrument, InstrumentControl> instruments = new HashMap<>();

  private Map<Tr8Controllers, Control> midiUiMap = new HashMap<>();

  void init() {
    initInstruments();
  }

  private void initInstruments() {
    HBox box = new HBox();

    for (Instrument i : Instrument.values()) {
      InstrumentControl ctrl = new InstrumentControl(i);
      box.getChildren().add(ctrl);
      instruments.put(i, ctrl);
      midiUiMap.putAll(ctrl.getMidiUiMap());
    }

    getChildren().add(box);
  }

  void recieve(Tr8Message msg) {
    String name = msg.getControlName();
    if (name == null) {
      return;
    }

    Tr8Controllers tr8Controller = Tr8Controllers.valueOf(name);
    if (!midiUiMap.containsKey(tr8Controller)) {
      return;
    }

    Control control = midiUiMap.get(tr8Controller);
    if (control instanceof Label) {
      ((Label) control).setText("" + msg.getControlValue());
    } else if (control instanceof Slider) {
      ((Slider) control).setValue(msg.getControlValue());
      System.out.println(((Slider) control).getValue() + " " + ((Slider) control).getMax());
    }
  }
}
