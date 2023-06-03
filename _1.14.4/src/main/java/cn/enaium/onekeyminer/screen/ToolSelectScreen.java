/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.enaium.onekeyminer.screen;

import cn.enaium.onekeyminer.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

/**
 * @author Enaium
 */
public class ToolSelectScreen extends Screen {
    public ToolSelectScreen() {
        super(new LiteralText(""));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderBackground();

        addButton(new ButtonWidget(0, 0, 200, 20, new TranslatableText("button.axe").asString(), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(Config.getModel().axe));
        }));

        addButton(new ButtonWidget(0, 30, 200, 20, new TranslatableText("button.hoe").asString(), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(Config.getModel().hoe));
        }));

        addButton(new ButtonWidget(0, 60, 200, 20, new TranslatableText("button.pickaxe").asString(), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(Config.getModel().pickaxe));
        }));

        addButton(new ButtonWidget(0, 90, 200, 20, new TranslatableText("button.shovel").asString(), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(Config.getModel().shovel));
        }));

        addButton(new ButtonWidget(0, 120, 200, 20, new TranslatableText("button.shears").asString(), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(Config.getModel().shears));
        }));

        super.render(mouseX, mouseY, delta);
    }
}
