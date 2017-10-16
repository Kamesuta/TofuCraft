package tsuteto.tofu.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tsuteto.tofu.Reference;

/**
 * Logger
 *
 * @author Tsuteto
 *
 */
public class ModLog {
	public static Logger log = LogManager.getLogger(Reference.MODID);
	public static boolean isDebug;

	public static void log(final Level level, final Throwable e, final String format, final Object... data) {
		log.log(level, String.format(format, data), e);
	}

	public static void log(final Level level, final String format, final Object... data) {
		log.log(level, String.format(format, data));
	}

	public static void info(final String format, final Object... data) {
		log(Level.INFO, format, data);
	}

	public static void debug(final Object format, final Object... data) {
		if (isDebug)
			//System.out.printf("[" + modId + "] " + format + "%n", data);
			info("(DEBUG) "+String.valueOf(format), data);
	}
}
