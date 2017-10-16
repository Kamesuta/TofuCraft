package tsuteto.tofu;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

public class Reference {
	public static final @Nonnull String MODID = "tofucraft";
	public static final @Nonnull String NAME = "豆腐Craft (TofuCraft)";
	public static final @Nonnull String DEPENDENCIES = "after:BambooMod;after:IC2";
	public static final @Nonnull String DESCRIPTION = "豆腐をおいしく頂きつつ本格豆腐建築を嗜むMODです。味噌、醤油、納豆、ずんだ、もやし、湯葉もあるよ。\n\n"
			+"TofuCraft adds Tofu that is a food made of soybeans and tastes very light but has so many ways to use and various dishes, and will show you a lot of features of Tofu such as many variations of tofu, dishes, mobs, tools, weapons, energy system, and Tofu world. I wish you liked Tofu :)";
	public static final @Nonnull String VERSION = "${version}";
	public static final @Nonnull String FORGE = "${forgeversion}";
	public static final @Nonnull String CREDITS = "Tofu fishing originally implemented by dewfalse";
	public static final @Nonnull String LOGO_FILE = "assets/tofucraft/textures/logo.png";
	public static final @Nonnull String URL = "http://forum.minecraftuser.jp/viewtopic.php?f=13&t=1014";
	public static final @Nonnull String UPDATE_URL = "https://dl.dropboxusercontent.com/u/14577828/mcmod/update/tofucraft.json";
	public static final @Nonnull List<String> AUTHORS = Lists.newArrayList("つてと (Tsuteto)");
	public static final @Nonnull String RESOURCE_DINAUN = "tofucraft:";
	//	public static final @Nonnull String MINECRAFT = "${mcversion}";
	public static final @Nonnull String PROXY_SERVER = "tsuteto.tofu.CommonProxy";
	public static final @Nonnull String PROXY_CLIENT = "tsuteto.tofu.ClientProxy";
}
