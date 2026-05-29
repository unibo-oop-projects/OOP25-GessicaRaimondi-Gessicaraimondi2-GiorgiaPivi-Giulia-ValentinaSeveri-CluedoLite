package it.unibo.cluedolite.view;

import java.awt.Color;
import java.awt.Font;

/**
 * Centralized color palette for CluedoLite.
 * Use these constants everywhere instead of hardcoding Color values.
 */
public final class AppColorFont {

    private AppColorFont() { } // utility class, no instantiation

    // ----------------------------------------------------------------
    // Background
    // ----------------------------------------------------------------
    public static final Color BACKGROUND_DARK    = new Color(80,  0,  0);
    public static final Color BACKGROUND_MEDIUM  = new Color(100,  0,  0);
    public static final Color BACKGROUND_LIGHT   = new Color(120,  0,  0);

    // ----------------------------------------------------------------
    // Foreground / Text
    // ----------------------------------------------------------------
    public static final Color TEXT_PRIMARY       = new Color(240, 240, 240);
    public static final Color TEXT_SECONDARY     = new Color(180, 180, 180);
    public static final Color TEXT_DISABLED      = new Color(100, 100, 100);

    // ----------------------------------------------------------------
    // Accent
    // ----------------------------------------------------------------
    public static final Color ACCENT_PRIMARY     = new Color(180,  30,  30); // rosso Cluedo
    public static final Color ACCENT_SECONDARY   = new Color(255, 215,  0); // oro
    public static final Color ACCENT_HOVER       = new Color(220,  60,  60);

    // ----------------------------------------------------------------
    // Feedback
    // ----------------------------------------------------------------
    public static final Color SUCCESS            = new Color( 60, 180,  60);
    public static final Color WARNING            = new Color(220, 160,  40);
    public static final Color ERROR              = new Color(200,  40,  40);

    // ----------------------------------------------------------------
    // UI Components
    // ----------------------------------------------------------------
    public static final Color BUTTON_BACKGROUND  = ACCENT_PRIMARY;
    public static final Color BUTTON_FOREGROUND  = TEXT_PRIMARY;
    public static final Color BUTTON_HOVER       = ACCENT_HOVER;
    public static final Color BORDER             = new Color(100,  30,  30);
    public static final Color PANEL_BACKGROUND   = new Color(50,  0,  0);
    public static final Color DROPDOWN_BACKGROUND = new Color(100, 15, 15);
    public static final Color DROPDOWN_FOREGROUND = TEXT_PRIMARY;

    // ----------------------------------------------------------------
    // Font
    // ----------------------------------------------------------------
    public static final Font FONT_TITLE          = new Font("Serif", Font.BOLD, 36);
    public static final Font FONT_LABEL          = new Font("Serif", Font.BOLD, 30);
    public static final Font FONT_BUTTON         = new Font("Serif", Font.BOLD, 30);
    public static final Font FONT_DROPDOWN       = new Font("Serif", Font.BOLD, 15);
    public static final Font FONT_SMALL          = new Font("Serif", Font.PLAIN, 12);
    public static final Font FONT_BODY           = new Font("Serif", Font.PLAIN, 16);
}