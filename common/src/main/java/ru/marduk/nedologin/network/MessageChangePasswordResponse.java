package ru.marduk.nedologin.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;

public record MessageChangePasswordResponse(boolean status) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageChangePasswordResponse> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NLConstants.MODID, "m_change_pwd_res"));
    public static final StreamCodec<ByteBuf, MessageChangePasswordResponse> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            MessageChangePasswordResponse::status,
            MessageChangePasswordResponse::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
