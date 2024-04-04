package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import com.github.charlyb01.music_control.client.MusicControlClient;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.components.LongTextButton;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PlayPanel extends WBox {
    protected final static Text NONE_TEXT = Text.translatable("music.none");
    protected final static String SELECTED_KEY = "gui.music_control.label.selected";
    protected static boolean isEvent = false;

    protected LongTextButton hoveredButton;

    public PlayPanel() {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);

        WLabel selected = new WLabel(Text.translatable(SELECTED_KEY, NONE_TEXT));
        selected.setHorizontalAlignment(HorizontalAlignment.CENTER);

        BiConsumer<Identifier, LongTextButton> onSoundClicked = (Identifier identifier, LongTextButton button) -> {
            if (identifier.equals(MusicControlClient.musicSelected)) {
                MusicControlClient.nextMusic = false;
                MusicControlClient.musicSelected = null;
                selected.setText(Text.translatable(SELECTED_KEY, NONE_TEXT));
            } else {
                MusicControlClient.nextMusic = true;
                MusicControlClient.musicSelected = identifier;
                selected.setText(Text.translatable(SELECTED_KEY, Music.getTranslatedText(identifier)));
            }

            if (hoveredButton != null) {
                hoveredButton.releaseFocus();
                if (hoveredButton.equals(button)) {
                    hoveredButton = null;
                } else {
                    hoveredButton = button;
                    hoveredButton.requestFocus();
                }
            } else {
                hoveredButton = button;
                hoveredButton.requestFocus();
            }
        };

        Consumer<Boolean> onToggle = (Boolean isEvent) -> {
            PlayPanel.isEvent = isEvent;
            if (hoveredButton != null) {
                hoveredButton.requestFocus();
            }
        };

        this.add(new SoundListPanel(onSoundClicked, onSoundClicked, onToggle, ModConfig.get().cosmetics.gui.width, PlayPanel.isEvent));
        this.add(selected, ModConfig.get().cosmetics.gui.width, 20);
    }
}
