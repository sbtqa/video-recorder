package ru.sbtqa.tag.videorecorder;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoRecorderService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRecorderService.class);

    private final Recorder screenRecorder;

    public VideoRecorderService(Recorder screenRecorder) {
        this.screenRecorder = screenRecorder;
    }

    public void start() {
        try {
            screenRecorder.start();
            LOG.info("Video recording started");
        } catch (IOException e) {
            LOG.error("Could not start video recording.", e);
        }

    }

    public void stop() {
        try {
            screenRecorder.stop();
            LOG.info("Video recording stopped");
        } catch (IOException e) {
            LOG.error("Could not stop video recording.", e);
        }
    }

    public String save(String path, String filename) {
        String savedFile = null;
        File dir = new File(path);
        if (!dir.exists()) {
            LOG.info("The directory at the path \"" + path + "\" does not exist. The directory was created");
            dir.mkdirs();
        }
        try {
            savedFile = screenRecorder.saveAs(path, filename);
            LOG.info("Video saved as " + savedFile);
        } catch (IOException e) {
            LOG.error("Could not save video file " + filename + " to destination path " + path, e);
        }
        return savedFile;
    }

}
