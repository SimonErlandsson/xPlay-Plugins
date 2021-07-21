package net.vouchs.arbete.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.ByteStreams;

import net.vouchs.arbete.Core;

public class FileConfig
{
	private String fileName;
	private File configFile;
	private FileConfiguration config;

	public FileConfig(Core core, String filePath, String fileName)
	{
		this.fileName = fileName;
		this.configFile = new File(core.getDataFolder() + "/" + filePath, fileName);

		if (!configFile.exists())
		{
			configFile.getParentFile().mkdirs();

			if (core.getResource(fileName) == null)
			{
				try
				{
					configFile.createNewFile();
				}
				catch (IOException e)
				{
					core.getLogger().severe("Failed to create new file " + fileName);
					e.printStackTrace();
				}
			}
			else
			{
				InputStream in = null;

				in = getClass().getResourceAsStream("/" + fileName);

				OutputStream out = null;
				try
				{
					out = new FileOutputStream(configFile);
					ByteStreams.copy(in, out);
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		this.config = YamlConfiguration.loadConfiguration(this.configFile);
	}

	public void save()
	{
		try
		{
			this.getConfig().save(this.configFile);
		}
		catch (IOException e)
		{
			Bukkit.getLogger().severe("Could not save config file " + this.configFile.toString());
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfig()
	{
		return this.config;
	}

	public String getFileName()
	{
		return fileName;
	}

	public File getConfigFile()
	{
		return configFile;
	}
}