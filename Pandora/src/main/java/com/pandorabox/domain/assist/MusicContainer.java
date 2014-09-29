package com.pandorabox.domain.assist;

import java.util.List;

import com.pandorabox.domain.FileDescriptor;

public interface MusicContainer {

    public int getPickedMusicIndex();

    public void setPickedMusicIndex(int index);

    public List<FileDescriptor> getFiles();

    public void setFiles(List<FileDescriptor> files);

    public void setMusicSelected(List<FileDescriptor> uploadedMusics, String musicSelectedIndex);
}
