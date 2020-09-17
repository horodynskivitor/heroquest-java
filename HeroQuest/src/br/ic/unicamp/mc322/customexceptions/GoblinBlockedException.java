package br.ic.unicamp.mc322.customexceptions;

public class GoblinBlockedException extends RuntimeException {
	public GoblinBlockedException() {
		super();
	}

	public GoblinBlockedException(String message) {
		super(message);
	}
}
