package it.unibo.cluedolite.view;

import java.awt.Color;
import java.awt.Font;

/**
 * Centralized color palette and font definitions for CluedoLite.
 * Use these constants everywhere instead of hardcoding Color or Font values.
 */
public final class AppColorFont {

    private static final String FONT_SERIF = "Serif";

    // Background
    private static final int BG_DARK_R = 80;
    private static final int BG_MED_R = 100;
    private static final int BG_LIGHT_R = 120;
    private static final int PANEL_BG_R = 50;

    // Text
    private static final int TEXT_PRIMARY_V = 240;
    private static final int TEXT_SECONDARY_V = 180;
    private static final int TEXT_DISABLED_V = 100;

    // Accent
    private static final int ACCENT_PRIMARY_R = 180;
    private static final int ACCENT_PRIMARY_G = 30;
    private static final int ACCENT_SECONDARY_R = 255;
    private static final int ACCENT_SECONDARY_G = 215;
    private static final int ACCENT_HOVER_R = 220;
    private static final int ACCENT_HOVER_G = 60;

    // Feedback
    private static final int SUCCESS_G = 180;
    private static final int SUCCESS_R = 60;
    private static final int WARNING_R = 220;
    private static final int WARNING_G = 160;
    private static final int WARNING_B = 40;
    private static final int ERROR_R = 200;
    private static final int ERROR_G = 40;

    // Border / Dropdown
    private static final int BORDER_R = 100;
    private static final int BORDER_G = 30;
    private static final int DROPDOWN_R = 100;
    private static final int DROPDOWN_G = 15;
    private static final int DROPDOWN_B = 15;

    // Font sizes
    private static final int FONT_SIZE_TITLE = 36;
    private static final int FONT_SIZE_LABEL = 30;
    private static final int FONT_SIZE_DROPDOWN = 15;
    private static final int FONT_SIZE_SMALL = 12;
    private static final int FONT_SIZE_BODY = 16;

    // ----------------------------------------------------------------
    // Background
    // ----------------------------------------------------------------
    /** Dark background color. */
    public static final Color BACKGROUND_DARK = new Color(BG_DARK_R, 0, 0);
    /** Medium background color. */
    public static final Color BACKGROUND_MEDIUM = new Color(BG_MED_R, 0, 0);
    /** Light background color. */
    public static final Color BACKGROUND_LIGHT = new Color(BG_LIGHT_R, 0, 0);

    // ----------------------------------------------------------------
    // Foreground / Text
    // ----------------------------------------------------------------
    /** Primary text color. */
    public static final Color TEXT_PRIMARY = new Color(TEXT_PRIMARY_V, TEXT_PRIMARY_V, TEXT_PRIMARY_V);
    /** Secondary text color. */
    public static final Color TEXT_SECONDARY = new Color(TEXT_SECONDARY_V, TEXT_SECONDARY_V, TEXT_SECONDARY_V);
    /** Disabled text color. */
    public static final Color TEXT_DISABLED = new Color(TEXT_DISABLED_V, TEXT_DISABLED_V, TEXT_DISABLED_V);

    // ----------------------------------------------------------------
    // Accent
    // ----------------------------------------------------------------
    /** Primary accent color (Cluedo red). */
    public static final Color ACCENT_PRIMARY = new Color(ACCENT_PRIMARY_R, ACCENT_PRIMARY_G, ACCENT_PRIMARY_G);
    /** Secondary accent color (gold). */
    public static final Color ACCENT_SECONDARY = new Color(ACCENT_SECONDARY_R, ACCENT_SECONDARY_G, 0);
    /** Hover accent color. */
    public static final Color ACCENT_HOVER = new Color(ACCENT_HOVER_R, ACCENT_HOVER_G, ACCENT_HOVER_G);

    // ----------------------------------------------------------------
    // Feedback
    // ----------------------------------------------------------------
    /** Success feedback color. */
    public static final Color SUCCESS = new Color(SUCCESS_R, SUCCESS_G, SUCCESS_R);
    /** Warning feedback color. */
    public static final Color WARNING = new Color(WARNING_R, WARNING_G, WARNING_B);
    /** Error feedback color. */
    public static final Color ERROR = new Color(ERROR_R, ERROR_G, ERROR_G);

    // ----------------------------------------------------------------
    // UI Components
    // ----------------------------------------------------------------
    /** Button background color. */
    public static final Color BUTTON_BACKGROUND = ACCENT_PRIMARY;
    /** Button foreground color. */
    public static final Color BUTTON_FOREGROUND = TEXT_PRIMARY;
    /** Button hover color. */
    public static final Color BUTTON_HOVER = ACCENT_HOVER;
    /** Border color. */
    public static final Color BORDER = new Color(BORDER_R, BORDER_G, BORDER_G);
    /** Panel background color. */
    public static final Color PANEL_BACKGROUND = new Color(PANEL_BG_R, 0, 0);
    /** Dropdown background color. */
    public static final Color DROPDOWN_BACKGROUND = new Color(DROPDOWN_R, DROPDOWN_G, DROPDOWN_B);
    /** Dropdown foreground color. */
    public static final Color DROPDOWN_FOREGROUND = TEXT_PRIMARY;

    // ----------------------------------------------------------------
    // Font
    // ----------------------------------------------------------------
    /** Title font. */
    public static final Font FONT_TITLE = new Font(FONT_SERIF, Font.BOLD, FONT_SIZE_TITLE);
    /** Label font. */
    public static final Font FONT_LABEL = new Font(FONT_SERIF, Font.BOLD, FONT_SIZE_LABEL);
    /** Button font. */
    public static final Font FONT_BUTTON = new Font(FONT_SERIF, Font.BOLD, FONT_SIZE_LABEL);
    /** Dropdown font. */
    public static final Font FONT_DROPDOWN = new Font(FONT_SERIF, Font.BOLD, FONT_SIZE_DROPDOWN);
    /** Small font. */
    public static final Font FONT_SMALL = new Font(FONT_SERIF, Font.PLAIN, FONT_SIZE_SMALL);
    /** Body font. */
    public static final Font FONT_BODY = new Font(FONT_SERIF, Font.PLAIN, FONT_SIZE_BODY);

    private AppColorFont() {
    }
}