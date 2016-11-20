/*
 * Copyright (c) Tuenti Technologies. All rights reserved.
 * @author David Santiago Turiño <dsantiago@tuenti.com>
 */
package ru.sbt.qa.video;


import ru.sbtqa.tag.videorecorder.ScreenRecorder;
import ru.sbtqa.tag.videorecorder.VideoRecorderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class VideoRecorderTests {

    @Mock
    ScreenRecorder screenRecorder;
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

    private VideoRecorderService newVideoRecorderInstance(ScreenRecorder screenRecorder) {
        VideoRecorderService videoRecorder = new VideoRecorderService(screenRecorder);
        return videoRecorder;
    }
}
