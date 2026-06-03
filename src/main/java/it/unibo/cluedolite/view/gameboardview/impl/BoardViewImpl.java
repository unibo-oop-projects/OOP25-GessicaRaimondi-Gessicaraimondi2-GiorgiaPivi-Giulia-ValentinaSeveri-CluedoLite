package it.unibo.cluedolite.view.gameboardview.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.cluedolite.controller.gameboardcontroller.api.GameBoardController;
import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.view.gameboardview.api.BoardView;

/**
 * Swing panel that renders the Cluedo Lite game board,
 * including rooms, player tokens, and the center label.
 */
@SuppressFBWarnings(
    value = { "SE_BAD_FIELD", "EI_EXPOSE_REP2" },
    justification = "Board view is not serialized; controller reference is intentionally stored"
)
public final class BoardViewImpl extends JPanel implements BoardView {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER =
            Logger.getLogger(BoardViewImpl.class.getName());

    private static final String FONT_NAME = "Serif";
    private static final String TITLE_MAIN = "CLUEDO";
    private static final String TITLE_SUB = "Lite";

    private static final double TOKEN_SIZE_RATIO = 0.025;
    private static final double PADDING_RATIO = 0.01;
    private static final double CENTER_W_RATIO = 0.20;
    private static final double CENTER_H_RATIO = 0.15;
    private static final double TITLE_FONT_RATIO = 0.05;
    private static final double ROOM_FONT_RATIO = 0.018;
    private static final double SUBTITLE_FONT_RATIO = 0.03;

    private static final int LABEL_FONT_SIZE = 14;
    private static final int LABEL_ARC = 6;
    private static final int LABEL_PAD = 4;
    private static final int SHADOW_OFFSET = 2;
    private static final int ROOM_NAME_TOP_PAD = 8;
    private static final int TEXT_Y_OFFSET = 1;

