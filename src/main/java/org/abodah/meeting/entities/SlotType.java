package org.abodah.meeting.entities;

public enum SlotType {
    Busy("Busy", "Not Available"), Free("Free", "Free");
    private String code;
    private String description;
    private SlotType(String code, String description){
        this.code = code;
        this.description = description;
    }
}
