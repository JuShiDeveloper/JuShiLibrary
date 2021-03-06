package com.jushi.library.lottie.parser;

import android.util.JsonReader;


import com.jushi.library.lottie.lottie.LottieComposition;
import com.jushi.library.lottie.model.animatable.AnimatableFloatValue;
import com.jushi.library.lottie.model.animatable.AnimatableTransform;
import com.jushi.library.lottie.model.content.Repeater;

import java.io.IOException;

class RepeaterParser {

  private RepeaterParser() {}

  static Repeater parse(
      JsonReader reader, LottieComposition composition) throws IOException {
    String name = null;
    AnimatableFloatValue copies = null;
    AnimatableFloatValue offset = null;
    AnimatableTransform transform = null;
    boolean hidden = false;

    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "nm":
          name = reader.nextString();
          break;
        case "c":
          copies = AnimatableValueParser.parseFloat(reader, composition, false);
          break;
        case "o":
          offset = AnimatableValueParser.parseFloat(reader, composition, false);
          break;
        case "tr":
          transform = AnimatableTransformParser.parse(reader, composition);
          break;
        case "hd":
          hidden = reader.nextBoolean();
          break;
        default:
          reader.skipValue();
      }
    }

    return new Repeater(name, copies, offset, transform, hidden);
  }
}
