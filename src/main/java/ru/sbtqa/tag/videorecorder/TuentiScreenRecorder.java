package ru.sbtqa.tag.videorecorder;

import java.io.IOException;

public interface TuentiScreenRecorder {

    public void start() throws IOException;

    public void stop() throws IOException;

    public String saveAs(String path, String fileName) throws IOException;

}
