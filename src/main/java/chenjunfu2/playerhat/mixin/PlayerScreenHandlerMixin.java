package chenjunfu2.playerhat.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//注入点为PlayerScreenHandler第一个匿名类$1，也就是Slot的匿名子类
@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public class PlayerScreenHandlerMixin
{
	//获取匿名类的捕获字段
	@Shadow @Final
	EquipmentSlot field_7834;//equipmentSlot
	
	//允许在头上放东西
	@Inject(
		method = "canInsert",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	private void canInsertInject(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
	{
		if(field_7834 == EquipmentSlot.HEAD)
		{
			cir.setReturnValue(true);
			cir.cancel();
		}
		
		//如若不是，则走原先处理
	}
	
	//解锁放物品个数限制
	@Inject(
		method = "getMaxItemCount",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	private void getMaxItemCountInject(CallbackInfoReturnable<Integer> cir)
	{
		if(field_7834 == EquipmentSlot.HEAD)
		{
			cir.setReturnValue(((Slot)(Object)this).inventory.getMaxCountPerStack());
			cir.cancel();
		}
	}
}