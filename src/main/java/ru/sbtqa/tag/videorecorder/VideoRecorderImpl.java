package ru.sbtqa.tag.videorecorder;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.monte.media.Format;
import ru.sbtqa.monte.media.Registry;

public class VideoRecorderImpl extends ru.sbtqa.monte.screenrecorder.ScreenRecorder implements VideoRecorder {
    
    private static final Logger LOG = LoggerFactory.getLogger(VideoRecorderImpl.class);
    
    private final String tempFilenameWithoutExtension = "currentRecording_" + System.currentTimeMillis();
    private final File tempMovieFolder;
    
    private String currentTempExtension;
    
    public VideoRecorderImpl(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
          Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder)
          throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        tempMovieFolder = movieFolder;
    }
    
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        this.currentTempExtension = Registry.getInstance().getExtension(fileFormat);
        String tempFile = getTempFileName();
        
        File fileToWriteMovie = new File(tempFile);
        if (fileToWriteMovie.exists()) {
            if (!fileToWriteMovie.delete()) {
                LOG.error("Failed to remove temporary file {}", fileToWriteMovie.getAbsolutePath());
            }
        }
        
        return fileToWriteMovie;
    }
    
    private String getTempFileName() {
        return tempMovieFolder.getPath() + File.separator
              + tempFilenameWithoutExtension + "." + this.currentTempExtension;
    }
    
    @Override
    public String saveAs(String path, String filename) throws IOException {
        this.stop();
        
        File tempFile = this.getCreatedMovieFiles().get(0);
        
        File destFile = getDestinationFile(path, filename);
        if (!tempFile.renameTo(destFile)) {
            LOG.error("Failed to rename file {}", path + filename);
        }
        return destFile.getAbsolutePath();
    }
    
    private File getDestinationFile(String path, String filename) {
        String destFolderSuffix = "";
        
        return new File(path + destFolderSuffix + File.separator
              + filename + "." + this.currentTempExtension);
    }
    
}
