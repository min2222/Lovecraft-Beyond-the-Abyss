package com.min01.beyondtheabyss.entity;

public interface IDialogue
{
	public void trigger(int chatIndex);
	
	public void onClose(int chatIndex);
	
	public void setChatIndex(int chatIndex);
	
	public int getChatIndex();
	
	public void setPrevChatIndex(int chatIndex);
	
	public int getPrevChatIndex();
}
