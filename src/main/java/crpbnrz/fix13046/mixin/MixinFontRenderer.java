package crpbnrz.fix13046.mixin;

import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {
    @ModifyVariable(method = "getCharWidth", at = @At("STORE"), name = "j")
    private int modifyJ(int j0) {
        return j0 & 0b1111;
    }

    @ModifyVariable(method = "renderUnicodeChar", at = @At("STORE"), name = "j")
    private int modifyJ2(int j0) {
        return j0 & 0b1111;
    }
}