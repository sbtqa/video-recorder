package ru.sbtqa.tag.videorecorder;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoRecorder {

    private static final Logger LOG = LoggerFactory.getLogger(VideoRecorder.class);
    private static final String DEFAULT_EXTENSION = ".avi";

    private static VideoRecorderService service;
    private static String videoFolder = System.getProperty("java.io.tmpdir");
    private static String videoFileName;
    private static boolean isVideoRecording = false;

    public static void startRecording() {
        if (isVideoRecording) {
            LOG.debug("Video is already being recorded");
            return;
        }

        isVideoRecording = true;

        videoFileName = UUID.randomUUID().toString();

        VideoRecorderModule videoRecorderModule = new VideoRecorderModule(getVideoFolder());
        Recorder provideScreenRecorder = videoRecorderModule.provideScreenRecorder();
        service = new VideoRecorderService(provideScreenRecorder);

        service.start();
    }

    public static String stopRecording() {
        if (isVideoRecording) {
            service.stop();
            isVideoRecording = false;
            return service.save(videoFolder, videoFileName);
        } else {
            LOG.warn("Сan't stop the video. It is not recorded now");
            return "";
        }
    }

    public static String getVideoFolder() {
        return videoFolder;
    }

    public static void setVideoFolder(String videoFolder) {
        if (!videoFolder.equals("")) {
            VideoRecorder.videoFolder = videoFolder;
        }
    }

    public String getVideoName() {
        return videoFileName + DEFAULT_EXTENSION;
    }

    public static boolean isVideoRecording() {
        return isVideoRecording;
    }
}
