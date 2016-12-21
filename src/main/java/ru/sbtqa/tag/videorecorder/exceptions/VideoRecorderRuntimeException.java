package ru.sbtqa.tag.videorecorder.exceptions;

public class VideoRecorderRuntimeException extends RuntimeException {

    /**
     * 
     * @param e  TODO
     */
    public VideoRecorderRuntimeException(Throwable e) {
        super(e);
    }
    
    /**
     *
     * @param message a {@link java.lang.String} object.
     * @param e a {@link java.lang.Throwable} object.
     */
    public VideoRecorderRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    /**
     *
     * @param message a {@link java.lang.String} object.
     */
    public VideoRecorderRuntimeException(String message) {
        super(message);
    }

}
