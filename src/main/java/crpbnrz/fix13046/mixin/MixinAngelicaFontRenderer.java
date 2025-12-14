package crpbnrz.fix13046.mixin;

import org.spongepowered.asm.mixin.*;
import com.gtnewhorizons.angelica.client.font.FontProviderUnicode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo //Should let the mod work without Angelica

@Mixin(FontProviderUnicode.class)
public class MixinAngelicaFontRenderer {
    @Shadow(remap = false)
    public byte[] glyphWidth;

    /**
     * @author CrpBnrz
     * @reason We just replace the entire method to fix "this.glyphWidth[chr] >>> 4" (corrupted vanilla logic) without precision loss regarding float value
     */
    @Overwrite(remap = false)
    public float getUStart(char chr) {
        final float startColumnF = (float)((this.glyphWidth[chr] & 255) >>> 4); //Fix from Vanilla 1.9
        return ((float) (chr % 16 * 16) + startColumnF + 0.21f) / 256.0f;
    }

    @Mutable
    @ModifyVariable(method = "getXAdvance", at = @At("STORE"), name = "startColumn", remap = false)
    private int modifyGetXAdvance(int stc0) {
        return stc0 & 0b1111;
    }

    @Mutable
    @ModifyVariable(method = "getGlyphW", at = @At("STORE"), name = "startColumn", remap = false)
    private int modifyGetGlyphW(int stc0) {
        return stc0 & 0b1111;
    }
    @Mutable
    @ModifyVariable(method = "getUSize", at = @At("STORE"), name = "startColumn", remap = false)
    private int modifyGetUSize(int stc0) {
        return stc0 & 0b1111;
    }
}
