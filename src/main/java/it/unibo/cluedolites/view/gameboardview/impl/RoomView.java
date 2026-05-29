package it.unibo.cluedolite.view.gameboardview.impl;

import java.awt.Rectangle;

public enum RoomView {

    KITCHEN  ("Kitchen",        0.02, 0.02, 0.23, 0.22, "src/main/resources/images/kitchen.png"),
    DINING   ("Dining Room",    0.02, 0.38, 0.23, 0.24, "src/main/resources/images/diningroom.png"),
    LOUNGE   ("Lounge",         0.02, 0.76, 0.23, 0.22,  "src/main/resources/images/lounge.png"),
    BALLROOM ("BallRoom",      0.29, 0.02, 0.22, 0.22, "src/main/resources/images/ballroom.png"),
    HALL     ("Hall",           0.39, 0.76, 0.22, 0.22, "src/main/resources/images/hall.png"),
    CONSERV  ("Conservatory",   0.55, 0.02, 0.16, 0.22, "src/main/resources/images/conservatory.png"),
    BILLIARD ("Billiard Room",  0.75, 0.02, 0.23, 0.22, "src/main/resources/images/billiardroom.png"),
    LIBRARY  ("Library",        0.75, 0.39, 0.23, 0.22, "src/main/resources/images/library.png"),
    STUDY    ("Study",          0.75, 0.76, 0.23, 0.22, "src/main/resources/images/study.png");
    
    public final String name;
    public final double x, y, width, height;
    public final String imagePath;
    
    RoomView(String name, double x, double y, double width, double height, String imagePath) {
        this.name = name;
        this.x   = x;
        this.y  = y;
        this.width = width;
        this.height= height;
        this.imagePath = imagePath;
    }

    //per cast da Room a RoomView
    public static RoomView fromName(String name) {
        for (RoomView rv : values()) {
            if (rv.name.equalsIgnoreCase(name)) return rv;
        }
        return null;
    }

    //Per sapere se il punto cliccato è dentro la stanza
    public Rectangle toRect(int panelW, int panelH) {
        return new Rectangle(
            (int)(x * panelW),
            (int)(y * panelH),
            (int)(width * panelW),
            (int)(height * panelH)
        );
    }
}