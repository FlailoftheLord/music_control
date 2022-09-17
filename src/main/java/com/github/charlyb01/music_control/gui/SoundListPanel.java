package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.categories.Music;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.github.charlyb01.music_control.categories.Music.*;

public class SoundListPanel extends WBox {
    protected static final int HEIGHT = 125;

    public SoundListPanel(final BiConsumer<Identifier, WButton> onMusicClicked,
                          final BiConsumer<Identifier, WButton> onEventClicked,
                          final Consumer<Boolean> onToggle,
                          final int width, final boolean isToggled) {
        super(Axis.VERTICAL);

        ArrayList<Identifier> musics = new ArrayList<>(MUSIC_BY_NAMESPACE.get(ALL_MUSICS).size());
        for (Music music : MUSIC_BY_NAMESPACE.get(ALL_MUSICS)) {
            musics.add(music.getIdentifier());
        }

        ButtonListPanel musicListPanel = new ButtonListPanel(musics, onMusicClicked, width, HEIGHT);
        ButtonListPanel eventListPanel = new ButtonListPanel(EVENTS, onEventClicked, width, HEIGHT);
        WCardPanel listPanel = new WCardPanel();
        listPanel.add(musicListPanel);
        listPanel.add(eventListPanel);
        if (isToggled) {
            listPanel.setSelectedCard(eventListPanel);
        }

        WToggleButton toggleButton = new WToggleButton(Text.translatable("gui.music_control.toggle.musicEvent"));
        toggleButton.setToggle(isToggled);
        toggleButton.setOnToggle((Boolean isEvent) -> {
            onToggle.accept(isEvent);
            if (isEvent) {
                listPanel.setSelectedCard(eventListPanel);
            } else {
                listPanel.setSelectedCard(musicListPanel);
            }
        });

        this.add(toggleButton);
        this.add(listPanel);
    }
}
