package ru.sbtqa.tag.videorecorder;

import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import org.monte.media.FormatKeys.MediaType;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.HeightKey;
import static org.monte.media.VideoFormatKeys.QualityKey;
import static org.monte.media.VideoFormatKeys.WidthKey;

public class VideoRecorderModule {

    private static final int MAX_RECORDING_TIME_SECS = Integer.MAX_VALUE;
    private static int FRAME_RATE_PER_SEC = 2;
    private static int BIT_DEPTH = 16;
    private static float QUALITY_RATIO = 0.7f;
    private final String videoFolderPath;

    public VideoRecorderModule(String videoFolderPath) {
        this.videoFolderPath = videoFolderPath;
    }

    public static void setFrameRatePerSec(int rate) {
        FRAME_RATE_PER_SEC = rate;
    }

    public static void setBitDepth(int bitDepth) {
        BIT_DEPTH = bitDepth;
    }

    public static void setQualityRatio(float qualityRatio) {
        QUALITY_RATIO = qualityRatio;
    }

    public TuentiScreenRecorder provideScreenRecorder() {
        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        File movieFolder = new File(videoFolderPath);
        String mimeType = FormatKeys.MIME_AVI;
        String videoFormatName = VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
        String compressorName = VideoFormatKeys.COMPRESSOR_NAME_AVI_TECHSMITH_SCREEN_CAPTURE;
        Dimension outputDimension = gc.getBounds().getSize();
        int bitDepth = BIT_DEPTH;
        float quality = QUALITY_RATIO;
        int screenRate = FRAME_RATE_PER_SEC;
        long maxRecordingTime = MAX_RECORDING_TIME_SECS;

        CustomScreenRecorder sr;
        try {
            sr = new CustomScreenRecorder(gc, gc.getBounds(),
                    getFileFormat(mimeType),
                    getOutputFormatForScreenCapture(videoFormatName, compressorName, outputDimension,
                            bitDepth, quality, screenRate),
                    getMouseFormat(),
                    getAudioFormat(),
                    movieFolder);
            sr.setMaxRecordingTime(maxRecordingTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        return sr;
    }

    private Format getMouseFormat() {
        return null;
    }

    private Format getAudioFormat() {
        return null;
    }

    private Format getOutputFormatForScreenCapture(String videoFormatName, String compressorName,
            Dimension outputDimension, int bitDepth, float quality, int screenRate) {
        return new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, videoFormatName,
                CompressorNameKey, compressorName,
                WidthKey, outputDimension.width,
                HeightKey, outputDimension.height,
                DepthKey, bitDepth, FrameRateKey, Rational.valueOf(screenRate),
                QualityKey, quality,
                KeyFrameIntervalKey, screenRate * 20 // one keyframe per minute
        );
    }

    private Format getFileFormat(String mimeType) {
        return new Format(MediaTypeKey, FormatKeys.MediaType.FILE, MimeTypeKey, mimeType);
    }

}
