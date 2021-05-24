package main;

public class FrequencyPair {
	private Integer code;

	private Integer frequency;

	public FrequencyPair(Integer code, Integer frequency) {
		this.code = code;
		this.frequency = frequency;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void incrementFrequency() {
		this.frequency++;
	}
}
