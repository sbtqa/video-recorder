package ru.sbtqa.tag.videorecorder;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

public class CustomScreenRecorder extends ScreenRecorder implements TuentiScreenRecorder {

    private final String TEMP_FILENAME_WITHOUT_EXTENSION = "currentRecording_" + System.currentTimeMillis();
    private final File TEMP_MOVIE_FOLDER;

    private String currentTempExtension;

    public CustomScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
            Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        TEMP_MOVIE_FOLDER = movieFolder;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        this.currentTempExtension = Registry.getInstance().getExtension(fileFormat);
        String tempFile = getTempFileName();

        File fileToWriteMovie = new File(tempFile);
        if (fileToWriteMovie.exists()) {
            fileToWriteMovie.delete();
        }

        return fileToWriteMovie;
    }

    private String getTempFileName() {
        return TEMP_MOVIE_FOLDER.getPath() + File.separator
                + TEMP_FILENAME_WITHOUT_EXTENSION + "." + this.currentTempExtension;
    }

    @Override
    public String saveAs(String path, String filename) throws IOException {
        this.stop();

        File tempFile = this.getCreatedMovieFiles().get(0);

        File destFile = getDestinationFile(path, filename);
        tempFile.renameTo(destFile);
        return destFile.getAbsolutePath();
    }

    private File getDestinationFile(String path, String filename) {
        String destFolderSuffix = "";

        File file = new File(path + destFolderSuffix + File.separator
                + filename + "." + this.currentTempExtension);

        return file;
    }

}