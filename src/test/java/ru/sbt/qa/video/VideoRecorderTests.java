/*
 * Copyright (c) Tuenti Technologies. All rights reserved.
 * @author David Santiago Turiño <dsantiago@tuenti.com>
 */
package ru.sbt.qa.video;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import ru.sbtqa.tag.videorecorder.Recorder;
import ru.sbtqa.tag.videorecorder.VideoRecorderService;

public class VideoRecorderTests {

    @Mock
    Recorder screenRecorder;
    private VideoRecorderService videoRecorder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        videoRecorder = newVideoRecorderInstance(screenRecorder);
    }

    @Test
    public void shouldStartRecordingWhenAskedToDoSo() throws Exception {
        videoRecorder.start();
//
        verify(screenRecorder).start();
    }

    @Test
    public void shouldStopRecordingWhenAskedToDoSo() throws Exception {
        videoRecorder.stop();
        verify(screenRecorder).stop();
    }

    @Test
    public void shouldSaveVideoRecordedWhenAskedToDoSo() throws Exception {
        String requestedVideoFileName = "whateverTheFileName";
        videoRecorder.save("", requestedVideoFileName);
        verify(screenRecorder).saveAs("", requestedVideoFileName);
    }

    private VideoRecorderService newVideoRecorderInstance(Recorder screenRecorder) {
        VideoRecorderService videoRecorder = new VideoRecorderService(screenRecorder);
        return videoRecorder;
    }
}
