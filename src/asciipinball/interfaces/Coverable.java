package asciipinball.interfaces;

import asciipinball.shapes.Line;

/**
 * Bei Coverable PhysicEntitys werden die Enden der Linien mit JointCovern versehen um ein aufspießen des Balles zu verhindern
 * Kann nur von nicht beweglichen Polygon Entities implementiert werden
 */
public interface Coverable {
    /**
     * Gibt alle Linien aus denen das Objekt besteht zurück
     * @return Linien aus denen das Objekt besteht
     */
    Line[] getAllLines();
}
