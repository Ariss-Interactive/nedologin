package ru.marduk.nedologin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import ru.marduk.nedologin.NLConfig;
import ru.marduk.nedologin.NLConstants;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public final class SetPasswordScreen extends Screen {
    private final Screen parentScreen;

    private EditBox password;
    private Button buttonRandom;
    private Button buttonComplete;

    private Component title;
    private Component describe;

    public SetPasswordScreen(Screen parent) {
        super(Component.translatable("nedologin.password.title"));
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        // 获取使用的标题和描述
        String configTitle = NLConfig.CLIENT.pwdGuiTitle.get();
        String configDescribe = NLConfig.CLIENT.pwdGuiDesc.get();
        title = configTitle.isEmpty() ?
                Component.translatable("nedologin.password.title") : Component.literal(configTitle);
        describe = configDescribe.isEmpty() ?
                Component.translatable("nedologin.password.describe") : Component.literal(configDescribe);

        this.password = new EditBox(this.font,
                this.width / 2 - 100, this.height / 2, 170, 20,
                Component.translatable("nedologin.password"));
        this.password.setBordered(true);
        this.password.setEditable(true);
        this.password.setMaxLength(NLConstants.MAX_PASSWORD_LENGTH);
        this.password.setFilter((p) -> p.length() <= NLConstants.MAX_PASSWORD_LENGTH);
        this.password.setResponder((p) -> buttonComplete.active = !p.isEmpty());

        this.buttonRandom = this.addWidget(Button.builder(Component.literal("R"), btn ->
                        this.password.setValue(UUID.randomUUID().toString()))
                .bounds(this.width / 2 + 80, this.height / 2, 20, 20)
                .build());

        this.buttonComplete = this.addWidget(Button.builder(CommonComponents.GUI_DONE, btn -> {
            String password = this.password.getValue();
            if (!password.isEmpty()) {
                if (PasswordHolder.instance().initialized()) {
                    PasswordHolder.instance().setPendingPassword(password);
                    PasswordHolder.instance().applyPending();
                } else {
                    PasswordHolder.instance().initialize(password);
                }
                Minecraft.getInstance().setScreen(parentScreen);
            }
        }).bounds(this.width / 2 - 100, this.height / 2 + 40, 200, 20).build());

        this.buttonComplete.active = false;
        this.setInitialFocus(this.password);
    }

    @Override
    public void tick() {
        // this.password.();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String pwd = password.getValue();
        this.init(minecraft, width, height);
        this.password.setValue(pwd);
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.setFocused(this.password);
        this.password.setFocused(true);
        renderBackground(gui, mouseX, mouseY, partialTicks);

        int middle = width / 2;
        gui.drawCenteredString(font, title, middle, height / 4, 0xFFFFFF);

        gui.drawCenteredString(font, describe, middle, (height / 4) + 15, 0xAAAAAA);

        this.password.render(gui, mouseX, mouseY, partialTicks);
        this.buttonRandom.render(gui, mouseX, mouseY, partialTicks);
        this.buttonComplete.render(gui, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        if (!PasswordHolder.instance().initialized()) {
            if (!this.password.getValue().isEmpty()) {
                PasswordHolder.instance().initialize(this.password.getValue());
            } else {
                PasswordHolder.instance().initialize(UUID.randomUUID().toString());
            }
        }
        assert this.minecraft != null;
        Minecraft.getInstance().setScreen(parentScreen);
    }
}
