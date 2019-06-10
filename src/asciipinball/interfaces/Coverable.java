package asciipinball.interfaces;

import asciipinball.shapes.Line;

//Can be only used by not moving entities
public interface Coverable {
    Line[] getAllLines();
}