    private static final float TOKEN_BORDER_STROKE = 3f;

    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 150);
    private static final Color ROOM_BG_COLOR = new Color(0, 0, 0, 100);
    private static final Color LABEL_BG_COLOR = new Color(0, 0, 0, 180);

    // Color components to avoid magic numbers in parseNamedColor
    private static final int RED_R = 178;
    private static final int RED_G = 34;
    private static final int RED_B = 34;

    private static final int GREEN_R = 34;
    private static final int GREEN_G = 139;
    private static final int GREEN_B = 34;

    private static final int BLUE_R = 30;
    private static final int BLUE_G = 144;
    private static final int BLUE_B = 255;

    private static final int YELLOW_R = 218;
    private static final int YELLOW_G = 165;
    private static final int YELLOW_B = 32;

    private static final int PURPLE_R = 128;
    private static final int PURPLE_G = 0;
    private static final int PURPLE_B = 128;

    private static final int ORANGE_R = 255;
    private static final int ORANGE_G = 140;
    private static final int ORANGE_B = 0;

    private final List<Player> players;
    private final GameBoardController controller;
    private Image backgroundImg;
    private final Map<RoomView, Image> roomImages =
            new EnumMap<>(RoomView.class);

    /**
     * Constructs the board view, loading images and registering the mouse listener.
     *
     * @param p the list of players in the game
     * @param c the game board controller
     */
    public BoardViewImpl(final List<Player> p, final GameBoardController c) {
        this.controller = c;
        this.players = new ArrayList<>(p);

        try {
            backgroundImg = ImageIO.read(
                new File("src/main/resources/images/floor.png")
            );
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load background image", e);
        }

        for (final RoomView roomView : RoomView.values()) {
            try {
                roomImages.put(
                    roomView,
                    ImageIO.read(new File(roomView.getImagePath()))
                );
            } catch (final IOException e) {
                LOGGER.log(
                    Level.WARNING,
                    "Failed to load image for room: " + roomView.getName(),
                    e
                );
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                handleClick(e.getPoint());
            }
        });
    }

    /**
     * Renders the board panel, drawing the background, rooms,
     * center label and player tokens.
     *
     * @param g the graphics context provided by Swing
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), null);

        drawRooms(g2);
        drawCenter(g2);
        drawPlayers(g2);
    }

    /**
     * Draws all rooms on the board with their background color,
     * image and name label.
     *
     * @param g2 the 2D graphics context used for rendering
     */
    private void drawRooms(final Graphics2D g2) {
        for (final RoomView roomView : RoomView.values()) {
            final int x = (int) (roomView.getX() * getWidth());
            final int y = (int) (roomView.getY() * getHeight());
            final int w = (int) (roomView.getWidth() * getWidth());
            final int h = (int) (roomView.getHeight() * getHeight());

            g2.setColor(Color.PINK);
            g2.fillRect(x, y, w, h);

            final Image img = roomImages.get(roomView);
            if (img != null) {
                g2.drawImage(img, x, y, w, h, null);
            }

            g2.setFont(
                new Font(FONT_NAME, Font.BOLD,
                    (int) (getWidth() * ROOM_FONT_RATIO))
            );
            final FontMetrics fm = g2.getFontMetrics();

            final int tx = x + (w - fm.stringWidth(roomView.getName())) / 2;
            final int ty = y + fm.getAscent() + LABEL_PAD + TEXT_Y_OFFSET;

            g2.setColor(ROOM_BG_COLOR);
            g2.fillRect(x, y, w, fm.getHeight() + ROOM_NAME_TOP_PAD);

            g2.setColor(SHADOW_COLOR);
            g2.drawString(
                roomView.getName(),
                tx + SHADOW_OFFSET,
                ty + SHADOW_OFFSET
            );

            g2.setColor(Color.WHITE);
            g2.drawString(roomView.getName(), tx, ty);
        }
    }

    /**
     * Draws the center area of the board with the "CLUEDO Lite" title.
     *
     * @param g2 the 2D graphics context used for rendering
     */
    private void drawCenter(final Graphics2D g2) {
        final Rectangle centerRect = getCenterRect();

        g2.setFont(
            new Font(FONT_NAME, Font.BOLD,
                (int) (getWidth() * TITLE_FONT_RATIO))
        );
        FontMetrics fm = g2.getFontMetrics();

        final int cluedoX =
            centerRect.x + (centerRect.width - fm.stringWidth(TITLE_MAIN)) / 2;
        final int cluedoY = centerRect.y + centerRect.height / 2;

        g2.setColor(SHADOW_COLOR);
        g2.drawString(
            TITLE_MAIN,
            cluedoX + SHADOW_OFFSET,
            cluedoY + SHADOW_OFFSET
        );

        g2.setColor(Color.WHITE);
        g2.drawString(TITLE_MAIN, cluedoX, cluedoY);

        g2.setFont(
            new Font(FONT_NAME, Font.BOLD,
                (int) (getWidth() * SUBTITLE_FONT_RATIO))
        );
        fm = g2.getFontMetrics();

        final int liteX =
            centerRect.x + (centerRect.width - fm.stringWidth(TITLE_SUB)) / 2;
        final int liteY = cluedoY + fm.getHeight();

        g2.setColor(SHADOW_COLOR);
        g2.drawString(
            TITLE_SUB,
            liteX + SHADOW_OFFSET,
            liteY + SHADOW_OFFSET
        );

        g2.setColor(Color.WHITE);
        g2.drawString(TITLE_SUB, liteX, liteY);
    }

    /**
     * Draws a colored token for each player on the board.
     * Players not yet placed are shown below the center area.
     *
     * @param g2 the 2D graphics context used for rendering
     */
    private void drawPlayers(final Graphics2D g2) {
        final int size = (int) (Math.min(getWidth(), getHeight())
                * TOKEN_SIZE_RATIO);
        final int padding = (int) (getWidth() * PADDING_RATIO);

        int centerIndex = 0;

        for (final Player p : players) {
            final Room currentRoom = controller.getCurrentRoomOf(p);

            if (currentRoom == null) {
                final Rectangle cr = getCenterRect();
                final int x = cr.x + padding
                        + centerIndex * (size + padding);
                final int y = cr.y + cr.height + padding;

                drawToken(g2, p, x, y, size);
                centerIndex++;
            } else {
                final RoomView roomView =
                    RoomView.fromName(currentRoom.getName());

                if (roomView == null) {
                    continue;
                }

                int roomIndex = 0;
                int count = 0;

                for (final Player other : players) {
                    if (currentRoom.equals(
                        controller.getCurrentRoomOf(other))) {

                        if (other.equals(p)) {
                            roomIndex = count;
                        }
                        count++;
                    }
                }

                final int cx = (int) (roomView.getX() * getWidth()) + padding;
                final int cy = (int) (roomView.getY() * getHeight())
                        + (int) (roomView.getHeight() * getHeight()) / 2;

                drawToken(
                    g2,
                    p,
                    cx + roomIndex % 2 * (size + padding),
                    cy + roomIndex / 2 * (size + padding),
                    size
                );
            }
        }
    }

    /**
     * Handles a mouse click on the board.
     * If the click falls inside a room, forwards the move request
     * to the controller.
     *
     * @param p the point clicked by the user
     */
    private void handleClick(final Point p) {
        for (final RoomView roomView : RoomView.values()) {
            if (roomView.toRect(getWidth(), getHeight()).contains(p)) {
                controller.move(controller.getRoomByName(roomView.getName()));
                return;
            }
        }
    }

    /**
     * Computes and returns the rectangle representing
     * the center area of the board.
     *
     * @return the center rectangle
     */
    private Rectangle getCenterRect() {
        final int w = (int) (getWidth() * CENTER_W_RATIO);
        final int h = (int) (getHeight() * CENTER_H_RATIO);
        final int x = (getWidth() - w) / 2;
        final int y = (getHeight() - h) / 2;

        return new Rectangle(x, y, w, h);
    }

    /**
     * Draws a single player token as a colored circle.
     * The active player is highlighted with a black border and name label.
     *
     * @param g2   the 2D graphics context used for rendering
     * @param p    the player whose token is being drawn
     * @param x    the x coordinate of the token
     * @param y    the y coordinate of the token
     * @param size the diameter of the token in pixels
     */
    private void drawToken(
        final Graphics2D g2,
        final Player p,
        final int x,
        final int y,
        final int size
    ) {
        final String raw = p.getCharacter().getColor();

        try {
            g2.setColor(
                raw.startsWith("#")
                    ? Color.decode(raw)
                    : parseNamedColor(raw)
            );
        } catch (final NumberFormatException e) {
            g2.setColor(Color.GRAY);
        }

        g2.fillOval(x, y, size, size);

        if (p.equals(controller.currentPlayer())) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(TOKEN_BORDER_STROKE));
            g2.drawOval(x, y, size, size);

            g2.setFont(new Font(FONT_NAME, Font.BOLD, LABEL_FONT_SIZE));
            final FontMetrics fm = g2.getFontMetrics();

            final String label = "Player " + (players.indexOf(p) + 1);
            final int lw = fm.stringWidth(label) + LABEL_PAD * 2;
            final int lh = fm.getHeight() + LABEL_PAD;

            final int lx = x + (size - lw) / 2;
            final int ly = y - lh - LABEL_PAD;

            g2.setColor(LABEL_BG_COLOR);
            g2.fillRoundRect(lx, ly, lw, lh, LABEL_ARC, LABEL_ARC);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(lx, ly, lw, lh, LABEL_ARC, LABEL_ARC);

            g2.drawString(
                label,
                lx + LABEL_PAD,
                ly + fm.getAscent() + SHADOW_OFFSET
            );
        }
    }

    /**
     * Resolves a named color string to a Color object.
     *
     * @param name the color name
     * @return the corresponding Color, or gray if unrecognized
     */
    private Color parseNamedColor(final String name) {
        return switch (name.toUpperCase(Locale.ROOT)) {
            case "RED" -> new Color(RED_R, RED_G, RED_B);
            case "GREEN" -> new Color(GREEN_R, GREEN_G, GREEN_B);
            case "BLUE" -> new Color(BLUE_R, BLUE_G, BLUE_B);
            case "YELLOW" -> new Color(YELLOW_R, YELLOW_G, YELLOW_B);
            case "WHITE" -> Color.WHITE;
            case "BLACK" -> Color.BLACK;
            case "PURPLE" -> new Color(PURPLE_R, PURPLE_G, PURPLE_B);
            case "ORANGE" -> new Color(ORANGE_R, ORANGE_G, ORANGE_B);
            default -> Color.GRAY;
        };
    }
}
