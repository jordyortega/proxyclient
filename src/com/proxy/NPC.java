package com.proxy;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class NPC extends Entity
{

	private Model getAnimatedModel()
	{
		if(super.anim >= 0 && super.animationDelay == 0)
		{
			Animation animation = Animation.anims[super.anim];
			int currentFrame = animation.frameIDs[super.currentAnimFrame];
			int nextFrame = -1;

			if (super.nextAnimationFrame >= animation.frameIDs.length) {
				nextFrame = currentFrame;
			} else if (super.nextAnimationFrame != -1) {
				nextFrame = animation.frameIDs[super.nextAnimationFrame];
			}

			int cycle1 = animation.delays[super.currentAnimFrame];
			int cycle2 = super.anInt1528;
			//int frame = Animation.anims[super.anim].anIntArray353[super.anInt1527];
			int i1 = -1;
			if(super.entityAnimation >= 0 && super.entityAnimation != super.standAnim)
				i1 = Animation.anims[super.entityAnimation].frameIDs[super.currentForcedAnimFrame];
			return desc.method164(i1, currentFrame, Animation.anims[super.anim].animationFlowControl, nextFrame, cycle1, cycle2);
		}
		int currentFrame = -1;
		int nextFrame = -1;
		int cycle1 = 0;
		int cycle2 = 0;
		if(super.entityAnimation >= 0) {
			Animation animation = Animation.anims[super.entityAnimation];
			if(animation.frameIDs != null) {
				currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
				nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
			}
			if(animation.delays != null) {
				cycle1 = animation.delays[super.currentForcedAnimFrame];
			}
			cycle2 = super.anInt1519;
		}
		return desc.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
	}

	public Model getRotatedModel()
	{
		if(desc == null)
			return null;
		Model model = getAnimatedModel();
		if(model == null)
			return null;
		super.height = model.modelHeight;
		if(super.anInt1520 != -1 && super.currentAnim != -1)
		{
			SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
			Model model_1 = spotAnim.getModel();
			if(model_1 != null)
			{
				int j = spotAnim.animation.frameIDs[super.currentAnim];
				Model model_2 = new Model(true, FrameReader.isNullFrame(j), false, model_1);
				model_2.method475(0, -super.graphicHeight, 0);
				model_2.method469();
				model_2.applyTransform(j);
				model_2.triangleSkin = null;
				model_2.vertexSkin = null;
				if(spotAnim.anInt410 != 128 || spotAnim.anInt411 != 128)
					model_2.method478(spotAnim.anInt410, spotAnim.anInt410, spotAnim.anInt411);
				model_2.method479(64 + spotAnim.anInt413, 850 + spotAnim.anInt414, -30, -50, -30, true);
				Model aModel[] = {
						model, model_2
				};
				model = new Model(aModel);
			}
		}
		if(desc.aByte68 == 1)
			model.aBoolean1659 = true;
		return model;
	}

	public boolean isVisible()
	{
		return desc != null;
	}

	NPC()
	{
	}

	public EntityDef desc;
}
