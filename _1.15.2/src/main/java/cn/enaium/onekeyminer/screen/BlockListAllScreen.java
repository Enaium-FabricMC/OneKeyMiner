/**
 * Copyright (C) 2022 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.onekeyminer.screen;

import cn.enaium.onekeyminer.OneKeyMiner;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class BlockListAllScreen extends Screen {

    private ListWidget<BlockListWidget.Entry> entryListWidget;
    private TextFieldWidget textFieldWidget;
    private ButtonWidget addButton;

    private final Screen parent;
    private final List<String> list;

    public BlockListAllScreen(Screen parent, List<String> list) {
        super(new LiteralText(""));
        this.parent = parent;
        this.list = list;
    }

    @Override
    public void init() {
        entryListWidget = new ListWidget<>(minecraft, width, height, 50, height - 50, 24);
        textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, "");
        addButton = new ButtonWidget(width / 2 - 100, height - 35, 200, 20, new TranslatableText("button.add").asString(), e -> {
            BlockListWidget.Entry selectedOrNull = entryListWidget.getSelected();
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name);
                OneKeyMiner.save();
                MinecraftClient.getInstance().openScreen(parent);
            }
        });
        get().forEach(entryListWidget::addEntry);
        textFieldWidget.setChangedListener(s -> {
            entryListWidget.replaceEntries(get());
            entryListWidget.setSelected(null);
        });
        children.add(entryListWidget);
        children.add(textFieldWidget);
        addButton(addButton);
        super.init();
    }

    public List<BlockListWidget.Entry> get() {
        return Registry.BLOCK.stream().filter(it -> {
            if (!textFieldWidget.getText().equals("")) {
                return (it.asItem().toString().contains(textFieldWidget.getText()) || new TranslatableText(it.asItem().getTranslationKey()).getString().contains(textFieldWidget.getText()));
            } else {
                return true;
            }
        }).map(it -> new BlockListWidget.Entry(Registry.ITEM.getId(it.asItem()).toString())).collect(Collectors.toList());
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderBackground();
        addButton.active = entryListWidget.getSelected() != null;
        entryListWidget.render(mouseX, mouseY, delta);
        textFieldWidget.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
    }
}
