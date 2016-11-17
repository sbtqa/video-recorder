package ru.sbtqa.tag.videorecorder;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Viktor Sidochenko <viktor.sidochenko@gmail.com>
 */
public class VideoRecorderService {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRecorderService.class);

    private final TuentiScreenRecorder tuentiScreenRecorder;

    public VideoRecorderService(TuentiScreenRecorder tuentiScreenRecorder) {
        this.tuentiScreenRecorder = tuentiScreenRecorder;
    }

    public void start() {
        LOG.info("Video recording start request received");
        try {
            tuentiScreenRecorder.start();
            LOG.info("Video recording started");
        } catch (IOException e) {
            LOG.error("Could not start video recording.", e);
        }

    }

    public void stop() {
        try {
            tuentiScreenRecorder.stop();
            LOG.info("Video recording stopped");
        } catch (IOException e) {
            LOG.error("Could not stop video recording.", e);
        }
    }

    public String save(String path, String filename) {
        LOG.info("Request to save current video as " + filename + " received");
        String savedFile = null;
        try {
            savedFile = tuentiScreenRecorder.saveAs(path, filename);
            LOG.info("Video saved as " + savedFile);
        } catch (IOException e) {
            LOG.error("Could not save video file " + filename + " to destination path " + path, e);
        }
        return savedFile;
    }

}
