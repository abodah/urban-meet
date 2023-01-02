package org.abodah.meeting.entities;

public enum MeetingType {
    Call("Call", "Through phone call"), Zoom("Zoom", "through zoom");
    private String code;
    private String description;

    private MeetingType(String code, String description){
        this.code = code;
        this.description = description;
    }

}
