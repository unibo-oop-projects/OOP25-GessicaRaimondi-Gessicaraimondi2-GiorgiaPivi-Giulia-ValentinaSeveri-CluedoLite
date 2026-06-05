package it.unibo.cluedolite.view.gameboardview.impl;

import java.awt.Rectangle;

/**
 * Enum representing each room on the game board,
 * with its relative position, size, and associated image path.
 */
public enum RoomView {

    KITCHEN(
        "Kitchen",
        0.02, 0.02, 0.23, 0.22,
        "/images/kitchen.png"
    ),
    DINING(
        "Dining Room",
        0.02, 0.38, 0.23, 0.24,
        "/images/diningroom.png"
    ),
    LOUNGE(
        "Lounge",
        0.02, 0.76, 0.23, 0.22,
        "/images/lounge.png"
    ),
    BALLROOM(
        "BallRoom",
        0.29, 0.02, 0.22, 0.22,
        "/images/ballroom.png"
    ),
    HALL(
        "Hall",
        0.39, 0.76, 0.22, 0.22,
        "/images/hall.png"
    ),
    CONSERVATORY(
        "Conservatory",
        0.55, 0.02, 0.16, 0.22,
        "/images/conservatory.png"
    ),
    BILLIARD(
        "Billiard Room",
        0.75, 0.02, 0.23, 0.22,
        "/images/billiardroom.png"
    ),
    LIBRARY(
        "Library",
        0.75, 0.39, 0.23, 0.22,
        "/images/library.png"
    ),
    STUDY(
        "Study",
        0.75, 0.76, 0.23, 0.22,
        "/images/study.png"
    );

    /** Display name of the room. */
    private final String name;

    /** Relative X position (0.0–1.0). */
    private final double x;

    /** Relative Y position (0.0–1.0). */
    private final double y;

    /** Relative width (0.0–1.0). */
    private final double width;

    /** Relative height (0.0–1.0). */
    private final double height;

    /** File path to the room image. */
    private final String imagePath;

    RoomView(
        final String name,
        final double x,
        final double y,
        final double width,
        final double height,
        final String imagePath
    ) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }

    /**
     * Returns the display name of the room.
     *
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the relative X position.
     *
     * @return the relative X (0.0–1.0)
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the relative Y position.
     *
     * @return the relative Y (0.0–1.0)
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the relative width.
     *
     * @return the relative width (0.0–1.0)
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the relative height.
     *
     * @return the relative height (0.0–1.0)
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the file path to the room image.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Returns the RoomView matching the given name (case-insensitive),
     * or null if none matches.
     *
     * @param name the room name to look up
     * @return the matching RoomView, or null if not found
     */
    public static RoomView fromName(final String name) {
        for (final RoomView roomView : values()) {
            if (roomView.name.equalsIgnoreCase(name)) {
                return roomView;
            }
        }
        return null;
    }

    /**
     * Returns the absolute pixel rectangle for this room
     * given the panel dimensions.
     *
     * @param panelW panel width in pixels
     * @param panelH panel height in pixels
     * @return the bounding Rectangle of this room
     */
    public Rectangle toRect(final int panelW, final int panelH) {
        return new Rectangle(
            (int) (x * panelW),
            (int) (y * panelH),
            (int) (width * panelW),
            (int) (height * panelH)
        );
    }
}
