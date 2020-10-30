package ssv.com.exception;

public class ResourceExistsException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ResourceExistsException() {
		super();
	}

	private Integer code;

	public ResourceExistsException(String message, Integer code) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public ResourceExistsException(String message, Throwable cause) {
		super(message, cause);
	}
}