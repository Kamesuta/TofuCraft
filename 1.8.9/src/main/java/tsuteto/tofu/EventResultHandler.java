package tsuteto.tofu;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class EventResultHandler {
	private final Event ev;

	public EventResultHandler(final Event ev) {
		this.ev = ev;
	}

	public void setResult(final EventResult result) {
		this.ev.setResult(result.result);
	}

	public static enum EventResult {
		DENY(Result.DENY),
		DEFAULT(Result.DEFAULT),
		ALLOW(Result.ALLOW),
		;

		private final Result result;

		private EventResult(final Result result) {
			this.result = result;
		}
	}
}
