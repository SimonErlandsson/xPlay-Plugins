package net.vouchs.xPlay.utilities.strings;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ConvertString
{

	public boolean bold, italic, underline, strike, magic, lastCharSection = false;
	public StringBuilder builder = new StringBuilder();
	public ComponentBuilder comp = new ComponentBuilder("");
	public ChatColor color;

	public ConvertString(String message)
	{
		for (char c : message.toCharArray())
		{
			// If section sign detected call for char processing on next char
			if (c == '§')
			{
				lastCharSection = true;
				continue;
			}

			// If last Character was section sign
			if (lastCharSection)
			{
				lastCharSection = false;

				// If passes processing format code
				if (processFormatCodeContains(c))
				{
					continue;
				}

				ChatColor color = ChatColor.getByChar(c);
				if (color != null)
				{
					// Finalize current Section set ChatColor for next
					finalizeSection();
					this.color = color;

					continue;
				}
			}

			builder.append(c);
		}

		finalizeSection();
	}

	private boolean processFormatCodeContains(char c)
	{
		switch (c)
		{
			case 'k':
				return (magic = true);

			case 'l':
				return (bold = true);

			case 'm':
				return (strike = true);

			case 'n':
				return (underline = true);

			case 'o':
				return (italic = true);

			case 'r':
				finalizeSection();
				color = null;
				return true;
		}

		return false;
	}

	private void finalizeSection()
	{
		// Add current line and formatting
		comp.append(builder.toString());

		comp.bold(bold);
		comp.strikethrough(strike);
		comp.underlined(underline);
		comp.italic(italic);
		comp.obfuscated(magic);

		if (color != null)
		{
			comp.color(color);
		}

		// Reset formatting & StringBuilder (Keeps Color)
		magic = false;
		bold = false;
		strike = false;
		underline = false;
		italic = false;

		builder = new StringBuilder();
	}

	public BaseComponent[] toComponent()
	{
		return comp.create();
	}

	public static BaseComponent[] getComponent(String message)
	{
		return new ConvertString(message).toComponent();
	}
}
