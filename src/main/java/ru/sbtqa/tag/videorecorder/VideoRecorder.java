package ru.sbtqa.tag.videorecorder;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.qautils.properties.Props;

public class VideoRecorder {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRecorder.class);

    private final String DEFAULT_VIDEOS_FOLDER = System.getProperty("java.io.tmpdir");
    private final String TEMP_FOLDER_PROPERTY_NAME = "video.path.temp";
    private final String DST_PATH_PROPERTY_NAME = "video.path.dest";
    private static VideoRecorder instance;
    private static VideoRecorderModule videoRecorderModule;
    private static VideoRecorderService service;
    private final String videoDestinationPath;
    private String savedVideoPath;
    private String videoFileName;
    private boolean isVideoStarted = false;

    public VideoRecorder() throws IOException {
        videoDestinationPath = Props.get(DST_PATH_PROPERTY_NAME);
        if (videoDestinationPath.isEmpty()) {
            throw new IOException("Property " + DST_PATH_PROPERTY_NAME + " was not set in application.properties");
        }
    }

    public static VideoRecorder getInstance() {
        if (instance == null) {
            try {
                instance = new VideoRecorder();
            } catch (IOException e) {
                LOG.error("Failed to get video recorder instance", e);
            }
        }
        return instance;
    }

    public void startRecording() {
        isVideoStarted = true;
        videoFileName = UUID.randomUUID().toString();
        videoRecorderModule = new VideoRecorderModule(getVideoFolderPath());
        Recorder provideScreenRecorder = videoRecorderModule.provideScreenRecorder();
        service = new VideoRecorderService(provideScreenRecorder);
        service.start();
    }

    public String stopRecording() {
        service.stop();
        isVideoStarted = false;
        savedVideoPath = service.save(videoDestinationPath, videoFileName);
        return savedVideoPath;
    }

    private String getVideoFolderPath() {
        String videoFolderPath = System.getProperty(TEMP_FOLDER_PROPERTY_NAME);
        if (videoFolderPath == null) {
            videoFolderPath = DEFAULT_VIDEOS_FOLDER;
        }

        return videoFolderPath;
    }

    public String getVideoPath() {
        return this.videoDestinationPath + "\\" + videoFileName + ".avi";
    }

    public boolean isVideoStarted() {
        return isVideoStarted;
    }

    public void resetVideoRecorder() {
        instance = null;
    }
}
